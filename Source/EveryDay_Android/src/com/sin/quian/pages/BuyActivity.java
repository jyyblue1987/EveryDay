package com.sin.quian.pages;

import org.json.JSONException;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout.LayoutParams;
import android.widget.TextView;
import common.component.ui.MyButton;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.MessageUtils;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class BuyActivity extends HeaderBarActivity
{
	EditText 		m_editStarNum= null;
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

		m_editStarNum = (EditText) findViewById(R.id.fragment_buy_num).findViewById(R.id.edit_content);
		m_btnBuy = (MyButton) findViewById(R.id.btn_buy);
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("买明星");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		m_editStarNum.setInputType(InputType.TYPE_CLASS_NUMBER);
		
		((TextView) findViewById(R.id.fragment_buy_num).findViewById(R.id.txt_label)).setText("金额");
	}
	
	protected void initEvents()
	{ 
		super.initEvents();

		m_btnBuy.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View paramView) {
				onClickBuy();				
			}
		});
	}
	
	private void onClickBuy()
	{
		String text = m_editStarNum.getText().toString();
		final int ammount = Integer.valueOf(text);
		
		showLoadingProgress();		
		ServerManager.addPoint(AppContext.getUserID(), ammount + "", new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(BuyActivity.this, result.mMessage);
					return;
				}
				
				int pointCount = AppContext.getProfile().optInt(Const.MY_POINT_NUM, 0);
				
				try {
					AppContext.getProfile().put(Const.MY_POINT_NUM, pointCount + ammount);
					
				 	Intent intent = new Intent();
			    	setResult(Activity.RESULT_OK, intent);    
			        onFinishActivity();		
				} catch (JSONException e) {
					e.printStackTrace();
				}				
			}
		});
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
