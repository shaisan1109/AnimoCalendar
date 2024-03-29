package com.mobdeve.s11.mco11.animocalendar

import android.content.Intent
import android.view.MenuItem
import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView
import androidx.core.app.ActivityOptionsCompat

open class DrawerBaseActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawerLayout: DrawerLayout

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
