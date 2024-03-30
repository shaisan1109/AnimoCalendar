package com.mobdeve.s11.mco11.animocalendar

import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.app.ActivityOptionsCompat
import androidx.core.view.isVisible
import com.google.android.material.floatingactionbutton.FloatingActionButton

open class DrawerBaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

    // Floating action button
    private var isExpanded = false // to expand smaller floating buttons

    // FAB animations
    private val fromBottomFab : Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.from_bottom_fab)
    }
    private val toBottomFab : Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.to_bottom_fab)
    }
    private val rotateClockwise : Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_clockwise)
    }
    private val rotateCounter : Animation by lazy {
        AnimationUtils.loadAnimation(this, R.anim.rotate_counter_clockwise)
    }

    override fun setContentView(view: View) {
        drawerLayout = layoutInflater.inflate(R.layout.activity_drawer_base, null) as DrawerLayout
        val container: FrameLayout = drawerLayout.findViewById(R.id.activityContainer)
        container.addView(view)
        super.setContentView(drawerLayout)

        val toolbar: Toolbar = drawerLayout.findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val navigationView: NavigationView = drawerLayout.findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        // Floating action buttons
        val mainFab : FloatingActionButton = drawerLayout.findViewById(R.id.mainFab)
        val taskFab : FloatingActionButton = drawerLayout.findViewById(R.id.fabAddTask)
        val eventFab : FloatingActionButton = drawerLayout.findViewById(R.id.fabAddEvent)
        val taskPrev : Button = drawerLayout.findViewById(R.id.btnAddTask)
        val taskEvent : Button = drawerLayout.findViewById(R.id.btnAddEvent)

        // Floating action button onClick actions
        mainFab.setOnClickListener {
            // If FAB menu is expanded already, tapping main FAB again shrinks menu
            if(isExpanded) {
                spinCounterFab(mainFab, taskFab, eventFab, taskPrev, taskEvent)
            } else {
                spinClockFab(mainFab, taskFab, eventFab, taskPrev, taskEvent)
            }
        }
    }

    // FAB animation functions
    private fun spinCounterFab(fabObj : FloatingActionButton, subObj1: FloatingActionButton,
                               subObj2: FloatingActionButton, subBtn1: Button, subBtn2: Button) {
        fabObj.startAnimation(rotateCounter)

        subObj1.startAnimation(toBottomFab)
        subObj1.isVisible = false

        subObj2.startAnimation(toBottomFab)
        subObj2.isVisible = false

        subBtn1.startAnimation(toBottomFab)
        subBtn1.isVisible = false

        subBtn2.startAnimation(toBottomFab)
        subBtn2.isVisible = false

        isExpanded = !isExpanded // flip value to reset variable
    }

    private fun spinClockFab(fabObj : FloatingActionButton, subObj1: FloatingActionButton,
                             subObj2: FloatingActionButton, subBtn1: Button, subBtn2: Button) {
        fabObj.startAnimation(rotateClockwise)

        subObj1.startAnimation(fromBottomFab)
        subObj1.isVisible = true

        subObj2.startAnimation(fromBottomFab)
        subObj2.isVisible = true

        subBtn1.startAnimation(fromBottomFab)
        subBtn1.isVisible = true

        subBtn2.startAnimation(fromBottomFab)
        subBtn2.isVisible = true

        isExpanded = !isExpanded // flip value to reset variable
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        drawerLayout.closeDrawer(GravityCompat.START)
        when (item.itemId) {
            R.id.nav_year -> startActivityWithTransition(MainActivity::class.java)
            R.id.nav_month -> startActivityWithTransition(MonthActivity::class.java)
            R.id.nav_week -> startActivityWithTransition(WeekActivity::class.java)
            R.id.nav_day -> startActivityWithTransition(DayActivity::class.java)
            R.id.nav_help -> startActivityWithTransition(HelpActivity::class.java)
            R.id.nav_preferences -> startActivityWithTransition(PreferencesActivity::class.java)
            R.id.nav_change_theme -> startActivityWithTransition(ThemesActivity::class.java)
            R.id.nav_tasks -> startActivityWithTransition(TasksActivity::class.java)
        }
        return false
    }
    private fun startActivityWithTransition(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        val options = ActivityOptionsCompat.makeCustomAnimation(this, android.R.anim.fade_in, android.R.anim.fade_out)
        startActivity(intent, options.toBundle())
    }
    protected fun allocateActivityTitle(titleString: String) {
        supportActionBar?.title = titleString
    }
}
