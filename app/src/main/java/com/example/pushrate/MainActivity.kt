package com.example.pushrate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.pushrating.RatingBuilderDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ratingDialog = RatingBuilderDialog(this).setDuration(1).setMinStar(4).build()

        findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {
            ratingDialog.show(false)
        }
    }
}