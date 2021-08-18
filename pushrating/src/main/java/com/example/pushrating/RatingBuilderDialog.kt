package com.example.pushrating

import android.content.Context

class RatingBuilderDialog(private val context: Context) : RatingBuilder{
    private var duration = 1
    private var condition: RatingBuilder.ShowCondition? = null
    private var minStar: Int = 4

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

    override fun build(): RatingDialog {
        return RatingDialog(
            context = context,
            duration = duration,
            minStar = minStar,
            condition = condition
        )
    }
}