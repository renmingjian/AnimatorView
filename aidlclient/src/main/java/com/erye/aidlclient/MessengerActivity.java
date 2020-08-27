package com.erye.aidlclient;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.Messenger;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.erge.animatorview.MyPerson;

public class MessengerActivity extends AppCompatActivity implements View.OnClickListener {

    // 客户端持有一个发送消息的Messenger对象
    private Messenger messenger;
    // 如果客户端需要处理服务端发送的消息，则还需要持有一个处理消息的Messenger对象
    private Messenger handleMessenger = new Messenger(new MessengerHandler());

    private ServiceConnection conn = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            Log.e("binder - client", "onServiceConnected");
            // 该对象需要在连接到服务端时根据服务端返回的Binder对象来创建
            messenger = new Messenger(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messenger);

        Button btn_send= findViewById(R.id.btn_send);
        btn_send.setOnClickListener(this);

        bindService();
    }


    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.erge.animatorview", "com.erge.animatorview.service.MessengerService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(conn);
    }

    @Override
    public void onClick(View v) {
        // 根据Messenger对象来发送消息
        Message message = new Message();
        message.what = 100;
        Bundle bundle = new Bundle();
        bundle.putString("msg", "Hello");
        message.setData(bundle);
        // 如果服务端需要回复信息给客户端，则需要客户端把Messenger给服务端
        message.replyTo = handleMessenger;
        try {
            messenger.send(message);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    // 处理服务端返回的消息
    private static class MessengerHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 101) {
                Bundle bundle = msg.getData();
                Log.e("binder - client ", bundle.getString("msg"));
            }
        }
    }

}