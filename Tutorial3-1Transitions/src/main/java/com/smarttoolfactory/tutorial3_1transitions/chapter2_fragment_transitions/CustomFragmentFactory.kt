package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory

//class CustomFragmentFactory : FragmentFactory() {
//
//    @DrawableRes
//    var drawableRes: Int = -1
//
//    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
//
//        return if (className == Fragment2_1Details::class.java.name && drawableRes != -1) {
//            Fragment2_1Details()
//        } else {
//            super.instantiate(classLoader, className)
//        }
//    }
//}