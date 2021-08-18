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

    private var star = 5f
    private val settingRepo = SettingRepo(context)

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
            //todo: not show any more
            d.dismiss()
        }
        .setNeutralButton(context.getString(R.string.later)) { d, _ ->
            d.dismiss()
        }
        .create()

    fun show() {
        var newCount = settingRepo.callShowCount
                if (settingRepo.callShowCount >= duration) {
                    if (condition!!.needCondition()) {
                        dialog.show()
                    }
                    newCount = 1
                } else {
                    newCount++
                }
                settingRepo.callShowCount = newCount
        }
}