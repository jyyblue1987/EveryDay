package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.image.load.ImageUtils;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class CommentListActivity extends HeaderBarActivity
{
	PullToRefreshListView		m_listPullItems = null;
	ListView					m_listItems = null;
	CommentListAdapter			m_adapterCommentList = null;
	TextView					m_txtEmptyView = null;
	
	int							m_nPageNum = 0;
	JSONObject					m_historyInfo = new JSONObject();
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_comment_list);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_items);
		m_listItems = m_listPullItems.getRefreshableView();
		
		m_txtEmptyView = (TextView) findViewById(R.id.txt_empty_view);
	}
	

	
	public void layoutControls()
	{
		super.layoutControls();
		
		m_txtEmptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		LayoutUtils.setPadding(m_txtEmptyView, 0, 0, 0, ScreenAdapter.getDeviceHeight() / 5, false);
	}

	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("评论列表");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		
		Bundle bundle = getIntent().getExtras();
		
		if( bundle != null )
		{
			String data = bundle.getString(INTENT_EXTRA, "");
			try {
				JSONObject stage = new JSONObject(data);
				m_historyInfo = stage;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		m_nPageNum = 0;
		getCommentList();
	}
	
	protected void showLabels()
	{
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.ReviewList);		
	}
	
	
	private void getCommentList()
	{
		showLoadingProgress();
			
		ServerManager.getCommentList(AppContext.getUserID(), m_historyInfo.optString(Const.ID, "0"), m_nPageNum + "", new ResultCallBack() {
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				showCommentListData(list);
				
				
			}
		});
	}
	
	private void getMoreCommentList()
	{
		ServerManager.getCommentList(AppContext.getUserID(), m_historyInfo.optString(Const.ID, "0"), (m_nPageNum + 1) + "", new ResultCallBack() {
			@Override
			public void doAction(LogicResult result) {
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				addCommentList(list);				
			}
		});
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
				getMoreCommentList();
			}
		});
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		
				gotoCommentPage(arg2);
			}
		});
	}
	
	public void showCommentListData(List<JSONObject> list) {
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

			m_adapterCommentList = new CommentListAdapter(this, list, R.layout.fragment_list_comment_item, null);
			
			m_listItems.setAdapter(m_adapterCommentList);	
		}
		
	}
	
	public void addCommentList(List<JSONObject> list) {
		m_listPullItems.onRefreshComplete();
		if( list.size() < 1 )
		{
			m_listPullItems.setMode(Mode.DISABLED);
		}
		else
		{
			m_nPageNum++;
			m_adapterCommentList.addItemList(list);
		}
		
	}
	
	private void gotoCommentPage(int pos)
	{
//		Bundle bundle = new Bundle();
//		ActivityManager.changeActivity(this, CommentDetailActivity.class, bundle, false, null );	
	}
	
	class CommentListAdapter extends MyListAdapter {
		public CommentListAdapter(Context context, List<JSONObject> data,
				int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_contact_icon), 150, 150, true);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_contact_icon), 40, 40, 0, 40, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_content), 40, 40, 40, 40, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_username)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(40));
			
			((TextView)ViewHolder.get(rowView, R.id.txt_content)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
			
			// show info
			((TextView)ViewHolder.get(rowView, R.id.txt_username)).setText(item.optString(Const.USERNAME, ""));
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(EveryDayUtils.getDate(item));
			((TextView)ViewHolder.get(rowView, R.id.txt_content)).setText(item.optString(Const.CONTENT, ""));			
			
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + item.optString(Const.PHOTO, ""), (ImageView)ViewHolder.get(rowView, R.id.img_contact_icon), options);

			
		}	
	}
	
	
	 
}