package com.smarttoolfactory.tutorial3_1transitions.transition

import android.transition.*

class CustomTransition: TransitionSet() {
    init {
        ordering = ORDERING_TOGETHER
        addTransition(ChangeBounds())
        addTransition(ChangeTransform())
        addTransition(ChangeClipBounds())
        addTransition(ChangeImageTransform())
    }
}