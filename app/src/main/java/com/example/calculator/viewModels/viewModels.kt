package com.example.calculator.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MyViewModel : ViewModel() {
    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input
    var lastClickTime = 0L

    fun onInputChange(newValue: String){
        _input.value = newValue
    }

    fun clear(){
        _input.value = ""
    }

    fun append(value: String){
        _input.value += value
    }

    fun deleteLast(){
        _input.value = _input.value.dropLast(1)
    }

    fun setResult(value: String){
        _input.value = value
    }
}