package com.yunext.twins.compose.ui.device.data

import androidx.annotation.IntegerRes
import androidx.annotation.StringRes
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.Color
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.theme.ItemDefaults
import com.yunext.twins.compose.ui.theme.app_appColor
import com.yunext.twins.compose.ui.theme.app_blue_light
import com.yunext.twins.compose.ui.theme.app_orange
import com.yunext.twins.compose.ui.theme.app_orange_light
import com.yunext.twins.compose.ui.theme.app_red
import com.yunext.twins.compose.ui.theme.app_red_light
import kotlin.random.Random

@Stable
internal data class EventData(
    val name: String,
    val key: String,
    val required: Boolean,
    val eventType: EventType,
    val output: List<*>,
    val desc: String,
) {

    enum class EventType(@StringRes val text:Int, val color:Pair<Color,Color>) {
        Alert(R.string.event_type_alert, app_orange to app_orange_light),
        Info(R.string.event_type_info, app_appColor to app_blue_light),
        Fault(R.string.event_type_fault, app_red to app_red_light)
        ;
    }

    companion object {
        internal fun random() = EventData(
            name = ItemDefaults.randomText(),
            key = ItemDefaults.randomText(),
            required = Random.nextBoolean(),
            eventType = EventType.values().random(),
            output = List(Random.nextInt(4)) { it },
            desc = ItemDefaults.randomText(),
        )
    }
}