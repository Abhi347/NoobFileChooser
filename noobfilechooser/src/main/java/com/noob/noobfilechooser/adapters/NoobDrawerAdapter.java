package com.noob.noobfilechooser.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noob.noobfilechooser.listeners.OnRecyclerViewItemClick;
import com.noob.noobfilechooser.models.NoobStorage;
import com.noob.noobfilechooser.viewmodels.NoobDrawerViewHolder;

import java.util.List;

/**
 * Created by abhi on 11/10/16.
 */

public class NoobDrawerAdapter extends RecyclerView.Adapter<NoobDrawerViewHolder> {
    private int mLayoutId;
    private List<NoobStorage> mStorageList;
    private OnRecyclerViewItemClick<NoobStorage> mListener;

    public NoobDrawerAdapter(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    public NoobDrawerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutId, parent, false);
        NoobDrawerViewHolder _viewHolder = new NoobDrawerViewHolder(_view);
        _viewHolder.setListener(mListener);
        return _viewHolder;
    }

    @Override
    public void onBindViewHolder(NoobDrawerViewHolder holder, int position) {
        holder.setStorage(mStorageList.get(position));
    }

    public void setItems(List<NoobStorage> storageList) {
        if (mStorageList == null) {
            mStorageList = storageList;
        } else {
            mStorageList.clear();
            mStorageList.addAll(storageList);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mStorageList == null)
            return 0;
        return mStorageList.size();
    }

    public void setListener(OnRecyclerViewItemClick<NoobStorage> listenerParam) {
        mListener = listenerParam;
    }
}
