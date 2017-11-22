package io.keinix.musicmachine;

import android.os.Looper;
import android.util.Log;

import static android.content.ContentValues.TAG;

public class DownloadThread extends Thread {

    public DownloadHandeler mDownloadHandeler;

    @Override
    public void run() {
        Looper.prepare();
        mDownloadHandeler = new DownloadHandeler();
        Looper.loop();
    }


}
