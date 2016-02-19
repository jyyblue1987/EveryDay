package com.sin.quian.pages;

import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import common.library.utils.AlgorithmUtils;
import common.library.utils.CheckUtils;
import common.library.utils.DataUtils;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class SplashActivity extends BaseActivity {
	ImageView m_imgSplash = null;
	
	int m_nTotalProcCount = 2;
	int m_nProcessedCount = 0;
	
	int m_nLoginState = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		ActivityManager.getInstance().popAllActivity();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_splash);
		
		loadComponents();		
	}
	
	protected void findViews()
	{
		m_imgSplash = (ImageView) findViewById(R.id.img_splash);
	}
	
	protected void initData()
	{
		m_nProcessedCount = 0;
	
		startAlphaAnimation();
	}
	
	protected void onServiceConnected() {
		autoLogin();		
    }
	
	private void autoLogin()
	{
		String loginOK = DataUtils.getPreference(Const.LOGIN_OK, "0");
		
		final String countryCode = DataUtils.getPreference(Const.COUNTRY_CODE, "");
		final String mobile = DataUtils.getPreference(Const.MOBILE, "");
		if( CheckUtils.isEmpty(countryCode) || CheckUtils.isEmpty(mobile) || loginOK.equals("1") == false )
		{
			m_nLoginState = 0; // not auto login
		}
		else
		{
			loginChatServer(countryCode, mobile);
			ServerManager.login(countryCode, mobile, DataUtils.getPreference(Const.GCM_PUSH_KEY, ""), new ResultCallBack() {
				
				@Override
				public void doAction(LogicResult result) {
					if( result.mResult == LogicResult.RESULT_NO_VERIFIED || result.mResult == LogicResult.RESULT_NO_USER_EXIST )
					{
						DataUtils.savePreference(Const.LOGIN_OK, "0");
					}
				}
			});
			
			m_nLoginState = 1; // goto chatting page.	
		}
				
		m_nProcessedCount++;
		onFinishAnimation();	
	}
	
	 private void loginChatServer(final String countryCode, final String mobile)
	 {
		 String username = countryCode + "_" + mobile;
		 String password = AlgorithmUtils.invert(username);
	 }
	private void startAlphaAnimation()
	{
		AlphaAnimation face_in_out_anim = new AlphaAnimation(0.1f, 1.0f);
		face_in_out_anim.setDuration(1000);     
		face_in_out_anim.setRepeatMode(Animation.REVERSE);
		
		if (m_imgSplash != null){
			m_imgSplash.setAnimation(face_in_out_anim);
		}
		face_in_out_anim.start(); 		
		
		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				m_nProcessedCount++;
				onFinishAnimation();
			}
		}, 1500);
	}
	
	private void onFinishAnimation()
	{
//		if( m_nProcessedCount < m_nTotalProcCount )
//			return;
		
//		if( m_nLoginState == 0 )
//			gotoSignUpPage();
//		else
//			gotoChattingPage();
			gotoLoginPage();
	}
	
	private void gotoLoginPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, LoginActivity.class, bundle, true, null );		
	}
	
//	private void gotoSignUpPage()
//	{
//		Bundle bundle = new Bundle();
//		ActivityManager.changeActivity(this, SignUpActivity.class, bundle, true, null );		
//	}
//	
//	private void gotoChattingPage()
//	{		 
//		 Bundle bundle = new Bundle();
//		 ActivityManager.changeActivity(this, ChattingHistoryActiviry.class, bundle, true, null );
//	}
}
