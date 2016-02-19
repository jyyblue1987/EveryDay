package com.sin.quian.pages;

import org.json.JSONObject;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;


public class MyCenterActivity extends HeaderBarActivity
{
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	
	ImageView 		m_imgPhoto = null;
	TextView 		m_txtName = null;
	
	int [] m_field_item = {
			R.id.fragment_username,
			R.id.fragment_password,
		};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_my_center);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgPhoto = (ImageView) findViewById(R.id.img_contact_icon);
		m_txtName = (TextView) findViewById(R.id.txt_name);
	}

	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("Personal Center");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		JSONObject profile = AppContext.getProfile();
		m_txtName.setText(profile.optString(Const.USERNAME, ""));
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
		
		findViewById(R.id.lay_contact_info).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}
		
	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setMargin(findViewById(R.id.lay_contact_info), 0, 50, 0, 0, true);
		LayoutUtils.setMargin(findViewById(R.id.lay_contact_item), 0, 30, 0, 30, true);
		
		LayoutUtils.setMargin(m_imgPhoto, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgPhoto, 200, 200, true);
		
		LayoutUtils.setMargin(m_txtName, 40, 0, 0, 0, true);
		m_txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setSize(findViewById(R.id.img_nav_icon), 25, 40, true);
		LayoutUtils.setMargin(findViewById(R.id.img_nav_icon), 40, 0, 40, 0, true);
	}
	 
}