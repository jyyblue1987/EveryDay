package com.sin.quian.pages;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.sin.quian.AppContext;
import com.sin.quian.Const;
import com.sin.quian.EveryDayUtils;
import com.sin.quian.R;
import com.sin.quian.locale.Locale;
import com.sin.quian.locale.LocaleFactory;
import com.sin.quian.network.ServerManager;

import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import common.design.layout.LayoutUtils;
import common.design.layout.ScreenAdapter;
import common.library.utils.DataUtils;
import common.library.utils.MessageUtils;
import common.list.adapter.ItemCallBack;
import common.list.adapter.MyListAdapter;
import common.list.adapter.ViewHolder;
import common.network.utils.LogicResult;
import common.network.utils.ResultCallBack;


public class RewardAppActivity extends HeaderBarActivity 
{	
	ListView	m_listItems = null;
	MyListAdapter	m_adapterItemList = null;
		
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
		
		Locale locale = LocaleFactory.getLocale();
		
		m_txtPageTitle.setText(locale.AppReward);
		m_btnRight.setVisibility(View.INVISIBLE);
		
		m_listItems.setDivider(getResources().getDrawable(R.color.transparent));
		
		List<JSONObject> list = new ArrayList<JSONObject>();
		
		for(int i = 0; i < 5; i++ )
		{
			JSONObject item = new JSONObject();
			
			list.add(item);
		}
		
		m_adapterItemList = new ItemListAdapter(this, list, R.layout.fragment_item_reward, null);
		m_listItems.setAdapter(m_adapterItemList);
		
	}
	

	private void onClickRewardApp(final int pos)
	{
		showLoadingProgress();
		ServerManager.ratingApp(AppContext.getUserID(), (pos + 1) + "", new ResultCallBack() {
			
			@Override
			public void doAction(LogicResult result) {
				hideProgress();
				if( result.mResult != LogicResult.RESULT_OK )
				{
					MessageUtils.showMessageDialog(RewardAppActivity.this, EveryDayUtils.getMessage(result.mMessage));
					return;
				}
				
				DataUtils.savePreference(Const.REWARD_APP, 1);
				AppContext.setProfile(result.getContentData());
				onFinishActivity();
			}
		});
	}
		
	class ItemListAdapter extends MyListAdapter {
		public ItemListAdapter(Context context, List<JSONObject> data,
				int resource, ItemCallBack callback) {
			super(context, data, resource, callback);
		}
		@Override
		protected void loadItemViews(View rowView, int position)
		{
			int star_id[] = {
				R.id.img_star_1,
				R.id.img_star_2,
				R.id.img_star_3,
				R.id.img_star_4,
				R.id.img_star_5
			};
			
			for(int i = 0; i < star_id.length; i++ )
			{
				LayoutUtils.setSize(ViewHolder.get(rowView, star_id[i]), 70, 70, true);	
				if( i > 0 )
					LayoutUtils.setMargin(ViewHolder.get(rowView, star_id[i]), 20, 0, 0, 0, true);
				if( i > position )
					ViewHolder.get(rowView, star_id[i]).setVisibility(View.INVISIBLE);
				else
					ViewHolder.get(rowView, star_id[i]).setVisibility(View.VISIBLE);					
			}
			
			LayoutUtils.setMargin(ViewHolder.get(rowView, R.id.btn_reward), 150, 40, 0, 40, true);
			
			((Button)ViewHolder.get(rowView, R.id.btn_reward)).setTextSize(TypedValue.COMPLEX_UNIT_PX, ScreenAdapter.computeWidth(35));
			LayoutUtils.setSize(ViewHolder.get(rowView, R.id.btn_reward), 250, 70, true);
			
			Locale locale = LocaleFactory.getLocale();
			((Button)ViewHolder.get(rowView, R.id.btn_reward)).setText(locale.AppReward);
			
			final int pos = position;
			ViewHolder.get(rowView, R.id.btn_reward).setOnClickListener(new View.OnClickListener() {
				
				@Override
				public void onClick(View v) {
					onClickRewardApp(pos);
				}
			});
		}	
	}


 
}
