package com.sin.quian.pages;

import com.sin.quian.Const;
import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import common.component.ui.MyButton;
import common.component.ui.MyTextView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.AlgorithmUtils;
import common.library.utils.AndroidUtils;
import common.library.utils.CheckUtils;
import common.library.utils.DataUtils;
import common.list.adapter.ItemCallBack;
import common.list.adapter.ItemResult;
import common.manager.activity.ActivityManager;


public class LoginActivity extends HeaderBarActivity
{
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	
	ImageView 		m_imgLogin = null;
	EditText 		m_editName = null;
	EditText 		m_editPassword = null;
	MyButton		m_btnLogin = null;
	MyTextView		m_txtRegister = null;
	MyTextView		m_txtForgot = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_login);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_imgLogin = (ImageView) findViewById(R.id.img_login);
		m_editName = (EditText) findViewById(R.id.txt_login_username);
		m_editPassword = (EditText) findViewById(R.id.txt_login_password);
		m_btnLogin = (MyButton) findViewById(R.id.btn_login);
		m_txtRegister = (MyTextView) findViewById(R.id.txt_login_register);
		m_txtForgot = (MyTextView) findViewById(R.id.txt_login_forgot);
	}

	protected void initData()
	{
		super.initData();
		
		m_editName.setText("");
		m_editPassword.setText("");
		m_txtPageTitle.setText("Login");
		m_btnRight.setVisibility(View.INVISIBLE);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
//		ResourceUtils.addClickEffect(m_btnSignUp);
		m_btnLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickLogin();				
			}
		});
		
		m_txtRegister.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickRegister();	
			}
		});

		m_txtForgot.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickForgot();	
			}
		});
	}
	
	 private void onClickLogin()
	 {
		 String username = m_editName.getText().toString();
//		 if( CheckUtils.isEmpty(username) )
//		 {
//			 DialogFactory.getInstance().showMessageDialog(this, "You must be input user name", false, null);
//			 return;
//		 }
//		 String password = m_editPassword.getText().toString();
//		 if( CheckUtils.isEmpty(password) )
//		 {
//			 DialogFactory.getInstance().showMessageDialog(this, "You must be input password", false, null);
//			 return;
//		 }
//		 login(username, password);
	 }

	 private void onClickRegister()
	 {
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(LoginActivity.this, RegisterActivity.class, bundle, false, null);		
	 }

	 private void onClickForgot()
	 {
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(LoginActivity.this, ChangePasswordActivity.class, bundle, false, null );		
	 }

	
	protected void layoutControls()
	{
		super.layoutControls();

		LayoutUtils.setSize(m_imgLogin, 500, 500, true);
		LayoutUtils.setMargin(m_imgLogin, 70, 170, 70, 0, true);
		LayoutUtils.setPadding(m_imgLogin, 50, 0, 50, 0, true);

		LayoutUtils.setMargin(m_editName, 130, 100, 130, 0, true);
		m_editName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editName, 50, 0, 50, 10, true);

		LayoutUtils.setMargin(m_editPassword, 20, 80, 20, 0, true);
		m_editPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editPassword, 20, 0, 20, 0, true);
		
		LayoutUtils.setMargin(m_btnLogin, 130, 80, 150, 0, true);
		LayoutUtils.setSize(m_btnLogin, LayoutParams.MATCH_PARENT, 140, true);
		m_btnLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(m_txtRegister, 130, 80, 30, 0, true);
		m_txtRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtRegister, 20, 0, 20, 0, true);

		LayoutUtils.setMargin(m_txtForgot, 30, 80, 130, 0, true);
		m_txtForgot.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtForgot, 20, 0, 20, 0, true);
	 }
	 
	 private void login(final String username, final String password)
	 {	
		 DataUtils.savePreference(Const.USERNAME, username);
		 DataUtils.savePreference(Const.PASSWORD, password);
		 
		 gotoChattingPage();
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
//				DataUtils.savePreference(Const.LOGIN_OK, "0");
//				
//				if( result.mResult == LogicResult.RESULT_NO_VERIFIED )
//				{
//					DataUtils.savePreference(Const.VCODE, result.getContentData().optString("vcode", ""));
//					gotoVerifyPage();
//					return;
//				}
//				else
//				{
//					DialogFactory.getInstance().showMessageDialog(LoginActivity.this, result.mMessage, false, null);
//				}
//			}
//		});
	 }

	 
//	 private void gotoVerifyPage()
//	 {
//		DialogFactory.getInstance().showMessageDialog(this, "We will send the verify code to this phone number.", false, new ItemCallBack() {
//			
//			@Override
//			public void doClick(ItemResult result) {
//				Bundle bundle = new Bundle();
//				ActivityManager.changeActivity(LoginActivity.this, MainMenuActivity.class, bundle, false, null);		
//			}
//		});
//	 }
	 
	 private void gotoChattingPage()
	 {		 
		 Bundle bundle = new Bundle();
//		 ActivityManager.changeActivity(this, MainMenuActivity.class, bundle, true, null );
	 }

}
