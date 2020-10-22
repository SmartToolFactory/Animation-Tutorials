//package com.smarttoolfactory.tutorial3_1transitions.transition
//
//import android.animation.Animator
//import android.graphics.Color
//import android.view.ViewGroup
//import androidx.transition.*
//
//class CustomFade(starColor: Int, endColor: Int) : BackgroundTransition(starColor,endColor) {
//
//    override fun captureEndValues(transitionValues: TransitionValues) {
//        super.captureEndValues(transitionValues)
//        println("🔥 CustomFade captureEndValues() view: ${transitionValues.view} ")
//
//        transitionValues.values.forEach { (key, value) ->
//            println("Key: $key, value: $value")
//        }
//    }
//
//    override fun captureStartValues(transitionValues: TransitionValues) {
//        super.captureStartValues(transitionValues)
//        println("⚠️ CustomFade captureStartValues() view: ${transitionValues.view} ")
//        transitionValues.values.forEach { (key, value) ->
//            println("Key: $key, value: $value")
//        }
//    }
//
//    override fun createAnimator(
//        sceneRoot: ViewGroup,
//        startValues: TransitionValues?,
//        endValues: TransitionValues?
//    ): Animator? {
//
//        println("🎃 CustomFade createAnimator() startValues: $startValues endValues: $endValues ")
//
//        return super.createAnimator(sceneRoot, startValues, endValues)
//    }
//}