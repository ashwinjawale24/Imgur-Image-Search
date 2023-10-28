package com.ashwin.thoughtctl.DetailScreen

import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.ashwin.thoughtctl.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class KotlinSecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlinsecondscreen)

        val image: ImageView = findViewById(R.id.imageView)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        val txtdatetime: TextView = findViewById(R.id.txtdatetime)
        val txtTitle: TextView = findViewById(R.id.txtTitle)

        val url: String = intent.extras?.getString("url") ?: ""
        val imageTitle: String = intent.extras?.getString("title") ?: "No Title"
        val imagedatetime: String = intent.extras?.getString("datetime") ?: "No Data"
        Log.d("lgTitle", imageTitle)
        Log.d("lgdatatime", imagedatetime)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val secondApiFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
        val timestamp = imagedatetime.toLong() // timestamp in Long
        val timestampAsDateString = java.time.format.DateTimeFormatter.ISO_INSTANT
            .format(java.time.Instant.ofEpochSecond(timestamp))
        Log.d("parseTesting", timestampAsDateString) // prints 2019-08-07T20:27:45Z
        val date = LocalDate.parse(timestampAsDateString, secondApiFormat)
        Log.d("parseTesting", date.dayOfWeek.toString()) // prints Wednesday
        Log.d("parseTesting", date.month.toString())
            txtdatetime.text = date.dayOfWeek.toString()+", "+date
    }

        txtTitle.text = imageTitle
        toolbar.title = imageTitle
        setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTheme)
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        val uri = Uri.parse(url)
        Glide.with(this).load(uri).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(image)


    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId == android.R.id.home)
        {
            finish()
            return true
        }


        return super.onOptionsItemSelected(item)
    }


}
