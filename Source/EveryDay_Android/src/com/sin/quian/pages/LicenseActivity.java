package com.sin.quian.pages;

import com.sin.quian.R;

import android.graphics.Paint.Align;
import android.os.Bundle;
import android.util.TypedValue;
import android.widget.TextView;
import common.component.ui.JustifiedTextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;


public class LicenseActivity extends HeaderBarActivity
{
	TextView				m_txtTitle = null;
	TextView			m_txtContent = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_license);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_txtTitle = (TextView)findViewById(R.id.txt_title);
		m_txtContent = (TextView) findViewById(R.id.txt_license);
	}

	protected void layoutControls()
	{
		super.layoutControls();
		
		m_txtTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(m_txtContent, 0, 20, 0, 0,  true);
		m_txtContent.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
	}
	
	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("用户服务协议");
		
		String license = getResources().getString(R.string.license);
		String license1 = getResources().getString(R.string.license1);
		String license2 = getResources().getString(R.string.license2);
		
		m_txtContent.setText(license + "\r\n " + license1 + "\r\n " + license2);
	}
	
}