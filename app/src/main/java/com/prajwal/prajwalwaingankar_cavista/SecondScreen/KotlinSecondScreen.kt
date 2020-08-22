package com.prajwal.prajwalwaingankar_cavista.SecondScreen

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.prajwal.prajwalwaingankar_cavista.R

class KotlinSecondScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_kotlinsecondscreen)

        val int: Int = intent.extras!!.getInt("id")
    }
}
