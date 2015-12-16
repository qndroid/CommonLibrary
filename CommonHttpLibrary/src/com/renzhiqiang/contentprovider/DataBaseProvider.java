package com.renzhiqiang.contentprovider;

import java.util.HashMap;

import com.renzhiqiang.database.DBHelper;

import android.content.ContentProvider;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;

/**
 * @author vision
 * @function 将本地数据库数据制作为ContentProvider供外部调用
 */
public class DataBaseProvider extends ContentProvider {
	/**
	 * 权限和uri定义
	 */
	private static final int FUNDS = 1;
	private static final int FUND_ITEM = 2;
	private static final int FUND_HISTORYS = 3;
	private static final int FUND_HISTORYS_ITEM = 4;

	private DBHelper mDbHelp;
	private static UriMatcher mUriMatcher;

	private static HashMap<String, String> fundListProjectMap;
	private static HashMap<String, String> fundHistoryProjectMap;

	static {

		mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);
		mUriMatcher.addURI(DataBaseConstraint.AUTHORITY, DataBaseConstraint.TABLE_FUND, FUNDS);
		mUriMatcher.addURI(DataBaseConstraint.AUTHORITY, DataBaseConstraint.TABLE_FUND + "/#", FUND_ITEM);
		mUriMatcher.addURI(DataBaseConstraint.AUTHORITY, DataBaseConstraint.TABLE_FUND_BROWSER, FUND_HISTORYS);
		mUriMatcher.addURI(DataBaseConstraint.AUTHORITY, DataBaseConstraint.TABLE_FUND_BROWSER + "/#",
				FUND_HISTORYS_ITEM);

		fundListProjectMap = new HashMap<String, String>();
		fundListProjectMap.put(DataBaseConstraint.COLMUN_FUND_CODE, DataBaseConstraint.COLMUN_FUND_CODE);
		fundListProjectMap.put(DataBaseConstraint.COLMUN_FUND_ABBREV, DataBaseConstraint.COLMUN_FUND_ABBREV);
		fundListProjectMap.put(DataBaseConstraint.COLMUN_FUND_SPELL, DataBaseConstraint.COLMUN_FUND_SPELL);
		fundListProjectMap.put(DataBaseConstraint.COLMUN_FUND_TYPE, DataBaseConstraint.COLMUN_FUND_TYPE);
		fundListProjectMap.put(DataBaseConstraint.COLMUN_FUND_ID, DataBaseConstraint.COLMUN_FUND_ID);

		fundHistoryProjectMap = new HashMap<String, String>();
		fundHistoryProjectMap.put(DataBaseConstraint.COLMUN_FUND_CODE, DataBaseConstraint.COLMUN_FUND_CODE);
		fundHistoryProjectMap.put(DataBaseConstraint.COLMUN_FUND_ABBREV, DataBaseConstraint.COLMUN_FUND_ABBREV);
		fundHistoryProjectMap.put(DataBaseConstraint.COLMUN_FUND_SPELL, DataBaseConstraint.COLMUN_FUND_SPELL);
		fundHistoryProjectMap.put(DataBaseConstraint.COLMUN_FUND_TYPE, DataBaseConstraint.COLMUN_FUND_TYPE);
		fundHistoryProjectMap.put(DataBaseConstraint.COLMUN_FUND_ID, DataBaseConstraint.COLMUN_FUND_ID);
	}

	@Override
	public boolean onCreate() {
		mDbHelp = new DBHelper(getContext());
		return true;
	}

	@Override
	public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		switch (mUriMatcher.match(uri)) {
		case FUND_ITEM:
			qb.setTables(DataBaseConstraint.TABLE_FUND);
			qb.setProjectionMap(fundListProjectMap);
			qb.appendWhere(DBHelper.ID + "=" + uri.getPathSegments().get(DataBaseConstraint.FUND_ID_PATH_POSITION));
			break;
		case FUNDS:
			qb.setTables(DataBaseConstraint.TABLE_FUND);
			qb.setProjectionMap(fundListProjectMap);
			break;
		case FUND_HISTORYS:
			qb.setTables(DataBaseConstraint.TABLE_FUND_BROWSER);
			qb.setProjectionMap(fundHistoryProjectMap);
			break;
		case FUND_HISTORYS_ITEM:
			qb.setTables(DataBaseConstraint.TABLE_FUND_BROWSER);
			qb.setProjectionMap(fundHistoryProjectMap);
			qb.appendWhere(DBHelper.ID + "=" + uri.getPathSegments().get(DataBaseConstraint.FUND_ID_PATH_POSITION));
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		Cursor c = qb.query(mDbHelp.getReadableDatabase(), // The database to
															// query
				projection, // The columns to return from the query
				selection, // The columns for the where clause
				selectionArgs, // The values for the where clause
				null, // don't group the rows
				null, // don't filter by row groups
				sortOrder // The sort order
		);

		/**
		 * 向观察者发送通知
		 */
		c.setNotificationUri(getContext().getContentResolver(), uri);
		return c;
	}

	@Override
	public String getType(Uri uri) {
		switch (mUriMatcher.match(uri)) {
		case FUNDS:
			return DataBaseConstraint.TYPE_FUNDS;
		case FUND_ITEM:
			return DataBaseConstraint.TYPE_FUND;
		case FUND_HISTORYS:
			return DataBaseConstraint.TYPE_FUND_BROWSERS;
		case FUND_HISTORYS_ITEM:
			return DataBaseConstraint.TYPE_FUND_BROWSER;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
	}

	@Override
	public Uri insert(Uri uri, ContentValues values) {
		long rowId;
		switch (mUriMatcher.match(uri)) {
		case FUNDS:
			rowId = mDbHelp.insert(DataBaseConstraint.TABLE_FUND, null, values);
			if (rowId > 0) {
				Uri fundUri = ContentUris.withAppendedId(DataBaseConstraint.URI_FUND_ID_BASE, rowId);
				getContext().getContentResolver().notifyChange(fundUri, null);
				return fundUri;
			}
		case FUND_HISTORYS:
			rowId = mDbHelp.insert(DataBaseConstraint.TABLE_FUND_BROWSER, null, values);
			if (rowId > 0) {
				Uri fundUri = ContentUris.withAppendedId(DataBaseConstraint.URI_FUND_BROWER_ID_BASE, rowId);
				getContext().getContentResolver().notifyChange(fundUri, null);
				return fundUri;
			}
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}

		throw new SQLException("Failed to insert row into " + uri);
	}

	/**
	 * 未对ID做处理
	 */
	@Override
	public int delete(Uri uri, String selection, String[] selectionArgs) {
		int count;
		switch (mUriMatcher.match(uri)) {
		case FUNDS:
			count = mDbHelp.delete(DataBaseConstraint.TABLE_FUND, selection, selectionArgs);
			break;
		case FUND_HISTORYS:
			count = mDbHelp.delete(DataBaseConstraint.TABLE_FUND_BROWSER, selection, selectionArgs);
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}

	@Override
	public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
		int count;
		switch (mUriMatcher.match(uri)) {
		case FUNDS:
			count = mDbHelp.update(DataBaseConstraint.TABLE_FUND, values, selection, selectionArgs);
			break;
		case FUND_HISTORYS:
			count = mDbHelp.update(DataBaseConstraint.TABLE_FUND_BROWSER, values, selection, selectionArgs);
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + uri);
		}
		getContext().getContentResolver().notifyChange(uri, null);
		return count;
	}
}