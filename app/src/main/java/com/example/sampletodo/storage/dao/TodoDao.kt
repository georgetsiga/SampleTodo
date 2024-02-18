package com.example.sampletodo.storage.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.sampletodo.TodoModel
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insetTodo(todoModel: TodoModel)

    @Query("SELECT * FROM tb_todo WHERE id = :id")
    suspend fun getTodo(id: Int): TodoModel

    @Query("SELECT * FROM tb_todo")
    fun getTodos(): Flow<List<TodoModel>>

    @Query("UPDATE tb_todo SET isCompleted = :isCompleted, title = :title, description = :description WHERE id = :id")
    suspend fun updateTodo(id: Int, isCompleted: Int, title: String, description: String)

    @Query("DELETE FROM tb_todo WHERE id = :id")
    suspend fun deleteTodo(id: Int)
}