package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.billing.util.IabBroadcastReceiver;
import com.sin.quian.billing.util.IabBroadcastReceiver.IabBroadcastListener;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.billing.util.IabHelper;
import com.sin.quian.billing.util.IabResult;
import com.sin.quian.billing.util.Inventory;
import com.sin.quian.billing.util.Purchase;
import com.sin.quian.network.ServerManager;

import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class BuyActivity extends HeaderBarActivity implements IabBroadcastListener
{
	private static final String ITEM_SKU = "item";
	private static final int	ITEM_SKU_COUNT = 6;
	
	private static int 	INAPP_TEST_MODE = 0; // 0: static reponse test, 1: real transaction
	private static final String TEST_PURCHASED = "android.test.purchased";
	private static final String TEST_CANCELED = "android.test.canceled";
	private static final String TEST_REFUNDED = "android.text.refunded";
	private static final String TEST_UNAVAILABLE = "android.test.item_unavailable";
	
	
	int[] item_name = {
			20, 60, 150, 360, 1000, 2500 	
		};
		
	String[] item_price = {
			"1.99", "4.99", "9.99", "19.99", "49.99", "99.99" 	
		};
	
	ListView	m_listItems = null;
	MyListAdapter	m_adapterItemList = null;
	private static final String ITEM_NAME = "item_name";
	private static final String ITEM_PRICE = "item_price";
	
	
	// The helper object
    IabHelper mHelper;

    // Provides purchase notification while this app is running
    IabBroadcastReceiver mBroadcastReceiver;
	
	static final String TAG = "EveryDay_Billing";
	static final int RC_REQUEST = 10001;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.layout_buy);
		loadComponents();
	}
	
	protected void findViews()
	{
		super.findViews();

		m_listItems = (ListView)findViewById(R.id.list_items);
	}

	protected void initData()
	{
		super.initData();
		m_txtPageTitle.setText("买明星");
		m_btnRight.setVisibility(View.INVISIBLE);
		
		initInAppBilling();
		
		m_listItems.setDivider(getResources().getDrawable(R.color.transparent));

		
		List<JSONObject> list = new ArrayList<JSONObject>();
		
		for(int i = 0; i < item_name.length; i++ )
		{
			JSONObject item = new JSONObject();
			try {
				item.put(ITEM_NAME, item_name[i]);
				item.put(ITEM_PRICE, item_price[i]);
			} catch (JSONException e) {
				e.printStackTrace();
			}
			
			list.add(item);
		}
		
		m_adapterItemList = new ItemListAdapter(this, list, R.layout.fragment_item_buy, null);
		m_listItems.setAdapter(m_adapterItemList);
		
	}
	
	protected void showLabels()
	{
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.Buying);	
	}
	
	private void initInAppBilling()
	{
		String billingKey = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA4N4Lt239v/d6NdzIBXcCUz2FDkkYhnVwoBoVwgCnUSi1+/ophFGjf5g0LbdJUNA5tbB0EB7gY9ne/XVIUKzF1YFW+i3PNYD7lJaAFgqpid3AAlqStsh5uki2PtUngzV+oNo9xLdwQqAwdQ7+imlvnf7w7AegozcUgM7hscD4WYQ1jRreKqB/MV+KmkcRKE9FzAmwdYoYl0ZAqAV9oVnULkfZyiogOpjbnhM0m1O8Mej3xKvO4FV0tCs+FyJAqjr9pN24yI268Oj+uxkn3FF29GSpewdGUqDJ5Hk1XWymZvVghoigA+qO/UzrTXIAkuvaqjxlV7mhL9k5hxpiF9yUDQIDAQAB";
		mHelper = new IabHelper(this, billingKey);
		
		
		// Start setup. This is asynchronous and the specified listener
        // will be called once setup completes.
        Log.d(TAG, "Starting setup.");
        mHelper.startSetup(new IabHelper.OnIabSetupFinishedListener() {
            public void onIabSetupFinished(IabResult result) {
                Log.d(TAG, "Setup finished.");

                if (!result.isSuccess()) {
                    // Oh noes, there was a problem.
                    return;
                }
                
                Toast.makeText(BuyActivity.this, "Startup OK", Toast.LENGTH_LONG).show();

                // Have we been disposed of in the meantime? If so, quit.
                if (mHelper == null) return;

                // Important: Dynamically register for broadcast messages about updated purchases.
                // We register the receiver here instead of as a <receiver> in the Manifest
                // because we always call getPurchases() at startup, so therefore we can ignore
                // any broadcasts sent while the app isn't running.
                // Note: registering this listener in an Activity is a bad idea, but is done here
                // because this is a SAMPLE. Regardless, the receiver must be registered after
                // IabHelper is setup, but before first call to getPurchases().
                mBroadcastReceiver = new IabBroadcastReceiver(BuyActivity.this);
                IntentFilter broadcastFilter = new IntentFilter(IabBroadcastReceiver.ACTION);
                registerReceiver(mBroadcastReceiver, broadcastFilter);

                // IAB is fully set up. Now, let's get an inventory of stuff we own.
                mHelper.queryInventoryAsync(mGotInventoryListener);
            }
        });
		
	}
	
	// Listener that's called when we finish querying the items and subscriptions we own
    IabHelper.QueryInventoryFinishedListener mGotInventoryListener = new IabHelper.QueryInventoryFinishedListener() {
        public void onQueryInventoryFinished(IabResult result, Inventory inventory) {
            Log.d(TAG, "Query inventory finished.");

            // Have we been disposed of in the meantime? If so, quit.
            if (mHelper == null) return;

            // Is it a failure?
            if (result.isFailure()) {
                return;
            }

            Log.d(TAG, "Query inventory was successful.");

            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
            if( INAPP_TEST_MODE == 0 )
            {	
            	Purchase purchase = inventory.getPurchase(TEST_PURCHASED);
                if (purchase != null && verifyDeveloperPayload(purchase)) {
                    mHelper.consumeAsync(inventory.getPurchase(TEST_PURCHASED), mConsumeFinishedListener);                    
                }               
            }
            else
            {
                for(int i = 0; i < ITEM_SKU_COUNT; i++ )
                {
                    Purchase purchase = inventory.getPurchase(ITEM_SKU + (i + 1));
                    if (purchase != null && verifyDeveloperPayload(purchase)) {
                        mHelper.consumeAsync(inventory.getPurchase(ITEM_SKU + (i + 1)), mConsumeFinishedListener);                    
                    }                	
                }            	
            }

            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };
	
	
	private void onClickBuy(int pos)
	{
		String payload = "";

		try {
			if( INAPP_TEST_MODE == 0 )	// static test mode
				mHelper.launchPurchaseFlow(this, TEST_PURCHASED, RC_REQUEST, mPurchaseFinishedListener, payload);
			else
				mHelper.launchPurchaseFlow(this, ITEM_SKU + (pos + 1), RC_REQUEST, mPurchaseFinishedListener, payload);
		} catch(Exception e) {			
		}
	}
	
	private void addPoint(final int amount)
	{
		showLoadingProgress();
		ServerManager.addPoint(AppContext.getUserID(), amount + "", new ResultCallBack() {
		
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(BuyActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				
				int pointCount = AppContext.getProfile().optInt(Const.POINT_NUM, 0);
				
				try {
					AppContext.getProfile().put(Const.POINT_NUM, pointCount + amount);
					
				 	Intent intent = new Intent();
			    	setResult(Activity.RESULT_OK, intent);    
			        onFinishActivity();		
				} catch (JSONException e) {
					e.printStackTrace();
				}				
			}
		});
	}
	
	// Callback for when a purchase is finished
    IabHelper.OnIabPurchaseFinishedListener mPurchaseFinishedListener = new IabHelper.OnIabPurchaseFinishedListener() {
        public void onIabPurchaseFinished(IabResult result, Purchase purchase) {
            Log.d(TAG, "Purchase finished: " + result + ", purchase: " + purchase);
            hideProgress();
            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            if (result.isFailure()) {
                return;
            }
            if (!verifyDeveloperPayload(purchase)) {
                return;
            }

            Log.d(TAG, "Purchase successful.");
            	
            if( INAPP_TEST_MODE == 0 )
            {
                if (purchase.getSku().equals(TEST_PURCHASED) ) {
                    Toast.makeText(BuyActivity.this, "Purchase is success", Toast.LENGTH_LONG).show();
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                }            	
            }
            else
            {
                if (purchase.getSku().contains(ITEM_SKU) ) {
                    Log.d(TAG, "Purchase is gas. Starting gas consumption.");
                    mHelper.consumeAsync(purchase, mConsumeFinishedListener);
                }            	
            }
        }
    };
	
    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            // if we were disposed of in the meantime, quit.
            if (mHelper == null)
            {
            	return;
            }

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                Log.d(TAG, "Consumption successful. Provisioning.");
                
                if( INAPP_TEST_MODE == 0 )
                {
                    if (purchase.getSku().equals(TEST_PURCHASED) ) {
                    	MessageUtils.showMessageDialog(BuyActivity.this, "Item is consummed");
                    }            	
                }
                else
                {
	                for(int i = 0; i < ITEM_SKU_COUNT; i++ )
	                {
	                	if( purchase.getSku().equals(ITEM_SKU_COUNT + (i + 1)) )
	                	{
	                		addPoint(item_name[i]);
	                		break;
	                	}
	                }
                }
            }
        }
    };
    /** Verifies the developer payload of a purchase. */
    boolean verifyDeveloperPayload(Purchase p) {
        String payload = p.getDeveloperPayload();

        /*
         * TODO: verify that the developer payload of the purchase is correct. It will be
         * the same one that you sent when initiating the purchase.
         *
         * WARNING: Locally generating a random string when starting a purchase and
         * verifying it here might seem like a good approach, but this will fail in the
         * case where the user purchases an item on one device and then uses your app on
         * a different device, because on the other device you will not have access to the
         * random string you originally generated.
         *
         * So a good developer payload has these characteristics:
         *
         * 1. If two different users purchase an item, the payload is different between them,
         *    so that one user's purchase can't be replayed to another user.
         *
         * 2. The payload must be such that you can verify it even when the app wasn't the
         *    one who initiated the purchase flow (so that items purchased by the user on
         *    one device work on other devices owned by the user).
         *
         * Using your own server to store and verify developer payloads across app
         * installations is recommended.
         */

        return true;
    }
    
	@Override
	public void receivedBroadcast() {
		 // Received a broadcast notification that the inventory of items has changed
        Log.d(TAG, "Received broadcast notification. Querying inventory.");
        mHelper.queryInventoryAsync(mGotInventoryListener);
	}
	
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult(" + requestCode + "," + resultCode + "," + data);
        if (mHelper == null) return;

        // Pass on the activity result to the helper for handling
        if (!mHelper.handleActivityResult(requestCode, resultCode, data)) {
            // not handled, so handle it ourselves (here's where you'd
            // perform any handling of activity results not related to in-app
            // billing...
            super.onActivityResult(requestCode, resultCode, data);
        }
        else {
            Log.d(TAG, "onActivityResult handled by IABUtil.");
        }
    }
    

	@Override
	public void onDestroy() {
	   super.onDestroy();

	   try {
		   // very important:
	       if (mBroadcastReceiver != null) {
	           unregisterReceiver(mBroadcastReceiver);
	       }
	
	       // very important:
	       Log.d(TAG, "Destroying helper.");
	       if (mHelper != null) {
	           mHelper.dispose();
	           mHelper = null;
	       }
	   }catch(Exception e){
		   e.printStackTrace();
	   }

	}
	
	class ItemListAdapter extends MyListAdapter {
		public ItemListAdapter(Context context, List<JSONObject> data,
				int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			final JSONObject item = getItem(position);
			
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.img_star_point), 70, 70, true);
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.img_star_point), 150, 40, 0, 40, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_item_name)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeWidth(50));
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_item_name), 20, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.txt_item_name), 200, LayoutParams.WRAP_CONTENT, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_item_price)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeWidth(50));
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.txt_item_price), 50, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.txt_item_price), 200, LayoutParams.WRAP_CONTENT, true);
			
			((Button)ViewHolder.get(rowView, R.id.btn_buy)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeWidth(35));
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.btn_buy), 50, 0, 0, 0, true);
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.btn_buy), 150, 70, true);
			
			((TextView)ViewHolder.get(rowView, R.id.txt_item_name)).setText(item.optString(ITEM_NAME, ""));
			((TextView)ViewHolder.get(rowView, R.id.txt_item_price)).setText(item.optString(ITEM_PRICE, ""));
			
			final int amount = Integer.valueOf(item.optString(ITEM_NAME, "100"));
			
			Locale locale = LocaleFactory.getLocale();
			((Button)ViewHolder.get(rowView, R.id.btn_buy)).setText(locale.Buying);
			
			final int pos = position;
			ViewHolder.get(rowView, R.id.btn_buy).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onClickBuy(pos);
				}
			});
		}	
	}


 
}
