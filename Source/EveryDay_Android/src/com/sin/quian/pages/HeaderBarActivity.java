package com.sin.quian.pages;

import com.sin.quian.R;

import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;


public class HeaderBarActivity extends BaseActivity {
	TextView 	m_txtPageTitle = null;
	
	RelativeLayout		m_layLeft = null;
	RelativeLayout		m_layRight = null;

	Button		m_btnLeft = null;
	Button		m_btnRight = null;
	
	TextView m_txtNotify = null;
	
	protected void findViews()
	{
		m_txtPageTitle = (TextView) findViewById(R.id.fragment_header).findViewById(R.id.txt_navigate_bar_title);
		m_layLeft = (RelativeLayout) findViewById(R.id.fragment_header).findViewById(R.id.lay_left);
		m_layRight = (RelativeLayout) findViewById(R.id.fragment_header).findViewById(R.id.lay_right);		
		m_btnLeft = (Button) findViewById(R.id.fragment_header).findViewById(R.id.btn_left_button);
		m_btnRight = (Button) findViewById(R.id.fragment_header).findViewById(R.id.btn_right_button);		
		m_txtNotify = (TextView) findViewById(R.id.fragment_header).findViewById(R.id.txt_notify_count);
	}
	protected void layoutControls()
	{
		LayoutUtils.setSize(m_layLeft, 160, 140, true);
		LayoutUtils.setSize(m_layRight, 160, 140, true);
		
		m_btnLeft.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		m_btnRight.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		LayoutUtils.setSize(m_btnLeft, 25, 45, true);
		
		LayoutUtils.setSize(m_txtNotify, 53, 53, true);
		m_txtNotify.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(44));
		LayoutUtils.setMargin(m_txtNotify, 90, 60, 0, 0, true);

		m_txtPageTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(60));
		
	}
	
	protected void initEvents()
	{
		m_layLeft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoBackPage();				
			}
		});
		
		m_layRight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoNextPage();				
			}
		});
		
		m_txtNotify.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				m_layLeft.performClick();	
			}
		});		
		
	}
	
	protected void gotoBackPage()
	{
		finishView();		
	}
	
	protected void gotoNextPage()
	{
		
	}
	
	protected void initData()
	{
	}
	
}
