package io.keinix.musicmachine;

import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;


public class PlayerService extends Service {
    public static final String TAG = PlayerService.class.getSimpleName();

    private MediaPlayer mPlayer;

    @Override
    public void onCreate() {
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
        Log.d(TAG, "onCreate called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind called");
        return null;

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

    // client methods

    public void play() {
        mPlayer.start();
    }

    public void pause() {
        mPlayer.pause();
    }
}
