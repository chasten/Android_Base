package com.android.base.androidbaseproject;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;

import com.android.base.androidbaseproject.presenter.MvpPresenterIml;
import com.android.base.androidbaseproject.utils.LanguageUtil;
import com.android.base.androidbaseproject.utils.MyContextWrapper;
import com.android.base.androidbaseproject.widget.LoadingDialog;

import java.util.Locale;

/**
 *
 * @param <P>
 */
public abstract class MvpActivity<P extends MvpPresenterIml> extends BaseActivity {
    protected P mvpPresenter;

    private LoadingDialog mLoadingDialog;
    private String mColor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.mvpPresenter = createPresenter();
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT >= 21){
            if (null != getSupportActionBar()) {
                getSupportActionBar().setElevation(0);
            }

            int color = this.getColorBackground();
            if (color == Color.WHITE) {
                this.mColor = "#000000";
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
        if (null == this.mLoadingDialog) {
            if (null == this.mColor) {
                this.mLoadingDialog = new LoadingDialog(this);
            } else {
                this.mLoadingDialog = new LoadingDialog(this, this.mColor);
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
    private @ColorInt int getColorBackground() {
        int[] attrs = new int[] {android.R.attr.colorPrimary};
        TypedArray typedArray = obtainStyledAttributes(attrs);
        int color = typedArray.getColor(0, Color.WHITE);
        typedArray.recycle();
        return color;
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
