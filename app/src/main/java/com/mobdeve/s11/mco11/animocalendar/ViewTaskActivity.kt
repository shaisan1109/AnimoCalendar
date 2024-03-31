package com.mobdeve.s11.mco11.animocalendar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityViewTaskBinding

class ViewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val taskId = intent.getStringExtra("taskId") ?: ""

        binding.editBtn.setOnClickListener {
            val intent = Intent(this, EditTaskActivity::class.java)
            intent.putExtra("taskId", taskId)
            startActivity(intent)
        }

        val taskName = intent.getStringExtra("task")
        val dueDate = intent.getStringExtra("due")
        val description = intent.getStringExtra("description")
        val host = intent.getStringExtra("host")
        val priority = intent.getStringExtra("priority")?.let { ToDoModel.Priority.valueOf(it) } ?: ToDoModel.Priority.Low
        val category = intent.getStringExtra("category")?.let { ToDoModel.Category.valueOf(it) } ?: ToDoModel.Category.Personal

        binding.apply {
            viewTaskNameTv.text = taskName
            viewDateTv.text = dueDate
            viewDescTv.text = description
            viewHostTv.text = host
            viewPrioTv.text = priority.displayName
            viewCatTv.text = category.displayName
        }
    }
}
