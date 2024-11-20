package com.example.todoappfirebase.todo

import android.util.Log
import com.example.todoappfirebase.api.TodoApi
import com.example.todoappfirebase.utils.model.ToDoData
import com.example.todoappfirebase.utils.model.TodoUpdateRequest

class TodoRepositoryImpl(private val todoApi: TodoApi) : TodoRepository {

    override suspend fun fetchTodos(): List<ToDoData>? {
        val response = todoApi.getData()
        Log.i("RESPONSE", response.toString())
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        } else {
            throw Exception("Error al obtener los datos: ${response.message()}")
        }
    }

    override suspend fun addTodo(newTodo: ToDoData) {
        val response = todoApi.addData(newTodo)
        if (!response.isSuccessful) {
            throw Exception("Error al agregar el todo: ${response.message()}")
        }
    }

    override suspend fun updateTodo(todoUpdated: TodoUpdateRequest) {
        val response = todoApi.updateTodo(todoUpdated)
        if (!response.isSuccessful) {
            throw Exception("Error al agregar el todo: ${response.message()}")
        }
    }
}
