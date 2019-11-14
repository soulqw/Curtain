package com.qw.curtain.lib;

import android.util.SparseArray;
import android.view.View;

import com.qw.curtain.lib.debug.CurtainDebug;
import com.qw.curtain.lib.flow.ICurtainFlow;

/**
 * manage a series of curtains
 *
 * @author: george
 * @date: 2019-11-09
 */
public class CurtainFlow implements ICurtainFlow {

    private SparseArray<Curtain> allCurtains;

    private GuideDialogFragment guider;

    private int currentCurtainId = -1;

    public CurtainFlow() {
        allCurtains = new SparseArray<>();
    }

    public void addCurtain(int key, Curtain curtain) {
        allCurtains.append(key, curtain);
    }

    public void start() {
        start(null);
    }

    public void start(CallBack callBack) {
        if (allCurtains.size() == 0) {
            return;
        }
        Curtain curtain = allCurtains.valueAt(0);
        currentCurtainId = allCurtains.keyAt(0);
        if (curtain.hollows.size() == 0) {
            CurtainDebug.w(Constance.TAG, "with out any views");
            return;
        }
        View checkStatusView = curtain.hollows.valueAt(0).targetView;
        if (checkStatusView.getWidth() == 0) {
            checkStatusView.post(new Runnable() {
                @Override
                public void run() {
                    start();
                }
            });
            return;
        }
        guider = new GuideDialogFragment();
        guider.setCancelable(curtain.cancelBackPressed);
        guider.setAnimationStyle(curtain.animationStyle);
        guider.setTopViewRes(curtain.topViewId);
        GuideView guideView = new GuideView(curtain.activity);
        guideView.setCurtainColor(curtain.curtainColor);
        curtain.addHollows(guideView);
        guider.show(guideView);
        if (null != callBack) {
            callBack.onStart(currentCurtainId, this);
        }
    }

    @Override
    public void push() {
    }

    @Override
    public void pop() {
    }

    @Override
    public void toNodeById(int curtainId) {
    }

    @Override
    public <T extends View> T findViewInCurrentCurtain(int id) {
        return null;
    }

    @Override
    public void finish() {
    }

    public interface CallBack {

        void onStart(int currentId, ICurtainFlow curtainFlow);

    }


}
