package com.example.pushrating

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.pushrating.RatingBuilder.ShowCondition
import com.example.pushrating.data.SettingRepo
import com.example.pushrating.databinding.DialogRatingBinding
import com.example.pushrating.util.openAppInStore
import java.util.*

class RatingDialog(
    private val context: Context,
    private val duration: Int = 1,
    private val minStar: Int = 4,
    private val threshold: Long = 86400,
    private var condition: ShowCondition?

) {

    private val mBinding = DialogRatingBinding.inflate(LayoutInflater.from(context), null, false)
    private var star = 5f
    private val settingRepo = SettingRepo(context)

    companion object {
        private const val TAG = "RatingDialog"
    }

    init {
        if (condition == null) {
            condition = object : ShowCondition {
                override fun needCondition(): Boolean = true
            }
        }
        mBinding.ratingBar.setOnRatingChangeListener { _, rating, fromUser ->
            if (fromUser) {
                star = rating
            }
        }
    }

    private val dialog = AlertDialog.Builder(context, R.style.RateDialogStyle)
        .setTitle(R.string.rating_dialog_title)
        .setView(mBinding.root)
        .setCancelable(false)
        .setPositiveButton(android.R.string.ok) { d, _ ->
            if (star >= minStar) {
                context.openAppInStore()
            }
            settingRepo.enableShow = false
            d.dismiss()
        }
        .setNegativeButton(context.getString(R.string.no_thanks)) { d, _ ->
            settingRepo.enableShow = false
            d.dismiss()
        }
        .setNeutralButton(context.getString(R.string.later)) { d, _ ->
            d.dismiss()
        }
        .create()

    fun show(shouldCount: Boolean = true) {
        Log.d(TAG, "show: ")

        var newCount = settingRepo.callShowCount
        Log.i(TAG, "count: $newCount")
        val enableShow = settingRepo.enableShow
        Log.i(TAG, "enable: $enableShow")
        val currentDate = Calendar.getInstance().timeInMillis / 1000

        val oldThreshold = settingRepo.timeThreshold
        if (oldThreshold != threshold) {
            settingRepo.timeThreshold = threshold
        }
        Log.i(TAG, "threshold: $threshold")
        var timeMileStone = settingRepo.lastTimeMileStone
        Log.i(TAG, "Milestone: $timeMileStone")
        if (timeMileStone == -1L) {
            // First time
            settingRepo.lastTimeMileStone = currentDate
            timeMileStone = currentDate
        }

        val afterThreshold = (currentDate - timeMileStone) > threshold
        Log.i(TAG, "After threshold: $afterThreshold")
        Log.i(TAG, "need condition: ${condition!!.needCondition()}")
        Log.i(TAG, "Duration: $duration")
        if (newCount >= duration) {
            if (condition!!.needCondition() && afterThreshold) {
                if ((shouldCount && enableShow) || (!shouldCount)) {
                    dialog.show()
                }
            }
            newCount = 1
        } else {
            newCount++
        }
        if (shouldCount) {
            settingRepo.callShowCount = newCount
        }
    }
}