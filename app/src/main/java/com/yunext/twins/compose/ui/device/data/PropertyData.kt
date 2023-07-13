package com.yunext.twins.compose.ui.device.data

import androidx.compose.runtime.Stable
import com.yunext.twins.compose.ui.theme.ItemDefaults
import java.util.UUID
import kotlin.random.Random

@Stable
internal data class PropertyData(
    val name: String = "",
    val key: String ="",
    val required: Boolean = false,
    val readWrite: ReadWrite = ReadWrite.R,
    val type: String = "",
    val desc: String ,
) {
    enum class ReadWrite {
        R, W, RW;
    }

    companion object{

        @JvmStatic
        internal fun random() = PropertyData(UUID.randomUUID().toString().take(4),UUID.randomUUID().toString().take(6),
            Random.nextBoolean(),ReadWrite.values().random(),UUID.randomUUID().toString().take(4),ItemDefaults.randomText(8))
    }
}