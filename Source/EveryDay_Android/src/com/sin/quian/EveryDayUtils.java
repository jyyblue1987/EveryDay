package com.sin.quian;

import org.json.JSONObject;

import common.library.utils.CheckUtils;

public class EveryDayUtils {
	public static final String getName(JSONObject profile)
	{
		if( profile == null )
			return "";
		
		String username = profile.optString(Const.USERNAME, ""); 
		if( CheckUtils.isNotEmpty(username) )
			return username;
		
		String email = profile.optString(Const.EMAIL, "");
		return email;
	}
}
