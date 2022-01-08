package com.example.pushrating.presentation.iap

import android.content.Context

class IAPDialogBuilder(private val context: Context) : IAPBuilder {
    private var duration = 1
    private var condition: IAPBuilder.ShowCondition? = null
    private var threshold: Long = 172800
    private var dontCountThisLaunch: Boolean = false

    override
    fun setDuration(duration: Int): IAPBuilder {
        this.duration = duration
        return this
    }

    override
    fun setShowCondition(condition: IAPBuilder.ShowCondition): IAPBuilder {
        this.condition = condition
        return this
    }

    override fun setThreshold(sec: Long): IAPBuilder {
        this.threshold = sec
        return this
    }

    override fun setDontCountThisLaunch(isCount: Boolean): IAPBuilder {
        this.dontCountThisLaunch = isCount
        return this
    }

    override fun build(onConfirmed: () -> Unit): IAPDialog {
        return IAPDialog(
            context = context,
            duration = duration,
            condition = condition,
            threshold = threshold,
            dontCountThisLaunch = dontCountThisLaunch,
            onConfirmed = onConfirmed
        )
    }
}