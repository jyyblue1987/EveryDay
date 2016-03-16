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
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
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
import common.library.utils.DataUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MessageUtils;
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
		LayoutUtils.setPadding(findViewById(R.id.lay_search_bar), 30, 20, 25, 20, true);
		
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
		
		m_txtPageTitle.setText("最新更新");
		
		getHistoryList();
			
	}
	
	protected void showLabels()
	{
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.Recent);
		
		m_editSearch.setHint(locale.SearchKey);
	}
	
	private void getHistoryList()
	{
		m_nPageNum = 0;
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		showLoadingProgress();
		
		ResultCallBack callback = new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				showHistoryList(list);
			}
		};
		
		String loginOK = DataUtils.getPreference(Const.LOGIN_OK, "0");
		
		if( loginOK.equals("1") )
			ServerManager.searchHistory(AppContext.getUserID(), m_nPageNum, m_editSearch.getText().toString(), callback);
		else
		{
			ServerManager.searchHistory(m_nPageNum, m_editSearch.getText().toString(), callback);
			findViewById(R.id.fragment_bottom).setVisibility(View.GONE);
		}		
	}
	
	private void getMoreHistoryList()
	{
		ResultCallBack callback = new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				addHistoryList(list);
			}
		};
		
		String loginOK = DataUtils.getPreference(Const.LOGIN_OK, "0");
		
		if( loginOK.equals("1") )
			ServerManager.searchHistory(AppContext.getUserID(), m_nPageNum + 1, m_editSearch.getText().toString(), callback);
		else
			ServerManager.searchHistory(m_nPageNum + 1, m_editSearch.getText().toString(), callback);
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
		String loginOK = DataUtils.getPreference(Const.LOGIN_OK, "0");
		
		if( loginOK.equals("1") == false )
			return;
			
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
	
	private void onClickAddContact(int pos)
	{
		final JSONObject item = m_adapterHistoryList.getItem(pos);
		
		showLoadingProgress();
		ServerManager.addContact(AppContext.getUserID(), item.optString(Const.HUSERNO, "0"), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(HistoryActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				try {
					item.put(Const.CHECKFRIEND, 1);
					m_adapterHistoryList.notifyDataSetChanged();
				} catch (JSONException e) {
					e.printStackTrace();
				}
				MessageUtils.showMessageDialog(HistoryActivity.this, "此用户添加到您的联系人列表.");
			}
		});
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
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_level), 200, 200, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_photo), 140, 140, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.txt_address), 190, LayoutParams.WRAP_CONTENT, true);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_address), 0, 30, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_address)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 38);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_add_contact), 80, 80, true);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_add_contact), 0, 30, 0, 0, true);

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
			((ImageView)ViewHolder.get(rowView, R.id.img_level)).setBackgroundResource(EveryDayUtils.getLevelImage(item));
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + item.optString(Const.PHOTO, ""), (ImageView)ViewHolder.get(rowView, R.id.img_photo), options);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_address)).setText(item.optString(Const.ADDRESS, ""));
			
			String loginOK = DataUtils.getPreference(Const.LOGIN_OK, "0");
			
			if( item.optInt(Const.CHECKFRIEND, 0) == 1 || loginOK.equals("1") == false )
				ViewHolder.get(rowView, R.id.img_add_contact).setVisibility(View.GONE);
			else
				ViewHolder.get(rowView, R.id.img_add_contact).setVisibility(View.VISIBLE);
			
			ViewHolder.get(rowView, R.id.txt_rank).setVisibility(View.GONE);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setText(EveryDayUtils.getName(item));
			((TextView)ViewHolder.get(rowView, R.id.txt_desc)).setText(item.optString(Const.CONTENT, ""));
			options = ImageUtils.buildUILOption(R.drawable.default_back_bg).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + MediaUtils.getThumnail(item.optString(Const.THUMBNAIL, "")), (ImageView)ViewHolder.get(rowView, R.id.img_gallery), options);
			
			if( MediaUtils.isVideoFile(item.optString(Const.THUMBNAIL, "")) == false )
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.GONE);
			else
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.VISIBLE);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(EveryDayUtils.getDate(item));
			
			((TextView)ViewHolder.get(rowView, R.id.txt_star)).setText(item.optString(Const.POINT_NUM, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_comment)).setText(item.optString(Const.COMMENT_COUNT, "0"));
			
			final int pos = position;
			ViewHolder.get(rowView, R.id.img_add_contact).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onClickAddContact(pos);
				}
			});
		}	
	}
	
	
	
}
