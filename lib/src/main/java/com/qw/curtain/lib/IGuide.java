package com.qw.curtain.lib;

import android.view.View;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;

/**
 * @author cd5160866
 */
public interface IGuide {

    void updateHollows(HollowInfo... hollows);

    void updateTopView(@LayoutRes int layoutId);

    <T extends View> T findViewByIdInTopView(@IdRes int id);

    void dismiss();

}
