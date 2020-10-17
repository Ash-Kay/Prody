package com.ashkay.prody.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.recyclerview.widget.RecyclerView
import com.ashkay.prody.R
import com.ashkay.prody.models.Todo

class TodoListAdapter(private val todoList: ArrayList<Todo>) :
    RecyclerView.Adapter<TodoListAdapter.TodoListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_todo, parent, false)
        return TodoListViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoListViewHolder, position: Int) {
        holder.bind(todoList[position])
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun addNewTodo(newTodo: Todo){
        todoList.add(newTodo)
        notifyItemInserted(todoList.size)
    }

    inner class TodoListViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {

        private var cbTodo: CheckBox = view.findViewById(R.id.cbTodo)

        fun bind(item: Todo) {
            cbTodo.isChecked = item.isDone
            cbTodo.text = item.todoText
        }
    }
}