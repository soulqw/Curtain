package com.qw.curtain.lib;

import android.util.SparseArray;
import android.view.View;

import androidx.annotation.Nullable;

import com.qw.curtain.lib.debug.CurtainDebug;
import com.qw.curtain.lib.flow.CurtainFlowInterface;

/**
 * manage a series of curtains
 *
 * @author: george
 */
public class CurtainFlow implements CurtainFlowInterface {

    private SparseArray<Curtain> allCurtains;

    private GuideDialogFragment guider;

    private int currentCurtainId = -1;

    private CallBack callBack;

    public CurtainFlow() {
        allCurtains = new SparseArray<>();
    }

    /**
     * add the curtain to the flow
     *
     * @param curtainId the id of curtains ,they will execute order by curtainId
     * @see Curtain
     */
    public void addCurtain(int curtainId, Curtain curtain) {
        allCurtains.append(curtainId, curtain);
    }

    public void start() {
        start(null);
    }

    /**
     * @param callBack the call back
     */
    public void start(final CallBack callBack) {
        this.callBack = callBack;
        if (allCurtains.size() == 0) {
            return;
        }
        Curtain curtain = allCurtains.valueAt(0);
        currentCurtainId = allCurtains.keyAt(0);
        if (curtain.buildParams.hollows.size() == 0) {
            CurtainDebug.w(Constance.TAG, "with out any views");
            return;
        }
        View checkStatusView = curtain.buildParams.hollows.valueAt(0).targetView;
        if (checkStatusView.getWidth() == 0) {
            checkStatusView.post(new Runnable() {
                @Override
                public void run() {
                    start(callBack);
                }
            });
            return;
        }
        guider = new GuideDialogFragment();
        updateCurtainInfo(curtain);
        guider.show();
        if (null != callBack) {
            callBack.onProcess(currentCurtainId, this);
        }
    }

    @Override
    public void push() {
        int currentIndex = allCurtains.indexOfKey(currentCurtainId);
        int nextIndex = currentIndex + 1;
        Curtain nextNode = getNodeInFlow(allCurtains, nextIndex);
        if (null != nextNode) {
            doWhenCurtainUpdated(nextNode, nextIndex);
        } else {
            finish();
        }
    }

    @Override
    public void pop() {
        int currentIndex = allCurtains.indexOfKey(currentCurtainId);
        int lastIndex = currentIndex - 1;
        if (lastIndex < 0) {
            return;
        }
        Curtain lastNode = getNodeInFlow(allCurtains, lastIndex);
        if (null != lastNode) {
            doWhenCurtainUpdated(lastNode, lastIndex);
        }
    }

    @Override
    public void toCurtainById(int curtainId) {
        int targetIndex = allCurtains.indexOfKey(curtainId);
        Curtain target = allCurtains.valueAt(targetIndex);
        if (null != target) {
            doWhenCurtainUpdated(target, targetIndex);
        }
    }

    @Override
    public <T extends View> T findViewInCurrentCurtain(int id) {
        if (null != guider) {
            return guider.findViewByIdInTopView(id);
        }
        return null;
    }

    @Override
    public void finish() {
        if (guider != null) {
            guider.dismissGuide();
        }
        if (null != callBack) {
            callBack.onFinish();
        }
    }

    private void updateCurtainInfo(Curtain curtain) {
        Curtain.Param param = curtain.buildParams;
        GuideView guideView = new GuideView(param.activity);
        guideView.setCurtainColor(param.curtainColor);
        guideView.setHollowInfo(param.hollows);
        guider.setGuideView(guideView);
        guider.setCancelable(param.cancelBackPressed);
        guider.setTopViewRes(param.topLayoutRes);
        guider.setParam(param);
    }

    private void doWhenCurtainUpdated(Curtain curtain, int index) {
        updateCurtainInfo(curtain);
        guider.updateContent();
        currentCurtainId = allCurtains.keyAt(index);
        if (null != callBack) {
            callBack.onProcess(currentCurtainId, this);
        }
    }

    /**
     * valueAt have different result in different android version
     * https://github.com/soulqw/Curtain/issues/7
     */
    @Nullable
    private Curtain getNodeInFlow(SparseArray<Curtain> flows, int index) {
        try {
            return flows.valueAt(index);
        } catch (Exception e) {
            return null;
        }
    }

    public interface CallBack {

        /**
         * will be called when each curtain is showing
         *
         * @param currentId   the currentId
         * @param curtainFlow the interface of the curtainFlow
         * @see CurtainFlowInterface
         */
        void onProcess(int currentId, CurtainFlowInterface curtainFlow);

        /**
         * call when all the flow is finished
         */
        void onFinish();

    }

    public static class Builder {

        SparseArray<Curtain> sc;

        public Builder() {
            this.sc = new SparseArray<>();
        }

        /**
         * they will execute order by curtainId
         */
        public Builder with(int curtainId, Curtain curtain) {
            sc.append(curtainId, curtain);
            return this;
        }

        public CurtainFlow create() {
            CurtainFlow curtainFlow = new CurtainFlow();
            curtainFlow.allCurtains = sc;
            return curtainFlow;
        }
    }

}
