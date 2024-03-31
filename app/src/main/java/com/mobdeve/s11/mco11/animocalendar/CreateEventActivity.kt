package com.mobdeve.s11.mco11.animocalendar

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityCreateEventBinding

class CreateEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateEventBinding
    private lateinit var cancelAction: Button
    private lateinit var doAction: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateEventBinding.inflate(layoutInflater)

        // Set theme for activity
        val themeEditor = getSharedPreferences("theme", MODE_PRIVATE)
        MainActivity.themeIndex = themeEditor.getInt("themeIndex", 0)
        setTheme(MainActivity.themesList[MainActivity.themeIndex])

        val view = binding.root
        setContentView(binding.root)


        cancelAction = findViewById(R.id.createEventCancel)
        doAction = findViewById(R.id.createEventSave)

        cancelAction.setOnClickListener{
            finish()
        }

        doAction.setOnClickListener{
            //send info to database
        }

    }
}
