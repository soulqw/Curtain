package com.qw.curtain.lib.flow;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * @author: george
 */
public interface CurtainFlowInterface {

    /**
     * 到下个
     * 如果下个没有，即等于 finish()
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
    void toCurtainById(int curtainId);

    /**
     * 找到当前展示curtain 中到view元素
     */
    <T extends View> T findViewInCurrentCurtain(@IdRes int id);

    /**
     * 结束
     */
    void finish();


}
