package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import common.design.layout.LayoutUtils;
import common.design.utils.ResourceUtils;
import common.manager.activity.ActivityManager;


public class BottomBarActivity extends HeaderBarActivity {
	private static int	BUY_POINT_CODE = 201;
	
	ImageView 		m_imgAdvertise = null;
	ImageView 		m_imgHome = null;
	ImageView 		m_imgBuyIcon = null;
	
	protected void findViews()
	{
		super.findViews();
		m_imgAdvertise = (ImageView) findViewById(R.id.fragment_bottom).findViewById(R.id.img_advertise);
		m_imgHome = (ImageView) findViewById(R.id.fragment_bottom).findViewById(R.id.img_home);
		m_imgBuyIcon = (ImageView) findViewById(R.id.fragment_bottom).findViewById(R.id.img_buy_point);

	}
	protected void layoutControls()
	{
		super.layoutControls();
		LayoutUtils.setSize(findViewById(R.id.fragment_bottom).findViewById(R.id.lay_main_bottom), LayoutParams.MATCH_PARENT, 140, true);
		
		LayoutUtils.setSize(m_imgAdvertise, 87, 69, true);
		
		LayoutUtils.setMargin(m_imgHome, 180, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgHome, 95, 95, true);
		
		LayoutUtils.setMargin(m_imgBuyIcon, 180, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgBuyIcon, 80, 80, true);		
	}
	
	protected void initEvents()
	{
		super.initEvents();		
		
		ResourceUtils.addClickEffect(m_imgAdvertise);
		m_imgAdvertise.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickAdvertise();
				
			}
		});
		
		ResourceUtils.addClickEffect(m_imgBuyIcon);
		m_imgBuyIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickBuy();				
			}
		});
		
		ResourceUtils.addClickEffect(m_imgHome);
		m_imgHome.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickHome();
			}
		});
	}
	
	protected void onClickAdvertise(){
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, AdvertiseActivity.class, bundle, false, BUY_POINT_CODE );		
	}
	
	protected void onClickBuy(){
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, BuyActivity.class, bundle, false, BUY_POINT_CODE );		
	}
	
	protected void onClickHome(){
		ActivityManager.getInstance().popAllActivityExceptOne(MainMenuActivity.class);
	}
}
