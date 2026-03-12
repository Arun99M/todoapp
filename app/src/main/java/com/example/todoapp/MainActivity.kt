package com.example.todoapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoapp.presentation.TodoViewModel
import com.example.todoapp.presentation.TodoAdapter

class MainActivity : AppCompatActivity() {

    private val viewModel: TodoViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerView = findViewById<RecyclerView>(R.id.activity_main)
        val etTodo = findViewById<EditText>(R.id.etTodo)
        val btnAdd = findViewById<Button>(R.id.btnAdd)

        val adapter = TodoAdapter(
            onToggle = { todo -> viewModel.toggleTodoDone(todo) },
            onDelete = { todo -> viewModel.deleteTodo(todo) },
            onEdit = { todo -> viewModel.editTodo(todo, todo.title) }
        )

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        btnAdd.setOnClickListener {
            val title = etTodo.text.toString()
            if (title.isNotEmpty()) {
                viewModel.addTodo(title)
                etTodo.text.clear()
            }
        }

        viewModel.todos.observe(this) { list ->
            adapter.submitList(list)
        }
    }
}