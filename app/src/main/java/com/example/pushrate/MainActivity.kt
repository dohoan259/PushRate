package com.example.pushrate

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.pushrating.presentation.iap.IAPDialogBuilder
import com.example.pushrating.presentation.rating.RatingDialogBuilder

class MainActivity : AppCompatActivity() {

    companion object {
        val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val ratingDialog =
            RatingDialogBuilder(this).setDuration(2).setMinStar(4).setThreshold(30).build()
        ratingDialog.showNow()

        val iapDialog = IAPDialogBuilder(this).build {
            Log.d(TAG, "confirm pay IAP")
        }

        findViewById<Button>(R.id.btn_show_dialog).setOnClickListener {
            iapDialog.showNow()
        }
    }
}