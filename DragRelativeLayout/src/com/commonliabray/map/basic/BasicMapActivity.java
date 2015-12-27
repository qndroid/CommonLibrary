package com.commonliabray.map.basic;

import android.app.Activity;
import android.os.Bundle;

import com.amap.api.maps.AMap;
import com.amap.api.maps.MapView;
import com.example.dragrelativelayout.R;

/**********************************************************
 * @文件名称：BasicMapActivity.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年12月25日 下午11:22:24
 * @文件描述：创建一个基本的2d地图
 * @修改历史：2015年12月25日创建初始版本
 **********************************************************/
public class BasicMapActivity extends Activity
{
	/**
	 * 地图相关
	 */
	private MapView mMapView;
	private AMap mAmap;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_basic_map);
		initView(savedInstanceState);
	}

	private void initView(Bundle savedInstanceState)
	{
		mMapView = (MapView) findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);
		if (mAmap == null)
		{
			mAmap = mMapView.getMap();
		}
	}

	@Override
	protected void onResume()
	{
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause()
	{
		super.onPause();
		mMapView.onPause();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
		mMapView.onDestroy();
	}
}