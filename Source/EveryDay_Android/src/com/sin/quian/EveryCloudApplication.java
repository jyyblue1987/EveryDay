package com.sin.quian;



import com.facebook.FacebookSdk;
import com.google.android.gcm.GCMRegistrar;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.umeng.analytics.MobclickAgent;

import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import common.design.layout.ScreenAdapter;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.network.utils.NetworkUtils;


public class EveryCloudApplication extends Application {
	
	   @Override
	    public void onCreate() {
	        super.onCreate();
	        
	        initAnalytics();
	        initScreenAdapter();
	        setContextToComponents();
	        initImageLoader(this);
	        startPushProcess();
	   }   
	 
	   private void initScreenAdapter()
	   {
		   ScreenAdapter.setDefaultSize(1080, 1920);        
	       ScreenAdapter.setApplicationContext(this.getApplicationContext());
	   }
	   
	   private void setContextToComponents()
	   {
		   FacebookSdk.sdkInitialize(getApplicationContext());
		   DataUtils.setContext(this);
		   NetworkUtils.setContext(this);
		   MessageUtils.setApplicationContext(this);
	   }
	   
	   private void initImageLoader(Context context) {
		   DisplayImageOptions defaultDisplayImageOptions = new DisplayImageOptions.Builder()
			.cacheInMemory(true)
			.cacheOnDisk(true)
			.imageScaleType(ImageScaleType.EXACTLY)
			.bitmapConfig(Bitmap.Config.RGB_565)
			.considerExifParams(true).build();
			
		    ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context)
		    .threadPriority(Thread.NORM_PRIORITY)
		    .denyCacheImageMultipleSizesInMemory()
		    .diskCacheFileNameGenerator(new HashCodeFileNameGenerator())
		    .defaultDisplayImageOptions(defaultDisplayImageOptions)
		    .threadPoolSize(3)
		    .diskCacheFileCount(100) // default
		    .memoryCacheSizePercentage(80)
		    .tasksProcessingOrder(QueueProcessingType.LIFO)
		    .build();
		    
		    ImageLoader imageLoader = ImageLoader.getInstance();
		    imageLoader.init(config);	
//		    imageLoader.clearDiskCache();
//		   ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(this));	
	   }
	   
	   public static EveryCloudApplication getApplication(Context context) {
	       return (EveryCloudApplication) context.getApplicationContext();
	   }   
	   
	   
	   private void startPushProcess()
	   {
		   try {
				GCMRegistrar.checkDevice(this);		
				GCMRegistrar.checkManifest(this);
			} catch (Exception e ){
				e.printStackTrace();
			}
						
			final String regId = GCMRegistrar.getRegistrationId(this);

//			if( regId.equals("") ) 
//			{
//				GCMRegistrar.register(this, GCMIntentService.GOOGLE_APP_ID);
//				
//			}else{
//				DataUtils.savePreference(Const.GCM_PUSH_KEY, regId);
//				Log.w("GCM", "load !! " + regId);		
////				Toast.makeText(this, "On Registerd: GCM RegID = " + DataUtils.getPreference(Const.GCM_PUSH_KEY, ""), Toast.LENGTH_LONG).show();
//			}
	   }
	   
	   private void initAnalytics()
	   {
		   if( Const.ANALYTICS == true )
		   {
			   MobclickAgent.setDebugMode(true);
			   MobclickAgent.openActivityDurationTrack(false);
		   }
	   }
	   
}
