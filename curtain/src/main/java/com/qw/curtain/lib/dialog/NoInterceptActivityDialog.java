package com.qw.curtain.lib.dialog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

public class NoInterceptActivityDialog extends Dialog {

    public NoInterceptActivityDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return super.onTouchEvent(event) || tryHandleByActivity(event);
    }

    private boolean tryHandleByActivity(MotionEvent ev) {
        Activity activity = getOwnerActivity();
        if (activity != null) {
            return activity.dispatchTouchEvent(ev);
        }
        return false;
    }

}
