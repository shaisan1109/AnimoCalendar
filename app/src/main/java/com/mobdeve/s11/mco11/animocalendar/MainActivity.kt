package com.mobdeve.s11.mco11.animocalendar

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityMainBinding

class MainActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityMainBinding

    // Themes list
    companion object {
        var themeIndex: Int = 0
        val themesList = arrayOf(R.style.Theme_AnimoCalendar, R.style.Dark, R.style.Halloween,
            R.style.Valentines, R.style.Christmas)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set theme for activity
        val themeEditor = getSharedPreferences("theme", MODE_PRIVATE)
        themeIndex = themeEditor.getInt("themeIndex", 0)
        setTheme(themesList[themeIndex])

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        allocateActivityTitle("Yearly View")
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }
}