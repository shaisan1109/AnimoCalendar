package com.mobdeve.s11.mco11.animocalendar

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityThemesBinding

class ThemesActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityThemesBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityThemesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        allocateActivityTitle("Themes")
        enableEdgeToEdge()
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}