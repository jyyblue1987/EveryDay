package com.sin.quian;

import org.json.JSONArray;
import org.json.JSONObject;

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
}
