package com.sin.quian;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import common.library.utils.CheckUtils;
import common.library.utils.MyTime;

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
	
	public static final String getDate(JSONObject data)
	{
		String time = data.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {  
		    Date date = format.parse(time);  
			SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd\r\nHH:mm:ss");
			return newFormat.format(date);
		}catch (ParseException e) {  
			Date date;
			try {
				date = format.parse(MyTime.getCurrentTime());
				SimpleDateFormat newFormat = new SimpleDateFormat("yyyy-MM-dd\r\nHH:mm:ss");
				return newFormat.format(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  			
		}
		return time;
	}
	
	public static final String getDateForStage(JSONObject data)
	{
		String time = data.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {  
		    Date date = format.parse(time);  
			SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm\r\nyyyy-MM-dd");
			return newFormat.format(date);
		}catch (ParseException e) {  
			Date date;
			try {
				date = format.parse(MyTime.getCurrentTime());
				SimpleDateFormat newFormat = new SimpleDateFormat("HH:mm\r\nyyyy-MM-dd");
				return newFormat.format(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  			
		}
		return time;
	}
}
