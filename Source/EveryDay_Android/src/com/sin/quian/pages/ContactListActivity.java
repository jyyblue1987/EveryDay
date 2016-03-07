
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
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.image.load.ImageUtils;
import common.library.utils.MessageUtils;
import common.library.utils.MessageUtils.OnButtonClickListener;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class ContactListActivity extends BottomBarActivity {
	PullToRefreshListView			m_listPullItems = null;
	ListView						m_listItems = null;
	TextView						m_txtEmptyView = null;
	
	MyListAdapter					m_adapterContactList = null;
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
		
		findViewById(R.id.lay_sort_tab).setVisibility(View.GONE);
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
		
		m_txtPageTitle.setText("好友");
		m_txtEmptyView.setText("没有联系人列表.");
		
		getContactList();	
	}
	
	private void getContactList()
	{
		m_nPageNum = 0;
		m_listPullItems.setMode(Mode.PULL_FROM_END);

		showLoadingProgress();
		ServerManager.getContactList(AppContext.getUserID(), m_nPageNum, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				showContactList(list);
			}
		});
	}
	
	private void showContactList(List<JSONObject> list) {
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

			m_adapterContactList = new ContactListAdapter(this, list, R.layout.fragment_list_contact, null);
			
			m_listItems.setAdapter(m_adapterContactList);
		}		
	}
	
	private void getMoreContactList()
	{
		ServerManager.getContactList(AppContext.getUserID(), m_nPageNum + 1, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray array = result.getContentArray();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < array.length(); i++ )
					list.add(array.optJSONObject(i));
				addContactList(list);
			}
		});
	}
	
	public void addContactList(List<JSONObject> list) {
		m_listPullItems.onRefreshComplete();
		if( list.size() < 1 )
		{
			m_listPullItems.setMode(Mode.DISABLED);
		}
		else
		{
			m_nPageNum++;
			m_adapterContactList.addItemList(list);
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
				getMoreContactList();
			}
		});
				
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long arg3) {	
				gotoHistoryPage(pos);
			}
		});
		
		m_listItems.setOnItemLongClickListener(new OnItemLongClickListener() {
			@Override
	        public boolean onItemLongClick(AdapterView<?> arg0, View arg1,
	                int arg2, long arg3) {
	            
				onClickDeleteContact(arg2);
	            return true;
	        }
		});
	}
	
	private void onClickDeleteContact(final int pos)
	{
		MessageUtils.showDialogYesNo(this, "您想要删除此联系人?", new OnButtonClickListener() {
			
			@Override
			public void onOkClick() {
				deleteContact(pos);
				
			}
			
			@Override
			public void onCancelClick() {
				// TODO Auto-generated method stub
				
			}
		});
		
	}
	
	private void deleteContact(final int pos)
	{
		JSONObject item = m_adapterContactList.getItem(pos - 1);	
		showLoadingProgress();
		ServerManager.deleteContact(AppContext.getUserID(), item.optInt(Const.ID, 0) + "", new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(ContactListActivity.this, result.mMessage);
					return;
				}
				
				m_adapterContactList.getData().remove(pos - 1);
				m_adapterContactList.notifyDataSetChanged();
			}
		});
	}
	
	private void gotoHistoryPage(int pos)
	{
		JSONObject item = m_adapterContactList.getItem(pos - 1);
		
		try {
			item.put(Const.CHECKFRIEND, 1);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		Bundle bundle = new Bundle();	
		bundle.putString(INTENT_EXTRA, item.toString());		
		ActivityManager.changeActivity(this, HistoryListActivity.class, bundle, false, null );
	}

	
	class ContactListAdapter extends MyListAdapter {
		public ContactListAdapter(Context context, List<JSONObject> data,
			int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_photo), 30, 30, 0, 30, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_photo), 200, 200, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_right_info), 40, 0, 30, 0, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(55));

			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_address_info), 0, 40, 0, 0, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_star), 55, 55, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_star), 20, 0, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_star)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

			// show info
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + item.optString(Const.PHOTO, ""), (ImageView)ViewHolder.get(rowView, R.id.img_photo), options);

			
			((TextView)ViewHolder.get(rowView, R.id.txt_name)).setText(item.optString(Const.USERNAME, ""));
			((TextView)ViewHolder.get(rowView, R.id.txt_star)).setText(item.optString(Const.POINT_NUM, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_address)).setText(item.optString(Const.ADDRESS, ""));			

			
		}	
	}
	
}
