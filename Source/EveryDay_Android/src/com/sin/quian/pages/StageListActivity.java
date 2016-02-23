package com.sin.quian.pages;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.text.AndroidCharacter;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.image.load.ImageUtils;
import common.library.utils.AlgorithmUtils;
import common.library.utils.AndroidUtils;
import common.library.utils.MessageUtils;
import common.library.utils.MessageUtils.OnButtonClickListener;
import common.library.utils.MyTime;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class StageListActivity extends HeaderBarActivity
{
	private static int	COMMENT_REQUEST_CODE = 200;
	
	PullToRefreshListView		m_listPullItems = null;
	ListView					m_listItems = null;
	HistoryListAdapter			m_adapterHistoryList = null;
	int							m_nPageNum = 0;
	
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
	
	@Override	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_stage_page);
		loadComponents();
	}	
	
	protected void findViews()
	{
		super.findViews();
		
		m_listPullItems = (PullToRefreshListView)findViewById(R.id.list_items);
		m_listItems = m_listPullItems.getRefreshableView();

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
		
		m_layRight.setVisibility(View.VISIBLE);
		m_btnRight.setBackgroundResource(R.drawable.comment_list);
		LayoutUtils.setSize(m_btnRight, 55, 48, true);
		
		int iconsize = 60;
		int fontsize = 40;
		int padding = 20;
		
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
		
		m_txtPageTitle.setText(AppContext.getProfile().optString(Const.USERNAME, ""));
		
		m_listPullItems.setMode(Mode.DISABLED);
		
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
		
		if( list.size() < 1 )
			m_btnPublish.setVisibility(View.GONE);
		
		showStageList(list);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
		
		
		m_btnPublish.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MessageUtils.showDialogYesNo(StageListActivity.this, "你想要发布这吗?", new OnButtonClickListener() {
					
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
			MessageUtils.showMessageDialog(this, "你已经很喜欢这段历史.");
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
					m_txtLike.setText("很喜欢");
					
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
	
	private void onRemoveStage(final int pos)
	{
		MessageUtils.showDialogYesNo(this, "Do you want to delete this stage?", new OnButtonClickListener() {
			
			@Override
			public void onOkClick() {
				removeStage(pos);				
			}
			
			@Override
			public void onCancelClick() {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	private void removeStage(final int pos)
	{
		if( m_nMode == Const.TEMP_STAGE_MODE )
		{
			JSONObject item = m_adapterHistoryList.getItem(pos);
			if( item == null )
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
					
					
					m_adapterHistoryList.getData().remove(pos);
					m_adapterHistoryList.notifyDataSetChanged();
					
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
		if( list.size() < 1 )
		{
			m_listPullItems.setMode(Mode.DISABLED);
		}
		else
		{
			m_listItems.setVisibility(View.VISIBLE);

			m_adapterHistoryList = new HistoryListAdapter(this, list, R.layout.fragment_list_history_item, null);
			
			m_listItems.setAdapter(m_adapterHistoryList);	
		}
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
			
	private void gotoGalleryDetailPage(JSONObject item)
	{
		String url = ServerTask.SERVER_UPLOAD_PATH + item.optString(Const.THUMBNAIL, "");
		File file = ImageLoader.getInstance().getDiskCache().get(url);
		
		String extension = url.substring(url.lastIndexOf(".")).toUpperCase();
		String path = file.getAbsolutePath();
		
        AndroidUtils.showImageInGallery(this, path, extension);
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
			
			ViewHolder.get(rowView, R.id.img_camera_icon).setVisibility(View.GONE);
			
			((TextView)ViewHolder.get(rowView, R.id.img_delete_history)).setTextSize(TypedValue.COMPLEX_UNIT_PX, fontsize);
			if( m_nMode != Const.TEMP_STAGE_MODE )
				ViewHolder.get(rowView, R.id.img_delete_history).setVisibility(View.GONE);
			else
			{
				final int pos = position;
				ViewHolder.get(rowView, R.id.img_delete_history).setVisibility(View.VISIBLE);
				ViewHolder.get(rowView, R.id.img_delete_history).setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View v) {
						onRemoveStage(pos);
					}
				});
			}
			
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
			ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PATH + item.optString(Const.THUMBNAIL, ""), (ImageView)ViewHolder.get(rowView, R.id.img_history_preview), options);
			((TextView)ViewHolder.get(rowView, R.id.txt_history)).setText(item.optString(Const.CONTENT, ""));
			
			String time = item.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
			String date = MyTime.getOnlyMonthDate(time) + "\n" + MyTime.getOnlyYear(time);
			((TextView)ViewHolder.get(rowView, R.id.txt_time)).setText(date);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_comment_count)).setText(item.optString(Const.COMMENT_COUNT, "0"));
			((TextView)ViewHolder.get(rowView, R.id.txt_like_count)).setText(item.optString(Const.LIKE_COUNT, "0"));
			
			ViewHolder.get(rowView, R.id.img_history_preview).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					gotoGalleryDetailPage(item);					
				}
			});
		}	
	}
}
