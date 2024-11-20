package com.example.todoappfirebase.todo

import com.example.todoappfirebase.utils.model.ToDoData

interface TodoRepository {
    suspend fun fetchTodos(): List<ToDoData>?
    suspend fun addTodo(todo: ToDoData): Unit?

}
