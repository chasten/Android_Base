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
    }

    protected abstract P createPresenter();

    protected void post(Object o) {
        this.mvpPresenter.post(o);
    }

    /**
     * Show loading
     */
    public void showLoading() {

    }

    /**
     * Hide loading
     */
    public void hideLoading() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != this.mvpPresenter) {
            this.mvpPresenter.detachView();
        }
    }

}
