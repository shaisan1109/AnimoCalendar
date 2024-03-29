package com.mobdeve.s11.mco11.animocalendar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewThemeAdapter (private val getActivity: ThemesActivity, private val themeList : List<Theme>) :
    RecyclerView.Adapter<RecyclerViewThemeAdapter.ThemesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ThemesViewHolder {
        // Set layout for theme card
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_theme, parent, false)

        return ThemesViewHolder(view)
    }

    override fun getItemCount(): Int {
        return themeList.size
    }

    override fun onBindViewHolder(holder: ThemesViewHolder, position: Int) {
        // Bind values to card
        holder.themeName.text = themeList[position].name
        holder.themeImg.setImageResource(themeList[position].image)

        // Each button sets the theme index through its ID
        holder.themeCard.setOnClickListener {
            holder.prefs.edit().putInt("themeIndex", themeList[position].id).apply()

            // Restart activity after appearance is set
            //finish()
            //startActivity(intent)
        }
    }

    class ThemesViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val themeName : TextView = itemView.findViewById(R.id.themeName)
        val themeImg : ImageView = itemView.findViewById(R.id.themeImg)
        val themeCard : CardView = itemView.findViewById(R.id.themeCard)

        val prefs = itemView.context.getSharedPreferences("theme", Context.MODE_PRIVATE)
    }

//    private fun saveTheme(index: Int) {
//        val editor = getSharedPreferences("Themes", AppCompatActivity.MODE_PRIVATE).edit()
//        editor.putInt("themeIndex", index)
//
//        // Restart activity after appearance is set
//        finish()
//        startActivity(intent)
//    }
}