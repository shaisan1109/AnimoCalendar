package com.mobdeve.s11.mco11.animocalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityEditEventBinding

class EditEventActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditEventBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditEventBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
