package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sin.quian.R;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;


public class CommentListActivity extends HeaderBarActivity
{
	PullToRefreshListView		m_listPullItems = null;
	ListView					m_listItems = null;
	CommentListAdapter			m_adapterCommentList = null;
	
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
	}

	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("Comment List");
		m_btnRight.setVisibility(View.INVISIBLE);
		
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
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		
				gotoCommentPage(arg2);
			}
		});
	}
		
	protected void layoutControls()
	{
		super.layoutControls();		
	}
	
	public void showHistoryListData(List<JSONObject> list) {
		if( list.size() < 1 )
		{
			m_listItems.setVisibility(View.GONE);
		}
		else
		{
			m_listItems.setVisibility(View.VISIBLE);

			m_adapterCommentList = new CommentListAdapter(this, list, R.layout.fragment_list_comment_item, null);
			
			m_listItems.setAdapter(m_adapterCommentList);	
		}
		
	}
	
	private void gotoCommentPage(int pos)
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, CommentDetailActivity.class, bundle, false, null );	
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
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_content), 0, 20, 0, 40, true);
		}	
	}
	
	
	 
}