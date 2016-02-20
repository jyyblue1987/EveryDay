package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.component.ui.MyTextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.CheckUtils;
import common.library.utils.MessageUtils;


public class ProfileActivity extends HeaderBarActivity
{
//	static final int DIALOG_PAUSED_ID = 0;
//	static final int DIALOG_GAMEOVER_ID = 1;
	
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
	ImageView		m_imgHard4 = null;
	
	MyTextView		m_txtHard1 = null;
	MyTextView		m_txtHard2 = null;
	MyTextView		m_txtHard3 = null;
	MyTextView		m_txtHard4 = null;
	
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

		m_imgHard1 = (ImageView) findViewById(R.id.img_profile_icon1);
		m_imgHard2 = (ImageView) findViewById(R.id.img_profile_icon2);
		m_imgHard3 = (ImageView) findViewById(R.id.img_profile_icon3);
		m_imgHard4 = (ImageView) findViewById(R.id.img_profile_icon4);

		m_txtHard1 = (MyTextView) findViewById(R.id.text_profile_num1);
		m_txtHard2 = (MyTextView) findViewById(R.id.text_profile_num2);
		m_txtHard3 = (MyTextView) findViewById(R.id.text_profile_num3);
		m_txtHard4 = (MyTextView) findViewById(R.id.text_profile_num4);
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

		LayoutUtils.setMargin(m_imgHard4, 40, 0, 0, 0, true);
		LayoutUtils.setSize(m_imgHard4, 50, 50, true);
		
		LayoutUtils.setMargin(m_txtHard4, 20, 0, 70, 0, true);
		m_txtHard4.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(50));
	}

	protected void initData()
	{
		super.initData();
		m_btnRight.setVisibility(View.INVISIBLE);
		m_txtPageTitle.setText("注册");
		
		((TextView) findViewById(R.id.fragment_profile_fullname).findViewById(R.id.txt_label)).setText("Full Name");
		((TextView) findViewById(R.id.fragment_profile_username).findViewById(R.id.txt_label)).setText("User Name");
		((TextView) findViewById(R.id.fragment_profile_thumbnail).findViewById(R.id.txt_label)).setText("Thumbnail");
		((TextView) findViewById(R.id.fragment_profile_email).findViewById(R.id.txt_label)).setText("Email");
		((TextView) findViewById(R.id.fragment_profile_phonenumber).findViewById(R.id.txt_label)).setText("Phone Number");
		((TextView) findViewById(R.id.fragment_profile_birthday).findViewById(R.id.txt_label)).setText("Birthday");
		((TextView) findViewById(R.id.fragment_profile_address).findViewById(R.id.txt_label)).setText("Address");
		((TextView) findViewById(R.id.fragment_profile_modifieddate).findViewById(R.id.txt_label)).setText("Modified Date");
		
//		((EditText) findViewById(R.id.fragment_profile_email).findViewById(R.id.edit_content)).setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		
	}
	
	protected void initEvents()
	{ 
		super.initEvents();

//		m_btnRegister.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View paramView) {
//				onClickRegister();				
//			}
//		});
	}
	
	private void onClickRegister()
	{
		String fullname = ((TextView) findViewById(R.id.fragment_profile_fullname).findViewById(R.id.edit_content)).getText().toString();
		String username = ((TextView) findViewById(R.id.fragment_profile_username).findViewById(R.id.edit_content)).getText().toString();
		String thumbnail = ((TextView) findViewById(R.id.fragment_profile_thumbnail).findViewById(R.id.edit_content)).getText().toString();
		String email = ((TextView) findViewById(R.id.fragment_profile_email).findViewById(R.id.edit_content)).getText().toString();
		String phonenumber = ((TextView) findViewById(R.id.fragment_profile_phonenumber).findViewById(R.id.edit_content)).getText().toString();
		String birthday = ((TextView) findViewById(R.id.fragment_profile_birthday).findViewById(R.id.edit_content)).getText().toString();
		String address = ((TextView) findViewById(R.id.fragment_profile_address).findViewById(R.id.edit_content)).getText().toString();
		String modified = ((TextView) findViewById(R.id.fragment_profile_modifieddate).findViewById(R.id.edit_content)).getText().toString();
		
		if( CheckUtils.isEmpty(fullname) )
		{
			MessageUtils.showMessageDialog(this, "Please input full name.");
			return;
		}
		if( CheckUtils.isEmpty(username) )
		{
			MessageUtils.showMessageDialog(this, "Please input user name.");
			return;
		}
		if( CheckUtils.isEmpty(thumbnail) )
		{
			MessageUtils.showMessageDialog(this, "Please input user thumbnail.");
			return;
		}
		if( CheckUtils.isEmpty(email) )
		{
			MessageUtils.showMessageDialog(this, "Please input user email.");
			return;
		}
		if( CheckUtils.isEmpty(phonenumber) )
		{
			MessageUtils.showMessageDialog(this, "Please input user phone number.");
			return;
		}
		if( CheckUtils.isEmpty(birthday) )
		{
			MessageUtils.showMessageDialog(this, "Please input user birthday.");
			return;
		}
		if( CheckUtils.isEmpty(address) )
		{
			MessageUtils.showMessageDialog(this, "Please input user address.");
			return;
		}
		if( CheckUtils.isEmpty(modified) )
		{
			MessageUtils.showMessageDialog(this, "Please input modified date.");
			return;
		}
	}
	
}
