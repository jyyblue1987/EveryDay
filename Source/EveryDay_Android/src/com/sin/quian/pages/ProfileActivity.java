package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import common.design.layout.LayoutUtils;


public class ProfileActivity extends HeaderBarActivity
{
	EditText		m_editContent = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profile_page);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

	}

	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("My profile");
		
	}
			
	protected void layoutControls()
	{
		super.layoutControls();		
		
		m_layRight.setVisibility(View.VISIBLE);
		m_btnRight.setBackgroundResource(R.drawable.complete_icon);
		LayoutUtils.setSize(m_btnRight, 55, 48, true);
	}
	
	protected void gotoNextPage()
	{
		finishView();
	}	 
}