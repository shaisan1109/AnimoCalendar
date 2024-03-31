package com.mobdeve.s11.mco11.animocalendar

import android.app.DatePickerDialog
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var setDueDate: EditText
    private lateinit var mTaskNameEdit: EditText
    private lateinit var mTaskDescEdit: EditText
    private lateinit var mHostEt: EditText
    private lateinit var lowPrioRBtn: RadioButton
    private lateinit var midPrioRBtn: RadioButton
    private lateinit var highPrioRBtn: RadioButton
    private lateinit var urgPrioRBtn: RadioButton
    private lateinit var personalRBtn: RadioButton
    private lateinit var acadRBtn: RadioButton
    private lateinit var holidayRBtn: RadioButton
    private lateinit var mCreateBtn: Button
    private lateinit var firestore: FirebaseFirestore
    private var dueDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        setDueDate = findViewById(R.id.dateTv)
        mTaskNameEdit = findViewById(R.id.taskNameEt)
        mTaskDescEdit = findViewById(R.id.descriptionEt)
        mCreateBtn = findViewById(R.id.createBtn)
        mHostEt = findViewById(R.id.hostEt)
        lowPrioRBtn = findViewById(R.id.lowPrioRBtn)
        midPrioRBtn = findViewById(R.id.midPrioRBtn)
        highPrioRBtn = findViewById(R.id.highPrioRBtn)
        urgPrioRBtn = findViewById(R.id.urgPrioRBtn)
        personalRBtn = findViewById(R.id.personalRBtn)
        acadRBtn = findViewById(R.id.acadRBtn)
        holidayRBtn = findViewById(R.id.holidayRBtn)

        firestore = FirebaseFirestore.getInstance()

        mTaskNameEdit.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                mCreateBtn.isEnabled = charSequence.isNotBlank()
                mCreateBtn.setBackgroundColor(
                    resources.getColor(
                        if (charSequence.isNotBlank()) R.color.light_accent else android.R.color.darker_gray
                    )
                )
            }

            override fun afterTextChanged(editable: Editable) {}
        })

        setDueDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                { _: DatePicker, i: Int, i1: Int, i2: Int ->
                    var i = i
                    setDueDate.setText("$i2/${i1 + 1}/$i")
                    dueDate = "$i2/${i1 + 1}/$i"
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            datePickerDialog.show()
        }

        mCreateBtn.setOnClickListener {
            val task = mTaskNameEdit.text.toString()
            val desc = mTaskDescEdit.text.toString()
            val host = mHostEt.text.toString()
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
    }
    private fun getPriority(): String {
        // Determine which RadioButton is selected for priority
        return when {
            lowPrioRBtn.isChecked -> "Low"
            midPrioRBtn.isChecked -> "Medium"
            highPrioRBtn.isChecked -> "High"
            else -> "Urgent"
        }
    }

    private fun getCategory(): String {
        // Determine which RadioButton is selected for category
        return when {
            personalRBtn.isChecked -> "Personal"
            acadRBtn.isChecked -> "Academic"
            else -> "Holiday"
        }
    }
}
