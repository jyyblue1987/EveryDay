package com.sin.quian.pages;

import org.json.JSONException;
import org.json.JSONObject;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import common.design.layout.LayoutUtils;
import common.library.utils.MessageUtils;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class CommentDetailActivity extends HeaderBarActivity
{
	EditText		m_editContent = null;
	String			m_path = "";
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

	protected void initData()
	{
		super.initData();
		
		Bundle bundle = getIntent().getExtras();
		
		if( bundle != null )
		{
			String data = bundle.getString(INTENT_EXTRA, "");
			try {
				JSONObject comment = new JSONObject(data);
				m_path = comment.optString(Const.FILE_PATH, "");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		
		
		m_txtPageTitle.setText("Comment");
		
		m_layRight.setVisibility(View.VISIBLE);
		m_btnRight.setBackgroundResource(R.drawable.complete_icon);
		LayoutUtils.setSize(m_btnRight, 55, 48, true);
		
		LayoutUtils.setPadding(m_editContent, 20, 20, 20, 20, true);
		m_editContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, 45);
	}
			
	protected void layoutControls()
	{
		super.layoutControls();		
	}
	
	protected void gotoNextPage()
	{
		showLoadingProgress();
		
		ServerManager.uploadStage(m_path, AppContext.getUserID(), m_editContent.getText().toString(), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(CommentDetailActivity.this, result.mMessage);
					return;
				}
				
			 	Intent intent = new Intent();
		    	intent.putExtra(INTENT_EXTRA, m_editContent.getText().toString());	
		        setResult(Activity.RESULT_OK, intent);    
		        onFinishActivity();				
			}
		});
		
	 
	}	 
}