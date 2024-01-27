package com.stonetree.retrofit.example

class GetCoroutineLatencyUsecase(
    private val repository: LatencyRepository = LatencyRepository()
) {

    suspend operator fun invoke() = repository.coroutine()
}
