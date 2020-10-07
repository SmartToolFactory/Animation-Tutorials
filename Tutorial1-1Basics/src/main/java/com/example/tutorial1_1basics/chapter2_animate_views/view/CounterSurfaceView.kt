package com.example.tutorial1_1basics.chapter2_animate_views.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.util.AttributeSet
import android.view.SurfaceHolder

class CounterSurfaceView : CoroutineSurfaceView {

    /*
        Animation variables
     */
    private var currentTextY = 0f
    private var nextTextY = 0f

    /**
     * Position of current text on screen
     */
    private var positionInitialCurrent = 0f

    /**
     * Position of next counter text
     */
    private var positionInitialNext = 0f

    private var isAnimating = true

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

    private var textHeight = 0

    private var isUpCounter = true

    var startValue = 0
        set(value) {
            isUpCounter = (endValue > value)
            field = value
        }

    var endValue = 0
        set(value) {
            isUpCounter = (startValue < value)
            field = value
        }


    private var counter = 0
    private var counterCurrent = 0
    private var counterNext = 1

    private val paintText = Paint().apply {
        style = Paint.Style.FILL
        strokeWidth = 4f
        textSize = 12f
        color = Color.WHITE
        isAntiAlias = true

    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    override fun init(context: Context) {
        super.init(context)
    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {
        super.surfaceChanged(holder, format, width, height)

        centerX = width / 2f
        centerY = height / 2f

        textHeight = getDynamicTextSize(height)
        positionInitialCurrent = centerY + textHeight / 2.toFloat()

        val verticalMargin = (height - textHeight) / 2f
        positionInitialNext = positionInitialCurrent + verticalMargin + textHeight

        currentTextY = positionInitialCurrent
        nextTextY = positionInitialNext
    }

    /**
     * Dynamically adjust text size to be 1/3 of height of this View
     */
    private fun getDynamicTextSize(height: Int): Int {

        val sampleText = "1"
        val length = sampleText.length

        while (rectTextBounds.height() < height / 3f) {
            paintText.getTextBounds(sampleText, 0, length, rectTextBounds)
            paintText.textSize += 2f
        }

        return rectTextBounds.height()
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

    private fun animateCounter() {

        if (isAnimating) {

            val animationScale = height / 20f
            nextTextY -= animationScale
            currentTextY -= animationScale

            if (currentTextY < 0) {
                currentTextY = positionInitialNext
                counterCurrent = counterNext + 1
            }

            if (nextTextY < 0) {
                nextTextY = positionInitialNext
                counterNext = counterCurrent + 1
            }
        }
    }

    fun startAnimation() {
        if (!isAnimating) {
            nextTextY = positionInitialNext
            currentTextY = positionInitialCurrent
            isAnimating = true
        }
    }

    override fun render(canvas: Canvas) {
        canvas.drawColor(Color.DKGRAY)
        drawText("$counterCurrent", currentTextY, canvas)
        drawText("$counterNext", nextTextY, canvas)
    }

    private fun drawText(text: String, y: Float, canvas: Canvas) {

        // Store text position and dimensions in rectangle
        paintText.getTextBounds(text, 0, text.length, rectTextBounds)

        // Draw border text
        paintText.color = Color.RED
        paintText.style = Paint.Style.STROKE
        canvas.drawText(
            text,
            centerX - rectTextBounds.width() / 2.toFloat(),
            y,
            paintText
        )

        // Draw text
        paintText.color = Color.WHITE
        paintText.style = Paint.Style.FILL
        canvas.drawText(
            text,
            centerX - rectTextBounds.width() / 2.toFloat(),
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
