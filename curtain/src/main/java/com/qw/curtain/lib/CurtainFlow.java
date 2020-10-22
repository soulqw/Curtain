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
     * 需要协同的 Curtain
     *
     * @param curtainId 添加的curtain 会按照id大小顺序先后展示，id越小 优先级越高
     * @param curtain   你构建的Curtain对象
     */
    public void addCurtain(int curtainId, Curtain curtain) {
        allCurtains.append(curtainId, curtain);
    }

    public void start() {
        start(null);
    }

    /**
     * @param callBack 回调
     */
    public void start(final CallBack callBack) {
        this.callBack = callBack;
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
        GuideView guideView = new GuideView(curtain.activity);
        guideView.setCurtainColor(curtain.curtainColor);
        curtain.addHollows(guideView);
        guider.setGuideView(guideView);
        guider.setCancelable(curtain.cancelBackPressed);
        guider.setAnimationStyle(curtain.animationStyle);
        guider.setTopViewRes(curtain.topViewId);
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
         * 每一步的Curtain展示将会回调
         *
         * @param currentId   这一步的id
         * @param curtainFlow 整个Flow对象 可控制前进，回退，找到当前Curtain中的Id等
         */
        void onProcess(int currentId, CurtainFlowInterface curtainFlow);

        /**
         * 当流程结束后触发
         */
        void onFinish();

    }

    public static class Builder {

        SparseArray<Curtain> sc;

        public Builder() {
            this.sc = new SparseArray<>();
        }

        /**
         * 需要协同的 Curtain
         *
         * @param curtainId 添加的curtain 会按照id大小顺序先后展示，id越小 优先级越高
         * @param curtain   你构建的Curtain对象
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
