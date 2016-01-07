package com.commonliabray.aidl;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * @author vision 计时服务,负计时并通知被调用者
 */
public class NativeAIDLService extends Service {

	private int number;
	
	/**
	 * 如果可能有多个监听者，则需要一个列表来记录所有的监听者
	 */
	private MessageListener mListener;

	@Override
	public void onCreate() {
		super.onCreate();
		new Thread() {

			public void run() {

				while (true) {
	
					number++;
					Log.e("countservice:", number + " ");
					if (mListener != null) {

						mListener.notifyMessage(number);
					}
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
	public int onStartCommand(Intent intent, int flags, int startId) {

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return new CountBinder();
	}

	public class CountBinder extends Binder {

		/**
		 * 主动推送消息到客户端
		 * @param listener
		 */
		public void registerListener(MessageListener listener) {
			mListener = listener;
		}

		/**
		 * 由客户端主动调用,即主动拉取数据
		 * @return
		 */
		public int getNumber() {
			return number;
		}
	}

	public interface MessageListener {

		public void notifyMessage(int number);
	}
}