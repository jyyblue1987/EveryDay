package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import common.component.ui.MyButton;
import common.component.ui.MyTextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.manager.activity.ActivityManager;


public class MainMenuActivity extends HeaderBarActivity
{
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	
	ImageView 		m_imgAppIcon = null;
	MyTextView 		m_textAppName= null;
	MyButton 		m_btnRecent = null;
	MyButton 		m_btnNamedStar = null;
	MyButton 		m_btnFriends = null;
	MyButton 		m_btnPersonal = null;
	ImageView 		m_imgCameraIcon = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_imagemenu);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgAppIcon = (ImageView) findViewById(R.id.img_menu_appicon);
		m_textAppName = (MyTextView) findViewById(R.id.text_menu_appname);
		m_btnRecent = (MyButton) findViewById(R.id.btn_menu_recent);
		m_btnNamedStar = (MyButton) findViewById(R.id.btn_menu_namedstar);
		m_btnFriends = (MyButton) findViewById(R.id.btn_menu_friends);
		m_btnPersonal = (MyButton) findViewById(R.id.btn_menu_personal);
		m_imgCameraIcon = (ImageView) findViewById(R.id.img_menu_cameraicon);
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("Main Menu");
		m_btnRight.setVisibility(View.INVISIBLE);

		
//		m_txtName.setText("");
//		m_txtPassword.setText("");
		
//		showLoadingProgress();
//		ServerManager.getCountryList(new ResultCallBack() {
//			
//			@Override
//			public void doAction(LogicResult result) {
//				hideProgress();
//				if( result.mResult != LogicResult.RESULT_OK )
//				{
//					Toast.makeText(LoginActivity.this, result.mMessage, Toast.LENGTH_LONG).show();
//					return;
//				}		
//			}
//		});

//		AndroidUtils.getDialingCountryCode(this);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
//		ResourceUtils.addClickEffect(m_btnSignUp);
		m_btnRecent.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickAlarm();				
			}
		});
		m_btnNamedStar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickAlarm();				
			}
		});
		m_btnFriends.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickAlarm();				
			}
		});
		m_btnPersonal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				gotoPersonalCenterPage();				
			}
		});

		m_imgCameraIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickAlarm();				
			}
		});
	}
	
	private void onClickAlarm()
	{
		
	}
	
	private void gotoPersonalCenterPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(MainMenuActivity.this, MyCenterActivity.class, bundle, false, null );		
	}

	protected void layoutControls()
	{
		super.layoutControls();

		LayoutUtils.setSize(findViewById(R.id.lay_menu_1), LayoutParams.MATCH_PARENT, 200, true);

		LayoutUtils.setSize(m_imgAppIcon, 150, 150, true);
		LayoutUtils.setMargin(m_imgAppIcon, 50, 50, 50, 50, true);
//		LayoutUtils.setPadding(m_imgSport1, 20, 20, 20, 20, true);
		
		LayoutUtils.setMargin(m_textAppName, 20, 20, 20, 20, true);
		m_textAppName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_textAppName, 20, 0, 20, 0, true);
		
		LayoutUtils.setSize(findViewById(R.id.lay_menu_2), LayoutParams.MATCH_PARENT, 700, true);
		
		LayoutUtils.setSize(m_btnRecent, 400, 550, true);
		LayoutUtils.setMargin(m_btnRecent, 100, 100, 40, 50, true);
		m_btnRecent.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setSize(m_btnNamedStar, 400, 550, true);
		LayoutUtils.setMargin(m_btnNamedStar, 40, 100, 100, 50, true);
		m_btnNamedStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setSize(findViewById(R.id.lay_menu_3), LayoutParams.MATCH_PARENT, 700, true);

		LayoutUtils.setSize(m_btnFriends, 400, 550, true);
		LayoutUtils.setMargin(m_btnFriends, 100, 50, 40, 100, true);
		m_btnFriends.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setSize(m_btnPersonal, 400, 550, true);
		LayoutUtils.setMargin(m_btnPersonal, 40, 50, 100, 100, true);
		m_btnPersonal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setSize(findViewById(R.id.lay_menu_4), LayoutParams.MATCH_PARENT, 200, true);

		LayoutUtils.setSize(m_imgCameraIcon, 150, 150, true);

}
	 
//	 private void onClickLogin()
//	 {
//		 String mobile = m_txtLoginUserName.getText().toString();
//		 if( CheckUtils.isEmpty(mobile) )
//		 {
//			 DialogFactory.getInstance().showMessageDialog(this, "You must be input phonenumber", false, null);
//			 return;
//		 }
//		 login(m_selectedCountry.code, mobile);
//	 }

//	 private void login(final String username, final String password)
//	 {	
//		 DataUtils.savePreference(Const.USERNAME, username);
//		 DataUtils.savePreference(Const.PASSWORD, password);
//		 
//		 showLoadingProgress();
//		 ServerManager.login(username, password, DataUtils.getPreference(Const.GCM_PUSH_KEY, ""), new ResultCallBack() {
//			
//			@Override
//			public void doAction(LogicResult result) {
//				hideProgress();
//				if( result.mResult == LogicResult.RESULT_OK )	// login ok
//				{
//					DataUtils.savePreference(Const.LOGIN_OK, "1");		
//					loginChatServer(username, password);					
//					return;
//				}
//				
//				
//				DataUtils.savePreference(Const.LOGIN_OK, "0");
//				
//				if( result.mResult == LogicResult.RESULT_NO_VERIFIED )
//				{
//					DataUtils.savePreference(Const.VCODE, result.getContentData().optString("vcode", "") );
//					gotoVerifyPage();
//					return;
//				}
//				else
//				{
//					DialogFactory.getInstance().showMessageDialog(RegisterActivity.this, result.mMessage, false, null);
//				}
//			}
//		});
//	 }
	 
//	 private void loginChatServer(final String countryCode, final String mobile)
//	 {
//		 String username = countryCode + "_" + mobile;
//		 String password = AlgorithmUtils.invert(username);
//		 mChatServiceInterface.login(username, password, null);
//		 
//		 gotoChattingPage();
//	 }
	 
//	 private void gotoVerifyPage()
//	 {
//		DialogFactory.getInstance().showMessageDialog(this, "We will send the verify code to this phone number.", false, new ItemCallBack() {
//			
//			@Override
//			public void doClick(ItemResult result) {
//				Bundle bundle = new Bundle();
//				ActivityManager.changeActivity(RegisterActivity.this, VerifyActivity.class, bundle, false, null );		
//				
//			}
//		});
//	 }
	 
//	 private void gotoChattingPage()
//	 {		 
//		 Bundle bundle = new Bundle();
//		 ActivityManager.changeActivity(this, ChattingHistoryActiviry.class, bundle, true, null );
//	 }

}
