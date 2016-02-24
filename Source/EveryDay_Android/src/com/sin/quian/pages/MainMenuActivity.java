package com.sin.quian.pages;

import org.json.JSONException;
import org.json.JSONObject;

import com.sin.quian.Const;
import com.sin.quian.R;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import common.design.layout.LayoutUtils;
import common.design.utils.ResourceUtils;
import common.library.utils.MediaUtils;
import common.manager.activity.ActivityManager;


public class MainMenuActivity extends BaseActivity
{
	private static int	PICK_GALLERY_CODE = 100;
	private static int	COMMENT_REQUEST_CODE = 200;
	
	ImageView 		m_imgMenu_New = null;
	ImageView 		m_imgMenu_Star = null;
	ImageView 		m_imgMenu_Friend = null;
	ImageView 		m_imgMenu_Personal = null;
	ImageView 		m_imgCamera = null;

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

		m_imgMenu_New = (ImageView) findViewById(R.id.img_menu_new);
		m_imgMenu_Star = (ImageView) findViewById(R.id.img_menu_star);
		m_imgMenu_Friend = (ImageView) findViewById(R.id.img_menu_friend);
		m_imgMenu_Personal = (ImageView) findViewById(R.id.img_menu_personal);
		m_imgCamera = (ImageView) findViewById(R.id.img_menu_camera);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
		
		ResourceUtils.addClickEffect(m_imgMenu_New);
		m_imgMenu_New.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickRecent();				
			}
		});
		
		ResourceUtils.addClickEffect(m_imgMenu_Star);
		m_imgMenu_Star.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickStar();				
			}
		});
		
		ResourceUtils.addClickEffect(m_imgMenu_Friend);
		m_imgMenu_Friend.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickFriends();				
			}
		});
		
		ResourceUtils.addClickEffect(m_imgMenu_Personal);
		m_imgMenu_Personal.setOnClickListener(new View.OnClickListener() {
			
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
	
	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setSize(findViewById(R.id.lay_main_head), LayoutParams.MATCH_PARENT, 273, true);

		LayoutUtils.setSize(m_imgCamera, 156, 154, true);
		LayoutUtils.setMargin(m_imgCamera, 0, 30, 0, 30, true);
		
		LayoutUtils.setMargin(findViewById(R.id.lay_menu_1), 90, 215, 90, 0, true);
		LayoutUtils.setMargin(findViewById(R.id.lay_menu_2), 90, 70, 90, 215, true);
		
		LayoutUtils.setMargin(m_imgMenu_Star, 70, 0, 0, 0, true);
		LayoutUtils.setMargin(m_imgMenu_Personal, 70, 0, 0, 0, true);
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
			gotoPersonalCenterPage();
		}	
		
		super.onActivityResult(requestCode, resultCode, data);	
	}

}
