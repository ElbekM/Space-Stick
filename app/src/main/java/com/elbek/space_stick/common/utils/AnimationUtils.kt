package com.elbek.space_stick.common.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.view.View

const val DEFAULT_ANIMATION_DURATION = 300L

object AnimationUtils {

    fun View.showWithAnimation(
        isVisible: Boolean = true,
        duration: Long = DEFAULT_ANIMATION_DURATION
    ) {
        if (isVisible) {
            alpha = 0f
            visibility = View.VISIBLE
            animate().alpha(1f).setDuration(duration).setListener(null)
        } else {
            alpha = 1f
            animate()
                .alpha(0f)
                .setDuration(duration)
                .setListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        visibility = View.GONE
                    }
                })
        }
    }
}
