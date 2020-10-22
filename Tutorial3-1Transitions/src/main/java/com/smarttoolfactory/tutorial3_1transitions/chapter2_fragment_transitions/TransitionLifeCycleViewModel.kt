package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransitionLifeCycleViewModel : ViewModel() {

    private val _lifecycleText = MutableLiveData<String>()
     val lifeCycleText: LiveData<String>
        get() = _lifecycleText

    fun clearText() {
        _lifecycleText.value = ""
    }

    fun appendText(text: String) {
        _lifecycleText.value += text
    }

}