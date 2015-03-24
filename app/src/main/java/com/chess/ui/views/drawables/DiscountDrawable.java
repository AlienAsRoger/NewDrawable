package com.chess.ui.views.drawables;

import android.graphics.*;
import android.graphics.drawable.Drawable;

/**
 * Created with IntelliJ IDEA.
 * User: roger sent2roger@gmail.com
 * Date: 24.03.2015
 * Time: 19:04
 */
public class DiscountDrawable extends Drawable {

	private final Paint paint;
	private final Path path;
	private Rect boundsRect;

	public DiscountDrawable(int color) {

		paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		paint.setColor(color);
		paint.setStyle(Paint.Style.FILL);

//		paint.setColor(Color.BLUE);
		boundsRect = new Rect();

		path = new Path();
	}

	@Override
	public void draw(Canvas canvas) {
		canvas.getClipBounds(boundsRect);
		int width = boundsRect.right;
		int height = boundsRect.bottom;

		int arrowLength = 50;
		// draw rectangle and triangle
		canvas.drawRect(arrowLength,0, width, height, paint);

		path.moveTo(0, height/2);
		path.lineTo(arrowLength, 0);

		path.lineTo(arrowLength, height);
//		path.lineTo(width, 0);
		path.close();

		canvas.drawPath(path, paint);

	}

	@Override
	public void setAlpha(int alpha) {

	}

	@Override
	public void setColorFilter(ColorFilter cf) {

	}

	@Override
	public int getOpacity() {
		return 0;
	}
}
