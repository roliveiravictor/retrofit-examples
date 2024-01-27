package com.stonetree.retrofit.example

import com.stonetree.retrofit.example.Latency.ACQUIRED
import com.stonetree.retrofit.example.Latency.FAILURE
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LatencyRemoteDataSource(
    private val service: LatencyService,
) {

    fun sync() = service.ping().execute().withRestHandler()

    fun async(callback: (Latency) -> Unit) {
        service.ping().enqueue(
            object : Callback<Unit> {
                override fun onResponse(call: Call<Unit>, response: Response<Unit>) {
                    callback(response.withRestHandler())
                }

                override fun onFailure(call: Call<Unit>, t: Throwable) {
                    callback(FAILURE)
                }
            }
        )
    }

    private fun Response<Unit>.withRestHandler(): Latency {
        return if (isSuccessful) {
            with(raw()) {
                ACQUIRED.also {
                    it.ms = receivedResponseAtMillis() - sentRequestAtMillis()
                }
            }
        } else {
            FAILURE
        }
    }
}