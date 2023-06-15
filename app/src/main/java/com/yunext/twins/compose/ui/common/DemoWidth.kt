package com.yunext.twins.compose.ui.common

import android.content.ClipData.Item
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.requiredSizeIn
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Preview
@Composable
fun WidthModifierExamplePreview() {
    val state = rememberScrollState()
    Column(modifier = Modifier
        .padding(16.dp)
        .verticalScroll(state)) {
        Spacer(modifier = Modifier.height(20.dp))
        WidthModifierExample()
        Spacer(modifier = Modifier.height(20.dp))
        WidthModifierExample1()
        Spacer(modifier = Modifier.height(20.dp))
        ConstraintsSample1()
        Spacer(
            modifier = Modifier
                .height(20.dp)
                .background(Color.Black)
        )
        ConstraintsSample2()
        Spacer(modifier = Modifier.height(20.dp))
        TextFieldSamples()
        Spacer(modifier = Modifier.height(20.dp))
        ConstraintsSample3()
        Spacer(modifier = Modifier.height(20.dp))
        BoxWithConstraintsSample4()
    }

}

@Composable
fun WidthModifierExample() {
    val columnWidth = 200.dp
    Column(
        modifier = Modifier
            .width(columnWidth) // minWidth = 200dp , maxWidth = 200.dp
            .border(1.dp, Color.Gray)
    ) {
        Text(
            text = "requiredWidth = parent - 50",
            modifier = Modifier
                .requiredWidth(columnWidth - 50.dp)// 忽略了父容器的约束
                .background(Color.LightGray)
                .border(width = 1.dp, color = Color.Red)
        )
        Text(
            text = "requiredWidth = parent + 50",
            modifier = Modifier
                .requiredWidth(columnWidth + 50.dp)// 忽略了父容器的约束
                .background(Color.LightGray)
                .border(width = 1.dp, color = Color.Green)
        )
        Text(
            text = "width = parent - 50",
            modifier = Modifier
                .width(columnWidth - 50.dp)
                .background(Color.LightGray)
                .border(width = 1.dp, color = Color.Yellow)

        )
        Text(
            text = "width = parent + 50",
            modifier = Modifier
                .width(columnWidth + 50.dp)
                .background(Color.LightGray)
                .border(width = 1.dp, color = Color.Red)
        )
    }

}

@Composable
fun WidthModifierExample1() {
    Column(
        modifier = Modifier.border(1.dp, Color.Gray)
    ) {
        val minWidth = 140
        val maxWidth = 200
        val widthDescriptions = arrayOf(
            WidthDescription(minWidth - 50, "min - 50"),
            WidthDescription((minWidth + maxWidth) / 2, "between min and max"),
            WidthDescription(maxWidth + 50, "max + 50")
        )

        for (widthDescription in widthDescriptions) {
            Text(
                text = "requiredWidth = ${widthDescription.description}",
                modifier = Modifier
                    .border(.5.dp, Color.Red)
                    .widthIn(minWidth.dp, maxWidth.dp)
                    .background(Color.LightGray)
                    .requiredWidth(widthDescription.width.dp)
            )
        }

        Spacer(modifier = Modifier.height(4.dp))

        for (widthDescription in widthDescriptions) {
            Text(
                text = "width = ${widthDescription.description}",
                modifier = Modifier
                    .border(.5.dp, Color.Red)
                    .widthIn(minWidth.dp, maxWidth.dp)
                    .background(Color.LightGray)
                    .width(widthDescription.width.dp)
            )
        }
    }
}

data class WidthDescription(
    val width: Int,
    val description: String,
)

