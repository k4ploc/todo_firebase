package com.example.todoappfirebase.di

import com.example.todoappfirebase.api.TodoApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

fun provideOkHttpClient(): OkHttpClient {
    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY // Nivel de log que deseas (BODY, HEADERS, etc.)
    }

    return OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor) // Agrega el interceptor de registro
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
        .baseUrl("http://10.0.2.2:8080/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}

fun provideTodoApi(retrofit: Retrofit): TodoApi {
    return retrofit.create(TodoApi::class.java)
}
