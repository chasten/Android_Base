package com.android.base.androidbaseproject;

import android.app.Activity;
import android.support.annotation.LayoutRes;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

public class BaseActivity extends AppCompatActivity {

    public Activity mActivity;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
        this.initToolBar();
        mActivity = this;
    }


    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        ButterKnife.bind(this);
        this.initToolBar();
        mActivity = this;
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        ButterKnife.bind(this);
        this.initToolBar();
        if (null != getSupportActionBar()) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mActivity = this;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void initToolBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        if (null != toolbar) {
            setSupportActionBar(toolbar);
            if (null != getSupportActionBar()) {
                // TODO: 2018/3/1 修改资源id
                getSupportActionBar().setHomeAsUpIndicator(0);
            }
        } else {
            this.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void setDisplayHomeAsUpEnabled(boolean enabled) {
        ActionBar actionBar = getSupportActionBar();
        if (null != actionBar) {
            actionBar.setDisplayHomeAsUpEnabled(enabled);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case android.R.id.home:
                super.onBackPressed();//返回
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void toast(int resId) {
        Toast.makeText(mActivity, resId, Toast.LENGTH_SHORT).show();
    }

    public void toast(CharSequence sequence) {
        Toast.makeText(mActivity, sequence, Toast.LENGTH_SHORT).show();
    }

}
