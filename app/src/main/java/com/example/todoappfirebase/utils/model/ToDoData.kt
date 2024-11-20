package com.example.todoappfirebase.utils.model

import com.google.gson.annotations.SerializedName

//data class ToDoData(var taskId: String? = null, var task: String, var completed: Boolean = false)
data class ToDoData(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String,
    @SerializedName("quantity") val quantity: String,
    @SerializedName("priority") val priority: String,
    @SerializedName("status") val status: String,
    @SerializedName("date") val date: String
)
