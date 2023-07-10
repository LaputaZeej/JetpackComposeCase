package com.yunext.twins.compose.ui.debug.cases

import android.text.style.AlignmentSpan
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yunext.twins.compose.R
import com.yunext.twins.compose.route.navigateSingleTopTo
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.data.TangShiSongCi
import com.yunext.twins.compose.ui.theme.x98f
import com.yunext.twins.compose.ui.theme.xf98
import kotlinx.coroutines.launch


@Preview
@Composable
fun LayoutCase13Preview() {
    LayoutCase13()
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutCase13() {
    D.i("∆∆∆∆ LayoutCase13 ")
    Column(
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp)
            .background(Color.Gray)
            .padding(16.dp)

    ) {
        Row(
            Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.DarkGray)
                .padding(16.dp)
                .background(Color.Gray)
                .padding(16.dp)

        ) {

            TextField(
                modifier = Modifier.firstBaselineToTopExt(16.dp),
                value = "hello zeejpg",
                onValueChange = {

                })

            Text(
                text = "【】abcdefghijklmn", modifier = MDF
                    .background(Color.Red)
                    .firstBaselineToTopExt((16.dp))
                //.background(Color.Green)
            )
            Spacer(
                modifier = Modifier
                    .width(10.dp)
                    .height(16.dp)
                    .background(Color.Blue)
            )
            Text(
                text = "【n】abcdefghijklmn", modifier = MDF
                    .background(Color.Green)
                    .padding(top = 16.dp)
            )

        }

//        BarChartMinMax(
//            dataPoints = listOf(4, 24, 15),
//            maxText = { Text("Max") },
//            minText = { Text("Min") },
//            modifier = Modifier.padding(24.dp)
//        )
    }

}

//@Composable
//fun BarChartMinMax(
//    dataPoints: List<Int>,
//    maxText: @Composable () -> Unit,
//    minText: @Composable () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    Layout(
//        content = {
//            maxText()
//            minText()
//            // Set a fixed size to make the example easier to follow
//            BarChart(dataPoints, Modifier.size(200.dp))
//        },
//        modifier = modifier
//    ) { measurables, constraints ->
//        check(measurables.size == 3)
//        val placeables = measurables.map {
//            it.measure(constraints.copy(minWidth = 0, minHeight = 0))
//        }
//
//        val maxTextPlaceable = placeables[0]
//        val minTextPlaceable = placeables[1]
//        val barChartPlaceable = placeables[2]
//
//        // Obtain the alignment lines from BarChart to position the Text
//        val minValueBaseline = barChartPlaceable[MinChartValue]
//        val maxValueBaseline = barChartPlaceable[MaxChartValue]
//        layout(constraints.maxWidth, constraints.maxHeight) {
//            maxTextPlaceable.placeRelative(
//                x = 0,
//                y = maxValueBaseline - (maxTextPlaceable.height / 2)
//            )
//            minTextPlaceable.placeRelative(
//                x = 0,
//                y = minValueBaseline - (minTextPlaceable.height / 2)
//            )
//            barChartPlaceable.placeRelative(
//                x = max(maxTextPlaceable.width, minTextPlaceable.width) + 20,
//                y = 0
//            )
//        }
//    }
//}


