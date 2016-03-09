package com.sin.quian.pages;

import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.image.load.ImageUtils;
import common.library.utils.CheckUtils;
import common.library.utils.DataUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MessageUtils;
import common.library.utils.MessageUtils.OnButtonClickListener;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class ProfileActivity extends BottomBarActivity
{
	String			m_cameraTempPath = "";
	String			m_cameraZoomTempPath = "";
	
	static int	PROFILE_PICK_GALLERY_CODE = 100;
	static final int PHOTOZOOM = 20; 

	ImageView		m_imgPhotoIcon = null;
	EditText 		m_editUserName = null;
	EditText 		m_editAddress = null;
	TextView 		m_txtLanguage = null;
	
	Button		m_btnSave = null;
	Button		m_btnChangePassword= null;
	Button		m_btnLogout = null;
	Button		m_btnAppReward = null;
	
	int [] m_field_item = {
		R.id.fragment_profile_username,
		R.id.fragment_profile_address,
		R.id.fragment_profile_language
	};
	
	boolean m_isChanged = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_profile_page);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgPhotoIcon = (ImageView) findViewById(R.id.img_profile_icon);
		
		m_editUserName = (EditText) findViewById(R.id.fragment_profile_username).findViewById(R.id.edit_content);
		m_editAddress = (EditText) findViewById(R.id.fragment_profile_address).findViewById(R.id.edit_content);
		m_txtLanguage = (TextView) findViewById(R.id.fragment_profile_language).findViewById(R.id.edit_content);
		
		m_btnSave = (Button) findViewById(R.id.btn_profile_save);
		m_btnChangePassword = (Button) findViewById(R.id.btn_profile_change);
		m_btnLogout = (Button) findViewById(R.id.btn_profile_logout);
		m_btnAppReward = (Button) findViewById(R.id.btn_app_reward);

	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setPadding(findViewById(R.id.lay_photo), 60, 30, 60, 30, true);
		LayoutUtils.setMargin(findViewById(R.id.txt_photo), 0, 0, 0, 0, true);
		((TextView)findViewById(R.id.txt_photo)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setSize(m_imgPhotoIcon, 180, 180, true);
		LayoutUtils.setMargin(m_imgPhotoIcon, 60, 0, 0, 0, true);

		for(int i = 0; i < m_field_item.length; i++ )
		{
			LayoutUtils.setMargin(findViewById(m_field_item[i]), 0, 4, 0, 0, true);
			LayoutUtils.setPadding(findViewById(m_field_item[i]).findViewById(R.id.lay_info), 60, 40, 60, 40, true);
			
			((TextView)findViewById(m_field_item[i]).findViewById(R.id.txt_label)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
			((TextView)findViewById(m_field_item[i]).findViewById(R.id.edit_content)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		}
		
		LayoutUtils.setMargin(m_btnSave, 60, 60, 60, 0, true);
		LayoutUtils.setSize(m_btnSave, LayoutParams.MATCH_PARENT, 114, true);
		m_btnSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setMargin(m_btnChangePassword, 60, 60, 60, 0, true);
		LayoutUtils.setSize(m_btnChangePassword, LayoutParams.MATCH_PARENT, 114, true);
		m_btnChangePassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setMargin(m_btnLogout, 60, 60, 60, 0, true);
		LayoutUtils.setSize(m_btnLogout, LayoutParams.MATCH_PARENT, 114, true);
		m_btnLogout.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		

		LayoutUtils.setMargin(m_btnAppReward, 60, 60, 60, 60, true);
		LayoutUtils.setSize(m_btnAppReward, LayoutParams.MATCH_PARENT, 114, true);
		m_btnAppReward.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

	}

	protected void initData()
	{
		super.initData();
		m_btnRight.setVisibility(View.INVISIBLE);
		
		((TextView) findViewById(R.id.fragment_profile_username).findViewById(R.id.txt_label)).setText("用户名");
		((TextView) findViewById(R.id.fragment_profile_address).findViewById(R.id.txt_label)).setText("地址");
		((TextView) findViewById(R.id.fragment_profile_language).findViewById(R.id.txt_label)).setText("语言");
				
		showLanguage();
		
		JSONObject profile = AppContext.getProfile();
		((TextView) findViewById(R.id.fragment_profile_username).findViewById(R.id.edit_content)).setText(profile.optString(Const.USERNAME));
		((TextView) findViewById(R.id.fragment_profile_address).findViewById(R.id.edit_content)).setText(profile.optString(Const.ADDRESS));
		
		DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
		ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + AppContext.getProfile().optString(Const.PHOTO, ""), m_imgPhotoIcon, options);
	}
	
	protected void showLabels()
	{
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.Setting);
		
		((TextView)findViewById(R.id.txt_photo)).setText(locale.Photo);
		
		((TextView)findViewById(R.id.fragment_profile_username).findViewById(R.id.txt_label)).setText(locale.NickName);
		((TextView)findViewById(R.id.fragment_profile_address).findViewById(R.id.txt_label)).setText(locale.MyPosition);
		((TextView)findViewById(R.id.fragment_profile_language).findViewById(R.id.txt_label)).setText(locale.Language);
		
		if( DataUtils.getPreference(Const.REWARD_APP, 0) == 1 )
			m_btnAppReward.setVisibility(View.GONE);
		else
			m_btnAppReward.setVisibility(View.VISIBLE);
		
		m_btnSave.setText(locale.Save);
		m_btnChangePassword.setText(locale.ChangePWD);
		m_btnLogout.setText(locale.Logout);
		m_btnAppReward.setText(locale.AppReward);
		
		AppContext.refreshProfile();
	}
	
	protected void initEvents()
	{ 
		super.initEvents();

		m_imgPhotoIcon.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				upLoadPhotoIcon();			}
		});
		m_btnSave.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickSave();				
			}
		});
		m_btnChangePassword.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickChange();				
			}
		});
		
		m_btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickLogout();				
			}
		});
		
		m_btnAppReward.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onRewardApp();				
			}
		});
		
		findViewById(R.id.fragment_profile_language).findViewById(R.id.txt_label).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSelectLanguage();				
			}
		});
		
		findViewById(R.id.fragment_profile_language).findViewById(R.id.edit_content).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onSelectLanguage();				
			}
		});
		

	}
	
	private void showLanguage()
	{
		int selectLanguage = DataUtils.getPreference(Const.LANGUAGE, 0);
		if( selectLanguage == 0 )  // english
		{
			m_txtLanguage.setText("ENGLISH");
			LocaleFactory.selectLocale(0);
		}
		else
		{
			m_txtLanguage.setText("简体中文");
			LocaleFactory.selectLocale(1);
		}
		showLabels();
	}

	private void onSelectLanguage()
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		
		String items[] = {"简体中文", "ENGLISH"};
		
		int selectLanguage = DataUtils.getPreference(Const.LANGUAGE, 0);

		dialog.setSingleChoiceItems(items, 1 - selectLanguage, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int whichButton) {
				DataUtils.savePreference(Const.LANGUAGE, 1 - whichButton);
				showLanguage();
				dialog.dismiss();
			}
		});
		
		AlertDialog alertDialog = dialog.create();
		alertDialog.show();
	}
	private void upLoadPhotoIcon()
	{
		m_cameraTempPath = Environment.getExternalStorageDirectory() + "/";
		m_cameraTempPath += "camera_temp.jpg";
		
		// communication between ProfileActivity and mediaUtils
		MediaUtils.showProfileCameraGalleryPage(this, PROFILE_PICK_GALLERY_CODE, m_cameraTempPath);// PROFILE_PICK_GALLERY_CODE: request code
	}
	
	private void onClickSave()
	{
		final String username = ((TextView) findViewById(R.id.fragment_profile_username).findViewById(R.id.edit_content)).getText().toString();
		String address = ((TextView) findViewById(R.id.fragment_profile_address).findViewById(R.id.edit_content)).getText().toString();
		
		if( CheckUtils.isEmpty(username) )
		{
			MessageUtils.showMessageDialog(this, "请输入用户名.");
			return;
		}

		if( CheckUtils.isEmpty(address) )
		{
			MessageUtils.showMessageDialog(this, "请输入用户地址.");
			return;
		}
		
		showLoadingProgress();

		String email = DataUtils.getPreference(Const.EMAIL, "");
		ServerManager.updateProfile(AppContext.getUserID(), username, email, address, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				 hideProgress();		
				
				 if( result.mResult != LogicResult.RESULT_OK )
				 {
					 MessageUtils.showMessageDialog(ProfileActivity.this, EveryDayUtils.getMessage(result.mMessage));
					 return;
				 }
				 AppContext.setProfile(result.getContentData());
				 
			 	 Intent intent = new Intent();
		         setResult(Activity.RESULT_OK, intent); 
				 onFinishActivity();
				 m_isChanged = true;
			}
		}); 
	}
	
	
	private void onClickChange()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(ProfileActivity.this, ChangePasswordActivity.class, bundle, false, null );		
	}
	
	private void onClickLogout(){
		DataUtils.savePreference(Const.LOGIN_OK, "0");
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(ProfileActivity.this, LoginActivity.class, bundle, false, null );
		ActivityManager.getInstance().popAllActivity();
	}
	
	private void onRewardApp()
	{
		Locale locale = LocaleFactory.getLocale();
		
		MessageUtils.showDialogYesNo(this, locale.AppRewardMessage, locale.Allow, locale.NoAllow, new OnButtonClickListener() {
			
			@Override
			public void onOkClick() {
				gotoRewardAppPage();				
			}
			
			@Override
			public void onCancelClick() {
				
				
			}
		});		
	}
	
	private void gotoRewardAppPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(ProfileActivity.this, RewardAppActivity.class, bundle, false, null );	
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
		m_cameraZoomTempPath = Environment.getExternalStorageDirectory() + "/";
		m_cameraZoomTempPath += "camera_zoom.jpg";

		MediaUtils.startPhotoZoom(this, path, m_cameraZoomTempPath, 400, PHOTOZOOM);// to zoom photo
	}
	
	protected void uploadPhoto(String path)
	{
		showLoadingProgress();
		ServerManager.uploadPhoto(AppContext.getUserID(), path, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();

				if( result.mResult != LogicResult.RESULT_OK )	// failed
				{
					MessageUtils.showMessageDialog(ProfileActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				String filename = result.getData().optString("content", "");// to save changed state 
				JSONObject profile = AppContext.getProfile();
				try {
					profile.put(Const.PHOTO, filename);
				} catch (JSONException e) {
					e.printStackTrace();
				}
				DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();// to load photo
				ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + filename, m_imgPhotoIcon, options);
				m_isChanged = true;
			}
		});
	}

	protected void gotoBackPage()
	{
		if( m_isChanged == true )
		{
			Intent intent = new Intent();
	        setResult(Activity.RESULT_OK, intent);	
		}
		 
		onFinishActivity();
	}
	
	
	@Override 
	public void onBackPressed( ) {	
		gotoBackPage();
	}
		
	// to receive intent info from startActivityForResult in MediaUtils
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{ 
		if (resultCode == 0)
			return;	

		if (requestCode == PHOTOZOOM) {
			uploadPhoto(m_cameraZoomTempPath);
		}
		
		if( requestCode == PROFILE_PICK_GALLERY_CODE + 1 )
		{
			Uri selectedImage = data.getData();			
			String picturePath = MediaUtils.getPathFromURI(this, selectedImage);
			
			processFile(picturePath);
		}
		
		if (requestCode == PROFILE_PICK_GALLERY_CODE ) {
			processFile(m_cameraTempPath);
		}	
		
		super.onActivityResult(requestCode,  resultCode, data);	
	}
	

	
}
