package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

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
import common.component.ui.MyButton;
import common.component.ui.MyTextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.manager.activity.ActivityManager;

public class StageActivity extends HeaderBarActivity 
{
	PullToRefreshListView			m_listPullItems = null;
	ListView						m_listItems = null;
	TextView						m_emptyView = null;
	MyListAdapter					m_adapterStageList = null;
	
	ImageView 						m_imgUserPhoto = null;
	MyTextView						m_textUserName = null;
	ImageView 						m_imgHard = null;
	MyTextView						m_textHardNum = null;
	ImageView 						m_imgStar = null;
	MyTextView						m_textStarNum = null;
	MyButton						m_btnFavorite = null;	
	MyButton						m_btnEdit = null;	
	MyButton						m_btnAid = null;	
	MyButton						m_btnFriendAdd = null;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_stage);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();
	
		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_stage); 
		m_listItems = m_listPullItems.getRefreshableView();
		m_emptyView = (TextView) findViewById(R.id.txt_stage);
		
		m_imgUserPhoto = (ImageView)findViewById(R.id.img_stage_icon);
		m_textUserName = (MyTextView) findViewById(R.id.text_stage_name);

		m_imgHard = (ImageView)findViewById(R.id.img_stage_hard);
		m_textHardNum = (MyTextView) findViewById(R.id.text_stage_hard_num);

		m_imgStar = (ImageView)findViewById(R.id.img_stage_star);
		m_textStarNum = (MyTextView) findViewById(R.id.text_stage_star_num);
		
		m_btnFavorite = (MyButton)findViewById(R.id.btn_stage_favorite);
		m_btnEdit = (MyButton)findViewById(R.id.btn_stage_edit);
		m_btnAid = (MyButton)findViewById(R.id.btn_stage_aid);
		m_btnFriendAdd = (MyButton)findViewById(R.id.btn_stage_friendAdd);

	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		m_listItems.setDivider(getResources().getDrawable(R.drawable.devider_line));
		m_listItems.setDividerHeight(ScreenAdapter.computeWidth(3));
		
		m_emptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(55));
		LayoutUtils.setMargin(m_emptyView, 0, 0, 0, 600, true);
		
		LayoutUtils.setSize(findViewById(R.id.lay_stage_1), LayoutParams.MATCH_PARENT, 100, true);
		
		LayoutUtils.setSize(m_imgUserPhoto, LayoutParams.MATCH_PARENT, 80, true);
		LayoutUtils.setMargin(m_imgUserPhoto, 20, 10, 10, 10, true);
		
		m_textUserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(55));
		LayoutUtils.setMargin(m_textUserName, 0, 0, 0, 600, true);

		LayoutUtils.setSize(m_imgHard, LayoutParams.MATCH_PARENT, 60, true);
		LayoutUtils.setMargin(m_imgHard, 20, 20, 10, 20, true);
		
		m_textHardNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(60));
		LayoutUtils.setMargin(m_textHardNum, 20, 20, 10, 20, true);

		LayoutUtils.setSize(m_imgStar, LayoutParams.MATCH_PARENT, 60, true);
		LayoutUtils.setMargin(m_imgStar, 20, 20, 10, 20, true);
		
		m_textStarNum.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(60));
		LayoutUtils.setMargin(m_textStarNum, 20, 20, 10, 20, true);

		LayoutUtils.setSize(findViewById(R.id.lay_stage_2), LayoutParams.MATCH_PARENT, 100, true);

		LayoutUtils.setSize(m_btnFavorite, LayoutParams.MATCH_PARENT, 60, true);
		LayoutUtils.setMargin(m_btnFavorite, 20, 20, 10, 20, true);
		
	}
	
	protected void initData()
	{
		super.initData();
		
//		m_nSelectedPage = 5;
		
		m_adapterStageList = new StageListAdapter(this, new ArrayList<JSONObject>(), R.layout.stage_item, null);
		m_listItems.setAdapter(m_adapterStageList);
		
//		m_listPullItems.setMode(Mode.DISABLED);
		
		m_txtPageTitle.setText("Stage");
//		m_btnRight.setVisibility(View.INVISIBLE);
 
//		List<JSONObject> list = DBManager.getNotificationList(this, 0, 100);
//		HealthcareUtils.changeDateFormat(list);
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		for(int i = 0; i < 10; i++ )
		{
			JSONObject item = new JSONObject();
			list.add(item);
		}
		
		showStageListData(list);	
	}
	
	protected void initEvents()
	{
		super.initEvents();
				
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {	
//				gotoStageDetailPage(pos);
			}
		});
	}

	protected void gotoNextPage()
	{
		Bundle bundle = new Bundle();				
		ActivityManager.changeActivity(this, MainMenuActivity.class, bundle, false, null );
	}
	
	public void showStageListData(List<JSONObject> list) {
		m_listPullItems.onRefreshComplete();
		if( list.size() < 1 )
		{
			m_listPullItems.setVisibility(View.GONE);
			m_emptyView.setText("There is no Stage list.");
		}
		else
		{
			m_listPullItems.setVisibility(View.VISIBLE);
			m_adapterStageList.setData(list);
		}		
	}
	
	class StageListAdapter extends MyListAdapter {
		public StageListAdapter(Context context, List<JSONObject> data,
			int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			LayoutUtils.setSize(findViewById(R.id.lay_stageitem_1), LayoutParams.MATCH_PARENT, 100, true);

			((TextView)rowView.findViewById(R.id.text_stageitem_address)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_stageitem_address), 50, 10, 10, 10, true);

			((TextView)rowView.findViewById(R.id.text_stageitem_hisaddress)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_stageitem_hisaddress), 10, 10, 10, 10, true);

			((TextView)rowView.findViewById(R.id.text_stageitem_favorite)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_stageitem_favorite), 400, 10, 50, 10, true);
			
			LayoutUtils.setSize(findViewById(R.id.lay_stageitem_2), LayoutParams.MATCH_PARENT, 450, true);

			LayoutUtils.setSize(((ImageView)rowView.findViewById(R.id.img_stageitem_photo)), 300, 300, true);
			LayoutUtils.setMargin(((ImageView)rowView.findViewById(R.id.img_stageitem_photo)), 50, 10, 50, 10, true);
			
			((TextView)rowView.findViewById(R.id.text_stageitem_note)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
			LayoutUtils.setMargin(rowView.findViewById(R.id.text_stageitem_note), 10, 10, 10, 10, true);
			
//			final int pos = position;
			rowView.setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View paramView) {
//					Bundle bundle = new Bundle();
//					ActivityManager.changeActivity(StageActivity.this, LoginActivity.class, bundle, false, null );		
				}
			});		

			
			
		}	
	}
	
}
