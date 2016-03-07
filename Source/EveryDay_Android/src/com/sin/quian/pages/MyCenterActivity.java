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
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import common.library.utils.AlgorithmUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class MyCenterActivity extends BottomBarActivity
{
	String			m_cameraTempPath = "";
	ImageView 		m_imgPhoto = null;
	TextView 		m_txtName = null;
	
	private static int	PICK_GALLERY_CODE = 100;
	private static int	COMMENT_REQUEST_CODE = 200;
	private static int	STAGE_LIST_CODE = 201;
	private static int	PROFILE_CHANGE_CODE = 202;
	
	ImageView 		m_imgStar = null;
	TextView 		m_txtStar = null;
	TextView 		m_txtAddress = null;

	PullToRefreshListView		m_listPullItems = null;
	ListView					m_listItems = null;
	TextView					m_txtEmptyView = null;
	
	HistoryListAdapter			m_adapterHistoryList = null;
	int							m_nPageNum = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_history);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgPhoto = (ImageView) findViewById(R.id.img_photo);
		m_txtName = (TextView) findViewById(R.id.txt_name);
		m_imgStar = (ImageView) findViewById(R.id.img_star);
		m_txtStar = (TextView) findViewById(R.id.txt_star);
		
		m_txtAddress = (TextView) findViewById(R.id.txt_address);
		
		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_items);
		m_listItems = m_listPullItems.getRefreshableView();
		m_txtEmptyView = (TextView) findViewById(R.id.txt_empty_view);
	}

	protected void layoutControls()
	{
		super.layoutControls();
		
		m_listItems.setDivider(getResources().getDrawable(R.drawable.devider_line));
		m_listItems.setDividerHeight(ScreenAdapter.computeWidth(3));
		
		m_txtEmptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		LayoutUtils.setPadding(m_txtEmptyView, 0, 0, 0, ScreenAdapter.getDeviceHeight() / 5, false);
		
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
	}
	
	protected void initData()
	{
		super.initData();

		m_txtPageTitle.setText("个人中心");
		
		JSONObject profile = AppContext.getProfile();
		showProfile(profile);

		getHistoryList();
	}
	
	private void showProfile(JSONObject profile)
	{
		m_txtName.setText(EveryDayUtils.getName(profile));
		m_txtStar.setText(profile.optString(Const.SEND_NUM, "0"));
		m_txtAddress.setText(profile.optString(Const.ADDRESS, ""));
		
		DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
		ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + profile.optString(Const.PHOTO, ""), m_imgPhoto, options);
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
	}
	
	private void onClickProfile()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(MyCenterActivity.this, ProfileActivity.class, bundle, false, null );		
	}

		
	
	public void getHistoryList() {
		m_listPullItems.setMode(Mode.PULL_FROM_END);		
		m_nPageNum = 0;
		
		showLoadingProgress();

		ServerManager.getOwnHistory(AppContext.getUserID(), m_nPageNum, new ResultCallBack() {
			
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
			m_listPullItems.setMode(Mode.DISABLED);
		}
		else
		{
			m_listItems.setVisibility(View.VISIBLE);

			m_adapterHistoryList = new HistoryListAdapter(this, list, R.layout.fragment_list_history_item, null);
			
			m_listItems.setAdapter(m_adapterHistoryList);	
		}
	}
	
	public void getMoreHistoryList() {
		ServerManager.getOwnHistory(AppContext.getUserID(), (m_nPageNum + 1), new ResultCallBack() {
			
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
			if( pos <= 1 )
				param.put(Const.MODE, Const.TEMP_STAGE_MODE);
			else
			{
				JSONObject item = m_adapterHistoryList.getItem(pos - 1);
				param.put(Const.MODE, Const.SELF_STAGE_MODE);
				AlgorithmUtils.bindJSONObject(param, item);
			}
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
		ActivityManager.changeActivity(this, ProfileActivity.class, bundle, false, PROFILE_CHANGE_CODE );
	}
	
	private void uploadStage()
	{
		m_cameraTempPath = Environment.getExternalStorageDirectory() + "/";
		m_cameraTempPath += "camera_temp";

		MediaUtils.showCameraGalleryPage(this, PICK_GALLERY_CODE, m_cameraTempPath);
	}
	
	private void processFile(String path)
	{
		Bundle bundle = new Bundle();
		
		JSONObject data = new JSONObject();
		
		try {
			data.put(Const.MODE, 0);
			data.put(Const.FILE_PATH, path);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		bundle.putString(INTENT_EXTRA, data.toString());
		ActivityManager.changeActivity(this, CommentDetailActivity.class, bundle, false, COMMENT_REQUEST_CODE);	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0)
			return;		
		
		m_cameraTempPath = Environment.getExternalStorageDirectory() + "/";
		m_cameraTempPath += "camera_temp";
		
		if( requestCode == PICK_GALLERY_CODE + 1 )
		{
			Uri selectedImage = data.getData();			
			String picturePath = MediaUtils.getPathFromURI(this, selectedImage);
			
			processFile(picturePath);
		}
		
		if (requestCode == PICK_GALLERY_CODE + 3 ) {
			Uri selectedImage = data.getData();			
			String picturePath = MediaUtils.getPathFromURI(this, selectedImage);
			
			processFile(picturePath);
		}	

		if (requestCode == PICK_GALLERY_CODE ) {
			processFile(m_cameraTempPath + ".jpg");
		}	
		
		if (requestCode == PICK_GALLERY_CODE + 2 ) { 
			processFile(m_cameraTempPath + ".mp4");
		}	
		
		if (requestCode == COMMENT_REQUEST_CODE ) {
			getHistoryList();
		}	
		
		if (requestCode == STAGE_LIST_CODE ) {
			getHistoryList();
		}	

		if (requestCode == PROFILE_CHANGE_CODE ) {
			JSONObject profile = AppContext.getProfile();
			showProfile(profile);
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
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_history_info), 30, 30, 0, 30, true);
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
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_history_content), 30, 30, 30, 30, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_history_preview), LayoutParams.MATCH_PARENT, 500, true);
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_delete_history), 140, 60, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_camera_icon), 200, 200, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_video_icon), 200, 200, true);
			
			if( position == 0 )
			{
				ViewHolder.get(rowView, R.id.img_camera_icon).setVisibility(View.VISIBLE);
				ViewHolder.get(rowView, R.id.img_delete_history).setVisibility(View.GONE);
				ViewHolder.get(rowView, R.id.lay_comment_like).setVisibility(View.GONE);
			}
			else
			{
				ViewHolder.get(rowView, R.id.img_camera_icon).setVisibility(View.GONE);
//				ViewHolder.get(rowView, R.id.img_delete_history).setVisibility(View.VISIBLE);
				ViewHolder.get(rowView, R.id.lay_comment_like).setVisibility(View.VISIBLE);
			}
			
			// show data
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + MediaUtils.getThumnail(item.optString(Const.THUMBNAIL, "")), (ImageView)ViewHolder.get(rowView, R.id.img_history_preview), options);
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setText(item.optString(Const.CONTENT, ""));
			
			String time = item.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
			String date = MyTime.getOnlyMonthDate(time) + "\n" + MyTime.getOnlyYear(time);
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(date);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_comment_count)).setText(item.optString(Const.COMMENT_COUNT, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_like_count)).setText(item.optString(Const.LIKE_COUNT, "0"));
						
			if( MediaUtils.isVideoFile(item.optString(Const.THUMBNAIL, "")) == false || position == 0)
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.GONE);
			else
				ViewHolder.get(rowView, R.id.img_video_icon).setVisibility(View.VISIBLE);
			
			// events
			ViewHolder.get(rowView, R.id.img_camera_icon).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					uploadStage();					
				}
			});			
			
			
			ViewHolder.get(rowView, R.id.img_delete_history).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
								
				}
			});	
		}	
	}
	
	
	 
}