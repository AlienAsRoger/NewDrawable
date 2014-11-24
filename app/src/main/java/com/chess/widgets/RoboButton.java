package com.chess.widgets;


import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.Button;
import com.chess.ui.views.drawables.smart_button.ButtonDrawable;
import com.chess.ui.views.drawables.smart_button.ButtonDrawableBuilder;
import com.chess.utilites.FontsHelper;
import com.example.roger.newdrawable.R;

import java.io.Serializable;

public class RoboButton extends Button implements Serializable {

	private String ttfName = FontsHelper.BOLD_FONT;

	public RoboButton(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		setupFont(context, attrs);
	}

	public RoboButton(Context context) {
		super(context);
	}

	public RoboButton(Context context, AttributeSet attrs) {
		super(context, attrs);

		setupFont(context, attrs);
	}

	private void setupFont(Context context, AttributeSet attrs) {
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoboTextView);
		if (array == null) {
			return;
		}
		try {
			if (array.hasValue(R.styleable.RoboTextView_ttf)) {
				ttfName = array.getString(R.styleable.RoboTextView_ttf);
			}
			if (array.hasValue(R.styleable.RoboTextView_themeColor)) {
				boolean useThemeColor = array.getBoolean(R.styleable.RoboTextView_themeColor, false);
				if (useThemeColor) {
					setTextColor(FontsHelper.getInstance().getThemeColorStateList(context, false));
				}
			}
		} finally {
			array.recycle();
		}

		init(context, attrs);
	}

	private void init(Context context, AttributeSet attrs) {
//		setTypeface(FontsHelper.getInstance().getTypeFace(context, ttfName));
		ButtonDrawableBuilder.setBackgroundToView(this, attrs);
	}

	public void setFont(String font) {
		ttfName = font;
		init(getContext(), null);
	}

	public void setDrawableStyle(int styleId) {
		ButtonDrawable buttonDrawable = ButtonDrawableBuilder.createDrawable(getContext(), styleId);
		if (ButtonDrawableBuilder.JELLYBEAN_PLUS_API) {
			setBackground(buttonDrawable);
		} else {
			setBackgroundDrawable(buttonDrawable);
		}
	}
}
