package com.commonliabray.map.basic;

import java.util.List;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationClientOption.AMapLocationMode;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.AMap.InfoWindowAdapter;
import com.amap.api.maps.AMap.OnMarkerClickListener;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.overlay.BusLineOverlay;
import com.amap.api.services.busline.BusLineItem;
import com.amap.api.services.busline.BusLineQuery;
import com.amap.api.services.busline.BusLineQuery.SearchType;
import com.amap.api.services.busline.BusLineResult;
import com.amap.api.services.busline.BusLineSearch;
import com.amap.api.services.busline.BusLineSearch.OnBusLineSearchListener;
import com.example.dragrelativelayout.R;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

/**********************************************************
 * @文件名称：BasicMapActivity.java
 * @文件作者：renzhiqiang
 * @创建时间：2015年12月25日 下午11:22:24
 * @文件描述：查找公交线路，这个类可以有较大的修改空间，目前是用一个Dilaog去实现路径的展示， 可以单独创建一个activity负责路径的查找，
 *                                                  此Activity只负责参数和显示,这样的结构才是合理的
 *                                                  ，好维护的。创建初始版本 @修改历史：
 *                                                  2015年12月25日
 **********************************************************/
public class BasicMapActivity extends Activity implements OnMarkerClickListener, InfoWindowAdapter, OnClickListener,
		OnItemSelectedListener, OnBusLineSearchListener, LocationSource, AMapLocationListener {
	/**
	 * 地图相关
	 */
	private MapView mMapView;
	private AMap mAmap;
	private BusLineResult mBusLineResult;
	private List<BusLineItem> mLineItems;
	private BusLineQuery mBusLineQuery;
	private BusLineSearch mBusLineSearch;
	/**
	 * 定位相关
	 */
	private AMapLocationClient mLocationClient;
	private AMapLocationClientOption mLocationClientOption;
	private OnLocationChangedListener mLocationChangedListener;
	/**
	 * UI
	 */
	private ProgressDialog mProgressDialog;
	private EditText mSearchNameView;
	private Spinner mSelectCityView;
	private ArrayAdapter<String> mCityAdapter;
	private Button mSearchView;

	/**
	 * data
	 */
	private String mCityCode;
	private String[] itemCitys = { "北京-010", "郑州-0371", "上海-021" };;
	private int currentPage = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.busline_activity);
		initView(savedInstanceState);
	}

	private void initView(Bundle savedInstanceState) {
		mMapView = (MapView) findViewById(R.id.map);
		mMapView.onCreate(savedInstanceState);
		if (mAmap == null) {

			mAmap = mMapView.getMap();
			mAmap.setOnMarkerClickListener(this);
			mAmap.setInfoWindowAdapter(this);
			// 定位相关监听器添加
			mAmap.setLocationSource(this);
			mAmap.getUiSettings().setMyLocationButtonEnabled(true);
			mAmap.setMyLocationEnabled(true);
			mAmap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
		}

		mSearchView = (Button) findViewById(R.id.searchbyname);
		mSearchView.setOnClickListener(this);
		mSelectCityView = (Spinner) findViewById(R.id.cityName);
		mCityAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, itemCitys);
		mCityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mSelectCityView.setAdapter(mCityAdapter);
		mSelectCityView.setPrompt("请选择城市:");
		mSelectCityView.setOnItemSelectedListener(this);
		mSearchNameView = (EditText) findViewById(R.id.busName);
	}

	@Override
	protected void onResume() {
		super.onResume();
		mMapView.onResume();
	}

	@Override
	protected void onPause() {
		super.onPause();
		mMapView.onPause();
		deactivate();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
		mMapView.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mMapView.onDestroy();
		if (null != mLocationClient) {
			mLocationClient.onDestroy();
		}
	}

	@Override
	public boolean onMarkerClick(Marker arg0) {
		return false;
	}

	@Override
	public View getInfoContents(Marker arg0) {
		return null;
	}

	@Override
	public View getInfoWindow(Marker arg0) {
		return null;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.searchbyname:
			searchLine();
			break;
		}
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		String cityString = itemCitys[position];
		mCityCode = cityString.substring(cityString.indexOf("-") + 1);
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		String cityString = itemCitys[0];
		mCityCode = cityString.substring(cityString.indexOf("-") + 1);
	}

	/**
	 * 开始搜索公交路线
	 */
	private void searchLine() {
		showProgressDialog();
		String search = mSearchNameView.getText().toString().trim();
		if ("".equals(search)) {
			search = "641";
			mSearchNameView.setText(search);
		}
		mBusLineQuery = new BusLineQuery(search, SearchType.BY_LINE_NAME, mCityCode);
		mBusLineQuery.setPageSize(10);
		mBusLineQuery.setPageNumber(currentPage);

		mBusLineSearch = new BusLineSearch(this, mBusLineQuery);
		mBusLineSearch.setOnBusLineSearchListener(this);
		mBusLineSearch.searchBusLineAsyn();
	}

	/**
	 * 搜索结果回调
	 */
	@Override
	public void onBusLineSearched(BusLineResult result, int rCode) {
		dismissProgressDialog();
		if (rCode == 0) {
			if (result != null && result.getQuery() != null && result.getQuery().equals(mBusLineQuery)) {
				if (result.getQuery().getCategory() == SearchType.BY_LINE_NAME) {
					mBusLineResult = result;
					mLineItems = result.getBusLines();
					showResult(mLineItems);
				} else if (result.getQuery().getCategory() == SearchType.BY_LINE_ID) {
					mAmap.clear();
					mBusLineResult = result;
					mLineItems = result.getBusLines();
					BusLineOverlay busLineOverlay = new BusLineOverlay(this, mAmap, mLineItems.get(0));
					busLineOverlay.removeFromMap();
					busLineOverlay.addToMap();
					busLineOverlay.zoomToSpan();
				}
			}
		}
	}

	private void showProgressDialog() {
		if (mProgressDialog == null)
			mProgressDialog = new ProgressDialog(this);
		mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		mProgressDialog.setIndeterminate(false);
		mProgressDialog.setCancelable(true);
		mProgressDialog.setMessage("正在搜索:\n");
		mProgressDialog.show();
	}

	private void dismissProgressDialog() {
		if (mProgressDialog != null) {
			mProgressDialog.dismiss();
			mProgressDialog = null;
		}
	}

	private void showResult(List<BusLineItem> busLineItems) {
		BusLineDialog busLineDialog = new BusLineDialog(this, busLineItems);
		/**
		 * 根据公交路线再去查找公交ID
		 */
		busLineDialog.onListItemClicklistener(new OnListItemlistener() {
			@Override
			public void onListItemClick(BusLineDialog dialog, final BusLineItem item) {
				showProgressDialog();

				String lineId = item.getBusLineId();// 得到当前点击item公交线路id
				mBusLineQuery = new BusLineQuery(lineId, SearchType.BY_LINE_ID, mCityCode);// 第一个参数表示公交线路id，第二个参数表示公交线路id查询，第三个参数表示所在城市名或者城市区号
				mBusLineSearch = new BusLineSearch(BasicMapActivity.this, mBusLineQuery);
				mBusLineSearch.setOnBusLineSearchListener(BasicMapActivity.this);
				mBusLineSearch.searchBusLineAsyn();// 异步查询公交线路id
			}
		});
		busLineDialog.show();
	}

	/**
	 * 所有公交线路显示页面
	 */
	class BusLineDialog extends Dialog implements OnClickListener {
		private List<BusLineItem> busLineItems;
		private BusLineAdapter busLineAdapter;
		private Button preButton, nextButton;
		private ListView listView;
		protected OnListItemlistener onListItemlistener;

		public BusLineDialog(Context context, int theme) {
			super(context, theme);
		}

		public void onListItemClicklistener(OnListItemlistener onListItemlistener) {
			this.onListItemlistener = onListItemlistener;
		}

		public BusLineDialog(Context context, List<BusLineItem> busLineItems) {
			this(context, android.R.style.Theme_NoTitleBar);
			this.busLineItems = busLineItems;
			busLineAdapter = new BusLineAdapter(context, busLineItems);
		}

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.busline_dialog);
			preButton = (Button) findViewById(R.id.preButton);
			nextButton = (Button) findViewById(R.id.nextButton);
			listView = (ListView) findViewById(R.id.listview);
			listView.setAdapter(busLineAdapter);
			listView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
					onListItemlistener.onListItemClick(BusLineDialog.this, busLineItems.get(arg2));
					dismiss();
				}
			});
			preButton.setOnClickListener(this);
			nextButton.setOnClickListener(this);
			if (currentPage <= 0) {
				preButton.setEnabled(false);
			}
			if (currentPage >= mBusLineResult.getPageCount() - 1) {
				nextButton.setEnabled(false);
			}
		}

		@Override
		public void onClick(View v) {
			this.dismiss();
			if (v.equals(preButton)) {
				currentPage--;
			} else if (v.equals(nextButton)) {
				currentPage++;
			}
			showProgressDialog();
			mBusLineQuery.setPageNumber(currentPage);// 设置公交查询第几页
			mBusLineSearch.setOnBusLineSearchListener(BasicMapActivity.this);
			mBusLineSearch.searchBusLineAsyn();// 异步查询公交线路名称
		}
	}

	public interface OnListItemlistener {
		public void onListItemClick(BusLineDialog dialog, BusLineItem item);
	}

	@Override
	public void onLocationChanged(AMapLocation amapLocation) {

		if (mLocationChangedListener != null && amapLocation != null) {

			if (amapLocation.getErrorCode() == 0) {
				mLocationChangedListener.onLocationChanged(amapLocation);
			} else {
				// 定位失败
			}
		}
	}

	/**
	 * 激活定位
	 */
	@Override
	public void activate(OnLocationChangedListener listener) {

		mLocationChangedListener = listener;
		if (mLocationClient == null) {
			mLocationClient = new AMapLocationClient(this);
			mLocationClientOption = new AMapLocationClientOption();
			mLocationClient.setLocationListener(this);
			mLocationClientOption.setLocationMode(AMapLocationMode.Hight_Accuracy);
			mLocationClient.setLocationOption(mLocationClientOption);
			mLocationClient.startLocation();
		}
	}

	/**
	 * 关闭定位
	 */
	@Override
	public void deactivate() {
		mLocationChangedListener = null;
		if (mLocationClient != null) {
			mLocationClient.stopLocation();
			mLocationClient.onDestroy();
		}
		mLocationClient = null;
	}
}