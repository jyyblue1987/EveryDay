package com.sin.quian.thirdparty;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.network.ServerManager;
import com.sin.quian.pages.BaseActivity;
import com.sin.quian.pages.LoginActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class Facebook {
	static Activity  parent = null;
	static CallbackManager callbackManager = null;
	public static void login(Activity context)
	{
		callbackManager = CallbackManager.Factory.create();
		
		parent = context;
		
		AccessToken accessToken = AccessToken.getCurrentAccessToken();
	    if(accessToken != null && accessToken.isExpired() == false){        	
	    	getProfile(accessToken);
	    }else{
		      LoginManager manager = LoginManager.getInstance();
		      
		      List<String> pubpermissions = new ArrayList<String>();
		      pubpermissions.add("email");
		      pubpermissions.add("user_friends");

		      manager.logInWithReadPermissions(context, pubpermissions);
		      
		      try {
			      manager.registerCallback(callbackManager,
		            new FacebookCallback<LoginResult>() {
		                @Override
		                public void onSuccess(LoginResult loginResult) {
		                	//hasPublishPermission();
		                    AccessToken token = loginResult.getAccessToken();
		                    AccessToken accessToken = token.getCurrentAccessToken();
		                    
		                    getProfile(token);
		                }
		
		                @Override
		                public void onCancel() {
		                	MessageUtils.showMessageDialog(parent, "Facebook login is canceled.");
		                }
		
		                @Override
		                public void onError(FacebookException exception) {
		                    MessageUtils.showMessageDialog(parent, "Facebook login is failed.");
		                }
			      });
		      }catch(Exception e){
		    	  e.printStackTrace();
		      }
	    }
	}
	
	public static void getProfile(AccessToken accessToken)
	{
		((BaseActivity)parent).showLoadingProgress();
		GraphRequest request = GraphRequest.newMeRequest(
		        accessToken,
		        new GraphRequest.GraphJSONObjectCallback() {
		            @Override
		            public void onCompleted(
		                   JSONObject object,
		                   GraphResponse response) {
		                // Application code
		            	updateProfile(object);
		            	
		            }
		        });
		Bundle parameters = new Bundle();
		parameters.putString("fields", "id, name, email, link");
		request.setParameters(parameters);
		request.executeAsync();
	}
	
	public static void updateProfile(JSONObject profile)
	{
		if( profile == null )
		{
			((BaseActivity)parent).hideProgress();
			MessageUtils.showMessageDialog(parent, "Facebook login is failed.");
			return;
		}
		
		String id = profile.optString(Const.ID, "");
		String fullname = profile.optString("name", "");
		String email = profile.optString(Const.EMAIL, "");
		
		DataUtils.savePreference(Const.USERNAME, email);
		DataUtils.savePreference(Const.PASSWORD, id);
		
		ServerManager.loginWithFacebook(id, fullname, email, new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				((BaseActivity)parent).hideProgress();


				if( result.mResult == LogicResult.RESULT_OK )	// login ok
				{
					DataUtils.savePreference(Const.LOGIN_OK, "1");
					AppContext.setProfile(result.getContentData());
					((LoginActivity)parent).gotoMainPage();
					
					return;
				}
						
				DataUtils.savePreference(Const.LOGIN_OK, "0");			
				MessageUtils.showMessageDialog(parent, EveryDayUtils.getMessage(result.mMessage));		
			}
		});
	}
	public static void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		callbackManager.onActivityResult(requestCode, resultCode, data);
	}
	
}
