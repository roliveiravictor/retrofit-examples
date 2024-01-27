package com.stonetree.retrofit.example

import kotlin.Long.Companion.MAX_VALUE


enum class Latency(var ms: Long?) {
    UNKNOWN(null),
    FAILURE(-1),
    ACQUIRED(MAX_VALUE)
}