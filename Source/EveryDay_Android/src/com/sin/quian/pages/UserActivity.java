
package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.image.load.ImageUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class UserActivity extends BottomBarActivity {
	PullToRefreshListView			m_listPullItems = null;
	ListView						m_listItems = null;
	TextView						m_txtEmptyView = null;
	
	MyListAdapter					m_adapteruserList = null;
	int								m_nPageNum = 0;
	
	TextView						m_txtSendSort = null;
	TextView						m_txtReceiveSort = null;
	
	int 							m_nSortMode = 0;
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
		
		m_txtSendSort = (TextView) findViewById(R.id.txt_send_sort);
		m_txtReceiveSort = (TextView) findViewById(R.id.txt_receive_sort);
	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		m_listItems.setDivider(getResources().getDrawable(R.drawable.devider_line));
		m_listItems.setDividerHeight(ScreenAdapter.computeWidth(3));
		
		m_txtEmptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		LayoutUtils.setPadding(m_txtEmptyView, 0, 0, 0, ScreenAdapter.getDeviceHeight() / 5, false);
		
		m_txtSendSort.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		m_txtReceiveSort.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setSize(m_txtSendSort, LayoutParams.WRAP_CONTENT, 120, true);
		LayoutUtils.setSize(m_txtReceiveSort, LayoutParams.WRAP_CONTENT, 120, true);
	}
	
	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("明星榜");
		
		onSelectSortTab(0);
	}
	
	protected void showLabels()
	{
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.StarTitle);
		m_txtSendSort.setText(locale.AtAppreciate);
		m_txtReceiveSort.setText(locale.HarvestAppreciate);
		
	}
	
	private void getUserList()
	{
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		
		m_nPageNum = 0;
		
		showLoadingProgress();
		ServerManager.getHighUserList(AppContext.getUserID(), m_nPageNum, m_nSortMode, new ResultCallBack() {
			
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

			m_adapteruserList = new BlogListAdapter(this, list, R.layout.fragment_list_blog, null);
			
			m_listItems.setAdapter(m_adapteruserList);
		}		
	}
	
	private void getMoreUserList()
	{
		ServerManager.getHighUserList(AppContext.getUserID(), m_nPageNum + 1, m_nSortMode, new ResultCallBack() {
			
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
				gotoHistoryPage(pos);
			}
		});
		
		findViewById(R.id.lay_send_sort).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSelectSortTab(0);				
			}
		});
		findViewById(R.id.lay_receive_sort).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSelectSortTab(1);				
			}
		});
	}
	
	private void onSelectSortTab(int tabNum)
	{
		m_nSortMode = tabNum;
		if( tabNum == 0 )
		{
			m_txtSendSort.setTextColor(getResources().getColor(R.color.app_red_color));
			findViewById(R.id.lay_send_underline).setBackgroundColor(Color.rgb(208, 0, 0));
			m_txtReceiveSort.setTextColor(getResources().getColor(R.color.app_grey_color));
			findViewById(R.id.lay_receive_underline).setBackgroundColor(Color.rgb(210, 210, 210));
		}
		else
		{
			m_txtSendSort.setTextColor(getResources().getColor(R.color.app_grey_color));
			findViewById(R.id.lay_send_underline).setBackgroundColor(Color.rgb(210, 210, 210));
			m_txtReceiveSort.setTextColor(getResources().getColor(R.color.app_red_color));
			findViewById(R.id.lay_receive_underline).setBackgroundColor(Color.rgb(208, 0, 0));
		}
		
		getUserList();
		
	}
	
	private void gotoHistoryPage(int pos)
	{
		JSONObject item = m_adapteruserList.getItem(pos - 1);
		Bundle bundle = new Bundle();	
		bundle.putString(INTENT_EXTRA, item.toString());
		ActivityManager.changeActivity(this, HistoryListActivity.class, bundle, false, null );
	}
	
	class BlogListAdapter extends MyListAdapter {
		public BlogListAdapter(Context context, List<JSONObject> data,
			int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_photo), 65, 35, 0, 35, true);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_gallery), 65, 60, 65, 35, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_photo), 200, 200, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.txt_address), 190, LayoutParams.WRAP_CONTENT, true);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_address), 0, 30, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_address)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_add_contact), 80, 80, true);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_add_contact), 0, 30, 0, 0, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_rank), 0, 10, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_rank)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 65);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_desc), 0, 23, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_desc)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_gallery_info), 0, 20, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_gallery), LayoutParams.MATCH_PARENT, 380, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_video_icon), 150, 150, true);
			
			// blog info(time, point, comment count)
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_blog_info), 0, 20, 0, 0, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_star), 20, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_star), 38, 38, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_star), 5, 0, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_star)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_comment), 20, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_comment), 45, 38, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_comment), 5, 0, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_comment)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
			
			// Data
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + item.optString(Const.PHOTO, ""), (ImageView)ViewHolder.get(rowView, R.id.img_photo), options);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_address)).setText(item.optString(Const.ADDRESS, ""));			
			ViewHolder.get(rowView, R.id.img_add_contact).setVisibility(View.GONE);
			((TextView)ViewHolder.get(rowView, R.id.txt_rank)).setText(String.format("%02d", position + 1));
			if( position > 0 )
				((TextView)ViewHolder.get(rowView, R.id.txt_rank)).setTextColor(Color.GRAY);
			else
				((TextView)ViewHolder.get(rowView, R.id.txt_rank)).setTextColor(Color.rgb(208, 0, 0));
			
			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setText(EveryDayUtils.getName(item));
			((TextView)ViewHolder.get(rowView, R.id.txt_desc)).setText(item.optString(Const.TITLE, ""));
			options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + MediaUtils.getThumnail(item.optString(Const.THUMBNAIL, "")), (ImageView)ViewHolder.get(rowView, R.id.img_gallery), options);
			
			if( MediaUtils.isVideoFile(item.optString(Const.THUMBNAIL, "")) == false )
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.GONE);
			else
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.VISIBLE);
			
			String time = item.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(time);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_star)).setText(item.optString(Const.POINT_NUM, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_comment)).setText(item.optString(Const.COMMENT_COUNT, "0"));
		}	
	}

	
}
