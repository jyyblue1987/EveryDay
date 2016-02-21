package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
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
	int				parentWidth = 0;
	int 			parentHeight = 0;

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
		
		parentWidth = ScreenAdapter.getDeviceWidth();
		parentHeight = ScreenAdapter.getDeviceHeight()-findViewById(R.id.fragment_header).getHeight();
		
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("分享生活");
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
				onClickRecent();				
			}
		});
		
		m_btnNamedStar.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickStar();				
			}
		});
		
		m_btnFriends.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickFriends();				
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
				onClickCamera();				
			}
		});
	}
	
	private void onClickRecent()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(MainMenuActivity.this, HistoryActivity.class, bundle, false, null );		
	}

	private void onClickStar()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(MainMenuActivity.this, UserActivity.class, bundle, false, null );		
	}
	
	private void gotoPersonalCenterPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(MainMenuActivity.this, MyCenterActivity.class, bundle, false, null );		
	}

	private void onClickFriends()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(MainMenuActivity.this, UserActivity.class, bundle, false, null );		
	}
	
//	private void onClickPersonal()
//	{
//		Bundle bundle = new Bundle();
//		ActivityManager.changeActivity(MainMenuActivity.this, UserActivity.class, bundle, false, null );		
//	}
	
	private void onClickCamera()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(MainMenuActivity.this, UserActivity.class, bundle, false, null );		
	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		
		m_layLeft.setVisibility(View.INVISIBLE);
		
		int heightUnit = parentHeight/20;
		int widthUnit = parentWidth/9;

		LayoutUtils.setSize(findViewById(R.id.lay_divide_0), LayoutParams.MATCH_PARENT, heightUnit/2, true);

		LayoutUtils.setSize(findViewById(R.id.lay_menu_1), LayoutParams.MATCH_PARENT, heightUnit*3, true);

		LayoutUtils.setSize(m_imgAppIcon, heightUnit*2, heightUnit*2, true);
		LayoutUtils.setMargin(m_imgAppIcon, widthUnit, heightUnit/2, 0, heightUnit/2, true);
		
		LayoutUtils.setMargin(m_textAppName, widthUnit, heightUnit, 0, heightUnit, true);
		m_textAppName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setSize(findViewById(R.id.lay_divide_1), LayoutParams.MATCH_PARENT, heightUnit/2, true);

		LayoutUtils.setSize(findViewById(R.id.lay_menu_2), LayoutParams.MATCH_PARENT, heightUnit*6, true);
		
		LayoutUtils.setSize(m_btnRecent, widthUnit*3, heightUnit*5, true);
		LayoutUtils.setMargin(m_btnRecent, widthUnit, heightUnit, 0, 0, true);
		m_btnRecent.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setSize(m_btnNamedStar, widthUnit*3, heightUnit*5, true);
		LayoutUtils.setMargin(m_btnNamedStar, widthUnit, heightUnit, widthUnit, 0, true);
		m_btnNamedStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setSize(findViewById(R.id.lay_menu_3), LayoutParams.MATCH_PARENT, heightUnit*6, true);

		LayoutUtils.setSize(m_btnFriends, widthUnit*3, heightUnit*5, true);
		LayoutUtils.setMargin(m_btnFriends, widthUnit, heightUnit, 0, 0, true);
		m_btnFriends.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setSize(m_btnPersonal, widthUnit*3, heightUnit*5, true);
		LayoutUtils.setMargin(m_btnPersonal, widthUnit, heightUnit, widthUnit, 0, true);
		m_btnPersonal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setSize(findViewById(R.id.lay_divide_2), LayoutParams.MATCH_PARENT, heightUnit/2, true);
		
		LayoutUtils.setSize(findViewById(R.id.lay_menu_4), LayoutParams.MATCH_PARENT, heightUnit*3, true);

		LayoutUtils.setSize(m_imgCameraIcon, heightUnit*2, heightUnit*2, true);
		LayoutUtils.setMargin(m_imgCameraIcon, 0, heightUnit/2, 0, heightUnit/2, true);
		
		LayoutUtils.setSize(findViewById(R.id.lay_divide_3), LayoutParams.MATCH_PARENT, heightUnit/2, true);

	}

}
