package com.sin.quian.pages;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.R;
import com.sin.quian.network.ServerManager;

import android.os.Bundle;
import android.os.Handler;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.ImageView;
import common.library.utils.CheckUtils;
import common.library.utils.DataUtils;
import common.manager.activity.ActivityManager;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class SplashActivity extends BaseActivity {
	ImageView m_imgSplash = null;
	
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
		startAlphaAnimation();
		
		m_nLoginState = 0;
		autoLogin();
	}
	
	private void autoLogin()
	{
		String loginOK = DataUtils.getPreference(Const.LOGIN_OK, "0");
		
		String username = DataUtils.getPreference(Const.USERNAME, "");
		String password = DataUtils.getPreference(Const.PASSWORD, "");
		if( CheckUtils.isEmpty(username) || CheckUtils.isEmpty(password) || loginOK.equals("1") == false )
		{
			m_nLoginState = 0; // not auto login
		}
		else
		{
			m_nLoginState = 1;
//			if(ChangePasswordActivity.m_boolChangePasswordState){}
			ServerManager.login(username, password, DataUtils.getPreference(Const.GCM_PUSH_KEY, ""), new ResultCallBack() {
				
				@Override
				public void doAction(LogicResult result) {
					if( result.mResult == LogicResult.RESULT_NO_USER_EXIST )
						DataUtils.savePreference(Const.LOGIN_OK, "0");
					
					if( result.mResult != LogicResult.RESULT_OK )
					{
						gotoLoginPage();
					}
					
					AppContext.setProfile(result.getContentData());
					gotoMainPage();
				}
			});
			
		}
				
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
				onFinishAnimation();
			}
		}, 1500);
	}
	
	private void onFinishAnimation()
	{
		if( m_nLoginState == 0 )
			gotoLoginPage();
	}
	
	private void gotoLoginPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, LoginActivity.class, bundle, true, null );		
	}

	private void gotoMainPage()
	{
		Bundle bundle = new Bundle();
		ActivityManager.changeActivity(this, MainMenuActivity.class, bundle, true, null );
	}
}
