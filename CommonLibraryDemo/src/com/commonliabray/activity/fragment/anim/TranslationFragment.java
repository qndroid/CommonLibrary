package com.commonliabray.activity.fragment.anim;

import java.lang.reflect.Field;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.dragrelativelayout.R;

/**********************************************************
 * @文件名称：SlidingListFragmentLeft.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年10月2日 下午2:56:50
 * @文件描述：完全是普通的Fragment
 * @修改历史：2015年10月2日创建初始版本
 **********************************************************/
public class TranslationFragment extends Fragment
{
	private View mContentView;
	private ImageView mImageView;
	private int index;

	public TranslationFragment(int index)
	{
		this.index = index;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		mContentView = inflater.inflate(R.layout.sliding_fragment_layout_left, container, false);
		mImageView = (ImageView) mContentView.findViewById(R.id.image_view);
		switch (index)
		{
		case 0:
			mImageView.setBackgroundResource(R.drawable.fragment_bg_one);
			break;
		case 1:
			mImageView.setBackgroundResource(R.drawable.fragment_bg_two);
			break;
		case 2:
			mImageView.setBackgroundResource(R.drawable.fragment_bg_three);
			break;
		case 3:
			mImageView.setBackgroundResource(R.drawable.fragment_bg_four);
			break;
		}
		return mContentView;
	}

	@Override
	public void onDetach()
	{
		super.onDetach();
		try
		{
			Field childFragmentManager = Fragment.class.getDeclaredField("mChildFragmentManager");
			childFragmentManager.setAccessible(true);
			childFragmentManager.set(this, null);

		}
		catch (NoSuchFieldException e)
		{
			throw new RuntimeException(e);
		}
		catch (IllegalAccessException e)
		{
			throw new RuntimeException(e);
		}

	}

}