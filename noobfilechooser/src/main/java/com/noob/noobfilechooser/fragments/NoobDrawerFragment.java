package com.noob.noobfilechooser.fragments;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.noob.noobfilechooser.R;
import com.noob.noobfilechooser.R2;
import com.noob.noobfilechooser.adapters.NoobDrawerAdapter;
import com.noob.noobfilechooser.listeners.OnRecyclerViewItemClick;
import com.noob.noobfilechooser.managers.NoobManager;
import com.noob.noobfilechooser.managers.NoobPrefsManager;
import com.noob.noobfilechooser.models.NoobStorage;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * A simple {@link Fragment} subclass.
 */
public class NoobDrawerFragment extends BaseFragment {

    @BindView(R2.id.noob_drawer_recycler_view)
    RecyclerView mDrawerRecyclerView;
    private NoobDrawerAdapter mNoobDrawerAdapter;

    private OnRecyclerViewItemClick<NoobStorage> mStorageItemClickListener;

    public NoobDrawerFragment() {
        // Required empty public constructor
    }

    @Override
    protected void onSetupView(View rootView) {
        initializeRecyclerView();
    }

    void initializeRecyclerView() {
        LinearLayoutManager _layoutManager = new LinearLayoutManager(getActivity());
        mDrawerRecyclerView.setLayoutManager(_layoutManager);

        // specify an adapter (see also next example)
        mNoobDrawerAdapter = new NoobDrawerAdapter(NoobManager.getInstance().getConfig().getStorageListLayoutItemResource());
        mDrawerRecyclerView.setAdapter(mNoobDrawerAdapter);

        if (mStorageItemClickListener != null)
            mNoobDrawerAdapter.setListener(mStorageItemClickListener);
        //loadStorage();
    }

    public void loadStorage() {
        if (mNoobDrawerAdapter != null) {
            ArrayList<NoobStorage> storageList = NoobPrefsManager.getInstance().getNoobStorageList();
            mNoobDrawerAdapter.setItems(storageList);
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_noob_drawer;
    }

    public static NoobDrawerFragment newInstance() {
        return new NoobDrawerFragment();
    }

    public void setStorageItemClickListener(OnRecyclerViewItemClick<NoobStorage> storageItemClickListenerParam) {
        mStorageItemClickListener = storageItemClickListenerParam;
    }
}
