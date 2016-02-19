
package com.sin.quian.pages;

import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import common.component.ui.MyButton;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.list.adapter.ItemCallBack;
import common.list.adapter.ItemResult;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class RegisterActivity extends HeaderBarActivity
{
	static final int DIALOG_PAUSED_ID = 0;
	static final int DIALOG_GAMEOVER_ID = 1;
	
	EditText 		m_txtName = null;
	EditText 		m_txtPassword = null;
	EditText 		m_txtEmail= null;
	EditText 		m_txtPhoneNumber = null;
	MyButton		m_btnRegister = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_register);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_txtName = (EditText) findViewById(R.id.txt_register_username);
		m_txtPassword = (EditText) findViewById(R.id.txt_register_password);
		m_txtEmail = (EditText) findViewById(R.id.txt_register_email);
		m_txtPhoneNumber = (EditText) findViewById(R.id.txt_register_phone);
		m_btnRegister = (MyButton) findViewById(R.id.btn_register);
	}

	protected void initData()
	{
		super.initData();
		m_btnRight.setVisibility(View.INVISIBLE);
		m_txtPageTitle.setText("Register");
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
		register();
//		DialogFactory.getInstance().showMessageDialog(this, "Welcome", false, new ItemCallBack() {
//			
//			@Override
//			public void doClick(ItemResult result) {
				Bundle bundle = new Bundle();
				ActivityManager.changeActivity(RegisterActivity.this, MainMenuActivity.class, bundle, false, null );		
//			}
//		});

	}
	
	private void register()
	{
		String username = "";
		String fullname = "";
		String password = "";
		String email = "";
		String phone = "";
		String thumb = "";
		String birth = "";
		String addr = "";
		
		showProgress("Loading", "Please wait");
		
		ServerManager.register(username, fullname, password, email, phone, thumb, birth, addr, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();				
			}
		});
	}

	protected void layoutControls()
	{
		super.layoutControls();

		LayoutUtils.setMargin(m_txtName, 130, 300, 130, 0, true);
		m_txtName.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtName, 50, 0, 50, 10, true);

		LayoutUtils.setMargin(m_txtPassword, 20, 150, 20, 0, true);
		m_txtPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtPassword, 20, 0, 20, 0, true);
		
		LayoutUtils.setMargin(m_txtEmail, 20, 150, 20, 0, true);
		m_txtEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtEmail, 20, 0, 20, 0, true);
		
		LayoutUtils.setMargin(m_txtPhoneNumber, 20, 150, 20, 0, true);
		m_txtPhoneNumber.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtPhoneNumber, 20, 0, 20, 0, true);
 
		LayoutUtils.setMargin(m_btnRegister, 130, 200, 130, 0, true);
		LayoutUtils.setSize(m_btnRegister, LayoutParams.MATCH_PARENT, 140, true);
		m_btnRegister.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
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
