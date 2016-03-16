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
import common.library.utils.MessageUtils;
import common.library.utils.MyTime;
import common.library.utils.MessageUtils.OnButtonClickListener;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class MyCenterActivity extends BottomBarActivity
{
	String			m_cameraTempPath = "";
	String			m_zoomTempPath = "";
	ImageView 		m_imgLevel = null;
	ImageView 		m_imgPhoto = null;
	TextView 		m_txtName = null;
	
	private static int	PICK_GALLERY_CODE = 100;
	private static int	PHOTO_ZOOM_CODE = 150;
	
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

		m_imgLevel = (ImageView) findViewById(R.id.img_level);
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
		
		m_layRight.setVisibility(View.VISIBLE);		
		m_btnRight.setBackgroundResource(R.drawable.setting_icon);
		LayoutUtils.setSize(m_btnRight, 56, 56, true);
		
		LayoutUtils.setMargin(findViewById(R.id.lay_user_info), 30, 30, 30, 0, true);
		LayoutUtils.setPadding(findViewById(R.id.lay_user_info), 40, 20, 40, 20, true);
		
		LayoutUtils.setSize(m_imgLevel, 200, 200, true);
		LayoutUtils.setSize(m_imgPhoto, 140, 140, true);
		
		LayoutUtils.setMargin(findViewById(R.id.lay_right_info), 40, 0, 0, 0, true);
		
		m_txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(55));

		LayoutUtils.setMargin(m_imgStar, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgStar, 55, 55, true);
		
		LayoutUtils.setMargin(m_txtStar, 20, 0, 0, 0, true);
		m_txtStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setMargin(findViewById(R.id.lay_address_info), 0, 20, 0, 0, true);
		m_txtAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		
		LayoutUtils.setMargin(m_listItems, 0, 30, 0, 0, true);
		m_listItems.setDivider(getResources().getDrawable(R.drawable.devider_line));
		m_listItems.setDividerHeight(ScreenAdapter.computeWidth(3));
		
		m_txtEmptyView.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		LayoutUtils.setPadding(m_txtEmptyView, 0, 0, 0, ScreenAdapter.getDeviceHeight() / 5, false);
	}
	
	protected void initData()
	{
		super.initData();

		m_txtPageTitle.setText("个人中心");
		
		JSONObject profile = AppContext.getProfile();
		showProfile(profile);

		getHistoryList();
	}
	
	protected void showLabels()
	{
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.PersonalCenter);
		
		showProfile(AppContext.getProfile());
		AppContext.refreshProfile();
	}
	
	private void showProfile(JSONObject profile)
	{
		m_txtName.setText(EveryDayUtils.getName(profile));
		m_txtStar.setText(profile.optString(Const.POINT_NUM, "0"));
		m_txtAddress.setText(profile.optString(Const.ADDRESS, ""));
		
		m_imgLevel.setBackgroundResource(EveryDayUtils.getLevelImage(profile));
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

			m_adapterHistoryList = new HistoryListAdapter(this, list, R.layout.fragment_list_myhistory_item, null);
			
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
		m_zoomTempPath = Environment.getExternalStorageDirectory() + "/";
		m_zoomTempPath += "zoom_temp.jpg";
		
		EveryDayUtils.showCameraGalleryPage(this, PICK_GALLERY_CODE, m_cameraTempPath);
	}
	
	private void onRemoveHistory(final int pos)
	{
		MessageUtils.showDialogYesNo(this, "您想要删除这段标题?", new OnButtonClickListener() {
			
			@Override
			public void onOkClick() {
				removeHistory(pos);				
			}
			
			@Override
			public void onCancelClick() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	
	private void removeHistory(final int pos)
	{
		JSONObject item = m_adapterHistoryList.getItem(pos);
		if( item == null )
			return;
		
		showLoadingProgress();
		ServerManager.deleteHistory(AppContext.getUserID(), item.optInt(Const.ID, 0) + "", new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(MyCenterActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				
				
				m_adapterHistoryList.getData().remove(pos);
				m_adapterHistoryList.notifyDataSetChanged();				
			}
		});
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
		
		if( requestCode == PICK_GALLERY_CODE + 1 )
		{
			Uri selectedImage = data.getData();			
			String picturePath = MediaUtils.getPathFromURI(this, selectedImage);
			
			MediaUtils.startPhotoZoom(this, picturePath, m_zoomTempPath, 300, 1.5f, PHOTO_ZOOM_CODE);			
		}
		
		if (requestCode == PICK_GALLERY_CODE + 3 ) {
			Uri selectedImage = data.getData();			
			String picturePath = MediaUtils.getPathFromURI(this, selectedImage);
			
			processFile(picturePath);
		}	

		if (requestCode == PICK_GALLERY_CODE ) {
			MediaUtils.startPhotoZoom(this, m_cameraTempPath + ".jpg", m_zoomTempPath, 300, 1.5f, PHOTO_ZOOM_CODE);
		}	
		
		if (requestCode == PICK_GALLERY_CODE + 2 ) { 
			processFile(m_cameraTempPath + ".mp4");
		}	
		
		if( requestCode == PHOTO_ZOOM_CODE )
		{
			processFile(m_zoomTempPath);
		}
		
		if (requestCode == COMMENT_REQUEST_CODE ) {
			getHistoryList();
		}	
		
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
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_time), 30, 20, 0, 30, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 40);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_camera), 0, 30, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_camera), 80, 70, true);
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_name_gallery), 30, 20, 30, 30, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_title)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 57);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_delete), 50, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_delete), 50, 60, true);			
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.lay_stage_list), 0, 0, 0, 0, true);
			
			int [] lay_gallery_array = {
					R.id.lay_gallery_1,
					R.id.lay_gallery_2,
					R.id.lay_gallery_3,
					R.id.lay_gallery_4
			};
			
			int [] img_gallery_array = {
					R.id.img_gallery_1,
					R.id.img_gallery_2,
					R.id.img_gallery_3,
					R.id.img_gallery_4
			};
			
			for(int i = 0; i < img_gallery_array.length; i++)
			{
				LayoutUtils.setSize(ViewHolder.get(rowView, lay_gallery_array[i]), 150, 150, true);	
				if( i > 0 )
					LayoutUtils.setMargin(ViewHolder.get(rowView, lay_gallery_array[i]), 20, 0, 0, 0, true);
			}
			
			
			// show list data
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(EveryDayUtils.getDate(item));
			if( position > 0 )
			{
				ViewHolder.get(rowView, R.id.img_camera).setVisibility(View.INVISIBLE);
				ViewHolder.get(rowView, R.id.img_delete).setVisibility(View.VISIBLE);
			}
			else
			{
				ViewHolder.get(rowView, R.id.img_camera).setVisibility(View.VISIBLE);
				ViewHolder.get(rowView, R.id.img_delete).setVisibility(View.INVISIBLE);
			}
			
			((TextView)ViewHolder.get(rowView, R.id.txt_title)).setText(item.optString(Const.TITLE));
			
			JSONArray thumbnail = item.optJSONArray(Const.THUMBNAIL);
			for(int i = 0; i < thumbnail.length(); i++)
			{
				DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
				ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + MediaUtils.getThumnail(thumbnail.optString(i, "")), (ImageView)ViewHolder.get(rowView, img_gallery_array[i]), options);				
			}
			
			// events
			ViewHolder.get(rowView, R.id.img_camera).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					uploadStage();					
				}
			});			
			
			final int pos = position;			
			ViewHolder.get(rowView, R.id.img_delete).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onRemoveHistory(pos);			
				}
			});	
		}	
	}
	
	
	 
}