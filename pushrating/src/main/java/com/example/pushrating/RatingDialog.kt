package com.example.pushrating

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.example.pushrating.RatingBuilder.ShowCondition
import com.example.pushrating.data.SettingRepo
import com.example.pushrating.databinding.DialogRatingBinding
import com.example.pushrating.util.openAppInStore

class RatingDialog (
    private val context: Context,
    private val duration: Int = 1,
    private val minStar: Int = 4,
    private var condition: ShowCondition?
) {

    private val mBinding = DialogRatingBinding.inflate(LayoutInflater.from(context), null, false)
    private var star = 5f
    private val settingRepo = SettingRepo(context)

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
        settingRepo.callShowCount = 1
    }

    private val dialog = AlertDialog.Builder(context)
        .setTitle(R.string.rating_dialog_title)
        .setView(mBinding.root)
        .setPositiveButton(android.R.string.ok) { d, _ ->
            if (star >= minStar) {
                context.openAppInStore()
            }
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
        var newCount = settingRepo.callShowCount
        val enableShow = settingRepo.enableShow
        if (settingRepo.callShowCount >= duration) {
            if (condition!!.needCondition()) {
                if ((shouldCount && enableShow) || (!shouldCount)){
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