package com.prajwal.prajwalwaingankar_cavista.secondScreen

import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.prajwal.prajwalwaingankar_cavista.R
import com.prajwal.prajwalwaingankar_cavista.firstScreen.ImageAdapter
import com.prajwal.prajwalwaingankar_cavista.model.RoomDB.CommentModel
import com.prajwal.prajwalwaingankar_cavista.model.RoomDB.Database
import kotlin.concurrent.thread

class KotlinSecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlinsecondscreen)

        val url: String = intent.extras?.getString("url") ?: ""
        val image: ImageView = findViewById(R.id.imageView)
        val editText: EditText = findViewById(R.id.editTextComment)
        val button: Button = findViewById(R.id.button)

        val commentModel = CommentModel()
        val database = Database.getInstance(applicationContext)

//        val imageAdapter = ImageAdapter(this, imageUrlsList)
//
        val uri = Uri.parse(url)
        Glide.with(this).load(uri).asBitmap().into(image)
//
//        image.setImageResource(imageAdapter.imageIds[position])

        button.setOnClickListener { view ->
            val commentString = editText.text.toString()
            commentModel.setComment(commentString)
            commentModel.setLink(url)

            thread(isDaemon = true) { database.comment_dao().InsertComment(commentModel) }
            editText.setText("")
            Toast.makeText(this, "Comment stored successfully!", Toast.LENGTH_SHORT).show()
        }


    }
}
