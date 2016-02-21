package common.library.utils;import android.app.AlertDialog;import android.content.Context;import android.content.DialogInterface;import android.view.ViewGroup.LayoutParams;import android.widget.EditText;import android.widget.Toast;import common.design.layout.LayoutUtils;import common.library.utils.MessageUtils.OnButtonClickListener;public class MessageUtils {	static Toast g_Toast = null;	static Context g_AppContext = null;		public static void setApplicationContext( Context context )	{		g_AppContext = context;	}		public static void Toast(String message )	{		if( message == null || message.length() < 1 )			return;				if( g_AppContext == null )			return;				if( g_Toast == null )		{			g_Toast = Toast.makeText(g_AppContext, message, Toast.LENGTH_SHORT);		}		else		{			g_Toast.setText(message);		}		//		g_Toast.show();	}		public static void Toast(int resID )	{		Toast.makeText(g_AppContext, g_AppContext.getResources().getString(resID), Toast.LENGTH_SHORT).show();				}		public static AlertDialog showMessageDialog(Context context, String message )	{		if( message == null || message.length() < 1 )			return null;				AlertDialog.Builder alert_confirm = new AlertDialog.Builder(context);		alert_confirm.setMessage(message).setCancelable(false).setPositiveButton("OK",		new DialogInterface.OnClickListener() {		    @Override		    public void onClick(DialogInterface dialog, int which) {		    			        		    }		});		AlertDialog alert = alert_confirm.create();		alert.show();		return alert;	}		public static AlertDialog showEditDialog(Context context, String title, final OnAlertClickListener callback )	{			AlertDialog.Builder alert_confirm = new AlertDialog.Builder(context);				alert_confirm.setTitle(title);//		LinearLayout layout = new LinearLayout(context);//		LayoutUtils.setSize(layout, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);//				final EditText edit = new EditText(context);		edit.setSingleLine(true);		LayoutUtils.setSize(edit, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);		LayoutUtils.setMargin(edit, 20, 20, 20, 10, true);//		layout.addView(edit);		alert_confirm.setView(edit);		alert_confirm.setPositiveButton("OK",			new DialogInterface.OnClickListener() {			    @Override			    public void onClick(DialogInterface dialog, int which) {			    	if(callback != null)			    	{			    		callback.onInputText(edit.getText().toString());			    	}			    }			});		alert_confirm.setNegativeButton("Cancel", null);		AlertDialog alert = alert_confirm.create();		alert.show();		return alert;	}		public interface OnButtonClickListener {		public void onOkClick();		public void onCancelClick();	}		public static AlertDialog showDialogYesNo(Context context, String message, final OnButtonClickListener callback)	{		AlertDialog.Builder alert_confirm = new AlertDialog.Builder(context);		alert_confirm.setMessage(message).setCancelable(false).setPositiveButton("OK",			new DialogInterface.OnClickListener() {			    @Override			    public void onClick(DialogInterface dialog, int which) {			    	if(callback != null)			    	{			    		callback.onOkClick();			    	}			    }		}).setNegativeButton("Cancel",			new DialogInterface.OnClickListener() {			    @Override			    public void onClick(DialogInterface dialog, int which) {			    	if(callback != null)			    	{			    		callback.onCancelClick();			    	}			    }		});		AlertDialog alert = alert_confirm.create();		alert.show();				return alert;	}}