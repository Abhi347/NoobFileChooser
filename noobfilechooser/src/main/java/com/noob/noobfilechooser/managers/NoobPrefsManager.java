package com.noob.noobfilechooser.managers;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.noob.noobfilechooser.models.NoobStorage;

import java.util.ArrayList;

/**
 * Created by abhi on 27/09/16.
 */

public class NoobPrefsManager {
    private static final String PREFS_FILE = "PREFS_FILE";
    //private static final String KEY_URI = "KEY_URI";
    private static final String KEY_STORAGE = "KEY_STORAGE";

    //private Uri mSDCardUri;
    private SharedPreferences mPreferences;

    private ArrayList<NoobStorage> mNoobStorageList;
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

    public void init(Activity context) {
        mPreferences = context.getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
    }

    public void loadStorage() {
        String storageStr = mPreferences.getString(KEY_STORAGE, null);
        if (storageStr != null) {
            mNoobStorageList = new GsonBuilder().create().fromJson(storageStr, new TypeToken<ArrayList<NoobStorage>>() {
            }.getType());
        }
        if (mNoobStorageList == null) {
            mNoobStorageList = new ArrayList<>();
        }
        if (mNoobStorageList.size() <= 0) {
            loadInternalStorage();
        }
    }

    public void loadInternalStorage() {
        //This method gives Security Exception. So it's better to go with the legacy approach using File
        /*String internalStorageStr = "content://com.android.externalstorage.documents/tree/primary%3A";
        Uri uri = Uri.parse(internalStorageStr);
        NoobSAFManager.addUriToStorage(uri, mContext);*/
        NoobStorage _storage = new NoobStorage(Environment.getExternalStorageDirectory());
        addStorage(_storage);
    }

    public void saveStorage() {
        if (mNoobStorageList != null) {
            String storageStr = new GsonBuilder().create().toJson(mNoobStorageList);
            SharedPreferences.Editor _editor = mPreferences.edit();
            _editor.putString(KEY_STORAGE, storageStr);
            _editor.apply();
        }
    }

    public boolean addStorage(NoobStorage storage) {
        if (mNoobStorageList != null) {
            for (NoobStorage store : mNoobStorageList) {
                if (storage.toString().equalsIgnoreCase(store.toString())) {
                    store.load(storage);
                    saveStorage();
                    return false;
                }
            }
        }
        getNoobStorageList().add(storage);
        saveStorage();
        return true;
    }

    /*void setSDCardUri(Uri cardUriParam) {
        mSDCardUri = cardUriParam;
        SharedPreferences.Editor _editor = mPreferences.edit();
        _editor.putString(KEY_URI, cardUriParam.toString());
        _editor.apply();
    }

    public Uri getSDCardUri() {
        if (mSDCardUri != null) {
            return mSDCardUri;
        }
        String cardUriStr = mPreferences.getString(KEY_URI, null);
        if (cardUriStr != null && !cardUriStr.isEmpty()) {
            mSDCardUri = Uri.parse(cardUriStr);
        }
        return mSDCardUri;
    }*/

    public ArrayList<NoobStorage> getNoobStorageList() {
        if (mNoobStorageList == null) {
            mNoobStorageList = new ArrayList<>();
        }
        return mNoobStorageList;
    }
}
