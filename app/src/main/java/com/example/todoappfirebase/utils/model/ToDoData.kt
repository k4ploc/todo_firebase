package com.example.todoappfirebase.utils.model

import com.google.gson.annotations.SerializedName

//data class ToDoData(var taskId: String? = null, var task: String, var completed: Boolean = false)
data class ToDoData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") var name: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("priority") val priority: PriorityTodo,
    @SerializedName("status") val status: String,
    @SerializedName("date") val date: String
)

data class TodoUpdateRequest(
    val id: Int,
    var name: String,
    var quantity: String,
    var priority: PriorityTodo
)
