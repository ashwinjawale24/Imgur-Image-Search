package com.prajwal.prajwalwaingankar_cavista.secondScreen

import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.bumptech.glide.Glide
import com.prajwal.prajwalwaingankar_cavista.R
import com.prajwal.prajwalwaingankar_cavista.model.RoomDB.CommentModel
import com.prajwal.prajwalwaingankar_cavista.model.RoomDB.Database
import kotlin.concurrent.thread

class KotlinSecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlinsecondscreen)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTheme)
        toolbar.setTitleTextColor(Color.WHITE)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setDisplayShowTitleEnabled(true)

        val url: String = intent.extras?.getString("url") ?: ""
        val image: ImageView = findViewById(R.id.imageView)
        val editText: EditText = findViewById(R.id.editTextComment)
        val button: Button = findViewById(R.id.button)

        val commentModel = CommentModel()
        val database = Database.getInstance(applicationContext)
        val uri = Uri.parse(url)
        Glide.with(this).load(uri).asBitmap().into(image)

        button.setOnClickListener { view ->
            val commentString = editText.text.toString()
            commentModel.setComment(commentString)
            commentModel.setLink(url)

            thread(isDaemon = true) { database.comment_dao().InsertComment(commentModel) }
            editText.setText("")
            Toast.makeText(this, "Comment stored successfully!", Toast.LENGTH_SHORT).show()
        }


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
