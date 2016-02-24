package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;
import com.sin.quian.thirdparty.Facebook;

import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
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
import common.design.utils.ResourceUtils;
import common.library.utils.CheckUtils;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


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
	
	ImageView 		m_imgFacebook = null;
	ImageView 		m_imgWeibo = null;
	
	int [] m_field_item = {
			R.id.fragment_username,
			R.id.fragment_password,
		};
	
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
		
		m_editName = (EditText) findViewById(R.id.fragment_username).findViewById(R.id.edit_content);
		m_editPassword = (EditText) findViewById(R.id.fragment_password).findViewById(R.id.edit_content);
		m_btnLogin = (MyButton) findViewById(R.id.btn_login);
		m_txtRegister = (MyTextView) findViewById(R.id.txt_login_register);
		m_txtForgot = (MyTextView) findViewById(R.id.txt_login_forgot);
		
		m_imgFacebook = (ImageView) findViewById(R.id.img_facebook);
		m_imgWeibo = (ImageView) findViewById(R.id.img_weibo);
	}

	protected void initData()
	{
		super.initData();
		
		String username = DataUtils.getPreference(Const.USERNAME, "");
		String password = DataUtils.getPreference(Const.PASSWORD, "");
		 
		m_editName.setText(username);
		m_editPassword.setText(password);
		m_txtPageTitle.setText("登录");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		((TextView) findViewById(R.id.fragment_username).findViewById(R.id.txt_label)).setText("用户名");
		((TextView) findViewById(R.id.fragment_password).findViewById(R.id.txt_label)).setText("密码");
		
		((EditText) findViewById(R.id.fragment_password).findViewById(R.id.edit_content)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();
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
		
		ResourceUtils.addClickEffect(m_imgFacebook);
		m_imgFacebook.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Facebook.login(LoginActivity.this);
			}
		});
		
		ResourceUtils.addClickEffect(m_imgWeibo);
		m_imgWeibo.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
			}
		});
	}
	
	 private void onClickLogin()
	 {
		 String username = m_editName.getText().toString();
		 if( CheckUtils.isEmpty(username) )
		 {
			 MessageUtils.showMessageDialog(this, "您必须输入用户名.");
			 return;
		 }
		 String password = m_editPassword.getText().toString();
		 if( CheckUtils.isEmpty(password) )
		 {
			 MessageUtils.showMessageDialog(this, "您必须输入密码.");
			 return;
		 }
		 login(username, password);
	 }

	 private void onClickRegister()
	 {
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(LoginActivity.this, RegisterActivity.class, bundle, false, null);		
	 }

	 private void onClickForgot()
	 {
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(LoginActivity.this, ForgotPasswordActivity.class, bundle, false, null );		
	 }

	
	protected void layoutControls()
	{
		super.layoutControls();

		LayoutUtils.setSize(m_imgLogin, 500, 500, true);
		LayoutUtils.setMargin(m_imgLogin, 70, 170, 70, 0, true);
		LayoutUtils.setPadding(m_imgLogin, 50, 0, 50, 0, true);
		
		LayoutUtils.setMargin(m_imgLogin, 0, 80, 0, 100, true);
		LayoutUtils.setSize(m_imgLogin, 300, 300, true);

		for(int i = 0; i < m_field_item.length; i++ )
		{
			LayoutUtils.setMargin(findViewById(m_field_item[i]), 80, 0, 80, 100, true);
			LayoutUtils.setPadding(findViewById(m_field_item[i]).findViewById(R.id.lay_info), 20, 0, 20, 0, true);
			
			((TextView)findViewById(m_field_item[i]).findViewById(R.id.txt_label)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
			((TextView)findViewById(m_field_item[i]).findViewById(R.id.edit_content)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		}
		
		LayoutUtils.setMargin(m_btnLogin, 80, 130, 80, 0, true);
		LayoutUtils.setSize(m_btnLogin, LayoutParams.MATCH_PARENT, 114, true);
		m_btnLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(findViewById(R.id.lay_login), 80, 25, 80, 100, true);
		
		m_txtRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));	
		m_txtForgot.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(findViewById(R.id.lay_third_party), 80, 100, 80, 100, true);
		LayoutUtils.setSize(m_imgFacebook, 120, 120, true);
		LayoutUtils.setSize(m_imgWeibo, 120, 120, true);
	 }
	 
	 private void login(final String username, final String password)
	 {
		 DataUtils.savePreference(Const.USERNAME, username);
		 DataUtils.savePreference(Const.PASSWORD, password);
			
		 showLoadingProgress();
		 ServerManager.login(username, password, DataUtils.getPreference(Const.GCM_PUSH_KEY, ""), new ResultCallBack() {
			 
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				

				if( result.mResult == LogicResult.RESULT_OK )	// login ok
				{
					DataUtils.savePreference(Const.LOGIN_OK, "1");
					AppContext.setProfile(result.getContentData());
					gotoMainPage();
					
					return;
				}
						
				DataUtils.savePreference(Const.LOGIN_OK, "0");			
				MessageUtils.showMessageDialog(LoginActivity.this, result.mMessage);
			}
		});
	 }
	 
	public void gotoMainPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, MainMenuActivity.class, bundle, true, null );
	}
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Facebook.onActivityResult(requestCode, resultCode, data);
        
    }
    private void loginFB(){
    	
    }

}
