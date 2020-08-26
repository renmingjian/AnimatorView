package com.erge.animatorview;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by mj on 2020/8/26 10:13
 */
public class IRemoteService extends Service {

    private List<MyPerson> list;

    /**
     * 当客户端绑定到该服务时，onBind方法会被回调
     *
     * @param intent
     * @return
     */
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        System.out.println("binder = onBind");
        list = new ArrayList<>();
        return binder;
    }

    private Binder binder = new IMyAidlInterface.Stub() {

        @Override
        public List<MyPerson> add(MyPerson person) throws RemoteException {
            list.add(person);
            return list;
        }
    };

}
