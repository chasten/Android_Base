package com.android.base.androidbaseproject;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;

import com.android.base.androidbaseproject.presenter.MvpPresenterIml;
import com.android.base.androidbaseproject.utils.LanguageUtil;
import com.android.base.androidbaseproject.utils.MyContextWrapper;

import java.util.Locale;

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

        if(Build.VERSION.SDK_INT>=21){
            if (null != getSupportActionBar()) {
                getSupportActionBar().setElevation(0);
            }
        }
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
    protected void attachBaseContext(Context newBase) {
        Locale languageType = LanguageUtil.getLanguageType(newBase);
        super.attachBaseContext(MyContextWrapper.wrap(newBase, languageType));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != this.mvpPresenter) {
            this.mvpPresenter.detachView();
        }
    }

}
