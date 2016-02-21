package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.design.utils.ResourceUtils;
import common.library.utils.AlgorithmUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MessageUtils;
import common.library.utils.MessageUtils.OnButtonClickListener;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyPagerAdapter;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class StageListActivity extends HeaderBarActivity
{
	private static int	COMMENT_REQUEST_CODE = 200;
	
	ViewPager 	m_photoPager = null;
	PhotoPagerAdapter	m_photoAdapter;
	
	int			m_nCurrentPage = 0;
	
	TextView	m_txtSubTitle = null;
	TextView	m_txtComment = null;
	
	ImageView	m_imgLikeIcon = null;
	TextView	m_txtLike = null;
	ImageView	m_imgCommentIcon = null;
	TextView	m_txtCommentInput = null;
	
	ImageView	m_imgLikeCountIcon = null;
	TextView	m_txtLikeCount = null;
	ImageView	m_imgCommentCountIcon = null;
	TextView	m_txtCommentCount = null;
	
	Button		m_btnRemove = null;
	Button		m_btnPublish = null;
	
	JSONObject		m_historyInfo = new JSONObject();
	int m_nMode = Const.TEMP_STAGE_MODE;
	boolean		m_bIsChanged = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_stage_page);
		loadComponents();
	}	
	
	protected void findViews()
	{
		super.findViews();
		
		m_photoPager = (ViewPager) findViewById(R.id.img_gallery_view);

		m_txtSubTitle  = (TextView) findViewById(R.id.fragment_header).findViewById(R.id.txt_navigate_sub_title);
		m_txtComment = (TextView) findViewById(R.id.txt_comment);
		m_imgLikeIcon = (ImageView) findViewById(R.id.img_like_icon);
		m_txtLike = (TextView) findViewById(R.id.txt_like);
		m_imgCommentIcon = (ImageView) findViewById(R.id.img_comment_icon);
		m_txtCommentInput = (TextView) findViewById(R.id.txt_comment_input);
	
		m_imgLikeCountIcon = (ImageView) findViewById(R.id.img_like_count_icon);
		m_txtLikeCount = (TextView) findViewById(R.id.txt_like_count);
		m_imgCommentCountIcon = (ImageView) findViewById(R.id.img_comment_count_icon);
		m_txtCommentCount = (TextView) findViewById(R.id.txt_comment_count);
		
		m_btnRemove  = (Button) findViewById(R.id.btn_remove);
		m_btnPublish = (Button) findViewById(R.id.btn_stage_publish);
	}

	protected void layoutControls()
	{
		super.layoutControls();
		
		m_txtSubTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(40));
		m_layRight.setVisibility(View.VISIBLE);
		m_btnRight.setBackgroundResource(R.drawable.comment_list);
		LayoutUtils.setSize(m_btnRight, 55, 48, true);
		
		int iconsize = 40;
		int fontsize = 25;
		int padding = 10;
		
		m_txtComment.setTextSize(TypedValue.COMPLEX_UNIT_PX, 55);
		
		LayoutUtils.setPadding(findViewById(R.id.lay_comment), 20, 20, 20, 30, true);
		LayoutUtils.setMargin(m_btnRemove, 30, 0, 0, 0, true);
		LayoutUtils.setSize(m_btnRemove, 80, 80, true);
		
		LayoutUtils.setPadding(findViewById(R.id.lay_stage_action), 20, 40, 20, 30, true);
		
		LayoutUtils.setMargin(findViewById(R.id.lay_input_action), 0, 0, padding, 0, true);
		LayoutUtils.setSize(m_imgLikeIcon, iconsize, iconsize, true);
		m_txtLike.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
		LayoutUtils.setMargin(m_txtLike, padding, 0, 0, 0, true);
		
		LayoutUtils.setMargin(findViewById(R.id.img_divider), padding, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgCommentIcon, iconsize, iconsize, true);
		LayoutUtils.setMargin(m_imgCommentIcon, padding, 0, 0, 0, true);
		m_txtCommentInput.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
		LayoutUtils.setMargin(m_txtCommentInput, padding, 0, 0, 0, true);
		
		LayoutUtils.setMargin(findViewById(R.id.lay_count), padding, padding, padding, padding, true);
		LayoutUtils.setSize(m_imgLikeCountIcon, iconsize, iconsize, true);
		m_txtLikeCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
		LayoutUtils.setMargin(m_txtLikeCount, padding, 0, 0, 0, true);
		
		LayoutUtils.setSize(m_imgCommentCountIcon, iconsize, iconsize, true);
		LayoutUtils.setMargin(m_imgCommentCountIcon, padding, 0, 0, 0, true);
		m_txtCommentCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
		LayoutUtils.setMargin(m_txtCommentCount, padding, 0, 0, 0, true);
		
		LayoutUtils.setSize(m_btnPublish, 460, 100, true);
		m_btnPublish.setTextSize(TypedValue.COMPLEX_UNIT_PX, 50);
		
	}
	protected void initData()
	{
		super.initData();
		
		Bundle bundle = getIntent().getExtras();
		
		if( bundle != null )
		{
			String data = bundle.getString(INTENT_EXTRA, "");
			try {
				JSONObject stage = new JSONObject(data);
				m_nMode = stage.optInt(Const.MODE, Const.TEMP_STAGE_MODE);
				m_historyInfo = stage;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if( m_nMode == Const.TEMP_STAGE_MODE ) // self temp stage mode 
		{
			findViewById(R.id.lay_input_action).setVisibility(View.GONE);
			findViewById(R.id.lay_count).setVisibility(View.GONE);
			
			showLoadingProgress();
			ServerManager.getTempStages(AppContext.getUserID(), new ResultCallBack() {
				
				@Override
				public void doAction(LogicResult result) {
					hideProgress();
					
					showStageList(result.getContentArray());
				}
			});
		}
		
		if( m_nMode == Const.SELF_STAGE_MODE ) // self published stage mode
		{
			findViewById(R.id.lay_input_action).setVisibility(View.GONE);
			m_btnPublish.setVisibility(View.GONE);
			m_btnRemove.setVisibility(View.GONE);
			
			m_txtLikeCount.setText(m_historyInfo.optString(Const.LIKE_COUNT, "0"));
			m_txtCommentCount.setText(m_historyInfo.optString(Const.COMMENT_COUNT, "0"));
			String hno = m_historyInfo.optString(Const.ID, "0");
			showLoadingProgress();
			ServerManager.getStages(AppContext.getUserID(), hno, new ResultCallBack() {
				
				@Override
				public void doAction(LogicResult result) {
					hideProgress();
					
					showStageList(result.getContentArray());
				}
			});
		}
		
		if( m_nMode == Const.OTHER_STAGE_MODE ) // self published stage mode
		{
			m_btnPublish.setVisibility(View.GONE);
			m_btnRemove.setVisibility(View.GONE);
			
			if( m_historyInfo.optInt(Const.FAVORITED_FLAG, 0) == 0 )
				m_txtLike.setText("Like");
			else
				m_txtLike.setText("Liked");
			
			m_txtLikeCount.setText(m_historyInfo.optString(Const.LIKE_COUNT, "0"));
			m_txtCommentCount.setText(m_historyInfo.optString(Const.COMMENT_COUNT, "0"));
			String hno = m_historyInfo.optString(Const.ID, "0");
			showLoadingProgress();
			ServerManager.getStages(AppContext.getUserID(), hno, new ResultCallBack() {
				
				@Override
				public void doAction(LogicResult result) {
					hideProgress();
					
					showStageList(result.getContentArray());
				}
			});
		}
	}
	
	private void showStageList(JSONArray array)
	{
		List<JSONObject> list = new ArrayList<JSONObject>();
		for(int i = 0; i < array.length(); i++ )
			list.add(array.optJSONObject(i));
		
		showStageList(list);
		
		if( list.size() > 0 )
			m_nCurrentPage = 0;
		else
			m_nCurrentPage = -1;
		showStageInfo(m_nCurrentPage);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
		
		ResourceUtils.addClickEffect(m_btnRemove);
		m_btnRemove.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MessageUtils.showDialogYesNo(StageListActivity.this, "Do you want to delete?", new OnButtonClickListener() {
					
					@Override
					public void onOkClick() {
						removeStage();						
					}
					
					@Override
					public void onCancelClick() {
						// TODO Auto-generated method stub
						
					}
				});
						
			}
		});
		
		m_btnPublish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MessageUtils.showDialogYesNo(StageListActivity.this, "Do you want to publish this?", new OnButtonClickListener() {
					
					@Override
					public void onOkClick() {
						addHistory();						
					}
					
					@Override
					public void onCancelClick() {
						
					}
				});
				
			}
		});
		
		m_txtCommentInput.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addComment();				
			}
		});
		
		m_txtLike.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				addLike();
			}
		});
	}
	
	private void addComment()
	{
		Bundle bundle = new Bundle();
		
		JSONObject data = new JSONObject();
		
		try {
			data.put(Const.MODE, 1);
			AlgorithmUtils.bindJSONObject(data, m_historyInfo);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		bundle.putString(INTENT_EXTRA, data.toString());
		ActivityManager.changeActivity(this, CommentDetailActivity.class, bundle, false, COMMENT_REQUEST_CODE);	
	}
	
	private void addLike()
	{
		if( m_historyInfo.optInt(Const.FAVORITED_FLAG, 0) != 0 )
		{
			MessageUtils.showMessageDialog(this, "You have already liked this history");
			return;
		}
				
		showLoadingProgress();
		ServerManager.addLike(AppContext.getUserID(), m_historyInfo.optString(Const.ID, "0"), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if(result.mResult != LogicResult.RESULT_OK)
				{
					MessageUtils.showMessageDialog(StageListActivity.this, result.mMessage);
					return;
				}
				
				try {
					m_bIsChanged = true;
					m_historyInfo.put(Const.FAVORITED_FLAG, 1);
					m_txtLike.setText("Liked");
					
					int likeCount = m_historyInfo.optInt(Const.LIKE_COUNT, 0);
					likeCount++;
					m_historyInfo.put(Const.LIKE_COUNT, likeCount);
					m_txtLikeCount.setText(likeCount + "");
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	private void removeStage()
	{
		if( m_nMode == Const.TEMP_STAGE_MODE )
		{
			JSONObject item = m_photoAdapter.getItem(m_nCurrentPage);
			if( item == null || m_nCurrentPage < 0 || m_nCurrentPage >= m_photoAdapter.getData().size() )
				return;
			showLoadingProgress();
			ServerManager.deleteTempStages(AppContext.getUserID(), item.optString(Const.ID, "0"), new ResultCallBack() {
				
				@Override
				public void doAction(LogicResult result) {
					hideProgress();
					if( result.mResult != LogicResult.RESULT_OK )
					{
						MessageUtils.showMessageDialog(StageListActivity.this, result.mMessage);
						return;
					}
					
					m_photoAdapter.getData().remove(m_nCurrentPage);
					m_photoPager.setAdapter(m_photoAdapter);
						
					if( m_nCurrentPage >= m_photoAdapter.getData().size() )
						m_nCurrentPage--;
					
					showStageInfo(m_nCurrentPage);
					
					if( m_nCurrentPage >= 0 )
						m_photoPager.setCurrentItem(m_nCurrentPage);
					
					m_bIsChanged = true;
				}
			});
		}
	}
	
	protected void gotoBackPage()
	{
		Intent intent = new Intent();
		if( m_bIsChanged == true )
			setResult(Activity.RESULT_OK, intent);
		else
			setResult(Activity.RESULT_CANCELED, intent);
        onFinishActivity();				
	}
	
	private void addHistory()
	{
		showLoadingProgress();
		ServerManager.addHistory(AppContext.getUserID(), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(StageListActivity.this, result.mMessage);
					return;
				}
				
				m_bIsChanged = true;
				gotoBackPage();
			}
		});
	}
	private void showStageList(List<JSONObject> list)
	{
		m_photoAdapter = new PhotoPagerAdapter(this, list, null);
		m_photoPager.setAdapter(m_photoAdapter);
		
		m_photoPager.setOnTouchListener(new View.OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
		        v.getParent().requestDisallowInterceptTouchEvent(true);
		        return false;
		    }
		});

		m_photoPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
		    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		    	m_photoPager.getParent().requestDisallowInterceptTouchEvent(true);
		    	m_nCurrentPage = position;
		    	showStageInfo(position);
		    }
		});
	}
	
	
	private void showStageInfo(int num)
	{
		JSONObject item = null;
		if( num >= 0 && num < m_photoAdapter.getData().size() )
			item = m_photoAdapter.getItem(num);
		
		if( num < 0 || item == null )
		{
			m_txtComment.setText("");
			m_txtPageTitle.setText(AppContext.getProfile().optString(Const.USERNAME, ""));
			m_txtSubTitle.setText("0/0");
			m_btnRemove.setVisibility(View.GONE);
			m_btnPublish.setVisibility(View.GONE);
			return;
		}
		
		m_txtComment.setText(item.optString(Const.CONTENT, ""));
//		m_btnRemove.setVisibility(View.VISIBLE);
		
		String time = item.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
		String date = MyTime.getChinaDate(time);
		m_txtPageTitle.setText(date);
		m_txtSubTitle.setText((num + 1) + "/" + m_photoAdapter.getData().size() );
	}
	protected void gotoNextPage()
	{
		if(m_nMode == Const.SELF_STAGE_MODE ||
				m_nMode == Const.OTHER_STAGE_MODE )
			gotoCommentListPage();
	}
	
	private void gotoCommentListPage()
	{
		Bundle bundle = new Bundle();		
		bundle.putString(INTENT_EXTRA, m_historyInfo.toString());
		ActivityManager.changeActivity(this, CommentListActivity.class, bundle, false, null );
	}

	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0)
			return;		
		
		if (requestCode == COMMENT_REQUEST_CODE ) {
			int commentCount = m_historyInfo.optInt(Const.COMMENT_COUNT, 0);
			commentCount++;
			m_txtCommentCount.setText(commentCount + "");
			try {
				m_historyInfo.put(Const.COMMENT_COUNT, commentCount);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			m_bIsChanged = true;			
		}	
				
		super.onActivityResult(requestCode,  resultCode, data);	
	}
				
	class PhotoPagerAdapter extends MyPagerAdapter {
	    public PhotoPagerAdapter(Context context, List<JSONObject> data, ItemCallBack callback) {	        
	        super(context, data, callback);
	    }

		@Override
		protected View loadItemViews(int position)
		{
			if( m_bValidData == true )
			{
				ImageView view = new ImageView(m_context);
//		    	view.setScaleType(ScaleType.FIT_XY);
		    	
		    	JSONObject item = getItem(position);
		    	if( item != null )		    	
		    		ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + item.optString(Const.THUMBNAIL, ""), view);
				
				return view;
			}
			else
			{
				TextView view = new TextView(m_context);
				view.setGravity(Gravity.CENTER);
				view.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(40));
				view.setText("There is no stage list\n\n\n\n\n\n\n\n");
				
				return view;
			}
		}  
	
	}
}
