package me.sudodios.stepprogressapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.WindowCompat
import me.sudodios.stepprogressapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        WindowCompat.setDecorFitsSystemWindows(window, false)


        binding.colorProgress.onColorChanged = {
            binding.lineProgress.progressColor = it
            binding.circleProgress.progressColor = it
        }

        binding.colorBackground.onColorChanged = {
            binding.lineProgress.progressBackgroundColor = it
            binding.circleProgress.progressBackgroundColor = it
        }

        binding.progressWidth.addOnChangeListener { _, value, _ ->
            binding.lineProgress.progressWidth = value
            binding.circleProgress.progressWidth = value
        }

        binding.progressBackWidth.addOnChangeListener { _, value, _ ->
            binding.lineProgress.progressBackgroundWidth = value
            binding.circleProgress.progressBackgroundWidth = value
        }

        binding.steps.addOnChangeListener { _, value, _ ->
            binding.lineProgress.steps = value.toInt()
            binding.circleProgress.steps = value.toInt()
            if (binding.currentStep.valueTo > value) {
                binding.currentStep.value = value
            }
            binding.currentStep.valueTo = value
        }

        binding.spaces.addOnChangeListener { _, value, _ ->
            binding.lineProgress.space = value * 2.5f
            binding.circleProgress.space = value.toInt()
        }

        binding.currentStep.addOnChangeListener { _, value, _ ->
            binding.lineProgress.progress = Pair(value.toInt(),binding.lineProgress.progress.second)
            binding.circleProgress.progress = Pair(value.toInt(),binding.circleProgress.progress.second)
        }

        binding.progress.addOnChangeListener { _, value, _ ->
            binding.lineProgress.progress = Pair(binding.lineProgress.progress.first,value)
            binding.circleProgress.progress = Pair(binding.circleProgress.progress.first,value)
        }

    }

}