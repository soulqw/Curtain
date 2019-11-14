package com.qw.curtain.lib.flow;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * @author: george
 * @date: 2019-11-09
 */
public interface ICurtainFlow {

    void push();

    void pop();

    void toNodeById(int curtainId);

    <T extends View> T findViewInCurrentCurtain(@IdRes int id);

    void finish();

}
