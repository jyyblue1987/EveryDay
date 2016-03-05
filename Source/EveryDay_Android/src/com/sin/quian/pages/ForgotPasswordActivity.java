package com.sin.quian.pages;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.component.ui.MyButton;
import common.component.ui.MyCheckBox;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class ForgotPasswordActivity extends HeaderBarActivity
{
	EditText 		m_editEmail = null;
	EditText 		m_editPassword = null;
	EditText 		m_editConfirmPassword = null;
	EditText 		m_editVerifyCode = null;
	
	Button			m_btnSendVerify = null;
	Button			m_btnChange = null;
	
	Button			m_btnRetureLogin = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_forgotpassword);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_editEmail = (EditText) findViewById(R.id.edit_email);
		m_editPassword = (EditText) findViewById(R.id.edit_password);
		m_editConfirmPassword = (EditText) findViewById(R.id.edit_confirm_password);
		
		m_editVerifyCode = (EditText) findViewById(R.id.edit_verify);
		m_btnSendVerify = (Button) findViewById(R.id.btn_verify_send);
	
		m_btnChange = (Button) findViewById(R.id.btn_forgot_change);
		m_btnRetureLogin = (Button) findViewById(R.id.btn_returnlogin);
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("忘记密码");
		m_btnRight.setVisibility(View.INVISIBLE);
	}
	
	protected void layoutControls()
	{
		super.layoutControls();

		LayoutUtils.setPadding(findViewById(R.id.lay_container), 60, 100, 60, 144, true);
		
		m_editEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editEmail, 65, 36, 65, 36, true);
		
		LayoutUtils.setMargin(m_editPassword, 0, 60, 0, 0, true);
		m_editPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editPassword, 65, 36, 65, 36, true);
		
		LayoutUtils.setMargin(m_editConfirmPassword, 0, 60, 0, 0, true);
		m_editConfirmPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editConfirmPassword, 65, 36, 65, 36, true);
		
		LayoutUtils.setMargin(findViewById(R.id.lay_verify), 0, 60, 0, 0, true);
		
		m_editVerifyCode.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_editVerifyCode, 65, 28, 65, 28, true);

		LayoutUtils.setSize(m_btnSendVerify, 0, 114, true);
		LayoutUtils.setMargin(m_btnSendVerify, 7, 0, 0, 0, true);
		m_btnSendVerify.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		
		LayoutUtils.setMargin(m_btnChange, 0, 130, 0, 0, true);
		LayoutUtils.setSize(m_btnChange, LayoutParams.MATCH_PARENT, 114, true);
		m_btnChange.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		
		LayoutUtils.setMargin(m_btnRetureLogin, 0, 500, 0, 0, true);
		m_btnRetureLogin.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));		
	}
	
	protected void initEvents()
	{ 
		super.initEvents();

		m_btnChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickforgot();				
			}
		});
		
		m_btnRetureLogin.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				onFinishActivity();				
			}
		});
	}
	
	private void onClickforgot()
	{
		String strEmail = m_editEmail.getText().toString();
		String strPassword = m_editPassword.getText().toString();
		String strConfirm = m_editConfirmPassword.getText().toString();
		String strUserName = strEmail + "";
		if(strPassword.equals(strConfirm) && strPassword != null ){
			 showLoadingProgress();
			 ServerManager.forgotPassword(strUserName, strEmail, strPassword, new ResultCallBack() {
				 
				@Override
				public void doAction(LogicResult result) {
					hideProgress();
					

					if( result.mResult == LogicResult.RESULT_OK )	// ok
					{
						DataUtils.savePreference(Const.LOGIN_OK, "1");
						AppContext.setProfile(result.getContentData());
						MessageUtils.showMessageDialog(ForgotPasswordActivity.this, result.mMessage);
						
						return;
					}
							
					DataUtils.savePreference(Const.LOGIN_OK, "0");			
					MessageUtils.showMessageDialog(ForgotPasswordActivity.this, result.mMessage);
				}
			});

//			Bundle bundle = new Bundle();
//			ActivityManager.changeActivity(ForgotPasswordActivity.this, LoginActivity.class, bundle, false, null );		
		}
	}
 

}
