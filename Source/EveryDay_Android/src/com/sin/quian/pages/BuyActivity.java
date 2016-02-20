package com.sin.quian.pages;

import com.sin.quian.R;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.component.ui.MyButton;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.manager.activity.ActivityManager;


public class BuyActivity extends HeaderBarActivity
{
	EditText 		m_txtStarNum= null;
	MyButton		m_btnBuy= null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_buy);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_txtStarNum = (EditText) findViewById(R.id.fragment_buy_num).findViewById(R.id.edit_content);
		m_btnBuy = (MyButton) findViewById(R.id.btn_buy);
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("购买");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		((TextView) findViewById(R.id.fragment_buy_num).findViewById(R.id.txt_label)).setText("购买");

	}
	
	protected void initEvents()
	{ 
		super.initEvents();

		m_btnBuy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickchange();				
			}
		});
	}
	
	private void onClickchange()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(BuyActivity.this, ProfileActivity.class, bundle, false, null );		
	}
 
	protected void layoutControls()
	{
		super.layoutControls();
		LayoutUtils.setMargin(findViewById(R.id.fragment_buy_num), 80, 200, 80, 0, true);
		LayoutUtils.setPadding(findViewById(R.id.fragment_buy_num).findViewById(R.id.lay_info), 20, 0, 20, 0, true);
		
		((TextView)findViewById(R.id.fragment_buy_num).findViewById(R.id.txt_label)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
		((TextView)findViewById(R.id.fragment_buy_num).findViewById(R.id.edit_content)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));

		LayoutUtils.setMargin(m_btnBuy, 80, 170, 80, 100, true);
		LayoutUtils.setSize(m_btnBuy, LayoutParams.MATCH_PARENT, 114, true);
		m_btnBuy.setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeHeight(57));
	}
}
