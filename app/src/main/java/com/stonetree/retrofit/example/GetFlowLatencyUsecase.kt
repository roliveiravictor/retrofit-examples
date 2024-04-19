package com.stonetree.retrofit.example

import kotlinx.coroutines.flow.Flow

class GetFlowLatencyUsecase(
    private val repository: LatencyRepository = LatencyRepository()
) {

    operator fun invoke(): Flow<Latency> = repository.flow()
}
