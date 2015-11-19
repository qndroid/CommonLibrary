package com.common.widget.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.example.commonhttplibrary.R;

/**********************************************************
 * @文件名称：IndiactorView.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年5月26日 下午3:23:52
 * @文件描述：自定义风险指示view
 * @修改历史：2015年5月26日创建初始版本
 **********************************************************/
public class IndiactorView extends View
{
	/**
	 * 公共部分
	 */
	private final int MAX = 100;
	private final int ROUND_DEGREE = 4;
	private final int SPACE_TO_Triangle = 1;
	private final int[] cursorColors = new int[]
	{ R.color.color_7ea1de, R.color.color_7aa7d6, R.color.color_73adcd, R.color.color_6cb4c4, R.color.color_66bbba,
			R.color.color_63c6a8, R.color.color_63cd99, R.color.color_6bce90, R.color.color_7dce8a,
			R.color.color_96cc84, R.color.color_b2c97d, R.color.color_d3c477, R.color.color_e9be72,
			R.color.color_fab76d, R.color.color_ffae68, R.color.color_ff9c60, R.color.color_f87653,
			R.color.color_ff8a5a, R.color.color_f0674e, R.color.color_e85548 };
	private Context mContext;
	private Resources res;
	private Paint mPaint;
	private Paint mSmallPaint;
	private DisplayMetrics dm;
	/**
	 * resource
	 */
	private Bitmap bitmapProgress;
	private Bitmap bitmapIndictor;

	/**
	 * 进度条宽高，坐标
	 */
	private float bitmapProgressWidth, bitmapProgressHeight;
	private float bitmapProgressX, bitmapProgressY;

	private float bitmapIndictorWidth, bitmapIndictorHeight;
	/**
	 * 手机屏幕宽高
	 */
	private int screenHeight, screenWidth;

	/**
	 * 指示矩形的宽高
	 */
	private int rectWidth;
	private int rectHeight;
	// 游标起点坐标
	private float cursorX;
	/**
	 * 风险指数内容
	 */
	private int cursorPosition = 0;
	private float precent = 0;
	private int offset;
	private String drawText = "0";
	private String houZhuiText = "/100";
	/**
	 * 点击事件回调
	 */
	private IndictorClickListener listener;