@Composable
private fun ConstraintsSample1() {
    Text(text = "Fixed Size")
    BoxWithConstraints(
        modifier = Modifier
            .size(100.dp)
            .border(3.dp, Color.Green)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
    BoxWithConstraints(
        modifier = Modifier
            .size(100.dp)
            .border(3.dp, Color.Green)
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Red)
        )
    }

    Text(text = "widthIn(min)")

    BoxWithConstraints(
        modifier = Modifier
            .widthIn(min = 100.dp)
            .border(3.dp, Color.Green)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
    BoxWithConstraints(
        modifier = Modifier
            .widthIn(min = 100.dp)
            .border(3.dp, Color.Green)
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Red)
        )
    }


    Text(text = "widthIn(max)")

    BoxWithConstraints(
        modifier = Modifier
            .widthIn(max = 100.dp)
            .border(3.dp, Color.Green)
    ) {
        Box(
            modifier = Modifier
                .size(50.dp)
                .background(Color.Red)
        )
    }

    Spacer(modifier = Modifier.height(10.dp))
    BoxWithConstraints(
        modifier = Modifier
            .widthIn(max = 100.dp)
            .border(3.dp, Color.Green)
    ) {
        Box(
            modifier = Modifier
                .size(150.dp)
                .background(Color.Red)
        )
    }
}

