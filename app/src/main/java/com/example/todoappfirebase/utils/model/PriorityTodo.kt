package com.example.todoappfirebase.utils.model

enum class PriorityTodo {
    LOW,
    NORMAL,
    HIGH
}

fun getPriority(value: String): PriorityTodo? {
    return when (value.lowercase()) {
        "baja", "low" -> PriorityTodo.LOW
        "normal" -> PriorityTodo.NORMAL
        "alta", "high" -> PriorityTodo.HIGH
        else -> null // Devuelve null si no hay un mapeo válido
    }
}
