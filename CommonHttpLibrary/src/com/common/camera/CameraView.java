package com.common.camera;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.ImageFormat;
import android.graphics.Point;
import android.graphics.Rect;
import android.hardware.Camera;
import android.hardware.Camera.Area;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.hardware.Camera.ShutterCallback;
import android.hardware.Camera.Size;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**********************************************************
 * @文件名称：CameraView.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年11月22日 下午1:02:02
 * @文件描述：包倉了相机的SurfaceView
 * @修改历史：2015年11月22日创建初始版本
 **********************************************************/
public class CameraView extends SurfaceView implements SurfaceHolder.Callback2
{
	private Camera mCamera;
	private Camera.Parameters mParams;

	/**
	 * UI
	 */
	private SurfaceHolder mHolder;
	private Context mContext;

	/**
	 * data
	 */
	private int mOrientation;
	private FlashMode mFlashMode = FlashMode.AUTO;
	private int mZoom;

	public CameraView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
		mContext = context;
		mHolder = getHolder();
		mHolder.addCallback(this);
		mHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	/**
	 * 创建Camera
	 */
	@SuppressWarnings("deprecation")
	@Override
	public void surfaceCreated(SurfaceHolder holder)
	{
		mCamera = openCamera();
		if (mCamera != null)
		{
			try
			{
				setCameraParams();
				mCamera.setPreviewDisplay(holder);
				mCamera.startPreview();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width, int height)
	{
	}

	/**
	 * 销毁Camera
	 */
	@Override
	public void surfaceDestroyed(SurfaceHolder holder)
	{
		mCamera.stopPreview();
		mCamera.release();
		mCamera = null;
	}

	@Override
	public void surfaceRedrawNeeded(SurfaceHolder holder)
	{
	}

	/**
	 * 	check if the device has a Camera
	 */
	private boolean checkCameraHardware()
	{
		if (mContext.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA))
		{
			return true;
		}
		else
		{
			return false;
		}
	}

	public void startPreview()
	{
		mCamera.startPreview();
	}

	@SuppressWarnings("deprecation")
	private Camera openCamera()
	{
		Camera c = null;
		if (checkCameraHardware())
		{
			c = Camera.open();
		}
		return c;
	}

	@SuppressWarnings("deprecation")
	private void setCameraParams()
	{
		Camera.Parameters parameters = mCamera.getParameters();
		List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();
		if (sizeList.size() > 0)
		{
			Size cameraSize = sizeList.get(0);
			parameters.setPreviewSize(cameraSize.width, cameraSize.height);
		}

		sizeList = parameters.getSupportedPictureSizes();
		if (sizeList.size() > 0)
		{
			Size cameraSize = sizeList.get(0);
			for (Size size : sizeList)
			{
				if (size.width * size.height < 100 * 10000)
				{
					cameraSize = size;
					break;
				}
			}
			parameters.setPictureSize(cameraSize.width, cameraSize.height);
		}
		parameters.setPictureFormat(ImageFormat.JPEG);
		parameters.setJpegQuality(100);
		parameters.setJpegThumbnailQuality(100);
		parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
		parameters.setRotation(90); //设置图片保存角度，90为与相机一致。
		setFlashMode(mFlashMode);
		mCamera.setDisplayOrientation(90); // 设置Camera的旋转角度
		mCamera.setParameters(parameters);

	}

	/**
	 * 拍照,具体的实现由调用者实现
	 */
	@SuppressWarnings("deprecation")
	public void takePicture(ShutterCallback shutterCallback, PictureCallback raw, PictureCallback jpeg)
	{
		mCamera.takePicture(shutterCallback, raw, jpeg);
	}

	public void setFlashMode(FlashMode flashMode)
	{
		if (mCamera == null)
		{
			return;
		}
		mFlashMode = flashMode;
		Camera.Parameters params = mCamera.getParameters();
		switch (flashMode)
		{
		case ON:
			params.setFlashMode(Camera.Parameters.FLASH_MODE_ON);
			break;
		case OFF:
			params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			break;
		case AUTO:
			params.setFlashMode(Camera.Parameters.FLASH_MODE_AUTO);
			break;
		case TORCH:
			params.setFlashMode(Camera.Parameters.FLASH_MODE_TORCH);
			break;
		default:
			params.setFlashMode(Camera.Parameters.FLASH_MODE_OFF);
			break;
		}
		mCamera.setParameters(params);
	}

	public FlashMode getFlashMode()
	{
		return mFlashMode;
	}

	public int getZoom()
	{
		return mZoom;
	}

	public int getMaxZoom()
	{
		if (mCamera == null)
			return -1;
		Camera.Parameters parameters = mCamera.getParameters();
		if (!parameters.isZoomSupported())
			return -1;
		return parameters.getMaxZoom() > 40 ? 40 : parameters.getMaxZoom();
	}

	public void setZoom(int zoom)
	{
		if (mCamera == null)
		{
			return;
		}
		Camera.Parameters params = mCamera.getParameters();
		if (!params.isZoomSupported())
		{
			return;
		}
		params.setZoom(zoom);
		mCamera.setParameters(params);
		mZoom = zoom;
	}

	public void setFocus(Point point, AutoFocusCallback callback)
	{
		Camera.Parameters parameters = mCamera.getParameters();
		if (parameters.getMaxNumFocusAreas() > 0)
		{
			List<Area> areas = new ArrayList<Camera.Area>();
			int left = point.x - 300;
			int top = point.y - 300;
			int right = point.x + 300;
			int bottom = point.y + 300;
			left = left < -1000 ? -1000 : left;
			top = top < -1000 ? -1000 : top;
			right = right > 1000 ? 1000 : right;
			bottom = bottom > 1000 ? 1000 : bottom;
			areas.add(new Area(new Rect(left, top, right, bottom), 100));
			parameters.setFocusAreas(areas);
			try
			{
				mCamera.setParameters(parameters);
			}
			catch (Exception e)
			{
			}
		}
		mCamera.autoFocus(callback);
	}

	/** 
	 * @Description: flash mode
	 */
	public enum FlashMode
	{
		ON, OFF, AUTO, TORCH
	}
}