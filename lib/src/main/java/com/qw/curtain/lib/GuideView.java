package com.qw.curtain.lib;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import java.util.HashMap;

import androidx.annotation.NonNull;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

/**
 * @author cd5160866
 */
public class GuideView extends View {

    private HollowInfo[] mHollows;

    private HashMap<HollowInfo, Rect> mPositionCache;

    private int mCurtainColor = 0x88000000;

    private Paint mPaint;

    public GuideView(@NonNull Context context) {
        super(context, null);
        init();
    }

    public void addHollowInfo(@NonNull HollowInfo... hollows) {
        this.mHollows = hollows;
        postInvalidate();
    }

    public void setCurtainColor(int color) {
        this.mCurtainColor = color;
        postInvalidate();
    }

    private void init() {
        mPaint = new Paint(ANTI_ALIAS_FLAG);
        mPositionCache = new HashMap<>(10);
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
        Rect fromCache = mPositionCache.get(info);
        if (null != fromCache) {
            canvas.drawRect(fromCache, mPaint);
            return;
        }
        if (null == info.targetBound) {
            info.targetBound = new Rect();
            info.targetView.getDrawingRect(info.targetBound);
        }
        int[] viewLocation = new int[2];
        info.targetView.getLocationOnScreen(viewLocation);
        info.targetBound.left = viewLocation[0];
        info.targetBound.top = viewLocation[1];
        info.targetBound.right += info.targetBound.left;
        info.targetBound.bottom += info.targetBound.top;
        //计算padding(对内)
        int padding = info.padding;
        info.targetBound.left -= padding;
        info.targetBound.top -= padding;
        info.targetBound.right += padding;
        info.targetBound.bottom += padding;
        //计算偏移量(对外)
        if (info.getOffset((HollowInfo.VERTICAL)) > 0) {
            info.targetBound.top += info.getOffset(HollowInfo.VERTICAL);
            info.targetBound.bottom += info.getOffset(HollowInfo.VERTICAL);
        }
        if (info.getOffset(HollowInfo.HORIZONTAL) > 0) {
            info.targetBound.right += info.getOffset(HollowInfo.HORIZONTAL);
            info.targetBound.left += info.getOffset(HollowInfo.HORIZONTAL);
        }
        //status bar height
        info.targetBound.top -= getStatusBarHeight();
        info.targetBound.bottom -= getStatusBarHeight();
        canvas.drawRect(info.targetBound, mPaint);
        mPositionCache.put(info, info.targetBound);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getScreenWidth(), getScreenHeight() * 2);
    }

    /**
     * 获取屏幕的宽度
     */
    private int getScreenWidth() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    /**
     * 获取屏幕的高度
     */
    private int getScreenHeight() {
        WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    private int getStatusBarHeight() {
        int statusBarHeight = 0;
        int resourceId = getContext().getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = getContext().getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight;
    }
}
