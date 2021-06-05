package com.qw.curtain.lib;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

/**
 * @author cd5160866
 */
public class GuideDialogFragment extends DialogFragment implements IGuide {

    private static final int MAX_CHILD_COUNT = 2;

    private static final int GUIDE_ID = 0x3;

    private FrameLayout contentView;

    private Dialog dialog;

    private int topLayoutRes = 0;

    private GuideView guideView;

    private Curtain.Param param;

    public static GuideDialogFragment newInstance(Curtain.Param param) {
        //build dialogFragment
        GuideDialogFragment guider = new GuideDialogFragment();
        guider.setParam(param);
        guider.setCancelable(param.cancelBackPressed);
        guider.setTopViewRes(param.topLayoutRes);

        //build contentView
        GuideView guideView = new GuideView(param.activity);
        guideView.setCurtainColor(param.curtainColor);
        SparseArray<HollowInfo> hollows = param.hollows;
        HollowInfo[] tobeDraw = new HollowInfo[hollows.size()];
        for (int i = 0; i < hollows.size(); i++) {
            tobeDraw[i] = hollows.valueAt(i);
        }
        guideView.setHollowInfo(tobeDraw);
        guider.setGuideView(guideView);

        return guider;
    }

    public void show() {
        guideView.setId(GUIDE_ID);
        this.contentView = new FrameLayout(guideView.getContext());
        this.contentView.addView(guideView);
        if (topLayoutRes != 0) {
            updateTopView();
        }
        show(param.fragmentManager, Constance.CURTAIN_FRAGMENT);
    }

    public void setParam(Curtain.Param param) {
        this.param = param;
    }

    public void setTopViewRes(int topLayoutRes) {
        this.topLayoutRes = topLayoutRes;
    }

    public void setGuideView(GuideView guideView) {
        this.guideView = guideView;
    }

    void updateContent() {
        contentView.removeAllViews();
        contentView.addView(guideView);
        updateTopView();
    }

    @Override
    public void show(FragmentManager manager, String tag) {
        try {
            super.show(manager, tag);
        } catch (Exception e) {
            manager.beginTransaction()
                    .add(this, tag)
                    .commitAllowingStateLoss();
        }
    }

    @Override
    public void updateHollows(HollowInfo... hollows) {
        GuideView guideView = contentView.findViewById(GUIDE_ID);
        if (null != guideView) {
            guideView.setHollowInfo(hollows);
        }
    }

    @Override
    public void updateTopView(@LayoutRes int layoutId) {
        if (null == contentView || getActivity() == null) {
            return;
        }
        setTopViewRes(layoutId);
        updateTopView();
    }

    @Override
    public <T extends View> T findViewByIdInTopView(int id) {
        if (null == contentView) {
            return null;
        }
        return contentView.findViewById(id);
    }

    @Override
    public void dismissGuide() {
        dismissAllowingStateLoss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        if (dialog == null) {
            dialog = new AlertDialog.Builder(requireActivity(), R.style.TransparentDialog)
                    .setView(contentView)
                    .create();
            setAnimation(dialog);
        }
        return dialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        try {
            super.onActivityCreated(savedInstanceState);
        } catch (Exception e) {
            return;
        }
        if (null != param.callBack) {
            param.callBack.onShow(this);
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if (null != param.callBack) {
            param.callBack.onDismiss(this);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (dialog != null) {
            dialog = null;
        }
    }

    private void setAnimation(Dialog dialog) {
        if (param.animationStyle != 0 && dialog != null && dialog.getWindow() != null) {
            dialog.getWindow().setWindowAnimations(param.animationStyle);
        }
    }

    private void updateTopView() {
        if (contentView.getChildCount() == MAX_CHILD_COUNT) {
            contentView.removeViewAt(1);
        }
        LayoutInflater.from(contentView.getContext()).inflate(topLayoutRes, contentView, true);
    }

}
