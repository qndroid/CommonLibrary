package com.commonliabray.activity.customview;

import com.example.dragrelativelayout.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

public class OverScrollActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_scrollview);
		findViewById(R.id.text).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(OverScrollActivity.this, "text", Toast.LENGTH_SHORT).show();
			}
		});
	}

	public void click(View v)
	{
		Toast.makeText(this, "click", Toast.LENGTH_SHORT).show();
	}
}
