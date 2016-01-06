package com.commonliabray.aidl;

import com.commonliabray.aidl.callback.IGetMessage;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;

/**
 * @author vision 模拟调用远程Service 1.调用远程服务时要把aidl中的所有文件拷贝过来，包名，类名要完全一样。
 *         2.通过ServerConnection去获取远程Service返回来的binder对象去调用远程Service提供的方法
 */
public class CustomClientActivity extends Activity {

	private IGetMessage iGetMessage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = new Intent();
		intent.setAction("com.commonliabray.aidl.CUSTOM_REMOTE_SERVICE");
		bindService(intent, conn, BIND_AUTO_CREATE);
	}

	/**
	 * 服务被正常启动以后就可以通过ServiceConnection得到AIDL接口的实现，从而调用远程接口
	 */
	private ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceDisconnected(ComponentName name) {
			iGetMessage = null;
		}

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			iGetMessage = IGetMessage.Stub.asInterface(service);
		}
	};
}