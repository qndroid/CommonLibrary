package com.renzhiqiang.update;

import java.io.File;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.example.commonhttplibrary.R;

/**********************************************************
 * @文件名称：UpdateService.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年8月26日 下午10:58:55
 * @文件描述：app更新下载后台服务
 * @修改历史：2015年8月26日创建初始版本
 **********************************************************/
public class UpdateService extends Service
{
	/**
	 * 服务器固定地址
	 */
	private static final String APK_URL_TITLE = "http://www.qianjing.com/download/android/qj_finacial_";
	private static final String APK_URL_END = ".apk";
	/**
	 * 文件存放路经
	 */
	private String filePath;
	/**
	 * 文件下载地址
	 */
	private String apkUrl;
	private String lastVersion;

	private NotificationManager notificationManager;
	private Notification mNotification;

	@Override
	public void onCreate()
	{
		notificationManager = (NotificationManager) getSystemService(android.content.Context.NOTIFICATION_SERVICE);
		filePath = Environment.getExternalStorageDirectory() + "/QjFund/QjFund.apk";
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId)
	{
		if (intent == null || (intent != null && !intent.hasExtra("lastVersion")))
		{
			notifySchedule(getString(R.string.update_download_failed), getString(R.string.update_download_failed_msg),
					0);
			stopSelf();// 停掉服务自身
			return super.onStartCommand(intent, flags, startId);
		}

		lastVersion = intent.getStringExtra("lastVersion");
		apkUrl = APK_URL_TITLE + lastVersion + APK_URL_END;
		notifySchedule(getString(R.string.update_download_start), getString(R.string.update_download_start), 0);
		startDownload();
		return super.onStartCommand(intent, flags, startId);
	}

	private void startDownload()
	{
		UpdateManager.getInstance().startDownload(apkUrl, filePath, new UpdateDownloadListener()
		{
			@Override
			public void onStarted()
			{
			}

			@Override
			public void onProgressChanged(int progress, String downloadUrl)
			{
				notifySchedule(getString(R.string.update_download_processing),
						getString(R.string.update_download_processing), progress);
			}

			@Override
			public void onPrepared(long contentLength, String downloadUrl)
			{
			}

			@Override
			public void onPaused(int progress, int completeSize, String downloadUrl)
			{
				notifySchedule(getString(R.string.update_download_failed),
						getString(R.string.update_download_failed_msg), 0);
				deleteApkFile();
				stopSelf();// 停掉服务自身
			}

			@Override
			public void onFinished(int completeSize, String downloadUrl)
			{
				notifySchedule(getString(R.string.update_download_finish), getString(R.string.update_download_finish),
						100);
				stopSelf();// 停掉服务自身
				startActivity(getInstallApkIntent());
			}

			@Override
			public void onFailure()
			{
				notifySchedule(getString(R.string.update_download_failed),
						getString(R.string.update_download_failed_msg), 0);
				deleteApkFile();
				stopSelf();// 停掉服务自身
			}
		});
	}

	@Override
	public IBinder onBind(Intent intent)
	{
		return null;
	}

	public void notifySchedule(String tickerMsg, String message, int progress)
	{
		if (Build.VERSION.SDK_INT >= 21)
		{
			notifyThatExceedLv21(tickerMsg, message, progress);
		}
		else
		{
			notifyThatUnder21(tickerMsg, message, progress);
		}
	}

	/**
	 * API21以上使用此方法
	 */
	private void notifyThatUnder21(String tickerMsg, String message, int progress)
	{
		NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
		notification.setSmallIcon(R.drawable.ic_launcher);
		if (progress > 0 && progress < 100)
		{
			RemoteViews contentView = new RemoteViews(getPackageName(), R.layout.update_notification);
			contentView.setTextViewText(R.id.name, getString(R.string.app_name));
			contentView.setProgressBar(R.id.progressbar, 100, progress, false);
			notification.setContent(contentView);
		}
		else
		{
			notification.setContentTitle(getString(R.string.app_name));
			notification.setContentText(message);
		}
		notification.setAutoCancel(true);
		notification.setWhen(System.currentTimeMillis());
		notification.setTicker(tickerMsg);
		notification.setContentIntent(progress >= 100 ? getContentIntent() : PendingIntent.getActivity(this, 0,
				new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
		mNotification = notification.build();
		notificationManager.notify(0, mNotification);
	}

	/**
	 * API21使用此方法
	 */
	private void notifyThatExceedLv21(String tickerMsg, String message, int progress)
	{
		NotificationCompat.Builder notification = new NotificationCompat.Builder(this);
		notification.setSmallIcon(R.drawable.icon_mini_lanucher);
		notification.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher));
		notification.setContentTitle(getString(R.string.app_name));
		if (progress > 0 && progress < 100)
		{
			notification.setProgress(100, progress, false);
		}
		else
		{
			/**
			 * 0,0,false,可以将进度条影藏
			 */
			notification.setProgress(0, 0, false);
			notification.setContentText(message);
		}
		notification.setAutoCancel(true);
		notification.setWhen(System.currentTimeMillis());
		notification.setTicker(tickerMsg);
		notification.setContentIntent(progress >= 100 ? getContentIntent() : PendingIntent.getActivity(this, 0,
				new Intent(), PendingIntent.FLAG_UPDATE_CURRENT));
		mNotification = notification.build();
		notificationManager.notify(0, mNotification);
	}

	private PendingIntent getContentIntent()
	{
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, getInstallApkIntent(),
				PendingIntent.FLAG_UPDATE_CURRENT);
		return contentIntent;
	}

	/**
	 * 下载完成，安装
	 */
	private Intent getInstallApkIntent()
	{
		File apkfile = new File(filePath);
		Intent intent = new Intent(Intent.ACTION_VIEW);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setDataAndType(Uri.parse("file://" + apkfile.toString()), "application/vnd.android.package-archive");
		return intent;
	}

	/**
	 * 删除无用apk文件
	 */
	private boolean deleteApkFile()
	{
		File apkFile = new File(filePath);
		if (apkFile.exists() && apkFile.isFile())
		{
			return apkFile.delete();
		}
		return false;
	}
}