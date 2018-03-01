package com.android.base.androidbaseproject;

import android.os.Bundle;

import com.android.base.androidbaseproject.presenter.MvpPresenterIml;

/**
 *
 * @param <P>
 */
public abstract class MvpActivity<P extends MvpPresenterIml> extends BaseActivity {
    protected P mvpPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);
        this.initView();
    }

    protected abstract P createPresenter();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (this.mvpPresenter != null) {
            this.mvpPresenter.detachView();
        }
    }

    public abstract void initView();

}
