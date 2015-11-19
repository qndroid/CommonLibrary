package com.common.widget.associatemail;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.MultiAutoCompleteTextView;

/**********************************************************
 * @文件名称：MailBoxAssociateView.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年8月26日 下午11:03:21
 * @文件描述：本在邮箱联想控件，输入@符后开始联想
 * @修改历史：2015年8月26日创建初始版本
 **********************************************************/
public class MailBoxAssociateView extends MultiAutoCompleteTextView
{
	public MailBoxAssociateView(Context context)
	{
		super(context);
	}

	public MailBoxAssociateView(Context context, AttributeSet attrs)
	{
		super(context, attrs);
	}

	public MailBoxAssociateView(Context context, AttributeSet attrs, int defStyle)
	{
		super(context, attrs, defStyle);
	}

	@Override
	public boolean enoughToFilter()
	{
		return getText().toString().contains("@") && getText().toString().indexOf("@") > 0;
	}
}