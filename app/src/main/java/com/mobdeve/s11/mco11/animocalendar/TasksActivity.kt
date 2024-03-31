package com.mobdeve.s11.mco11.animocalendar

import ToDoModel
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentChange
import com.google.firebase.firestore.ListenerRegistration
import com.google.firebase.firestore.Query
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityTasksBinding

class TasksActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTasksBinding
    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ToDoAdapter
    private lateinit var mList: MutableList<ToDoModel>
    private lateinit var query: Query
    private lateinit var listenerRegistration: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTasksBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        binding.apply {
            taskListRv.setHasFixedSize(true)
            taskListRv.layoutManager = LinearLayoutManager(this@TasksActivity)

            addTaskFab.setOnClickListener {
                startActivity(Intent(this@TasksActivity, CreateTaskActivity::class.java))
            }
        }

        mList = mutableListOf()
        adapter = ToDoAdapter(this, mList, supportFragmentManager)

        val itemTouchHelper = ItemTouchHelper(TouchHelper(adapter, supportFragmentManager))
        itemTouchHelper.attachToRecyclerView(binding.taskListRv)

        binding.taskListRv.adapter = adapter
        showData()
    }

    private fun showData() {
        query = firestore.collection("task").orderBy("time", Query.Direction.DESCENDING)
        listenerRegistration = query.addSnapshotListener { value, error ->
            if (error != null) {
                // Handle error
                return@addSnapshotListener
            }

            for (documentChange in value?.documentChanges!!) {
                if (documentChange.type == DocumentChange.Type.ADDED) {
                    val id = documentChange.document.id
                    val toDoModel = documentChange.document.toObject(ToDoModel::class.java).withId<ToDoModel>(id)

                    mList.add(toDoModel)
                    adapter.notifyDataSetChanged()
                }
            }
            listenerRegistration.remove()
        }
    }

    override fun onResume() {
        super.onResume()
        mList.clear()
        showData()
        adapter.notifyDataSetChanged()
    }
}
