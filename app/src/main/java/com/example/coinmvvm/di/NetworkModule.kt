package com.example.coinmvvm.di

import com.example.coinmvvm.BuildConfig
import com.example.coinmvvm.data.remote.network.NetworkAPI
import com.example.coinmvvm.data.remote.network.NetworkRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.ktor.client.*
import io.ktor.client.engine.okhttp.*
import io.ktor.client.plugins.websocket.*
import io.ktor.serialization.gson.*
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitClient(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val loggingInterceptor = HttpLoggingInterceptor()
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
            okHttpClient.addInterceptor(loggingInterceptor)
        }

        return Retrofit.Builder()
            .baseUrl(NetworkRepositoryImpl.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build()
    }

    @Provides
    @Singleton
    fun provideNetworkAPI(retrofit: Retrofit): NetworkAPI {
        return retrofit.create(NetworkAPI::class.java)
    }

    @Provides
    @Singleton
    fun provideHttpClient(): HttpClient {
        return HttpClient(OkHttp) {
            install(WebSockets) {
                contentConverter = GsonWebsocketContentConverter()
            }
        }
    }
}