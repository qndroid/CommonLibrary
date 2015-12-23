package com.renzhiqiang.contentprovider;

import com.renzhiqiang.database.DBHelper;

import android.content.ContentResolver;
import android.net.Uri;

/**
 * @author vision
 *
 */
public final class DataBaseConstraint {
	private DataBaseConstraint() {
	}

	public static final int VERSION = 1;

	/**
	 * 权限
	 */
	public static final String AUTHORITY = "com.example.commonhttplibrary.provider";
	/**
	 * ContentProvider使用的协议
	 */
	public static final String SCHEME = "content://";
	/**
	 * Uri结尾ID
	 */
	public static final int FUND_ID_PATH_POSITION = 1;
	/**
	 * 默认排序方式
	 */
	public static final String DEFAULT_SORT_ORDER = "fdcode DESC";

	/**
	 * 对应数据库上的表
	 */
	public static final String TABLE_FUND = DBHelper.FUND_LIST_TABLE;

	/**
	 * 对应表中的字段
	 */
	public static final String COLMUN_FUND_CODE = DBHelper.FUND_CODE;
	public static final String COLMUN_FUND_ABBREV = DBHelper.FUND_ABBREV;
	public static final String COLMUN_FUND_TYPE = DBHelper.FUND_TYPE;
	public static final String COLMUN_FUND_SPELL = DBHelper.FUND_SPELL;
	public static final String COLMUN_FUND_ID = DBHelper.ID;

	/**
	 * 表的Uri供应用层访问数据
	 */
	public static final Uri URI_FUND = Uri.parse(SCHEME + AUTHORITY + "/" + TABLE_FUND);
	public static final Uri URI_FUND_ID_BASE = Uri.parse(SCHEME + AUTHORITY + "/" + TABLE_FUND + "/");
	public static final Uri URI_FUND_ID = Uri.parse(SCHEME + AUTHORITY + "/" + TABLE_FUND + "/#");

	/**
	 * 返回类型是dir还是item
	 */
	public static final String TYPE_FUNDS = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/vnd.com.example.commonhttplibrary.provider.fundListTable";
	public static final String TYPE_FUND = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/vnd.com.example.commonhttplibrary.provider.fundListTable";

	/**
	 * 同上一个表的处理方式完全一样
	 */
	public static final String TABLE_FUND_BROWSER = DBHelper.FUND_BROWSE_TABLE;

	public static final Uri URI_FUND_BROWER = Uri.parse(SCHEME + AUTHORITY + "/" + TABLE_FUND_BROWSER);
	public static final Uri URI_FUND_BROWER_ID_BASE = Uri.parse(SCHEME + AUTHORITY + "/" + TABLE_FUND_BROWSER + "/");
	public static final Uri URI_FUND_BROWER_ID = Uri.parse(SCHEME + AUTHORITY + "/" + TABLE_FUND_BROWSER + "/#");
	public static final String TYPE_FUND_BROWSERS = ContentResolver.CURSOR_DIR_BASE_TYPE
			+ "/vnd.com.example.commonhttplibrary.provider.fundBrowseTable";
	public static final String TYPE_FUND_BROWSER = ContentResolver.CURSOR_ITEM_BASE_TYPE
			+ "/vnd.com.example.commonhttplibrary.provider.fundBrowseTable";
}
