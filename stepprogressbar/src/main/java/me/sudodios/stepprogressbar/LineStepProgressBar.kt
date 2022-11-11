package me.sudodios.stepprogressbar

import android.content.Context
import android.content.res.Resources
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.view.ViewCompat
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

class LineStepProgressBar(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var paintBack = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = Color.GRAY
        strokeWidth = 20f
    }

    private var paintProgress = Paint().apply {
        isAntiAlias = true
        style = Paint.Style.STROKE
        strokeCap = Paint.Cap.ROUND
        color = Color.RED
        strokeWidth = 20f
    }

    var space = 20f.dpToPx()
        set(value) {
            field = value
            requestLayout()
            invalidate()
        }
    var steps = 6
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
    var progressStartColor: Int? = null
        set(value) {
            field = if (value == 0) null else value
            setupProgressColor()
            invalidate()
        }
    var progressEndColor: Int? = null
        set(value) {
            field = if (value == 0) null else value
            setupProgressColor()
            invalidate()
        }
    var progressGradientDegree : Float = 45f
        set(value) {
            if (field < 0f || field > 360f) {
                Log.e("StepProgressBar","gradient degree is not until 0..360")
            } else {
                field = value
                setupProgressColor()
                invalidate()
            }
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

    var progress: Pair<Int,Float> = Pair(1,2f)
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
        val attributes = context.theme.obtainStyledAttributes(attrs, R.styleable.LineStepProgressBar, 0, 0)
        space = attributes.getDimension(R.styleable.LineStepProgressBar_lsp_space,space).pxToDp()
        steps = attributes.getInt(R.styleable.LineStepProgressBar_lsp_steps,2)
        roundCorners = attributes.getBoolean(R.styleable.LineStepProgressBar_lsp_roundCorners,true)

        progressColor = attributes.getColor(R.styleable.LineStepProgressBar_lsp_progressColor,progressColor)
        progressStartColor = attributes.getColor(R.styleable.LineStepProgressBar_lsp_progressStartColor,0)
        progressEndColor = attributes.getColor(R.styleable.LineStepProgressBar_lsp_progressEndColor,0)
        progressGradientDegree = attributes.getFloat(R.styleable.LineStepProgressBar_lsp_progressGradientDegree,45f)
        progressBackgroundColor = attributes.getColor(R.styleable.LineStepProgressBar_lsp_progressBackgroundColor,progressBackgroundColor)

        progressWidth = attributes.getDimension(R.styleable.LineStepProgressBar_lsp_progressWidth,progressWidth).pxToDp()
        progressBackgroundWidth = attributes.getDimension(R.styleable.LineStepProgressBar_lsp_progressBackgroundWidth,progressBackgroundWidth).pxToDp()

        progress = Pair(attributes.getInt(R.styleable.LineStepProgressBar_lsp_currentStep,0),attributes.getFloat(
            R.styleable.StepProgressBar_sp_progress,0f))
        attributes.recycle()
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val maxHeight = max(progressWidth,progressBackgroundWidth)
        setMeasuredDimension(widthMeasureSpec, max(maxHeight.toInt(),heightMeasureSpec))
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setupProgressColor()
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        //calculate total spaces between lines & line size
        val lineSizes = measuredWidth / steps
        for (i in 0 until steps) {
            val lineLocations = getLinesLocation(lineSizes,i)
            canvas.drawLine(lineLocations[0],centerH(), lineLocations[1],centerH(),paintBack)
            //progress
            val counter = if (isRtl()) (steps - i) - 1 else i
            if (counter < progress.first - 1) {
                //draw completed progresses
                canvas.drawLine(lineLocations[0],centerH(), lineLocations[1],centerH(),paintProgress)
            } else if (counter == progress.first - 1) {
                //draw in progresses
                if (progress.second != 0f) {
                    val percentage = (lineLocations[1] - lineLocations[0]) * (progress.second / 100f)
                    canvas.drawLine(lineLocations[0],centerH(), lineLocations[0] + percentage,centerH(),paintProgress)
                }
            }
        }
    }

    private fun getLinesLocation (lineSize : Int,index : Int) : FloatArray {
        val stepSize = lineSize * index
        val startLine = stepSize + (space / 2)
        val endLine = stepSize + (lineSize - (space / 2))
        return if (!isRtl()) floatArrayOf(startLine,endLine) else floatArrayOf(endLine,startLine)
    }

    private fun centerH(): Float = height / 2f

    private fun setupProgressColor () {
        val angleInRadians = Math.toRadians(progressGradientDegree.toDouble())
        val halfWidth = width / 2
        val halfHeight = height / 2
        val sinAngle = sin(angleInRadians)
        val cosAngle = cos(angleInRadians)
        val x0 = (halfWidth * (1 + sinAngle)).toFloat()
        val y0 = (halfHeight * (1 - cosAngle)).toFloat()
        val x1 = (halfWidth * (1 - sinAngle)).toFloat()
        val y1 = (halfHeight * (1 + cosAngle)).toFloat()
        paintProgress.shader = LinearGradient(x0,y0,x1,y1,progressStartColor ?: progressColor,
            progressEndColor ?: progressColor, Shader.TileMode.CLAMP)
    }

    private fun Float.dpToPx(): Float = this * Resources.getSystem().displayMetrics.density

    private fun Float.pxToDp(): Float = this / Resources.getSystem().displayMetrics.density

    private fun isRtl (): Boolean = ViewCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL
}