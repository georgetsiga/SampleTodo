package com.example.sampletodo.repository

import com.example.sampletodo.TodoModel
import com.example.sampletodo.storage.dao.TodoDao
import kotlinx.coroutines.flow.Flow

class TodoRepository(private val todoDao: TodoDao) : ITodoRepository {
    override suspend fun insertTodo(todoModel: TodoModel) {
        todoDao.insetTodo(todoModel)
    }

    override suspend fun getTodo(id: Int): TodoModel {
        return todoDao.getTodo(id)
    }

    override suspend fun getTodos(): Flow<List<TodoModel>> {
        return todoDao.getTodos()
    }

    override suspend fun updateTodo(todoModel: TodoModel){
        todoDao.updateTodo(todoModel.id, todoModel.isCompleted, todoModel.title, todoModel.description)
    }

    override suspend fun deleteTodo(id: Int){
        todoDao.deleteTodo(id)
    }
}