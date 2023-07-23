package com.qw.curtain.sample;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.IGuide;
import com.qw.curtain.lib.OnViewInTopClickListener;
import com.qw.curtain.lib.Padding;
import com.qw.curtain.lib.shape.RoundShape;

public class SimpleGuideActivity extends AppCompatActivity {

    private static String TAG = "SimpleGuideActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_guide);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        final DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        drawer.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                showGuideLeft();
            }
        });
        toggle.syncState();
        findViewById(R.id.btn_open_left).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawer.openDrawer(GravityCompat.START);
            }
        });
        //first guide
        showInitGuide();

        findViewById(R.id.btn_shape_custom).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "custom click", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * 高亮图片
     * 高亮圆形文字，自动识别圆形背景
     * 高亮自定义按钮，形状自定圆角程度
     */
    private void showInitGuide() {
        new Curtain(SimpleGuideActivity.this)
                .with(findViewById(R.id.iv_guide_first))
                .with(findViewById(R.id.btn_shape_circle))
                .with(findViewById(R.id.btn_shape_custom))
                //自定义高亮形状
                .withShape(findViewById(R.id.btn_shape_custom), new RoundShape(12))
                //自定义高亮形状的Padding
//                .withPadding(findViewById(R.id.btn_shape_custom), Padding.only(30,20))
                .withPadding(findViewById(R.id.btn_shape_custom), Padding.all(10))
                .setTopView(R.layout.view_guide_1)
//                .setNoCurtainAnimation(true)
                //如果你希望高亮的目标View仍然可以响应touch事件的话(默认true)
                .setInterceptTargetView(false)
                //offset
//                .withOffset(findViewById(R.id.btn_shape_circle),20, HollowInfo.HORIZONTAL)
                //如果你不希望Curtain拦截蒙层之下的事件的话(默认true)
//                .setInterceptTouchEvent(false)
                //add onclick listener in the top view
                .addOnTopViewClickListener(R.id.tv_i_know, new OnViewInTopClickListener<IGuide>() {
                    @Override
                    public void onClick(View current, IGuide currentHost) {
                        //close the
                        currentHost.dismissGuide();
                    }
                })
                .setCallBack(new Curtain.CallBack() {
                    @Override
                    public void onShow(IGuide curtain) {
                        //get top view when curtain showed
                        Log.d(TAG, "on guide show: " + curtain.getCurrentTopView()
                                + "\n and size is" + curtain.getCurrentTopView().getWidth() + " " + curtain.getCurrentTopView().getHeight());
                    }

                    @Override
                    public void onDismiss(IGuide iGuide) {

                    }
                })
                .show();
    }


    private void showThirdGuide() {
        new Curtain(SimpleGuideActivity.this)
                .with(findViewById(R.id.btn_open_left))
                .setTopView(R.layout.view_guide_2)
                .addOnTopViewClickListener(R.id.tv_i_know, new OnViewInTopClickListener<IGuide>() {
                    @Override
                    public void onClick(View current, IGuide currentHost) {
                        currentHost.dismissGuide();
                    }
                }).show();
    }

    private void showGuideLeft() {
        new Curtain(SimpleGuideActivity.this)
                .with(findViewById(R.id.textView))
                .withPadding(findViewById(R.id.textView), 8)
                .with(findViewById(R.id.rv))
                .show();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

}
