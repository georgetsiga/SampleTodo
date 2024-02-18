package com.example.sampletodo

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "tb_todo")
class TodoModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    var title: String,
    var description: String,
    var isCompleted: Int
)
