package com.chess.ui.views.drawables.smart_button;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.ShapeDrawable;
import android.util.AttributeSet;
import com.example.roger.newdrawable.R;

/**
 * Created with IntelliJ IDEA.
 * User: roger sent2roger@gmail.com
 * Date: 12.05.13
 * Time: 18:40
 */
public class ButtonGlassyDrawable extends ButtonDrawable {

//	public static final int[] STATE_SELECTED = new int[]{android.R.attr.state_enabled, android.R.attr.state_selected};
//	public static final int[] STATE_PRESSED = new int[]{android.R.attr.state_enabled, android.R.attr.state_pressed};
//	public static final int[] STATE_ENABLED = new int[]{};

//	LayerDrawable selectedDrawable;
	int colorSolidS;
//	private boolean initialized;
//	private static final int glassyBorderIndex = 0;
//	int glassyBevelSize;

	/**
	 * Use for init ButtonDrawableBuilder
	 */
	ButtonGlassyDrawable() {
		setDefaults();
	}

	public ButtonGlassyDrawable(Context context, AttributeSet attrs) {
		Resources resources = context.getResources();

		setDefaults();

		parseAttributes(context, attrs);

		init(resources);
	}

	private void setDefaults() {
		// defaults
		bevelLvl = 1;
		isSolid = true;
		radius = DEFAULT_RADIUS;

		disabledAlpha = 100;
		enabledAlpha = 0xFF;
	}

	/**
	 * Set padding to internal cover only shape of LayerDrawables
	 *
	 * @param drawable to which we set padding must be ShapeDrawable
	 */
	@Override
	void setPaddingToShape(ShapeDrawable drawable) {
		int leftPad = padding;
		int topPad = padding;
		int rightPad = padding;
		int bottomPad = padding;

		if (leftPadding != DEF_VALUE) {
			leftPad = leftPadding;
		}

		if (rightPadding != DEF_VALUE) {
			rightPad = rightPadding;
		}

		if (topPadding != DEF_VALUE) {
			topPad = topPadding;
		}

		if (bottomPadding != DEF_VALUE) {
			bottomPad = bottomPadding;
		}

		drawable.setPadding(leftPad, topPad, rightPad, bottomPad);
	}

	@Override
	protected void parseAttributes(Context context, AttributeSet attrs) {
		super.parseAttributes(context, attrs);
		// get style
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoboButton);
		if (array == null) {
			return;
		}

		try {
			colorSolidS = array.getInt(R.styleable.RoboButton_btn_solid_s, TRANSPARENT);
		} finally {
			array.recycle();
		}
	}



//		pressedFilter = new PorterDuffColorFilter(PRESSED_OVERLAY, PorterDuff.Mode.SCREEN); // bad edges
//		pressedFilter = new PorterDuffColorFilter(PRESSED_OVERLAY, PorterDuff.Mode.SRC_IN); //  make transparent  - dark - bad
//		pressedFilter = new PorterDuffColorFilter(PRESSED_OVERLAY, PorterDuff.Mode.SRC_OUT); // bad edges
//		pressedFilter = new PorterDuffColorFilter(PRESSED_OVERLAY, PorterDuff.Mode.DST_IN); // make light transparent
//		pressedFilter = new PorterDuffColorFilter(PRESSED_OVERLAY, PorterDuff.Mode.DARKEN);  // bad edges
//		pressedFilter = new PorterDuffColorFilter(PRESSED_OVERLAY, PorterDuff.Mode.MULTIPLY); // make transparent  - dark - bad
//		pressedFilter = new PorterDuffColorFilter(PRESSED_OVERLAY, PorterDuff.Mode.OVERLAY); // bad edges
//		pressedFilter = new PorterDuffColorFilter(PRESSED_OVERLAY, PorterDuff.Mode.XOR);  // bad edges


//	@Override
//	void iniLayers(Canvas canvas) {
//		canvas.getClipBounds(clipBounds);
//		int width = clipBounds.width();
//		int height = clipBounds.height();
//		if (!isSolid) {
////			buttonPaint.setShader(makeLinear(width, height, colorGradientStart, colorGradientCenter, colorGradientEnd));
////			buttonPaint.setShader(makeLinear(width, height, 0x25FFFFFF, colorGradientCenter, 0x3F000000));
//
//			if (usePressedLayer) {
//				buttonPaint.setShader(makeLinear(width, height, colorGradientStartP, colorGradientCenterP, colorGradientEndP));
//			}
//		}
//
//		if (isGlassy) {
//			buttonPaint.setShader(makeLinear(width, height, colorGradientStart, colorGradientCenter, colorGradientEnd));
//			if (usePressedLayer) {
//				buttonPaint.setShader(makeLinear(width, height, colorGradientStart, colorGradientCenter, colorGradientEnd)); // pressed
//				buttonPaint.setShader(makeLinear(width, height, colorGradientStart, colorGradientCenter, colorGradientEnd)); // selected
//			}
//		}
//
//		initialized = true;
//	}
}
