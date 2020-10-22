package com.qw.curtain.lib.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.qw.curtain.lib.HollowInfo;

public interface Shape {

    /**
     * draw any shape you want
     */
    void drawShape(Canvas canvas, Paint paint, HollowInfo info);

}
