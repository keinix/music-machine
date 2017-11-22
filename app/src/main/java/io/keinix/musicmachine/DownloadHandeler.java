package io.keinix.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.util.Log;



public class DownloadHandeler extends Handler {

    public static final String TAG = DownloadHandeler.class.getSimpleName();
    @Override
    public void handleMessage(Message msg) {
        downloadSong(msg.obj.toString());
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
