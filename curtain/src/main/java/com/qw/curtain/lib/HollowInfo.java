package com.qw.curtain.lib;

import android.graphics.Rect;
import android.view.View;

import androidx.annotation.IntDef;

import com.qw.curtain.lib.shape.Shape;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author cd5160866
 */
public class HollowInfo {

    private static final int SHIFT = 29;

    private static final int MODE_MASK = 0x3 << SHIFT;

    public static final int VERTICAL = 1 << SHIFT;

    public static final int HORIZONTAL = 2 << SHIFT;

    public static final int BOTH = 3 << SHIFT;

    /**
     * Whether the hollow，s shape will adjust the shape of the view automatic
     */
    private boolean autoAdaptViewBackGround = true;

    /**
     * The mask of offset and the direction
     */
    private int mOffsetMask;

    @IntDef(flag = true,
            value = {VERTICAL, HORIZONTAL, BOTH})
    @Retention(RetentionPolicy.SOURCE)
    public @interface direction {
    }

    /**
     * The target view to be highlight
     */
    public View targetView;

    /**
     * The Area of the highlight field
     */
    public Rect targetBound;

    /**
     * The padding of the highlight field
     */
    public Padding padding;

    /**
     * The shape of the highlight field
     */
    public Shape shape;

    public void setAutoAdaptViewBackGround(boolean autoAdaptViewBackGround) {
        this.autoAdaptViewBackGround = autoAdaptViewBackGround;
    }

    /**
     * set the  highlight shape
     *
     * @param shape highlight shape
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    public HollowInfo(View targetView) {
        this.targetView = targetView;
    }

    /**
     * set the offset of the highlight field
     *
     * @param offset    the offset
     * @param direction the direction
     * @see direction
     */
    public void setOffset(int offset, @direction int direction) {
        this.mOffsetMask = (offset & ~MODE_MASK) | (direction & MODE_MASK);
    }

    /**
     * get the offset of the target direction
     *
     * @param direction the target direction
     * @return offset of target direction
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
