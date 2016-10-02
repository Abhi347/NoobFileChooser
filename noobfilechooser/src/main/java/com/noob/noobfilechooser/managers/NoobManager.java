package com.noob.noobfilechooser.managers;

import com.noob.noobfilechooser.listeners.OnNoobFileSelected;
import com.noob.noobfilechooser.utility.NoobConfig;

/**
 * Created by abhi on 03/10/16.
 */

//This file is supposed to be a single point contact for the client
public class NoobManager {
    //region singleton
    private static NoobManager mInstance;

    public static NoobManager getInstance() {
        if (mInstance == null)
            mInstance = new NoobManager();
        return mInstance;
    }

    private NoobManager() {
    }
    //endregion

    private OnNoobFileSelected mNoobFileSelectedListener;
    private NoobConfig mConfig;

    public OnNoobFileSelected getNoobFileSelectedListener() {
        return mNoobFileSelectedListener;
    }

    public void setNoobFileSelectedListener(OnNoobFileSelected noobFileSelectedListenerParam) {
        mNoobFileSelectedListener = noobFileSelectedListenerParam;
    }

    public NoobConfig getConfig() {
        if(mConfig==null){
            mConfig = new NoobConfig();
        }
        return mConfig;
    }

    public void setConfig(NoobConfig configParam) {
        mConfig = configParam;
    }
}