	public IndiactorView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		this.mContext = context;
		this.res = getResources();
		initView();
	}

	private void initView()
	{
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mSmallPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		dm = new DisplayMetrics();
		WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
		wm.getDefaultDisplay().getMetrics(dm);

		screenWidth = dm.widthPixels;
		screenHeight = dm.heightPixels;

		rectWidth = dip2px(47);
		rectHeight = dip2px(25);

		bitmapProgress = BitmapFactory.decodeResource(res, R.drawable.icon_indictor);
		bitmapIndictor = BitmapFactory.decodeResource(res, R.drawable.icon_san_jiao);

		bitmapProgressWidth = bitmapProgress.getWidth();
		bitmapProgressHeight = bitmapProgress.getHeight();

		bitmapIndictorWidth = bitmapIndictor.getWidth();
		bitmapIndictorHeight = bitmapIndictor.getHeight();

		bitmapProgressX = (screenWidth - bitmapProgressWidth) / 2;
		bitmapProgressY = rectHeight + bitmapIndictorHeight;
	}

	/**
	 * 更新游标位置
	 * 
	 * @param position
	 */
	public void setPosition(int position)
	{

		cursorPosition = position;
		precent = (position * bitmapProgressWidth) / MAX;
		drawText = new StringBuilder().append(position).toString();
		/**
		 * 还要根据position设置paint颜色
		 */
		offset = position / 5;
		if (offset == 20)
		{
			offset = offset - 1;
		}
		invalidate();
	}

	public void setIndictorClickListener(IndictorClickListener listener)
	{

		this.listener = listener;
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
	{

		int viewHeight = (int) (bitmapIndictorHeight + bitmapProgressHeight + rectHeight);
		setMeasuredDimension((int) screenWidth, viewHeight);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event)
	{
		float pointX = event.getX();
		float pointY = event.getY();
		switch (event.getAction())
		{

		case MotionEvent.ACTION_DOWN:

			if (pointInIndictor(pointX, pointY, cursorX))
			{

				if (listener != null)
				{

					listener.onClick();
				}
			}
			break;
		case MotionEvent.ACTION_MOVE:
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return super.onTouchEvent(event);
	}

	@Override
	protected void onDraw(Canvas canvas)
	{
		mPaint.setColor(res.getColor(cursorColors[offset]));
		/**
		 * 游标当前X坐标
		 */
		cursorX = (cursorPosition > 0) ? (bitmapProgressX + precent - bitmapIndictorWidth / 2)
				: (bitmapProgressX + precent);

		canvas.drawBitmap(bitmapProgress, bitmapProgressX, bitmapProgressY, null);
		/**
		 * 绘制小三角
		 */
		drawTriangle(canvas, cursorX);

		/**
		 * 边界判断，防止超出屏幕
		 */
		if (cursorX - bitmapProgressX + rectWidth > bitmapProgressWidth)
		{
			cursorX = cursorX - rectWidth + bitmapIndictorWidth;
		}
		RectF rect = new RectF(cursorX, 0, cursorX + rectWidth, rectHeight - SPACE_TO_Triangle);
		canvas.drawRoundRect(rect, dip2px(ROUND_DEGREE), dip2px(ROUND_DEGREE), mPaint);

		mPaint.setColor(Color.WHITE);
		mPaint.setTextSize(dip2px(11));
		mPaint.setFakeBoldText(true);
		Rect textRect = new Rect();
		mPaint.getTextBounds(drawText, 0, drawText.length(), textRect);

		mSmallPaint.setColor(Color.WHITE);
		mSmallPaint.setTextSize(dip2px(9));

		float cursorTextX = cursorX
				+ (rectWidth - (mPaint.measureText(drawText) + mSmallPaint.measureText(houZhuiText))) / 2;
		float cursorTextY = (rectHeight + textRect.height()) / 2;
		canvas.drawText(drawText, cursorTextX, cursorTextY, mPaint);
		canvas.drawText(houZhuiText, cursorTextX + textRect.width(), cursorTextY, mSmallPaint);
	}

	/**
	 * 释放资源，竟可能少消耗内存
	 */
	@Override
	protected void onDetachedFromWindow()
	{
		mContext = null;
		res = null;
		mPaint = null;
		dm = null;
		bitmapIndictor = null;
		bitmapProgress = null;
	}

	/**
	 * 绘制小三角形到指定位置
	 */
	private void drawTriangle(Canvas canvas, float orginalX)
	{
		Path path = new Path();
		path.moveTo(orginalX, rectHeight);
		path.lineTo(orginalX + bitmapIndictorWidth, rectHeight);
		path.lineTo(orginalX + bitmapIndictorWidth / 2, rectHeight + bitmapIndictorHeight);
		path.close();
		canvas.drawPath(path, mPaint);
	}

	/**
	 * 判断手指按下点是否在指示器内
	 * 
	 * @param pointX
	 * @param pointY
	 * @param orginalX
	 * @return
	 */
	private boolean pointInIndictor(float pointX, float pointY, float orginalX)
	{

		if (pointX > orginalX && pointX < (orginalX + rectWidth) && pointY > 0 && pointY < rectHeight)
		{
			return true;
		}
		return false;
	}

	/**
	 * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 */
	private int dip2px(float dpValue)
	{
		return (int) (dpValue * dm.density + 0.5f);
	}

	/**
	 * 点击事件回调接口
	 * 
	 * @author renzhiqiang
	 */
	public interface IndictorClickListener
	{
		public void onClick();
	}
}