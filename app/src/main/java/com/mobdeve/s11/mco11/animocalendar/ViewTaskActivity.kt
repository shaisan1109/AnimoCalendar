package com.mobdeve.s11.mco11.animocalendar

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class ViewTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_view_task)

        // Retrieve task details from intent extras
        val taskName = intent.getStringExtra("task")
        val dueDate = intent.getStringExtra("due")
        val description = intent.getStringExtra("description")
        val host = intent.getStringExtra("host")
        val priority = intent.getStringExtra("priority")?.let { ToDoModel.Priority.valueOf(it) } ?: ToDoModel.Priority.Low
        val category = intent.getStringExtra("category")?.let { ToDoModel.Category.valueOf(it) } ?: ToDoModel.Category.Personal


        // Find TextViews in the layout
        val taskNameTv: TextView = findViewById(R.id.viewTaskNameTv)
        val dueDateTv: TextView = findViewById(R.id.viewDateTv)
        val descriptionTv: TextView = findViewById(R.id.viewDescTv)
        val hostTv: TextView = findViewById(R.id.viewHostTv)
        val priorityTv: TextView = findViewById(R.id.viewPrioTv)
        val categoryTv: TextView = findViewById(R.id.viewCatTv)

        // Set task details to TextViews
        taskNameTv.text = taskName
        dueDateTv.text = dueDate
        descriptionTv.text = description
        hostTv.text = host
        priorityTv.text = priority.displayName
        categoryTv.text = category.displayName
    }
}
