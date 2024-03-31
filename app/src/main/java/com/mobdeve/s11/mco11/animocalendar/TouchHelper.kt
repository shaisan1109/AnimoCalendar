package com.mobdeve.s11.mco11.animocalendar

import android.app.AlertDialog
import android.content.DialogInterface
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class TouchHelper(private val adapter: ToDoAdapter, private val fragmentManager: FragmentManager) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (direction == ItemTouchHelper.RIGHT) {
            AlertDialog.Builder(adapter.getContext())
                .setMessage("Are you sure?")
                .setTitle("Delete Task")
                .setPositiveButton("Yes") { dialog, which -> adapter.deleteTask(position) }
                .setNegativeButton("No") { dialog, which -> adapter.notifyItemChanged(position) }
                .create()
                .show()
        } else {
            adapter.editTask(position, fragmentManager)
        }
    }
}
