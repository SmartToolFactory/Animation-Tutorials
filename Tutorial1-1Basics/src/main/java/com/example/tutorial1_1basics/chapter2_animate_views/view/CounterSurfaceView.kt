package com.example.tutorial1_1basics.chapter2_animate_views.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.SurfaceHolder

class CounterSurfaceView : CoroutineSurfaceView {

    /*
        Animation variables
     */
    /**
     * Bottom position of current value text
     */
    private var currentTextY = 0f

    /**
     * Bottom position of next value text
     */
    private var nextTextY = 0f

    /**
     * Position of current text on screen
     */
    private var positionInitialCurrent = 0f

    /**
     * Position of next counter text
     */
    private var positionInitialNext = 0f

    private var isAnimating = false

    var animationSpeed = 0f

    /*
        Drawing
     */
    private var centerX = 0f
    private var centerY = 0f

    /**
     * Scaling coefficient to match different resolutions
     */
    private var pixelScale = 1f

    /**
     * Rectangle that covers text bounds
     */
    private val rectTextBounds = Rect()

    private var textHeight = 0f

    private var isUpCounter = true

    var startValue = 0
        set(value) {
            isUpCounter = (endValue > value)
            field = value
        }

    var endValue = 20
        set(value) {
            isUpCounter = (startValue < value)
            field = value
        }

    private var counterCurrent = 0
    private var counterNext = 1

    private val paintText = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 4f
        textSize = 12f
        color = Color.WHITE
        isAntiAlias = true
    }

    /*
        Debugging
     */

    var debugMode = false

    private val paintDebug = Paint().apply {
        style = Paint.Style.STROKE
        strokeWidth = 2f
        textSize = 12f
        color = Color.GREEN
    }

    private val paintDebugText = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 2f
        textSize = 36f
        color = Color.GREEN
