package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.data.TangShiSongCi
import com.yunext.twins.compose.ui.theme.x98f
import com.yunext.twins.compose.ui.theme.xf98



@Preview
@Composable
fun LayoutCase09Preview() {
    LayoutCase09()
}

@Composable
fun LayoutCase09() {
    D.i("∆∆∆∆ LayoutCase09 ")
    var showOnePane by remember {
        mutableStateOf(false)
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(16.dp)
            .background(Color.Yellow)
    ) {
        Button(onClick = { showOnePane = !showOnePane }) {
            Text(text = "<${if (showOnePane)"1屏" else "2屏"}>")
        }
        Spacer(modifier = Modifier.height(4.dp))
        AdaptivePane(showOnePane)

    }
}

@Composable
private fun AdaptivePane(
    showOnePane: Boolean,
) {

    if (showOnePane) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(painter = painterResource(id = R.mipmap.ic_app), contentDescription = "")
            Text(text = TangShiSongCi.t0001)
        }
    } else {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Column() {
                Image(painter = painterResource(id = R.mipmap.ic_app), contentDescription = "")
                Text(text = "DescTitle")
            }
            Text(text = TangShiSongCi.t0001)
        }
    }

}
