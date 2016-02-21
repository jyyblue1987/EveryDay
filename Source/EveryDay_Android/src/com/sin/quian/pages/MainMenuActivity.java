package com.sin.quian.pages;

import org.json.JSONException;
import org.json.JSONObject;

import com.sin.quian.Const;
import com.sin.quian.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.Display;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import common.component.ui.MyButton;
import common.component.ui.MyTextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.MediaUtils;
import common.manager.activity.ActivityManager;


public class MainMenuActivity extends HeaderBarActivity
{
	private static int	PICK_GALLERY_CODE = 100;
	private static int	COMMENT_REQUEST_CODE = 200;
	
	ImageView 		m_imgAppIcon = null;
	MyTextView 		m_textAppName= null;
	MyButton 		m_btnRecent = null;
	MyButton 		m_btnNamedStar = null;
	MyButton 		m_btnFriends = null;
	MyButton 		m_btnPersonal = null;
	ImageView 		m_imgCameraIcon = null;
	int				parentWidth = 0;
	int 			parentHeight = 0;

	String			m_cameraTempPath = "";
	
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
		ActivityManager.changeActivity(MainMenuActivity.this, ContactListActivity.class, bundle, false, null );		
	}
	
//	private void onClickPersonal()
//	{
//		Bundle bundle = new Bundle();
//		ActivityManager.changeActivity(MainMenuActivity.this, UserActivity.class, bundle, false, null );		
//	}
	
	private void onClickCamera()
	{
		uploadStage();
	}
	
	private void uploadStage()
	{
		m_cameraTempPath = Environment.getExternalStorageDirectory() + "/";
		m_cameraTempPath += "camera_temp.jpg";

		MediaUtils.showCameraGalleryPage(this, PICK_GALLERY_CODE, m_cameraTempPath);
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
	
	private void processFile(String path)
	{
		Bundle bundle = new Bundle();
		
		JSONObject data = new JSONObject();
		
		try {
			data.put(Const.MODE, 0);
			data.put(Const.FILE_PATH, path);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		bundle.putString(INTENT_EXTRA, data.toString());
		ActivityManager.changeActivity(this, CommentDetailActivity.class, bundle, false, COMMENT_REQUEST_CODE);	
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == 0)
			return;		
		
		if( requestCode == PICK_GALLERY_CODE + 1 )
		{
			Uri selectedImage = data.getData();			
			String picturePath = MediaUtils.getPathFromURI(this, selectedImage);
			
			processFile(picturePath);
		}
		
		if (requestCode == PICK_GALLERY_CODE ) {
			processFile(m_cameraTempPath);
		}	
		
		if (requestCode == PICK_GALLERY_CODE + 2 ) {
			processFile(m_cameraTempPath);
		}	
		
		if (requestCode == COMMENT_REQUEST_CODE ) {
			gotoPersonalCenterPage();
		}	
		
		super.onActivityResult(requestCode, resultCode, data);	
	}

}
