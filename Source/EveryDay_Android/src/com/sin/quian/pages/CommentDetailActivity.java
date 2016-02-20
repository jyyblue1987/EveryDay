package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import common.design.layout.LayoutUtils;


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

	protected void initData()
	{
		super.initData();
		
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
		finishView();
	}	 
}