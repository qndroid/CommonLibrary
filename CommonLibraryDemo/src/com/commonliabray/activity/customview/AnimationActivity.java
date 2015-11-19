package com.commonliabray.activity.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

import com.example.dragrelativelayout.R;

public class AnimationActivity extends Activity
{
	ArrayList<View> viewLists;
	int i = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_animation);

		viewLists = new ArrayList<View>();

		View oneView = findViewById(R.id.one_view);
		View twoView = findViewById(R.id.two_view);
		View threeView = findViewById(R.id.three_view);
		View fourView = findViewById(R.id.four_view);
		View fiveView = findViewById(R.id.five_view);
		View sixView = findViewById(R.id.six_view);

		viewLists.add(oneView);
		viewLists.add(twoView);
		viewLists.add(threeView);
		viewLists.add(fourView);
		viewLists.add(fiveView);
		viewLists.add(sixView);

		animationView(viewLists.get(i));
	}

	private void animationView(View view)
	{
		ObjectAnimator anim = ObjectAnimator.ofFloat(view, "rotationY", 0, 180);
		anim.addListener(new AnimatorListenerAdapter()
		{
			@Override
			public void onAnimationEnd(Animator animation)
			{
				i += 1;
				if (i >= viewLists.size())
				{
					i = 0;
				}
				animationView(viewLists.get(i));
			}
		});
		anim.setDuration(500);
		anim.setStartDelay(1000);
		anim.start();
	}
}