package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
import common.component.ui.MyButton;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.list.adapter.ItemCallBack;
import common.list.adapter.ItemResult;
import common.manager.activity.ActivityManager;


public class ForgotPasswordActivity extends HeaderBarActivity
{
	EditText 		m_txtLast = null;
	EditText 		m_txtNew = null;
	EditText 		m_txtConfirm = null;
	MyButton		m_btnChange = null;
	
	int [] m_field_item = {
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

		m_txtLast = (EditText) findViewById(R.id.fragment_forgot_email).findViewById(R.id.edit_content);
		m_txtNew = (EditText) findViewById(R.id.fragment_forgot_new).findViewById(R.id.edit_content);
		m_txtConfirm = (EditText) findViewById(R.id.fragment_forgot_confirm).findViewById(R.id.edit_content);
		m_btnChange = (MyButton) findViewById(R.id.btn_forgot_change);
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("忘记密码");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		((TextView) findViewById(R.id.fragment_forgot_email).findViewById(R.id.txt_label)).setText("邮箱");
		((TextView) findViewById(R.id.fragment_forgot_new).findViewById(R.id.txt_label)).setText("密码");
		((TextView) findViewById(R.id.fragment_forgot_confirm).findViewById(R.id.txt_label)).setText("确认密码");
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
		String strPassword = m_txtNew.getText().toString();
		String strConfirm = m_txtConfirm.getText().toString();
		if(strPassword.equals(strConfirm) && strPassword != null ){
		
//			DialogFactory.getInstance().showMessageDialog(this, "Changing your password is successful.", false, new ItemCallBack() {
//				
//				@Override
//				public void doClick(ItemResult result) {
					Bundle bundle = new Bundle();
					ActivityManager.changeActivity(ForgotPasswordActivity.this, LoginActivity.class, bundle, false, null );		
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
