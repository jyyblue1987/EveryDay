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


public class MainMenuActivity extends BaseActivity
{
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	
	ImageView 		m_imgMenu_New = null;
	ImageView 		m_imgMenu_Star = null;
	ImageView 		m_imgMenu_Friend = null;
	ImageView 		m_imgMenu_Personal = null;
	ImageView 		m_imgCamera = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_imagemenu);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgMenu_New = (ImageView) findViewById(R.id.img_menu_new);
		m_imgMenu_Star = (ImageView) findViewById(R.id.img_menu_star);
		m_imgMenu_Friend = (ImageView) findViewById(R.id.img_menu_friend);
		m_imgMenu_Personal = (ImageView) findViewById(R.id.img_menu_personal);
		m_imgCamera = (ImageView) findViewById(R.id.img_menu_camera);
	}

	protected void initData()
	{
		super.initData();
//		m_txtPageTitle.setText("分享生活");
//		m_btnRight.setVisibility(View.INVISIBLE);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
//		ResourceUtils.addClickEffect(m_btnSignUp);
		m_imgMenu_New.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickRecent();				
			}
		});
		
		m_imgMenu_Star.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickStar();				
			}
		});
		
		m_imgMenu_Friend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickFriends();				
			}
		});
		
		m_imgMenu_Personal.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				gotoPersonalCenterPage();				
			}
		});

		m_imgCamera.setOnClickListener(new View.OnClickListener() {
			
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
		ActivityManager.changeActivity(MainMenuActivity.this, ContactListActivity.class, bundle, false, null );		
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
		
//		LayoutUtils.setMargin(findViewById(R.id.lay_menu_1), 96, 218,0.0, true);
//		LayoutUtils.setMargin(findViewById(R.id.lay_menu_1), 96, 218,0.0, true);
//
		LayoutUtils.setSize(findViewById(R.id.lay_main_head), LayoutParams.MATCH_PARENT, 273, true);
//
		LayoutUtils.setSize(m_imgMenu_New, 418, 402, true);
		LayoutUtils.setMargin(m_imgMenu_New, 96, 214, 0, 0, true);

		LayoutUtils.setSize(m_imgMenu_New, 418, 402, true);
		LayoutUtils.setMargin(m_imgMenu_New, 68, 214, 0, 0, true);

		LayoutUtils.setSize(m_imgMenu_Friend, 418, 402, true);
		LayoutUtils.setMargin(m_imgMenu_Friend, 96, 102, 0, 0, true);

		LayoutUtils.setSize(m_imgMenu_Personal, 418, 402, true);
		LayoutUtils.setMargin(m_imgMenu_Personal, 68, 102, 0, 0, true);

		
		
		LayoutUtils.setSize(m_imgCamera, 156, 154, true);
		LayoutUtils.setMargin(m_imgCamera, 0, 30, 0, 30, true);
		
//		LayoutUtils.setMargin(m_textAppName, widthUnit, heightUnit, 0, heightUnit, true);
//		m_textAppName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
//		
//		LayoutUtils.setSize(findViewById(R.id.lay_divide_1), LayoutParams.MATCH_PARENT, heightUnit/2, true);
//
//		LayoutUtils.setSize(findViewById(R.id.lay_menu_2), LayoutParams.MATCH_PARENT, heightUnit*6, true);
//		
//		LayoutUtils.setSize(m_btnRecent, widthUnit*3, heightUnit*5, true);
//		LayoutUtils.setMargin(m_btnRecent, widthUnit, heightUnit, 0, 0, true);
//		m_btnRecent.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
//
//		LayoutUtils.setSize(m_btnNamedStar, widthUnit*3, heightUnit*5, true);
//		LayoutUtils.setMargin(m_btnNamedStar, widthUnit, heightUnit, widthUnit, 0, true);
//		m_btnNamedStar.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
//
//		LayoutUtils.setSize(findViewById(R.id.lay_menu_3), LayoutParams.MATCH_PARENT, heightUnit*6, true);
//
//		LayoutUtils.setSize(m_btnFriends, widthUnit*3, heightUnit*5, true);
//		LayoutUtils.setMargin(m_btnFriends, widthUnit, heightUnit, 0, 0, true);
//		m_btnFriends.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
//
//		LayoutUtils.setSize(m_btnPersonal, widthUnit*3, heightUnit*5, true);
//		LayoutUtils.setMargin(m_btnPersonal, widthUnit, heightUnit, widthUnit, 0, true);
//		m_btnPersonal.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
//		
//		LayoutUtils.setSize(findViewById(R.id.lay_divide_2), LayoutParams.MATCH_PARENT, heightUnit/2, true);
//		
//		LayoutUtils.setSize(findViewById(R.id.lay_menu_4), LayoutParams.MATCH_PARENT, heightUnit*3, true);
//
//		LayoutUtils.setSize(m_imgCameraIcon, heightUnit*2, heightUnit*2, true);
//		LayoutUtils.setMargin(m_imgCameraIcon, 0, heightUnit/2, 0, heightUnit/2, true);
//		
//		LayoutUtils.setSize(findViewById(R.id.lay_divide_3), LayoutParams.MATCH_PARENT, heightUnit/2, true);
//
	}

}
