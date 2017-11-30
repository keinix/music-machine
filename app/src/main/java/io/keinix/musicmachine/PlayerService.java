package io.keinix.musicmachine;

import android.app.Notification;
import android.app.Service;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.os.Messenger;
import android.support.annotation.Nullable;
import android.util.Log;


public class PlayerService extends Service {
    public static final String TAG = PlayerService.class.getSimpleName();

    private MediaPlayer mPlayer;
    public Messenger mMessenger = new Messenger(new PlayerHandler(this));
    // private IBinder mIBinder = new LocalBinder();

    @Override
    public void onCreate() {
        mPlayer = MediaPlayer.create(this, R.raw.jingle);
        Log.d(TAG, "onCreate called");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind called");
        return mMessenger.getBinder();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Notification.Builder notificationBuilder = new Notification.Builder(this);
        notificationBuilder.setSmallIcon(R.mipmap.ic_launcher);
        Notification notification = notificationBuilder.build();
        startForeground(11, notification);

        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopSelf();
                stopForeground(true);
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

    // use this if your are not binding across processes
    // return LocalBinder in onBind the retrive it in the serviceConnection
    // in the activity. in the method cast binder to LocalBinder and call
    // getService() to set a service field
    /*public class LocalBinder extends Binder {

        public PlayerService getService() {
            return PlayerService.this;
        }
    }*/

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
