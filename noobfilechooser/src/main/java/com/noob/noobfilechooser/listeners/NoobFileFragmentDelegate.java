package com.noob.noobfilechooser.listeners;

import com.noob.noobfilechooser.models.NoobFile;

/**
 * Created by abhi on 12/10/16.
 */

public interface NoobFileFragmentDelegate {
    void onLoadFolder(NoobFile newFile);
    void onSelectionModeChanged(boolean isMultiSelectOn);
    void onFileFragmentViewLoaded();
}
