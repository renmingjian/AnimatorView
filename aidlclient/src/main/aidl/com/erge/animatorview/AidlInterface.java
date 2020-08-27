
package com.erge.animatorview;

/**
 * AIDL的文件说明
 */
public interface AidlInterface extends android.os.IInterface {
    /**
     * Default implementation for IMyAidlInterface.
     */
    public static class Default implements AidlInterface {
        @Override
        public java.util.List<com.erge.animatorview.MyPerson> add(com.erge.animatorview.MyPerson person, int num) throws android.os.RemoteException {
            return null;
        }

        @Override
        public android.os.IBinder asBinder() {
            return null;
        }
    }

    /**
     * Local-side IPC implementation stub class.
     */
    public static abstract class Stub extends android.os.Binder implements AidlInterface {
        private static final java.lang.String DESCRIPTOR = "com.erge.animatorview.IMyAidlInterface";

        /**
         * Construct the stub at attach it to the interface.
         */
        public Stub() {
            // 让Binder关联到我们的接口IMyAidlInterface
            this.attachInterface(this, DESCRIPTOR);
        }

        /**
         * Cast an IBinder object into an com.erge.animatorview.IMyAidlInterface interface,
         * generating a proxy if needed.
         */
        public static AidlInterface asInterface(android.os.IBinder obj) {
            if ((obj == null)) {
                return null;
            }
            // 取出构造函数中关联的接口引用
            android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (((iin != null) && (iin instanceof AidlInterface))) {
                return ((AidlInterface) iin);
            }
            return new AidlInterface.Stub.Proxy(obj);
        }

        @Override
        public android.os.IBinder asBinder() {
            return this;
        }

        /**
         * 该方法是在服务端调用的
         *
         * @param code  客户端传入的数据，是调用哪个方法的标识
         * @param data  输入型数据，是客户端transact方法传入的数据，无论方法多少个参数，都会被组装到这里成
         *              为一个参数。通过他可以拿到方法的多个参数。
         * @param reply 输出型参数，是客户端transact方法传入的数据，如果方法有返回值，则返回的内容需要写入
         *              到这里。写入数据后直接交给客户端的transact方法
         * @param flags 暂时无用
         * @return 如果返回true，则客户端可以相应成功，否则失败
         * @throws android.os.RemoteException
         */
        @Override
        public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException {
            java.lang.String descriptor = DESCRIPTOR;
            switch (code) {
                case INTERFACE_TRANSACTION: {
                    reply.writeString(descriptor);
                    return true;
                }
                // 该case值是客户端调用transact方法时传入的方法名称的标识，通过他服务端可以知道调用的是哪个
                // 方法。比如这里调用的是add方法。由于我们的接口只定义了一个add方法，这里才会只有一个，如果
                // 还有其他方法，这里会有多个case，根据case调用不同的方法
                case TRANSACTION_add: {
                    // 根据case可以知道调用哪个方法。所以这里的主要逻辑是，通过data拿到方法需要使用的所有
                    // 参数，然后直接调用Binder所实现的接口的方法，这里是add方法，调用后会拿到一个返回值，
                    // 这里的返回值是一个集合类型，也可以是其他的类型，或者是void。拿到返回值后，写入到reply
                    // 中。客户端就可以拿到有返回值的reply参数
                    data.enforceInterface(descriptor);
                    // 1.拿参数
                    com.erge.animatorview.MyPerson _arg0;
                    if ((0 != data.readInt())) {
                        _arg0 = com.erge.animatorview.MyPerson.CREATOR.createFromParcel(data);
                    } else {
                        _arg0 = null;
                    }
                    int _arg1;
                    _arg1 = data.readInt();
                    // 2.调用方法
                    java.util.List<com.erge.animatorview.MyPerson> _result = this.add(_arg0, _arg1);
                    // 3.写入返回值
                    reply.writeNoException();
                    reply.writeTypedList(_result);
                    // 4.return后，客户端线程继续执行。
                    return true;
                }
                default: {
                    return super.onTransact(code, data, reply, flags);
                }
            }
        }

        private static class Proxy implements AidlInterface {
            private android.os.IBinder mRemote;

            Proxy(android.os.IBinder remote) {
                mRemote = remote;
            }

            @Override
            public android.os.IBinder asBinder() {
                return mRemote;
            }

            public java.lang.String getInterfaceDescriptor() {
                return DESCRIPTOR;
            }

            /**
             * 此方法是在客户端执行的。其中transct方法是远程请求，远程请求的同时，服务端的onTransact方法会被
             * 调用，是一个同步操作，会耗时，直到远程请求成功，然后把返回值返回给客户端（如果有返回值的话）。
             * 无论如何，只要onTransact执行了return操作，客户端当前线程会被继续执行。
             *
             * @param person
             * @param num
             * @return
             * @throws android.os.RemoteException
             */
            @Override
            public java.util.List<com.erge.animatorview.MyPerson> add(com.erge.animatorview.MyPerson person, int num) throws android.os.RemoteException {
                // 创建方法所需的输入型Parcel对象，该对象为发送远程请求使用--客户端创建时调用
                android.os.Parcel _data = android.os.Parcel.obtain();
                // 创建方法所需的输出型Parcel对象，该对象为发送远程请求使用--客户端创建时调用
                android.os.Parcel _reply = android.os.Parcel.obtain();
                // 创建方法的返回值对象，当远程请求成功后，该对象用于返回--客户端创建时调用
                java.util.List<com.erge.animatorview.MyPerson> _result;
                try {
                    // 当方法被调用时，对方法的参数进行封装，写入到输入性数据_data中
                    _data.writeInterfaceToken(DESCRIPTOR);
                    if ((person != null)) {
                        _data.writeInt(1);
                        person.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(num);

                    // 此方法是客户端发起远程请求，可能耗时，然后当前线程会挂起，等待服务端的响应。
                    // transact方法被调用后，服务端的onTransact方法会被调用。在onTransact方法中会获取到
                    // 客户端调用的方法传进来的参数，当然由于Proxy对应的方法会对参数进行组装，这里需要解析
                    // 出数据。数据解析成功后，服务端再调用方法（跟客户端发送请求调用的是同一个方法，该方法
                    // 就是Sub类实现的接口的方法，只有调用后，在Service中接口的方法才会被调用），调用此方法
                    // 后会得到一个返回值，并把该方法值写入到上面创建的输出型_reply中。
                    boolean _status = mRemote.transact(IMyAidlInterface.Stub.TRANSACTION_add, _data, _reply, 0);
                    if (!_status && getDefaultImpl() != null) {
                        return getDefaultImpl().add(person, num);
                    }

                    // 然后当前线程继续执行，并从_reply中读取数据
                    _reply.readException();
                    _result = _reply.createTypedArrayList(com.erge.animatorview.MyPerson.CREATOR);
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }

                // 作为方法的返回值返回。客户端就可以拿到数据
                return _result;
            }

            public static AidlInterface sDefaultImpl;
        }

        static final int TRANSACTION_add = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);

        public static boolean setDefaultImpl(AidlInterface impl) {
            if (AidlInterface.Stub.Proxy.sDefaultImpl == null && impl != null) {
                AidlInterface.Stub.Proxy.sDefaultImpl = impl;
                return true;
            }
            return false;
        }

        public static AidlInterface getDefaultImpl() {
            return AidlInterface.Stub.Proxy.sDefaultImpl;
        }
    }

    public java.util.List<com.erge.animatorview.MyPerson> add(com.erge.animatorview.MyPerson person, int num) throws android.os.RemoteException;
}
