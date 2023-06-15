package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.data.TangShiSongCi
import com.yunext.twins.compose.ui.theme.x98f
import com.yunext.twins.compose.ui.theme.xf98


@Preview
@Composable
fun LayoutCase12Preview() {
    LayoutCase12()
}

@Composable
fun LayoutCase12() {
    D.i("∆∆∆∆ LayoutCase12 ")
//    var showOnePane by remember {
//        mutableStateOf(false)
//    }
//
//    val transition = updateTransition(targetState = showOnePane, "showOnePane")
//    val maxWidthForContent: Dp by transition.animateDp(label = "width", transitionSpec = {
//        spring(
//            dampingRatio = Spring.DampingRatioLowBouncy,
//            stiffness =
//            Spring.StiffnessLow
//        )
//    }) {
//        if (it) 1000.dp else 360.dp
//    }
//    Column(
//        Modifier
//            .background(Color.LightGray)
//            .fillMaxHeight()
//            .width(maxWidthForContent)
//            .padding(16.dp)
//            .background(Color.Yellow)
//    ) {
//
//    }
}
