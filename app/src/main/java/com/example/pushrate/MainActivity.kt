package com.example.pushrate

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pushrating.RatingDialogBuilder

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ratingDialog =
            RatingDialogBuilder(this).setDuration(2).setMinStar(4).setThreshold(30).build()
        ratingDialog.showNow()
        findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {
            ratingDialog.showIfMeetsConditions()
        }
    }
}