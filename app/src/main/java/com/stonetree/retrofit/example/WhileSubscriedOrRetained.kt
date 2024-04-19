package com.stonetree.retrofit.example

import android.os.Handler
import android.os.Looper
import android.view.Choreographer
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.SharingCommand
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.dropWhile
import kotlinx.coroutines.flow.transformLatest

object WhileSubscribedOrRetained : SharingStarted {

    private val handler = Handler(Looper.getMainLooper())

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun command(subscriptionCount: StateFlow<Int>) = subscriptionCount
        .transformLatest { count ->
            if (count > 0) {
                emit(SharingCommand.START)
            } else {
                val posted = CompletableDeferred<Unit>()
                Choreographer.getInstance().postFrameCallback {
                    handler.postAtFrontOfQueue {
                        handler.post {
                            posted.complete(Unit)
                        }
                    }
                }
                posted.await()
                emit(SharingCommand.STOP)
            }
        }
        .dropWhile { it != SharingCommand.START }
        .distinctUntilChanged()

    override fun toString(): String = "WhileSubscribedOrRetained"
}