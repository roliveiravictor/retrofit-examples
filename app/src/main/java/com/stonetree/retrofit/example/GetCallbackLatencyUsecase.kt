package com.stonetree.retrofit.example

class GetCallbackLatencyUsecase(
    private val repository: LatencyRepository = LatencyRepository()
) {

    operator fun invoke(onServerResponse: Latency.() -> Unit) = repository.callback(onServerResponse)
}
