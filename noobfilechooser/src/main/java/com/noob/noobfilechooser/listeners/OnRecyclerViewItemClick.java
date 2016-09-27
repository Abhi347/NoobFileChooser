package com.noob.noobfilechooser.listeners;

import android.view.View;

/**
 * Created by abhi on 28/09/16.
 */

public interface OnRecyclerViewItemClick<T> {
    void onClick(T model, View view);
    void onLongClick(T model, View view);
}
