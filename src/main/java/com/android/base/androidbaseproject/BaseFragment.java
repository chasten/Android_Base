package com.android.base.androidbaseproject;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import butterknife.ButterKnife;

public abstract class BaseFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view;
        if (getContentResId() > 0) {
            view = inflater.inflate(getContentResId(), container, false);
        } else {
            view = super.onCreateView(inflater, container, savedInstanceState);
        }

        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initView(getView());
    }

    protected abstract int getContentResId();

    protected abstract void initView(View view);

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public void toast(int resId) {
        Toast.makeText(getActivity(), resId, Toast.LENGTH_SHORT).show();
    }

    public void toast(CharSequence msg) {
        Toast.makeText(getActivity(), msg + "", Toast.LENGTH_SHORT).show();
    }

}
