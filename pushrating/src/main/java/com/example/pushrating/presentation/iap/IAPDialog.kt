package com.example.pushrating.presentation.iap

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AlertDialog
import com.example.pushrating.R
import com.example.pushrating.data.IAPRepo
import com.example.pushrating.databinding.DialogIapBinding
import java.util.*

class IAPDialog(
    private val context: Context,
    private val duration: Int = 1,
    private val threshold: Long = 172800,
    private var condition: IAPBuilder.ShowCondition?,
    private var dontCountThisLaunch: Boolean = false,
    private val price: String = "5$",
    private val onConfirmed: () -> Unit
) {

    private val mBinding = DialogIapBinding.inflate(LayoutInflater.from(context), null, false)
    private val iapRepo = IAPRepo(context)

    companion object {
        private const val TAG = "IAPDialog"
    }

    init {
        if (condition == null) {
            condition = object : IAPBuilder.ShowCondition {
                override fun needCondition(): Boolean = true
            }
        }
        val priceStr = context.resources.getString(R.string.five_dollars, price)
        mBinding.tvPrice.text = priceStr
        val animation = AnimationUtils.loadAnimation(context, R.anim.vip_icon_anim)
        mBinding.tvPrice.startAnimation(animation)
    }

    private val dialog = AlertDialog.Builder(context, R.style.RateDialogStyle)
        .setTitle(R.string.iap_dialog_title)
        .setView(mBinding.root)
        .setCancelable(false)
        .setPositiveButton(android.R.string.ok) { d, _ ->
            onConfirmed.invoke()
            d.dismiss()
        }
        .setNegativeButton(context.getString(R.string.no_thanks)) { d, _ ->
            iapRepo.enableShow = false
            d.dismiss()
        }
        .setNeutralButton(context.getString(R.string.later)) { d, _ ->
            resetCondition()
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
    }

    fun showIfMeetsConditions() {
        Log.d(TAG, "show: ")
        val enableShow = iapRepo.enableShow

        Log.i(TAG, "need condition: ${condition!!.needCondition()}")
        Log.i(TAG, "Duration: $duration")
        if (meetDuration()) {
            if (condition!!.needCondition() && meetThreshold() && enableShow) {
                dialog.show()
            }
            resetDuration()
        } else {
            increaseDuration()
        }
    }

    private fun meetDuration(): Boolean {
        val newCount = iapRepo.callShowCount
        return newCount >= duration
    }

    private fun resetDuration() {
        iapRepo.callShowCount = 1
    }

    private fun increaseDuration() {
        var newCount = iapRepo.callShowCount
        newCount++
        if (!dontCountThisLaunch) {
            iapRepo.callShowCount = newCount
        }
    }

    private fun resetThreshold() {
        val currentDate = Calendar.getInstance().timeInMillis / 1000
        iapRepo.lastTimeMileStone = currentDate
    }

    private fun meetThreshold(): Boolean {
        val currentDate = Calendar.getInstance().timeInMillis / 1000

        val oldThreshold = iapRepo.timeThreshold
        if (oldThreshold != threshold) {
            iapRepo.timeThreshold = threshold
        }
        Log.i(TAG, "threshold: $threshold")
        var timeMileStone = iapRepo.lastTimeMileStone
        Log.i(TAG, "Milestone: $timeMileStone")
        if (timeMileStone == -1L) {
            // First time
            iapRepo.lastTimeMileStone = currentDate
            timeMileStone = currentDate
        }

        return (currentDate - timeMileStone) > threshold
    }
}
