package com.noob.noobfilechooser.managers;

import com.noob.noobfilechooser.listeners.OnNoobFileSelected;
import com.noob.noobfilechooser.models.NoobFile;
import com.noob.noobfilechooser.utility.NoobConfig;

/**
 * Created by abhi on 03/10/16.
 */

//This file is supposed to be a single point contact for the client
public class NoobManager {
    private OnNoobFileSelected mNoobFileSelectedListener;
    private NoobConfig mConfig;
    private NoobFile mCurrentFile;
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

    public OnNoobFileSelected getNoobFileSelectedListener() {
        return mNoobFileSelectedListener;
    }

    public void setNoobFileSelectedListener(OnNoobFileSelected noobFileSelectedListenerParam) {
        mNoobFileSelectedListener = noobFileSelectedListenerParam;
    }

    //region Accessors
    public NoobConfig getConfig() {
        if(mConfig==null){
            mConfig = new NoobConfig();
        }
        return mConfig;
    }

    public void setConfig(NoobConfig configParam) {
        mConfig = configParam;
    }

    public NoobFile getCurrentFile() {
        return mCurrentFile;
    }

    public void setCurrentFile(NoobFile currentFileParam) {
        mCurrentFile = currentFileParam;
    }
    //endregion
}
