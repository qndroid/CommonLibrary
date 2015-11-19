package com.commonliabray.activity.customview;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.Toast;

import com.common.widget.view.DragLayout;
import com.common.widget.view.DragLayout.ScrollBottomListener;
import com.common.widget.view.DragLayout.ScrollTopListener;
import com.example.dragrelativelayout.R;

public class DragLayoutActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_drag_layout);

		findViewById(R.id.btn).setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				Toast.makeText(DragLayoutActivity.this, "onClick", Toast.LENGTH_SHORT).show();
			}
		});

		final ImageView image = (ImageView) findViewById(R.id.image);
		DragLayout layout = (DragLayout) findViewById(R.id.drag_layout);
		layout.setmTopListener(new ScrollTopListener()
		{
			@Override
			public void onScrollTop()
			{
				image.setBackgroundResource(R.drawable.icon_arrow_bottom);
			}
		});

		layout.setmBottomListener(new ScrollBottomListener()
		{
			@Override
			public void onScrollBottom()
			{
				image.setBackgroundResource(R.drawable.icon_arrow);
			}
		});

	}

}
