package com.commonliabray.activity.customview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.common.widget.view.SecretTextView;
import com.example.dragrelativelayout.R;

public class SecretTextViewActivity extends Activity
{

	SecretTextView secretTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_secrettextview);
		secretTextView = (SecretTextView) findViewById(R.id.textview);
		secretTextView.setmDuration(3000);
		secretTextView.setIsVisible(true);
		secretTextView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{
				secretTextView.toggle();
			}
		});
	}
}