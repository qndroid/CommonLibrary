package com.commonliabray.activity.customview;

import com.example.dragrelativelayout.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class AutomicMoveActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auto_move);
	}

	public void click(View view)
	{
		Log.e("----------->", "click");
	}
}
