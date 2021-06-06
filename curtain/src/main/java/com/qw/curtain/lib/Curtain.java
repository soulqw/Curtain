package com.qw.curtain.lib;

import android.content.Context;
import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.qw.curtain.lib.debug.CurtainDebug;
import com.qw.curtain.lib.shape.Shape;

/**
 * https://github.com/soulqw/Curtain
 *
 * @author cd5160866
 */
public class Curtain {

    Param buildParams;

    /**
     * fragment must have an host,so it must attach to an activity
     *
     * @param fragment use curtain in the fragment
     */
    public Curtain(@NonNull Fragment fragment) {
        this(fragment.requireActivity());
        buildParams.fragmentManager = fragment.getChildFragmentManager();
    }

    public Curtain(@NonNull FragmentActivity activity) {
        this.buildParams = new Param();
        buildParams.activity = activity;
        buildParams.hollows = new SparseArray<>();
        buildParams.fragmentManager = activity.getSupportFragmentManager();
    }

    /**
     * @param which the view that will be highLight
     */
    public Curtain with(@NonNull View which) {
        return with(which, true);
    }

    /**
     * @param which                     the view that will be highLight
     * @param isAutoAdaptViewBackGround Whether the shape of highLight will adjust view,s shape automatic
     * @see #withShape(View, Shape)
     */
    public Curtain with(@NonNull View which, boolean isAutoAdaptViewBackGround) {
        getHollowInfo(which)
                .setAutoAdaptViewBackGround(isAutoAdaptViewBackGround);
        return this;
    }

    /**
     * @param paddingSize the size of padding in all directions
     * @param which       the view will be set the padding
     */
    public Curtain withPadding(@NonNull View which, int paddingSize) {
        return withPadding(which, Padding.all(paddingSize));
    }

    /**
     * @param padding the describe of padding
     * @param which   the view will be set the padding
     * @see Padding  use Padding.all() or Padding.only() to build an padding
     */
    public Curtain withPadding(@NonNull View which, Padding padding) {
        getHollowInfo(which).padding = padding;
        return this;
    }

    /**
     * the size of highLight area
     *
     * @param which  the view,s location on screen will be the origin point of the area
     * @param width  the width
     * @param height the height
     */
    public Curtain withSize(@NonNull View which, int width, int height) {
        getHollowInfo(which).targetBound = new Rect(0, 0, width, height);
        return this;
    }

    /**
     * the offset of highLight area
     *
     * @param which     the view that will be highLight
     * @param offset    the offset px
     * @param direction the direction of offset
     */
    public Curtain withOffset(@NonNull View which, int offset, @HollowInfo.direction int direction) {
        getHollowInfo(which).setOffset(offset, direction);
        return this;
    }

    /**
     * the shape of highLight
     *
     * @param which the view that will be highLight
     * @param shape the shape of highLight shape
     */
    public Curtain withShape(@NonNull View which, Shape shape) {
        getHollowInfo(which).setShape(shape);
        return this;
    }

    /**
     * set the embellish view of the curtain
     */
    public Curtain setTopView(@LayoutRes int layoutId) {
        this.buildParams.topLayoutRes = layoutId;
        return this;
    }

    /**
     * set the color of the curtain
     *
     * @param color the color
     */
    public Curtain setCurtainColor(int color) {
        this.buildParams.curtainColor = color;
        return this;
    }

    public Curtain setCurtainColorRes(@ColorRes int color) {
        this.buildParams.curtainColor = color;
        return this;
    }

    /**
     * set the config whether can dismiss when back pressed
     *
     * @param cancelBackPress whether can dismiss when back pressed
     */
    public Curtain setCancelBackPressed(boolean cancelBackPress) {
        this.buildParams.cancelBackPressed = cancelBackPress;
        return this;
    }

    /**
     * set the call back of curtain show
     *
     * @param callBack the call back
     */
    public Curtain setCallBack(CallBack callBack) {
        this.buildParams.callBack = callBack;
        return this;
    }

    /**
     * set the animation the curtain will be show
     * the default config is alpha
     *
     * @param animation the animation style
     * @see R.style.dialogWindowAnim
     */
    public Curtain setAnimationStyle(@StyleRes int animation) {
        this.buildParams.animationStyle = animation;
        return this;
    }

    /**
     * remove the curtain animation
     */
    public Curtain setNoCurtainAnimation(boolean isNotNeed) {
        if (isNotNeed) {
            this.setAnimationStyle(Constance.STATE_NO_NEED_SET);
        }
        return this;
    }

    @MainThread
    public void show() {
        SparseArray<HollowInfo> hollows = buildParams.hollows;
        if (hollows.size() == 0) {
            CurtainDebug.w(Constance.TAG, "with out any views");
            return;
        }
        View checkStatusView = hollows.valueAt(0).targetView;
        if (checkStatusView.getWidth() == 0) {
            checkStatusView.post(new Runnable() {
                @Override
                public void run() {
                    show();
                }
            });
            return;
        }
        GuideDialogFragment
                .newInstance(buildParams)
                .show();
    }

    private HollowInfo getHollowInfo(View which) {
        SparseArray<HollowInfo> hollows = buildParams.hollows;
        HollowInfo info = hollows.get(which.hashCode());
        if (null == info) {
            info = new HollowInfo(which);
            info.targetView = which;
            hollows.append(which.hashCode(), info);
        }
        return info;
    }

    public static class Param {

        Context activity;

        FragmentManager fragmentManager;

        SparseArray<HollowInfo> hollows;

        int topLayoutRes;

        CallBack callBack;

        boolean cancelBackPressed = true;

        int curtainColor = 0xAA000000;

        int animationStyle = Constance.STATE_NOT_SET;

    }

    public interface CallBack {

        /**
         * call when show success
         */
        void onShow(IGuide iGuide);

        /**
         * call when dismiss
         */
        void onDismiss(IGuide iGuide);

    }

}