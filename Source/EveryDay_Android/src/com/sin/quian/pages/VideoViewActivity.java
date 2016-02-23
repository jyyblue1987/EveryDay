package com.sin.quian.pages;

import com.sin.quian.R;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnErrorListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.VideoView;
import common.library.utils.BackgroundTaskUtils;
import common.library.utils.BackgroundTaskUtils.OnTaskProgress;
import common.network.utils.NetworkUtils;

public class VideoViewActivity extends HeaderBarActivity {

	// Declare variables
	ProgressDialog pDialog;
	VideoView videoview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.layout_video_page);
		
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();
		
		videoview = (VideoView) findViewById(R.id.VideoView);
	}
	
	protected void initData()
	{
		super.initData();
		
		m_txtPageTitle.setText("Video");
		// Get the layout from video_main.xml
		String m_videoURL = "";
		Bundle bundle = getIntent().getExtras();
		if( bundle != null )
		{
			m_videoURL = bundle.getString(INTENT_EXTRA);
		}
		
		showProgress("Loading", "Please wait...");
		try {
			// Start the MediaController
			MediaController mediacontroller = new MediaController(this);
			// Get the URL from String VideoURL
			videoview.setMediaController(mediacontroller);
		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}
		if(m_videoURL.contains("www.youtube.com")){
			playYoutubeVideo(m_videoURL);
		}else
			playVideo(m_videoURL);
	}
		
	private void playYoutubeVideo(final String url){
		new BackgroundTaskUtils(new OnTaskProgress() {
			
			String youtube_url = "";
			@Override
			public void onProgress() {
				youtube_url = NetworkUtils.getUrlVideoRTSP(url);
				
			}
			
			@Override
			public void onFinished() {
				playVideo(youtube_url);				
			}
		}).execute();
		
	}
	private void playVideo(String url)
	{
		try {
			// Start the MediaController
			Uri video = Uri.parse(url);
			videoview.setVideoURI(video);

		} catch (Exception e) {
			Log.e("Error", e.getMessage());
			e.printStackTrace();
		}

		videoview.requestFocus();
		
		videoview.setOnPreparedListener(new OnPreparedListener() {
			// Close the progress bar and play the video
			public void onPrepared(MediaPlayer mp) {
//				pDialog.dismiss();

				hideProgress();
				videoview.start();
			}
		});

		videoview.setOnErrorListener(new OnErrorListener(){

			@Override
			public boolean onError(MediaPlayer mp, int what, int extra) {
				hideProgress();				

				
				return false;
			}
        	
        });
		
	}
}
