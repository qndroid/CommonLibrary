package com.commonliabray.aidl;

import com.commonliabray.aidl.callback.IApplicationServiceRemoteBinder;
import com.commonliabray.aidl.callback.TimeServiceCallback;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteCallbackList;
import android.os.RemoteException;

public class RemoteAIDLService extends Service {

	private RemoteCallbackList<TimeServiceCallback> timeCallbackList;
	private int index;

	@Override
	public void onCreate() {
		super.onCreate();
		timeCallbackList = new RemoteCallbackList<TimeServiceCallback>();
		new Thread() {

			public void run() {

				for (index = 0; true; index++) {

					int count = timeCallbackList.beginBroadcast();
					while (count-- > 0) {

						try {
							timeCallbackList.getBroadcastItem(count).onTimer(index);
						} catch (RemoteException e) {
							e.printStackTrace();
						}
					}

					timeCallbackList.finishBroadcast();

					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			};
		}.start();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new RemoteCountBinder();
	}

	public class RemoteCountBinder extends IApplicationServiceRemoteBinder.Stub {

		@Override
		public void registerCallback(TimeServiceCallback callback) throws RemoteException {
			timeCallbackList.register(callback);
		}

		@Override
		public void unRegisterCallback(TimeServiceCallback callback) throws RemoteException {
			timeCallbackList.unregister(callback);
		}
	}
}