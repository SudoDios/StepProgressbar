package me.sudodios.stepprogressbar

import android.content.Context
import android.content.res.Resources
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import kotlin.math.min

class StepProgressBar(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var paintBack = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        color = Color.GRAY
        strokeWidth = 20f
    }

    private var paintProgress = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        strokeJoin = Paint.Join.ROUND
        color = Color.RED
        strokeWidth = 20f
    }

    var space = 8
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }
    var steps = 2
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }
    var roundCorners : Boolean = true
        set(value) {
            field = value
            paintBack.strokeCap = if (field) Paint.Cap.ROUND else Paint.Cap.SQUARE
            paintProgress.strokeCap = if (field) Paint.Cap.ROUND else Paint.Cap.SQUARE
        }
    var progressColor = Color.RED
        set(value) {
            field = value
            paintProgress.color = value
            requestLayout()
            invalidate()
        }
    var progressBackgroundColor = Color.GRAY
        set(value) {
            field = value
            paintBack.color = value
            requestLayout()
            invalidate()
        }
    var progressWidth: Float = 20f
        set(value) {
            field = value.dpToPx()
            paintProgress.strokeWidth = field
            requestLayout()
            invalidate()
        }
    var progressBackgroundWidth: Float = 20f
        set(value) {
            field = value.dpToPx()
            paintBack.strokeWidth = field
            requestLayout()
            invalidate()
        }

    var progress: Pair<Int,Float> = Pair(1,70f)
        set(value) {
            if (value.first < 0 || value.first > steps) {
                Log.e("StepProgressBar","not found step ${value.first}")
            } else if (value.second < 0f || value.second > 100f) {
                Log.e("StepProgressBar","progress is not until 0..100")
            } else {
                field = value
                if (this::progressListener.isInitialized) {
                    progressListener.invoke(value.first,value.second)
                }
                requestLayout()
                invalidate()
            }
        }

    lateinit var progressListener : (step : Int,progress : Float) -> Unit

    init {
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.StepProgressBar, 0, 0)

        space = attributes.getInt(R.styleable.StepProgressBar_sp_space,8)
        steps = attributes.getInt(R.styleable.StepProgressBar_sp_steps,2)
        roundCorners = attributes.getBoolean(R.styleable.StepProgressBar_sp_roundCorners,true)

        progressColor = attributes.getColor(R.styleable.StepProgressBar_sp_progressColor,progressColor)
        progressBackgroundColor = attributes.getColor(R.styleable.StepProgressBar_sp_progressBackgroundColor,progressBackgroundColor)

        progressWidth = attributes.getDimension(R.styleable.StepProgressBar_sp_progressWidth,progressWidth).pxToDp()
        progressBackgroundWidth = attributes.getDimension(R.styleable.StepProgressBar_sp_progressBackgroundWidth,progressBackgroundWidth).pxToDp()

        progress = Pair(attributes.getInt(R.styleable.StepProgressBar_sp_currentStep,0),attributes.getFloat(R.styleable.StepProgressBar_sp_progress,0f))
        attributes.recycle()
    }


    private var rectF = RectF()
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val height = getDefaultSize(suggestedMinimumHeight, heightMeasureSpec)
        val width = getDefaultSize(suggestedMinimumWidth, widthMeasureSpec)
        val min = min(width, height)
        setMeasuredDimension(min, min)
        val highStroke = if (progressWidth > progressBackgroundWidth) progressWidth else progressBackgroundWidth
        rectF.set(0 + highStroke / 2, 0 + highStroke / 2, min - highStroke / 2, min - highStroke / 2)
    }

    override fun onDraw(canvas: Canvas) {
        //calculate total spaces between lines & line size
        val totalSpace = steps * space
        val lineSizes = (360f - totalSpace) / steps
        for (i in 0 until steps) {
            //calculate raw space
            val segments = (space * i) + (lineSizes * i)
            //move angle to degree 90 and handle center space
            val finalStartAngle = segments - (90f - space / 2f)
            canvas.drawArc(rectF,finalStartAngle,lineSizes,false,paintBack)
            //progress
            if (i < progress.first - 1) {
                //draw completed progresses
                canvas.drawArc(rectF,finalStartAngle,lineSizes,false,paintProgress)
            } else if (i == progress.first - 1) {
                //draw in progresses
                val percentage = lineSizes * (progress.second / 100f)
                canvas.drawArc(rectF,finalStartAngle, (if (percentage == 0f) 0.1f else percentage),false,paintProgress)
            }
        }
    }

    private fun Float.dpToPx(): Float =
        this * Resources.getSystem().displayMetrics.density

    private fun Float.pxToDp(): Float =
        this / Resources.getSystem().displayMetrics.density
}