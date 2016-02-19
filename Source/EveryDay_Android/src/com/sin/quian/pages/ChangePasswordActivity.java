package com.sin.quian.pages;

import com.sin.quian.R;

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


public class ChangePasswordActivity extends HeaderBarActivity
{
	EditText 		m_txtEmail= null;
	EditText 		m_txtPassword = null;
	EditText 		m_txtConfirm = null;
	MyButton		m_btnChange = null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_changepassword);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_txtEmail = (EditText) findViewById(R.id.txt_change_email);
		m_txtPassword = (EditText) findViewById(R.id.txt_change_password);
		m_txtConfirm = (EditText) findViewById(R.id.txt_change_confirm);
		m_btnChange = (MyButton) findViewById(R.id.btn_change);
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("Change Password");
		m_btnRight.setVisibility(View.INVISIBLE);
	}
	
	protected void initEvents()
	{ 
		super.initEvents();

		m_btnChange.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickChange();				
			}
		});
	}
	
	private void onClickChange()
	{
		String strPassword = m_txtPassword.getText().toString();
		String strConfirm = m_txtConfirm.getText().toString();
		if(strPassword.equals(strConfirm) && strPassword != null ){
		
//			DialogFactory.getInstance().showMessageDialog(this, "Changing your password is successful.", false, new ItemCallBack() {
//				
//				@Override
//				public void doClick(ItemResult result) {
					Bundle bundle = new Bundle();
					ActivityManager.changeActivity(ChangePasswordActivity.this, LoginActivity.class, bundle, false, null );		
//				}
//			});
		}else {
//			DialogFactory.getInstance().showMessageDialog(this, "Please enter the password again.", false, null);
			return;
		}
	}
 
	protected void layoutControls()
	{
		super.layoutControls();

		LayoutUtils.setMargin(m_txtEmail, 20, 350, 20, 0, true);
		m_txtEmail.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtEmail, 20, 0, 20, 0, true);
		
		LayoutUtils.setMargin(m_txtPassword, 20, 170, 20, 0, true);
		m_txtPassword.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtPassword, 20, 0, 20, 0, true);
		
		LayoutUtils.setMargin(m_txtConfirm, 20, 170, 20, 0, true);
		m_txtConfirm.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		LayoutUtils.setPadding(m_txtConfirm, 20, 0, 20, 0, true);
 
		LayoutUtils.setMargin(m_btnChange, 130, 270, 130, 0, true);
		LayoutUtils.setSize(m_btnChange, LayoutParams.MATCH_PARENT, 140, true);
		m_btnChange.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
	}
}
