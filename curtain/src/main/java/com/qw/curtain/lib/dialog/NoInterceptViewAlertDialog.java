package com.qw.curtain.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.util.SparseArray;
import android.view.MotionEvent;

import androidx.annotation.NonNull;

import com.qw.curtain.lib.HollowInfo;
import com.qw.curtain.lib.debug.CurtainDebug;

public class NoInterceptViewAlertDialog extends Dialog {

    public static final String TAG = "NoInterceptViewAlertDialog";

    private final SparseArray<HollowInfo> hollows;

    public NoInterceptViewAlertDialog(@NonNull Context context, int themeResId, SparseArray<HollowInfo> hollows) {
        super(context, themeResId);
        this.hollows = hollows;
    }

    @Override
    public boolean onTouchEvent(@NonNull MotionEvent event) {
        return super.onTouchEvent(event) || tryHandByHollows(event);
    }

    private boolean tryHandByHollows(MotionEvent event) {
        for (int i = 0, size = hollows.size(); i < size; i++) {
            HollowInfo current = hollows.valueAt(i);
            if (isEventInHollowField(event, current)) {
                return current.targetView.dispatchTouchEvent(event);
            }
        }
        return false;
    }

    private boolean isEventInHollowField(MotionEvent event, HollowInfo hollowInfo) {
        int[] hollowLocation = new int[2];
        hollowInfo.targetView.getLocationOnScreen(hollowLocation);
        int xRangeEnd = hollowLocation[0] + hollowInfo.targetView.getWidth();
        int yRangeEnd = hollowLocation[1] + hollowInfo.targetView.getHeight();
        boolean inXRange = (event.getRawX() > hollowLocation[0]) && (event.getRawX() < xRangeEnd);
        boolean inYRange = (event.getRawY() > hollowLocation[1]) && (event.getRawY() < yRangeEnd);
        CurtainDebug.d(TAG, " eventRawX " + event.getRawX() + " eventRawY " + event.getRawY());
        CurtainDebug.d(TAG, " inX " + inXRange + " inY " + inYRange);
        return inXRange && inYRange;
    }

}
