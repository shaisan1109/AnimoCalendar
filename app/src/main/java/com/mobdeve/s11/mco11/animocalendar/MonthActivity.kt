package com.mobdeve.s11.mco11.animocalendar

import android.os.Bundle
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList
import com.mobdeve.s11.mco11.animocalendar.databinding.ActivityMonthBinding

class MonthActivity : DrawerBaseActivity(), MyAdapter.OnItemListener {

    private lateinit var binding: ActivityMonthBinding
    private lateinit var monthYearText: TextView
    private lateinit var calendarRecyclerView: RecyclerView
    private lateinit var selectedDate: Calendar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMonthBinding.inflate(layoutInflater)

        // Set theme for activity
        val themeEditor = getSharedPreferences("theme", MODE_PRIVATE)
        MainActivity.themeIndex = themeEditor.getInt("themeIndex", 0)
        setTheme(MainActivity.themesList[MainActivity.themeIndex])

        setContentView(binding.root)
        allocateActivityTitle("Month View")

        initWidgets()
        selectedDate = Calendar.getInstance()
        setMonthView()
    }

    private fun initWidgets() {
        calendarRecyclerView = binding.calendarRecyclerView
        monthYearText = binding.monthYearTV
    }

    private fun setMonthView() {
        monthYearText.text = monthYearFromDate(selectedDate)
        val daysInMonth = daysInMonthArray(selectedDate)

        val myAdapter = MyAdapter(daysInMonth, this)
        val layoutManager = GridLayoutManager(this, 7)
        calendarRecyclerView.layoutManager = layoutManager
        calendarRecyclerView.adapter = myAdapter
    }

    private fun daysInMonthArray(date: Calendar): ArrayList<String> {
        val daysInMonthArray = ArrayList<String>()
        val year = date.get(Calendar.YEAR)
        val month = date.get(Calendar.MONTH)

        val cal = Calendar.getInstance()
        cal.set(year, month, 1)
        val daysInMonth = cal.getActualMaximum(Calendar.DAY_OF_MONTH)
        val dayOfWeek = cal.get(Calendar.DAY_OF_WEEK)

        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) {
                daysInMonthArray.add("")
            } else {
                daysInMonthArray.add((i - dayOfWeek).toString())
            }
        }
        return daysInMonthArray
    }

    private fun monthYearFromDate(date: Calendar): String {
        val formatter = SimpleDateFormat("MMMM yyyy", Locale.getDefault())
        return formatter.format(date.time)
    }

    override fun onItemClick(position: Int, dayText: String) {
        if (dayText != "") {
            val message = "Selected Date $dayText ${monthYearFromDate(selectedDate)}"
            Toast.makeText(this, message, Toast.LENGTH_LONG).show()
        }
    }

    fun previousMonthAction(view: View) {
        selectedDate.add(Calendar.MONTH, -1)
        setMonthView()
    }

    fun nextMonthAction(view: View) {
        selectedDate.add(Calendar.MONTH, 1)
        setMonthView()
    }
}
