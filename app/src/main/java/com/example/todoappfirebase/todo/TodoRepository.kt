package com.example.todoappfirebase.todo

import com.example.todoappfirebase.utils.model.ToDoData
import com.example.todoappfirebase.utils.model.TodoUpdateRequest

interface TodoRepository {
    suspend fun fetchTodos(): List<ToDoData>?
    suspend fun addTodo(todo: ToDoData): Unit?
    suspend fun updateTodo(todo: TodoUpdateRequest): Unit?
    suspend fun deleteTodo(id: Int): Unit?

}
