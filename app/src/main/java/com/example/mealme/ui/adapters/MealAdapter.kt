package com.example.mealme.ui.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mealme.R
import com.example.mealme.model.Meal
import kotlinx.coroutines.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL
import kotlin.coroutines.CoroutineContext


class MealAdapter(val mealList: ArrayList<Meal>) :
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
        RecyclerView.ViewHolder(inflater.inflate(R.layout.meal_item, parent, false)), CoroutineScope {

        val TAG = this.javaClass.name

//        private var job = Job()

        override val coroutineContext: CoroutineContext
            get() = Dispatchers.Main
        //+ job


        fun bind(meal: Meal) {
            val title = itemView.findViewById<TextView>(R.id.meal_item_title)
            val instructions = itemView.findViewById<TextView>(R.id.meal_item_instructions)
            val image = itemView.findViewById<ImageView>(R.id.meal_item_image)

            title.text = meal.name
            if (meal.instructions.length > 150)
                instructions.text = meal.instructions.take(150).plus("...")
            else
                instructions.text = meal.instructions

            launch {
                fetchImage(image, meal.id, meal.thumbURL)
            }
        }

        suspend fun fetchImage(image: ImageView, imageID: Int, url: String) {
            var bitmap: Bitmap? = null

            // Create image name to local store
            val filename = imageID.toString() + url.takeLastWhile { it != '/' }
            val file = File(image.context.filesDir, filename)

            withContext(Dispatchers.IO) {
                try {
                    if (file.exists()) {
                        val inputStream = FileInputStream(file)
                        bitmap = BitmapFactory.decodeStream(inputStream)
                        inputStream.close()
                    } else {
                        val inputStream = URL(url).openStream()
                        bitmap = BitmapFactory.decodeStream(inputStream)
                        val outputStrem = FileOutputStream(file)
                        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStrem)
                        outputStrem.apply {
                            flush()
                            close()
                        }
                        inputStream.close()
                    }
                    image.setImageBitmap(bitmap)
                } catch (e: Exception) {
                    Log.e(">>>>>>>", "Exception 1, Something went wrong!")
                    e.printStackTrace()
                }
            }
        }
    }
}