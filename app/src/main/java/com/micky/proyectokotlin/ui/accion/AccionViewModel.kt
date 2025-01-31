package com.micky.proyectokotlin.ui.accion

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class AccionViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is ACCION Fragment"
    }
    val text: LiveData<String> = _text
}