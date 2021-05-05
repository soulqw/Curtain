package com.qw.curtain.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;

import androidx.annotation.NonNull;

import java.lang.reflect.Field;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;
import static com.qw.curtain.lib.InnerUtils.getScreenHeight;
import static com.qw.curtain.lib.InnerUtils.getScreenWidth;
import static com.qw.curtain.lib.InnerUtils.getStatusBarHeight;

/**
 * @author cd5160866
 */
public class GuideView extends View {

    private HollowInfo[] mHollows;

    private OptimizedMap<HollowInfo, HollowInfo> mPositionCache;

    private int mCurtainColor = 0x88000000;

    private Paint mPaint;

    public GuideView(@NonNull Context context) {
        super(context, null);
        init();
    }

    public void setHollowInfo(@NonNull HollowInfo... hollows) {
        this.mHollows = hollows;
        postInvalidate();
    }

    public void setCurtainColor(int color) {
        this.mCurtainColor = color;
        postInvalidate();
    }

    private void init() {
        mPaint = new Paint(ANTI_ALIAS_FLAG);
        mPositionCache = new OptimizedMap<>();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int count;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            count = canvas.saveLayer(0, 0, getWidth(), getHeight(), null);
        } else {
            count = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        }
        drawBackGround(canvas);
        drawHollowFields(canvas);
        canvas.restoreToCount(count);
    }

    private void drawBackGround(Canvas canvas) {
        mPaint.setXfermode(null);
        mPaint.setColor(mCurtainColor);
        canvas.drawRect(0, 0, getWidth(), getHeight(), mPaint);
    }

    private void drawHollowFields(Canvas canvas) {
        mPaint.setColor(Color.WHITE);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.DST_OUT));
        for (HollowInfo mHollow : mHollows) {
            drawSingleHollow(mHollow, canvas);
        }
    }

    private void drawSingleHollow(HollowInfo info, Canvas canvas) {
        if (mHollows.length <= 0) {
            return;
        }
        HollowInfo fromCache = mPositionCache.get(info);
        if (null != fromCache) {
            realDrawHollows(fromCache, canvas);
            return;
        }
        info.targetBound = new Rect();
        info.targetView.getDrawingRect(info.targetBound);
        int[] viewLocation = new int[2];
        info.targetView.getLocationOnScreen(viewLocation);
        info.targetBound.left = viewLocation[0];
        info.targetBound.top = viewLocation[1];
        info.targetBound.right += info.targetBound.left;
        info.targetBound.bottom += info.targetBound.top;
        //set the padding
        setTheBoundPadding(info);
        //set the offset
        if (info.getOffset((HollowInfo.VERTICAL)) > 0) {
            info.targetBound.top += info.getOffset(HollowInfo.VERTICAL);
            info.targetBound.bottom += info.getOffset(HollowInfo.VERTICAL);
        }
        if (info.getOffset(HollowInfo.HORIZONTAL) > 0) {
            info.targetBound.right += info.getOffset(HollowInfo.HORIZONTAL);
            info.targetBound.left += info.getOffset(HollowInfo.HORIZONTAL);
        }
        //status bar height
        info.targetBound.top -= getStatusBarHeight(getContext());
        info.targetBound.bottom -= getStatusBarHeight(getContext());
        //draw highlight info
        realDrawHollows(info, canvas);
        mPositionCache.put(info, info);
    }

    private void setTheBoundPadding(HollowInfo info) {
        Padding padding = info.padding;
        if (null == padding) {
            return;
        }
        boolean isAllPadding = padding.isAll();
        int allPadding = padding.getSizeByDirection(Padding.ALL);
        Rect bound = info.targetBound;
        bound.left -= isAllPadding ? allPadding : padding.getSizeByDirection(Padding.LEFT);
        bound.top -= isAllPadding ? allPadding : padding.getSizeByDirection(Padding.TOP);
        bound.right += isAllPadding ? allPadding : padding.getSizeByDirection(Padding.RIGHT);
        bound.bottom += isAllPadding ? allPadding : padding.getSizeByDirection(Padding.BOTTOM);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getScreenWidth(getContext()), getScreenHeight(getContext()) * 2);
    }

    private void realDrawHollows(HollowInfo info, Canvas canvas) {
        if (!drawHollowSpaceIfMatched(info, canvas)) {
            //did not match the shape so draw a rect
            canvas.drawRect(info.targetBound, mPaint);
        }
    }

    private boolean drawHollowSpaceIfMatched(HollowInfo info, Canvas canvas) {
        //user custom shape
        if (null != info.shape) {
            info.shape.drawShape(canvas, mPaint, info);
            return true;
        }
        //check need auto adapt if needed
        if (!info.isAutoAdaptViewBackGround()) {
            return false;
        }
        //android shape backGround
        Drawable drawable = info.targetView.getBackground();
        if (drawable instanceof GradientDrawable) {
            drawGradientHollow(info, canvas, drawable);
            return true;
        }
        //android selector backGround
        if (drawable instanceof StateListDrawable) {
            if (drawable.getCurrent() instanceof GradientDrawable) {
                drawGradientHollow(info, canvas, drawable.getCurrent());
                return true;
            }
        }
        return false;
    }

    private void drawGradientHollow(HollowInfo info, Canvas canvas, Drawable drawable) {
        Field fieldGradientState;
        Object mGradientState = null;
        int shape = GradientDrawable.RECTANGLE;
        try {
            fieldGradientState = Class.forName("android.graphics.drawable.GradientDrawable").getDeclaredField("mGradientState");
            fieldGradientState.setAccessible(true);
            mGradientState = fieldGradientState.get(drawable);
            Field fieldShape = mGradientState.getClass().getDeclaredField("mShape");
            fieldShape.setAccessible(true);
            shape = (int) fieldShape.get(mGradientState);
        } catch (Exception e) {
            e.printStackTrace();
        }
        float mRadius = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            mRadius = ((GradientDrawable) drawable).getCornerRadius();
        } else {
            try {
                Field fieldRadius = mGradientState.getClass().getDeclaredField("mRadius");
                fieldRadius.setAccessible(true);
                mRadius = (float) fieldRadius.get(mGradientState);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (shape == GradientDrawable.OVAL) {
            canvas.drawOval(new RectF(info.targetBound.left, info.targetBound.top, info.targetBound.right, info.targetBound.bottom), mPaint);
        } else {
            float rad = Math.min(mRadius,
                    Math.min(info.targetBound.width(), info.targetBound.height()) * 0.5f);
            canvas.drawRoundRect(new RectF(info.targetBound.left, info.targetBound.top, info.targetBound.right, info.targetBound.bottom), rad, rad, mPaint);
        }
    }

}
