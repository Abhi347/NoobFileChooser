package com.noob.noobfilechooser;


import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.provider.DocumentFile;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.noob.noobfilechooser.adapters.NoobFileAdapter;
import com.noob.noobfilechooser.listeners.OnRecyclerViewItemClick;
import com.noob.noobfilechooser.managers.NoobFile;
import com.noob.noobfilechooser.managers.NoobPrefsManager;
import com.noob.noobfilechooser.managers.NoobSAFManager;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoobFileFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoobFileFragment extends Fragment {

    RecyclerView mFileRecyclerView;
    private GridLayoutManager mLayoutManager;
    NoobFileAdapter mNoobFileAdapter;
    private TextView mTitleTextView;
    ImageButton mSelectionDoneButton,mSelectionCancelButton;

    private NoobFile mParent;
    boolean mMultiSelectionMode = false;

    List<NoobFile> mSelectionFiles = new ArrayList<>();
    List<View> mSelectionViews = new ArrayList<>();
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
        NoobFileFragment fragment = new NoobFileFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View parent = inflater.inflate(R.layout.fragment_noob_file, container, false);
        mTitleTextView = (TextView) parent.findViewById(R.id.noob_folder_title_text);
        mSelectionDoneButton = (ImageButton) parent.findViewById(R.id.button_selection_done);
        mSelectionCancelButton = (ImageButton) parent.findViewById(R.id.button_selection_cancel);
        mFileRecyclerView = (RecyclerView) parent.findViewById(R.id.noob_file_recycler_view);
        initializeRecyclerView();
        return parent;
    }

    protected int getColumnCount() {
        return 3;
    }

    void initializeRecyclerView() {
        turnOnMultiSelectMode(false);
        mFileRecyclerView.setHasFixedSize(true);
        mLayoutManager = new GridLayoutManager(getActivity(), getColumnCount());
        mFileRecyclerView.setLayoutManager(mLayoutManager);

        // specify an adapter (see also next example)
        mNoobFileAdapter = new NoobFileAdapter(R.layout.item_noob_file_item);
        mFileRecyclerView.setAdapter(mNoobFileAdapter);

        mNoobFileAdapter.setListener(new OnRecyclerViewItemClick<NoobFile>() {
            @Override
            public void onClick(NoobFile model, View view) {
                if(mMultiSelectionMode){
                    selectFile(model,view);
                }else {
                    if (model.isDirectory())
                        loadParent(model);
                    else {
                        if (NoobPrefsManager.getInstance().getNoobFileSelectedListener() != null) {
                            NoobPrefsManager.getInstance().getNoobFileSelectedListener().onSingleFileSelection(model);
                            getActivity().finish();
                        }
                    }
                }
            }

            @Override
            public void onLongClick(NoobFile model, View view) {
                if(!mMultiSelectionMode){
                    turnOnMultiSelectMode(true);
                }
                selectFile(model,view);
            }
        });
        mSelectionCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewParam) {
                turnOnMultiSelectMode(false);
            }
        });
        mSelectionDoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View viewParam) {
                if (NoobPrefsManager.getInstance().getNoobFileSelectedListener() != null) {
                    NoobPrefsManager.getInstance().getNoobFileSelectedListener().onMultipleFilesSelection(mSelectionFiles);
                    getActivity().finish();
                }
            }
        });
        if (mParent == null) {
            NoobFile _file = NoobSAFManager.buildTreeFile(getActivity(), NoobPrefsManager.getInstance().getSDCardUri());
            loadParent(_file);
        }
    }

    void selectFile(NoobFile file, View view){
        if(file.isSelected()){
            file.setSelected(false);
            mSelectionFiles.remove(file);
            mSelectionViews.remove(view);
            view.setBackgroundColor(Color.TRANSPARENT);
        }else{
            file.setSelected(true);
            mSelectionFiles.add(file);
            mSelectionViews.add(view);
            view.setBackgroundResource(R.drawable.bg_selection_border);
        }
    }

    void turnOnMultiSelectMode(boolean flag){
        if(flag){
            mMultiSelectionMode = true;
            mSelectionDoneButton.setVisibility(View.VISIBLE);
            mSelectionCancelButton.setVisibility(View.VISIBLE);
        }else{
            mMultiSelectionMode = false;
            for (View view :mSelectionViews) {
                view.setBackgroundColor(Color.TRANSPARENT);
            }
            mSelectionFiles.clear();
            mSelectionViews.clear();
            mSelectionDoneButton.setVisibility(View.GONE);
            mSelectionCancelButton.setVisibility(View.GONE);
        }
    }

    void loadParent(NoobFile parentParam) {
        mParent = parentParam;
        mTitleTextView.setText(parentParam.getName());
        /*if (parentParam.isTreeDoc())
            mNoobFileAdapter.setItems(parentParam, NoobSAFManager.buildChildFiles(getActivity(), parentParam.getUri()));
        else*/
        if (parentParam.isDirectory()) {
            DocumentFile[] _files = parentParam.getDocumentFile().listFiles();
            List<NoobFile> noobFiles = new ArrayList<>();
            for (DocumentFile docFile : _files) {
                noobFiles.add(new NoobFile(docFile));
            }
            mNoobFileAdapter.setItems(parentParam, noobFiles);
        }
    }

    public boolean onBackPressed() {
        if(mMultiSelectionMode){
            turnOnMultiSelectMode(false);
            return true;
        }
        if (mParent != null && !mParent.isTreeDoc() && mParent.getDocumentFile().getParentFile()!=null) {
            loadParent(new NoobFile(mParent.getDocumentFile().getParentFile()));
            return true;
        }
        return false;
    }



    /*List<NoobFile> createDummy(int count){
        List<NoobFile> items = new ArrayList<>();
        for (int i = 0; i < count; i++) {
            NoobFile item = new NoobFile();
            item.setName("Test "+i);
            items.add(item);
        }
        return items;
    }*/

}
