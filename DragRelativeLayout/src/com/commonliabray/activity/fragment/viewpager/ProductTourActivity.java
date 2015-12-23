package com.commonliabray.activity.fragment.viewpager;

import com.example.dragrelativelayout.R;
import com.nineoldandroids.view.ViewHelper;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

public class ProductTourActivity extends FragmentActivity {

	static final int NUM_PAGES = 5;

	ViewPager pager;
	PagerAdapter pagerAdapter;
	LinearLayout circles;
	boolean isOpaque = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		/**
		 * 设置状态栏为透明,则底部的内容可以显示出来。因为PhoneWindow的Layout为FrameLayout.所以会显示底层的内容
		 */
		Window window = getWindow();
		window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
				WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.activity_tutorial);

		pager = (ViewPager) findViewById(R.id.pager);
		pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
		pager.setAdapter(pagerAdapter);
		/**
		 * 为viewpager的滑动添加自定义的动画效果
		 */
		pager.setPageTransformer(true, new CrossfadePageTransformer());
		pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

				if (position == NUM_PAGES - 2 && positionOffset > 0) {
					if (isOpaque) {
						pager.setBackgroundColor(Color.TRANSPARENT);
						isOpaque = false;
					}
				} else {
					if (!isOpaque) {
						pager.setBackgroundColor(getResources().getColor(R.color.primary_material_light));
						isOpaque = true;
					}
				}
			}

			@Override
			public void onPageSelected(int position) {
				if (position == NUM_PAGES - 2) {
				} else if (position < NUM_PAGES - 2) {
				} else if (position == NUM_PAGES - 1) {
					endTutorial();
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});

	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (pager != null) {
			pager.clearOnPageChangeListeners();
		}
	}

	private void endTutorial() {
		finish();
	}

	@Override
	public void onBackPressed() {
		if (pager.getCurrentItem() == 0) {
			super.onBackPressed();
		} else {
			pager.setCurrentItem(pager.getCurrentItem() - 1);
		}
	}

	private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

		public ScreenSlidePagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			ProductTourFragment tp = null;
			switch (position) {
			case 0:
				tp = ProductTourFragment.newInstance(R.layout.welcome_fragment1);
				break;
			case 1:
				tp = ProductTourFragment.newInstance(R.layout.welcome_fragment2);
				break;
			case 2:
				tp = ProductTourFragment.newInstance(R.layout.welcome_fragment3);
				break;
			case 3:
				tp = ProductTourFragment.newInstance(R.layout.welcome_fragment4);
				break;
			case 4:
				tp = ProductTourFragment.newInstance(R.layout.welcome_fragment5);
				break;
			}

			return tp;
		}

		@Override
		public int getCount() {
			return NUM_PAGES;
		}
	}

	/**
	 * @description 自定义PageTransformer实现自定义动画
	 * @author majinxin
	 * @date 2015年10月15日
	 */
	public class CrossfadePageTransformer implements ViewPager.PageTransformer {
		@Override
		public void transformPage(View page, float position) {
			int pageWidth = page.getWidth();

			View backgroundView = page.findViewById(R.id.welcome_fragment);
			View text_head = page.findViewById(R.id.heading);
			View text_content = page.findViewById(R.id.content);
			View object1 = page.findViewById(R.id.a000);
			View object2 = page.findViewById(R.id.a001);
			View object3 = page.findViewById(R.id.a002);
			View object4 = page.findViewById(R.id.a003);
			View object5 = page.findViewById(R.id.a004);
			View object6 = page.findViewById(R.id.a005);
			View object7 = page.findViewById(R.id.a006);
			View object8 = page.findViewById(R.id.a008);
			View object9 = page.findViewById(R.id.a010);
			View object10 = page.findViewById(R.id.a011);
			View object11 = page.findViewById(R.id.a007);
			View object12 = page.findViewById(R.id.a012);
			View object13 = page.findViewById(R.id.a013);

			if (0 <= position && position < 1) {
				/**
				 * [1 , 0]右侧page处理,抵消page本身的滑动动画
				 */
				ViewHelper.setTranslationX(page, pageWidth * (-position));
			}

			if (-1 < position && position < 0) {
				/**
				 * [-1 , 0]左侧page处理,抵消page本身的滑动动画
				 */
				ViewHelper.setTranslationX(page, pageWidth * -position);
			}
			/*************************************************************************************************************************/

			if (position <= -1.0f || position >= 1.0f) {
				/**
				 * (-& ~ -1),(1 ~ +&)不可见部分不作处理
				 */
			} else if (position == 0.0f) {
			} else {
				/**
				 * 针对具体的View,移动产生视差
				 */
				if (backgroundView != null) {
					ViewHelper.setAlpha(backgroundView, 1.0f - Math.abs(position));
				}

				if (text_head != null) {
					ViewHelper.setTranslationX(text_head, pageWidth * position);
					ViewHelper.setAlpha(text_head, 1.0f - Math.abs(position));
				}

				if (text_content != null) {
					ViewHelper.setTranslationX(text_content, pageWidth * position);
					ViewHelper.setAlpha(text_content, 1.0f - Math.abs(position));
				}

				if (object1 != null) {
					ViewHelper.setTranslationX(object1, pageWidth * position);
				}

				// parallax effect
				if (object2 != null) {
					ViewHelper.setTranslationX(object2, pageWidth * position);
				}

				if (object4 != null) {
					ViewHelper.setTranslationX(object4, pageWidth / 2 * position);
				}
				if (object5 != null) {
					ViewHelper.setTranslationX(object5, pageWidth / 2 * position);
				}
				if (object6 != null) {
					ViewHelper.setTranslationX(object6, pageWidth / 2 * position);
				}
				if (object7 != null) {
					ViewHelper.setTranslationX(object7, pageWidth / 2 * position);
				}

				if (object8 != null) {
					ViewHelper.setTranslationX(object8, (float) (pageWidth / 1.5 * position));
				}

				if (object9 != null) {
					ViewHelper.setTranslationX(object9, (float) (pageWidth / 2 * position));
				}

				if (object10 != null) {
					ViewHelper.setTranslationX(object10, pageWidth / 2 * position);
				}

				if (object11 != null) {
					ViewHelper.setTranslationX(object11, (float) (pageWidth / 1.2 * position));
				}

				if (object12 != null) {
					ViewHelper.setTranslationX(object12, (float) (pageWidth / 1.3 * position));
				}

				if (object13 != null) {
					ViewHelper.setTranslationX(object13, (float) (pageWidth / 1.8 * position));
				}

				if (object3 != null) {
					ViewHelper.setTranslationX(object3, (float) (pageWidth / 1.2 * position));
				}
			}
		}
	}
}