package io.keinix.musicmachine;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
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
    public static final String KEY_SONG = "SONG";
    private boolean mIsServiceBound = false;
    private Messenger mServiceMessenger;
    public Messenger mActivityMessenger = new Messenger(new ActivityHandler(this));
    // private PlayerService mPlayerService;
    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder binder) {
            Log.d(TAG, "onServiceConnectedCalled");
            mIsServiceBound = true;
            mServiceMessenger = new Messenger(binder);
            Message message = Message.obtain();
            message.arg1 = 2; // check if Music is playing
            message.arg2 = 1; // just update Text
            message.replyTo = mActivityMessenger;
            try {
                mServiceMessenger.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            // this is only called when something un expected happens
            // !!! you need to update mIsServiceBound when you unBind as well
            mIsServiceBound = false;
        }
    };


    @BindView(R.id.downloadButton) Button mDownloadButton;
    @BindView(R.id.playPauseButton) Button mPlayPauseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        // start thread early so it can init before sending messages


        mDownloadButton.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {

             Toast.makeText(MainActivity.this, "Downloading", Toast.LENGTH_SHORT).show();
             // send Messages or  to handler for processing

             for (String song : Playlist.songs) {
                 Intent intent = new Intent(MainActivity.this, DownloadIntentService.class);
                 intent.putExtra(KEY_SONG, song);
                 startService(intent);
             }

            }
        });

        mPlayPauseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 if (mIsServiceBound) {
                     Intent intent = new Intent(MainActivity.this, PlayerService.class);
                     startService(intent);

                     Message message = Message.obtain();
                     message.arg1 = 2;
                     message.replyTo = mActivityMessenger;
                     try {
                         mServiceMessenger.send(message);
                     } catch (RemoteException e) {
                         e.printStackTrace();
                     }
                 }

            }
         });

    }

    public void changePlayButtonText(String text) {
        mPlayPauseButton.setText(text);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this, PlayerService.class);
        bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (mIsServiceBound) {
            unbindService(mServiceConnection);
            mIsServiceBound = false;
        }
    }


}
