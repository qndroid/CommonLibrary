package com.commonliabray.activity.customview;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import com.common.widget.associatemail.MailBoxAssociateTokenizer;
import com.common.widget.associatemail.MailBoxAssociateView;
import com.example.dragrelativelayout.R;

/**********************************************************
 * @文件名称：LoginActivity.java
 * @文件作者：rzq
 * @创建时间：2015年3月31日 上午11:24:00
 * @文件描述：登录页
 * @修改历史：2015年3月31日创建初始版本
 **********************************************************/

public class MailAssociateActivity extends Activity
{
	protected MailBoxAssociateView emailInput;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_mailassociate);
		initView();
	}

	protected void initView()
	{
		emailInput = (MailBoxAssociateView) findViewById(R.id.associate_email_input);
		String[] recommendMailBox = getResources().getStringArray(R.array.recommend_mailbox);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.associate_mail_list_item,
				R.id.tv_recommend_mail, recommendMailBox);
		emailInput.setAdapter(adapter);
		emailInput.setTokenizer(new MailBoxAssociateTokenizer());
	}
}