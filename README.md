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
	        compile 'com.github.Abhi347:NoobFileChooser:0.1.0'
	  }

##Usage
You just need to set a listener for selecting single and or multiple files (We need to work on this part a bit) and then call the NoobFileActivity using an intent like any other activity in your app.

    NoobPrefsManager.getInstance().setNoobFileSelectedListener(new OnNoobFileSelected() {
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
    
The result is provided in the form of NoobFile or a List of NoobFile objects. You can call `file.getDocumentFile()` to get the DocumentFile on which you can call delete or rename or any other operation.

## Contributions
Feel free to report bugs, feedbacks or even suggest new features. I'd love to make it a great library.

## Donate
[Paypal](https://paypal.me/Abhi347/5)

## Warning
NoobFileChooser is not a production ready library and thus you should not use it in a production ready code. Please read the license term carefully before including it in your projects.
