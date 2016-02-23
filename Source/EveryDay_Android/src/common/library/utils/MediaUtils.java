package common.library.utils;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import common.image.crop.CropImage;

public class MediaUtils {
	public static void playAudio(String path)
	{
		if( CheckUtils.isEmpty(path) )
			return;
		
	    MediaPlayer mp = new MediaPlayer();
	    try {
			mp.setDataSource(path);
			mp.prepare();
			mp.start();
			mp.setVolume(10, 10);
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}				
	}
	
	public static void playAudioWithIntent(Context context, String path)
	{
		Intent intent = new Intent();  
		intent.setAction(android.content.Intent.ACTION_VIEW);  
		File file = new File(path);  
		intent.setDataAndType(Uri.fromFile(file), "audio/*");  
		context.startActivity(intent);
	}
	
	static int picture_mode = 0;
	
	private static final String IMAGE_UNSPECIFIED = "image/*";
	private static final String VIDEO_UNSPECIFIED = "video/*";
	private static final String AUDIO_UNSPECIFIED = "audio/*";
	
	
	public static void showCameraGalleryPage(final Context context, final int requestCode, final String output)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		String items[] = {"拍张照片", "从库拍张照片", "带视频"};
		
		dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int whichButton) {
				picture_mode = whichButton;						
				if (picture_mode == 0) {
					doTakePhotoFromCamera(context, requestCode, output );
				} else if (picture_mode == 1) {
					doTakePhotoFromGallery(context, requestCode + 1 );
				} else if (picture_mode == 2) {
					doTakeVideoFromCamera(context, requestCode + 2, output );
				}
				dialog.dismiss();
			}
		});
			
		dialog.create();
		AlertDialog alertDialog = dialog.show();
		
		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
	}
	
	public static void showProfileCameraGalleryPage(final Context context, final int requestCode, final String output)
	{
		AlertDialog.Builder dialog = new AlertDialog.Builder(context);

		String items[] = {"Camera", "Gallery"};
		
		dialog.setSingleChoiceItems(items, 0, new DialogInterface.OnClickListener() {			
			public void onClick(DialogInterface dialog, int whichButton) {
				picture_mode = whichButton;						
				if (picture_mode == 0) {
					doTakePhotoFromCamera(context, requestCode, output );
				} else if (picture_mode == 1) {
					doTakePhotoFromGallery(context, requestCode + 1 );
				}
				dialog.dismiss();
			}
		});
			
		dialog.create();
		AlertDialog alertDialog = dialog.show();
		
		alertDialog.show();
		alertDialog.setCanceledOnTouchOutside(true);
	}
	public static void doTakeVideoFromCamera(Context context, int requestCode, String output )
	{
		Activity activity = (Activity) context;
		
		Intent 	intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
		
		File photo = new File(output);
		
		Uri imageUri = Uri.fromFile(photo);
		
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);   // does file create?
		intent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION,
				activity.getWindowManager().getDefaultDisplay().getOrientation());
		intent.putExtra("return-data", true);		
		
		activity.startActivityForResult(intent, requestCode);
	}
	
	public static void doTakePhotoFromCamera(Context context, int requestCode, String output )
	{
		Activity activity = (Activity) context;
		
		Intent 	intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		
		File photo = new File(output);
		
		Uri imageUri = Uri.fromFile(photo);
		
		intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT, imageUri);   // does file create?
		intent.putExtra(android.provider.MediaStore.EXTRA_SCREEN_ORIENTATION,
				activity.getWindowManager().getDefaultDisplay().getOrientation());
		intent.putExtra("return-data", true);		
		
		activity.startActivityForResult(intent, requestCode);
	}
	
	public static void doTakePhotoFromGallery(Context context, int requestCode )
	{
		Activity activity = (Activity) context;
		
		Intent intent = null;		
		
		if (Build.VERSION.SDK_INT < 19) {
            intent = new Intent(Intent.ACTION_PICK);
        } else {
    	    intent = new Intent(Intent.ACTION_GET_CONTENT);
        }
		
		intent.setType(IMAGE_UNSPECIFIED);

		activity.startActivityForResult(intent, requestCode );// to connect onActivityResult in activity	
	}
	
	public static String getPathFromURI(Context context, Uri uri) {
        // just some safety built in 
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        Cursor cursor = null;
        
        try {
	        String[] projection = { MediaStore.Images.Media.DATA };
	        cursor = context.getContentResolver().query(uri, projection, null, null, null);
	        if( cursor != null ){
	            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
	            cursor.moveToFirst();
	            String path = cursor.getString(column_index);
	            if( path == null || path.length() < 1 )
	            	path = uri.getPath();
	            return path;
	        }
        }
	    finally {
	    	if (cursor != null) {
	    	      cursor.close();
	    	}
	    }
	        
        // this is our fallback here
        return uri.getPath();
	}
	
	
	public static void startPhotoZoom(Activity activity, String  picturePath, String outputPath, int iconSize, int requestCode ) {
	    Intent intent = new Intent(activity, CropImage.class);
	    
	    // here you have to pass absolute path to your file		
	    intent.putExtra("image-path", picturePath);
	    intent.putExtra("save-path", outputPath);
	    intent.putExtra("scale", true);
		intent.putExtra("outputX", iconSize);
		intent.putExtra("outputY", iconSize);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        activity.startActivityForResult(intent, requestCode);
	}
}
