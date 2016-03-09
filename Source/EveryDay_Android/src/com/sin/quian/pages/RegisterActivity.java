
package com.sin.quian.pages;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.network.ServerManager;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.component.ui.MyCheckBox;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.CheckUtils;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class RegisterActivity extends HeaderBarActivity
{
	ImageView		m_imgPhotoIcon = null;
	EditText 		m_editEmail = null;
	EditText 		m_editPassword = null;
	EditText 		m_editConfirmPassword = null;
	
	Button			m_btnRegister = null;
	
	MyCheckBox 		m_chkAgree = null;
	TextView 		m_txtAgree = null;
	
	Button			m_btnRetureLogin = null;

	
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
		
		m_editEmail = (EditText) findViewById(R.id.edit_email);
		m_editPassword = (EditText) findViewById(R.id.edit_password);
		m_editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
		
		m_btnRegister = (Button) findViewById(R.id.btn_register);
		
		m_chkAgree = (MyCheckBox) findViewById(R.id.chk_agree);
		m_txtAgree = (TextView) findViewById(R.id.txt_agree);
		
		m_btnRetureLogin = (Button) findViewById(R.id.btn_returnlogin);
	}
	
	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setPadding(findViewById(R.id.lay_container), 60, 100, 60, 144, true);
		
		LayoutUtils.setMargin(m_imgPhotoIcon, 0, 80, 0, 100, true);
		LayoutUtils.setSize(m_imgPhotoIcon, 300, 300, true);
		
		m_editEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editEmail, 65, 36, 65, 36, true);
		
		LayoutUtils.setMargin(m_editPassword, 0, 60, 0, 0, true);
		m_editPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editPassword, 65, 36, 65, 36, true);
		
		LayoutUtils.setMargin(m_editConfirmPassword, 0, 60, 0, 0, true);
		m_editConfirmPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editConfirmPassword, 65, 36, 65, 36, true);
		
		LayoutUtils.setMargin(m_btnRegister, 0, 130, 0, 0, true);
		LayoutUtils.setSize(m_btnRegister, LayoutParams.MATCH_PARENT, 114, true);
		m_btnRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		
		LayoutUtils.setMargin(findViewById(R.id.lay_agree), 0, 100, 0, 0, true);
		if( LocaleFactory.getLanguage() == 0 )
			((LinearLayout)findViewById(R.id.lay_agree)).setOrientation(LinearLayout.VERTICAL);	
		
		
 		LayoutUtils.setSize(m_chkAgree, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
 		m_chkAgree.setTextSize(ScreenAdapter.computeHeight(45)); 		
 		LayoutUtils.setMargin(m_txtAgree, 5, 0, 0, 0, true);
 		m_txtAgree.setTextSize(ScreenAdapter.computeHeight(45));
		
		LayoutUtils.setMargin(m_btnRetureLogin, 0, 500, 0, 0, true);
		m_btnRetureLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));		
	}
	
	protected void initData()
	{
		super.initData();
		
		if( DataUtils.getPreference(Const.LICENSE_AGREE, 0) == 1 )
		{
			m_chkAgree.setVisibility(View.GONE);
			m_txtAgree.setVisibility(View.GONE);
		}	
	}
	
	protected void showLabels()
	{		
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.RegisterText);
		
		m_editEmail.setHint(locale.EmailText);
		m_editPassword.setHint(locale.PasswordText);
		m_editConfirmPassword.setHint(locale.ConfirmPW);
		
		m_btnRegister.setText(locale.RegisterText);
		
		m_chkAgree.setText(locale.ReadAgree);
		m_txtAgree.setText(locale.AllTerms);
		
		m_btnRetureLogin.setText(locale.ExistUser);
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
		
		m_btnRetureLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onFinishActivity();				
			}
		});
		
		m_txtAgree.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoLicensePage();
			}
		});
		
		m_chkAgree.getCheckBox().setOnCheckedChangeListener(new android.widget.CompoundButton.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				if( isChecked == true )
					DataUtils.savePreference(Const.LICENSE_AGREE, 1);
				else
					DataUtils.savePreference(Const.LICENSE_AGREE, 0);
			}
		});
	}
	
	private void onClickRegister()
	{
		if( DataUtils.getPreference(Const.LICENSE_AGREE, 0) == 0 )
			return;

		String email = m_editEmail.getText().toString();
		String password = m_editPassword.getText().toString();
		String confirmpassword = m_editConfirmPassword.getText().toString();
		
		if( CheckUtils.isEmpty(email) )
		{
			MessageUtils.showMessageDialog(this, "请输入用户邮箱.");
			return;
		}
		
		if( CheckUtils.isEmpty(password) || CheckUtils.isEmpty(confirmpassword) )
		{
			MessageUtils.showMessageDialog(this, "请输入密码.");
			return;
		}
		
		if( password.equals(confirmpassword) == false )
		{
			MessageUtils.showMessageDialog(this, "密码不匹配.");
			return;
		}
		
		register(email, password);
	}
	
	private void register(String email, String password)
	{
		 DataUtils.savePreference(Const.USERNAME, email);
		 DataUtils.savePreference(Const.EMAIL, email);
		 DataUtils.savePreference(Const.PASSWORD, password);

		 showLoadingProgress();
		
		 ServerManager.register(email, password, new ResultCallBack() {
			
			 @Override
			 public void doAction(LogicResult result) {
				 hideProgress();		
				
				 if( result.mResult != LogicResult.RESULT_OK )
				 {
					 DataUtils.savePreference(Const.LOGIN_OK, "0");
					 MessageUtils.showMessageDialog(RegisterActivity.this, EveryDayUtils.getMessage(result.mMessage));
					 return;
				 }
				 AppContext.setProfile(result.getContentData());
				 DataUtils.savePreference(Const.LOGIN_OK, "1");
				 gotoMainPage();
			 }
		 });
	}
	
	private void gotoMainPage()
	{
		ActivityManager.getInstance().popAllActivity();
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, MainMenuActivity.class, bundle, false, null );
	}
	
	private void gotoLicensePage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, LicenseActivity.class, bundle, false, null );		
	}
	
}
