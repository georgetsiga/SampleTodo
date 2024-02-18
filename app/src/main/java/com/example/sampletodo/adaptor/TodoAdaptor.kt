package com.example.sampletodo.adaptor

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.sampletodo.TodoModel
import com.example.sampletodo.databinding.ViewTodoItemListBinding
import com.example.sampletodo.viewholder.TodoViewHolder

class TodoAdaptor(
    private val context: Context,
    private val onClick: (TodoModel, Int) -> Unit,
    private val onCompletedClick: (TodoModel, Int) -> Unit
): RecyclerView.Adapter<TodoViewHolder>() {
    var todoList: List<TodoModel> = arrayListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val binding = ViewTodoItemListBinding.inflate(layoutInflater, parent, false)
        return TodoViewHolder(binding, onClick, onCompletedClick)
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bindData(this, position)
    }

    fun setTodoData(todoList: List<TodoModel>) {
        this.todoList = ArrayList(todoList)
    }
}