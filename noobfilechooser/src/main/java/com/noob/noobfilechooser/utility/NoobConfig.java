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
    private int fileGridLayoutResource;

    public NoobConfig() {
        folderDrawableResource = R.drawable.ic_folder;
        fileDrawableResource = R.drawable.ic_file;
        imageFileDrawableResource = R.drawable.ic_image_file;
        audioFileDrawableResource = R.drawable.ic_audio_file;
        videoFileDrawableResource = R.drawable.ic_video_file;
        fileGridLayoutItemResource = R.layout.item_noob_file_item;
        fileGridLayoutResource = R.layout.fragment_noob_file;
    }

    //region Accessors

    public int getFolderDrawableResource() {
        return folderDrawableResource;
    }

    public void setFolderDrawableResource(int folderDrawableResourceParam) {
        folderDrawableResource = folderDrawableResourceParam;
    }

    public int getFileDrawableResource() {
        return fileDrawableResource;
    }

    public void setFileDrawableResource(int fileDrawableResourceParam) {
        fileDrawableResource = fileDrawableResourceParam;
    }

    public int getImageFileDrawableResource() {
        return imageFileDrawableResource;
    }

    public void setImageFileDrawableResource(int imageFileDrawableResourceParam) {
        imageFileDrawableResource = imageFileDrawableResourceParam;
    }

    public int getAudioFileDrawableResource() {
        return audioFileDrawableResource;
    }

    public void setAudioFileDrawableResource(int audioFileDrawableResourceParam) {
        audioFileDrawableResource = audioFileDrawableResourceParam;
    }

    public int getVideoFileDrawableResource() {
        return videoFileDrawableResource;
    }

    public void setVideoFileDrawableResource(int videoFileDrawableResourceParam) {
        videoFileDrawableResource = videoFileDrawableResourceParam;
    }

    public int getFileGridLayoutItemResource() {
        return fileGridLayoutItemResource;
    }

    public void setFileGridLayoutItemResource(int fileGridLayoutItemResourceParam) {
        fileGridLayoutItemResource = fileGridLayoutItemResourceParam;
    }

    public int getFileGridLayoutResource() {
        return fileGridLayoutResource;
    }

    public void setFileGridLayoutResource(int fileGridLayoutResourceParam) {
        fileGridLayoutResource = fileGridLayoutResourceParam;
    }

    //endregion
}
