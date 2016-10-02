package com.noob.noobfilechooser.managers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.Uri;

import com.noob.noobfilechooser.listeners.OnNoobFileSelected;

/**
 * Created by abhi on 27/09/16.
 */

public class NoobPrefsManager {
    //region singleton
    private static NoobPrefsManager mInstance;
    public static NoobPrefsManager getInstance() {
        if (mInstance == null)
            mInstance = new NoobPrefsManager();
        return mInstance;
    }

    private NoobPrefsManager() {
    }
    //endregion

    private static final String PREFS_FILE = "PREFS_FILE";
    private static final String KEY_URI = "KEY_URI";

    private Uri mSDCardUri;
    private SharedPreferences mPreferences;
    private OnNoobFileSelected mNoobFileSelectedListener;

    public void init(Activity context){
        mPreferences = context.getSharedPreferences(PREFS_FILE,Context.MODE_PRIVATE);
    }

    void setSDCardUri(Uri cardUriParam){
        mSDCardUri = cardUriParam;
        SharedPreferences.Editor _editor = mPreferences.edit();
        _editor.putString(KEY_URI,cardUriParam.toString());
        _editor.apply();
    }
    public Uri getSDCardUri(){
        if(mSDCardUri!=null){
            return mSDCardUri;
        }
        String cardUriStr = mPreferences.getString(KEY_URI,null);
        if(cardUriStr!=null && !cardUriStr.isEmpty()){
            mSDCardUri = Uri.parse(cardUriStr);
        }
        return mSDCardUri;
    }

    public OnNoobFileSelected getNoobFileSelectedListener() {
        return mNoobFileSelectedListener;
    }

    public void setNoobFileSelectedListener(OnNoobFileSelected noobFileSelectedListenerParam) {
        mNoobFileSelectedListener = noobFileSelectedListenerParam;
    }
}
