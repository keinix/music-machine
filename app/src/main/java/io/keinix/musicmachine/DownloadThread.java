package io.keinix.musicmachine;

import android.util.Log;

import static android.content.ContentValues.TAG;

public class DownloadThread extends Thread {

    @Override
    public void run() {
        for (String song : Playlist.songs) {
        downloadSong();
        }
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
