package com.qw.curtain.lib;

import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.CheckResult;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.qw.curtain.lib.debug.CurtainDebug;


public class ViewGetter {

    /**
     * get the child item form adapter view such as ListView,GridView
     *
     * @param targetContainer such as ListView
     * @param position        the position of the adapter view
     * @return ItemView
     * @see android.widget.ListView
     * @see android.widget.GridView
     */
    @Nullable
    @CheckResult
    public static View getFromAdapterView(AdapterView targetContainer, int position) {
        View result = targetContainer.getChildAt(position - targetContainer.getFirstVisiblePosition());
        if (null == result) {
            CurtainDebug.w(Constance.TAG, "your target position may not on screen now");
        }
        return result;
    }

    /**
     * get the child item of the recycler view
     *
     * @param targetContainer RecyclerView
     * @param position        the position of the recycler view
     * @return ItemView
     */
    @Nullable
    @CheckResult
    public static View getFromRecyclerView(RecyclerView targetContainer, int position) {
        if (targetContainer.getLayoutManager() == null) {
            CurtainDebug.w(Constance.TAG, "recyclerView did not have layoutManager yet");
            return null;
        }
        return targetContainer.getLayoutManager().getChildAt(position);
    }

}
