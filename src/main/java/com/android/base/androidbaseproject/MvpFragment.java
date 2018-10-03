package com.android.base.androidbaseproject;

import android.os.Bundle;
import android.view.View;

import com.android.base.androidbaseproject.presenter.MvpPresenterIml;
import com.android.base.androidbaseproject.widget.LoadingDialog;

public abstract class MvpFragment<P extends MvpPresenterIml> extends BaseFragment {
    protected P mvpPresenter;

    private LoadingDialog mLoadingDialog;

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
        if (null == this.mLoadingDialog) {
            this.mLoadingDialog = new LoadingDialog(getActivity());
        }
        if (!this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.show();
        }
    }

    /**
     * Hide loading
     */
    public void hideLoading() {
        if (null != this.mLoadingDialog && this.mLoadingDialog.isShowing()) {
            this.mLoadingDialog.dismiss();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
