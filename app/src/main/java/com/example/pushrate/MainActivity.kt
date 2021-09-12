package com.example.pushrate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pushrating.RatingDialgoBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ratingDialog =
            RatingDialgoBuilder(this).setDuration(2).setMinStar(4).setThreshold(30).build()
        ratingDialog.show(true)
        findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {
            ratingDialog.show(false)
        }
    }
}