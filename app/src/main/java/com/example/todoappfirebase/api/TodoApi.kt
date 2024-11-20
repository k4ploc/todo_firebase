package com.example.todoappfirebase.api

import com.example.todoappfirebase.utils.model.ToDoData
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface TodoApi {
    @GET("todo")
    suspend fun getData(): Response<List<ToDoData>>

    @POST("todo")
    // suspend fun createTodo(@Body newTodo: ToDoData): Response<ToDoData>
    suspend fun addData(@Body newTodo: ToDoData): Response<Unit>
}
