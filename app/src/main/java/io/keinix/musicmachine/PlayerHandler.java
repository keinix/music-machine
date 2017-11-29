package io.keinix.musicmachine;

import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;


public class PlayerHandler extends Handler {

    private PlayerService mPlayerService;


    public PlayerHandler(PlayerService playerService) {
        mPlayerService = playerService;
    }

    @Override
    public void handleMessage(Message msg) {
        switch (msg.arg1) {
            case 0: // play
                mPlayerService.play();
                break;
            case 1: // pause
                mPlayerService.pause();
                break;
            case 2: // isPLaying
                // the activity will reply to this mess
                msg.replyTo = mPlayerService.mMessenger;

                int isPlaying = mPlayerService.isPlaying() ? 1 : 0;
                Message message = Message.obtain();
                message.arg1 = isPlaying;
                if (msg.arg2 == 1) {
                    message.arg2 = 1;
                }
                try {
                    msg.replyTo.send(message);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                break;
        }
    }
}
