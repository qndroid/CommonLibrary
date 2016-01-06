package com.commonliabray.aidl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.commonliabray.aidl.callback.IGetMessage.Stub;
import com.commonliabray.aidl.callback.Message;
import com.commonliabray.aidl.callback.User;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

/**
 * @author vision 供客户端调用的远程服务 1.创建AIDL接口文件和要传递的数据(不是基本类型的必须Parcelable)。
 *         2.远程服务实现生成的Stub，onBind();方法返回此binder给绑定远程服务的客户端 3.清单文件中注册
 */
public class CustomRemoteService extends Service {

	private MessageBinder messageBinder;
	private static Map<User, List<Message>> map = new HashMap<User, List<Message>>();

	/**
	 * 实现aidl接口中的方法
	 * 
	 * @author vision
	 *
	 */
	public class MessageBinder extends Stub {

		@Override
		public List<Message> getMessage(User user) throws RemoteException {
			for (Map.Entry<User, List<Message>> msgs : map.entrySet()) {
				if (msgs.getKey().getUsername().equals(user.getUsername())
						&& msgs.getKey().getPassword().equals(user.getPassword())) {
					return msgs.getValue();
				}
			}
			return map.get(user);
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		initData();
		messageBinder = new MessageBinder();

	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		messageBinder = null;
	}

	@Override
	public IBinder onBind(Intent intent) {
		return messageBinder;
	}

	/**
	 * 初始化模拟数据
	 */
	private void initData() {
		for (int i = 0; i < 3; i++) {
			User user = new User(i, "jack" + i, "9999999999" + i);
			List<Message> messages = new ArrayList<Message>();
			Message msg = null;
			if (i == 0) {
				msg = new Message(i, "dog0", "Jerry", new Date().toGMTString());
				messages.add(msg);
			} else if (i == 1) {
				msg = new Message(i, "dog1", "Tim", new Date().toGMTString());
				messages.add(msg);
				msg = new Message(i, "cat1", "Wesley", new Date().toGMTString());
				messages.add(msg);
			} else {
				msg = new Message(i, "dog2", "Bonnie", new Date().toGMTString());
				messages.add(msg);
				msg = new Message(i, "cat2", "Matt", new Date().toGMTString());
				messages.add(msg);
				msg = new Message(i, "snake2", "Caroline", new Date().toGMTString());
				messages.add(msg);
			}
			map.put(user, messages);
		}
	}
}