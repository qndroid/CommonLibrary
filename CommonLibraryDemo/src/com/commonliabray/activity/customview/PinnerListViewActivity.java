package com.commonliabray.activity.customview;

import com.commonliabray.model.SortModel;
import com.commonliabray.util.CharacterParser;
import com.commonliabray.util.PinyinComparator;
import com.commonliabray.widget.SortAdapter;
import com.example.dragrelativelayout.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PinnerListViewActivity extends Activity
{
	private ListView sortListView;
	private SortAdapter adapter;

	private CharacterParser characterParser;
	private List<SortModel> SourceDateList;

	private PinyinComparator pinyinComparator;

	private LinearLayout titleLayout;
	private TextView title;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pinner_layout);
		initViews();
	}

	private void initViews()
	{
		titleLayout = (LinearLayout) findViewById(R.id.title_layout);
		title = (TextView) findViewById(R.id.title);
		characterParser = CharacterParser.getInstance();
		pinyinComparator = new PinyinComparator();

		sortListView = (ListView) findViewById(R.id.country_lvcountry);

		SourceDateList = filledData(getResources().getStringArray(R.array.date));

		Collections.sort(SourceDateList, pinyinComparator);
		adapter = new SortAdapter(this, SourceDateList);
		sortListView.setAdapter(adapter);

		sortListView.setOnScrollListener(new OnScrollListener()
		{
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState)
			{
			}

			/**
			 * AbsListView滑动时执行 
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount)
			{
				if (firstVisibleItem >= sortListView.getHeaderViewsCount())
				{
					titleLayout.setVisibility(View.VISIBLE);
					int realItem = sortListView.getHeaderViewsCount();

					/**
					 * 由此列可知,Adapter的开始项只是他ArrayList的第零项，ListView的开始是其头部
					 */
					SortModel modelFirst = (SortModel) adapter.getItem(firstVisibleItem - realItem);
					SortModel modelSecond = (SortModel) adapter.getItem(firstVisibleItem - realItem + 1);
					if (firstVisibleItem != lastFirstVisibleItem)
					{
						MarginLayoutParams params = (MarginLayoutParams) titleLayout.getLayoutParams();
						params.topMargin = 0;
						titleLayout.setLayoutParams(params);
						if (modelFirst.getSortLetters() != null)
						{
							title.setText(modelFirst.getSortLetters());
						}
					}

					if (modelFirst.isGroup() == false && modelSecond.isGroup() == true)
					{
						View childView = view.getChildAt(1);
						if (childView != null)
						{
							int titleHeight = titleLayout.getHeight();

							int top = childView.getTop();
							MarginLayoutParams params = (MarginLayoutParams) titleLayout.getLayoutParams();
							if (top <= titleHeight)
							{
								float pushedDistance = -(titleHeight - top);
								params.topMargin = (int) pushedDistance;
								titleLayout.setLayoutParams(params);
							}
						}
					}
					lastFirstVisibleItem = firstVisibleItem;
				}
				else
				{
					titleLayout.setVisibility(View.GONE);
				}
			}
		});
	}

	private int lastFirstVisibleItem = -1;

	private List<SortModel> filledData(String[] date)
	{
		List<SortModel> mSortList = new ArrayList<SortModel>();

		for (int i = 0; i < date.length; i++)
		{
			SortModel sortModel = new SortModel();
			sortModel.setName(date[i]);
			if (date[i].matches("[A-Z]"))
			{
				sortModel.setSortLetters(date[i]);
				sortModel.setGroup(true);
			}
			else
			{
				String pinyin = characterParser.getSelling(date[i]);
				String sortString = pinyin.substring(0, 1).toUpperCase();

				if (sortString.matches("[A-Z]"))
				{
					sortModel.setSortLetters(sortString.toUpperCase());
				}
				else
				{
					sortModel.setSortLetters("#");
				}

				sortModel.setGroup(false);
			}

			mSortList.add(sortModel);
		}

		return mSortList;
	}
}