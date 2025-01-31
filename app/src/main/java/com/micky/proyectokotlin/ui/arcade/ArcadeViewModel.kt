package com.micky.proyectokotlin.ui.arcade

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ArcadeViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is Arcade Fragment"
    }
    val text: LiveData<String> = _text
}