package com.chess.ui.views.drawables.smart_button;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import com.example.roger.newdrawable.R;

/**
 * Created with IntelliJ IDEA.
 * User: roger sent2roger@gmail.com
 * Date: 12.05.13
 * Time: 18:40
 */
public class ButtonDrawable extends StateListDrawable {

	public static final boolean HONEYCOMB_PLUS_API = Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
	public static final boolean JELLYBEAN_MR1_PLUS_API = Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1;

	static final int DEFAULT_RADIUS = 4;
	static final int DEFAULT_BEVEL_INSET = 2;

	static final int TOP_BOTTOM = 0;
	static final int TR_BL = 1;
	static final int RIGHT_LEFT = 2;
	static final int BR_TL = 3;
	static final int BOTTOM_TOP = 4;
	static final int BL_TR = 5;
	static final int LEFT_RIGHT = 6;
	static final int TL_BR = 7;


	static final int TRANSPARENT = 0x00000000;
	public static final int DEF_VALUE = -1;

	// used to toggle outside of this view

	public static final int[] STATE_PRESSED = new int[]{android.R.attr.state_pressed};
	public static final int[] STATE_ENABLED = new int[]{android.R.attr.state_enabled};
	public static final int[] STATE_SELECTED = new int[]{android.R.attr.state_enabled, android.R.attr.state_checked};
	public static final int[] STATE_CHECKED = new int[]{android.R.attr.state_checked};
	public static final int[] STATE_DISABLED = new int[]{-android.R.attr.state_enabled};

	ColorFilter enabledFilter;
	ColorFilter pressedFilter;
	ColorFilter selectedFilter;
	ColorFilter checkedFilter;
	int disabledAlpha;
	int enabledAlpha;

	RectF glassyRect;

	/* Button parameter */
	int colorOuterBorder;
	int colorBottom;
	int colorRight;
	int colorLeft;
	int colorTop;
	int colorBottom2;
	int colorRight2;
	int colorLeft2;
	int colorTop2;
	int colorSolid;
	int colorGradientStart;
	int colorGradientCenter;
	int colorGradientEnd;

	int colorBottomP;
	int colorRightP;
	int colorLeftP;
	int colorTopP;
	int colorBottom2P;
	int colorRight2P;
	int colorLeft2P;
	int colorTop2P;
	int colorSolidP;
	int colorGradientStartP;
	int colorGradientCenterP;
	int colorGradientEndP;

	int gradientAngle;
	boolean isSolid;
	boolean isGlassy;
	boolean isClickable = true;
	boolean useBorder;
	boolean usePressedLayer;
	int bevelLvl;
	int bevelInset;
	int radius;
	int bevelSize;

	/* Padding */
	int padding;
	int leftPadding;
	int topPadding;
	int rightPadding;
	int bottomPadding;


	/* state & other values */
	private boolean initialized;
	int glassyBevelSize;

	boolean boundsInit;
	Rect clipBounds;

	ColorFilter currentFilter;
	int currentAlpha;
	RectF buttonRect;
	Paint buttonPaint;
	RectF leftRect;
	RectF topRect;
	RectF rightRect;
	RectF bottomRect;
	Paint leftLinePaint;
	Paint topLinePaint;
	Paint rightLinePaint;
	Paint bottomLinePaint;
	private int previousRight;
	private int previousBottom;

	/**
	 * Use for init ButtonDrawableBuilder
	 */
	ButtonDrawable() {
		setDefaults();
	}

	public ButtonDrawable(Context context, AttributeSet attrs) {
		Resources resources = context.getResources();

		setDefaults();

		parseAttributes(context, attrs);

		init(resources);
	}

	private void setDefaults() {
		clipBounds = new Rect();

		// defaults
		bevelLvl = 1;
		isSolid = true;
		radius = DEFAULT_RADIUS;

		disabledAlpha = 100;
		enabledAlpha = 0xFF;
	}

