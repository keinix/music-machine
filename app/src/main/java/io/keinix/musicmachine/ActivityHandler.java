package io.keinix.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;


public class ActivityHandler extends Handler {

    private MainActivity mMainActivity;

    public ActivityHandler(MainActivity mainActivity) {
        mMainActivity = mainActivity;
    }

    @Override
    public void handleMessage(Message msg) {

        Log.d("FINDME", "ActivityHandler: " + "arg1 " + msg.arg1 + " " + "arg2: " + msg.arg2);
        System.out.println("activity Handler");

        if (msg.arg1 == 0) {
            // music is not playing
            if (msg.arg2 == 1) {
                // app is just being opened and onServiceConnected() called
                // play/pause button is NOT pressed
                mMainActivity.changePlayButtonText("Play");
            } else {
                Message message = Message.obtain();
                message.arg1 = 0; // play call in PlayerHandler
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mMainActivity.changePlayButtonText("Pause");
            }

        } else if (msg.arg1 == 1 ) {
            // music is playing
            if (msg.arg2 == 1) {
                // app is just being opened and onServiceConnected() called
                // play/pause button is NOT pressed
                mMainActivity.changePlayButtonText("Pause");
            } else {
                Message message = Message.obtain();
                message.arg1 = 1; // pause call in PlayerHandler
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                mMainActivity.changePlayButtonText("Play");
            }
        }
    }
}
