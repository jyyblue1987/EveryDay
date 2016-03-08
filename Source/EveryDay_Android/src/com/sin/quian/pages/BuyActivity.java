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
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.MessageUtils;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class BuyActivity extends HeaderBarActivity implements IabBroadcastListener
{
	
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
	
	private static String BUY_ITEM_NAME = "item_name";
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
		String[] item_name = {
				"20", "60", "150", "360", "1000", "2500" 	
			};
			
			String[] item_price = {
					"1.99", "4.99", "9.99", "19.99", "49.99", "99.99" 	
				};
			
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
	
	private void initInAppBilling()
	{
		String billingKey = "CONSTRUCT_YOUR_KEY_AND_PLACE_IT_HERE";
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

            /*
             * Check for items we own. Notice that for each purchase, we check
             * the developer payload to see if it's correct! See
             * verifyDeveloperPayload().
             */

            Purchase premiumPurchase = inventory.getPurchase(BUY_ITEM_NAME);
//
//            // First find out which subscription is auto renewing
//            Purchase gasMonthly = inventory.getPurchase(SKU_INFINITE_GAS_MONTHLY);
//            Purchase gasYearly = inventory.getPurchase(SKU_INFINITE_GAS_YEARLY);
//            if (gasMonthly != null && gasMonthly.isAutoRenewing()) {
//                mInfiniteGasSku = SKU_INFINITE_GAS_MONTHLY;
//                mAutoRenewEnabled = true;
//            } else if (gasYearly != null && gasYearly.isAutoRenewing()) {
//                mInfiniteGasSku = SKU_INFINITE_GAS_YEARLY;
//                mAutoRenewEnabled = true;
//            } else {
//                mInfiniteGasSku = "";
//                mAutoRenewEnabled = false;
//            }
//
//            // The user is subscribed if either subscription exists, even if neither is auto
//            // renewing
//            mSubscribedToInfiniteGas = (gasMonthly != null && verifyDeveloperPayload(gasMonthly))
//                    || (gasYearly != null && verifyDeveloperPayload(gasYearly));
//            Log.d(TAG, "User " + (mSubscribedToInfiniteGas ? "HAS" : "DOES NOT HAVE")
//                    + " infinite gas subscription.");
//            if (mSubscribedToInfiniteGas) mTank = TANK_MAX;
//
//            // Check for gas delivery -- if we own gas, we should fill up the tank immediately
//            Purchase gasPurchase = inventory.getPurchase(SKU_GAS);
//            if (gasPurchase != null && verifyDeveloperPayload(gasPurchase)) {
//                Log.d(TAG, "We have gas. Consuming it.");
//                mHelper.consumeAsync(inventory.getPurchase(SKU_GAS), mConsumeFinishedListener);
//                return;
//            }
//
//            updateUi();
//            setWaitScreen(false);
            Log.d(TAG, "Initial inventory query finished; enabling main UI.");
        }
    };
	
	
	private void onClickBuy(final int amount)
	{
		String payload = "";

		try {
			mHelper.launchPurchaseFlow(this, "Star Point", RC_REQUEST, mPurchaseFinishedListener, payload);			
		} catch(Exception e) {
			showLoadingProgress();
			addPoint(amount);
		}
	}
	
	private void addPoint(final int amount)
	{
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

            if (purchase.getSku().equals(BUY_ITEM_NAME)) {
                // bought 1/4 tank of gas. So consume it.
                Log.d(TAG, "Purchase is gas. Starting gas consumption.");
                mHelper.consumeAsync(purchase, mConsumeFinishedListener);
            }
//            else if (purchase.getSku().equals(SKU_PREMIUM)) {
//                // bought the premium upgrade!
//                Log.d(TAG, "Purchase is premium upgrade. Congratulating user.");
//            }
//            else if (purchase.getSku().equals(SKU_INFINITE_GAS_MONTHLY)
//                    || purchase.getSku().equals(SKU_INFINITE_GAS_YEARLY)) {
//            }
        }
    };
	
    // Called when consumption is complete
    IabHelper.OnConsumeFinishedListener mConsumeFinishedListener = new IabHelper.OnConsumeFinishedListener() {
        public void onConsumeFinished(Purchase purchase, IabResult result) {
            Log.d(TAG, "Consumption finished. Purchase: " + purchase + ", result: " + result);
            hideProgress();
            // if we were disposed of in the meantime, quit.
            if (mHelper == null) return;

            // We know this is the "gas" sku because it's the only one we consume,
            // so we don't check which sku was consumed. If you have more than one
            // sku, you probably should check...
            if (result.isSuccess()) {
                // successfully consumed, so we apply the effects of the item in our
                // game world's logic, which in our case means filling the gas tank a bit
                Log.d(TAG, "Consumption successful. Provisioning.");
//                mTank = mTank == TANK_MAX ? TANK_MAX : mTank + 1;
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
	public void onDestroy() {
	   super.onDestroy();
	   if (mHelper != null) mHelper.dispose();
	   mHelper = null;
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
			
			ViewHolder.get(rowView, R.id.btn_buy).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onClickBuy(amount);
				}
			});
		}	
	}


 
}
