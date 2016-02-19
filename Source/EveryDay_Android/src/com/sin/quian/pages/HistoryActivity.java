package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sin.quian.R;

import android.content.Context;
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
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.manager.activity.ActivityManager;

public class HistoryActivity extends HeaderBarActivity {
	PullToRefreshListView			m_listPullItems = null;
	ListView						m_listItems = null;
	TextView						m_emptyView = null;
	MyListAdapter					m_adapterHistoryList = null;
//	int								m_nHistoryType = 0;
//	int								m_nParentID = 0;

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
		m_emptyView = (TextView) findViewById(R.id.txt_history);
	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		m_listItems.setDivider(getResources().getDrawable(R.drawable.devider_line));
		m_listItems.setDividerHeight(ScreenAdapter.computeWidth(3));
		
		m_emptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(55));
		LayoutUtils.setMargin(m_emptyView, 0, 0, 0, 600, true);
	}
	
	protected void initData()
	{
		super.initData();
		
//		m_nSelectedPage = 5;
		
		m_adapterHistoryList = new HistoryListAdapter(this, new ArrayList<JSONObject>(), R.layout.history_item, null);
		m_listItems.setAdapter(m_adapterHistoryList);
		
		m_listPullItems.setMode(Mode.DISABLED);
		
		m_txtPageTitle.setText("History");
//		m_btnRight.setVisibility(View.INVISIBLE);
 
//		List<JSONObject> list = DBManager.getNotificationList(this, 0, 100);
//		HealthcareUtils.changeDateFormat(list);
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		for(int i = 0; i < 10; i++ )
		{
			JSONObject item = new JSONObject();
			list.add(item);
		}
		
		showHistoryListData(list);	
	}
	
	protected void initEvents()
	{
		super.initEvents();
				
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {	
//				gotoHistoryDetailPage(pos);
			}
		});
	}

	protected void gotoNextPage()
	{
		Bundle bundle = new Bundle();				
		ActivityManager.changeActivity(this, MainMenuActivity.class, bundle, false, null );
	}
	
	public void showHistoryListData(List<JSONObject> list) {
		m_listPullItems.onRefreshComplete();
		if( list.size() < 1 )
		{
			m_listPullItems.setVisibility(View.GONE);
			m_emptyView.setText("There is no History list.");
		}
		else
		{
			m_listPullItems.setVisibility(View.VISIBLE);
			m_adapterHistoryList.setData(list);
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
			
			LayoutUtils.setSize(findViewById(R.id.lay_historyitem_1), LayoutParams.MATCH_PARENT, 100, true);

			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_icon)), 80, 80, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_historyitem_icon)), 50, 10, 20, 10, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_name)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_name), 20, 20, 50, 20, true);
//			((TextView)rowView.findViewById(R.id.txt_History_info)).setText(item.optString(Const.DISP_DATE, MyTime.getCurrentDate()));

			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_hard)), 200, 50, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_historyitem_hard)), 20, 20, 10, 20, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_hard_num)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_hard_num), 10, 20, 20, 20, true);

			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_star)), 100, 50, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_historyitem_star)), 20, 20, 10, 20, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_star_num)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_star_num), 10, 20, 10, 20, true);

			LayoutUtils.setSize(findViewById(R.id.lay_historyitem_2), LayoutParams.MATCH_PARENT, 70, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_address)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_address), 50, 10, 10, 10, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_hisaddress)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_hisaddress), 10, 10, 10, 10, true);

			((TextView)rowView.findViewById(R.id.text_historyitem_favorite)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_favorite), 400, 10, 50, 10, true);
			
			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_historyitem_photo)), 300, 300, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_historyitem_photo)), 50, 10, 50, 10, true);
			
			((TextView)rowView.findViewById(R.id.text_historyitem_note)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_historyitem_note), 10, 10, 10, 10, true);
			
			final int pos = position;
			rowView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View paramView) {
					Bundle bundle = new Bundle();
					ActivityManager.changeActivity(HistoryActivity.this, StageActivity.class, bundle, false, null );		
				}
			});		

			
			
		}	
	}
	
}
