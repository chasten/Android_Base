package com.android.base.androidbaseproject.mvpview;

/**
 * mvp base view
 * Created by secretqi on 2018/2/28.
 */

public interface MvpView {

//    void showLoading();
//
//    void hideLoading();
//
    /**
     * show message
     * @param msg message
     */
    void showMgs(String msg);

    /**
     * show message
     * @param resId message
     */
    void showMgs(int resId);

}