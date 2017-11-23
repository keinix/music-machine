package io.keinix.musicmachine;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.util.Log;


public class DownloadIntentService extends IntentService {
    public static final String TAG = DownloadService.class.getSimpleName();


    public DownloadIntentService() {
        super("DownloadIntentService");
        setIntentRedelivery(true);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String song = intent.getStringExtra(MainActivity.KEY_SONG);
        downloadSong(song);
    }

    private void downloadSong(String song) {
        long endTime = System.currentTimeMillis() + (10 * 1000);
        while (System.currentTimeMillis() < endTime ) {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, "Download Finished: " + song);
    }
}
