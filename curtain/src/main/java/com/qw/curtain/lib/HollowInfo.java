package com.qw.curtain.lib;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.IntDef;

import com.qw.curtain.lib.shape.Shape;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author cd5160866
 * 透明区域
 */
public class HollowInfo {

    private static final int SHIFT = 30;

    private static final int MODE_MASK = 0x3 << SHIFT;

    public static final int VERTICAL = 1 << SHIFT;

    public static final int HORIZONTAL = 2 << SHIFT;

    /**
     * 是否按照View背景形状自适应
     */
    private boolean autoAdaptViewBackGround = true;

    /**
     * 存偏移量和方向的变量
     */
    private int mOffsetMask;

    @IntDef(flag = true,
            value = {VERTICAL, HORIZONTAL})
    @Retention(RetentionPolicy.SOURCE)
    public @interface direction {
    }

    /**
     * 目标View 用于定位和确定透明区域大小
     */
    public View targetView;

    /**
     * 可自定义区域大小
     */
    public Rect targetBound;

    /**
     * 透明区域的padding
     */
    public int padding;

    /**
     * 指定的形状
     */
    public Shape shape;

    public void setAutoAdaptViewBackGround(boolean autoAdaptViewBackGround) {
        this.autoAdaptViewBackGround = autoAdaptViewBackGround;
    }

    /**
     * 指定形状
     *
     * @param shape 形状
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public HollowInfo(View targetView) {
        this.targetView = targetView;
    }

    /**
     * 设置透明区域的偏移量
     *
     * @param offset    对外
     * @param direction 方向
     * @see direction
     */
    public void setOffset(int offset, @direction int direction) {
        this.mOffsetMask = (offset & ~MODE_MASK) | (direction & MODE_MASK);
    }

    /**
     * 获得某个方向的偏移量
     *
     * @param direction 方向
     * @return offset
     * @see direction
     */
    public int getOffset(@direction int direction) {
        if ((mOffsetMask & MODE_MASK) == direction) {
            return mOffsetMask & ~MODE_MASK;
        }
        return 0;
    }

    public boolean isAutoAdaptViewBackGround() {
        return autoAdaptViewBackGround;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HollowInfo) {
            HollowInfo target = (HollowInfo) obj;
            return target.targetView == targetView;
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
