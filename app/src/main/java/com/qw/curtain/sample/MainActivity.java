package com.qw.curtain.sample;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.IGuide;
import com.qw.curtain.lib.shape.RoundShape;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
    }

    /**
     * 高亮图片
     * 高亮圆形文字，自动识别圆形背景
     * 高亮自定义按钮，形状自定圆角程度
     */
    private void showInitGuide() {
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.iv_guide_first))
                .with(findViewById(R.id.btn_shape_circle))
                .with(findViewById(R.id.btn_shape_custom))
                //自定义高亮形状
                .withShape(findViewById(R.id.btn_shape_custom), new RoundShape(12))
                //自定义高亮形状的Padding
                .withPadding(findViewById(R.id.btn_shape_custom), 24)
                .setTopView(R.layout.view_guide_1)
                .setCallBack(new Curtain.CallBack() {
                    @Override
                    public void onShow(final IGuide iGuide) {
                        iGuide.findViewByIdInTopView(R.id.tv_i_know)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        iGuide.dismissGuide();
                                    }
                                });
                    }

                    @Override
                    public void onDismiss(IGuide iGuide) {
                        showThirdGuide();
                    }
                }).show();
    }


    private void showThirdGuide() {
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.btn_open_left))
                .setTopView(R.layout.view_guide_2)
                .setCallBack(new Curtain.CallBack() {
                    @Override
                    public void onShow(final IGuide iGuide) {
                        iGuide.findViewByIdInTopView(R.id.tv_i_know)
                                .setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        iGuide.dismissGuide();
                                    }
                                });
                    }

                    @Override
                    public void onDismiss(IGuide iGuide) {

                    }
                }).show();
    }

    private void showGuideLeft() {
        new Curtain(MainActivity.this)
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
