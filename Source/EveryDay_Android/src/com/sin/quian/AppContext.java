package com.sin.quian;

import org.json.JSONObject;

import com.sin.quian.network.ServerManager;

import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;

public class AppContext {
	private static JSONObject g_MyProfile = new JSONObject();
	
	public static void setProfile(JSONObject profile)
	{
		if( profile != null)
			g_MyProfile = profile;
	}
	
	public static JSONObject getProfile()
	{
		return g_MyProfile;
	}
	
	public static String getUserID()
	{
		if( g_MyProfile == null )
			return "0";
		
		return g_MyProfile.optString("id", "0");
	}
	
	public static void refreshProfile()
	{
		ServerManager.getProfile(AppContext.getUserID(), new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				if( result.mResult != LogicResult.RESULT_OK )
					return;
				
				setProfile(result.getContentData());
			}
		});
	}	
	
}
