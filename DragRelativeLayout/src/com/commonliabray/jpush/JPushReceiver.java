package com.commonliabray.jpush;

import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.commonliabray.activity.demo.MainActivity;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningTaskInfo;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 
 * 如果不定义这个 Receiver，则： 1) 默认用户会打开主界面 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {
	private static final String TAG = "JPush";

	@Override
	public void onReceive(Context context, Intent intent) {
		Bundle bundle = intent.getExtras();
		Log.d(TAG, "[MyReceiver] onReceive - " + intent.getAction() + ", extras: " + printBundle(bundle));

		if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知");
			int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
			Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

		} else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
			Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

			// // 打开自定义的Activity
			// Intent i = new Intent(context, JPushTestActivity.class);
			// i.putExtras(bundle);
			// // i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			// i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |
			// Intent.FLAG_ACTIVITY_CLEAR_TOP);
			// context.startActivity(i);
			/**
			 * 此处可以通过写一个方法，决定出要跳转到那些页面，一些细节的处理，可以通过是不是从推送过来的，去多一个分支去处理。
			 * 1.应用未启动，则依次启动以下三个页面。一次推送跳转流程中止。
			 * 2.如果应用已经启动，------>不需要登陆的信息类型，直接跳转到信息展示页面。 ------>需要登陆的信息类型
			 * ------>已经登陆----->直接跳转到信息展示页面。 ------>未登陆------->则跳转到登陆页面
			 * ----->登陆完毕，跳转到信息展示页面。 ----->取消登陆，回到首页。
			 * 
			 * 3.startActivities(Intent[]);在推送中的妙用,注意startActivity在生命周期上的一个细节,
			 * 前面的Activity是不会真正创建的，直到要到对应的页面
			 * 4.如果为了利用，可以将极光推送封装到一个Manager类中,为外部提供init, setTag, setAlias,
			 * setNotificationCustom等一系列常用的方法。
			 */
			// Intent intentZero = new Intent(context, MainActivity.class);
			// intentZero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK); //
			// 如果本包名的应用还没有启动，则一下要加此flag,
			// // 为其新建一个任务栈。如果应用已经启动，则不需要.
			// Intent intentOne = new Intent(context, JPushMainActivity.class);
			// // intentOne.putExtras(bundle);
			// Intent intentTwo = new Intent(context, JPushTestActivity.class);
			// intentTwo.putExtras(bundle);
			// context.startActivities(new Intent[] { intentZero, intentOne,
			// intentTwo });

			if (getCurrentTask(context)) {
				/**
				 * 已经运行,无需用户登陆的消息
				 */
				Log.e("------>", "is running");
				Intent intentOne = new Intent(context, JPushTestActivity.class);
				intentOne.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intentOne.putExtras(bundle);
				context.startActivity(intentOne);
				/**
				 * 已经运行，需要用户登陆
				 */

			} else {
				/**
				 * 还未运行,无需用户登陆的消息
				 */
				Log.e("------>", "is not running");
				Intent intentZero = new Intent(context, MainActivity.class);
				intentZero.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				Intent intentTwo = new Intent(context, JPushTestActivity.class);
				intentTwo.putExtras(bundle);
				context.startActivities(new Intent[] { intentZero, intentTwo });
				/**
				 * 还未运行，需要用户登陆 
				 */
			}
		}
	}

	// 打印所有的 intent extra 数据
	private static String printBundle(Bundle bundle) {
		StringBuilder sb = new StringBuilder();
		for (String key : bundle.keySet()) {
			if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
			} else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
				sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
			} else if (key.equals(JPushInterface.EXTRA_EXTRA)) {
				if (bundle.getString(JPushInterface.EXTRA_EXTRA).isEmpty()) {
					Log.i(TAG, "This message has no Extra data");
					continue;
				}

				try {
					JSONObject json = new JSONObject(bundle.getString(JPushInterface.EXTRA_EXTRA));
					Iterator<String> it = json.keys();

					while (it.hasNext()) {
						String myKey = it.next().toString();
						sb.append("\nkey:" + key + ", value: [" + myKey + " - " + json.optString(myKey) + "]");
					}
				} catch (JSONException e) {
					Log.e(TAG, "Get message extra JSON error!");
				}

			} else {
				sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
			}
		}
		return sb.toString();
	}

	/**
	 * 这个是真正的获取指定包名的应用程序是否在运行(无论前台还是后台)
	 * 
	 * @return
	 */
	private boolean getCurrentTask(Context context) {

		ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		List<ActivityManager.RunningTaskInfo> appProcessInfos = activityManager.getRunningTasks(50);
		for (RunningTaskInfo process : appProcessInfos) {

			if (process.baseActivity.getPackageName().equals(context.getPackageName())
					|| process.topActivity.getPackageName().equals(context.getPackageName())) {

				return true;
			}
		}
		return false;
	}
}
