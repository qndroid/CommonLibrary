package com.common.widget.view;

import com.example.commonhttplibrary.R;

import android.animation.Animator;
import android.animation.Animator.AnimatorListener;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;

/**
 * @description ��58����View
 * @author majinxin
 * @date 2015��10��10��
 */
public class LoadingView extends View
{
	/**
	 * ��������
	 */
	private Context mContext;
	private Resources mResource;
	private Paint mPaint;
	private Paint mOvalPaint;
	/**
	 */
	private int mRadius;
	private int mDistance;
	private int mOvalTop;
	private int mOvalHeight;
	private int mOvalWidth;

	/**
	 * ������״������λ��
	 */
	private int mCenterX, mCenterY;
	private int currentCenterY;
	private Animator mRotationAnim;
	private ValueAnimator mLineAnimDown;
	private ValueAnimator mLineAnimUp;
	private Shape shape;

	public LoadingView(Context context)
	{
		this(context, null);
	}

	public LoadingView(Context context, AttributeSet attrs)
	{
		super(context);
		this.mContext = context;
		this.mResource = context.getResources();
		init();
	}

	private void init()
	{
		mRadius = 10;
		mDistance = 50;
		mOvalHeight = 3;
		mOvalWidth = 10;
		mOvalTop = 25;

		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setStyle(Style.FILL);
		mPaint.setColor(mResource.getColor(R.color.rect));

		mOvalPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mOvalPaint.setStyle(Style.FILL);
		mOvalPaint.setColor(mResource.getColor(R.color.color_666666));

		setupAnimations();
		shape = Shape.RECT;

	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{
		/**
		 * �����Ǿ��εĿ�,�߶�����Ǿ��εĸ߼���distance
		 */
		int width = (mRadius * 2);
		int height = (mDistance + mRadius * 2 + mOvalTop + mOvalHeight);
		setMeasuredDimension(width, height);
		currentCenterY = mCenterX = mCenterY = mRadius;
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		switch (shape)
		{
		case RECT:
			drawRect(canvas);
			break;
		case TRAIL:
			drawTrail(canvas);
			break;
		case CIRCLE:
			drawCircle(canvas);
			break;
		}

		drawOval(canvas);
	}

	/**
	 * ������ĵ�ĸı䣬���ƾ���
	 */
	private void drawRect(Canvas canvas)
	{
		canvas.drawRect(mCenterX - mRadius, currentCenterY - mRadius, mCenterX + mRadius, currentCenterY + mRadius,
				mPaint);
	}

	private void drawTrail(Canvas canvas)
	{
		Path path = new Path();
		int leftX = 0;
		int leftY = currentCenterY + mRadius;
		int middleX = mCenterX;
		int middleY = currentCenterY - mRadius;
		int rightX = mCenterX + mRadius;
		int rightY = currentCenterY + mRadius;
		path.moveTo(leftX, leftY);
		path.lineTo(middleX, middleY);
		path.lineTo(rightX, rightY);
		path.close();
		canvas.drawPath(path, mPaint);
	}

	private void drawCircle(Canvas canvas)
	{
		canvas.drawCircle(mCenterX, currentCenterY, mRadius, mPaint);
	}

	private void drawOval(Canvas canvas)
	{
		float factory = ((mDistance + mRadius) - currentCenterY) / (float) mDistance;
		RectF rectF = new RectF(mCenterX - mOvalWidth * factory, mDistance + mRadius * 2 + mOvalTop, mCenterX
				+ mOvalWidth * factory, mDistance + mRadius * 2 + mOvalTop + mOvalHeight);
		canvas.drawOval(rectF, mOvalPaint);
	}

	private void setupAnimations()
	{
		mRotationAnim = ValueAnimator.ofInt(0, 180);

		mLineAnimDown = ValueAnimator.ofInt(mCenterY + mRadius, mDistance);
		mLineAnimDown.setInterpolator(new AccelerateInterpolator(1.2f));
		mLineAnimDown.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator)
			{
				currentCenterY = (Integer) valueAnimator.getAnimatedValue();
				invalidate();
			}
		});

		mLineAnimUp = ValueAnimator.ofInt(mDistance, mCenterY + mRadius);
		mLineAnimUp.setInterpolator(new DecelerateInterpolator(1.2f));
		mLineAnimUp.addUpdateListener(new ValueAnimator.AnimatorUpdateListener()
		{
			@Override
			public void onAnimationUpdate(ValueAnimator valueAnimator)
			{
				currentCenterY = (Integer) valueAnimator.getAnimatedValue();
				invalidate();
			}
		});

		mLineAnimDown.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				if (shape == Shape.RECT)
				{
					mPaint.setColor(mResource.getColor(R.color.triangle));
					shape = Shape.TRAIL;
				}
				else
				{
					if (shape == Shape.TRAIL)
					{

						mPaint.setColor(mResource.getColor(R.color.circle));
						shape = Shape.CIRCLE;
					}
					else
					{
						mPaint.setColor(mResource.getColor(R.color.rect));
						shape = Shape.RECT;
					}
				}
			}
		});

		final AnimatorSet set = new AnimatorSet();
		set.addListener(new AnimatorListener()
		{
			@Override
			public void onAnimationStart(Animator animation)
			{
			}

			@Override
			public void onAnimationRepeat(Animator animation)
			{
			}

			@Override
			public void onAnimationEnd(Animator animation)
			{
				set.start();
			}

			@Override
			public void onAnimationCancel(Animator animation)
			{
			}
		});
		set.playSequentially(mLineAnimDown, mLineAnimUp);
		set.setDuration(300);
		set.setStartDelay(100);
		set.start();
	}

	private enum Shape
	{
		RECT, TRAIL, CIRCLE;
	}
}