package com.qw.curtain.sample;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.CurtainFlow;
import com.qw.curtain.lib.flow.CurtainFlowInterface;
import com.qw.curtain.lib.shape.RoundShape;

public class CurtainFlowGuideActivity extends AppCompatActivity {

    /**
     * 第一步 高亮一个View
     */
    private static final int ID_STEP_1 = 1;

    /**
     * 第二步 高亮一个带圆形的View
     */
    private static final int ID_STEP_2 = 2;

    /**
     * 第三步 为一个View指定自定义的透明形状
     */
    private static final int ID_STEP_3 = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //first guide
        showInitGuide();
        findViewById(R.id.btn_open_left).setVisibility(View.GONE);
    }

    private void showInitGuide() {
        new CurtainFlow.Builder()
                .with(ID_STEP_1, getStepOneGuide())
                .with(ID_STEP_2, getStepTwoGuide())
                .with(ID_STEP_3, getStepThreeGuide())
                .create()
                .start(new CurtainFlow.CallBack() {
                    @Override
                    public void onProcess(int currentId, final CurtainFlowInterface curtainFlow) {
                        switch (currentId) {
                            case ID_STEP_2:
                                //回到上个
                                curtainFlow.findViewInCurrentCurtain(R.id.tv_to_last)
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                curtainFlow.pop();
                                            }
                                        });
                                break;
                            case ID_STEP_3:
                                curtainFlow.findViewInCurrentCurtain(R.id.tv_to_last)
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                curtainFlow.pop();
                                            }
                                        });
                                //重新来一遍，即回到第一步
                                curtainFlow.findViewInCurrentCurtain(R.id.tv_retry)
                                        .setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                curtainFlow.toCurtainById(ID_STEP_1);
                                            }
                                        });
                                break;
                        }
                        //去下一个
                        curtainFlow.findViewInCurrentCurtain(R.id.tv_to_next)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        curtainFlow.push();
                                    }
                                });
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(CurtainFlowGuideActivity.this, "all flow ended", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private Curtain getStepOneGuide() {
        return new Curtain(CurtainFlowGuideActivity.this)
                .with(findViewById(R.id.iv_guide_first))
                .setTopView(R.layout.view_guide_flow1);
    }

    private Curtain getStepTwoGuide() {
        return new Curtain(CurtainFlowGuideActivity.this)
                .with(findViewById(R.id.btn_shape_circle))
                .setTopView(R.layout.view_guide_flow2);
    }

    private Curtain getStepThreeGuide() {
        return new Curtain(CurtainFlowGuideActivity.this)
                //自定义高亮形状
                .withShape(findViewById(R.id.btn_shape_custom), new RoundShape(12))
                //自定义高亮形状的Padding
                .withPadding(findViewById(R.id.btn_shape_custom), 24)
                .setTopView(R.layout.view_guide_flow3);
    }

}
