package com.mobdeve.s11.mco11.animocalendar

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageSwitcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityCreateEventBinding
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*


class CreateEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEventBinding
    private lateinit var cancelAction: Button
    private lateinit var doAction: Button
    private lateinit var eventName: EditText
    private lateinit var startDate: EditText
    private lateinit var endDate: EditText
    private lateinit var startTime: EditText
    private lateinit var endTime: EditText
    private lateinit var hostName: EditText
    private lateinit var hostPfp: ImageSwitcher
    private lateinit var eventDesc: EditText
    private lateinit var eventLoc: EditText
    private lateinit var cateButton: Button
    private lateinit var repeatButt: Button
    private lateinit var firestore: FirebaseFirestore
    private var dueDate: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)

        // Set theme for activity
        val themeEditor = getSharedPreferences("theme", MODE_PRIVATE)
        MainActivity.themeIndex = themeEditor.getInt("themeIndex", 0)
        setTheme(MainActivity.themesList[MainActivity.themeIndex])

        val view = binding.root
        setContentView(binding.root)

        eventName = findViewById(R.id.createEventName)
        //startDate = findViewById(R.id.createEventStartDate)
        //endDate = findViewById(R.id.createEventEndDate)
        //startTime = findViewById(R.id.createEventStartTime)
        //endTime = findViewById(R.id.createEventEndTime)
        hostName = findViewById(R.id.createEventUsername)
        //hostPfp = findViewById(R.id.createEventUser)
        eventDesc = findViewById(R.id.createEventDesc)
        eventLoc = findViewById(R.id.createEventLoc)
        cateButton = findViewById(R.id.createEventCat)
        repeatButt = findViewById(R.id.createEventRepeat)
        firestore = FirebaseFirestore.getInstance()

        eventName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                eventName.isEnabled = charSequence.isNotBlank()
                eventName.setBackgroundColor(
                    resources.getColor(
                        if (charSequence.isNotBlank()) R.color.light_accent else android.R.color.darker_gray
                    )
                )
            }

            override fun afterTextChanged(editable: Editable) {}
        })





        cancelAction = findViewById(R.id.createEventCancel)
        doAction = findViewById(R.id.createEventSave)

        cancelAction.setOnClickListener {
            finish()
        }

        doAction.setOnClickListener {
            val event = eventName.text.toString()
            val desc = eventDesc.text.toString()
            val host = hostName.text.toString()
            //val priority = getPriority()
            //val category = getCategory()

            if (event.isEmpty()) {
                Toast.makeText(this, "Event name cannot be blank", Toast.LENGTH_SHORT).show()
            } else {
                val eventMap = hashMapOf<String, Any>()
                eventMap["event"] = event
                eventMap["due"] = dueDate
                eventMap["description"] = desc
                eventMap["status"] = 0
                eventMap["time"] = FieldValue.serverTimestamp()
                eventMap["host"] = host
                //eventMap["priority"] = priority
                //eventMap["category"] = category

                firestore.collection("event").add(eventMap)
                    .addOnCompleteListener { event ->
                        if (event.isSuccessful) {
                            Toast.makeText(this, "Event Saved", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, event.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }.addOnFailureListener { e ->
                        Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
                    }
            }

        }
    }

}
