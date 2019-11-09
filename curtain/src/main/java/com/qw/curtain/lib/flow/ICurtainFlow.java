package com.qw.curtain.lib.flow;

/**
 * @author: george
 * @date: 2019-11-09
 */
public interface ICurtainFlow {

    void push();

    void pop();

    void toNodeById(int curtainId);

    void finish();

}
