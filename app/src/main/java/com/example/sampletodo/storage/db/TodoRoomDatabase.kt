package com.example.sampletodo.storage.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.sampletodo.TodoModel
import com.example.sampletodo.storage.dao.TodoDao

@Database(entities = [TodoModel::class], version = 1)
abstract class TodoRoomDatabase: RoomDatabase() {
    abstract fun todoDao(): TodoDao
}