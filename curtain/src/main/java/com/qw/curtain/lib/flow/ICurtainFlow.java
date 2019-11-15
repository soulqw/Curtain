package com.qw.curtain.lib.flow;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * @author: george
 * @date: 2019-11-09
 */
public interface ICurtainFlow {

    /**
     * 推送到下个
     */
    void push();

    /**
     * 回到上个
     */
    void pop();

    /**
     * 按照id 去某个节点
     *
     * @param curtainId
     */
    void toNodeById(int curtainId);

    /**
     * 找到当前展示curtain 中到view元素
     */
    <T extends View> T findViewInCurrentCurtain(@IdRes int id);

    /**
     * 结束
     */
    void finish();


}
