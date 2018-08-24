package com.android.base.androidbaseproject;

import android.os.Bundle;
import android.view.View;

import com.android.base.androidbaseproject.presenter.MvpPresenterIml;

public abstract class MvpFragment<P extends MvpPresenterIml> extends BaseFragment {
    protected P mvpPresenter;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();
    }

    protected abstract P createPresenter();

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
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
