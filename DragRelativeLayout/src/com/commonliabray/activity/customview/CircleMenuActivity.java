package com.commonliabray.activity.customview;

import com.common.widget.view.CircleMenuLayout;
import com.common.widget.view.CircleMenuLayout.OnMenuItemClickListener;
import com.example.dragrelativelayout.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

/**
 * <pre>
 * @author zhy 
 * http://blog.csdn.net/lmj623565791/article/details/43131133
 * </pre>
 */
public class CircleMenuActivity extends Activity
{

	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[]
	{ "安全中心 ", "特色服务", "投资理财", "转账汇款", "我的账户", "信用卡" };
	private int[] mItemImgs = new int[]
	{ R.drawable.hand_house_drawable, R.drawable.near_2nd_hand_wupin_default, R.drawable.near_fangchan_default,
			R.drawable.near_friends_default, R.drawable.near_fulltime_jobs_default, R.drawable.near_life_serv_default,
			R.drawable.near_parttime_jobs_default };

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// 自已切换布局文件看效果
		setContentView(R.layout.activity_circle_layout);
		// setContentView(R.layout.activity_main);

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);

		mCircleMenuLayout.setOnMenuItemClickListener(new OnMenuItemClickListener()
		{

			@Override
			public void itemClick(View view, int pos)
			{
				Toast.makeText(CircleMenuActivity.this, mItemTexts[pos], Toast.LENGTH_SHORT).show();

			}

			@Override
			public void itemCenterClick(View view)
			{
				Toast.makeText(CircleMenuActivity.this, "you can do something just like ccb  ", Toast.LENGTH_SHORT)
						.show();

			}
		});

	}

}
