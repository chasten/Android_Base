package com.android.base.androidbaseproject.presenter;


public interface IPresenter<V> {

    void attachView(V view);

    void detachView();

}
