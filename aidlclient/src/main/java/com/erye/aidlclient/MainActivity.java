package com.erye.aidlclient;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.erge.animatorview.IMyAidlInterface;
import com.erge.animatorview.MyPerson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText et_num1, et_num2;
    private TextView tv_add;
    private Button btn_start;
    IMyAidlInterface iMyAidlInterface;
    private int click;
    // 服务连接对象
    private ServiceConnection conn = new ServiceConnection() {
        // 当服务绑定的时候回调
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            System.out.println("binder = onServiceConnected");
            iMyAidlInterface = IMyAidlInterface.Stub.asInterface(service);
        }

        // 当服务断开的时候回调
        @Override
        public void onServiceDisconnected(ComponentName name) {
            System.out.println("binder = onServiceDisconnected");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_num1 = findViewById(R.id.et_num1);
        et_num2 = findViewById(R.id.et_num2);
        tv_add = findViewById(R.id.tv_add);
        btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(this);
        bindService();
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.erge.animatorview",  "com.erge.animatorview.IRemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        try {
            List<MyPerson> list = iMyAidlInterface.add(new MyPerson("name" + click, click));
            System.out.println(list.toString());
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        click++;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 接触绑定
        unbindService(conn);
    }
}
