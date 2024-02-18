package com.example.sampletodo.viewmodel

import androidx.lifecycle.ViewModel
import com.example.sampletodo.TodoModel
import com.example.sampletodo.repository.ITodoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class TodoViewModel @Inject constructor(private val todoRepository: ITodoRepository): ViewModel() {

    suspend fun addTodos(todo: TodoModel) {
        todoRepository.insertTodo(todo)
    }

    suspend fun getTodo(id: Int): TodoModel {
        return todoRepository.getTodo(id)
    }

    suspend fun getTodos(): Flow<List<TodoModel>> {
        return todoRepository.getTodos()
    }

    suspend fun updateTodo(todo: TodoModel){
        todoRepository.updateTodo(todo)
    }

    suspend fun deleteTodo(id: Int){
        todoRepository.deleteTodo(id)
    }
}