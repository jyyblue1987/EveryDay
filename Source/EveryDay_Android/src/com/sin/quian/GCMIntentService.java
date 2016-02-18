package com.sin.quian;

import com.google.android.gcm.GCMBaseIntentService;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;
import common.library.utils.DataUtils;

public class GCMIntentService extends GCMBaseIntentService {
	private static final String TAG = "GCM";
	public static final String  GOOGLE_APP_ID = "200641637057";
	
	public GCMIntentService() {
		super(GOOGLE_APP_ID);			
	}

	@Override
	protected void onMessage(Context context, Intent intent) {
		if (intent.getAction().equals("com.google.android.c2dm.intent.RECEIVE")) {
			showMessage(context, intent);
		}
	}

	@Override
	protected void onError(Context context, String msg) {
		
	}

	@Override
	protected void onRegistered(Context context, String regID) {		
		if(!regID.equals("") || regID != null){
			Toast.makeText(this, "On Registerd: GCM RegID = " + regID, Toast.LENGTH_LONG).show();
			Log.w(TAG, "onRegistered!! " + regID);			
			insertRegistrationID(regID);
		}
	}

	@Override
	protected void onUnregistered(Context context, String regID) {
		
		Log.w(TAG, "onUnregistered!! " + regID);
	}
	
	
	private void showMessage(Context context, Intent intent){		
//		String testmsg = intent.getStringExtra("msg");		
//		Toast.makeText(context, testmsg, Toast.LENGTH_LONG).show();
		
//		NotificationReceiver.showGCMNotification(context, intent);		
	}
	
	public void insertRegistrationID(String id){
		DataUtils.savePreference(Const.GCM_PUSH_KEY, id);
	}
}
