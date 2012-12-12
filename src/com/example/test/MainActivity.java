
package com.example.test;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity {

	// サウンドファイル名
    private static final String RECORDED_AUDIO = "sound.3gp";
    private MediaPlayer mPlayer;
    private MediaRecorder mRecorder;
    private File mSound;

    private Button recordButton;
    private Button playButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSound = new File(getCacheDir(), RECORDED_AUDIO);

        recordButton = (Button) findViewById(R.id.button_toggle_record);
        playButton = (Button) findViewById(R.id.button_play);

        recordButton.setOnClickListener(new recordButtonOnClickListener());
        playButton.setOnClickListener(new playButtonOnClickListener());

        if (mSound.exists()) {
            playButton.setEnabled(true);
        } else {
            playButton.setEnabled(false);
        }

    }

    /**
     * 録音ボタンが押されたときの動作を規定する
     * 
     * @author keima
     */
    class recordButtonOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            // ボタンを押す毎にファイルの存在を確認する
            if (mSound.exists()) {
                playButton.setEnabled(true);
            } else {
                playButton.setEnabled(false);
            }

            if (recordButton.getText().equals(getString(R.string.start_record))) {
                // 録音開始の動作
                recordButton.setText(R.string.stop_record);

                mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
                mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
                mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
                mRecorder.setOutputFile(mSound.getAbsolutePath());
                try {
                    mRecorder.prepare();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mRecorder.start();

            } else {
                // 録音終了の動作
                recordButton.setText(R.string.start_record);

                mRecorder.stop();
            }

        }
    }

    /**
     * 再生ボタンが押されたときの動作を規定する
     * 
     * @author keima
     */
    class playButtonOnClickListener implements OnClickListener {

        @Override
        public void onClick(View v) {
            // TODO Auto-generated method stub

            if (playButton.getText().equals(getString(R.string.play_sound))) {
                // 再生開始の動作
                playButton.setText(R.string.stop_sound);

                try {
                    mPlayer.setDataSource(mSound.getAbsolutePath());
                } catch (IllegalArgumentException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (SecurityException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IllegalStateException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }

                try {
                    mPlayer.prepare();
                } catch (IllegalStateException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                mPlayer.start();
            } else {
                // 再生終了(停止)の動作
                playButton.setText(R.string.play_sound);

                mPlayer.stop();

            }

        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        mPlayer = new MediaPlayer();
        mRecorder = new MediaRecorder();

        mPlayer.setOnCompletionListener(new SampleCompletionListener());
    }

    @Override
    protected void onPause() {
        super.onPause();

        mPlayer.release();
        mRecorder.release();
    }

    class SampleCompletionListener implements OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            // 再生終了後にどういう処理をするか

            playButton.setText(R.string.play_sound);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        // getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // TODO Auto-generated method stub
        return super.onOptionsItemSelected(item);

    }

}
