package com.mobdeve.s11.mco11.animocalendar

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityViewTaskBinding

class ViewTaskActivity : AppCompatActivity() {

    private lateinit var binding: ActivityViewTaskBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityViewTaskBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}
