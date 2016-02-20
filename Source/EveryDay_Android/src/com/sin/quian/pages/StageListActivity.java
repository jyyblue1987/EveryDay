package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerTask;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.image.load.ImageUtils;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyPagerAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;


public class StageListActivity extends HeaderBarActivity
{
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
	
	Button		m_btnPublish = null;
	
	int m_nMode = Const.TEMP_STAGE_MODE;
	List<JSONObject> m_stageList = null;
	
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
		LayoutUtils.setPadding(m_txtComment, 10, 10, 10, 10, true);
		
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
		
		LayoutUtils.setSize(m_btnPublish, 460, 80, true);
		m_btnPublish.setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
		
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
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		if( m_nMode == Const.TEMP_STAGE_MODE ) // self temp stage mode 
		{
			findViewById(R.id.lay_input_action).setVisibility(View.GONE);
			findViewById(R.id.lay_count).setVisibility(View.GONE);
			
			JSONArray tempStageArray = AppContext.getTempStageArray();
			List<JSONObject> list = new ArrayList<JSONObject>();
			for(int i = 0; i < tempStageArray.length(); i++ )
				list.add(tempStageArray.optJSONObject(i));
			
			m_stageList = list;
			showStageList(list);
		} 
		else if( m_nMode == Const.SELF_STAGE_MODE ) // self published stage mode
		{
			findViewById(R.id.lay_input_action).setVisibility(View.GONE);
			findViewById(R.id.lay_count).setVisibility(View.GONE);
			m_btnPublish.setVisibility(View.GONE);
		}
		

		
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
		JSONObject item = m_stageList.get(m_nCurrentPage);
		m_txtComment.setText(item.optString(Const.CONTENT, ""));
		
		String time = item.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
		String date = MyTime.getChinaDate(time);
		m_txtPageTitle.setText(date);
		m_txtSubTitle.setText((num + 1) + "/" + m_stageList.size());
	}
	protected void gotoNextPage()
	{
		gotoCommentListPage();
	}
	
	private void gotoCommentListPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, CommentListActivity.class, bundle, false, null );
	}
	
	
	class PhotoPagerAdapter extends MyPagerAdapter {
	    public PhotoPagerAdapter(Context context, List<JSONObject> data, ItemCallBack callback) {	        
	        super(context, data, callback);
	    }

		@Override
		protected View loadItemViews(int position)
		{
			ImageView view = new ImageView(m_context);
//	    	view.setScaleType(ScaleType.FIT_XY);
	    	
	    	JSONObject item = getItem(position);
	    	
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + item.optString(Const.THUMBNAIL, ""), view);
			
			return view;
		}  
	
	}
}
