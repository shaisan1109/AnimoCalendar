package com.mobdeve.s11.mco11.animocalendar

import android.app.DatePickerDialog
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.DatePicker
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FirebaseFirestore
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityEditTaskBinding
import java.util.*

class EditTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditTaskBinding
    private var dueDate: String = ""
    private lateinit var firestore: FirebaseFirestore
    private var id: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firestore = FirebaseFirestore.getInstance()

        id = intent.getStringExtra("taskId") ?: ""
        if (id.isNotEmpty()) {
            loadTaskDetails()
        }

        setupListeners()
    }

    private fun setupListeners() {
        binding.apply {
            editTaskNameEt.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    editSaveBtn.isEnabled = !s.isNullOrBlank()
                    editSaveBtn.setBackgroundColor(if (s.isNullOrBlank()) Color.GRAY else resources.getColor(R.color.light_accent))
                }
                override fun afterTextChanged(s: Editable?) {}
            })

            editDateTv.setOnClickListener {
                val calendar = Calendar.getInstance()
                val datePickerDialog = DatePickerDialog(
                    this@EditTaskActivity,
                    { _: DatePicker, i: Int, i1: Int, i2: Int ->
                        editDateTv.setText("$i2/${i1 + 1}/$i")
                        dueDate = "$i2/${i1 + 1}/$i"
                    },
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                )
                datePickerDialog.show()
            }

            editSaveBtn.setOnClickListener {
                saveTask()
            }

            editCancelBtn.setOnClickListener {
                finish()
            }
        }
    }

    private fun loadTaskDetails() {
        // Load task details from Firestore and update UI accordingly
    }

    private fun saveTask() {
        // Save task details to Firestore
    }
}
