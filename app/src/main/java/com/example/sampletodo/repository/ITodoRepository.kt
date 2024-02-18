package com.example.sampletodo.repository

import com.example.sampletodo.TodoModel
import kotlinx.coroutines.flow.Flow

interface ITodoRepository {
    suspend fun insertTodo(todoModel: TodoModel)
    suspend fun getTodo(id: Int): TodoModel
    suspend fun getTodos(): Flow<List<TodoModel>>
    suspend fun updateTodo(todoModel: TodoModel)
    suspend fun deleteTodo(id: Int)
}