package com.common.widget.view;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;

/**********************************************************
 * @文件名称：ClickImageView.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年1月26日 下午11:07:18
 * @文件描述：仿携程app首页按钮点击缩放效果
 * @修改历史：2015年1月26日创建初始版本
 **********************************************************/
public class ClickImageView extends ImageView
{

	private Animator anim1;
	private Animator anim2;
	private int mHeight;
	private int mWidth;
	private float mX, mY;
	private Handler mHandler = new Handler();

	private ClickListener listener;

	public ClickImageView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		init();
	}

	@Override
	protected void onSizeChanged(int w, int h, int oldw, int oldh)
	{
		super.onSizeChanged(w, h, oldw, oldh);
		mHeight = getHeight() - getPaddingTop() - getPaddingBottom();
		mWidth = getWidth() - getPaddingLeft() - getPaddingRight();
		mX = getX();
		mY = getY();
	}

	private void init()
	{

		PropertyValuesHolder valueHolder_1 = PropertyValuesHolder.ofFloat("scaleX", 1f, 0.9f);
		PropertyValuesHolder valuesHolder_2 = PropertyValuesHolder.ofFloat("scaleY", 1f, 0.9f);
		anim1 = ObjectAnimator.ofPropertyValuesHolder(this, valueHolder_1, valuesHolder_2);
		anim1.setDuration(200);
		anim1.setInterpolator(new LinearInterpolator());

		PropertyValuesHolder valueHolder_3 = PropertyValuesHolder.ofFloat("scaleX", 0.9f, 1f);
		PropertyValuesHolder valuesHolder_4 = PropertyValuesHolder.ofFloat("scaleY", 0.9f, 1f);
		anim2 = ObjectAnimator.ofPropertyValuesHolder(this, valueHolder_3, valuesHolder_4);
		anim2.setDuration(200);
		anim2.setInterpolator(new LinearInterpolator());
	}

	public void setClickListener(ClickListener clickListener)
	{
		this.listener = clickListener;
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		switch (event.getAction())
		{
		case MotionEvent.ACTION_DOWN:
			mHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					anim2.end();
					anim1.start();
				}
			});
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
			mHandler.post(new Runnable()
			{
				@Override
				public void run()
				{
					anim1.end();
					anim2.start();
				}
			});
			if (listener != null)
			{

				listener.onClick();
			}
			break;
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return true;
	}

	protected boolean innerImageView(float x, float y)
	{

		if (x >= mX && y <= mX + mWidth)
		{
			if (y >= mY && y <= mY + mHeight)
			{
				return true;
			}
		}
		return false;
	}

	@Override
	protected void onDetachedFromWindow()
	{
		// TODO Auto-generated method stub
		super.onDetachedFromWindow();
	}

	/**********************************************************
	 * @文件名称：ClickListener.java
	 * @文件作者：renzhiqiang
	 * @创建时间：2015年1月26日 下午11:07:18
	 * @文件描述：点击事件回调接回
	 * @修改历史：2015年1月26日创建初始版本
	 **********************************************************/
	public interface ClickListener
	{
		public void onClick();
	}
}
