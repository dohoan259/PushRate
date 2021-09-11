package com.example.pushrating

interface RatingBuilder {

    fun setDuration(duration: Int): RatingBuilder

    fun setShowCondition(condition: ShowCondition): RatingBuilder

    fun setMinStar(star: Int): RatingBuilder

    fun setThreshold(sec: Long): RatingBuilder
    fun build(): RatingDialog

    interface ShowCondition {
        fun needCondition(): Boolean
    }
}