package com.yunext.twins.compose.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.ui.debug.cases.compoents.China

@Composable
fun PreviewPart(content: @Composable () -> Unit) {
    Box(
        Modifier
            .fillMaxSize()
            .background(China.w_qian_shi_bai)
            .padding(16.dp), contentAlignment = Alignment.Center
    ) {
        content()
    }
}

@Preview
@Composable
internal fun BoxDemo() {
    PreviewPart {
        Box(
            Modifier
                .size(300.dp)
                .background(China.r_fen_hong)
        ) {

            Box(
                Modifier
                    .size(100.dp)
                    .background(China.g_zhu_lv)
                    .clickable {

                    }) {
                Text(text = "点击")
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White.copy(.5f))

            ){
                Text(text = "最上层", modifier = Modifier.clickable {

                }.align(Alignment.CenterEnd))
            }
        }
    }

}