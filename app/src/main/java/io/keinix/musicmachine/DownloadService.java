package io.keinix.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.Message;
import android.support.annotation.Nullable;
import android.util.Log;


public class DownloadService extends Service {

    public static final String TAG = DownloadService.class.getSimpleName();
    DownloadThread mThread;

    @Override
    public void onCreate() {
        mThread = new DownloadThread();
        mThread.setName("DownloadThread");
        mThread.start();
        mThread.mDownloadHandeler.setService(this);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String song = intent.getStringExtra(MainActivity.KEY_SONG);
        Message message = Message.obtain();
        message.obj = song;
        message.arg1 = startId;
        mThread.mDownloadHandeler.sendMessage(message);
        return Service.START_REDELIVER_INTENT;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
