package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.sin.quian.AppContext;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.network.ServerManager;
import com.sin.quian.network.ServerTask;
import com.viewpagerindicator.CirclePageIndicator;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.LayoutParams;
import android.support.v4.view.ViewPager.SimpleOnPageChangeListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.image.load.ImageUtils;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyPagerAdapter;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class AdvertiseActivity extends HeaderBarActivity {
	ViewPager 	m_photoPager = null;
	PhotoPagerAdapter	m_photoAdapter;
	CirclePageIndicator m_photoIndicator = null;
	int			m_nCurrentPage = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_gallery_view);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();
		
	 	m_photoPager = (ViewPager) this.findViewById(R.id.img_gallery_view);     
	 	m_photoIndicator = (CirclePageIndicator) findViewById(R.id.picture_indicator);
	}
	
	protected void initData()
	{
		super.initData();
		
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.Advertise);
		
		getAdvertiseList();
	}
	
	private void getAdvertiseList()
	{
		showLoadingProgress();
		ServerManager.getAdvertiseList(AppContext.getUserID(), "0", new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();

				if( result.mResult != LogicResult.RESULT_OK )	// login ok
				{
					return;
				}
				
				showAdvertiseList(result.getContentArray());				
			}
		});
	}
	
	private void showAdvertiseList(JSONArray array)
	{
		List<JSONObject> list = new ArrayList<JSONObject>();
		for(int i = 0; i < array.length(); i++ )
		{
			JSONObject data = array.optJSONObject(i);
		
			list.add(data);
		}
		m_photoAdapter = new PhotoPagerAdapter(this, list, null);
		m_photoPager.setAdapter(m_photoAdapter);
		
		m_photoPager.setOnTouchListener(new View.OnTouchListener() {
		    public boolean onTouch(View v, MotionEvent event) {
		        v.getParent().requestDisallowInterceptTouchEvent(true);
		        return false;
		    }
		});

		m_photoPager.setOnPageChangeListener(new SimpleOnPageChangeListener() {
		    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		    	m_photoPager.getParent().requestDisallowInterceptTouchEvent(true);
		    	m_nCurrentPage = position;
		    }
		});
		
		m_photoIndicator.setViewPager(m_photoPager);

	}
	protected void layoutControls()
	{
		super.layoutControls();
		
		LayoutUtils.setSize(m_photoIndicator, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
    	m_photoIndicator.setRadius(ScreenAdapter.computeWidth(15));
    	LayoutUtils.setMargin(m_photoIndicator, 0, 0, 0, 80, true);
    	
	}

	protected void gotoNextPage()
	{
		onFinishActivity();
	}
	
	class PhotoPagerAdapter extends MyPagerAdapter {
	    public PhotoPagerAdapter(Context context, List<JSONObject> data, ItemCallBack callback) {	        
	        super(context, data, callback);
	    }

		@Override
		protected View loadItemViews(int position)
		{
			ImageView view = new ImageView(m_context);
	    	
	    	JSONObject item = getItem(position);
	    	String url = ServerTask.SERVER_UPLOAD_PATH + item.optString("thumbpath", "");	    		    
	    	
			DisplayImageOptions options = ImageUtils.buildUILOption(R.drawable.default_image_bg).build();
			ImageLoader.getInstance().displayImage(url, view, options);
			
			return view;
		}  
	
	}
}
