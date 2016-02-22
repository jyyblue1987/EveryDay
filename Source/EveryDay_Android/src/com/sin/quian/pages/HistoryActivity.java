package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.AlgorithmUtils;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class HistoryActivity extends HeaderBarActivity {
	PullToRefreshListView			m_listPullItems = null;
	ListView						m_listItems = null;
	TextView						m_txtEmptyView = null;
	
	MyListAdapter					m_adapterHistoryList = null;
	int								m_nPageNum = 0;
	
	private static int	STAGE_LIST_CODE = 201;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_history);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();
	
		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_history); 
		m_listItems = m_listPullItems.getRefreshableView();
		m_txtEmptyView = (TextView) findViewById(R.id.txt_empty_view);
	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		
		m_listItems.setDivider(getResources().getDrawable(R.drawable.devider_line));
		m_listItems.setDividerHeight(ScreenAdapter.computeWidth(3));
		
		m_txtEmptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		LayoutUtils.setPadding(m_txtEmptyView, 0, 0, 0, ScreenAdapter.getDeviceHeight() / 5, false);
	}
	
	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("最新们");
		
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		m_nPageNum = 0;
		getHistoryList();
			
	}
	
	private void getHistoryList()
	{
		showLoadingProgress();
		ServerManager.getRecentHistory(AppContext.getUserID(), m_nPageNum, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				showHistoryList(list);
			}
		});
	}
	
	private void getMoreHistoryList()
	{
		ServerManager.getRecentHistory(AppContext.getUserID(), m_nPageNum + 1, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				addHistoryList(list);
			}
		});
	}
	
	public void addHistoryList(List<JSONObject> list) {
		m_listPullItems.onRefreshComplete();
		if( list.size() < 1 )
		{
			m_listPullItems.setMode(Mode.DISABLED);
		}
		else
		{
			m_nPageNum++;
			m_adapterHistoryList.addItemList(list);
		}
		
	}
	
	protected void initEvents()
	{
		super.initEvents();
				
		m_listPullItems.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView)
			{
			}
			@Override
			public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView)
			{
				getMoreHistoryList();
			}
		});
		
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {	
				gotoStageListPage(pos);
			}
		});
	}

	protected void gotoNextPage()
	{
		Bundle bundle = new Bundle();				
		ActivityManager.changeActivity(this, MainMenuActivity.class, bundle, false, null );
	}
	
	private void gotoStageListPage(int pos)
	{
		Bundle bundle = new Bundle();
		
		JSONObject param = new JSONObject();
		
		try {
			JSONObject item = m_adapterHistoryList.getItem(pos - 1);
			param.put(Const.MODE, Const.OTHER_STAGE_MODE);
			AlgorithmUtils.bindJSONObject(param, item);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		bundle.putString(INTENT_EXTRA, param.toString());
		
		ActivityManager.changeActivity(this, StageListActivity.class, bundle, false, STAGE_LIST_CODE );			
	}
	
	public void showHistoryList(List<JSONObject> list) {
		if( list.size() < 1 )
		{
			m_listItems.setVisibility(View.GONE);
			m_listPullItems.setVisibility(View.GONE);
			m_listPullItems.setMode(Mode.DISABLED);
		}
		else
		{
			m_listItems.setVisibility(View.VISIBLE);
			m_listPullItems.setVisibility(View.VISIBLE);

			m_adapterHistoryList = new HistoryListAdapter(this, list, R.layout.history_item, null);
			
			m_listItems.setAdapter(m_adapterHistoryList);
		}		
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0)
			return;		
		
		if (requestCode == STAGE_LIST_CODE ) {
			getHistoryList();
		}	

		super.onActivityResult(requestCode, resultCode, data);	
	}
	
	class HistoryListAdapter extends MyListAdapter {
		public HistoryListAdapter(Context context, List<JSONObject> data,
			int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			// user info
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_historyitem_1), 30, 30, 30, 0, true);
			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_icon)), 80, 80, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_name)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_name), 20, 0, 50, 0, true);
//			((TextView)rowView.findViewById(R.id.txt_History_info)).setText(item.optString(Const.DISP_DATE, MyTime.getCurrentDate()));

			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_hard)), 50, 50, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_historyitem_hard)), 50, 0, 0, 0, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_hard_num)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_hard_num), 10, 0, 0, 0, true);

			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_star)), 50, 50, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_historyitem_star)), 50, 0, 0, 0, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_star_num)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_star_num), 10, 0, 0, 0, true);

			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_historyitem_2), 30, 0, 30, 0, true);
			((TextView)rowView.findViewById(R.id.text_historyitem_address)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_address), 0, 0, 0, 0, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_hisaddress)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_hisaddress), 10, 0, 0, 0, true);
			
			// history info			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_historyitem_3), 30, 10, 30, 30, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
			
			int iconsize = 40;
			int fontsize = 25;
			int padding = 10;
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_like_count_icon), iconsize, iconsize, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_like_count)).setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_like_count), padding, 0, 0, 0, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_comment_count_icon), iconsize, iconsize, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_comment_count)).setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_comment_count), padding, 0, 0, 0, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_history_content), 30, 0, 0, 0, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_history_preview), LayoutParams.MATCH_PARENT, 500, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
			
			// show data
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + item.optString(Const.PHOTO, ""), (ImageView)ViewHolder.get(rowView, R.id.img_historyitem_icon));
			
			((TextView)ViewHolder.get(rowView, R.id.text_historyitem_name)).setText(item.optString(Const.USERNAME, ""));
			((TextView)ViewHolder.get(rowView, R.id.text_historyitem_hard_num)).setText(item.optString(Const.RECEIVE_NUM, ""));
			((TextView)ViewHolder.get(rowView, R.id.text_historyitem_star_num)).setText(item.optString(Const.POINT_NUM, ""));
			((TextView)ViewHolder.get(rowView, R.id.text_historyitem_address)).setText(item.optString(Const.ADDRESS, ""));			
			
//			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + item.optString(Const.THUMBNAIL, ""), (ImageView)ViewHolder.get(rowView, R.id.img_history_preview));
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setText(item.optString(Const.CONTENT, ""));
			
			String time = item.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
			String date = MyTime.getOnlyMonthDate(time) + "\n" + MyTime.getOnlyYear(time);
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(date);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_comment_count)).setText(item.optString(Const.COMMENT_COUNT, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_like_count)).setText(item.optString(Const.LIKE_COUNT, "0"));			
		}	
	}
	
}
