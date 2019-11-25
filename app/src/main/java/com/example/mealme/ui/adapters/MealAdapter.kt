package com.example.mealme.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.mealme.R
import com.example.mealme.model.Meal


class MealAdapter(val mealList: ArrayList<Meal>, val onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    val TAG = this.javaClass.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.meal_item, parent, false)

        return MealViewHolder(view)
    }

    override fun getItemCount(): Int = mealList.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(mealList[position], onItemClickListener)
    }


    /**
     * ViewHolder
     */
    class MealViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val TAG = this.javaClass.name

        fun bind(meal: Meal, onClickListener: OnItemClickListener) {
            val title = itemView.findViewById<TextView>(R.id.meal_item_title)
            val category = itemView.findViewById<TextView>(R.id.meal_item_category)
            val tags = itemView.findViewById<TextView>(R.id.meal_item_tags)
            val image = itemView.findViewById<ImageView>(R.id.meal_item_image)

            title.text = meal.name
            category.text = meal.category
            tags.text = meal.tags

            Glide.with(itemView)
                .load(meal.thumbURL)
                .placeholder(R.drawable.ic_image)
                .into(image)

            itemView.setOnClickListener {
                onClickListener.OnItemClick(meal)
            }
        }
    }

    /**
     * OnClick Interface
     */
    interface OnItemClickListener {
        fun OnItemClick(item: Meal)
    }
}