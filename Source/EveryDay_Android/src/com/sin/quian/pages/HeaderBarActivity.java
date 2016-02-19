package com.sin.quian.pages;

import com.sin.quian.Const;
import com.sin.quian.R;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.design.utils.ResourceUtils;


public class HeaderBarActivity extends BaseActivity {
	TextView 	m_txtPageTitle = null;
	Button		m_btnLeft = null;
	Button		m_btnRight = null;
	
	protected void findViews()
	{
		m_txtPageTitle = (TextView) findViewById(R.id.fragment_header).findViewById(R.id.txt_navigate_bar_title);
		m_btnLeft = (Button) findViewById(R.id.fragment_header).findViewById(R.id.btn_left_button);
		m_btnRight = (Button) findViewById(R.id.fragment_header).findViewById(R.id.btn_right_button);		
	}
	protected void layoutControls()
	{
		LayoutUtils.setSize(m_btnLeft, 160, 125, true);
		LayoutUtils.setSize(m_btnRight, 160, 125, true);
		ResourceUtils.addClickEffect(m_btnLeft);
		ResourceUtils.addClickEffect(m_btnRight);
		
		m_txtPageTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(54));
		
	}
	
	protected void initEvents()
	{
		m_btnLeft.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoBackPage();				
			}
		});
		
		m_btnRight.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoNextPage();				
			}
		});
		
		registerReceiver(mNotifyReceiver, new IntentFilter(Const.RECEIVE_MESSAGE_ACTION));
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
		showUnreadMessageCount();
	}
	
	private BroadcastReceiver mNotifyReceiver = new BroadcastReceiver() {
	      @Override
	      public void onReceive(Context context, Intent intent) {
	    	  showUnreadMessageCount();	    		  
	      }
	};
	
	private void showUnreadMessageCount()
	{
//		int count = DBManager.getUnreadTotalMessageCount(this, ChatController.getUsername());
//  	  	if( count < 1 )
//  	  		m_txtNotify.setVisibility(View.GONE);
//  	  	else
//  	  	{	
//  	  		m_txtNotify.setVisibility(View.VISIBLE);
//  	  		
//			if( count < 10 )
//				m_txtNotify.setText(count + "");
//			else
//				m_txtNotify.setText("!");	
//  	  	}
//  	  		
	}
	
	@Override
	public void onDestroy( ) {
		unregisterReceiver(mNotifyReceiver);
		
		super.onDestroy();
	}
	
	protected void onResume( ) {
		super.onResume();
		
		showUnreadMessageCount();
	}
}
