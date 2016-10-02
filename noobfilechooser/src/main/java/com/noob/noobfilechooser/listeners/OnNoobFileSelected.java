package com.noob.noobfilechooser.listeners;

import com.noob.noobfilechooser.models.NoobFile;

import java.util.List;

/**
 * Created by abhi on 28/09/16.
 */

public interface OnNoobFileSelected {
    void onSingleFileSelection(NoobFile file);
    void onMultipleFilesSelection(List<NoobFile> files);
}
