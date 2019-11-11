package com.example.mealme.ui.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealme.R
import com.example.mealme.model.Meal
import com.example.mealme.net.ImageDownloader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.URL


class MealAdapter(private val mealList: ArrayList<Meal>) :
    RecyclerView.Adapter<MealAdapter.MealViewHolder>() {

    val TAG = this.javaClass.name

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealViewHolder {

        return MealViewHolder(LayoutInflater.from(parent.context), parent)
    }

    override fun getItemCount(): Int = mealList.size

    override fun onBindViewHolder(holder: MealViewHolder, position: Int) {
        holder.bind(mealList[position])
    }


    /**
     * ViewHolder
     */
    class MealViewHolder(inflater: LayoutInflater, val parent: ViewGroup) :
        RecyclerView.ViewHolder(inflater.inflate(R.layout.meal_item, parent, false)) {

        fun bind(meal: Meal) {
            val title = itemView.findViewById<TextView>(R.id.meal_item_title)
            val instructions = itemView.findViewById<TextView>(R.id.meal_item_instructions)
            val image = itemView.findViewById<ImageView>(R.id.meal_item_image)
            title.text = meal.name
            instructions.text = meal.instructions
            ImageDownloader().execute(meal.thumbURL)


        }

        suspend fun fetchImage(url: String) : Bitmap? {
            var bitmap: Bitmap? = null

            withContext(Dispatchers.IO) {
                try {
                    val inputStream = URL(url).openStream()       // Download Image from URL
                    bitmap = BitmapFactory.decodeStream(inputStream)    // Decode Bitmap

                    inputStream.close()
                } catch (e: Exception) {
                    Log.e(">>>>>>>", "Exception 1, Something went wrong!")
                    e.printStackTrace()
                }

            }

            return bitmap
        }
    }
}