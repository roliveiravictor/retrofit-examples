package com.stonetree.retrofit.example

import android.util.Log
import com.stonetree.retrofit.example.Latency.UNKNOWN
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.Default
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext
import retrofit2.Retrofit

class LatencyRepository {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://rest.ensembl.org")
        .build()

    private val service = retrofit.create(LatencyService::class.java)

    fun callback(onServerResponse: (Latency) -> Unit) =
        LatencyRemoteDataSource(service).async {
            onServerResponse(it)
        }

    suspend fun coroutine() = withContext(IO) {
        LatencyRemoteDataSource(service).sync()
    }

    fun flow(): Flow<Latency> = callbackFlow {
        send(UNKNOWN)
        withContext(IO) {
            trySend(LatencyRemoteDataSource(service).sync())
            close()
        }
    }
}
