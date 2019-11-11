package com.example.mealme.net

import android.content.Context
import android.graphics.Bitmap
import android.os.AsyncTask
import android.graphics.BitmapFactory
import android.util.Log
import java.net.URL
import android.widget.ImageView
import java.io.FileOutputStream


class ImageDownloader(): AsyncTask<String, Void, ByteArray>() {

    private val TAG = this.javaClass.name


    override fun doInBackground(vararg params: String): ByteArray? {
//        var bitmap: Bitmap? = null
        var bytes: ByteArray? = null
        try {
            val inputStream = URL(params[0]).openStream()       // Download Image from URL
//            bitmap = BitmapFactory.decodeStream(inputStream)    // Decode Bitmap
            bytes = inputStream.readBytes()
            inputStream.close()
        } catch (e: Exception) {
            Log.d(TAG, "Exception 1, Something went wrong!")
            e.printStackTrace()
        }

//        return bitmap
        return bytes
    }

//    override fun onPostExecute(result: String) {
//        val foStream: FileOutputStream
//        try {
//
//            foStream = context.openFileOutput(imageName, Context.MODE_PRIVATE)
//            b.compress(Bitmap.CompressFormat.PNG, 100, foStream)
//            foStream.close()
//        } catch (e: Exception) {
//            Log.d("saveImage", "Exception 2, Something went wrong!")
//            e.printStackTrace()
//        }
//
//        saveImage(getApplicationContext<Context>(), result, "my_image.png")
//        imageView.setImageBitmap(result)
//    }
}