package com.qw.curtain.lib.flow;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * @author: george
 */
public interface CurtainFlowInterface {

    /**
     * to the next
     * if next is null , it will be finished
     *
     * @see CurtainFlowInterface#finish()
     */
    void push();

    /**
     * back to former one
     */
    void pop();

    /**
     * to the curtain by their id
     *
     * @param curtainId
     */
    void toCurtainById(int curtainId);

    /**
     * find the view element in curtain,s top view
     *
     * @see com.qw.curtain.lib.Curtain#setTopView(int)
     */
    <T extends View> T findViewInCurrentCurtain(@IdRes int id);

    /**
     * finish
     */
    void finish();


}
