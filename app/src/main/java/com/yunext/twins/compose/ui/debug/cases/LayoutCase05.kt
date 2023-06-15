package com.yunext.twins.compose.ui.debug.cases

import android.util.Log
import android.widget.TextView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import kotlinx.coroutines.delay
import java.sql.Time
import kotlin.math.roundToInt
import kotlin.random.Random

@Composable
fun LayoutCase05() {
    val column = 7
    val row = 5
    val hours = List(row) { "${it}" }
    var ratio by remember {
        mutableStateOf(1f)
    }
    LaunchedEffect(key1 = "demo"){
        while (true){
            delay(1000)
            ratio = Random.nextFloat()
        }
    }
    D.i("∆∆∆∆ LayoutCase05 ")

    Column {


        TimeGraph(hoursHeader = {
            Row(modifier = MDF.background(Color.Blue.copy(.5f))) {
                hours.forEach { hour ->
                    Text(
                        text = "[12月-${hour}号]", modifier = MDF
                            .width(50.dp)
                            .wrapContentHeight()
                    )
                }
            }
        }, rowCount = column, dayLabel = { index ->
            Text(
                text = "周${index + 1}", modifier = Modifier
                    .background(Color.Cyan.copy(.5f))
                    .height(24.dp)
                    .wrapContentHeight()
            )
        }, sleepBar = { index ->
            val offset = (index * 1f * ratio / column + Random.nextFloat() * 0.5f).coerceAtMost(1f)
            Box(
                Modifier
                    .background(Color.Yellow)
                    .timeGraphSleepBar(offset, 1 - offset)
                    .height(24.dp)
            ) {

            }
        }, modifier = MDF
            .padding(16.dp)
            .background(Color.Green.copy(0.2f))
        )

        Spacer(modifier = Modifier.height(16.dp))
        Row(verticalAlignment = Alignment.CenterVertically){
            Box(modifier = Modifier
                .background(Color.Green.copy(.2f))
                .size(50.dp, 24.dp)) {

            }
            Text(text = "整个Composable")
        }
        Row(){
            Box(modifier = Modifier
                .background(Color.Yellow)
                .size(50.dp, 24.dp)) {

            }
            Text(text = "数据部分")
        }

        Row(verticalAlignment = Alignment.CenterVertically){
            Box(modifier = Modifier
                .background(Color.Cyan.copy(.5f))
                .size(50.dp, 24.dp)) {

            }
            Text(text = "星期部分")
        }

        Row(verticalAlignment = Alignment.CenterVertically){
            Box(modifier = Modifier
                .background(Color.Blue.copy(.5f))
                .size(50.dp, 24.dp)) {

            }
            Text(text = "日期部分")
        }
    }
}

private class TimeGraphParentData(
    /* need to measure */
    val duration: Float,
    /* need to place */
    val offset: Float,
) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any? {
        return this@TimeGraphParentData
    }
}

private fun MDF.timeGraphSleepBar(
    duration: Float ,
    offset: Float,
) = this then TimeGraphParentData(
    duration = duration,
    offset = offset
)


@Composable
private fun TimeGraph(
    hoursHeader: @Composable () -> Unit,
    rowCount: Int,
    dayLabel: @Composable (index: Int) -> Unit,
    sleepBar: @Composable (index: Int) -> Unit,
    modifier: Modifier = Modifier,
) {

    val dayLabels = @Composable {
        repeat(rowCount) {
            dayLabel(it)
        }
    }
    val sleepBars = @Composable {
        repeat(rowCount) {
            sleepBar(it)
        }
    }

    Layout(
        contents = listOf(hoursHeader, dayLabels, sleepBars),
        modifier = modifier,
        measurePolicy = {
            //measures:List<List<Measurable>>,constracts:Constraints ->
                (hoursHeaderMeasurables, dayLabelMeasureables, sleepBarMeasurables), constraints: Constraints ->


            // 1.Measurement step
            // Determine sizes of components  MeasureScope
            D.i("1.Measurement step")
            require(hoursHeaderMeasurables.size == 1) {
                "hoursHeader should only emit one composable!"
            }
            val hoursHeaderPlaceable = hoursHeaderMeasurables.first().measure(constraints)
            var totalHeight = hoursHeaderPlaceable.height
            D.i("   hoursHeaderPlaceable.height = $totalHeight")
            val dayLabelPlaceables = dayLabelMeasureables.map { measurable ->
                D.i("   ->dayLabelPlaceables")
                measurable.measure(constraints)

            }
            val sleepBarPlaceables = sleepBarMeasurables.map { measurable ->
                val barParentData = measurable.parentData as TimeGraphParentData
                val barWidth = (barParentData.duration * hoursHeaderPlaceable.width).roundToInt()
                val barPlaceable = measurable.measure(
                    constraints.copy(
                        minWidth = barWidth,
                        maxWidth = barWidth
                    )
                )
                D.e("   ->sleepBarPlaceables.height = ${barPlaceable.height}")
                totalHeight += barPlaceable.height
                D.i("   ->sleepBarPlaceables.height = $totalHeight")
                barPlaceable
            }
            val totalWidth = dayLabelPlaceables.first().width + hoursHeaderPlaceable.width

            D.i("totalHeight:$totalHeight ,totalWidth:$totalWidth")

            // 2.Placement step
            // Determine positions of components PlacementScope
            D.i("2.Placement step")
            layout(totalWidth, totalHeight) {
                var xPosition = dayLabelPlaceables.first().width
                var yPosition = hoursHeaderPlaceable.height
                D.i("   place hoursHeader")
                hoursHeaderPlaceable.placeRelative(x = xPosition, 0)
                D.i("   place sleepBar")
                sleepBarPlaceables.forEachIndexed { index, placeable ->
                    D.i("   ->place sleepBar $index - $yPosition")
                    val barParentData = placeable.parentData as TimeGraphParentData
                    val barOffset = (barParentData.offset * hoursHeaderPlaceable.width).roundToInt()
                    placeable.place(x = barOffset + xPosition, y = yPosition)
                    D.i("   ->place sleepBar ok")
                    val dayLabelPlaceable = dayLabelPlaceables[index]
                    dayLabelPlaceable.place(x = 0, y = yPosition)
                    D.i("   ->place dayLabel ok")
                    yPosition += placeable.height
                }
            }
        })
}

