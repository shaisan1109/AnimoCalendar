package com.mobdeve.s11.mco11.animocalendar

import android.content.DialogInterface
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

class TasksActivity : AppCompatActivity(), OnDialogCloseListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var mFab: FloatingActionButton

    private lateinit var firestore: FirebaseFirestore
    private lateinit var adapter: ToDoAdapter
    private lateinit var mList: MutableList<ToDoModel>
    private lateinit var query: Query
    private lateinit var listenerRegistration: ListenerRegistration

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tasks)

        recyclerView = findViewById(R.id.taskListRv)
        mFab = findViewById(R.id.addTaskFab)
        firestore = FirebaseFirestore.getInstance()

        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)

        mFab.setOnClickListener {
            AddNewTask.newInstance().show(supportFragmentManager, AddNewTask.TAG)
        }

        mList = mutableListOf()
        adapter = ToDoAdapter(this, mList)

        val itemTouchHelper = ItemTouchHelper(TouchHelper(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        recyclerView.adapter = adapter
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

    override fun onDialogClose(dialogInterface: DialogInterface) {
        mList.clear()
        showData()
        adapter.notifyDataSetChanged()
    }
}


