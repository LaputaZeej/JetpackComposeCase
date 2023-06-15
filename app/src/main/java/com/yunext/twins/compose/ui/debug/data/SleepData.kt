/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.yunext.twins.compose.ui.debug.data

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.theme.Yellow_Awake
import com.yunext.twins.compose.ui.theme.Yellow_Deep
import com.yunext.twins.compose.ui.theme.Yellow_Light
import com.yunext.twins.compose.ui.theme.Yellow_Rem
import java.time.Duration
import java.time.LocalDateTime
import kotlin.random.Random

data class SleepGraphData(
    val sleepDayData: List<SleepDayData>,
) {
    val earliestStartHour: Int by lazy {
        sleepDayData.minOf { it.firstSleepStart.hour }
    }
    val latestEndHour: Int by lazy {
        sleepDayData.maxOf { it.lastSleepEnd.hour }
    }
}

data class SleepDayData(
    val startDate: LocalDateTime,
    val sleepPeriods: List<SleepPeriod>,
    val sleepScore: Int,
) {
    val firstSleepStart: LocalDateTime by lazy {
        sleepPeriods.sortedBy(SleepPeriod::startTime).first().startTime
    }
    val lastSleepEnd: LocalDateTime by lazy {
        sleepPeriods.sortedBy(SleepPeriod::startTime).last().endTime
    }
    val totalTimeInBed: Duration by lazy {
        Duration.between(firstSleepStart, lastSleepEnd)
    }

    val sleepScoreEmoji: String by lazy {
        when (sleepScore) {
            in 0..40 -> "😖"
            in 41..60 -> "😏"
            in 60..70 -> "😴"
            in 71..100 -> "😃"
            else -> "🤷‍"
        }
    }

    fun fractionOfTotalTime(sleepPeriod: SleepPeriod): Float {
        return sleepPeriod.duration.toMinutes() / totalTimeInBed.toMinutes().toFloat()
    }

    fun minutesAfterSleepStart(sleepPeriod: SleepPeriod): Long {
        return Duration.between(
            firstSleepStart,
            sleepPeriod.startTime
        ).toMinutes()
    }

    companion object {
        fun random() = SleepDayData(
            LocalDateTime.MIN, SleepPeriod.DEBUG, Random.nextInt(100)
        )
    }
}


data class SleepPeriod(
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val type: SleepType,
) {

    val duration: Duration by lazy {
        Duration.between(startTime, endTime)
    }

    companion object {
        val DEBUG = List(4) {
            SleepPeriod(
                LocalDateTime.of(2012, 12, 3, it, it, it),
                LocalDateTime.of(2012, 12, 3, it, it, it),
                SleepType.values().random()
            )
        }
    }
}

enum class SleepType(val title: Int, val color: Color) {
    Awake(R.string.sleep_type_awake, Yellow_Awake),
    REM(R.string.sleep_type_rem, Yellow_Rem),
    Light(R.string.sleep_type_light, Yellow_Light),
    Deep(R.string.sleep_type_deep, Yellow_Deep)
}


