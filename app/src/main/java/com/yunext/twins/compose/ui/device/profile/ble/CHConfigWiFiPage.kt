package com.yunext.twins.compose.ui.device.profile.ble

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.yunext.twins.compose.ui.common.CHTitle

@Composable
fun CHConfigWiFiPage(onLeft:()->Unit) {

    Column(modifier = Modifier.fillMaxSize()) {
        CHTitle(text = "配网", leftClick = onLeft)
    }
}