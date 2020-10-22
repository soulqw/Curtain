package com.qw.curtain.lib.shape;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.qw.curtain.lib.HollowInfo;

/**
 * Circle
 */
public class CircleShape implements Shape {

    @Override
    public void drawShape(Canvas canvas, Paint paint, HollowInfo info) {
        canvas.drawOval(new RectF(info.targetBound.left, info.targetBound.top, info.targetBound.right, info.targetBound.bottom), paint);
    }
}
