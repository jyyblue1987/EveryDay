package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;


public class MyCenterActivity extends HeaderBarActivity
{
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	
	ImageView 		m_imgPhoto = null;
	TextView 		m_txtName = null;
	
	int [] m_field_item = {
			R.id.fragment_username,
			R.id.fragment_password,
		};
	
	PullToRefreshListView		m_listPullItems = null;
	ListView					m_listItems = null;
	HistoryListAdapter			m_adapterHistoryList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_center);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgPhoto = (ImageView) findViewById(R.id.img_contact_icon);
		m_txtName = (TextView) findViewById(R.id.txt_name);
		
		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_items);
		m_listItems = m_listPullItems.getRefreshableView();
	}

	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("Personal Center");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		JSONObject profile = AppContext.getProfile();
		m_txtName.setText(profile.optString(Const.USERNAME, ""));
		
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		for(int i = 0; i < 10; i++)
		{
			list.add(new JSONObject());
		}
		
		showHistoryListData(list);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
		
		findViewById(R.id.lay_contact_info).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
		
		m_listPullItems.setOnRefreshListener(new OnRefreshListener2<ListView>() {
			@Override
			public void onPullDownToRefresh(final PullToRefreshBase<ListView> refreshView)
			{
			}
			@Override
			public void onPullUpToRefresh(final PullToRefreshBase<ListView> refreshView)
			{
//				presenter.getContactList();
			}
		});
	}
		
	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setMargin(findViewById(R.id.lay_contact_info), 0, 50, 0, 0, true);
		LayoutUtils.setMargin(findViewById(R.id.lay_contact_item), 0, 30, 0, 30, true);
		
		LayoutUtils.setMargin(m_imgPhoto, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgPhoto, 200, 200, true);
		
		LayoutUtils.setMargin(m_txtName, 40, 0, 0, 0, true);
		m_txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setSize(findViewById(R.id.img_nav_icon), 25, 40, true);
		LayoutUtils.setMargin(findViewById(R.id.img_nav_icon), 40, 0, 40, 0, true);
	}
	
	public void showHistoryListData(List<JSONObject> list) {
		if( list.size() < 1 )
		{
			m_listItems.setVisibility(View.GONE);
		}
		else
		{
			m_listItems.setVisibility(View.VISIBLE);

			m_adapterHistoryList = new HistoryListAdapter(this, list, R.layout.fragment_list_history_item, null);
			
			m_listItems.setAdapter(m_adapterHistoryList);	
		}
		
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
			
			LayoutUtils.setPadding(ViewHolder.get(rowView, R.id.lay_fragment), 30, 30, 30, 30, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_history_preview), 10, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_history_preview), 300, 300, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
			
		}	
	}
	
	
	 
}