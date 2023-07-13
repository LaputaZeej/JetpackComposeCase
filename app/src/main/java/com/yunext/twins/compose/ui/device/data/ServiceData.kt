package com.yunext.twins.compose.ui.device.data

import androidx.compose.runtime.Stable
import com.yunext.twins.compose.ui.theme.ItemDefaults
import java.util.UUID
import kotlin.random.Random

@Stable
internal data class ServiceData(
    val name: String,
    val key: String,
    val required: Boolean,
    val async: Boolean,
    val input: List<*>,
    val output: List<*>,
    val desc: String,
) {
    companion object {
        internal fun random() = ServiceData(
            name = ItemDefaults.randomText(),
            key = ItemDefaults.randomText(),
            required = Random.nextBoolean(),
            async = Random.nextBoolean(),
            input = List(Random.nextInt(4)) { it },
            output = List(Random.nextInt(4)) { it },
            desc = ItemDefaults.randomText(),
        )
    }
}