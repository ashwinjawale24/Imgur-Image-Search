package com.prajwal.prajwalwaingankar_cavista.secondScreen

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.prajwal.prajwalwaingankar_cavista.R
import com.prajwal.prajwalwaingankar_cavista.firstScreen.ImageAdapter

class KotlinSecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlinsecondscreen)

        val url: String = intent.extras?.getString("url") ?: ""
        val image: ImageView = findViewById(R.id.imageView)
        val editText: EditText = findViewById(R.id.editTextComment)
        val button: Button = findViewById(R.id.button)
//        val imageAdapter = ImageAdapter(this, imageUrlsList)
//
        val uri = Uri.parse(url)
        Glide.with(this).load(uri).asBitmap().into(image)
//
//        image.setImageResource(imageAdapter.imageIds[position])



    }
}
