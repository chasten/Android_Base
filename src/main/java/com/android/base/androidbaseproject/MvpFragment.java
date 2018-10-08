package com.android.base.androidbaseproject;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.view.View;

import com.android.base.androidbaseproject.presenter.MvpPresenterIml;
import com.android.base.androidbaseproject.widget.LoadingDialog;

public abstract class MvpFragment<P extends MvpPresenterIml> extends BaseFragment {
    protected P mvpPresenter;

    private LoadingDialog mLoadingDialog;
    private String mColor;

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mvpPresenter = createPresenter();

        if (Build.VERSION.SDK_INT >= 21) {
            int color = this.getColorBackground();
            if (color == Color.WHITE) {
                this.mColor = "#000000";
            }
        }
    }

    protected abstract P createPresenter();

    /**
     * Show loading
     */
    public void showLoading() {
        if (null == this.mLoadingDialog) {
            if (null == this.mColor) {
                this.mLoadingDialog = new LoadingDialog(getActivity());
            } else {
                this.mLoadingDialog = new LoadingDialog(getActivity(), this.mColor);
            }
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private @ColorInt
    int getColorBackground() {
        int[] attrs = new int[] {android.R.attr.colorPrimary};
        TypedArray typedArray = getActivity().obtainStyledAttributes(attrs);
        int color = typedArray.getColor(0, Color.WHITE);
        typedArray.recycle();
        return color;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mvpPresenter != null) {
            mvpPresenter.detachView();
        }
    }
}
