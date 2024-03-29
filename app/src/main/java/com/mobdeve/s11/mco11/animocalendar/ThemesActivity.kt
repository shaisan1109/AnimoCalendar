package com.mobdeve.s11.mco11.animocalendar

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityThemesBinding

class ThemesActivity : DrawerBaseActivity() {
    private lateinit var binding: ActivityThemesBinding

    // Recycler view variables
    private var recyclerView : RecyclerView? = null
    private var recyclerViewThemeAdapter : RecyclerViewThemeAdapter? = null
    private var themeList = mutableListOf<Theme>()

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

        // Recycler view implementation
        themeList = ArrayList()
        recyclerView = findViewById<View>(R.id.rvThemeList) as RecyclerView
        recyclerViewThemeAdapter = RecyclerViewThemeAdapter(this, themeList)

        // Renders recycler view with two columns
        val layoutManager : RecyclerView.LayoutManager = GridLayoutManager(this, 2)

        // Set layout manager and adapter
        recyclerView!!.layoutManager = layoutManager
        recyclerView!!.adapter = recyclerViewThemeAdapter

        prepareThemeListData()
    }

    // Not exactly the best function atm; replace with data helper or DB data later
    private fun prepareThemeListData() {
        var theme = Theme("light", "Light Mode", R.drawable.theme_light)
        themeList.add(theme)

        theme = Theme("dark", "Dark Mode", R.drawable.theme_dark)
        themeList.add(theme)

        theme = Theme("halloween", "Halloween", R.drawable.theme_halloween)
        themeList.add(theme)

        theme = Theme("valentines", "Valentines", R.drawable.theme_valentines)
        themeList.add(theme)

        theme = Theme("xmas", "Christmas", R.drawable.theme_xmas)
        themeList.add(theme)

        recyclerViewThemeAdapter!!.notifyDataSetChanged()
    }
}