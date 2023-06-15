package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.ui.common.MDF

@Composable
fun ActionSlot(
    title:String,
    onPreEnable: () -> Boolean,
    onNextEnable: () -> Boolean,
    onPre: () -> Unit = {},
    onNext: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    val nextEnable = onNextEnable()
    val preEnable = onPreEnable()

    Column() {

        Box(modifier = MDF
            .fillMaxHeight()
            .weight(1f)) {
            content()
        }
        Row(
            MDF
                .background(Color.Black.copy(.1f))
                .fillMaxWidth()
                .padding(16.dp), horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(text = "上一个",
                MDF
                    .padding(16.dp)
                    .background(Color.Cyan.copy(alpha = .5f))
                    .clickable(enabled = preEnable) {
                        onPre()
                    }
                    .padding(16.dp), color = if (preEnable) Color.Black else Color.Red, fontWeight = FontWeight.Bold
            )
            Text(text = title,MDF.weight(1f).padding(12.dp))
            Text(text = "下一个",
                MDF
                    .padding(16.dp)
                    .background(Color.Cyan.copy(alpha = .5f))
                    .clickable(enabled = nextEnable) { onNext() }
                    .padding(16.dp),
                color = if (nextEnable) Color.Black else Color.Red, fontWeight = FontWeight.Bold
            )

        }


    }

}