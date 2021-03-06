package com.sin.quian.pages;

import org.json.JSONException;
import org.json.JSONObject;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.network.ServerManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import common.design.layout.LayoutUtils;
import common.image.load.ImageUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MessageUtils;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class CommentDetailActivity extends HeaderBarActivity
{
	EditText		m_editContent = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_comment_detail);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_editContent = (EditText) findViewById(R.id.edit_content);
	}

	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setPadding(m_editContent, 60, 100, 60, 100, true);
		m_editContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
	
	}
	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("评论");
		
		m_layRight.setVisibility(View.VISIBLE);
		m_btnRight.setBackgroundResource(R.drawable.complete_icon);
		LayoutUtils.setSize(m_btnRight, 55, 48, true);
		
	}
	
	protected void showLabels()
	{
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.Review);
	}
	
	
	private void uploadVideoThumbnail(String path, String name)
	{
		String thumbPath = Environment.getExternalStorageDirectory() + "/" + name + ".jpg";
		
		ImageUtils.createThumbnail(path, thumbPath);
		
		ServerManager.uploadThumbnail(thumbPath, AppContext.getUserID(), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(CommentDetailActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				
				Intent intent = new Intent();
		    	intent.putExtra(INTENT_EXTRA, m_editContent.getText().toString());	
		        setResult(Activity.RESULT_OK, intent);    
		        
		        onFinishActivity();		
			}
		});
	}

	private void uploadStage(final String path)
	{
		ServerManager.uploadStage(path, AppContext.getUserID(), m_editContent.getText().toString(), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				if( result.mResult != LogicResult.RESULT_OK )
				{
					hideProgress();
					MessageUtils.showMessageDialog(CommentDetailActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				
				if( MediaUtils.isVideoFile(path) == false )
				{
					hideProgress();
					
				 	Intent intent = new Intent();
			    	intent.putExtra(INTENT_EXTRA, m_editContent.getText().toString());	
			        setResult(Activity.RESULT_OK, intent);
			        
			        onFinishActivity();
			        return;
				}
					
				uploadVideoThumbnail(path, result.getData().optString(Const.CONTENT, ""));				
			}
		});
		
	}
	protected void gotoNextPage()
	{
		showLoadingProgress();
		
		Bundle bundle = getIntent().getExtras();
		
		if( bundle != null )
		{
			String data = bundle.getString(INTENT_EXTRA, "");
			try {
				JSONObject comment = new JSONObject(data);
				
				int mode = comment.optInt(Const.MODE, 0);
				String path = comment.optString(Const.FILE_PATH, "");
				if( mode == 0 )	// add stage
				{
					uploadStage(path);
				}
				else
				{
					ServerManager.addComment(AppContext.getUserID(), comment.optString(Const.ID, "0"), m_editContent.getText().toString(), new ResultCallBack() {
						
						@Override
						public void doAction(LogicResult result) {
							hideProgress();
							if( result.mResult != LogicResult.RESULT_OK )
							{
								MessageUtils.showMessageDialog(CommentDetailActivity.this, EveryDayUtils.getMessage(result.mMessage));
								return;
							}
							
							Intent intent = new Intent();
					    	intent.putExtra(INTENT_EXTRA, m_editContent.getText().toString());	
					        setResult(Activity.RESULT_OK, intent);
					        
					        onFinishActivity();
					        											
						}
					});					
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
		
	 
	}	 
}