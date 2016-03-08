package com.sin.quian.pages;

import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.network.ServerTask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.design.utils.ResourceUtils;
import common.image.load.ImageUtils;
import common.library.utils.MediaUtils;
import common.manager.activity.ActivityManager;


public class MainMenuActivity extends HeaderBarActivity
{
	private static int	PICK_GALLERY_CODE = 100;
	private static int	COMMENT_REQUEST_CODE = 200;
	private static int	BUY_POINT_CODE = 201;
	
	ImageView		m_imgPhoto = null;
	TextView		m_txtUserName = null;
	TextView		m_txtPointCount = null;
	
	ImageView 		m_imgAdvertise = null;
	ImageView 		m_imgCamera = null;
	ImageView 		m_imgBuyIcon = null;

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
		
		m_imgPhoto = (ImageView) findViewById(R.id.img_photo);
		m_txtUserName = (TextView) findViewById(R.id.txt_username);
		m_txtPointCount = (TextView) findViewById(R.id.txt_point_count);

		m_imgAdvertise = (ImageView) findViewById(R.id.img_advertise);
		m_imgCamera = (ImageView) findViewById(R.id.img_menu_camera);
		m_imgBuyIcon = (ImageView) findViewById(R.id.img_buy_point);
	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		
		m_layLeft.setVisibility(View.INVISIBLE);
		
		LayoutUtils.setSize(findViewById(R.id.lay_main_head), LayoutParams.MATCH_PARENT, ScreenAdapter.getDeviceHeight() / 3, false);
		
		LayoutUtils.setSize(m_imgPhoto, 300, 300, true);
		LayoutUtils.setMargin(findViewById(R.id.lay_user_info), 0, 40, 0, 0, true);
		
		m_txtUserName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
		
		LayoutUtils.setMargin(findViewById(R.id.img_star), 50, 0, 0, 0, true);
		LayoutUtils.setSize(findViewById(R.id.img_star), 50, 50, true);
		
		LayoutUtils.setMargin(m_txtPointCount, 10, 0, 0, 0, true);
		m_txtPointCount.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
				
		LayoutUtils.setSize(findViewById(R.id.lay_main_bottom), LayoutParams.MATCH_PARENT, 210, true);
		LayoutUtils.setPadding(findViewById(R.id.lay_main_bottom), 0, 57, 0, 0, true);
		LayoutUtils.setSize(m_imgAdvertise, 87, 69, true);
		
		LayoutUtils.setMargin(m_imgCamera, 180, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgCamera, 120, 95, true);
		
		LayoutUtils.setMargin(m_imgBuyIcon, 180, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgBuyIcon, 80, 80, true);
		
		int menu_icon [] = {
				R.id.img_menu_news, R.id.img_menu_topstar,
				R.id.img_menu_friends, R.id.img_menu_personal
		};
		
		int menu_label [] = {
				R.id.txt_menu_news, R.id.txt_menu_topstar,
				R.id.txt_menu_friends, R.id.txt_menu_personal
		};

		
		for(int i = 0; i < menu_icon.length; i++ )
		{
			LayoutUtils.setSize(findViewById(menu_icon[i]), 110, 110, true);
			((TextView)findViewById(menu_label[i])).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(45));
			LayoutUtils.setMargin(findViewById(menu_label[i]), 0, 30, 0, 0, true);
		}
	}
	
	
	protected void initData()
	{
		super.initData();
		
		Locale locale = LocaleFactory.getLocale();
		m_txtPageTitle.setText(locale.EveryDay);
		
		JSONObject profile = AppContext.getProfile();
		m_txtUserName.setText(profile.optString(Const.USERNAME, ""));
		m_txtPointCount.setText(profile.optInt(Const.POINT_NUM, 0) + "");
		
		DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
		ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + profile.optString(Const.PHOTO, ""), m_imgPhoto, options);
		
		((TextView)findViewById(R.id.txt_menu_news)).setText(locale.Recent);
		((TextView)findViewById(R.id.txt_menu_topstar)).setText(locale.Favorite);
		((TextView)findViewById(R.id.txt_menu_friends)).setText(locale.Friend);
		((TextView)findViewById(R.id.txt_menu_personal)).setText(locale.PersonalCenter);		
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
		
		findViewById(R.id.lay_menu_recent).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickRecent();					
			}
		});
		
		
		findViewById(R.id.lay_menu_topstar).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickStar();				
			}
		});
		
		findViewById(R.id.lay_menu_friends).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickFriends();				
			}
		});
		
		findViewById(R.id.lay_menu_personal).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				gotoPersonalCenterPage();				
			}
		});

		ResourceUtils.addClickEffect(m_imgCamera);
		m_imgCamera.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickCamera();				
			}
		});
		
		ResourceUtils.addClickEffect(m_imgAdvertise);
		ResourceUtils.addClickEffect(m_imgBuyIcon);
		m_imgBuyIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onClickBuy();				
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
	
	private void onClickBuy(){
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, BuyActivity.class, bundle, false, BUY_POINT_CODE );		
	}
	
	
	private void onClickCamera()
	{
		uploadStage();
	}
	
	private void uploadStage()
	{
		m_cameraTempPath = Environment.getExternalStorageDirectory() + "/";
		m_cameraTempPath += "camera_temp";//.jpg

		MediaUtils.showCameraGalleryPage(this, PICK_GALLERY_CODE, m_cameraTempPath);
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
		m_cameraTempPath = Environment.getExternalStorageDirectory() + "/";
		m_cameraTempPath += "camera_temp";
		
		if( requestCode == PICK_GALLERY_CODE + 1 )
		{
			Uri selectedImage = data.getData();			
			String picturePath = MediaUtils.getPathFromURI(this, selectedImage);
			
			processFile(picturePath);
		}
		
		if (requestCode == PICK_GALLERY_CODE + 3 ) {
			Uri selectedImage = data.getData();			
			String picturePath = MediaUtils.getPathFromURI(this, selectedImage);
			
			processFile(picturePath);
		}	
		
		if (requestCode == PICK_GALLERY_CODE ) {
			processFile(m_cameraTempPath + ".jpg");
		}	
		
		if (requestCode == PICK_GALLERY_CODE + 2 ) {
			processFile(m_cameraTempPath + ".mp4");
		}	
		

		if (requestCode == COMMENT_REQUEST_CODE ) {
			Log.e("MainMenu", "gotoPersonalCenterPage");
//			gotoPersonalCenterPage();
		}	
		
		super.onActivityResult(requestCode, resultCode, data);	
	}

}
