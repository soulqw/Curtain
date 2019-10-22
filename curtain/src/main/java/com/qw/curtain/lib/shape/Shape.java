package com.qw.curtain.lib.shape;

import android.graphics.Canvas;
import android.graphics.Paint;

import com.qw.curtain.lib.HollowInfo;

public interface Shape {

    /**
     * 画你想要的任何形状
     */
    void drawShape(Canvas canvas, Paint paint, HollowInfo info);

}
