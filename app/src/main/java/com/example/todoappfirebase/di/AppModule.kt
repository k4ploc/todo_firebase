package com.example.todoappfirebase.di

import com.example.todoappfirebase.todo.TodoRepository
import com.example.todoappfirebase.todo.TodoRepositoryImpl
import com.example.todoappfirebase.ui.viewmodel.TodoViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single { provideOkHttpClient() } // Proveer OkHttpClient
    single { provideRetrofit(get()) } // Retrofit usa el cliente OkHttp
    single { provideTodoApi(get()) }

    // Usa la interface y la implementación
    single<TodoRepository> { TodoRepositoryImpl(get()) }
    viewModel { TodoViewModel(get()) }
}
