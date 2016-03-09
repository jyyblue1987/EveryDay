package com.sin.quian.pages;

import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;

import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import common.component.ui.MyCheckBox;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.DataUtils;
import common.manager.activity.ActivityManager;

public class LanguageActivity extends BaseActivity {
	RadioGroup		m_radioLanguage;
	RadioButton		m_radioChinese;
	RadioButton		m_radioEnglish;
	
	MyCheckBox		m_chkAgree;
	TextView		m_txtAgree;
	
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
		m_radioLanguage = (RadioGroup) findViewById(R.id.radiogroup_language);
		m_radioChinese = (RadioButton) findViewById(R.id.radio_chinese);
		m_radioEnglish = (RadioButton) findViewById(R.id.radio_english);
		
		m_btnStart = (Button) findViewById(R.id.btn_start);
		m_chkAgree = (MyCheckBox) findViewById(R.id.chk_agree);
		m_txtAgree = (TextView) findViewById(R.id.txt_agree);
	}
	
	protected void layoutControls()
	{
 		// Checkbox Phase
// 		LayoutUtils.setSize(m_radioChinese, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
 		LayoutUtils.setMargin(findViewById(R.id.radiogroup_language), 0, 500, 0, 0, true);
 		m_radioChinese.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
 		LayoutUtils.setPadding(m_radioChinese, 100, 0, 0, 0, true);
// 		
 		
// 		LayoutUtils.setSize(m_radioEnglish, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true);
 		LayoutUtils.setMargin(m_radioEnglish, 0, 36, 0, 0, true);
 		m_radioEnglish.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
 		LayoutUtils.setPadding(m_radioEnglish, 100, 0, 0, 0, true);
 		
		LayoutUtils.setMargin(m_btnStart, 150, 700, 150, 0, true);
		LayoutUtils.setSize(m_btnStart, LayoutParams.MATCH_PARENT, 114, true);
		m_btnStart.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		
		LayoutUtils.setMargin(findViewById(R.id.lay_license), 0, 86, 0, 0, true);
		
 		LayoutUtils.setSize(m_chkAgree, LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, true); 		
 		m_chkAgree.setTextSize(ScreenAdapter.computeHeight(57));
 		
 		m_txtAgree.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
 		
	}
	
	protected void initData()
	{
		m_radioChinese.setText("简体中文");	
		m_radioEnglish.setText("ENGLISH");

		int lang = DataUtils.getPreference(Const.LANGUAGE, 1);
		DataUtils.savePreference(Const.LANGUAGE, lang);
		
		LocaleFactory.selectLocale(lang);
		if( lang == 0 )
		{
			m_radioEnglish.setChecked(true);
			m_radioChinese.setChecked(false);
		}
		else
		{
			m_radioEnglish.setChecked(false);
			m_radioChinese.setChecked(true);
		}
		
		Locale locale = LocaleFactory.getLocale();
		
		String mystring=new String(locale.AgreeOption);
		SpannableString content = new SpannableString(mystring);
		content.setSpan(new UnderlineSpan(), 0, mystring.length(), 0);
		m_txtAgree.setText(content);
		
		if( DataUtils.getPreference(Const.LICENSE_AGREE, 0) == 1 )
		{
			m_chkAgree.setVisibility(View.GONE);
			m_txtAgree.setVisibility(View.GONE);
		}
		
		m_btnStart.setText(locale.Start);
	}
	
	protected void initEvents()
	{
		m_btnStart.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoLoginPage();	
			}
		});	
		
		m_radioLanguage.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if( checkedId == R.id.radio_chinese )
				{
					DataUtils.savePreference(Const.LANGUAGE, 1);
				}
				if( checkedId == R.id.radio_english )
				{
					DataUtils.savePreference(Const.LANGUAGE, 0);
				}
				initData();
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
		
		m_txtAgree.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				gotoLicensePage();
			}
		});
	}
	
	private void gotoLoginPage()
	{
		if( DataUtils.getPreference(Const.LICENSE_AGREE, 0) == 0 )
			return;
		
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, LoginActivity.class, bundle, true, null );		
	}
	
	private void gotoLicensePage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, LicenseActivity.class, bundle, false, null );		
	}

}
