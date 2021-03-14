package com.erge.animatorview.transition

import android.animation.Animator
import android.animation.ValueAnimator
import android.view.ViewGroup
import androidx.transition.Transition
import androidx.transition.TransitionValues

/**
 * Created by erge 3/12/21 3:23 PM
 */
class CustomTransition : Transition() {

    private val KEY = "customer_translate"

    override fun captureStartValues(transitionValues: TransitionValues) {
        saveValue(transitionValues)
    }

    override fun captureEndValues(transitionValues: TransitionValues) {
        saveValue(transitionValues)
    }

    private fun saveValue(values: TransitionValues) {
        values.values[KEY] = values.view.translationY
        println("values = ${values.view.translationY}")
    }

    override fun createAnimator(
        sceneRoot: ViewGroup,
        startValues: TransitionValues?,
        endValues: TransitionValues?
    ): Animator? {
        if (startValues == null || endValues == null) return null
        val animator =
            ValueAnimator.ofFloat(startValues.values[KEY] as Float, endValues.values[KEY] as Float)
        return super.createAnimator(sceneRoot, startValues, endValues)
    }
}