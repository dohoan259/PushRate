package com.example.pushrating.presentation.rating

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.pushrating.R
import com.example.pushrating.presentation.rating.RatingBuilder.ShowCondition
import com.example.pushrating.data.SettingRepo
import com.example.pushrating.databinding.DialogRatingBinding
import com.example.pushrating.util.openAppInStore
import java.util.*

class RatingDialog(
    private val context: Context,
    private val duration: Int = 1,
    private val minStar: Int = 4,
    private val threshold: Long = 86400,
    private var condition: ShowCondition?,
    private var dontCountThisLaunch: Boolean = false,
    private var indicator: Boolean = true,
    private var onPositiveClicked: (() -> Unit)? = null,
    private var onNegativeClicked: (() -> Unit)? = null,
    private var onNeutralClicked: (() -> Unit)? = null,
    private var onShow: (() -> Unit)? = null
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
        mBinding.ratingBar.setOnRatingBarChangeListener { _, rating, fromUser ->
            if (fromUser) {
                star = rating
            }
        }
        mBinding.ratingBar.setIsIndicator(indicator)
    }

    private val dialog = AlertDialog.Builder(context, R.style.RateDialogStyle)
        .setTitle(R.string.rating_dialog_title)
        .setView(mBinding.root)
        .setCancelable(false)
        .setPositiveButton(android.R.string.ok) { d, _ ->
            if (star >= minStar) {
                settingRepo.enableShow = false
                context.openAppInStore()
            }

            onPositiveClicked?.invoke()
            d.dismiss()
        }
        .setNegativeButton(context.getString(R.string.no_thanks)) { d, _ ->
            settingRepo.enableShow = false
            onNegativeClicked?.invoke()
            d.dismiss()
        }
        .setNeutralButton(context.getString(R.string.later)) { d, _ ->
            resetCondition()
            onNeutralClicked?.invoke()
            d.dismiss()
        }
        .create()

    private fun resetCondition() {
        resetDuration()
        resetThreshold()
    }

    fun showNow() {
        increaseDuration()
        dialog.show()
        onShow?.invoke()
    }

    fun showIfMeetsConditions() {
        Log.d(TAG, "show: ")
        val enableShow = settingRepo.enableShow
        Log.i(TAG, "enable: $enableShow")

        Log.i(TAG, "need condition: ${condition!!.needCondition()}")
        Log.i(TAG, "Duration: $duration")
        if (meetDuration()) {
            if (condition!!.needCondition() && meetThreshold() && enableShow) {
                dialog.show()
                onShow?.invoke()
            }
            resetDuration()
        } else {
            increaseDuration()
        }
    }

    private fun meetDuration(): Boolean {
        val newCount = settingRepo.callShowCount
        return newCount >= duration
    }

    private fun resetDuration() {
        settingRepo.callShowCount = 1
    }

    private fun increaseDuration() {
        var newCount = settingRepo.callShowCount
        newCount++
        if (!dontCountThisLaunch) {
            settingRepo.callShowCount = newCount
        }
    }

    private fun resetThreshold() {
        val currentDate = Calendar.getInstance().timeInMillis / 1000
        settingRepo.lastTimeMileStone = currentDate
    }

    private fun meetThreshold(): Boolean {
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

        return (currentDate - timeMileStone) > threshold
    }
}