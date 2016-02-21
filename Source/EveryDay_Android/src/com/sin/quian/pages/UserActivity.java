
package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sin.quian.AppContext;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;
import com.sin.quian.pages.HistoryActivity.HistoryListAdapter;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class UserActivity extends HeaderBarActivity {
	PullToRefreshListView			m_listPullItems = null;
	ListView						m_listItems = null;
	TextView						m_txtEmptyView = null;
	
	MyListAdapter					m_adapteruserList = null;
	int								m_nPageNum = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();
	
		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_user); 
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
		
		m_txtPageTitle.setText("用户们");
		
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		
		m_nPageNum = 0;
		getUserList();	
	}
	
	private void getUserList()
	{
		showLoadingProgress();
		ServerManager.getHighUserList(AppContext.getUserID(), m_nPageNum, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				showUserList(list);
			}
		});
	}
	
	private void showUserList(List<JSONObject> list) {
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

			m_adapteruserList = new UserListAdapter(this, list, R.layout.history_item, null);
			
			m_listItems.setAdapter(m_adapteruserList);
		}		
	}
	
	private void getMoreUserList()
	{
		ServerManager.getHighUserList(AppContext.getUserID(), m_nPageNum + 1, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				addUserList(list);
			}
		});
	}
	
	public void addUserList(List<JSONObject> list) {
		m_listPullItems.onRefreshComplete();
		if( list.size() < 1 )
		{
			m_listPullItems.setMode(Mode.DISABLED);
		}
		else
		{
			m_nPageNum++;
			m_adapteruserList.addItemList(list);
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
				getMoreUserList();
			}
		});
				
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {	
			}
		});
	}

	protected void gotoNextPage()
	{
		Bundle bundle = new Bundle();				
		ActivityManager.changeActivity(this, MainMenuActivity.class, bundle, false, null );
	}
	

	
	class UserListAdapter extends MyListAdapter {
		public UserListAdapter(Context context, List<JSONObject> data,
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

			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_hard)), 50, 50, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_historyitem_hard)), 50, 0, 0, 0, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_hard_num)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_hard_num), 10, 0, 0, 0, true);

			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_star)), 50, 50, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_historyitem_star)), 50, 0, 0, 0, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_star_num)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_star_num), 10, 0, 0, 0, true);

			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_historyitem_2), 30, 0, 30, 30, true);
			((TextView)rowView.findViewById(R.id.text_historyitem_address)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_address), 0, 0, 0, 0, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_hisaddress)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_hisaddress), 10, 0, 0, 0, true);
			
			ViewHolder.get(rowView, R.id.lay_historyitem_3).setVisibility(View.GONE);
		}	
	}
	
}
