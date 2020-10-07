package com.example.tutorial1_1basics.chapter2_animate_views.view

import android.content.Context
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.SurfaceHolder
import android.view.SurfaceView
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.*

abstract class CoroutineSurfaceView : SurfaceView, SurfaceHolder.Callback,
    DefaultLifecycleObserver {

    // Handle works in thread that exception is caught that are
    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        println("🤬 Parent Caught $throwable in thread ${Thread.currentThread().name}, and coroutineContext: $coroutineContext")
    }

    internal lateinit var canvas: Canvas
    var framePerSecond = 60

    private var renderTime = 100L / framePerSecond

    private val coroutineScope = CoroutineScope(handler + SupervisorJob() + Dispatchers.Default)

    private lateinit var job: Job

    private lateinit var surfaceHolder: SurfaceHolder

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context)
    }

    constructor(context: Context) : super(context) {
        init(context)
    }

    open fun init(context: Context) {
        surfaceHolder = this.holder
        surfaceHolder.addCallback(this)
        setZOrderOnTop(true)
    }

    override fun surfaceCreated(holder: SurfaceHolder) {

    }

    override fun surfaceChanged(holder: SurfaceHolder, format: Int, width: Int, height: Int) {

    }

    override fun surfaceDestroyed(holder: SurfaceHolder) {

    }

    private fun startCoroutineRendering() {
        job = coroutineScope.launch {

            while (isActive) {

                if (!surfaceHolder.surface.isValid) {
                    continue
                }

                canvas = surfaceHolder.lockCanvas()
                update()
                render(canvas)
                holder.unlockCanvasAndPost(canvas)

                delay(renderTime)
            }
        }
    }

    internal abstract fun update()

    internal abstract fun render(canvas: Canvas)

    override fun onResume(owner: LifecycleOwner) {
        super.onResume(owner)
        startCoroutineRendering()
    }

    override fun onPause(owner: LifecycleOwner) {
        super.onPause(owner)
        coroutineScope.launch(Dispatchers.Main.immediate) {
            job.cancelAndJoin()
        }
    }
}

