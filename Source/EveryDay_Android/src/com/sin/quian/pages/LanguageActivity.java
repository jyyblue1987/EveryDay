package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RadioButton;
import common.component.ui.MyCheckBox;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.manager.activity.ActivityManager;

public class LanguageActivity extends BaseActivity {
	RadioButton		m_radioChinese;
	RadioButton		m_radioEnglish;
	
	MyCheckBox		m_chkAgree;
	
	Button			m_btnStart;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityManager.getInstance().popAllActivity();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_language);
		
		loadComponents();		
	}
	
	protected void findViews()
	{
		m_radioChinese = (RadioButton) findViewById(R.id.radio_chinese);
		m_radioEnglish = (RadioButton) findViewById(R.id.radio_english);
		
		m_btnStart = (Button) findViewById(R.id.btn_start);
		m_chkAgree = (MyCheckBox) findViewById(R.id.chk_agree);
	}
	
	protected void layoutControls()
	{
 		// Checkbox Phase
// 		LayoutUtils.setSize(m_radioChinese, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
 		LayoutUtils.setMargin(findViewById(R.id.radiogroup_language), 0, 500, 0, 0, true);
 		m_radioChinese.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
// 		
 		
// 		LayoutUtils.setSize(m_radioEnglish, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
 		LayoutUtils.setMargin(m_radioEnglish, 0, 36, 0, 0, true);
 		m_radioEnglish.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
 		
		LayoutUtils.setMargin(m_btnStart, 0, 700, 0, 0, true);
		LayoutUtils.setSize(m_btnStart, LayoutParams.MATCH_PARENT, 114, true);
		m_btnStart.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
 		LayoutUtils.setSize(m_chkAgree, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
 		LayoutUtils.setMargin(m_chkAgree, 0, 86, 0, 0, true);
 		m_chkAgree.setTextSize(ScreenAdapter.computeHeight(57));
 		
 		
	}
	
	protected void initData()
	{
		m_radioChinese.setText("简体中文");	
		m_radioEnglish.setText("ENGLISH");

		m_chkAgree.setText("同意所有条款的选项");
	}
	
	protected void initEvents()
	{
		m_btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoLoginPage();	
			}
		});	
	}
	
	private void gotoLoginPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, LoginActivity.class, bundle, true, null );		
	}

}
