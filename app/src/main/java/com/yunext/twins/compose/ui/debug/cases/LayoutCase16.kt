package com.yunext.twins.compose.ui.debug.cases

import android.widget.SeekBar
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.DrawModifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.ContentDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.inset
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.cases.compoents.XplSpacer
import com.yunext.twins.compose.ui.debug.cases.compoents.desc
import com.yunext.twins.compose.ui.debug.cases.compoents.randomContentScale
import com.yunext.twins.compose.ui.debug.cases.compoents.xplBorder
import com.yunext.twins.compose.ui.debug.cases.compoents.xplBrush
import com.yunext.twins.compose.ui.debug.cases.compoents.xplRandomColor
import com.yunext.twins.compose.ui.debug.cases.data.XplMsg
import com.yunext.twins.compose.ui.theme.x98f
import com.yunext.twins.compose.ui.theme.x9f8
import com.yunext.twins.compose.ui.theme.xf98
import java.util.Random
import kotlin.math.roundToInt

private const val ULR =
    "https://img2.baidu.com/it/u=3853345508,384760633&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1200"

@Preview
@Composable
fun LayoutCase16Preview() {
    LayoutCase16()
}

@OptIn(ExperimentalTextApi::class)
@Composable
fun LayoutCase16() {
    D.i("∆∆∆∆ LayoutCase16 ")
    val verticalScrollState = rememberScrollState()

    var pointerOffset by remember {
        mutableStateOf(Offset(0f, 0f))
    }

    var open by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = MDF
            .verticalScroll(verticalScrollState)
            .fillMaxSize()
    ) {

        Spacer(
            modifier = Modifier
                .size(100.dp)
                .drawBehind {
                    // this = DrawScope
                    drawCircle(Color.Red)
                }
        )

        Spacer(modifier = Modifier.height(16.dp))
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .background(x98f)
        ) {
            val canvasQuadrantSize = size / 2F
            drawRect(
                color = Color.Magenta,
                size = canvasQuadrantSize
            )

        }

        Spacer(modifier = Modifier.height(16.dp))
        Canvas(
            modifier = Modifier
                .size(100.dp)
                .background(x98f)
        ) {
            scale(scaleX = 10f, scaleY = 15f) {
                drawCircle(Color.Blue, radius = 5.dp.toPx())
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .size(100.dp)
                .background(x98f)
        ) {
            translate(left = 30f, top = -30f) {
                drawCircle(Color.Yellow, radius = 30.dp.toPx())
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .height(height = 100.dp)
                .aspectRatio(2f)
                .background(x98f)
        ) {
            rotate(degrees = 45F) {
                drawRect(
                    color = Color.Gray,
                    topLeft = Offset(x = size.width / 3F, y = size.height / 3F),
                    size = size / 3F
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Canvas(
            modifier = Modifier
                .height(height = 100.dp)
                .aspectRatio(2f)
                .background(x98f)
        ) {
            inset(horizontal = 15f, vertical = 30f) {
                drawRect(
                    color = Color.Yellow,
                    topLeft = Offset(x = 10f, y = 10f),
                    size = size * 2f / 3F
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))
        Canvas(
            modifier = Modifier
                .height(height = 100.dp)
                .aspectRatio(2f)
                .background(x98f)
        ) {
            withTransform({
                translate(left = 90f)
                rotate(degrees = 45F)
            }) {
                drawRect(
                    color = Color.Gray,
                    topLeft = Offset(x = size.width / 3F, y = size.height / 3F),
                    size = size / 3F
                )
            }

        }

        Spacer(modifier = Modifier.height(16.dp))
        val textMeasurer = rememberTextMeasurer()
        Button(onClick = { open = !open }) {
            Text(text = "开灯/关灯")
        }
        Box(
            MDF
                .fillMaxSize()
                .pointerInput("dragging") {
                    detectDragGestures { change, dragAmount ->
                        pointerOffset += dragAmount
                    }
                }
                .onSizeChanged {
                    pointerOffset = Offset(it.width / 2f, it.height / 2f)
                }
                .drawWithContent {
                    drawContent()
                    // draws a fully black area with a small keyhole at pointerOffset that’ll show part of the UI.
                    if (open) {
                        drawRect(
                            Brush.radialGradient(
                                0.2f to Color.Transparent,
                                0.8f to Color.Yellow,
                                1f to Color.Black,
//                        listOf(Color.Transparent, Color.Black),
                                center = pointerOffset,
                                radius = 150.dp.toPx(),
                            )
                        )
                    }
                }) {


            Spacer(
                modifier = Modifier
                    .drawWithCache {
                        val measuredText =
                            textMeasurer.measure(
                                AnnotatedString(TEXT),
//                            constraints = Constraints.fixedWidth((size.width * 2f / 3f).toInt()),
                                constraints = Constraints.fixedWidth((size.width).toInt()),
                                style = TextStyle(fontSize = 18.sp)
                            )

                        onDrawBehind {
                            drawRect(x9f8, size = measuredText.size.toSize())
                            drawText(measuredText)
                        }
                    }
                    .fillMaxWidth()
                    .height(600.dp)
//                .wrapContentHeight()
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Hello Compose!",
            modifier = Modifier
                .drawBehind {
                    drawRoundRect(
                        Color(0xFFBBAAEE),
                        cornerRadius = CornerRadius(10.dp.toPx())
                    )
                }
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            "Hello Compose!",
            modifier = Modifier
                .drawWithCache {
                    val brush = Brush.linearGradient(
                        listOf(
                            Color(0xFF9E82F0),
                            Color(0xFF42A5F5)
                        )
                    )
                    onDrawBehind {
                        drawRoundRect(
                            brush,
                            cornerRadius = CornerRadius(10.dp.toPx())
                        )
                    }
                }
                .padding(16.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        var scaleX  by remember {
            mutableStateOf(1f)
        }

        var scaleY  by remember {
            mutableStateOf(1f)
        }
        Text(text = "scaleX : $scaleX")
        Text(text = "scaleY : $scaleY")

        Slider(value = scaleX, onValueChange = {
            scaleX = it
        })
        Slider(value = scaleY, onValueChange = {
            scaleY = it
        })
        var rotationX  by remember {
            mutableStateOf(1f)
        }
        Slider(value = rotationX, onValueChange = {
            rotationX = it
        })
        Text(text = "rotationX : $rotationX")
        var rotationY  by remember {
            mutableStateOf(1f)
        }
        Slider(value = rotationY, onValueChange = {
            rotationY = it
        })
        Text(text = "rotationZ : $rotationY")
        var rotationZ  by remember {
            mutableStateOf(1f)
        }
        Slider(value = rotationZ, onValueChange = {
            rotationZ = it
        })
        Text(text = "rotationZ : $rotationZ")
        Image(
            painterResource(id = R.drawable.death_valley),
            "Hello Compose!",
            modifier = Modifier
                .graphicsLayer {
                    this.scaleX = scaleX
                    this.scaleY = scaleY
                    this.rotationX = rotationX*360
                    this.rotationY = rotationY*360
                    this.rotationZ = rotationZ*360
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "裁剪")
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .graphicsLayer {
                        clip = true
                        shape = CircleShape
                    }
                    .background(Color(0xFFF06292))
            ) {
                Text(
                    "Hello Compose",
                    style = TextStyle(color = Color.Black, fontSize = 46.sp),
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
                    .background(Color(0xFF4DB6AC))
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "裁剪")
        Column(modifier = Modifier.padding(16.dp)) {
            Box(
                modifier = Modifier
                    .clip(RectangleShape) //！！！为了将可组合项裁剪至其绘制区域，可以在修饰符链的开头再添加一个 Modifier.clip(RectangleShape)。然后，内容会保留在原始边界内。
                    .size(200.dp)
                    .border(2.dp, Color.Black)
                    .graphicsLayer {
                        clip = true
                        shape = CircleShape
                        translationY = 50.dp.toPx()
                    }
                    .background(Color(0xFFF06292))
            ) {
                Text(
                    "Hello Compose",
                    style = TextStyle(color = Color.Black, fontSize = 46.sp),
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            Box(
                modifier = Modifier
                    .size(200.dp)
                    .clip(RoundedCornerShape(500.dp))
                    .background(Color(0xFF4DB6AC))
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "合成策略")
        Row() {
            Image(painter = painterResource(id = R.drawable.goldengate),
                contentDescription = "Dog",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .aspectRatio(1f)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFFC5E1A5),
                                Color(0xFF80DEEA)
                            )
                        )
                    )
                    .padding(8.dp)
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithCache {
                        val path = Path()
                        path.addOval(
                            Rect(
                                topLeft = Offset.Zero,
                                bottomRight = Offset(size.width, size.height)
                            )
                        )
                        onDrawWithContent {
//                            clipPath(path) {
//                                // this draws the actual image - if you don't call drawContent, it wont
//                                // render anything
//                                this@onDrawWithContent.drawContent()
//                            }
                        this@onDrawWithContent.drawContent()

                            val dotSize = size.width / 8f
                            // Clip a white border for the content
                            drawCircle(
                                Color.Green,
                                radius = dotSize,
                                center = Offset(
                                    x = size.width - dotSize,
                                    y = size.height - dotSize
                                ),
                                blendMode = BlendMode.Clear
                            )
                            // draw the red circle indication
                            drawCircle(
                                Color(0xFFEF5350), radius = dotSize * 0.8f,
                                center = Offset(
                                    x = size.width - dotSize,
                                    y = size.height - dotSize
                                )
                            )
                        }

                    }

            )


            Image(painter = painterResource(id = R.drawable.goldengate),
                contentDescription = "Dog",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .aspectRatio(1f)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFFC5E1A5),
                                Color(0xFF80DEEA)
                            )
                        )
                    )
                    .padding(8.dp)
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithCache {
                        val path = Path()
                        path.addOval(
                            Rect(
                                topLeft = Offset.Zero,
                                bottomRight = Offset(size.width, size.height)
                            )
                        )
                        onDrawWithContent {
                            clipPath(path) {
                                // this draws the actual image - if you don't call drawContent, it wont
                                // render anything
                                this@onDrawWithContent.drawContent()
                            }
//                        this@onDrawWithContent.drawContent()

                            val dotSize = size.width / 8f
                            // Clip a white border for the content
                            drawCircle(
                                Color.Green,
                                radius = dotSize,
                                center = Offset(
                                    x = size.width - dotSize,
                                    y = size.height - dotSize
                                ),
//                                blendMode = BlendMode.Clear
                                blendMode = BlendMode.Xor
                            )
                            // draw the red circle indication
                            drawCircle(
                                Color(0xFFEF5350), radius = dotSize * 0.8f,
                                center = Offset(
                                    x = size.width - dotSize,
                                    y = size.height - dotSize
                                )
                            )
                        }

                    }

            )

            Image(painter = painterResource(id = R.drawable.goldengate),
                contentDescription = "Dog",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(120.dp)
                    .aspectRatio(1f)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFFC5E1A5),
                                Color(0xFF80DEEA)
                            )
                        )
                    )
                    .padding(8.dp)
                    .graphicsLayer {
                        compositingStrategy = CompositingStrategy.Offscreen
                    }
                    .drawWithCache {
                        val path = Path()
                        path.addOval(
                            Rect(
                                topLeft = Offset.Zero,
                                bottomRight = Offset(size.width, size.height)
                            )
                        )
                        onDrawWithContent {
                            clipPath(path) {
                                // this draws the actual image - if you don't call drawContent, it wont
                                // render anything
                                this@onDrawWithContent.drawContent()
                            }
//                        this@onDrawWithContent.drawContent()

                            val dotSize = size.width / 8f
                            // Clip a white border for the content
                            drawCircle(
                                Color.Green,
                                radius = dotSize,
                                center = Offset(
                                    x = size.width - dotSize,
                                    y = size.height - dotSize
                                ),
                                blendMode = BlendMode.Clear
                            )
                            // draw the red circle indication
                            drawCircle(
                                Color(0xFFEF5350), radius = dotSize * 0.8f,
                                center = Offset(
                                    x = size.width - dotSize,
                                    y = size.height - dotSize
                                )
                            )
                        }

                    }

            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "自定义绘制修饰符", modifier = Modifier.flipped())

        Spacer(modifier = Modifier.height(16.dp))
    }
}

// 自定义绘制修饰符
class MyFlippedModifier:DrawModifier{
    override fun ContentDrawScope.draw() {
        scale(1f,-.5f){
            this@draw.drawContent()
        }
    }
}

private fun Modifier.flipped() = this then MyFlippedModifier()


private const val TEXT = """
    val textMeasurer = rememberTextMeasurer()

Spacer(
    modifier = Modifier
        .drawWithCache {
            val measuredText =
                textMeasurer.measure(
                    AnnotatedString(longTextSample),
                    constraints = Constraints.fixedWidth((size.width * 2f / 3f).toInt()),
                    style = TextStyle(fontSize = 18.sp)
                )

            onDrawBehind {
                drawRect(pinkColor, size = measuredText.size.toSize())
                drawText(measuredText)
            }
        }
        .fillMaxSize()
)
"""

