package com.sin.quian.thirdparty;

import com.sina.weibo.sdk.auth.AuthInfo;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import common.library.utils.DataUtils;

public class SinaWeibo {
	static Activity  parent = null;
	
	static AuthInfo mAuthInfo;
	    
    /** 封装了 "access_token"，"expires_in"，"refresh_token"，并提供了他们的管理功能  */
	static Oauth2AccessToken mAccessToken;

    /** 注意：SsoHandler 仅当 SDK 支持 SSO 时有效 */
	static SsoHandler mSsoHandler;
	    
	static final String APP_KEY = "1321312321321321";
	static final String REDIRECT_URL = "http://localhost/phonebook";
    static final String SCOPE = 
            "email,direct_messages_read,direct_messages_write,"
            + "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
            + "follow_app_official_microblog," + "invitation_write";
	
	private static final String KEY_UID           = "uid";
    private static final String KEY_ACCESS_TOKEN  = "access_token";
    private static final String KEY_EXPIRES_IN    = "expires_in";
    private static final String KEY_REFRESH_TOKEN    = "refresh_token";

	
	public static void login(Activity context)
	{
		parent = context;
		
		mAuthInfo = new AuthInfo(context, APP_KEY, REDIRECT_URL, SCOPE);
	    mSsoHandler = new SsoHandler(context, mAuthInfo);
	    
	    try {
	    	mSsoHandler.authorizeWeb(new AuthListener());
	    } catch(Exception e) {
	    	e.printStackTrace();
	    }
	}
	
    
    public static  void onActivityResult(int requestCode, int resultCode, Intent data) {
        // SSO 授权回调
        // 重要：发起 SSO 登陆的 Activity 必须重写 onActivityResults
        if (mSsoHandler != null) {
            mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
        }
        
    }
	
	static class AuthListener implements WeiboAuthListener {
        
        @Override
        public void onComplete(Bundle values) {
            // 从 Bundle 中解析 Token
            mAccessToken = Oauth2AccessToken.parseAccessToken(values);
            //从这里获取用户输入的 电话号码信息 
            String  phoneNum =  mAccessToken.getPhoneNum();
            if (mAccessToken.isSessionValid()) {
                // 保存 Token 到 SharedPreferences
                writeAccessToken(parent, mAccessToken);
            } else {
                // 以下几种情况，您会收到 Code：
                // 1. 当您未在平台上注册的应用程序的包名与签名时；
                // 2. 当您注册的应用程序包名与签名不正确时；
                // 3. 当您在平台上注册的包名和签名与您当前测试的应用的包名和签名不匹配时。
                String code = values.getString("code");
              
            }
        }

        @Override
        public void onCancel() {
        }

        @Override
        public void onWeiboException(WeiboException e) {
        }
    }
	
	public static void writeAccessToken(Context context, Oauth2AccessToken token) {
        if (null == context || null == token) {
            return;
        }
        
        DataUtils.savePreference(KEY_UID, token.getUid());
        DataUtils.savePreference(KEY_ACCESS_TOKEN, token.getToken());
        DataUtils.savePreference(KEY_REFRESH_TOKEN, token.getRefreshToken());
        DataUtils.savePreference(KEY_EXPIRES_IN, token.getExpiresTime());
    }

    /**
     * 从 SharedPreferences 读取 Token 信息。
     * 
     * @param context 应用程序上下文环境
     * 
     * @return 返回 Token 对象
     */
    public static Oauth2AccessToken readAccessToken(Context context) {
        if (null == context) {
            return null;
        }
        
        Oauth2AccessToken token = new Oauth2AccessToken();
        token.setUid(DataUtils.getPreference(KEY_UID, ""));
        token.setToken(DataUtils.getPreference(KEY_ACCESS_TOKEN, ""));
        token.setRefreshToken(DataUtils.getPreference(KEY_REFRESH_TOKEN, ""));
        token.setExpiresTime(DataUtils.getPreference(KEY_EXPIRES_IN, 0));
        
        return token;
    }

    /**
     * 清空 SharedPreferences 中 Token信息。
     * 
     * @param context 应用程序上下文环境
     */
    public static void clear(Context context) {
        if (null == context) {
            return;
        }
        
//        SharedPreferences pref = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_APPEND);
//        Editor editor = pref.edit();
//        editor.clear();
//        editor.commit();
    }
}
