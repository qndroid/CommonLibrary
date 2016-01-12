package com.commonliabray.camera;

import java.io.File;
import java.io.FileOutputStream;

import com.common.camera.CameraView;
import com.common.camera.CameraView.FlashMode;
import com.example.dragrelativelayout.R;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.hardware.Camera;
import android.hardware.Camera.AutoFocusCallback;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;

public class CameraActivity extends Activity {
	private CameraView mCameraView;
	private ImageView mThumbView;
	private SeekBar mSeekBar;
	private FocusImageView mFocusImageView;
	private String rootPath;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_camera);
		mCameraView = (CameraView) findViewById(R.id.camera_view);
		mThumbView = (ImageView) findViewById(R.id.photo_view);
		mFocusImageView = (FocusImageView) findViewById(R.id.focusImageView);
		mSeekBar = (SeekBar) findViewById(R.id.seek_bar);
		mSeekBar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {
			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
				mCameraView.setZoom((int) ((seekBar.getProgress() * mCameraView.getMaxZoom() / 100)));
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
			}
		});

		rootPath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/mycamera/";

		/**
		 * 可以优化的地方，优化到一个专门用来管理文件的类中
		 */
		File rootFile = new File(rootPath);
		if (!rootFile.exists()) {
			rootFile.mkdir();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.camera, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.take_photo) {
			mCameraView.takePicture(null, null, new PictureCallback() {
				@Override
				public void onPictureTaken(byte[] data, Camera camera) {
					if (data != null) {
						Bitmap bm = BitmapFactory.decodeByteArray(data, 0, data.length);
						mThumbView.setImageBitmap(bm);
						File file = new File(rootPath + System.currentTimeMillis() + ".jpg");
						try {
							/**
							 * 将buffer写入到指定路径
							 */
							FileOutputStream fos = new FileOutputStream(file);
							fos.write(data);
							fos.flush();
							fos.close();
						} catch (Exception e) {
						}
						mCameraView.startPreview();
					}
				}
			});
		}
		if (id == R.id.auto_focus) {
			if (mCameraView.getFlashMode() == FlashMode.ON) {
				mCameraView.setFlashMode(FlashMode.OFF);
			} else if (mCameraView.getFlashMode() == FlashMode.OFF) {
				mCameraView.setFlashMode(FlashMode.AUTO);
			} else if (mCameraView.getFlashMode() == FlashMode.AUTO) {
				mCameraView.setFlashMode(FlashMode.TORCH);
			} else if (mCameraView.getFlashMode() == FlashMode.TORCH) {
				mCameraView.setFlashMode(FlashMode.ON);
			}
		}
		return true;
	}

	private static final int MODE_INIT = 0;
	private static final int MODE_ZOOM = 1;
	private int mode = MODE_INIT;
	private float startDis;

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		switch (event.getAction() & MotionEvent.ACTION_MASK) {
		case MotionEvent.ACTION_DOWN:
			mode = MODE_INIT;
			break;
		case MotionEvent.ACTION_POINTER_DOWN:
			mode = MODE_ZOOM;
			/** ����������ָ��ľ��� */
			startDis = distance(event);
			break;
		case MotionEvent.ACTION_MOVE:
			if (mode == MODE_ZOOM) {
				if (event.getPointerCount() < 2)
					return true;
				float endDis = distance(event);
				int scale = (int) ((endDis - startDis) / 10f);
				if (scale >= 1 || scale <= -1) {
					int zoom = mCameraView.getZoom() + scale;
					if (zoom > mCameraView.getMaxZoom())
						zoom = mCameraView.getMaxZoom();
					if (zoom < 0)
						zoom = 0;
					mCameraView.setZoom(zoom);
					mSeekBar.setProgress(zoom);
					startDis = endDis;
				}
			}
			break;
		case MotionEvent.ACTION_UP:
			if (mode != MODE_ZOOM) {
				Point point = new Point((int) event.getX(), (int) event.getY());
				mCameraView.setFocus(point, auroCallback);
				/**
				 * 显示FocusImageView
				 */
				mFocusImageView.startFocus(point);
			}
			break;
		}
		return true;
	}

	private AutoFocusCallback auroCallback = new AutoFocusCallback() {
		@Override
		public void onAutoFocus(boolean success, Camera camera) {
			if (success) {
				mFocusImageView.onFocusSuccess();
			} else {
				mFocusImageView.onFocusFailed();
			}
		}
	};

	private float distance(MotionEvent event) {
		float dx = event.getX(1) - event.getX(0);
		float dy = event.getY(1) - event.getY(0);
		return (float) Math.sqrt(dx * dx + dy * dy);
	}
}