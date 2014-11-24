package com.chess.ui.views.drawables.smart_button;

import android.content.res.Resources;
import android.graphics.*;

/**
 * Created with IntelliJ IDEA.
 * User: roger sent2roger@gmail.com
 * Date: 08.03.14
 * Time: 20:40
 */
public class EnvelopeButtonDrawable extends RectButtonDrawable {

	private Paint rectPaint;
	private Rect boundsRect;

	private float envelopeRadius;
	private Path mPath = new Path();
	private boolean pathInitiated;
	private float density;
	private RectF rectArc;

	@Override
	void init(Resources resources) {
		super.init(resources);

		boundsRect = new Rect();

		rectArc = new RectF();

		int color = Color.WHITE;
		rectPaint = new Paint();
		rectPaint.setAntiAlias(true);

		rectPaint.setColor(color);
		rectPaint.setAntiAlias(true);
		rectPaint.setStyle(Paint.Style.FILL);

		density = resources.getDisplayMetrics().density;
		envelopeRadius = 5 * density;
	}

	@Override
	public void draw(Canvas canvas) {
		super.draw(canvas);

		canvas.save();
		canvas.getClipBounds(boundsRect);
		int width = boundsRect.width();
		int height = boundsRect.height();

		if (!pathInitiated) {

			float startAngle = 180f;
			float sweepAngle = 180f;

			float arcWidth = width / 6.29f; // TODO improve calculations, to make rounded part more small

			float x1 = width / 2.37f;
			float x2 = x1 + arcWidth;
			float y1 = height / 1.27f;

			float xInset = 1 * density;
			float yInsetTop = 2 * density;
			float yInsetBottom = 2 * density;

			// construct envelop triangle
			// set size of oval itself. radius works here as a height of arc
			rectArc.set(x1 - xInset, y1 - envelopeRadius - yInsetTop,
					x2 + xInset, y1 + envelopeRadius - yInsetBottom);

			mPath.moveTo(0, 0);
			mPath.lineTo(x1, y1);
			mPath.arcTo(rectArc, startAngle, -sweepAngle, false);
			mPath.lineTo(x2, y1);
			mPath.lineTo(width, 0);
			mPath.close();

			if (width > 0 && height > 0) {
				pathInitiated = true;
			}
		}

		canvas.drawPath(mPath, rectPaint);

		canvas.restore();
	}
}
