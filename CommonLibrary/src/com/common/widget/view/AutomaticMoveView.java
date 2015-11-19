package com.common.widget.view;

import com.example.commonhttplibrary.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

/**********************************************************
 * @文件名称：AutomaticMoveView.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年10月1日 下午7:29:21
 * @文件描述：仿格哇拉循环滑动View
 * @修改历史：2015年10月1日创建初始版本
 **********************************************************/
public class AutomaticMoveView extends View
{
	/**
	 * Ĭ�ϳ���
	 */
	private static final int DIRECTION = 1;
	private static final int SPEED = 5;
	private static final float ALPHA = 0.5f;
	/**
	 * ��������
	 */
	private Context mContext;
	private DisplayMetrics dm;
	private int screenWidth;

	/**
	 * �����ƶ�����
	 */
	private MoveDirection mDirection;

	/**
	 * ѭ���ƶ��ı��� 
	 */
	private Bitmap mBackgroundOne, mBackgroundTwo;
	/**
	 * ���ֲ�͸���Ȱٷֱ�
	 */
	private float mAlpha;

	/**
	 * ѭ���ƶ����ٶ�
	 */
	private float mSpeed;

	/**
	 * �����ֲ�ͼ�ĵĺ�����
	 */
	private float x1, x2;
	private int mBgOneWidth, mBgOneHeight;
	private int mBgTwoWidth;

	/**
	 * 相当于递归调用了handleMessage方法
	 */
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			x1 += mSpeed;
			x2 += mSpeed;
			switch (mDirection)
			{
			case RIGHT:
				if (x1 >= screenWidth)
				{
					x1 = x2 - mBgOneWidth;
				}
				if (x2 >= screenWidth)
				{
					x2 = x1 - mBgTwoWidth;
				}
				break;
			case LEFT:
				if (x1 <= -mBgOneWidth)
				{
					x1 = x2 + mBgTwoWidth;
				}
				if (x2 <= -mBgTwoWidth)
				{
					x2 = x1 + mBgOneWidth;
				}
				break;
			}

			invalidate();
			mHandler.sendEmptyMessageDelayed(0, 50);
		};
	};

	public AutomaticMoveView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;

		TypedArray a = mContext.obtainStyledAttributes(attrs, R.styleable.AutomicMove);
		Drawable drawableOne = a.getDrawable(R.styleable.AutomicMove_auto_background_one);
		Drawable drawableTwo = a.getDrawable(R.styleable.AutomicMove_auto_background_two);
		if (drawableOne instanceof BitmapDrawable && drawableTwo instanceof BitmapDrawable)
		{
			mBackgroundOne = ((BitmapDrawable) drawableOne).getBitmap();
			mBackgroundTwo = ((BitmapDrawable) drawableTwo).getBitmap();
		}
		mDirection = a.getInt(R.styleable.AutomicMove_auto_direction, DIRECTION) == DIRECTION ? MoveDirection.LEFT
				: MoveDirection.RIGHT;
		mSpeed = a.getDimension(R.styleable.AutomicMove_auto_speed, SPEED);
		mAlpha = a.getFloat(R.styleable.AutomicMove_auto_alpha, ALPHA);
		a.recycle();

		init();
	}

	private void init()
	{
		dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);
		screenWidth = dm.widthPixels;
		mBgOneWidth = mBackgroundOne.getWidth();
		mBgOneHeight = mBackgroundOne.getHeight();
		mBgTwoWidth = mBackgroundTwo.getWidth();

		switch (mDirection)
		{
		case RIGHT:
			x1 = screenWidth - mBgOneWidth;
			x2 = screenWidth - mBgOneWidth - mBgTwoWidth;
			break;
		case LEFT:
			mSpeed *= -1;
			x1 = 0;
			x2 = mBgOneWidth;
			break;
		}
		mHandler.sendEmptyMessageDelayed(0, 0);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		/**
		 * ָ��View�Ŀ��Ϊ��Ļ��ȣ��߶�Ϊ�����߶ȡ�
		 */
		int viewHeight = mBgOneHeight + getPaddingTop() + getPaddingBottom();
		setMeasuredDimension(screenWidth, viewHeight);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		/**
		 * �������ű���ͼ
		 */
		canvas.drawBitmap(mBackgroundOne, x1, 0, null);
		canvas.drawBitmap(mBackgroundTwo, x2, 0, null);
		/**
		 * �������ֲ�
		 */
		canvas.drawARGB((int) (mAlpha * 255), 0, 0, 0);
	}

	@Override
	protected void onDetachedFromWindow()
	{
		super.onDetachedFromWindow();
		mBackgroundOne = null;
		mBackgroundTwo = null;
	}

	/**
	 * �����ƶ�����ö�� 
	 */
	public enum MoveDirection
	{
		LEFT, RIGHT;
	}
}