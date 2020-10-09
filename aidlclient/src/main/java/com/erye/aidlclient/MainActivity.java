package com.erye.aidlclient;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.erge.animatorview.IMyAidlInterface;
import com.erge.animatorview.MyPerson;

import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

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
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn_start = findViewById(R.id.btn_start);

        btn_start.setOnClickListener(this);
        getSystemService(Context.WINDOW_SERVICE);

        bindService();


        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putParcelable("bitmap", bitmap);
        intent.putExtras(bundle);

    }

    @Override
    protected void onResume() {
        super.onResume();
        startActivity(new Intent(this, MessengerActivity.class));
    }

    private void bindService() {
        Intent intent = new Intent();
        intent.setComponent(new ComponentName("com.erge.animatorview", "com.erge.animatorview.IRemoteService"));
        bindService(intent, conn, Context.BIND_AUTO_CREATE);
    }

    @Override
    public void onClick(View v) {
        try {
            List<MyPerson> list = iMyAidlInterface.add(new MyPerson("name" + click, click), 1);
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
