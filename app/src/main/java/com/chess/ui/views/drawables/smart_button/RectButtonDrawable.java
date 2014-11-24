package com.chess.ui.views.drawables.smart_button;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.*;
import android.util.AttributeSet;
import com.example.roger.newdrawable.R;

/**
 * Created with IntelliJ IDEA.
 * User: roger sent2roger@gmail.com
 * Date: 19.05.13
 * Time: 22:15
 */
public class RectButtonDrawable extends ButtonDrawable {

	static final int TOP_LEFT = 0;
	static final int TOP_MIDDLE = 1;
	static final int TOP_RIGHT = 2;

	static final int TAB_LEFT = 3;
	static final int TAB_MIDDLE = 4;
	static final int TAB_RIGHT = 5;

	static final int BOTTOM_LEFT = 6;
	static final int BOTTOM_MIDDLE = 7;
	static final int BOTTOM_RIGHT = 8;

	static final int LIST_ITEM = 9;
	static final int LIST_ITEM_HEADER = 10;
	static final int LIST_ITEM_HEADER_2 = 11;
	static final int LIST_ITEM_HEADER_DARK = 12;
	static final int LIST_ITEM_HEADER_2_DARK = 13;
	static final int LIST_ITEM_HEADER_TOP = 14;

	static final int SIDE_LEFT = 15;
	static final int SIDE_MIDDLE = 16;
	static final int SIDE_RIGHT = 17;

	static final int TABLET_LEFT = 18;
	static final int TABLET_MIDDLE = 19;
	static final int TABLET_RIGHT = 20;

	static final int SILVER_LEFT = 21;
	static final int SILVER_MIDDLE = 22;
	static final int SILVER_RIGHT = 23;

	static final int LIST_ITEM_WHITE = 24; // used for monotone dividers on content screens

	int rectPosition = DEF_VALUE;

	private int edgeOffset;

	/* state & other values */
	private Rect lightRect;
	private Rect darkRect;
	private Paint lightLinePaint;
	private Paint darkLinePaint;

	/**
	 * Use for init ButtonDrawableBuilder
	 */
	RectButtonDrawable() {
		setDefaults();
	}

