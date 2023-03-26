package com.example.pushrating.presentation.rating

import android.content.Context

class RatingDialogBuilder(
    private val context: Context,
    private var onPositiveClicked: (() -> Unit)? = null,
    private var onNegativeClicked: (() -> Unit)? = null,
    private var onNeutralClicked: (() -> Unit)? = null,
    private var onShow: (() -> Unit)? = null
) : RatingBuilder {
    private var duration = 1
    private var condition: RatingBuilder.ShowCondition? = null
    private var minStar: Int = 4
    private var threshold: Long = 86400
    private var dontCountThisLaunch: Boolean = false
    private var indicator = true

    override
    fun setDuration(duration: Int): RatingBuilder {
        this.duration = duration
        return this
    }

    override
    fun setShowCondition(condition: RatingBuilder.ShowCondition): RatingBuilder {
        this.condition = condition
        return this
    }

    override
    fun setMinStar(star: Int): RatingBuilder {
        this.minStar = star
        return this
    }

    override fun setThreshold(sec: Long): RatingBuilder {
        this.threshold = sec
        return this
    }

    override fun setDontCountThisLaunch(isCount: Boolean): RatingBuilder {
        this.dontCountThisLaunch = isCount
        return this
    }

    override fun setIndicator(isIndicator: Boolean): RatingBuilder {
        this.indicator = isIndicator
        return this
    }

    override fun build(): RatingDialog {
        return RatingDialog(
            context = context,
            duration = duration,
            minStar = minStar,
            condition = condition,
            threshold = threshold,
            dontCountThisLaunch = dontCountThisLaunch,
            indicator = indicator,
            onNegativeClicked = this.onNegativeClicked,
            onNeutralClicked = this.onNeutralClicked,
            onPositiveClicked = this.onPositiveClicked,
            onShow = this.onShow
        )
    }
}