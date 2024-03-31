package com.mobdeve.s11.mco11.animocalendar

import ToDoModel
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.FirebaseFirestore


class ToDoAdapter(private val activity: TasksActivity, private val todoList: List<ToDoModel>, private val fragmentManager: FragmentManager) :
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

    fun viewTask(position: Int, fragmentManager: FragmentManager) {
        val toDoModel = todoList[position]

        val intent = Intent(activity, ViewTaskActivity::class.java).apply {
            putExtra("task", toDoModel.taskName)
            putExtra("due", toDoModel.dueDate)
            putExtra("description", toDoModel.taskDescription)
            putExtra("host", toDoModel.host)
            putExtra("priority", toDoModel.priority.name)
            putExtra("category", toDoModel.category.name)
            putExtra("id", toDoModel.taskId)
        }

        activity.startActivity(intent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val toDoModel = todoList[position]
        holder.mCheckBox.text = toDoModel.taskName
        holder.mDueDateTv.text = "Due On " + toDoModel.dueDate
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
