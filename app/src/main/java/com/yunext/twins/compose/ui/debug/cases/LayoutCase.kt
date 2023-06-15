package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.IntrinsicMeasurable
import androidx.compose.ui.layout.IntrinsicMeasureScope
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.Measurable
import androidx.compose.ui.layout.MeasurePolicy
import androidx.compose.ui.layout.MeasureResult
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.theme.Purple40
import com.yunext.twins.compose.ui.theme.Purple80

@Composable
fun LayoutCase01() {
    D.i("∆∆∆∆ LayoutCase01 ")
    Surface(color = Purple80, modifier = MDF.fillMaxSize()) {
    }

}

@Composable
fun LayoutCase02() {
    D.i("∆∆∆∆ LayoutCase02 ")
    Surface(color = Purple40.copy(.5f), modifier = MDF.fillMaxSize()) {

        Column(modifier = MDF.wrapContentSize(), verticalArrangement = Arrangement.Top) {
            val spaceTMD = Modifier
                .fillMaxWidth()
                .height(4.dp)
            Spacer(modifier = spaceTMD)
            Box(modifier = MDF.height(200.dp)) {
                TwoTexts("hello", "固有特性测量 Intrinsic Measurements", debug = true)
            }
            Spacer(modifier = spaceTMD)
            Box(modifier = MDF.height(200.dp)) {
                TwoTexts("hello", "固有特性测量 Intrinsic Measurements", debug = false)
            }
            Spacer(modifier = spaceTMD)

        }
    }
}


@Composable
private fun TwoTexts(
    text1: String,
    text2: String,
    modifier: Modifier = Modifier,
    debug: Boolean,
) {
   val m =  modifier.background(Color.White)
    val mm  = if (debug)  m.wrapContentHeight() else  m.height(IntrinsicSize.Min)
    Row(
        modifier = mm
    ) {
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(start = 4.dp)
                .wrapContentWidth(Alignment.Start),
            text = text1
        )

        Divider(
            color = Color.Black,
            modifier = Modifier
                .fillMaxHeight()
                .width(1.dp)
        )
        Text(
            modifier = Modifier
                .weight(1f)
                .padding(end = 4.dp)
                .wrapContentWidth(Alignment.End),
            text = text2
        )
    }
}

@Composable
private fun MyCustomLayout(
    content: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Layout(
        modifier = modifier,
        content = content,
        measurePolicy = object : MeasurePolicy {

            override fun IntrinsicMeasureScope.maxIntrinsicHeight(
                measurables: List<IntrinsicMeasurable>,
                width: Int
            ): Int {
                // Logic for calculating custom maxIntrinsicHeight here
                TODO()
            }

            override fun MeasureScope.measure(
                measurables: List<Measurable>,
                constraints: androidx.compose.ui.unit.Constraints,
            ): MeasureResult {
                TODO("Not yet implemented")
            }

            // Other intrinsics related methods have a default value,
            // you can override only the methods that you need.
        }
    )
}