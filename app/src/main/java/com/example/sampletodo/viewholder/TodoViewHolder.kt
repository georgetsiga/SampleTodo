package com.example.sampletodo.viewholder

import android.widget.CheckBox
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sampletodo.TodoModel
import com.example.sampletodo.adaptor.TodoAdaptor
import com.example.sampletodo.databinding.ViewTodoItemListBinding

class TodoViewHolder(private val binding: ViewTodoItemListBinding, val onClick: (TodoModel, Int) -> Unit, val onCompletedClick: (TodoModel, Int) -> Unit): RecyclerView.ViewHolder(binding.root) {
    fun bindData(adapter: TodoAdaptor, position: Int) {
        val todoModel = adapter.todoList[position]
        val isCompleted = todoModel.isCompleted == 1
        binding.isTaskCompleted.isChecked = isCompleted
        binding.todoTitle.text = todoModel.title
        binding.todoDescription.text = todoModel.description

        binding.todoListItems.setOnClickListener {
            onClick(todoModel, position)
        }
        binding.isTaskCompleted.setOnClickListener {
            todoModel.isCompleted = if (todoModel.isCompleted == 1) 0 else 1
            onCompletedClick(todoModel, position)
        }
    }
}