	void init(Resources resources) {

		bevelSize = resources.getDimensionPixelSize(R.dimen.default_bevel_size);

		leftRect = new RectF();
		topRect = new RectF();
		rightRect = new RectF();
		bottomRect = new RectF();
		buttonRect = new RectF();

		leftLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		leftLinePaint.setStyle(Paint.Style.STROKE);
		leftLinePaint.setColor(colorLeft);

		topLinePaint= new Paint(Paint.ANTI_ALIAS_FLAG);
		topLinePaint.setStyle(Paint.Style.STROKE);
		topLinePaint.setColor(colorTop);

		rightLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		rightLinePaint.setStyle(Paint.Style.STROKE);
		rightLinePaint.setColor(colorRight);

		bottomLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		bottomLinePaint.setStyle(Paint.Style.STROKE);
		bottomLinePaint.setColor(colorBottom);

		buttonPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		buttonPaint.setStyle(Paint.Style.FILL);
		buttonPaint.setColor(colorSolid);

		if (isGlassy) {
			glassyBevelSize = resources.getDimensionPixelSize(R.dimen.default_bevel_glassy_size);
			glassyRect = new RectF(glassyBevelSize, glassyBevelSize, glassyBevelSize, glassyBevelSize);

			int pressedOverlay = resources.getColor(R.color.glassy_button_overlay_p);
			int selectedOverlay = resources.getColor(R.color.glassy_button_overlay_s);
			pressedFilter = new PorterDuffColorFilter(pressedOverlay, PorterDuff.Mode.DARKEN);
			selectedFilter = new PorterDuffColorFilter(selectedOverlay, PorterDuff.Mode.DARKEN);
		} else {
			int pressedOverlay = resources.getColor(R.color.default_button_overlay_p);
			int selectedOverlay = resources.getColor(R.color.default_button_overlay_s);
			pressedFilter = new PorterDuffColorFilter(pressedOverlay, PorterDuff.Mode.SRC_ATOP); // lighter color will overlay main
			selectedFilter = new PorterDuffColorFilter(selectedOverlay, PorterDuff.Mode.SRC_ATOP);
		}
		int checkedOverlay = resources.getColor(R.color.default_button_overlay_c);
		checkedFilter = new PorterDuffColorFilter(checkedOverlay, PorterDuff.Mode.MULTIPLY);

	}

	@Override
	public void draw(Canvas canvas) {
		canvas.getClipBounds(clipBounds);
		if (clipBounds.bottom != previousBottom  || clipBounds.right != previousRight) {
			previousBottom = clipBounds.bottom;
			previousRight = clipBounds.right;
			boundsInit = false;
			initialized = false;
		}
		if (!initialized) {
			iniLayers();
		}

		if (!boundsInit) {
			initBounds();
		}

		topLinePaint.setColorFilter(currentFilter);
		bottomLinePaint.setColorFilter(currentFilter);
		leftLinePaint.setColorFilter(currentFilter);
		rightLinePaint.setColorFilter(currentFilter);
		buttonPaint.setColorFilter(currentFilter);

//		topLinePaint.setAlpha(currentAlpha);   // makes white and black colors
//		buttonPaint.setAlpha(currentAlpha);

		// draw bevel lines
		canvas.drawRoundRect(topRect, radius, radius, topLinePaint);
		canvas.drawRoundRect(bottomRect, radius, radius, bottomLinePaint);
		canvas.drawRoundRect(leftRect, radius, radius, leftLinePaint);
		canvas.drawRoundRect(rightRect, radius, radius, rightLinePaint);
		// draw top layer of button
		canvas.drawRoundRect(buttonRect, radius, radius, buttonPaint);
	}

	private void initBounds() {
		int width = clipBounds.width();
		int height = clipBounds.height();

		int noTop = bevelLvl;
		int noLeft = bevelLvl;
		int noRight = width;
		int noBottom = height;

		int showTop = 0;
		int showLeft = 0;
		int showRight = width - bevelLvl;
		int showBottom = height - bevelLvl;

		leftRect.set(showLeft, noTop, noRight, noBottom);
		topRect.set(noLeft, showTop, noRight, noBottom);
		rightRect.set(noLeft, noTop, showRight, noBottom);
		bottomRect.set(noLeft, noTop, noRight, showBottom);
		buttonRect.set(noLeft, noTop, noRight, noBottom);

		boundsInit = true;
	}

	void iniLayers() {
		int width = clipBounds.width();
		int height = clipBounds.height();
		if (!isSolid) {
			buttonPaint.setShader(makeLinear(width, height, colorGradientStart, colorGradientCenter, colorGradientEnd));
			if (usePressedLayer) {
				buttonPaint.setShader(makeLinear(width, height, colorGradientStartP, colorGradientCenterP, colorGradientEndP));
			}
		}

		if (isGlassy && useBorder) {
			buttonPaint.setShader(makeLinear(width, height, colorGradientStart, colorGradientCenter, colorGradientEnd));
		}

		initialized = true;
	}

	/**
	 * Set padding to internal cover only shape of LayerDrawables
	 *
	 * @param drawable to which we set padding must be ShapeDrawable
	 */
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

