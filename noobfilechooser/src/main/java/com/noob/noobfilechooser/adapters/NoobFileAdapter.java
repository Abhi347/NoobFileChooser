package com.noob.noobfilechooser.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.noob.noobfilechooser.viewmodels.NoobFileViewHolder;
import com.noob.noobfilechooser.listeners.OnRecyclerViewItemClick;
import com.noob.noobfilechooser.models.NoobFile;

import java.util.List;

/**
 * Created by abhi on 23/09/16.
 */

public class NoobFileAdapter extends RecyclerView.Adapter<NoobFileViewHolder> {
    private int mLayoutId;
    private NoobFile mParent;
    private List<NoobFile> mNoobFiles;
    OnRecyclerViewItemClick<NoobFile> mListener;

    public NoobFileAdapter(int layoutId) {
        mLayoutId = layoutId;
    }

    @Override
    public NoobFileViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View _view = LayoutInflater.from(parent.getContext())
                .inflate(mLayoutId, parent, false);
        NoobFileViewHolder _viewHolder = new NoobFileViewHolder(_view);
        _viewHolder.setListener(mListener);
        return _viewHolder;
    }

    @Override
    public void onBindViewHolder(NoobFileViewHolder holder, int position) {
        holder.setItem(mNoobFiles.get(position));
    }

    public void setItems(NoobFile parent, List<NoobFile> noobFileListParam) {
        mParent = parent;
        if(mNoobFiles==null){
            mNoobFiles = noobFileListParam;
        }else {
            mNoobFiles.clear();
            mNoobFiles.addAll(noobFileListParam);
        }
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mNoobFiles == null)
            return 0;
        return mNoobFiles.size();
    }

    public void setListener(OnRecyclerViewItemClick<NoobFile> listenerParam) {
        mListener = listenerParam;
    }
}
