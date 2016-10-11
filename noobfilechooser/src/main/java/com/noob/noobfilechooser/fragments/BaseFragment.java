package com.noob.noobfilechooser.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Created by abhi on 06/10/16.
 */

public abstract class BaseFragment extends Fragment {
    private View mRootView;

    //public BaseFragment(){}
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mRootView = inflater.inflate(getLayout(), container, false);
        ButterKnife.bind(this, mRootView);
        onSetupView(mRootView);
        return mRootView;
    }

    public View getRootView() {
        return mRootView;
    }

    protected abstract void onSetupView(View rootView);
    protected abstract int getLayout();
}
