package com.noob.noobfilechooser.fragments;


import android.app.Activity;
import android.graphics.Color;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.noob.noobfilechooser.R;
import com.noob.noobfilechooser.R2;
import com.noob.noobfilechooser.adapters.NoobFileAdapter;
import com.noob.noobfilechooser.listeners.NoobFileFragmentDelegate;
import com.noob.noobfilechooser.listeners.OnRecyclerViewItemClick;
import com.noob.noobfilechooser.managers.NoobManager;
import com.noob.noobfilechooser.managers.NoobSAFManager;
import com.noob.noobfilechooser.models.NoobFile;
import com.noob.noobfilechooser.models.NoobStorage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


/**
 * A simple {@link BaseFragment} subclass.
 * Use the {@link NoobFileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoobFileFragment extends BaseFragment {

    @BindView(R2.id.noob_file_recycler_view)
    RecyclerView mFileRecyclerView;

    private NoobFileAdapter mNoobFileAdapter;

    private boolean mMultiSelectionMode = false;

    private List<NoobFile> mSelectionFiles = new ArrayList<>();
    private List<View> mSelectionViews = new ArrayList<>();
    private NoobFileFragmentDelegate mDelegate;

    public NoobFileFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment NoobFileFragment.
     */
    public static NoobFileFragment newInstance() {
        return new NoobFileFragment();
    }

    @Override
    protected void onSetupView(View rootView) {
        initializeRecyclerView();
        if (mDelegate != null)
            mDelegate.onFileFragmentViewLoaded();
    }

    @Override
    protected int getLayout() {
        return NoobManager.getInstance().getConfig().getFileGridLayoutResource();
    }

    protected int getColumnCount() {
        return 3;
    }

    void initializeRecyclerView() {

        turnOnMultiSelectMode(false);

        mFileRecyclerView.setHasFixedSize(true);
        GridLayoutManager _layoutManager = new GridLayoutManager(getActivity(), getColumnCount());
        mFileRecyclerView.setLayoutManager(_layoutManager);

        // specify an adapter (see also next example)
        mNoobFileAdapter = new NoobFileAdapter(NoobManager.getInstance().getConfig().getFileGridLayoutItemResource());
        mFileRecyclerView.setAdapter(mNoobFileAdapter);

        mNoobFileAdapter.setListener(new OnRecyclerViewItemClick<NoobFile>() {
            @Override
            public void onClick(NoobFile model, View view) {
                if (mMultiSelectionMode) {
                    selectFile(model, view);
                } else {
                    if (model.isDirectory())
                        loadCurrentFile(model);
                    else {
                        if (NoobManager.getInstance().getNoobFileSelectedListener() != null) {
                            NoobManager.getInstance().getNoobFileSelectedListener().onSingleFileSelection(model);
                            getActivity().finish();
                        }
                    }
                }
            }

            @Override
            public void onLongClick(NoobFile model, View view) {
                if (!mMultiSelectionMode) {
                    turnOnMultiSelectMode(true);
                }
                selectFile(model, view);
            }
        });
        /*mSelectionCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewParam) {
                turnOnMultiSelectMode(false);
            }
        });
        mSelectionDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewParam) {
                if (NoobManager.getInstance().getNoobFileSelectedListener() != null) {
                    NoobManager.getInstance().getNoobFileSelectedListener().onMultipleFilesSelection(mSelectionFiles);
                    getActivity().finish();
                }
            }
        });*/
        load(null, false);
    }

    public void load(NoobStorage storage, boolean forceLoad) {
        if (forceLoad || NoobManager.getInstance().getCurrentFile() == null) {
            if (storage != null)
                buildAndLoad(getActivity(), storage);
        } else {
            loadCurrentFile(NoobManager.getInstance().getCurrentFile());
        }
    }

    public void buildAndLoad(Activity activity, NoobStorage storage) {
        try {
            NoobFile _file;
            if (storage.getUri() != null) {
                _file = NoobSAFManager.buildTreeFile(activity, storage.getUri());
            } else {
                File _legacyFile = new File(storage.getAbsolutePath());
                _file = new NoobFile(_legacyFile);
            }
            loadCurrentFile(_file);
        } catch (SecurityException ex) {
            ex.printStackTrace();
            NoobSAFManager.takeCardUriPermission(activity);
        }
    }

    void selectFile(NoobFile file, View view) {
        if (file.isSelected()) {
            file.setSelected(false);
            mSelectionFiles.remove(file);
            mSelectionViews.remove(view);
            view.setBackgroundColor(Color.TRANSPARENT);
        } else {
            file.setSelected(true);
            mSelectionFiles.add(file);
            mSelectionViews.add(view);
            view.setBackgroundResource(R.drawable.bg_selection_border);
        }
    }

    public void turnOnMultiSelectMode(boolean flag) {
        if (flag) {
            mMultiSelectionMode = true;
        } else {
            mMultiSelectionMode = false;
            for (View view : mSelectionViews) {
                view.setBackgroundColor(Color.TRANSPARENT);
            }
            mSelectionFiles.clear();
            mSelectionViews.clear();
        }
        if (mDelegate != null) {
            mDelegate.onSelectionModeChanged(mMultiSelectionMode);
        }
    }

    void loadCurrentFile(NoobFile fileParam) {
        NoobManager.getInstance().setCurrentFile(fileParam);
        /*if (mTitleTextView != null)
            mTitleTextView.setText(fileParam.getName());*/
        if (mDelegate != null && fileParam.isDirectory()) {
            mDelegate.onLoadFolder(fileParam);
        }
        /*if (parentParam.isTreeDoc())
            mNoobFileAdapter.setItems(parentParam, NoobSAFManager.buildChildFiles(getActivity(), parentParam.getUri()));
        else*/
        if (mNoobFileAdapter != null && fileParam.isDirectory()) {
            List<NoobFile> noobChildFiles = fileParam.getChildren();
            mNoobFileAdapter.setItems(fileParam, noobChildFiles);
        }
    }

    public boolean onBackPressed() {
        if (mMultiSelectionMode) {
            turnOnMultiSelectMode(false);
            return true;
        }
        NoobFile _currentFile = NoobManager.getInstance().getCurrentFile();
        if (_currentFile != null) {
            NoobFile _parentFile = _currentFile.getParentNoobFile();
            if (_parentFile != null) {
                loadCurrentFile(_parentFile);
                return true;
            }
        }
        return false;
    }

    public void setDelegate(NoobFileFragmentDelegate delegateParam) {
        mDelegate = delegateParam;
    }

    public List<NoobFile> getSelectionFiles() {
        return mSelectionFiles;
    }
}
