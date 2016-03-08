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
import android.text.InputType;
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
import common.image.load.ImageUtils;
import common.library.utils.AlgorithmUtils;
import common.library.utils.CheckUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MessageUtils;
import common.library.utils.MessageUtils.OnButtonClickListener;
import common.library.utils.MyTime;
import common.library.utils.OnAlertClickListener;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class HistoryListActivity extends HeaderBarActivity
{
	private static int	STAGE_LIST_CODE = 201;
	
	String			m_cameraTempPath = "";
	
	ImageView 		m_imgPhoto = null;
	TextView 		m_txtName = null;	
	
	ImageView 		m_imgStar = null;
	TextView 		m_txtStar = null;
	TextView 		m_txtAddress = null;
	
	ImageView 		m_imgAddContact = null;
	ImageView 		m_imgMyReceive = null;
	TextView 		m_txtMyReceiveCount = null;
	ImageView 		m_imgMyPoint = null;
	TextView 		m_txtMyPointCount = null;



	PullToRefreshListView		m_listPullItems = null;
	ListView					m_listItems = null;	
	TextView					m_txtEmptyView = null;
	
	HistoryListAdapter			m_adapterHistoryList = null;
	int							m_nPageNum = 0;
	
	JSONObject					m_profile = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_user_history);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_txtEmptyView = (TextView) findViewById(R.id.txt_empty_view);
		
		m_imgPhoto = (ImageView) findViewById(R.id.img_photo);
		m_txtName = (TextView) findViewById(R.id.txt_name);
		m_imgStar = (ImageView) findViewById(R.id.img_star);
		m_txtStar = (TextView) findViewById(R.id.txt_star);
		
		m_txtAddress = (TextView) findViewById(R.id.txt_address);
		
		m_imgAddContact = (ImageView) findViewById(R.id.img_add_contact);
		m_imgMyPoint = (ImageView) findViewById(R.id.img_my_point);
		m_txtMyPointCount = (TextView) findViewById(R.id.txt_my_point);
		m_imgMyReceive = (ImageView) findViewById(R.id.img_my_receive_point);
		m_txtMyReceiveCount = (TextView) findViewById(R.id.txt_my_receive_point);

		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_items);
		m_listItems = m_listPullItems.getRefreshableView();
	}
	
	protected void layoutControls()
	{
		super.layoutControls();

		m_layRight.setVisibility(View.INVISIBLE);
		
		m_txtEmptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		LayoutUtils.setPadding(m_txtEmptyView, 0, 0, 0, ScreenAdapter.getDeviceHeight() / 5, false);
		
		m_listItems.setDivider(getResources().getDrawable(R.drawable.devider_line));
		m_listItems.setDividerHeight(ScreenAdapter.computeWidth(3));

		
		LayoutUtils.setMargin(findViewById(R.id.lay_user_info), 30, 30, 30, 0, true);
		LayoutUtils.setPadding(findViewById(R.id.lay_user_info), 40, 20, 40, 20, true);
		
		LayoutUtils.setSize(m_imgPhoto, 200, 200, true);
		
		LayoutUtils.setMargin(findViewById(R.id.lay_right_info), 40, 0, 0, 0, true);
		
		m_txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(55));

		LayoutUtils.setMargin(m_imgStar, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgStar, 55, 55, true);
		
		LayoutUtils.setMargin(m_txtStar, 20, 0, 0, 0, true);
		m_txtStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setMargin(findViewById(R.id.lay_address_info), 0, 20, 0, 0, true);
		m_txtAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		
		LayoutUtils.setMargin(m_listItems, 0, 30, 0, 0, true);
				
		LayoutUtils.setPadding(findViewById(R.id.lay_my_info), 30, 30, 30, 30, true);
		
		LayoutUtils.setSize(m_imgAddContact, 80, 80, true);

		LayoutUtils.setSize(m_imgMyPoint, 55, 55, true);		
		LayoutUtils.setMargin(m_txtMyPointCount, 20, 0, 0, 0, true);
		m_txtMyPointCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(40));

		LayoutUtils.setSize(m_imgMyReceive, 55, 55, true);		
		LayoutUtils.setMargin(m_txtMyReceiveCount, 20, 0, 0, 0, true);
		m_txtMyReceiveCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(40));

	}

	protected void initData()
	{
		super.initData();

		
		Bundle bundle = getIntent().getExtras();
		
		if( bundle != null )
		{
			String data = bundle.getString(INTENT_EXTRA, "");
			try {
				m_profile = new JSONObject(data);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
		m_txtName.setText(m_profile.optString(Const.USERNAME, ""));
		m_txtMyReceiveCount.setText(m_profile.optString(Const.SEND_NUM, ""));
		
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		
		m_nPageNum = 0;
		showUserInfo();
		showMyPointInfo();
		getHistoryList();		
	}
	
	protected void showLabels()
	{
		Locale locale = LocaleFactory.getLocale();
		
		if( m_profile.optInt(Const.CHECKFRIEND, 0) == 1 )
			m_txtPageTitle.setText(locale.Friend);
		else
			m_txtPageTitle.setText(locale.StarTitle);
		
	}
	protected void initEvents()
	{ 
		super.initEvents();
		
		m_btnRight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickProfile();				
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
				getMoreHistoryList();
			}
		});
		m_listItems.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {		
				gotoStageListPage(arg2);
			}
		});
		
		m_imgMyPoint.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickSendPoint();
			}
		});
		
		m_txtMyPointCount.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				m_imgMyPoint.performClick();				
			}
		});
		
		m_imgAddContact.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickAddContact();				
			}
		});
	}
	
	private void onClickSendPoint()
	{
		EditText input = MessageUtils.showEditDialog(this, "请输入的金额点.", new OnAlertClickListener() {
			
			@Override
			public void onInputText(final String text) {
				MessageUtils.showDialogYesNo(HistoryListActivity.this, "您真的想要向该用户发送星星?", new OnButtonClickListener() {
					
					@Override
					public void onOkClick() {
						sendPoint(text);
						
					}
					
					@Override
					public void onCancelClick() {
						// TODO Auto-generated method stub
						
					}
				});				
			}
		});		
		
		input.setInputType(InputType.TYPE_CLASS_NUMBER);
	}
	
	private void sendPoint(String text)
	{
		if( CheckUtils.isEmpty(text) )
		{
			MessageUtils.showMessageDialog(this, "请输入星星数");
			return;
		}
		
		final int ammount = Integer.valueOf(text);
		
		int myPointCount = AppContext.getProfile().optInt(Const.POINT_NUM, 0);
		if( ammount > myPointCount )
		{
			MessageUtils.showMessageDialog(this, "您的输入值必须小于 " + myPointCount );
			return;
		}
		
		final int res = myPointCount - ammount;
		showLoadingProgress();
		ServerManager.sendPoint(AppContext.getUserID(), m_profile.optString(Const.ID, "0"), ammount + "", new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(HistoryListActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				try {
					AppContext.getProfile().put(Const.POINT_NUM, res);
					m_txtMyPointCount.setText(res + "");					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void onClickAddContact()
	{
		
		showLoadingProgress();
		ServerManager.addContact(AppContext.getUserID(), m_profile.optString(Const.ID, "0"), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(HistoryListActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				MessageUtils.showMessageDialog(HistoryListActivity.this, "此用户添加到您的联系人列表.");
			}
		});
	}
	private void onClickProfile()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(HistoryListActivity.this, ProfileActivity.class, bundle, false, null );		
	}

		

	
	private void showUserInfo()
	{
		m_txtName.setText(EveryDayUtils.getName(m_profile));
		m_txtStar.setText(m_profile.optString(Const.SEND_NUM, "0"));
		m_txtAddress.setText(m_profile.optString(Const.ADDRESS, ""));
		
		DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
		ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + m_profile.optString(Const.PHOTO, ""), m_imgPhoto, options);
	}
	
	private void showMyPointInfo()
	{
		if( m_profile.optInt(Const.CHECKFRIEND, 0) == 1 )
			m_imgAddContact.setVisibility(View.INVISIBLE);
		m_txtMyPointCount.setText(AppContext.getProfile().optString(Const.POINT_NUM, "0"));
		m_txtMyReceiveCount.setText(AppContext.getProfile().optString(Const.RECEIVE_NUM, "0"));
	}
	
	public void getHistoryList() {
		m_nPageNum = 0;
		
		showLoadingProgress();
	
		ServerManager.getUserHistory(AppContext.getUserID(), m_profile.optString(Const.ID, "0"), m_nPageNum, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				List<JSONObject> list = new ArrayList<JSONObject>();
				JSONArray history = result.getContentArray();
				for(int i = 0; i < history.length(); i++)
					list.add(history.optJSONObject(i));
				
				showHistoryListData(list);							
			}
		});
				
	}
	
	private void showHistoryListData(List<JSONObject> list)
	{
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

			m_adapterHistoryList = new HistoryListAdapter(this, list, R.layout.fragment_list_userhistory_item, null);
			
			m_listItems.setAdapter(m_adapterHistoryList);	
		}
	}
	
	public void getMoreHistoryList() {
		ServerManager.getUserHistory(AppContext.getUserID(), m_profile.optString(Const.ID, "0"), m_nPageNum + 1, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				
				JSONArray history = result.getContentArray();
				List<JSONObject> list = new ArrayList<JSONObject>();
				for(int i = 0; i < history.length(); i++)
					list.add(history.optJSONObject(i));
				
				addHistoryListData(list);									
			}
		});
	}
	
	private void addHistoryListData(List<JSONObject> list)
	{
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
	
	protected void gotoNextPage()
	{
		gotoProfilePage();
	}
	
	private void gotoProfilePage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, ProfileActivity.class, bundle, false, null );
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
	class HistoryListAdapter extends MyListAdapter {
		public HistoryListAdapter(Context context, List<JSONObject> data,
				int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			// layout controls
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_history_content), 30, 30, 30, 0, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_history_preview), LayoutParams.MATCH_PARENT, 500, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_video_icon), 150, 150, true);
		
			// blog info(time, point, comment count)
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_blog_info), 30, 15, 30, 25, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_star), 20, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_star), 38, 38, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_star), 5, 0, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_star)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_comment), 20, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_comment), 45, 38, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_comment), 5, 0, 0, 0, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_comment)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
			
			// show info
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + MediaUtils.getThumnail(item.optString(Const.THUMBNAIL, "")), (ImageView)ViewHolder.get(rowView, R.id.img_history_preview), options);
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setText(item.optString(Const.CONTENT, ""));
			
		
			if( MediaUtils.isVideoFile(item.optString(Const.THUMBNAIL, "")) == false )
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.GONE);
			else
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.VISIBLE);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(EveryDayUtils.getDate(item));
			
			((TextView)ViewHolder.get(rowView, R.id.txt_star)).setText(item.optString(Const.POINT_NUM, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_comment)).setText(item.optString(Const.COMMENT_COUNT, "0"));

		}	
	}
	
	
	 
}