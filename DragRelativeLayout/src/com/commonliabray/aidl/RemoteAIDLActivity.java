package com.commonliabray.aidl;

import com.commonliabray.aidl.callback.IApplicationServiceRemoteBinder;
import com.commonliabray.aidl.callback.TimeServiceCallback;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

public class RemoteAIDLActivity extends Activity {

	/**
	 * 远程服务Binder接口
	 */
	private IApplicationServiceRemoteBinder iBinder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent();
		intent.setComponent(
				new ComponentName("com.example.dragrelativelayout", "com.commonliabray.aidl.RemoteAIDLService"));
		bindService(intent, conn, BIND_AUTO_CREATE);
	}

	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {

			try {
				iBinder.unRegisterCallback(timeCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {

			iBinder = IApplicationServiceRemoteBinder.Stub.asInterface(service);
			try {
				iBinder.registerCallback(timeCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
	};

	protected void onDestroy() {
		super.onDestroy();
		unbindService(conn);
		if (iBinder != null) {
			try {
				iBinder.unRegisterCallback(timeCallback);
			} catch (RemoteException e) {
				e.printStackTrace();
			}
		}
		iBinder = null;
		conn = null;
		timeCallback = null;
	}

	/**
	 * 应用层业务处理回调
	 */
	private TimeServiceCallback.Stub timeCallback = new TimeServiceCallback.Stub() {

		@Override
		public void onTimer(int numIndex) throws RemoteException {

			Log.e("----->in the remote activity:", numIndex + " ");
		}
	};
}