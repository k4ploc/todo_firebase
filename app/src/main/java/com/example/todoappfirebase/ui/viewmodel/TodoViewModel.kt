package com.example.todoappfirebase.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappfirebase.todo.TodoRepository
import com.example.todoappfirebase.utils.model.ToDoData
import com.example.todoappfirebase.utils.model.TodoUpdateRequest
import kotlinx.coroutines.launch

class TodoViewModel(private val repository: TodoRepository) : ViewModel() {

    private val _todos = MutableLiveData<List<ToDoData>>()
    val todos: LiveData<List<ToDoData>> get() = _todos

    private val _isTodoAdded = MutableLiveData<Boolean>()
    val isTodoAdded: LiveData<Boolean> get() = _isTodoAdded

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun fetchTodos() {
        viewModelScope.launch {
            try {
                val data = repository.fetchTodos() ?: emptyList()
                Log.i("DATA", data.toString())
                _todos.value = data
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun addTodo(newTodo: ToDoData) {
        viewModelScope.launch {
            try {
                repository.addTodo(newTodo)

                _isTodoAdded.value = true
                _todos.value = emptyList()

                fetchTodos() // Actualiza la lista después de agregar
            } catch (e: Exception) {
                _isTodoAdded.value = false
                _error.value = e.message
            }
        }
    }

    fun updateTodo(todoUpdateRequest: TodoUpdateRequest) {
        viewModelScope.launch {
            try {
                repository.updateTodo(todoUpdateRequest)

                _isTodoAdded.value = true
                _todos.value = emptyList()

                fetchTodos() // Actualiza la lista después de agregar
            } catch (e: Exception) {
                _isTodoAdded.value = false
                _error.value = e.message
            }
        }
    }
}
