package com.example.todoapp.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.R
import com.example.todoapp.domain.Todo

class TodoAdapter(
    private val onToggle: (Todo) -> Unit,
    private val onDelete: (Todo) -> Unit,
    private val onEdit: (Todo) -> Unit
) : ListAdapter<Todo, TodoAdapter.TodoViewHolder>(TodoDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_todo_item, parent, false)
        return TodoViewHolder(view)
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class TodoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val cbDone = view.findViewById<CheckBox>(R.id.cbDone)
        private val tvTitle = view.findViewById<TextView>(R.id.tvTitle)
        private val tvDescription = view.findViewById<TextView>(R.id.tvDescription)
        private val btnEdit = view.findViewById<ImageButton>(R.id.btnEdit)
        private val btnDelete = view.findViewById<ImageButton>(R.id.btnDelete)

        fun bind(todo: Todo) {
            tvTitle.text = todo.title
            tvDescription.text = todo.description
            cbDone.isChecked = todo.isDone

            cbDone.setOnCheckedChangeListener { _, _ ->
                onToggle(todo)
            }

            btnEdit.setOnClickListener {
                onEdit(todo)
            }

            btnDelete.setOnClickListener {
                onDelete(todo)
            }
        }
    }

    class TodoDiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}