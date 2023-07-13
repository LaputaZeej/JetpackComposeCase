package com.yunext.twins.compose.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import com.yunext.twins.compose.ui.theme.ItemShape

@Preview
@Composable
fun ItemShadowShapeBlockPreview() {
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Column(Modifier.padding(16.dp)) {

            Spacer(modifier = Modifier.height(32.dp))
            CHItemShadowShape(elevation = 0.dp) {
                Text(
                    text = "elevation:0dp",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Red)
                        .wrapContentHeight()
                )
            }
            Spacer(modifier = Modifier.height(32.dp))

            CHItemShadowShape() {
                Text(
                    text = "elevation:8dp",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Red)
                        .wrapContentHeight()
                )
            }
            Spacer(modifier = Modifier.height(32.dp))
            CHItemShadowShape(elevation = 16.dp) {
                Text(
                    text = "elevation:16dp",
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .size(100.dp)
                        .background(Color.Red)
                        .wrapContentHeight()
                )
            }
        }
    }


}

@Composable
fun CHItemShadowShape(
    modifier: Modifier = Modifier,
    elevation: Dp = 8.dp,
    content: @Composable () -> Unit,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .wrapContentSize()
//            .background(
//                shape = ItemShape,
//                color = Color.White
//            )
            .shadow(
                elevation = elevation,
                shape = ItemShape,
                clip = false,
//                ambientColor = China.b_tian_lan,
//                spotColor = China.r_fen_hong
            )
    ) {
        content()
    }
}