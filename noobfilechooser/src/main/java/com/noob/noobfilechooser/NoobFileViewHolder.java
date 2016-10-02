package com.noob.noobfilechooser;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.noob.noobfilechooser.listeners.OnRecyclerViewItemClick;
import com.noob.noobfilechooser.managers.NoobFile;

/**
 * Created by abhi on 23/09/16.
 */

public class NoobFileViewHolder extends RecyclerView.ViewHolder {
    private TextView mTitleText;
    private ImageView mFileImageView;
    private NoobFile mItem;
    private View mParent;
    OnRecyclerViewItemClick<NoobFile> mListener;

    public NoobFileViewHolder(View itemView) {
        super(itemView);
        mParent = itemView;
        mTitleText = (TextView) itemView.findViewById(R.id.item_name);
        mFileImageView = (ImageView) itemView.findViewById(R.id.item_image);
    }

    public void setListener(OnRecyclerViewItemClick<NoobFile> listenerParam) {
        mListener = listenerParam;
        mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewParam) {
                if (mListener != null) {
                    mListener.onClick(mItem, mParent);
                }
            }
        });
        mParent.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View viewParam) {
                if (mListener != null) {
                    mListener.onLongClick(mItem, mParent);
                    return true;
                }
                return false;
            }
        });
    }

    public TextView getTitleText() {
        return mTitleText;
    }

    public ImageView getFileImageView() {
        return mFileImageView;
    }

    public void setItem(NoobFile itemParam) {
        mItem = itemParam;
        mTitleText.setText(mItem.getName());
        if (mItem.getBitmap() != null) {
            getFileImageView().setImageBitmap(mItem.getBitmap());
        } else {
            getFileImageView().setImageResource(R.drawable.ic_file);
        }
    }

    public NoobFile getItem() {
        return mItem;
    }
}
