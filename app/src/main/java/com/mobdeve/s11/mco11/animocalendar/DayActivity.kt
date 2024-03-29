package com.mobdeve.s11.mco11.animocalendar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityDayBinding

class DayActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityDayBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDayBinding.inflate(layoutInflater)

        // Set theme for activity
        val themeEditor = getSharedPreferences("theme", MODE_PRIVATE)
        MainActivity.themeIndex = themeEditor.getInt("themeIndex", 0)
        setTheme(MainActivity.themesList[MainActivity.themeIndex])

        setContentView(binding.root)
        allocateActivityTitle("Daily View")
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}