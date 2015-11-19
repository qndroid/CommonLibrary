package com.commonliabray.activity.customview;

import com.common.widget.view.DownloadProgressBar;
import com.example.dragrelativelayout.R;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class DownloadaAnimatiorActivity extends Activity
{

	private int val = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_download_layout);
		final DownloadProgressBar downloadProgressView = (DownloadProgressBar) findViewById(R.id.dpv3);
		final TextView successTextView = (TextView) findViewById(R.id.success_text_view);
		successTextView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				val = val + 10;
				downloadProgressView.setProgress(val);
			}
		});

		/**
		 * ΪTextViewָ������
		 */
		Typeface robotoFont = Typeface.createFromAsset(getAssets(), "fonts/Roboto-Light.ttf");
		successTextView.setTypeface(robotoFont);

		downloadProgressView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				downloadProgressView.playManualProgressAnimation();
			}
		});
		downloadProgressView.setOnProgressUpdateListener(new DownloadProgressBar.OnProgressUpdateListener()
		{
			@Override
			public void onProgressUpdate(float currentPlayTime)
			{

			}

			@Override
			public void onAnimationStarted()
			{
				downloadProgressView.setEnabled(false);
			}

			@Override
			public void onAnimationEnded()
			{
				val = 0;
				successTextView.setText("点击下载");
				downloadProgressView.setEnabled(true);
			}

			@Override
			public void onAnimationSuccess()
			{
				successTextView.setText("下载完成!");
			}

			@Override
			public void onAnimationError()
			{
				successTextView.setText("Aborted!");
			}

			@Override
			public void onManualProgressStarted()
			{

			}

			@Override
			public void onManualProgressEnded()
			{

			}
		});
	}
}
