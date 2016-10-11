package com.noob.noobfilechooser.utility;

import com.noob.noobfilechooser.R;

/**
 * Created by abhi on 03/10/16.
 */

public class NoobConfig {
    private int folderDrawableResource;
    private int fileDrawableResource;
    private int imageFileDrawableResource;
    private int audioFileDrawableResource;
    private int videoFileDrawableResource;
    private int fileGridLayoutItemResource;
    private int storageListLayoutItemResource;
    private int fileGridLayoutResource;
    private boolean shouldShowStorageName;
    private boolean forceSDCardAddition;

    public NoobConfig() {
        folderDrawableResource = R.drawable.ic_folder;
        fileDrawableResource = R.drawable.ic_file;
        imageFileDrawableResource = R.drawable.ic_image_file;
        audioFileDrawableResource = R.drawable.ic_audio_file;
        videoFileDrawableResource = R.drawable.ic_video_file;
        fileGridLayoutItemResource = R.layout.item_noob_file_item;
        fileGridLayoutResource = R.layout.fragment_noob_file;
        storageListLayoutItemResource = R.layout.item_noob_storage_list;
        shouldShowStorageName = false;
        forceSDCardAddition = false;
    }

    //region Accessors

    public int getFolderDrawableResource() {
        return folderDrawableResource;
    }

    public NoobConfig setFolderDrawableResource(int folderDrawableResourceParam) {
        folderDrawableResource = folderDrawableResourceParam;
        return this;
    }

    public int getFileDrawableResource() {
        return fileDrawableResource;
    }

    public NoobConfig setFileDrawableResource(int fileDrawableResourceParam) {
        fileDrawableResource = fileDrawableResourceParam;
        return this;
    }

    public int getImageFileDrawableResource() {
        return imageFileDrawableResource;
    }

    public NoobConfig setImageFileDrawableResource(int imageFileDrawableResourceParam) {
        imageFileDrawableResource = imageFileDrawableResourceParam;
        return this;
    }

    public int getAudioFileDrawableResource() {
        return audioFileDrawableResource;
    }

    public NoobConfig setAudioFileDrawableResource(int audioFileDrawableResourceParam) {
        audioFileDrawableResource = audioFileDrawableResourceParam;
        return this;
    }

    public int getVideoFileDrawableResource() {
        return videoFileDrawableResource;
    }

    public NoobConfig setVideoFileDrawableResource(int videoFileDrawableResourceParam) {
        videoFileDrawableResource = videoFileDrawableResourceParam;
        return this;
    }

    public int getFileGridLayoutItemResource() {
        return fileGridLayoutItemResource;
    }

    public NoobConfig setFileGridLayoutItemResource(int fileGridLayoutItemResourceParam) {
        fileGridLayoutItemResource = fileGridLayoutItemResourceParam;
        return this;
    }

    public int getFileGridLayoutResource() {
        return fileGridLayoutResource;
    }

    public NoobConfig setFileGridLayoutResource(int fileGridLayoutResourceParam) {
        fileGridLayoutResource = fileGridLayoutResourceParam;
        return this;
    }

    public int getStorageListLayoutItemResource() {
        return storageListLayoutItemResource;
    }

    public NoobConfig setStorageListLayoutItemResource(int storageListLayoutItemResourceParam) {
        storageListLayoutItemResource = storageListLayoutItemResourceParam;
        return this;
    }

    public boolean isShouldShowStorageName() {
        return shouldShowStorageName;
    }

    public void setShouldShowStorageName(boolean shouldShowStorageNameParam) {
        shouldShowStorageName = shouldShowStorageNameParam;
    }

    public boolean isForceSDCardAddition() {
        return forceSDCardAddition;
    }

    public void setForceSDCardAddition(boolean forceSDCardAdditionParam) {
        forceSDCardAddition = forceSDCardAdditionParam;
    }
    //endregion
}
