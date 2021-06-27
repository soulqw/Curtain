package com.qw.curtain.lib;

import android.view.View;

public interface OnViewInTopClickListener<T> {

    /**
     * the onclick listener of view in the top
     *
     * @param current     the clicked view
     * @param currentHost the host curtain or curtainFlow
     */
    void onClick(View current, T currentHost);

}
