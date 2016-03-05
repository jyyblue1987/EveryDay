/**
 * @(#)Control01BorderContainer.java 1.00 2013-4-28 <br>
 *                                   Copyright 2009�?019 MarsorStudio , Inc. All rights reserved.<br>
 *                                   fhvsbgmy the same as qxc permitted.<br>
 *                                   Use is subject to license terms.<br>
 */

package common.component.ui;

import com.sin.quian.R;

import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import common.design.layout.ScreenAdapter;

public class MyCheckBox extends LinearLayout {

	private CheckBox chkCheker = null;

	private TextView chkText = null;

	/**
	 * @param context
	 */
	public MyCheckBox(Context context) {
		this(context, null);
	}
	
	@Override
	public void setId(int id) {
		chkCheker.setId(id);
	}

	/**
	 * @param context
	 */
	public MyCheckBox(Context context, AttributeSet attrs) {
		super(context, attrs);

		setGravity(Gravity.CENTER_VERTICAL);

		chkCheker = new CheckBox(context);
		chkCheker.setBackgroundResource(R.drawable.checkbox_selector);
		chkCheker.setButtonDrawable(null);
		chkCheker.setButtonDrawable(new ColorDrawable(0x00000000));
		chkCheker.setId(getId());
		
		chkText = new TextView(context);
		chkText.setGravity(Gravity.CENTER_VERTICAL|Gravity.LEFT);
		 
		int boxWidth = ScreenAdapter.computeWidth(57);
		int boxHeight = ScreenAdapter.computeWidth(57);

		addView(chkCheker, boxWidth, boxHeight);
		LinearLayout.LayoutParams txtParams = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);		
		txtParams.leftMargin = ScreenAdapter.computeWidth(15);
		addView(chkText, txtParams);
		chkText.setGravity(Gravity.CENTER_VERTICAL);

		chkText.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				chkCheker.performClick();
			}
		});
		
		float textSize = ScreenAdapter.computeWidth(36);
		setTextSize(textSize);

	}

	public void setTextColor(int color) {
		if (chkText != null) {
			chkText.setTextColor(color);
		}
	}

	public void setTextSize(float txtSize) {
		if (chkText != null) {
			chkText.setTextSize(TypedValue.COMPLEX_UNIT_PX, txtSize);
		}
	}

	public void setText(int resText) {
		if (chkText != null) {
			chkText.setText(resText);
		}
	}

	public void setText(CharSequence resText) {
		if (chkText != null) {
			chkText.setText(resText);
		}
	}

	public void setChecked(boolean checked) {
		if (chkCheker != null) {
			chkCheker.setChecked(checked);
		}
	}

	public boolean isChecked() {
		if (chkCheker != null) {
			return chkCheker.isChecked();
		}
		return false;
	}
	
	public void setOnClickListener(View.OnClickListener clickListner) {
		if (chkCheker != null) {
			chkCheker.setOnClickListener(clickListner);
		}
	}
	
	public void setAdapterSize(int size)
	{
		chkCheker.getLayoutParams().width = size;
		chkCheker.getLayoutParams().height = size;
		chkText.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)size * 0.8f);
	}

	public void setBackgroundCheckbox(int resID)
	{
		chkCheker.setBackgroundResource(resID);
	}
	
	public CheckBox getCheckBox()
	{
		return chkCheker;
	}

}
