package com.noob.noobfilechooser.viewmodels;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.noob.noobfilechooser.R;
import com.noob.noobfilechooser.listeners.OnRecyclerViewItemClick;
import com.noob.noobfilechooser.models.NoobStorage;

/**
 * Created by abhi on 11/10/16.
 */

public class NoobDrawerViewHolder extends RecyclerView.ViewHolder {
    private TextView mTitleText;
    private ImageView mStorageImageView;
    private NoobStorage mStorage;
    private View mParent;
    private OnRecyclerViewItemClick<NoobStorage> mListener;

    public NoobDrawerViewHolder(View itemView) {
        super(itemView);
        mParent = itemView;
        mTitleText = (TextView) itemView.findViewById(R.id.item_name);
        mStorageImageView = (ImageView) itemView.findViewById(R.id.item_image);
    }

    public void setListener(OnRecyclerViewItemClick<NoobStorage> listenerParam) {
        mListener = listenerParam;
        mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewParam) {
                if (mListener != null) {
                    mListener.onClick(mStorage, mParent);
                }
            }
        });
        mParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View viewParam) {
                if (mListener != null) {
                    mListener.onLongClick(mStorage, mParent);
                    return true;
                }
                return false;
            }
        });
    }

    public TextView getTitleText() {
        return mTitleText;
    }

    public ImageView getStorageImageView() {
        return mStorageImageView;
    }

    public void setStorage(NoobStorage itemParam) {
        mStorage = itemParam;
        mTitleText.setText(mStorage.getTitle());
        //mStorage.loadImage(getFileImageView());
    }

    public NoobStorage getStorage() {
        return mStorage;
    }
}