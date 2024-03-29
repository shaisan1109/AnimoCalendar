package com.mobdeve.s11.mco11.animocalendar

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
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

        holder.themeCard.setOnClickListener {
            // TODO: Add function to change theme upon tapping option
        }
    }

    class ThemesViewHolder (itemView : View) : RecyclerView.ViewHolder(itemView) {
        val themeName : TextView = itemView.findViewById(R.id.themeName)
        val themeImg : ImageView = itemView.findViewById(R.id.themeImg)
        val themeCard : CardView = itemView.findViewById(R.id.themeCard)
    }

}