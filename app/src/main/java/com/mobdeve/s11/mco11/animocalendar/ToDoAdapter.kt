package com.mobdeve.s11.mco11.animocalendar

import AddNewTask
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore

class ToDoAdapter(private val activity: TasksActivity, private val todoList: List<ToDoModel>) :
    RecyclerView.Adapter<ToDoAdapter.MyViewHolder>() {

    private val firestore: FirebaseFirestore = FirebaseFirestore.getInstance()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(activity).inflate(R.layout.each_task, parent, false)
        return MyViewHolder(view)
    }

    fun deleteTask(position: Int) {
        val toDoModel = todoList[position]
        val taskId = toDoModel.taskId ?: ""
        firestore.collection("task").document(taskId).delete()
        todoList.toMutableList().removeAt(position)
        notifyItemRemoved(position)
    }

    fun getContext(): Context {
        return activity
    }

    fun editTask(position: Int) {
        val toDoModel = todoList[position]

        val bundle = Bundle().apply {
            putString("task", toDoModel.task)
            putString("due", toDoModel.due)
            putString("id", toDoModel.taskId)
        }

        val addNewTask = AddNewTask()
        addNewTask.arguments = bundle
        addNewTask.show(activity.supportFragmentManager, addNewTask.tag)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val toDoModel = todoList[position]
        holder.mCheckBox.text = toDoModel.task
        holder.mDueDateTv.text = "Due On " + toDoModel.due
        holder.mCheckBox.isChecked = toBoolean(toDoModel.status)
        val taskId = toDoModel.taskId ?: ""

        holder.mCheckBox.setOnCheckedChangeListener { compoundButton, isChecked ->
            if (isChecked) {
                firestore.collection("task").document(taskId).update("status", 1)
            } else {
                firestore.collection("task").document(taskId).update("status", 0)
            }
        }
    }

    private fun toBoolean(status: Int): Boolean {
        return status != 0
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var mDueDateTv: TextView = itemView.findViewById(R.id.dueDateTv)
        var mCheckBox: CheckBox = itemView.findViewById(R.id.mCheckBox)
    }
}
