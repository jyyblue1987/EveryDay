package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
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
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class MyCenterActivity extends HeaderBarActivity
{
	String			m_cameraTempPath = "";
	ImageView 		m_imgPhoto = null;
	TextView 		m_txtName = null;
	
	private static int	PICK_GALLERY_CODE = 100;
	private static int	COMMENT_REQUEST_CODE = 200;
	
	ImageView 		m_imgHard = null;
	TextView 		m_txtHard = null;
	ImageView 		m_imgStar = null;
	TextView 		m_txtStar = null;
	TextView 		m_txtAddress = null;
	TextView 		m_txtHisAddress = null;

	int [] m_field_item = {
			R.id.fragment_username,
			R.id.fragment_password,
		};
	
	PullToRefreshListView		m_listPullItems = null;
	ListView					m_listItems = null;
	HistoryListAdapter			m_adapterHistoryList = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_center);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgPhoto = (ImageView) findViewById(R.id.img_my_center_icon);
		m_txtName = (TextView) findViewById(R.id.text_my_center_name);
		m_imgHard = (ImageView) findViewById(R.id.img_my_center_hard);
		m_txtHard = (TextView) findViewById(R.id.txt_my_center_hard);
		m_imgStar = (ImageView) findViewById(R.id.img_my_center_star);
		m_txtStar = (TextView) findViewById(R.id.txt_my_center_star);
		
		m_txtAddress = (TextView) findViewById(R.id.txt_my_center_address);
		m_txtHisAddress = (TextView) findViewById(R.id.txt_my_center_hisaddress);

		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_items);
		m_listItems = m_listPullItems.getRefreshableView();
	}

	protected void initData()
	{
		super.initData();
		

		m_txtPageTitle.setText("个人中心");
//		m_btnRight.setVisibility(View.INVISIBLE);
		
		JSONObject profile = AppContext.getProfile();
		m_txtName.setText(profile.optString(Const.USERNAME, ""));
		
		m_listPullItems.setMode(Mode.PULL_FROM_END);
		
		
		getHistoryListData();
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
				gotoStageListPage(arg2);
			}
		});
	}
		
	protected void layoutControls()
	{
		super.layoutControls();
		

		LayoutUtils.setSize(findViewById(R.id.lay_empty_1),LayoutParams.MATCH_PARENT, 50, true);
		
		m_layRight.setVisibility(View.VISIBLE);
		m_btnRight.setBackgroundResource(R.drawable.profile_white_icon);
		LayoutUtils.setSize(m_btnRight, 55, 48, true);
		
		LayoutUtils.setMargin(findViewById(R.id.lay_contact_info), 0, 50, 0, 0, true);
//		LayoutUtils.setMargin(findViewById(R.id.lay_contact_item), 0, 30, 0, 30, true);

		
		LayoutUtils.setSize(findViewById(R.id.lay_my_center_1), LayoutParams.MATCH_PARENT, 300, true);

		LayoutUtils.setMargin(m_imgPhoto, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgPhoto, 200, 200, true);
		
		LayoutUtils.setMargin(m_txtName, 40, 0, 0, 0, true);
		m_txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setMargin(m_imgHard, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgHard, 55, 55, true);
		
		LayoutUtils.setMargin(m_txtHard, 20, 0, 0, 0, true);
		m_txtHard.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setMargin(m_imgStar, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgStar, 55, 55, true);
		
		LayoutUtils.setMargin(m_txtStar, 20, 0, 40, 0, true);
		m_txtStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setSize(findViewById(R.id.lay_my_center_2), LayoutParams.MATCH_PARENT, 70, true);

		LayoutUtils.setMargin(m_txtAddress, 40, 0, 0, 0, true);
		m_txtAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setMargin(m_txtHisAddress, 40, 0, 0, 0, true);
		m_txtHisAddress.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		
		LayoutUtils.setSize(findViewById(R.id.lay_empty_2),LayoutParams.MATCH_PARENT, 50, true);


	}
	
	public void getHistoryListData() {
		final List<JSONObject> list = new ArrayList<JSONObject>();
		
		showLoadingProgress();
		// get temp stage
		ServerManager.getTempStages(AppContext.getUserID(), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				if( result.mResult == LogicResult.RESULT_OK )
				{
					JSONArray tempStageArray = result.getContentArray();
					if( tempStageArray != null && tempStageArray.length() > 0 )
						list.add(tempStageArray.optJSONObject(0));
					else
						list.add(new JSONObject());
				}
				else
					list.add(new JSONObject());
				ServerManager.getOwnHistory(AppContext.getUserID(), 0, new ResultCallBack() {
					
					@Override
					public void doAction(LogicResult result) {
						hideProgress();
						showHistoryListData(list);
					}
				});
				
			}
		});
		
	
		
	}
	
	private void showHistoryListData(List<JSONObject> list)
	{
		if( list.size() < 1 )
		{
			m_listItems.setVisibility(View.GONE);
		}
		else
		{
			m_listItems.setVisibility(View.VISIBLE);

			m_adapterHistoryList = new HistoryListAdapter(this, list, R.layout.fragment_list_history_item, null);
			
			m_listItems.setAdapter(m_adapterHistoryList);	
		}
	}
	
	private void gotoStageListPage(int pos)
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, StageListActivity.class, bundle, false, null );			
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
	
	private void uploadStage()
	{
		m_cameraTempPath = Environment.getExternalStorageDirectory() + "/";
		m_cameraTempPath += "camera_temp.jpg";

		MediaUtils.showCameraGalleryPage(this, PICK_GALLERY_CODE, m_cameraTempPath);
	}
	
	private void processFile(String path)
	{
		Bundle bundle = new Bundle();
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
			
			processFile(picturePath);
		}
		
		if (requestCode == PICK_GALLERY_CODE ) {
			processFile(m_cameraTempPath);
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
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setTextSize(TypedValue.COMPLEX_UNIT_PX, 30);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_delete_history), 140, 60, true);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_camera_icon), 200, 200, true);
			
			
			if( position == 1 )
				ViewHolder.get(rowView, R.id.img_camera_icon).setVisibility(View.GONE);
			else
				ViewHolder.get(rowView, R.id.img_camera_icon).setVisibility(View.VISIBLE);
			
			if( item.has("thumb_url") )
			{
				ViewHolder.get(rowView, R.id.txt_history).setVisibility(View.VISIBLE);
				ViewHolder.get(rowView, R.id.img_delete_history).setVisibility(View.VISIBLE);
				ViewHolder.get(rowView, R.id.lay_comment_like).setVisibility(View.VISIBLE);
			}
			else
			{
				ViewHolder.get(rowView, R.id.txt_history).setVisibility(View.INVISIBLE);
				ViewHolder.get(rowView, R.id.img_delete_history).setVisibility(View.INVISIBLE);
				ViewHolder.get(rowView, R.id.lay_comment_like).setVisibility(View.INVISIBLE);
			}
			
			// show data

			// events
			ViewHolder.get(rowView, R.id.img_camera_icon).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					uploadStage();					
				}
			});			
		}	
	}
	
	
	 
}