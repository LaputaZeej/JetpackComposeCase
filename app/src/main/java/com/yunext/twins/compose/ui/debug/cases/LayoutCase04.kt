package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.FirstBaseline
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D

@Composable
fun LayoutCase04() {

    D.i("∆∆∆∆ LayoutCase04 ")
    MyBasicColumn {


        Row(
            Modifier
                .border(1.dp, Color.Red)
                .padding(32.dp)

        ) {
            Text(
                text = "【】abcdefghijklmn", modifier = MDF
                    .background(Color.Red)
                    .firstBaselineToTopExt((32.dp))
                    .background(Color.Gray)
            )
            Spacer(
                modifier = Modifier
                    .width(10.dp)
                    .height(32.dp)
                    .background(Color.Blue)
            )
            Text(
                text = "【n】abcdefghijklmn", modifier = MDF
                    .background(Color.Gray)
                    .padding(top = 32.dp)
            )


        }

        Text(
            text = """
            @Composable
            private fun MyBasicColumn(modifier: Modifier = MDF, content: @Composable () -> Unit) {
                Layout(content = content, measurePolicy = MeasurePolicy { measurables, constraints ->

                    val placeableS = measurables.map { measurable ->
                        measurable.measure(constraints = constraints)
                    }

                    layout(constraints.maxWidth, constraints.maxHeight) {
                        var yPosition = 0
                        placeableS.forEach { placeable ->
                            placeable.placeRelative(x = 0, y = yPosition)
                            yPosition += placeable.height
                        }
                    }

                })
            }
            
        """.trimIndent(), fontSize = 11.sp, fontWeight = FontWeight.Light,
            modifier = MDF
                .border(1.dp, Color.Yellow)
                .scale(.8f)
                .border(1.dp, Color.Blue)
        )


    }

}

private fun MDF.firstBaselineToTopExt(
    firstBaselineToTop: Dp,
) = this.layout { measurable, constraints ->
    // measure the composable
    // 测量可测量参数表示的Text
    val placeable = measurable.measure(constraints)

    // check the composable has a first baseline
    check(placeable[FirstBaseline] != AlignmentLine.Unspecified)
    val firstBaseline = placeable[FirstBaseline]

    // Height of the composable with padding - first baseline
    val placeableY = firstBaselineToTop.roundToPx() - firstBaseline
    val height = placeable.height + placeableY

    // 指定Composable的尺寸
    layout(placeable.width, height) {
        // where the composable gets placed
        placeable.placeRelative(0, placeableY)
    }
}

@Composable
private fun MyBasicColumn(modifier: Modifier = MDF, content: @Composable () -> Unit) {
    Layout(content = content, measurePolicy = MeasurePolicy { measurables, constraints ->

        val placeableS = measurables.map { measurable ->
            measurable.measure(constraints = constraints)
        }

        layout(constraints.maxWidth, constraints.maxHeight) {
            var yPosition = 0
            placeableS.forEachIndexed { index,placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                //placeable.placeRelative(x = constraints.maxWidth-placeable.width, y = yPosition)

                yPosition += placeable.height
            }
        }

    }, modifier = modifier)
}