//        textAlign = Paint.Align.CENTER
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        super.surfaceChanged(holder, format, width, height)

        centerX = width / 2f
        centerY = height / 2f

        textHeight = height / 3f
        getDynamicTextSize(textHeight)

        textHeight = rectTextBounds.height().toFloat()

        // This is the vertical of space between 2 texts
        val verticalMargin = (height - textHeight) / 2

        // Text uses top position of rectangle
        val centerPosition = centerY + textHeight / 2
        val bottomPosition = height + textHeight
        val topPosition = 0f

        positionInitialCurrent = centerPosition
        positionInitialNext = if (isUpCounter) {
            bottomPosition
        } else {
            topPosition
        }

        currentTextY = positionInitialCurrent
        nextTextY = positionInitialNext

        animationSpeed = height / 12f

        debugMode = true
    }

    private fun getDynamicTextSize(textHeight: Float) {
        val sampleText = "H"
        while (rectTextBounds.height() <= textHeight) {
            paintText.textSize += 0.1f
            paintText.getTextBounds(sampleText, 0, sampleText.length, rectTextBounds)
        }
    }

    override fun update() {
        /*
            Final structure of counter should be as
             - text (partial)
             | margin
             -text
             | margin
             - text (partial)
         */
        animateCounter()
    }

    fun moveDown() {

        // Check if passed down border
        if (currentTextY >= height + textHeight) {
            if (counterNext <= endValue || counterCurrent <= endValue) {
                stopAnimation()
            } else {
                currentTextY = positionInitialNext
                counterCurrent = counterNext - 1
            }
        }

        if (nextTextY >= height + textHeight) {
            if (counterNext <= endValue || counterCurrent <= endValue) {
                stopAnimation()
            } else {
                nextTextY = positionInitialNext
                counterNext = counterCurrent - 1
            }
        }

        if (isAnimating) {
            nextTextY += animationSpeed
            currentTextY += animationSpeed
        }
    }

    fun moveUp() {

        // Check if passed up border
        if (currentTextY <= 0) {
            if (counterNext >= endValue || counterCurrent >= endValue) {
                stopAnimation()
            } else {
                currentTextY = positionInitialNext
                counterCurrent = counterNext + 1
            }
        }

        if (nextTextY <= 0) {
            if (counterNext >= endValue || counterCurrent >= endValue) {
                stopAnimation()
            } else {
                nextTextY = positionInitialNext
                counterNext = counterCurrent + 1
            }
        }

        if (isAnimating) {
            nextTextY -= animationSpeed
            currentTextY -= animationSpeed
        }
    }

    private fun animateCounter() {

        if (!isAnimating) return

        if (isUpCounter) {
            moveUp()
        } else {
            moveDown()
        }
    }

    private fun startAnimation() {

        // End and stop value are equal, don't do anything
        if (endValue == startValue) return

        // Chek incremental or decremental counter
        isUpCounter = endValue > startValue

        // This is the vertical of space between 2 texts
        val verticalMargin = (height - textHeight) / 2

        // Text uses top position of rectangle
        val centerPosition = centerY + (textHeight / 2f)
        val bottomPosition = height + textHeight
        val topPosition = 0f


        if (isUpCounter) {
            positionInitialNext = bottomPosition
            counterCurrent = startValue
            counterNext = counterCurrent + 1
        } else {
            positionInitialNext = topPosition
            counterCurrent = startValue
            counterNext = counterCurrent - 1
        }

        positionInitialCurrent = centerPosition
        nextTextY = positionInitialNext
        currentTextY = positionInitialCurrent

        isAnimating = true
    }

    private fun stopAnimation() {
        isAnimating = false
    }

    fun toggleAnimationState() {
        if (!isAnimating) {
            startAnimation()
        } else {
            stopAnimation()
        }
    }

    override fun render(canvas: Canvas) {
        canvas.drawColor(Color.LTGRAY)

        // Draw current value
        drawText("$counterCurrent", currentTextY, canvas)

        if (debugMode) {
            drawDebug(canvas)
        }

        // Draw next value
        paintText.color = Color.RED
        drawText("$counterNext", nextTextY, canvas)


    }

    /**
     * Debugging function to check spacing and animation speed
     */
    private fun drawDebug(canvas: Canvas) {

        val verticalMargin = (height - textHeight) / 2

        paintDebug.color = Color.GREEN
        if (isUpCounter) {
            canvas.drawRect(
                centerX - rectTextBounds.width() / 2,
                currentTextY,
                centerX + rectTextBounds.width() / 2,
                currentTextY + verticalMargin,
                paintDebug
            )
        } else {
            canvas.drawRect(
                centerX - rectTextBounds.width() / 2,
                nextTextY,
                centerX + rectTextBounds.width() / 2,
                nextTextY + verticalMargin,
                paintDebug
            )
        }

        paintDebug.color = Color.CYAN

        canvas.drawRect(
            centerX - rectTextBounds.width() / 2,
            0f,
            centerX + rectTextBounds.width() / 2,
            verticalMargin,
            paintDebug
        )

        canvas.drawRect(
            centerX - rectTextBounds.width() / 2,
            height - verticalMargin,
            centerX + rectTextBounds.width() / 2,
            height.toFloat(),
            paintDebug
        )

        canvas.drawText(
            "currentTextY: $currentTextY, " +
                    "nextTextY: $nextTextY, " +
                    "height: $height",

            0f,
            30f,
            paintDebugText
        )

        paintDebug.color = Color.MAGENTA
        canvas.drawLine(0f, height / 2f, width.toFloat(), height / 2f, paintDebug)
        canvas.drawLine(width / 2f, 0f, width / 2f, height.toFloat(), paintDebug)
        canvas.drawText(
            "Vertical margin: $verticalMargin, " +
                    "textHeight: $textHeight",
            0f,
            70f,
            paintDebugText
        )
    }

    private fun drawText(text: String, y: Float, canvas: Canvas) {

        val horizontalCenter = centerX - paintText.measureText(text) / 2

        // Draw border text
        paintText.color = Color.DKGRAY
        paintText.style = Paint.Style.STROKE
        canvas.drawText(
            text,
            horizontalCenter,
            y,
            paintText
        )

        // Draw text
        paintText.color = Color.WHITE
        paintText.style = Paint.Style.FILL
        canvas.drawText(
            text,
            horizontalCenter,
            y,
            paintText
        )
    }

    /**
     * Scale images or drawing according to screen resolution
     *
     * @param width to be scaled
     * @return scaled dimension depending on device resolution
     */
    private fun realPx(width: Float): Float {
        return width * pixelScale
    }

}
