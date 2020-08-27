package com.erge.animatorview.service;

import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erge.animatorview.MyConstants;

/**
 * 服务端Service
 * Created by mj on 2020/8/27 9:50
 */
public class MessengerService extends Service {

    // 需要持有Messenger对象，并且Messenger对象需要一个Handler来发送消息方便我们自己处理
    private Messenger messenger = new Messenger(new MessengerHandler());

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        // Messenger中有Binder对象，在客户端绑定的时候把Binder给客户端
        return messenger.getBinder();
    }

    // Handler对象，我们只需要处理消息即可，消息的发送是在Messenger中进行的。
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what) {
                case MyConstants.MSG_MESSENGER_CLIENT:
                    Bundle bundle = msg.getData();
                    Log.e("binder - service ", bundle.getString("msg"));
                    send(msg);
                    break;
                default:
                    super.handleMessage(msg);
            }
        }

        // 服务端收到客户端消息后，向客户端发送消息
        private void send(Message msg) {
            Messenger client = msg.replyTo;
            if (client == null) return;
            Message message = new Message();
            message.what = 101;
            Bundle bundle = new Bundle();
            bundle.putString("msg", "消息已收到");
            message.setData(bundle);
            try {
                client.send(message);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 权限验证，看绑定服务的客户端是否具有该权限，如果没有则不提供服务
     */
    private boolean hasPermission() {
        int permission = checkCallingOrSelfPermission("com.erge.animatorview.REMOTE_SERVICE_PERMISSION");
        return permission == PackageManager.PERMISSION_GRANTED;
    }

}