	public RectButtonDrawable(Context context, AttributeSet attrs) {
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

	@Override
	void init(Resources resources) {
		bevelSize = resources.getDimensionPixelSize(R.dimen.default_bevel_size);


		clipRect = new Rect();

		lightLinePaint = new Paint(); // light line
		lightLinePaint.setColor(colorTop);
		lightLinePaint.setStyle(Paint.Style.STROKE);
		lightLinePaint.setStrokeWidth(bevelSize);

		darkLinePaint = new Paint(); // dark line
		darkLinePaint.setColor(colorBottom);
		darkLinePaint.setStyle(Paint.Style.STROKE);
		darkLinePaint.setStrokeWidth(bevelSize);

		lightRect = new Rect();
		darkRect = new Rect();

		if (radius > 0) {
			outerRect = new float[]{radius, radius, radius, radius, radius, radius, radius, radius};
		} else {
			outerRect = null;
		}

		bevelRect = new RectF(bevelSize, bevelSize, bevelSize, bevelSize);

		// set color filters for different drawable state
		int pressedOverlay = resources.getColor(R.color.rect_button_overlay_p);
		int selectedOverlay = resources.getColor(R.color.default_button_overlay_s);
		int checkedOverlay = resources.getColor(R.color.rect_button_overlay_c);

		if (isCheckable()) {
			pressedOverlay = resources.getColor(R.color.rect_button_overlay_p_dark);
		}

		pressedFilter = new PorterDuffColorFilter(pressedOverlay, PorterDuff.Mode.DARKEN);
		selectedFilter = new PorterDuffColorFilter(selectedOverlay, PorterDuff.Mode.DARKEN);
		checkedFilter = new PorterDuffColorFilter(checkedOverlay, PorterDuff.Mode.DARKEN);

		if (isCheckable()) {
			enabledFilter = checkedFilter;
			checkedFilter = null;
		} else {
			selectedFilter = null;
			enabledFilter = null;
		}

		edgeOffset = bevelSize * 2;

	}

	/**
	 * For tabs and top buttons we need to draw default state darker then checked
	 *
	 * @return true if drawable used for tabs or top buttons
	 */
	private boolean isCheckable() {
		return rectPosition == TAB_LEFT || rectPosition == TAB_MIDDLE || rectPosition == TAB_RIGHT
				|| rectPosition == TOP_LEFT || rectPosition == TOP_MIDDLE || rectPosition == TOP_RIGHT
				|| rectPosition == TABLET_LEFT || rectPosition == TABLET_MIDDLE || rectPosition == TABLET_RIGHT;
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.getClipBounds(clipRect);
		if (!boundsInit) {
			initBounds(canvas);
		}

		if (rectPosition == LIST_ITEM) {
//			canvas.drawColor(Color.BLUE);
		}

		if (rectPosition == TOP_MIDDLE) {
//			canvas.drawColor(Color.GRAY);
		}

		lightLinePaint.setColorFilter(currentFilter);
		darkLinePaint.setColorFilter(currentFilter);

		canvas.drawRect(lightRect, lightLinePaint);
		canvas.drawRect(darkRect, darkLinePaint);
	}

	private void initBounds(Canvas canvas) {
		canvas.getClipBounds(clipBounds);
		int width = clipBounds.width();
		int height = clipBounds.height();

		int noLeft = -edgeOffset;
		int noTop = -edgeOffset;
		int noRight = width + edgeOffset;
		int noBottom = height + edgeOffset;

		int showTop = edgeOffset / 3; // show top line
		int showTop2 = edgeOffset - edgeOffset / 4;
		int showLeft = edgeOffset / 3;
		int showRight = width - edgeOffset / 4;
		int showBottom = height - edgeOffset / 4;

		switch (rectPosition) {
			case TOP_LEFT:
				darkRect.set(noLeft, showTop, showRight, showBottom);
				lightRect.set(noLeft, showTop2, noRight, noBottom);
				break;
			case TOP_MIDDLE:
				darkRect.set(noLeft, showTop, showRight, showBottom);
				lightRect.set(showLeft, showTop2, noRight, noBottom);
				break;
			case TOP_RIGHT:
				darkRect.set(noLeft, showTop, noRight, showBottom);
				lightRect.set(showLeft, showTop2, noRight, noBottom);
				break;
			case TAB_LEFT:
				darkRect.set(noLeft, showTop, showRight, showBottom);
				lightRect.set(noLeft, showTop2, noRight, noBottom);

				break;
			case TAB_MIDDLE:
				darkRect.set(noLeft, showTop, showRight, showBottom);
				lightRect.set(showLeft, showTop2, noRight, noBottom);
				break;
			case TAB_RIGHT:
				darkRect.set(noLeft, showTop, noRight, showBottom);
				lightRect.set(showLeft, showTop2, noRight, noBottom);
				break;
			case BOTTOM_LEFT:
				darkRect.set(noLeft, showTop, showRight, noBottom);
				lightRect.set(noLeft, showTop2, noRight, noBottom);
				break;
			case BOTTOM_MIDDLE:
				darkRect.set(noLeft, showTop, showRight, noBottom);
				lightRect.set(showLeft, showTop2, noRight, noBottom);
				break;
			case BOTTOM_RIGHT:
				darkRect.set(noLeft, showTop, noRight, noBottom);
				lightRect.set(showLeft, showTop2, noRight, noBottom);
				break;
			case LIST_ITEM:
			case LIST_ITEM_HEADER:
			case LIST_ITEM_HEADER_2:
			case LIST_ITEM_HEADER_TOP:
			case LIST_ITEM_HEADER_DARK:
			case LIST_ITEM_HEADER_2_DARK:
			case LIST_ITEM_WHITE:
				darkRect.set(noLeft, noTop, noRight, showBottom);
				lightRect.set(noLeft, showTop, noRight, noBottom);
				break;

			// show only side dark borders
			case SIDE_LEFT:
				darkRect.set(showLeft, noTop, showRight, noBottom);
				lightRect.set(noLeft, noTop, noRight, noBottom);
				break;
			case SIDE_MIDDLE:
				darkRect.set(showLeft, noTop, showRight, noBottom);
				lightRect.set(noLeft, noTop, noRight, noBottom);
				break;
			case SIDE_RIGHT:
				darkRect.set(noLeft, noTop, showRight, noBottom);
				lightRect.set(noLeft, noTop, noRight, noBottom);
				break;

			// same as tabs, but with dark background
			case TABLET_LEFT:
				darkRect.set(noLeft, showTop, showRight, showBottom);
				lightRect.set(noLeft, showTop2, noRight, noBottom);
				break;
			case TABLET_MIDDLE:
				darkRect.set(noLeft, showTop, showRight, showBottom);
				lightRect.set(showLeft, showTop2, noRight, noBottom);
				break;
			case TABLET_RIGHT:
				darkRect.set(noLeft, showTop, noRight, showBottom);
				lightRect.set(showLeft, showTop2, noRight, noBottom);
				break;

//			case SILVER_LEFT:   // seems that not used anywhere
//				lightRect.set(-edgeOffset, 0, showRight, height);
//				break;
//			case SILVER_MIDDLE:
//				lightRect.set(showLeft, 0, showRight, height);
//				break;
//			case SILVER_RIGHT:
//				lightRect.set(showLeft, 0, width + edgeOffset, height);
//				break;
		}

		boundsInit = true;
	}

	@Override
	protected boolean onStateChange(int[] states) {
		boundsInit = false;

		return super.onStateChange(states);
	}

	@Override
	protected void onBoundsChange(Rect bounds) {
		boundsInit = false;
		invalidateSelf();
		super.onBoundsChange(bounds);
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
			rectPosition = array.getInt(R.styleable.RoboButton_btn_rect_pos, BOTTOM_MIDDLE);
		} finally {
			array.recycle();
		}
	}

}
