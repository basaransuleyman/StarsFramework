package com.starFramework.feature.domain.model

import androidx.compose.runtime.Immutable

@Immutable
data class Star(val size: String, val color: String, val brightness: String) {
    companion object {
        fun createSmallStar(): Star {
            val color = listOf("Red", "Blue", "Green").random()
            val brightness = listOf("Bright", "Not so much").random()
            return Star("S", color, brightness)
        }

        fun createBigStar(): Star {
            val color = listOf("Yellow", "Purple", "Gray").random()
            val brightness = listOf("Bright", "Not so much").random()
            return Star("B", color, brightness)
        }
    }
}