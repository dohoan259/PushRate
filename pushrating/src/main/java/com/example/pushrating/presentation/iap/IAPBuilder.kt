package com.example.pushrating.presentation.iap

interface IAPBuilder {

    fun setDuration(duration: Int): IAPBuilder

    fun setShowCondition(condition: ShowCondition): IAPBuilder

    fun setThreshold(sec: Long): IAPBuilder

    fun setDontCountThisLaunch(isCount: Boolean): IAPBuilder

    fun build(onConfirmed: () -> Unit): IAPDialog

    interface ShowCondition {
        fun needCondition(): Boolean
    }
}