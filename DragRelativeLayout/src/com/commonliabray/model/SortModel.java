package com.commonliabray.model;

public class SortModel
{

	private String name; // ��ʾ������
	private String sortLetters; // ��ʾ����ƴ��������ĸ
	private boolean isGroup;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getSortLetters()
	{
		return sortLetters;
	}

	public void setSortLetters(String sortLetters)
	{
		this.sortLetters = sortLetters;
	}

	public boolean isGroup()
	{
		return isGroup;
	}

	public void setGroup(boolean isGroup)
	{
		this.isGroup = isGroup;
	}
}