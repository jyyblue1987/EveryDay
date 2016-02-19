
package com.sin.quian.pages;

import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.os.Bundle;
import android.os.Message;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import common.component.ui.MyButton;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.CheckUtils;
import common.library.utils.MessageUtils;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class RegisterActivity extends HeaderBarActivity
{
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	
	ImageView		m_imgPhotoIcon = null;
	EditText 		m_editName = null;
	EditText 		m_editEmail = null;
	EditText 		m_editPassword = null;
	EditText 		m_editConfirmPassword = null;
	MyButton		m_btnRegister = null;
	
	int [] m_field_item = {
		R.id.fragment_username,
		R.id.fragment_email,
		R.id.fragment_password,
		R.id.fragment_confirm_password
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgPhotoIcon = (ImageView) findViewById(R.id.img_photo_icon);
		
		m_editName = (EditText) findViewById(R.id.fragment_username).findViewById(R.id.edit_content);
		m_editEmail = (EditText) findViewById(R.id.fragment_email).findViewById(R.id.edit_content);
		m_editPassword = (EditText) findViewById(R.id.fragment_password).findViewById(R.id.edit_content);
		m_btnRegister = (MyButton) findViewById(R.id.btn_register);
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
		
		LayoutUtils.setMargin(m_btnRegister, 0, 200, 0, 200, true);
		LayoutUtils.setSize(m_btnRegister, 582, 117, true);
		m_btnRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
	}
	

	protected void initData()
	{
		super.initData();
		m_btnRight.setVisibility(View.INVISIBLE);
		m_txtPageTitle.setText("Register");
		
		((TextView) findViewById(R.id.fragment_username).findViewById(R.id.txt_label)).setText("User Name");
		((TextView) findViewById(R.id.fragment_email).findViewById(R.id.txt_label)).setText("Email");
		((TextView) findViewById(R.id.fragment_password).findViewById(R.id.txt_label)).setText("Password");
		((TextView) findViewById(R.id.fragment_confirm_password).findViewById(R.id.txt_label)).setText("Retype");
		
		((EditText) findViewById(R.id.fragment_email).findViewById(R.id.edit_content)).setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
		((EditText) findViewById(R.id.fragment_password).findViewById(R.id.edit_content)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		((EditText) findViewById(R.id.fragment_confirm_password).findViewById(R.id.edit_content)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		
	}
	
	protected void initEvents()
	{ 
		super.initEvents();

		m_btnRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickRegister();				
			}
		});
	}
	
	private void onClickRegister()
	{
		String username = ((TextView) findViewById(R.id.fragment_username).findViewById(R.id.edit_content)).getText().toString();
		String email = ((TextView) findViewById(R.id.fragment_email).findViewById(R.id.edit_content)).getText().toString();
		String password = ((TextView) findViewById(R.id.fragment_password).findViewById(R.id.edit_content)).getText().toString();
		String confirmpassword = ((TextView) findViewById(R.id.fragment_confirm_password).findViewById(R.id.edit_content)).getText().toString();
		
		if( CheckUtils.isEmpty(username) )
		{
			MessageUtils.showMessageDialog(this, "Please input user name.");
			return;
		}
		
		if( CheckUtils.isEmpty(email) )
		{
			MessageUtils.showMessageDialog(this, "Please input user email.");
			return;
		}
		
		if( CheckUtils.isEmpty(password) || CheckUtils.isEmpty(confirmpassword) )
		{
			MessageUtils.showMessageDialog(this, "Please input password.");
			return;
		}
		
		if( password.equals(confirmpassword) == false )
		{
			MessageUtils.showMessageDialog(this, "Password is not matching.");
			return;
		}
		
		
		register(username, email, password);
	}
	
	private void register(String username, String email, String password)
	{

		showProgress("Loading", "Please wait");
		
		ServerManager.register(username, email, password, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();		
				
				if( result.mResult != LogicResult.RESULT_OK )
				{
					return;
				}
				
				gotoMainPage();
			}
		});
	}
	
	private void gotoMainPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(RegisterActivity.this, MainMenuActivity.class, bundle, false, null );
	}
	
	


	 
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
