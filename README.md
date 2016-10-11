[![](https://jitpack.io/v/Abhi347/NoobFileChooser.svg)](https://jitpack.io/#Abhi347/NoobFileChooser) [![Build Status](https://travis-ci.org/Abhi347/NoobFileChooser.svg?branch=master)](https://travis-ci.org/Abhi347/NoobFileChooser)

# NoobFileChooser - SAF ready file chooser for Android
A complete File manager for Android with complete support of Storage Access Framework. Now you won't need to rely on Android's stock system UI to deliver files from the External SD Card. 

## Features
 * No need to code for extra permissions
 * Simplest possible code to fetch files
 * Custom User Interface

## Planned feature
 * More Customization support
 * Support for internal storage
 * Support for devices lower than Android 5.0

## Setup
NoobFileChooser is hosted at jitpack.io. The instructions are as follows -   
Step 1. Add the JitPack repository to your build file. Add it in your root build.gradle at the end of repositories:

    allprojects {
		    repositories {
    			...
		    	maven { url "https://jitpack.io" }
    		}
    }

Step 2. Add the dependency

    dependencies {
	        compile 'com.github.Abhi347:NoobFileChooser:0.2.0'
	  }

## Usage
You just need to set a listener for selecting single and or multiple files (We need to work on this part a bit) and then call the NoobFileActivity using an intent like any other activity in your app.

    NoobManager.getInstance().setNoobFileSelectedListener(new OnNoobFileSelected() {
        @Override
            public void onSingleFileSelection(NoobFile file) {
                //Use the file Object
            }

            @Override
            public void onMultipleFilesSelection(List<NoobFile> files) {
                //Use the files Object
            }
    });
    Intent _intent = new Intent(this, NoobFileActivity.class);
    startActivity(_intent);
    
The result is provided in the form of NoobFile or a List of NoobFile objects.

## Advanced Usage
If you want to customize the User Interface, you can use `NoobConfig` class. Just put the following lines before setting the `OnNoobFileSelected` Listener.

    NoobConfig noobConfig = new NoobConfig();
    noobConfig.setFolderDrawableResource(R.drawable.ic_folder) //Use your own Folder Drawable
              .setFileDrawableResource(R.drawable.ic_file) //Use your own Vanilla File Drawable
              .setImageFileDrawableResource(R.drawable.ic_image_file) //Use your own Image File Drawable
              .setAudioFileDrawableResource(R.drawable.ic_audio_file) //Use your own Audio File Drawable
              .setVideoFileDrawableResource(R.drawable.ic_video_file) //Use your own Video File Drawable
              .setFileGridLayoutItemResource(R.layout.item_noob_file_item) //Use your own Layout Resource for each File Item. Please check item_noob_file_item.xml for ids
              .setFileGridLayoutResource(R.layout.fragment_noob_file) //Use your own Layout Resource for the complete file fragment. Please check fragment_noob_file.xml for ids.
    NoobManager.getInstance().setConfig(noobConfig);

## Contributions
Feel free to report bugs, feedback or even suggest new features. I'd love to make it a great library.

## Credits
Icons are provided by [Icons8](https://icons8.com/web-app/12245/Image-File). Check with them for license, you want to use the icons in your project too.

## Donate
[Paypal](https://paypal.me/Abhi347/5)

## Warning
NoobFileChooser is not a production ready library (We haven't reached 1.0.0 yet) and thus you should not use it in a production ready code. Please read the license term carefully before including it in your projects.
