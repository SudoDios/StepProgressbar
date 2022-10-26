<img src="https://github.com/SudoDios/StepProgressbar/blob/master/app/src/main/ic_launcher-playstore.png" alt="drawing" width="160"/>

# StepProgressbar
make your step progress bar easily on your project

<img src="https://github.com/SudoDios/StepProgressbar/blob/master/stepProgressbar.gif" width="144" height="321"/>

### XML Layout (Circle Progressbar)
```xml
<me.sudodios.stepprogressbar.StepProgressBar
     android:id="@+id/circleProgress"
     android:layout_width="160dp"
     android:layout_height="160dp"
     app:sp_progressWidth="10dp"
     app:sp_progressBackgroundWidth="10dp"
     app:sp_progressColor="?attr/colorPrimary"
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
