package com.sin.quian.pages;

import org.json.JSONException;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.component.ui.MyButton;
import common.component.ui.MyTextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.image.load.ImageUtils;
import common.library.utils.CheckUtils;
import common.library.utils.DataUtils;
import common.library.utils.MediaUtils;
import common.library.utils.MessageUtils;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class ProfileActivity extends HeaderBarActivity
{
	String			m_cameraTempPath = "";
	String			m_cameraZoomTempPath = "";
	
	static int	PROFILE_PICK_GALLERY_CODE = 100;
	static final int PHOTOZOOM = 20; 


	private static int	BUY_POINT_CODE = 200;
	
	ImageView		m_imgPhotoIcon = null;
	EditText 		m_editFullName = null;
	EditText 		m_editUserName = null;
	EditText 		m_editThumbnail = null;
	EditText 		m_editEmail = null;
	EditText 		m_editPhonenumber = null;
	EditText 		m_editBirthday = null;
	EditText 		m_editAddress = null;
	EditText 		m_editModifiedData = null;
	
	ImageView		m_imgHard1 = null;
	ImageView		m_imgHard2 = null;
	ImageView		m_imgHard3 = null;
	
	MyTextView		m_txtHard1 = null;
	MyTextView		m_txtHard2 = null;
	MyTextView		m_txtHard3 = null;
	
	MyButton		m_btnSave = null;
	MyButton		m_btnChangePassword= null;
	MyButton		m_btnBuy = null;
	MyButton		m_btnLogout = null;
	
	int [] m_field_item = {
		R.id.fragment_profile_fullname,
		R.id.fragment_profile_username,
		R.id.fragment_profile_thumbnail,
		R.id.fragment_profile_email,
		R.id.fragment_profile_phonenumber,
		R.id.fragment_profile_birthday,
		R.id.fragment_profile_address,
		R.id.fragment_profile_modifieddate
	};
	
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
		
		m_editFullName = (EditText) findViewById(R.id.fragment_profile_fullname).findViewById(R.id.edit_content);
		m_editUserName = (EditText) findViewById(R.id.fragment_profile_username).findViewById(R.id.edit_content);
		m_editThumbnail = (EditText) findViewById(R.id.fragment_profile_thumbnail).findViewById(R.id.edit_content);
		m_editEmail = (EditText) findViewById(R.id.fragment_profile_email).findViewById(R.id.edit_content);
		m_editPhonenumber = (EditText) findViewById(R.id.fragment_profile_phonenumber).findViewById(R.id.edit_content);
		m_editBirthday = (EditText) findViewById(R.id.fragment_profile_birthday).findViewById(R.id.edit_content);
		m_editAddress = (EditText) findViewById(R.id.fragment_profile_address).findViewById(R.id.edit_content);
		m_editModifiedData = (EditText) findViewById(R.id.fragment_profile_modifieddate).findViewById(R.id.edit_content);

		m_btnSave = (MyButton) findViewById(R.id.btn_profile_save);
		m_btnChangePassword = (MyButton) findViewById(R.id.btn_profile_change);
		m_btnBuy = (MyButton) findViewById(R.id.btn_profile_buy);
		m_btnLogout = (MyButton) findViewById(R.id.btn_profile_logout);

		m_imgHard1 = (ImageView) findViewById(R.id.img_profile_icon1);
		m_imgHard2 = (ImageView) findViewById(R.id.img_profile_icon2);
		m_imgHard3 = (ImageView) findViewById(R.id.img_profile_icon3);

		m_txtHard1 = (MyTextView) findViewById(R.id.text_profile_num1);
		m_txtHard2 = (MyTextView) findViewById(R.id.text_profile_num2);
		m_txtHard3 = (MyTextView) findViewById(R.id.text_profile_num3);
}
	
	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setMargin(m_imgPhotoIcon, 0, 80, 0, 100, true);
		LayoutUtils.setSize(m_imgPhotoIcon, 300, 300, true);

		for(int i = 0; i < m_field_item.length; i++ )
		{
			LayoutUtils.setMargin(findViewById(m_field_item[i]), 80, 0, 80, 100, true);
			LayoutUtils.setPadding(findViewById(m_field_item[i]).findViewById(R.id.lay_info), 20, 0, 20, 0, true);
			
			((TextView)findViewById(m_field_item[i]).findViewById(R.id.txt_label)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
			((TextView)findViewById(m_field_item[i]).findViewById(R.id.edit_content)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		}
		
		LayoutUtils.setSize(findViewById(R.id.lay_profile_1),LayoutParams.MATCH_PARENT, 130, true);

		LayoutUtils.setMargin(m_imgHard1, 70, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgHard1, 50, 50, true);
		
		LayoutUtils.setMargin(m_txtHard1, 20, 0, 0, 0, true);
		m_txtHard1.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setMargin(m_imgHard2, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgHard2, 50, 50, true);
		
		LayoutUtils.setMargin(m_txtHard2, 20, 0, 0, 0, true);
		m_txtHard2.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setMargin(m_imgHard3, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgHard3, 50, 50, true);
		
		LayoutUtils.setMargin(m_txtHard3, 20, 0, 0, 0, true);
		m_txtHard3.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));

		LayoutUtils.setMargin(m_btnSave, 80, 60, 80, 0, true);
		LayoutUtils.setSize(m_btnSave, LayoutParams.MATCH_PARENT, 114, true);
		m_btnSave.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setMargin(m_btnChangePassword, 80, 60, 80, 0, true);
		LayoutUtils.setSize(m_btnChangePassword, LayoutParams.MATCH_PARENT, 114, true);
		m_btnChangePassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setMargin(m_btnBuy, 80, 60, 80, 0, true);
		LayoutUtils.setSize(m_btnBuy, LayoutParams.MATCH_PARENT, 114, true);
		m_btnBuy.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setMargin(m_btnLogout, 80, 60, 80, 60, true);
		LayoutUtils.setSize(m_btnLogout, LayoutParams.MATCH_PARENT, 114, true);
		m_btnLogout.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

	}

	protected void initData()
	{
		super.initData();
		m_btnRight.setVisibility(View.INVISIBLE);
		m_txtPageTitle.setText("个人信息");
		
		((TextView) findViewById(R.id.fragment_profile_fullname).findViewById(R.id.txt_label)).setText("全名");
		((TextView) findViewById(R.id.fragment_profile_username).findViewById(R.id.txt_label)).setText("用户名");
		((TextView) findViewById(R.id.fragment_profile_thumbnail).findViewById(R.id.txt_label)).setText("缩图");
		((TextView) findViewById(R.id.fragment_profile_email).findViewById(R.id.txt_label)).setText("邮箱");
		((TextView) findViewById(R.id.fragment_profile_phonenumber).findViewById(R.id.txt_label)).setText("手机号码");
		((TextView) findViewById(R.id.fragment_profile_birthday).findViewById(R.id.txt_label)).setText("生日");
		((TextView) findViewById(R.id.fragment_profile_address).findViewById(R.id.txt_label)).setText("地址");
		((TextView) findViewById(R.id.fragment_profile_modifieddate).findViewById(R.id.txt_label)).setText("修改日期");
		
		JSONObject profile = AppContext.getProfile();
		((TextView) findViewById(R.id.fragment_profile_fullname).findViewById(R.id.edit_content)).setText(profile.optString(Const.FULLNAME));
		((TextView) findViewById(R.id.fragment_profile_username).findViewById(R.id.edit_content)).setText(profile.optString(Const.USERNAME));
		((TextView) findViewById(R.id.fragment_profile_thumbnail).findViewById(R.id.edit_content)).setText(profile.optString(Const.THUMBNAIL));
		((TextView) findViewById(R.id.fragment_profile_email).findViewById(R.id.edit_content)).setText(profile.optString(Const.EMAIL));
		((TextView) findViewById(R.id.fragment_profile_phonenumber).findViewById(R.id.edit_content)).setText(profile.optString(Const.PHONENUMBER));
		((TextView) findViewById(R.id.fragment_profile_birthday).findViewById(R.id.edit_content)).setText(profile.optString(Const.BIRTHDAY));
		((TextView) findViewById(R.id.fragment_profile_address).findViewById(R.id.edit_content)).setText(profile.optString(Const.ADDRESS));
		((TextView) findViewById(R.id.fragment_profile_modifieddate).findViewById(R.id.edit_content)).setText(profile.optString(Const.MODIFY_DATE));
		
		m_txtHard1.setText(profile.optString(Const.RECEIVE_NUM, "0"));
		m_txtHard2.setText(profile.optString(Const.POINT_NUM, "0"));
		m_txtHard3.setText(profile.optString(Const.SEND_NUM, "0"));

		DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.contact_icon).build();
		ImageLoader.getInstance().displayImage(ServerTask.SERVER_UPLOAD_PHOTO_PATH + AppContext.getProfile().optString(Const.PHOTO, ""), m_imgPhotoIcon, options);

