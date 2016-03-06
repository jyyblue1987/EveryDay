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
import com.nostra13.universalimageloader.core.DisplayImageOptions;
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
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.design.utils.ResourceUtils;
import common.image.load.ImageUtils;
import common.library.utils.AlgorithmUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class HistoryActivity extends BottomBarActivity {
	PullToRefreshListView			m_listPullItems = null;
	ListView						m_listItems = null;
	TextView						m_txtEmptyView = null;
	
	MyListAdapter					m_adapterHistoryList = null;
	int								m_nPageNum = 0;
	
	EditText						m_editSearch = null;
	ImageView						m_imgSearch = null;
	
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
		
		m_editSearch = (EditText) findViewById(R.id.edit_search);
		m_imgSearch = (ImageView) findViewById(R.id.img_search_icon);
	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setMargin(findViewById(R.id.lay_search_bar), 60, 20, 60, 0, true);
		LayoutUtils.setPadding(findViewById(R.id.lay_search_bar), 65, 36, 40, 36, true);
		
		m_editSearch.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(m_imgSearch, 65, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgSearch, 65, 65, true);
				
		LayoutUtils.setMargin(m_listItems, 0, 20, 0, 0, true);
		m_listItems.setDivider(getResources().getDrawable(R.drawable.devider_line));
		m_listItems.setDividerHeight(ScreenAdapter.computeWidth(3));
		
		m_txtEmptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		LayoutUtils.setPadding(m_txtEmptyView, 0, 0, 0, ScreenAdapter.getDeviceHeight() / 5, false);
		
	}
	
	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("最新");
		
		getHistoryList();
			
	}
	
	private void getHistoryList()
	{
		m_nPageNum = 0;
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		showLoadingProgress();
		ServerManager.searchHistory(AppContext.getUserID(), m_nPageNum, m_editSearch.getText().toString(), new ResultCallBack() {
			
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
		ServerManager.searchHistory(AppContext.getUserID(), m_nPageNum + 1, m_editSearch.getText().toString(), new ResultCallBack() {
			
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
		
		ResourceUtils.addClickEffect(m_imgSearch);
		m_imgSearch.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getHistoryList();				
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

			m_adapterHistoryList = new BlogListAdapter(this, list, R.layout.fragment_list_blog, null);
			
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

			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_desc), 0, 23, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_desc)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_gallery), LayoutParams.MATCH_PARENT, 380, true);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_gallery), 0, 20, 0, 0, true);
			
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
			
			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setText(item.optString(Const.USERNAME, ""));
			((TextView)ViewHolder.get(rowView, R.id.txt_desc)).setText(item.optString(Const.TITLE, ""));
			options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + MediaUtils.getThumnail(item.optString(Const.THUMBNAIL, "")), (ImageView)ViewHolder.get(rowView, R.id.img_gallery), options);
			
			String time = item.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(time);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_star)).setText(item.optString(Const.POINT_NUM, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_comment)).setText(item.optString(Const.COMMENT_COUNT, "0"));
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
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_video_icon), 200, 200, true);
			
			// show data
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + item.optString(Const.PHOTO, ""), (ImageView)ViewHolder.get(rowView, R.id.img_historyitem_icon), options);
			
			((TextView)ViewHolder.get(rowView, R.id.text_historyitem_name)).setText(item.optString(Const.USERNAME, ""));
			((TextView)ViewHolder.get(rowView, R.id.text_historyitem_hard_num)).setText(item.optString(Const.RECEIVE_NUM, ""));
			((TextView)ViewHolder.get(rowView, R.id.text_historyitem_star_num)).setText(item.optString(Const.POINT_NUM, ""));
			((TextView)ViewHolder.get(rowView, R.id.text_historyitem_hisaddress)).setText(item.optString(Const.ADDRESS, ""));			
			
			options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + MediaUtils.getThumnail(item.optString(Const.THUMBNAIL, "")), (ImageView)ViewHolder.get(rowView, R.id.img_history_preview), options);
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setText(item.optString(Const.CONTENT, ""));
			
			String time = item.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
			String date = MyTime.getOnlyMonthDate(time) + "\n" + MyTime.getOnlyYear(time);
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(date);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_comment_count)).setText(item.optString(Const.COMMENT_COUNT, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_like_count)).setText(item.optString(Const.LIKE_COUNT, "0"));	
			
			if( MediaUtils.isVideoFile(item.optString(Const.THUMBNAIL, "")) == false )
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.GONE);
			else
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.VISIBLE);
		}	
	}
	
}
