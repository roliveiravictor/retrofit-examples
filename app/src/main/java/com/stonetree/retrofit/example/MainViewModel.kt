package com.stonetree.retrofit.example

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stonetree.retrofit.example.Latency.ACQUIRED
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainViewModel(
    private val callback: GetCallbackLatencyUsecase = GetCallbackLatencyUsecase(),
    private val coroutine: GetCoroutineLatencyUsecase = GetCoroutineLatencyUsecase(),
    private val flow: GetFlowLatencyUsecase = GetFlowLatencyUsecase()
) : ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, exception ->
        Log.e(
            javaClass.name,
            exception.message.toString()
        )
    }

    fun ping() {
        viewModelScope.launch(exceptionHandler) {
            flow().collect { it.log("Flow") }
            coroutine().log("Coroutine")
            callback { log("Callback") }
        }
    }

    private fun Latency.log(name: String) {
        Log.d(
            javaClass.simpleName,
            if (this == ACQUIRED) {
                "Latency from $name ".plus(toString()).plus(" within ${ms}ms \n")
            } else {
                "Latency from $name ".plus(toString())
            }
        )
    }
}