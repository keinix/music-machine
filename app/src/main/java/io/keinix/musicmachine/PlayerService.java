package io.keinix.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class PlayerService extends Service {
    public static final String TAG = PlayerService.class.getSimpleName();

    private MediaPlayer mPlayer;
    private IBinder mIBinder = new LocalBinder();

    @Override
    public void onCreate() {
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
        Log.d(TAG, "onCreate called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind called");
        return mIBinder;

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
            }
        });
        return Service.START_NOT_STICKY;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.d(TAG, "onUnBind called");
        return super.onUnbind(intent);
    }

    @Override
    public void onDestroy() {
        Log.d(TAG, "onDestroy called");
        mPlayer.release();
    }

    public class LocalBinder extends Binder {

        public PlayerService getService() {
            return PlayerService.this;
        }
    }

    // client methods

    public boolean isPlaying() {
        return mPlayer.isPlaying();
    }

    public void play() {
        mPlayer.start();
    }

    public void pause() {
        mPlayer.pause();
    }
}
