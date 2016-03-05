package com.sin.quian.pages;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;
import com.sin.quian.thirdparty.Facebook;
import com.sin.quian.thirdparty.SinaWeibo;

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
	
	MyButton		m_btnFastLogin = null;
	
	ImageView 		m_imgFacebook = null;
	ImageView 		m_imgWeibo = null;
	
	MyButton		m_btnPreview = null;
	
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
		
		m_editName = (EditText) findViewById(R.id.edit_username);
		m_editPassword = (EditText) findViewById(R.id.edit_password);
		m_btnLogin = (MyButton) findViewById(R.id.btn_login);
		m_txtRegister = (MyTextView) findViewById(R.id.txt_login_register);		
		m_txtForgot = (MyTextView) findViewById(R.id.txt_login_forgot);
		
		m_btnFastLogin = (MyButton) findViewById(R.id.btn_fastlogin);
		
		m_imgFacebook = (ImageView) findViewById(R.id.img_facebook);
		m_imgWeibo = (ImageView) findViewById(R.id.img_weibo);
		
		m_btnPreview = (MyButton) findViewById(R.id.btn_preview);
	}

	protected void initData()
	{
		super.initData();
		
		String username = DataUtils.getPreference(Const.USERNAME, "");
		String password = DataUtils.getPreference(Const.PASSWORD, "");
		 
		m_editName.setText(username);
		m_editPassword.setText(password);
		m_txtPageTitle.setText("登录");
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
		
		m_btnFastLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
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
				SinaWeibo.login(LoginActivity.this);
			}
		});
		
		ResourceUtils.addClickEffect(m_btnPreview);
		m_btnPreview.setOnClickListener(new View.OnClickListener() {
			
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
		
		LayoutUtils.setPadding(findViewById(R.id.lay_container), 60, 100, 60, 144, true);

		
		m_editName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editName, 65, 36, 65, 36, true);
		
		LayoutUtils.setMargin(m_editPassword, 0, 60, 0, 0, true);
		m_editPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editPassword, 65, 36, 65, 36, true);
				
		LayoutUtils.setMargin(m_btnLogin, 0, 130, 0, 0, true);
		LayoutUtils.setSize(m_btnLogin, LayoutParams.MATCH_PARENT, 114, true);
		m_btnLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(findViewById(R.id.lay_login), 0, 25, 0, 0, true);
		
		m_txtRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));	
		m_txtForgot.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(m_btnFastLogin, 0, 200, 0, 0, true);
		m_btnFastLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(findViewById(R.id.lay_third_party), 170, 60, 170, 0, true);
		LayoutUtils.setSize(m_imgFacebook, 180, 180, true);
		LayoutUtils.setSize(m_imgWeibo, 180, 180, true);
		
		LayoutUtils.setMargin(m_btnPreview, 0, 150, 0, 0, true);
		m_btnPreview.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setSize(m_btnPreview, 364, 98, true);
		
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
        SinaWeibo.onActivityResult(requestCode, resultCode, data);
    }


}
