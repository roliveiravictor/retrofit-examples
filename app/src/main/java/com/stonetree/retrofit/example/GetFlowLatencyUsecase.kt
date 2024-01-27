package com.stonetree.retrofit.example

class GetFlowLatencyUsecase(
    private val repository: LatencyRepository = LatencyRepository()
) {

    operator fun invoke() = repository.flow()
}
