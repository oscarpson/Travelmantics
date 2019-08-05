Travelmantics Android Application 
=============================

Travelmantics android application 

## Travelmantics App

First, clone the repo:

`https://github.com/oscarpson/Travelmantics.git`


Building the sample then depends on your build tools.
## Prerequisites
1. Libraries used include:
*  firebase auth ui `implementation 'com.firebaseui:firebase-ui-auth:4.3.1'`
*  firebase UI implementation `'com.firebaseui:firebase-ui-database:5.0.0'`
*  firebase implementation `'com.firebaseui:firebase-ui-auth:4.3.1'`
*  implementation `'com.github.bumptech.glide:glide:4.9.0'`
*  annotationProcessor `'com.github.bumptech.glide:compiler:4.9.0'`


### Android Studio (Recommended)


* Open Android Studio and select `File->Open...` or from the Android Launcher select `Import project ` and navigate to the root directory of your project.
* Select the directory or drill in and select the file `build.gradle` in the cloned repo.
* Click 'OK' to open the the project in Android Studio.
* A Gradle sync should start, but you can force a sync and build the 'app' module as needed.

### Gradle (command line)

* Build the APK: `./gradlew build`

## Running the  App

Connect an Android device to your development machine.

### Android Studio

* Select `Run -> Run 'app'` (or `Debug 'app'`) from the menu bar
* Select the device you wish to run the app on and click 'OK'

### Gradle

* Install the debug APK on your device `./gradlew installDebug`
* 

## Screenshot 
* Authentication Ui ![alt text](/images/img1.PNG)
 * Add email ![alt text](/images/img2.PNG)
 * Enter authentication password ![alt text](/images/img3.PNG)
  * List of deals  ![alt text](/image4/img4.PNG)
 * Admin add new deal ![alt text](/images/img5.PNG)
# Acknowledgment
 © 2019, Travelmantics, maintained by  [Oscar Muigai](https://github.com/oscarpson/Travelmantics.git)