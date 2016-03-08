
		package com.sin.quian.pages;

		import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.component.ui.MyButton;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


		public class ChangePasswordActivity extends HeaderBarActivity
		{
			EditText 		m_txtLast = null;
			EditText 		m_txtNew = null;
			EditText 		m_txtConfirm = null;
			MyButton		m_btnChange = null;
			
			Boolean 		m_boolChangePasswordState = false;
			
			int [] m_field_item = {
					R.id.fragment_change_last,
					R.id.fragment_change_new,
					R.id.fragment_change_confirm,
				};

			
			@Override
			protected void onCreate(Bundle savedInstanceState) {
				super.onCreate(savedInstanceState);
				setContentView(R.layout.layout_changepassword);
				loadComponents();
			}
			
			protected void findViews()
			{
				super.findViews();

				m_txtLast = (EditText) findViewById(R.id.fragment_change_last).findViewById(R.id.edit_content);
				m_txtNew = (EditText) findViewById(R.id.fragment_change_new).findViewById(R.id.edit_content);
				m_txtConfirm = (EditText) findViewById(R.id.fragment_change_confirm).findViewById(R.id.edit_content);
				m_btnChange = (MyButton) findViewById(R.id.btn_change);
			}

			protected void initData()
			{
				super.initData();
				m_txtPageTitle.setText("修改密码");
				m_btnRight.setVisibility(View.INVISIBLE);
				
				((TextView) findViewById(R.id.fragment_change_last).findViewById(R.id.txt_label)).setText("旧密码");
				((TextView) findViewById(R.id.fragment_change_new).findViewById(R.id.txt_label)).setText("新的密码");
				((TextView) findViewById(R.id.fragment_change_confirm).findViewById(R.id.txt_label)).setText("确认密码");

			}
			
			protected void initEvents()
			{ 
				super.initEvents();

				m_btnChange.setOnClickListener(new View.OnClickListener() {
					
					@Override
					public void onClick(View paramView) {
						onClickchange();				
					}
				});
			}
			
			private void onClickchange()
			{
				String oldPassword = m_txtLast.getText().toString();
				final String newPassword = m_txtNew.getText().toString();
				String confirmPassword = m_txtConfirm.getText().toString();
				if(newPassword.equals(confirmPassword) && newPassword != null && oldPassword != null ){
					showLoadingProgress();
					ServerManager.changePassword(AppContext.getUserID(), oldPassword, newPassword, new ResultCallBack() {
						
						@Override
						public void doAction(LogicResult result) {
							hideProgress();
							if( result.mResult != LogicResult.RESULT_OK )
							{
								MessageUtils.showMessageDialog(ChangePasswordActivity.this, EveryDayUtils.getMessage(result.mMessage));
								return;
							}
							
							DataUtils.savePreference(Const.PASSWORD, newPassword);
							
							m_boolChangePasswordState = true;
							Bundle bundle = new Bundle();
							ActivityManager.changeActivity(ChangePasswordActivity.this, ProfileActivity.class, bundle, false, null );	

						}
					});
				}else {
					MessageUtils.showMessageDialog(this, "请输入密码.");
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
