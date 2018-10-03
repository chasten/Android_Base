package com.android.base.androidbaseproject.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.widget.ProgressBar;

import com.android.base.androidbaseproject.R;

public class LoadingDialog extends Dialog {
    public LoadingDialog(@NonNull Context context) {
        this(context, R.style.dialog);
    }

    public LoadingDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_loading_dialog);
        setCancelable(false);
        setCanceledOnTouchOutside(false);

        ProgressBar progressBar = findViewById(R.id.progress_loading);
        ProgressDrawable drawable = new ProgressDrawable();
        drawable.setColor(Color.WHITE);
        progressBar.setIndeterminateDrawable(drawable);
    }
}
