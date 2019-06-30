package com.qw.curtain.lib;

import android.graphics.Rect;
import android.util.SparseArray;
import android.view.View;

import androidx.annotation.ColorRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.StyleRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * @author cd5160866
 */
public class GuideHelper {

    private FragmentActivity activity;

    private SparseArray<HollowInfo> hollows;

    private CallBack callBack;

    private boolean cancelBackPressed = true;

    private int curtainColor = 0;

    private int topViewId;

    private int animationStyle = 0;


    public GuideHelper(Fragment fragment) {
        this(fragment.getActivity());
    }

    public GuideHelper(FragmentActivity activity) {
        this.activity = activity;
        this.hollows = new SparseArray<>();
    }

    /**
     * @param which 页面上任一要高亮的view
     */
    public GuideHelper with(View which) {
        if (which.getId() == View.NO_ID) {
            throw new IllegalArgumentException("view must have an id");
        }
        HollowInfo info = findHollow(which);
        if (null == info) {
            append(which);
        }
        return this;
    }

    /**
     * 指定区域的padding
     *
     * @param which 该view对应的蒙层区域
     */
    public GuideHelper withPadding(View which, int padding) {
        if (which.getId() == View.NO_ID) {
            throw new IllegalArgumentException("view must have an id");
        }
        HollowInfo info = findHollow(which);
        if (null == info) {
            info = append(which);
        }
        info.padding = padding;
        return this;
    }

    /**
     * 指定蒙层大小
     *
     * @param which  以该View的左上角作为初始坐标
     * @param width  宽
     * @param height 高
     */
    public GuideHelper withSize(View which, int width, int height) {
        if (which.getId() == View.NO_ID) {
            throw new IllegalArgumentException("view must have an id");
        }
        HollowInfo info = findHollow(which);
        if (null == info) {
            info = append(which);
        }
        info.targetBound = new Rect(0, 0, width, height);
        return this;
    }

    /**
     * 设置蒙层偏移量
     *
     * @param which     view对应产生的蒙层
     * @param offset    偏移量 px
     * @param direction 偏移方向
     */
    public GuideHelper withOffset(View which, int offset, @HollowInfo.direction int direction) {
        if (which.getId() == View.NO_ID) {
            throw new IllegalArgumentException("view must have an id");
        }
        HollowInfo info = findHollow(which);
        if (null == info) {
            info = append(which);
        }
        info.setOffset(offset, direction);
        return this;
    }

    /**
     * 自定义的引导页蒙层和镂空部分View
     */
    public GuideHelper setTopView(@LayoutRes int layoutId) {
        this.topViewId = layoutId;
        return this;
    }

    /**
     * 设置阴影部分颜色
     *
     * @param color 颜色
     */
    public GuideHelper setCurtainColor(int color) {
        this.curtainColor = color;
        return this;
    }

    public GuideHelper setCurtainColorRes(@ColorRes int color) {
        this.curtainColor = color;
        return this;
    }

    public GuideHelper setCancelBackPressed(boolean cancelBackPress) {
        this.cancelBackPressed = cancelBackPress;
        return this;
    }

    public GuideHelper setCallBack(CallBack callBack) {
        this.callBack = callBack;
        return this;
    }

    public GuideHelper setAnimationStyle(@StyleRes int animation) {
        this.animationStyle = animation;
        return this;
    }

    public void show() {
        if (hollows.size() == 0) {
            throw new IllegalStateException("with out any views");
        }
        GuideDialogFragment guider = new GuideDialogFragment();
        guider.setCancelable(cancelBackPressed);
        guider.setCallBack(callBack);
        guider.setAnimationStyle(animationStyle);
        guider.setTopViewRes(topViewId);
        GuideView guideView = new GuideView(activity);
        guideView.setCurtainColor(curtainColor);
        addHollows(guideView);
        guider.show(guideView);
    }

    private HollowInfo findHollow(View view) {
        for (int i = 0; i < hollows.size(); i++) {
            HollowInfo info = hollows.valueAt(i);
            if (info.targetView == view) {
                return info;
            }
        }
        return null;
    }

    private HollowInfo append(View view) {
        HollowInfo info = new HollowInfo(view);
        info.targetView = view;
        hollows.append(view.getId(), info);
        return info;
    }

    private void addHollows(GuideView guideView) {
        for (int i = 0; i < hollows.size(); i++) {
            HollowInfo hollowInfo = hollows.valueAt(i);
            guideView.addHollowInfo(hollowInfo);
        }
    }

    public interface CallBack {

        /**
         * 展示成功
         */
        void onShow(IGuide iGuide);

        /**
         * 消失
         */
        void onDismiss(IGuide iGuide);

    }
}