@Composable
private fun ConstraintsSample2() {

    Column(modifier = Modifier
        .fillMaxSize()
        .padding(30.dp)
        .border(4.dp, Color.Cyan)) {

        Text(text = "Chaining size modifiers")

        Box(modifier = Modifier
            .size(50.dp)
            .background(Color.Yellow))

        Box(modifier = Modifier
            .size(50.dp)
            .size(100.dp)
            .background(Color.Red))
        Box(modifier = Modifier
            .size(100.dp)
            .size(50.dp)
            .background(Color.Blue))

        Box(modifier = Modifier
            .size(50.dp)
            .requiredSizeIn(100.dp)
            .background(Color.Green)
            .border(2.dp, Color.Black)
        )


        Box(modifier = Modifier
            .size(150.dp)
            .requiredSizeIn(100.dp)
            .background(Color.Yellow)
            .border(2.dp, Color.Black)
        )



        Text(text = "widthIn(max)")  // 父级

        BoxWithConstraints(
            modifier = Modifier
                .width(100.dp)
                .border(3.dp, Color.Green)
        ) {
            Box(
                modifier = Modifier
                    .requiredWidthIn(min = 20.dp, max = 50.dp)
                    .height(50.dp)
                    .background(Color.Red)
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        BoxWithConstraints(
            modifier = Modifier
                .width(100.dp)
                .border(3.dp, Color.Green)
        ) {
            Box(
                modifier = Modifier
                    .requiredWidthIn(min = 150.dp, max = 200.dp)
                    .height(50.dp)
                    .background(Color.Red)
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TextFieldSamples() {

    Column(
        modifier = Modifier
            .padding(20.dp)
            .border(2.dp, Color.Cyan)
    ) {

        var text1 by remember { mutableStateOf("") }

        Column(modifier = Modifier) {
            TextField(
                modifier = Modifier
                    .border(2.dp, Color.Green)
                    .widthIn(56.dp),
                value = text1,
                onValueChange = { text1 = it }
            )

            TextField(
                modifier = Modifier
                    .border(2.dp, Color.Red)
                    .requiredWidthIn(56.dp),
                value = text1, onValueChange = { text1 = it })
        }

        Spacer(modifier = Modifier.height(30.dp))

        var text2 by remember { mutableStateOf("") }

        Column(modifier = Modifier.width(50.dp)) {
            TextField(
                modifier = Modifier
                    .border(2.dp, Color.Green)
                    .widthIn(56.dp),
                value = text2, onValueChange = { text2 = it })

            TextField(
                modifier = Modifier
                    .border(2.dp, Color.Red)
                    .requiredWidthIn(56.dp),
                value = text2, onValueChange = { text2 = it })
        }
    }
}

@Composable
fun ConstraintsSample3() {
    Column(modifier = Modifier) {

        Text(text = "No Dimension Modifier")

        BoxWithConstraints(modifier = Modifier.background(Color.Gray)) {
            val hasBoundedWidth = constraints.hasBoundedWidth
            val hasFixedWidth = constraints.hasFixedWidth
            val minWidth = constraints.minWidth
            val maxWidth = constraints.maxWidth

            val hasBoundedHeight = constraints.hasBoundedHeight
            val hasFixedHeight = constraints.hasFixedHeight
            val minHeight = constraints.minHeight
            val maxHeight = constraints.maxHeight
            Text(
                "minWidth: $minWidth, maxWidth: $maxWidth\n" +
                        "hasBoundedWidth: $hasBoundedWidth, hasFixedWidth: $hasFixedWidth\n" +
                        "minHeight: $minHeight, maxHeight: $maxHeight\n" +
                        "hasBoundedHeight: $hasBoundedHeight, hasFixedHeight: $hasFixedHeight",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "FillMaxWidth and 200.dp Height")
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .background(Color.Red)
        ) {
            val hasBoundedWidth = constraints.hasBoundedWidth
            val hasFixedWidth = constraints.hasFixedWidth
            val minWidth = constraints.minWidth
            val maxWidth = constraints.maxWidth

            val hasBoundedHeight = constraints.hasBoundedHeight
            val hasFixedHeight = constraints.hasFixedHeight
            val minHeight = constraints.minHeight
            val maxHeight = constraints.maxHeight
            Text(
                "minWidth: $minWidth, maxWidth: $maxWidth\n" +
                        "hasBoundedWidth: $hasBoundedWidth, hasFixedWidth: $hasFixedWidth\n" +
                        "minHeight: $minHeight, maxHeight: $maxHeight\n" +
                        "hasBoundedHeight: $hasBoundedHeight, hasFixedHeight: $hasFixedHeight",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "wrapContentSize()")
        BoxWithConstraints(
            modifier = Modifier
                .wrapContentSize()
                .background(Color.Cyan)
        ) {

            val hasBoundedWidth = constraints.hasBoundedWidth
            val hasFixedWidth = constraints.hasFixedWidth
            val minWidth = constraints.minWidth
            val maxWidth = constraints.maxWidth

            val hasBoundedHeight = constraints.hasBoundedHeight
            val hasFixedHeight = constraints.hasFixedHeight
            val minHeight = constraints.minHeight
            val maxHeight = constraints.maxHeight
            Text(
                "minWidth: $minWidth, maxWidth: $maxWidth\n" +
                        "hasBoundedWidth: $hasBoundedWidth\n" +
                        "hasFixedWidth: $hasFixedWidth\n" +
                        "minHeight: $minHeight\n" +
                        "maxHeight: $maxHeight\n" +
                        "hasBoundedHeight: $hasBoundedHeight\n" +
                        "hasFixedHeight: $hasFixedHeight",
                color = Color.White
            )
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "200.dp Width and Height")
        BoxWithConstraints(
            modifier = Modifier
                .width(200.dp)
                .height(200.dp)
                .background(Color.Magenta)
        ) {

            val hasBoundedWidth = constraints.hasBoundedWidth
            val hasFixedWidth = constraints.hasFixedWidth
            val minWidth = constraints.minWidth
            val maxWidth = constraints.maxWidth

            val hasBoundedHeight = constraints.hasBoundedHeight
            val hasFixedHeight = constraints.hasFixedHeight
            val minHeight = constraints.minHeight
            val maxHeight = constraints.maxHeight
            Text(
                "minWidth: $minWidth, maxWidth: $maxWidth\n" +
                        "hasBoundedWidth: $hasBoundedWidth, hasFixedWidth: $hasFixedWidth\n" +
                        "minHeight: $minHeight, maxHeight: $maxHeight\n" +
                        "hasBoundedHeight: $hasBoundedHeight, hasFixedHeight: $hasFixedHeight",
                color = Color.White
            )
        }
    }
}

@Composable
private fun BoxWithConstraintsSample4() {
    Text(text = "200.dp WidthIn(min) and HeightIn(min)")
    BoxWithConstraints(
        modifier = Modifier
            .widthIn(min = 200.dp)
            .heightIn(200.dp)
            .background(Color.Blue)
    ) {

        val hasBoundedWidth = constraints.hasBoundedWidth
        val hasFixedWidth = constraints.hasFixedWidth
        val minWidth = constraints.minWidth
        val maxWidth = constraints.maxWidth

        val hasBoundedHeight = constraints.hasBoundedHeight
        val hasFixedHeight = constraints.hasFixedHeight
        val minHeight = constraints.minHeight
        val maxHeight = constraints.maxHeight
        Text(
            "minWidth: $minWidth, maxWidth: $maxWidth\n" +
                    "hasBoundedWidth: $hasBoundedWidth, hasFixedWidth: $hasFixedWidth\n" +
                    "minHeight: $minHeight, maxHeight: $maxHeight\n" +
                    "hasBoundedHeight: $hasBoundedHeight, hasFixedHeight: $hasFixedHeight",
            color = Color.White
        )
    }

    Text(text = "200.dp requiredWidth(min) and requiredHeight(min)")
    BoxWithConstraints(
        modifier = Modifier
            .requiredWidthIn(min = 200.dp)
            .requiredHeightIn(200.dp)
            .background(Color.Magenta)
    ) {

        val hasBoundedWidth = constraints.hasBoundedWidth
        val hasFixedWidth = constraints.hasFixedWidth
        val minWidth = constraints.minWidth
        val maxWidth = constraints.maxWidth

        val hasBoundedHeight = constraints.hasBoundedHeight
        val hasFixedHeight = constraints.hasFixedHeight
        val minHeight = constraints.minHeight
        val maxHeight = constraints.maxHeight
        Text(
            "minWidth: $minWidth, maxWidth: $maxWidth\n" +
                    "hasBoundedWidth: $hasBoundedWidth, hasFixedWidth: $hasFixedWidth\n" +
                    "minHeight: $minHeight, maxHeight: $maxHeight\n" +
                    "hasBoundedHeight: $hasBoundedHeight, hasFixedHeight: $hasFixedHeight",
            color = Color.White
        )
    }

    Text(text = "200.dp defaultMinSize()")
    BoxWithConstraints(
        modifier = Modifier
            .defaultMinSize(200.dp)
            .background(Color.Cyan)
    ) {

        val hasBoundedWidth = constraints.hasBoundedWidth
        val hasFixedWidth = constraints.hasFixedWidth
        val minWidth = constraints.minWidth
        val maxWidth = constraints.maxWidth

        val hasBoundedHeight = constraints.hasBoundedHeight
        val hasFixedHeight = constraints.hasFixedHeight
        val minHeight = constraints.minHeight
        val maxHeight = constraints.maxHeight
        Text(
            "minWidth: $minWidth, maxWidth: $maxWidth\n" +
                    "hasBoundedWidth: $hasBoundedWidth, hasFixedWidth: $hasFixedWidth\n" +
                    "minHeight: $minHeight, maxHeight: $maxHeight\n" +
                    "hasBoundedHeight: $hasBoundedHeight, hasFixedHeight: $hasFixedHeight",
            color = Color.White
        )
    }

    Text(text = "200.dp WidthIn(max)")
    BoxWithConstraints(
        modifier = Modifier
            .widthIn(max = 200.dp)
            .background(Color.Red)
    ) {

        val hasBoundedWidth = constraints.hasBoundedWidth
        val hasFixedWidth = constraints.hasFixedWidth
        val minWidth = constraints.minWidth
        val maxWidth = constraints.maxWidth

        val hasBoundedHeight = constraints.hasBoundedHeight
        val hasFixedHeight = constraints.hasFixedHeight
        val minHeight = constraints.minHeight
        val maxHeight = constraints.maxHeight
        Text(
            "minWidth: $minWidth, maxWidth: $maxWidth\n" +
                    "hasBoundedWidth: $hasBoundedWidth, hasFixedWidth: $hasFixedWidth\n" +
                    "minHeight: $minHeight, maxHeight: $maxHeight\n" +
                    "hasBoundedHeight: $hasBoundedHeight, hasFixedHeight: $hasFixedHeight",
            color = Color.White
        )
    }
}

// https://www.qiniu.com/qfans/qnso-65779226#comments
// https://stackoverflow.com/questions/65779226/android-jetpack-compose-width-height-size-modifier-vs-requiredwidth-requir