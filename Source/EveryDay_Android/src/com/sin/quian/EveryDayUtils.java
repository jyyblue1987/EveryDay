package com.sin.quian;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import common.library.utils.CheckUtils;
import common.library.utils.MediaUtils;
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
	
	public static final String getMessage(String message)
	{
		if( CheckUtils.isEmpty(message) )
			return "";
		
		String [] splite = message.split("\\|\\|");
		if( splite.length < 2 )
			return message;
		
		int lang = LocaleFactory.getLanguage();
		return splite[lang];		
	}
	public static final String getDate(JSONObject data)
	{
		String time = data.optString(Const.MODIFY_DATE, MyTime.getCurrentTime());
		
		SimpleDateFormat newFormat = null;
		if( LocaleFactory.getLanguage() == 0 )
			newFormat = new SimpleDateFormat("yyyy-MM-dd");
		else
			newFormat = new SimpleDateFormat("yyyy年MM月dd日");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {  
		    Date date = format.parse(time);  
			return newFormat.format(date);
		}catch (ParseException e) {  
			Date date;
			try {
				date = format.parse(MyTime.getCurrentTime());
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
		
		SimpleDateFormat newFormat = null;
		if( LocaleFactory.getLanguage() == 0 )
			newFormat = new SimpleDateFormat("MM-dd\r\nHH:mm:ss");
		else
			newFormat = new SimpleDateFormat("MM月dd日\r\nHH:mm:ss");
		
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		try {  
		    Date date = format.parse(time);  
			return newFormat.format(date);
		}catch (ParseException e) {  
			Date date;
			try {
				date = format.parse(MyTime.getCurrentTime());
				return newFormat.format(date);
			} catch (ParseException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}  			
		}
		return time;
	}
	
	public static int getTotalCount(JSONObject profile)
	{
		if( profile == null )
			return 0;
		
		int count = profile.optInt(Const.POINT_NUM, 0) + profile.optInt(Const.SEND_NUM, 0);
		return count;
	}
	
	public static int getLevelImage(JSONObject profile)
	{
		int count = getTotalCount(profile);
		if( count < 100 )
			return R.drawable.main_photobg1;
		
		if( count < 500 )
			return R.drawable.main_photobg2;
		if( count < 1000 )
			return R.drawable.main_photobg3;
		if( count < 3000 )
			return R.drawable.main_photobg4;
		if( count < 5000 )
			return R.drawable.main_photobg5;
		
		return R.drawable.main_photobg6;
	}
	
	public static void sendPoint(JSONObject profile, int amount)
	{
		if( profile == null )
			return;
		
		if( amount < 0 )
			return;
		
		int point = profile.optInt(Const.POINT_NUM, 0);
		int sendPoint = profile.optInt(Const.SEND_NUM, 0);
		
		if( amount > point )
			amount = point;
		
		try {
			profile.put(Const.POINT_NUM, point - amount);
			profile.put(Const.SEND_NUM, sendPoint + amount);
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	public static void addPoint(JSONObject profile, int amount)
	{
		if( profile == null )
			return;
		
		if( amount < 0 )
			return;
		
		int point = profile.optInt(Const.POINT_NUM, 0);
	
		try {
			profile.put(Const.POINT_NUM, point + amount);
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	public static void receivePoint(JSONObject profile, int amount)
	{
		if( profile == null )
			return;
		
		if( amount < 0 )
			return;
		
		int point = profile.optInt(Const.RECEIVE_NUM, 0);
		
		try {
			profile.put(Const.RECEIVE_NUM, point + amount);
		} catch (JSONException e) {
			e.printStackTrace();
		}		
	}
	
	static int picture_mode = 0;
	
	public static void showCameraGalleryPage(final Context context, final int requestCode, final String output)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		Locale locale = LocaleFactory.getLocale();

		String items[] = {locale.Camera, locale.Gallery, locale.Video};
		
		dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int whichButton) {
				picture_mode = whichButton;						
				if (picture_mode == 0) {
					MediaUtils.doTakePhotoFromCamera(context, requestCode, output+".jpg" );
				} else if (picture_mode == 1) {
					MediaUtils.doTakePhotoFromGallery(context, requestCode + 1 );
				} else if (picture_mode == 2) {
					MediaUtils.doTakeVideoFromCamera(context, requestCode + 2, output+".mp4" );
				}else if (picture_mode == 3) {
					MediaUtils.doTakeVideoFromGallery(context, requestCode + 3);
				}
				dialog.dismiss();
			}
		});
			
		dialog.create();
		AlertDialog alertDialog = dialog.show();
		
		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
	}
	
	public static void showPicturePage(final Context context, final int requestCode, final String output)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		Locale locale = LocaleFactory.getLocale();
		String items[] = {locale.Camera, locale.Gallery};
		
		dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int whichButton) {
				picture_mode = whichButton;						
				if (picture_mode == 0) {
					MediaUtils.doTakePhotoFromCamera(context, requestCode, output );
				} else if (picture_mode == 1) {
					MediaUtils.doTakePhotoFromGallery(context, requestCode + 1 );
				}
				dialog.dismiss();
			}
		});
			
		dialog.create();
		AlertDialog alertDialog = dialog.show();
		
		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
	}
}
