package com.qw.curtain.sample;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.qw.curtain.lib.Curtain;
import com.qw.curtain.lib.IGuide;

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

    private void showInitGuide() {
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.iv_guide_first))
                .setCallBack(new Curtain.CallBack() {
                    @Override
                    public void onShow(final IGuide iGuide) {

                    }

                    @Override
                    public void onDismiss(IGuide iGuide) {
                        showSecondGuide();
                    }
                }).show();
    }

    private void showSecondGuide() {
        new Curtain(MainActivity.this)
                .with(findViewById(R.id.btn_open_left))
                .setTopView(R.layout.view_guide)
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
