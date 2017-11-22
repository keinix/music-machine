package io.keinix.musicmachine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    @BindView(R.id.downloadButton) Button mDownloadButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

     mDownloadButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();

             Runnable runnable = new Runnable() {
                 @Override
                 public void run() {
                     downloadSong();
                 }
             };

             DownloadThread thread = new DownloadThread();
             thread.setName("DownloadThread");
             thread.start();
         }
     });
    }

    private void downloadSong() {
        long endTime = System.currentTimeMillis() + (10 * 1000);
        while (System.currentTimeMillis() < endTime ) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "song DLed");
    }
}
