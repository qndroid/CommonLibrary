package com.commonliabray.activity.fragment.anim;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.dragrelativelayout.R;

public class FragmentTranslationActivity extends Activity implements OnClickListener
{
	private LinearLayout mCategoryLayout;
	private LinearLayout mNearLayout;
	private LinearLayout mPublishLayout;
	private LinearLayout mPersionalLayout;
	private TextView mCateiView, mNearView, mPublishView, mPersionalView;

	private FragmentManager fm;
	private Fragment mCategoryFragment;
	private Fragment mNearFragment;
	private Fragment mPublishFragment;
	private Fragment mPersionalCenterFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_fragment_transation);

		mCategoryLayout = (LinearLayout) findViewById(R.id.catagories_view);
		mCategoryLayout.setOnClickListener(this);
		mCateiView = (TextView) findViewById(R.id.category);
		mCateiView.setBackgroundResource(R.drawable.tab_lastcategories_selected);
		mNearLayout = (LinearLayout) findViewById(R.id.near_view);
		mNearLayout.setOnClickListener(this);
		mNearView = (TextView) findViewById(R.id.near);
		mPublishLayout = (LinearLayout) findViewById(R.id.publish_view);
		mPublishLayout.setOnClickListener(this);
		mPublishView = (TextView) findViewById(R.id.publish);
		mPersionalLayout = (LinearLayout) findViewById(R.id.persional_view);
		mPersionalLayout.setOnClickListener(this);
		mPersionalView = (TextView) findViewById(R.id.persional);
		// Add first fragment
		mCategoryFragment = new TranslationFragment(0);

		/**
		 * 不应该在onCreate时全部创建Fragment,否则此Activity会加载的很慢，尤其有请求的时候
		 */
		// mNearFragment = new TranslationFragment(1);
		// mPublishFragment = new TranslationFragment(2);
		// mPersionalCenterFragment = new TranslationFragment(3);
		fm = getFragmentManager();

		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		fragmentTransaction.replace(R.id.fragment_place, mCategoryFragment);
		fragmentTransaction.commit();
	}

	@Override
	public void onClick(View v)
	{
		FragmentTransaction fragmentTransaction = fm.beginTransaction();
		if (Build.VERSION.SDK_INT >= 11)
		{
			fragmentTransaction.setCustomAnimations(R.animator.slide_fragment_horizontal_right_in,
					R.animator.slide_fragment_horizontal_left_out, R.animator.slide_fragment_horizontal_left_in,
					R.animator.slide_fragment_horizontal_right_out);
		}

		switch (v.getId())
		{
		case R.id.catagories_view:
			mCateiView.setBackgroundResource(R.drawable.tab_lastcategories_selected);
			mNearView.setBackgroundResource(R.drawable.tab_near);
			mPublishView.setBackgroundResource(R.drawable.tab_publish);
			mPersionalView.setBackgroundResource(R.drawable.tab_personal_centre_default);
			if (mCategoryFragment == null)
			{
				mCategoryFragment = new TranslationFragment(0);
			}
			fragmentTransaction.replace(R.id.fragment_place, mCategoryFragment);
			break;
		case R.id.near_view:
			mNearView.setBackgroundResource(R.drawable.tab_near_selected);
			mCateiView.setBackgroundResource(R.drawable.tab_lastcategories);
			mPublishView.setBackgroundResource(R.drawable.tab_publish);
			mPersionalView.setBackgroundResource(R.drawable.tab_personal_centre_default);
			if (mNearFragment == null)
			{
				mNearFragment = new TranslationFragment(1);
			}
			fragmentTransaction.replace(R.id.fragment_place, mNearFragment);
			break;
		case R.id.publish_view:
			mPublishView.setBackgroundResource(R.drawable.tab_publish_selected);
			mCateiView.setBackgroundResource(R.drawable.tab_lastcategories);
			mNearView.setBackgroundResource(R.drawable.tab_near);
			mPersionalView.setBackgroundResource(R.drawable.tab_personal_centre_default);
			if (mPublishFragment == null)
			{
				mPublishFragment = new TranslationFragment(2);
			}
			fragmentTransaction.replace(R.id.fragment_place, mPublishFragment);
			break;
		case R.id.persional_view:
			mPersionalView.setBackgroundResource(R.drawable.tab_personal_centre_selected);
			mCateiView.setBackgroundResource(R.drawable.tab_lastcategories);
			mNearView.setBackgroundResource(R.drawable.tab_near);
			mPublishView.setBackgroundResource(R.drawable.tab_publish);
			if (mPersionalCenterFragment == null)
			{
				mPersionalCenterFragment = new TranslationFragment(3);
			}
			fragmentTransaction.replace(R.id.fragment_place, mPersionalCenterFragment);
			break;
		}

		fragmentTransaction.commit();
	}
}