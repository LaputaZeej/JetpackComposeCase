package com.yunext.twins.compose.ui.device.profile.log

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yunext.twins.compose.ui.common.CHTitle

@Composable
fun CHLogPage() {

    Column(modifier = Modifier.fillMaxSize()) {
        CHTitle(text = "日志")
    }
}