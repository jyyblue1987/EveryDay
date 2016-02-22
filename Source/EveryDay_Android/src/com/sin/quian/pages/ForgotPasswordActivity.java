package com.sin.quian.pages;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import common.component.ui.MyButton;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.list.adapter.ItemCallBack;
import common.list.adapter.ItemResult;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class ForgotPasswordActivity extends HeaderBarActivity
{
	EditText 		m_txtUserName = null;
	EditText 		m_txtEmail = null;
	EditText 		m_txtNew = null;
	EditText 		m_txtConfirm = null;
	MyButton		m_btnChange = null;
	
	int [] m_field_item = {
			R.id.fragment_forgot_username,
			R.id.fragment_forgot_email,
			R.id.fragment_forgot_new,
			R.id.fragment_forgot_confirm,
		};

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_forgotpassword);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_txtUserName = (EditText) findViewById(R.id.fragment_forgot_username).findViewById(R.id.edit_content);
		m_txtEmail = (EditText) findViewById(R.id.fragment_forgot_email).findViewById(R.id.edit_content);
		m_txtNew = (EditText) findViewById(R.id.fragment_forgot_new).findViewById(R.id.edit_content);
		m_txtConfirm = (EditText) findViewById(R.id.fragment_forgot_confirm).findViewById(R.id.edit_content);
		m_btnChange = (MyButton) findViewById(R.id.btn_forgot_change);
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("忘记了密码");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		((TextView) findViewById(R.id.fragment_forgot_username).findViewById(R.id.txt_label)).setText("用户名");
		((TextView) findViewById(R.id.fragment_forgot_email).findViewById(R.id.txt_label)).setText("邮箱");
		((TextView) findViewById(R.id.fragment_forgot_new).findViewById(R.id.txt_label)).setText("密码");
		((TextView) findViewById(R.id.fragment_forgot_confirm).findViewById(R.id.txt_label)).setText("确认密码");
		
		((EditText) findViewById(R.id.fragment_forgot_new).findViewById(R.id.edit_content)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
		((EditText) findViewById(R.id.fragment_forgot_confirm).findViewById(R.id.edit_content)).setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

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
	}
	
	private void onClickforgot()
	{
		String strUserName = m_txtUserName.getText().toString();
		String strEmail = m_txtEmail.getText().toString();
		String strPassword = m_txtNew.getText().toString();
		String strConfirm = m_txtConfirm.getText().toString();
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
 
	protected void layoutControls()
	{
		super.layoutControls();

		for(int i = 0; i < m_field_item.length; i++ )
		{
			if(i==0){
				LayoutUtils.setMargin(findViewById(m_field_item[i]), 80, 200, 80, 0, true);
			}else{
				LayoutUtils.setMargin(findViewById(m_field_item[i]), 80, 100, 80, 0, true);
			}
			LayoutUtils.setPadding(findViewById(m_field_item[i]).findViewById(R.id.lay_info), 20, 0, 20, 0, true);
			
			((TextView)findViewById(m_field_item[i]).findViewById(R.id.txt_label)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
			((TextView)findViewById(m_field_item[i]).findViewById(R.id.edit_content)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		}
		
		LayoutUtils.setMargin(m_btnChange, 80, 170, 80, 100, true);
		LayoutUtils.setSize(m_btnChange, LayoutParams.MATCH_PARENT, 114, true);
		m_btnChange.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
	}
}
