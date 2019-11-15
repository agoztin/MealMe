package com.example.mealme.util

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.net.URL

object ImageFetcher {

    suspend fun get(imageView: ImageView, url: String, localFilename: String) {
        var bitmap: Bitmap? = null

        // Create image name to local store
        val file = File(imageView.context.filesDir, localFilename)

        withContext(Dispatchers.IO) {
            try {
                if (file.exists() && file.length() > 0) {
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
            } catch (e: Exception) {
                Log.e(">>>>>>>", "Exception 1, Something went wrong!")
                e.printStackTrace()
            }
        }

        imageView.setImageBitmap(bitmap)
    }
}