	Shader makeLinear(int width, int height, int startColor, int centerColor, int endColor) { // TODO improve performance
		Rect rect = new Rect(0, 0, width, height);
		float x0, x1, y0, y1;
		switch (gradientAngle) {
			case TOP_BOTTOM:
				x0 = rect.left;
				y0 = rect.top;
				x1 = x0;
				y1 = rect.bottom;
				break;
			case TR_BL:
				x0 = rect.right;
				y0 = rect.top;
				x1 = rect.left;
				y1 = rect.bottom;
				break;
			case RIGHT_LEFT:
				x0 = rect.right;
				y0 = rect.top;
				x1 = rect.left;
				y1 = y0;
				break;
			case BR_TL:
				x0 = rect.right;
				y0 = rect.bottom;
				x1 = rect.left;
				y1 = rect.top;
				break;
			case BOTTOM_TOP:
				x0 = rect.left;
				y0 = rect.bottom;
				x1 = x0;
				y1 = rect.top;
				break;
			case BL_TR:
				x0 = rect.left;
				y0 = rect.bottom;
				x1 = rect.right;
				y1 = rect.top;
				break;
			case LEFT_RIGHT:
				x0 = rect.left;
				y0 = rect.top;
				x1 = rect.right;
				y1 = y0;
				break;
			default:/* TL_BR */
				x0 = rect.left;
				y0 = rect.top;
				x1 = rect.right;
				y1 = rect.bottom;
				break;
		}

		return new LinearGradient(x0, y0, x1, y1,
				new int[]{startColor, endColor},
				null,
				Shader.TileMode.CLAMP);
	}

	@Override
	public int getOpacity() {
		return 0;
	}

	@Override
	protected boolean onStateChange(int[] states) {
		boolean enabled = false;
		boolean pressed = false;
		boolean selected = false;
		boolean checked = false;

		for (int state : states) {
			if (state == android.R.attr.state_enabled) {
				enabled = true;
			} else if (state == android.R.attr.state_pressed) {
				pressed = true;
			} else if (state == android.R.attr.state_selected) {
				selected = true;
			} else if (state == android.R.attr.state_checked) {
				checked = true;
			}
		}


		Drawable drawable = this;
		currentFilter = null;
		currentAlpha = 0;
		if (enabled && pressed) {

			drawable.setColorFilter(pressedFilter);
			drawable.setAlpha(enabledAlpha);
			currentFilter = pressedFilter;
			currentAlpha = enabledAlpha;
		} else if (enabled && selected) {
			drawable.setColorFilter(selectedFilter);
			drawable.setAlpha(enabledAlpha);
			currentFilter = selectedFilter;
			currentAlpha = enabledAlpha;
		} else if (enabled && checked) {
			drawable.setColorFilter(checkedFilter);
			drawable.setAlpha(enabledAlpha);
			currentFilter = checkedFilter;
			currentAlpha = enabledAlpha;
		} else if (!enabled) {
			drawable.setAlpha(disabledAlpha);
			currentAlpha = disabledAlpha;
		} else {
			drawable.setColorFilter(enabledFilter);
			drawable.setAlpha(enabledAlpha);
			currentFilter = enabledFilter;
			currentAlpha = enabledAlpha;
		}

		if (!isClickable) { // override all states to default
			drawable.mutate().setColorFilter(enabledFilter);
			drawable.mutate().setAlpha(enabledAlpha);
			currentFilter = enabledFilter;
			currentAlpha = enabledAlpha;
		}

		invalidateSelf();

		return super.onStateChange(states);
	}

	protected void parseAttributes(Context context, AttributeSet attrs) {
		// get style
		TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.RoboButton);
		if (array == null) {
			return;
		}