//		((EditText) findViewById(R.id.fragment_profile_email).findViewById(R.id.edit_content)).setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		
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
		m_btnBuy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickBuy();				
			}
		});
		m_btnLogout.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickLogout();				
			}
		});
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
		String fullname = ((TextView) findViewById(R.id.fragment_profile_fullname).findViewById(R.id.edit_content)).getText().toString();
		final String username = ((TextView) findViewById(R.id.fragment_profile_username).findViewById(R.id.edit_content)).getText().toString();
		String email = ((TextView) findViewById(R.id.fragment_profile_email).findViewById(R.id.edit_content)).getText().toString();
		String phonenumber = ((TextView) findViewById(R.id.fragment_profile_phonenumber).findViewById(R.id.edit_content)).getText().toString();
		String birthday = ((TextView) findViewById(R.id.fragment_profile_birthday).findViewById(R.id.edit_content)).getText().toString();
		String address = ((TextView) findViewById(R.id.fragment_profile_address).findViewById(R.id.edit_content)).getText().toString();
		String modified = ((TextView) findViewById(R.id.fragment_profile_modifieddate).findViewById(R.id.edit_content)).getText().toString();
		
		if( CheckUtils.isEmpty(fullname) )
		{
			MessageUtils.showMessageDialog(this, "请输入全名.");
			return;
		}
		if( CheckUtils.isEmpty(username) )
		{
			MessageUtils.showMessageDialog(this, "请输入用户名.");
			return;
		}

		if( CheckUtils.isEmpty(email) )
		{
			MessageUtils.showMessageDialog(this, "请输入用户邮箱.");
			return;
		}
		if( CheckUtils.isEmpty(phonenumber) )
		{
			MessageUtils.showMessageDialog(this, "请输入用户的电话号码.");
			return;
		}
		if( CheckUtils.isEmpty(birthday) )
		{
			MessageUtils.showMessageDialog(this, "请输入的用户的生日.");
			return;
		}
		if( CheckUtils.isEmpty(address) )
		{
			MessageUtils.showMessageDialog(this, "请输入用户地址.");
			return;
		}
		if( CheckUtils.isEmpty(modified) )
		{
			MessageUtils.showMessageDialog(this, "请输入修改的日期.");
			return;
		}
		
		showLoadingProgress();

		ServerManager.updateProfile(AppContext.getUserID(), username, fullname, email, phonenumber, birthday, address, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				 hideProgress();		
				
				 if( result.mResult != LogicResult.RESULT_OK )
				 {
					 MessageUtils.showMessageDialog(ProfileActivity.this, result.mMessage);
					 return;
				 }
				 AppContext.setProfile(result.getContentData());
				 DataUtils.savePreference(Const.USERNAME, username);
				 onFinishActivity();
				
			}
		}); 
	}
	
	
	private void onClickChange()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(ProfileActivity.this, ChangePasswordActivity.class, bundle, false, null );		
	}
	
	private void onClickBuy(){
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(ProfileActivity.this, BuyActivity.class, bundle, false, BUY_POINT_CODE );		
		
	}
	
	private void onClickLogout(){
		DataUtils.savePreference(Const.LOGIN_OK, "0");
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(ProfileActivity.this, LoginActivity.class, bundle, false, null );
		ActivityManager.getInstance().popAllActivity();
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
					MessageUtils.showMessageDialog(ProfileActivity.this, result.mMessage);
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

			}
		});
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
		
		if (requestCode == BUY_POINT_CODE ) {
			m_txtHard2.setText(AppContext.getProfile().optString(Const.POINT_NUM, "0"));
		}	
				
		super.onActivityResult(requestCode,  resultCode, data);	
	}

	
}
