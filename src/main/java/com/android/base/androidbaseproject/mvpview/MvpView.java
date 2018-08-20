package com.android.base.androidbaseproject.mvpview;

/**
 * mvp base view
 * Created by secretqi on 2018/2/28.
 */

public interface MvpView {

    /**
     * Show loading dialog
     */
    void showLoading();

    /**
     * Hide loading dialog
     */
    void hideLoading();

    /**
     * Toast message for short time
     * @param sequence message
     */
    void toast(CharSequence sequence);

    /**
     * Toast message for short time
     * @param resId message id
     */
    void toast(int resId);

}
