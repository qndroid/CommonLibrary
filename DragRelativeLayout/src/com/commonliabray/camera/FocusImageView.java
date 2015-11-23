package com.commonliabray.camera;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Point;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.dragrelativelayout.R;

/**********************************************************
 * @文件名称：FocusImageView.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月23日 下午9:37:00
 * @文件描述：
 * @修改历史：2015年11月23日创建初始版本
 **********************************************************/
public class FocusImageView extends ImageView
{
	public final static String TAG = "FocusImageView";
	private static final int NO_ID = -1;
	private int mFocusImg = NO_ID;
	private int mFocusSucceedImg = NO_ID;
	private int mFocusFailedImg = NO_ID;
	private Animation mAnimation;
	private Handler mHandler;

	public FocusImageView(Context context)
	{
		super(context);
		mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.focusview_show);
		setVisibility(View.GONE);
		mHandler = new Handler();
	}

	public FocusImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mAnimation = AnimationUtils.loadAnimation(getContext(), R.anim.focusview_show);
		mHandler = new Handler();

		TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.FocusImageView);
		mFocusImg = a.getResourceId(R.styleable.FocusImageView_focus_focusing_id, NO_ID);
		mFocusSucceedImg = a.getResourceId(R.styleable.FocusImageView_focus_success_id, NO_ID);
		mFocusFailedImg = a.getResourceId(R.styleable.FocusImageView_focus_fail_id, NO_ID);
		a.recycle();

		if (mFocusImg == NO_ID || mFocusSucceedImg == NO_ID || mFocusFailedImg == NO_ID)
			throw new RuntimeException("Animation is null");
	}

	/**  
	 *  ��ʾ�۽�ͼ��
	 *  @param x ������x����
	 *  @param y ������y����
	 */
	public void startFocus(Point point)
	{
		if (mFocusImg == NO_ID || mFocusSucceedImg == NO_ID || mFocusFailedImg == NO_ID)
			throw new RuntimeException("focus image is null");
		RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) getLayoutParams();
		params.topMargin = point.y - getHeight() / 2;
		params.leftMargin = point.x - getWidth() / 2;
		setLayoutParams(params);
		setVisibility(View.VISIBLE);
		setImageResource(mFocusImg);
		startAnimation(mAnimation);
		mHandler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				setVisibility(View.GONE);
			}
		}, 3500);
	}

	/**  
	*   �۽��ɹ��ص�
	*/
	public void onFocusSuccess()
	{
		setImageResource(mFocusSucceedImg);
		mHandler.removeCallbacks(null, null);
		mHandler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				setVisibility(View.GONE);
			}
		}, 1000);

	}

	/**  
	*   �۽�ʧ�ܻص�
	*/
	public void onFocusFailed()
	{
		setImageResource(mFocusFailedImg);
		mHandler.removeCallbacks(null, null);
		mHandler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				// TODO Auto-generated method stub
				setVisibility(View.GONE);
			}
		}, 1000);
	}

	/**  
	 * ���ÿ�ʼ�۽�ʱ��ͼƬ
	 *  @param focus   
	 */
	public void setFocusImg(int focus)
	{
		this.mFocusImg = focus;
	}

	/**  
	 *  ���þ۽��ɹ���ʾ��ͼƬ
	 *  @param focusSucceed   
	 */
	public void setFocusSucceedImg(int focusSucceed)
	{
		this.mFocusSucceedImg = focusSucceed;
	}
}
