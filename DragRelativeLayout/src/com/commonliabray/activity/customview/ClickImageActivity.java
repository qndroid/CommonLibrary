package com.commonliabray.activity.customview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.common.widget.view.ClickImageView;
import com.common.widget.view.ClickImageView.ClickListener;
import com.example.dragrelativelayout.R;

public class ClickImageActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_click_image);
		ClickImageView view = (ClickImageView) findViewById(R.id.image_view_1);
		view.setClickListener(new ClickListener()
		{
			@Override
			public void onClick()
			{
				Toast.makeText(ClickImageActivity.this, "ImageView be clicked!", Toast.LENGTH_SHORT).show();
			}
		});
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}
}
