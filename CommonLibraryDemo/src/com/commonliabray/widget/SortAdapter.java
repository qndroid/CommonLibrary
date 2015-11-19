package com.commonliabray.widget;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.commonliabray.model.SortModel;
import com.example.dragrelativelayout.R;

/** 
* @description TODO���������������ã�
* @author majinxin
* @date 2015��9��19��
*/
public class SortAdapter extends BaseAdapter
{
	private List<SortModel> list = null;
	private Context mContext;
	private SortModel country;
	private ViewHolder holder1, holder2;

	public SortAdapter(Context mContext, List<SortModel> list)
	{
		this.mContext = mContext;
		this.list = list;
	}

	@Override
	public int getCount()
	{
		return list.size();
	}

	@Override
	public Object getItem(int position)
	{
		return list.get(position);
	}

	@Override
	public long getItemId(int position)
	{
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{

		country = (SortModel) list.get(position);
		int type = getItemViewType(position);
		if (convertView == null)
		{
			switch (type)
			{
			case 0:
				holder1 = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.pinner_item_group, parent, false);
				holder1.tvTitle = (TextView) convertView.findViewById(R.id.catalog);
				convertView.setTag(holder1);
				break;
			case 1:
				holder2 = new ViewHolder();
				convertView = LayoutInflater.from(mContext).inflate(R.layout.pinner_item, parent, false);
				holder2.tvTitle = (TextView) convertView.findViewById(R.id.title);
				convertView.setTag(holder2);
				break;
			}
		}
		else
		{
			switch (type)
			{
			case 0:
				holder1 = (ViewHolder) convertView.getTag();
				break;
			case 1:
				holder2 = (ViewHolder) convertView.getTag();
				break;
			}
		}

		switch (type)
		{
		case 0:
			holder1.tvTitle.setText(country.getSortLetters());
			break;
		case 1:
			holder2.tvTitle.setText(country.getName());
			break;
		}
		return convertView;
	}

	@Override
	public int getItemViewType(int position)
	{
		country = (SortModel) getItem(position);
		if (country.isGroup())
		{
			return 0;
		}
		else
		{
			return 1;
		}
	}

	@Override
	public int getViewTypeCount()
	{
		return 2;
	}

	final static class ViewHolder
	{
		TextView tvTitle;
	}
}