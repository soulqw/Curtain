package com.qw.curtain.lib;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 * @author cd5160866
 */
public interface IGuide {

    /**
     * you can refresh the hollow fields when it showed
     *
     * @param hollows the hollow fields
     */
    void updateHollows(HollowInfo... hollows);

    /**
     * you can refresh the top view
     *
     * @param layoutId the view res
     */
    void updateTopView(@LayoutRes int layoutId);

    /**
     * if you want do more operate in top view (onClickListener or onTouchListener)
     * you can find it by this method
     * if you just need an onclick listener in top view you can use Curtain.addOnTopViewClickListener instead
     *
     * @param id
     * @param <T>
     * @see #Curtain.addOnTopViewClickListener()
     */
    <T extends View> T findViewByIdInTopView(@IdRes int id);

    /**
     * dismiss the curtain
     */
    void dismissGuide();

}