		try { // values
			radius = array.getDimensionPixelSize(R.styleable.RoboButton_btn_radius, DEFAULT_RADIUS);
			isSolid = array.getBoolean(R.styleable.RoboButton_btn_is_solid, true);
			isGlassy = array.getBoolean(R.styleable.RoboButton_btn_is_glassy, false);
			isClickable = array.getBoolean(R.styleable.RoboButton_btn_is_clickable, true);
			useBorder = array.getBoolean(R.styleable.RoboButton_btn_use_border, true);
			usePressedLayer = array.getBoolean(R.styleable.RoboButton_btn_use_pressed_layer, false);
			gradientAngle = array.getInt(R.styleable.RoboButton_btn_gradient_angle, TL_BR);
			bevelLvl = array.getInt(R.styleable.RoboButton_btn_bevel_lvl, 1);
			bevelInset = array.getDimensionPixelSize(R.styleable.RoboButton_btn_bevel_inset, DEFAULT_BEVEL_INSET);

			// Colors for bevel
			colorOuterBorder = array.getColor(R.styleable.RoboButton_btn_outer_border, TRANSPARENT);
			colorTop = array.getColor(R.styleable.RoboButton_btn_top, TRANSPARENT);
			colorLeft = array.getColor(R.styleable.RoboButton_btn_left, TRANSPARENT);
			colorRight = array.getColor(R.styleable.RoboButton_btn_right, TRANSPARENT);
			colorBottom = array.getColor(R.styleable.RoboButton_btn_bottom, TRANSPARENT);

			if (bevelLvl == 2) {
				// Level 2 for bevel
				colorTop2 = array.getInt(R.styleable.RoboButton_btn_top_2, TRANSPARENT);
				colorLeft2 = array.getInt(R.styleable.RoboButton_btn_left_2, TRANSPARENT);
				colorRight2 = array.getInt(R.styleable.RoboButton_btn_right_2, TRANSPARENT);
				colorBottom2 = array.getInt(R.styleable.RoboButton_btn_bottom_2, TRANSPARENT);
			}
			// Button colors
			colorSolid = array.getColor(R.styleable.RoboButton_btn_solid, TRANSPARENT);
			colorGradientStart = array.getColor(R.styleable.RoboButton_btn_gradient_start, TRANSPARENT);
			colorGradientCenter = array.getColor(R.styleable.RoboButton_btn_gradient_center, TRANSPARENT);
			colorGradientEnd = array.getColor(R.styleable.RoboButton_btn_gradient_end, TRANSPARENT);

			if (usePressedLayer) {
				/* ---------------------- Pressed states colors -------------------------------------------*/
				colorTopP = array.getInt(R.styleable.RoboButton_btn_top_p, TRANSPARENT);
				colorLeftP = array.getInt(R.styleable.RoboButton_btn_left_p, TRANSPARENT);
				colorRightP = array.getInt(R.styleable.RoboButton_btn_right_p, TRANSPARENT);
				colorBottomP = array.getInt(R.styleable.RoboButton_btn_bottom_p, TRANSPARENT);

				if (bevelLvl == 2) {
					// Level 2 Pressed
					colorTop2P = array.getInt(R.styleable.RoboButton_btn_top_2_p, TRANSPARENT);
					colorLeft2P = array.getInt(R.styleable.RoboButton_btn_left_2_p, TRANSPARENT);
					colorRight2P = array.getInt(R.styleable.RoboButton_btn_right_2_p, TRANSPARENT);
					colorBottom2P = array.getInt(R.styleable.RoboButton_btn_bottom_2_p, TRANSPARENT);
				}
				// Button colors Pressed
				colorSolidP = array.getInt(R.styleable.RoboButton_btn_solid_p, TRANSPARENT);
				colorGradientStartP = array.getInt(R.styleable.RoboButton_btn_gradient_start_p, TRANSPARENT);
				colorGradientCenterP = array.getInt(R.styleable.RoboButton_btn_gradient_center_p, TRANSPARENT);
				colorGradientEndP = array.getInt(R.styleable.RoboButton_btn_gradient_end_p, TRANSPARENT);
			}

			parseDefaultAttrs(context, attrs);
		} finally {
			array.recycle();
		}
	}

	void parseDefaultAttrs(Context context, AttributeSet attrs) {
		int PADDING_INDEX = 0;
		int PADDING_LEFT_INDEX = 1;
		int PADDING_TOP_INDEX = 2;
		int PADDING_RIGHT_INDEX = 3;
		int PADDING_BOTTOM_INDEX = 4;

		int[] defaultPadding;
		if (JELLYBEAN_MR1_PLUS_API) {
			defaultPadding = new int[]{android.R.attr.padding, android.R.attr.paddingLeft, android.R.attr.paddingTop,
					android.R.attr.paddingRight, android.R.attr.paddingBottom, android.R.attr.paddingStart, android.R.attr.paddingEnd
			};
		} else {
			defaultPadding = new int[]{android.R.attr.padding, android.R.attr.paddingLeft, android.R.attr.paddingTop,
					android.R.attr.paddingRight, android.R.attr.paddingBottom};
		}

		TypedArray array = context.obtainStyledAttributes(attrs, defaultPadding);
		if (array == null) {
			return;
		}
		padding = array.getDimensionPixelSize(PADDING_INDEX, DEF_VALUE);
		leftPadding = array.getDimensionPixelSize(PADDING_LEFT_INDEX, DEF_VALUE);
		topPadding = array.getDimensionPixelSize(PADDING_TOP_INDEX, DEF_VALUE);
		rightPadding = array.getDimensionPixelSize(PADDING_RIGHT_INDEX, DEF_VALUE);
		bottomPadding = array.getDimensionPixelSize(PADDING_BOTTOM_INDEX, DEF_VALUE);

		array.recycle();
	}

}
