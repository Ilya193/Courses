package ru.ikom.core.network

import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

interface CoreNetworkModule {

    val json: Json

    val okHttpClient: OkHttpClient

    val retrofit: Retrofit
}

fun CoreNetworkModule() =
    object : CoreNetworkModule {

        override val json: Json = Json { ignoreUnknownKeys = true }

        override val okHttpClient: OkHttpClient =
            OkHttpClient().newBuilder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()

        override val retrofit: Retrofit =
            Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .baseUrl("http://localhost/")
                .build()
    }