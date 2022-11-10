<img src="https://github.com/SudoDios/StepProgressbar/blob/master/app/src/main/ic_launcher-playstore.png" alt="drawing" width="160"/>

# StepProgressbar
make your step progress bar easily on your project

[![Platform](https://img.shields.io/badge/platform-android-green.svg)](http://developer.android.com/index.html)
[![API](https://img.shields.io/badge/API-16%2B-brightgreen.svg?style=flat)](https://android-arsenal.com/api?level=16)
[![](https://jitpack.io/v/SudoDios/StepProgressbar.svg)](https://jitpack.io/#SudoDios/StepProgressbar)

<img src="https://github.com/SudoDios/StepProgressbar/blob/master/screen_shot.jpg" width="144" height="321"/>

### How to use
Step 1 : Add it in your root build.gradle at the end of repositories:
```gradle
allprojects {
	repositories {
		...
		maven { url 'https://jitpack.io' }
	}
}
```
Step 2 : Add the dependency:
```gradle
dependencies {
	implementation 'com.github.SudoDios:StepProgressbar:1.2.1'
}
```

### XML Layout (Circle Progressbar)
```xml
<me.sudodios.stepprogressbar.StepProgressBar
     android:id="@+id/circleProgress"
     android:layout_width="160dp"
     android:layout_height="160dp"
     app:sp_progressWidth="10dp"
     app:sp_progressBackgroundWidth="10dp"
     app:sp_progressColor="?attr/colorPrimary"
     app:sp_progressStartColor="#ff9800"
     app:sp_progressEndColor="#701DC3"
     app:sp_progressGradientDegree="45"
     app:sp_progressBackgroundColor="?attr/colorControlHighlight"
     app:sp_steps="7"
     app:sp_space="16"
     app:sp_roundCorners="true"
     app:sp_currentStep="5"
     app:sp_progress="60"/>
```
### XML Layout (Line Progressbar)
```xml
<me.sudodios.stepprogressbar.LineStepProgressBar
     android:id="@+id/lineProgress"
     android:layout_width="match_parent"
     app:lsp_progressWidth="10dp"
     android:layout_marginTop="50dp"
     app:lsp_progressBackgroundWidth="10dp"
     app:lsp_progressColor="?attr/colorPrimary"
     app:lsp_progressStartColor="#ff9800"
     app:lsp_progressEndColor="#701DC3"
     app:lsp_progressGradientDegree="45"
     app:lsp_progressBackgroundColor="?attr/colorControlHighlight"
     app:lsp_steps="7"
     app:lsp_space="40dp"
     app:lsp_roundCorners="true"
     app:lsp_currentStep="5"
     app:lsp_progress="60"/>
```
### Kotlin
```kotlin
binding.stepProgressbar.apply {
            
      //count of steps
      steps = 10

      //size of space between steps
      /* in circle set degree size & in line is in dp*/
      space = 120
            
      //round corner of borders
      roundCorners = true
            
      //customize color 
      progressColor = Color.GREEN
      progressBackgroundColor = Color.GRAY
      /*you can add gradient to progress*/
      progressStartColor = Color.RED
      progressEndColor = Color.YELLOW
      /*gradient degree (angle) between 0..360*/
      progressGradientDegree = 180f
      
      //customize size
      progressWidth = 10f
      progressBackgroundWidth = 10f
            
      //set progress with a Pair(step,percentage)
      progress = Pair(5,60f)
            
      //set listener for progressed
      progressListener = { step, progress ->  
                
      }
}
```
#### Note
`RTL support is coming soon`
