package com.mobdeve.s11.mco11.animocalendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityCreateTaskBinding
import java.util.*

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateTaskBinding
    private lateinit var firestore: FirebaseFirestore
    private var dueDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firestore
        firestore = FirebaseFirestore.getInstance()

        // Add text change listener to task name edit text
        binding.taskNameEt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                binding.createBtn.isEnabled = charSequence.isNotBlank()
                binding.createBtn.setBackgroundColor(
                    resources.getColor(
                        if (charSequence.isNotBlank()) R.color.light_accent else android.R.color.darker_gray
                    )
                )
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        // Set click listener for setting due date
        binding.dateTv.setOnClickListener {
            showDatePickerDialog()
        }

        // Set click listener for create button
        binding.createBtn.setOnClickListener {
            saveTask()
        }

        // Set click listener for cancel button
        binding.cancelBtn.setOnClickListener {
            finish()
        }
    }

    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val datePickerDialog = DatePickerDialog(
            this,
            { _: DatePicker, i: Int, i1: Int, i2: Int ->
                setDueDate("$i2/${i1 + 1}/$i")
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        )

        datePickerDialog.show()
    }

    private fun setDueDate(date: String) {
        dueDate = date
        binding.dateTv.setText(date)
    }

    private fun saveTask() {
        val task = binding.taskNameEt.text.toString()
        val desc = binding.descriptionEt.text.toString()
        val host = binding.hostEt.text.toString()
        val priority = getPriority()
        val category = getCategory()

        if (task.isEmpty()) {
            Toast.makeText(this, "Task name cannot be blank", Toast.LENGTH_SHORT).show()
        } else {
            val taskMap = hashMapOf<String, Any>()
            taskMap["task"] = task
            taskMap["due"] = dueDate
            taskMap["description"] = desc
            taskMap["status"] = 0
            taskMap["time"] = FieldValue.serverTimestamp()
            taskMap["host"] = host
            taskMap["priority"] = priority
            taskMap["category"] = category

            firestore.collection("task").add(taskMap)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(this, "Task Saved", Toast.LENGTH_SHORT).show()
                        finish()
                    } else {
                        Toast.makeText(this, task.exception?.message, Toast.LENGTH_SHORT).show()
                    }
                }.addOnFailureListener { e ->
                    Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                }
        }
    }

    private fun getPriority(): String {
        return when {
            binding.lowPrioRBtn.isChecked -> "Low"
            binding.midPrioRBtn.isChecked -> "Medium"
            binding.highPrioRBtn.isChecked -> "High"
            else -> "Urgent"
        }
    }

    private fun getCategory(): String {
        return when {
            binding.personalRBtn.isChecked -> "Personal"
            binding.acadRBtn.isChecked -> "Academic"
            else -> "Holiday"
        }
    }
}
