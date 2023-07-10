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
import androidx.compose.foundation.layout.requiredSize
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
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageShader
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.TileMode
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
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontWeight
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
fun LayoutCase17Preview() {
    LayoutCase17()
}

// https://developer.android.com/jetpack/compose/graphics/draw/brush?hl=zh-cn
@OptIn(ExperimentalTextApi::class)
@Composable
fun LayoutCase17() {
    D.i("∆∆∆∆ LayoutCase17 ")
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

        val brush = Brush.horizontalGradient(listOf(Color.Red, Color.Blue))
        Canvas(
            modifier = Modifier.size(200.dp),
            onDraw = {
                drawCircle(brush)
            }
        )

        Spacer(modifier = Modifier.height(16.dp))

        val colorStops = arrayOf(
            0.0f to Color.Yellow,
            0.2f to Color.Red,
            1f to Color.Blue
        )
        Box(
            modifier = Modifier
                .requiredSize(200.dp)
                .background(Brush.horizontalGradient(colorStops = colorStops))
        )

        Spacer(modifier = Modifier.height(16.dp))

        val listColors = listOf(Color.Yellow, Color.Red, Color.Blue)
        val tileSize = with(LocalDensity.current) {
            50.dp.toPx()
        }
        Box(
            modifier = Modifier
                .requiredSize(200.dp)
                .background(
                    Brush.horizontalGradient(
                        listColors,
                        endX = tileSize,
                        tileMode = TileMode.Repeated
                    )
                )
        )
        Spacer(modifier = Modifier.height(16.dp))


        //val listColors = listOf(Color.Yellow, Color.Red, Color.Blue)
        val customBrush = remember {
            object : ShaderBrush() {
                override fun createShader(size: Size): Shader {
                    return LinearGradientShader(
                        colors = listColors,
                        from = Offset.Zero,
                        to = Offset(size.width / 4f, 0f),
                        tileMode = TileMode.Mirror
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .requiredSize(200.dp)
                .background(customBrush)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(
                    Brush.radialGradient(
                        listOf(Color(0xFF2be4dc), Color(0xFF243484))
                    )
                )
        )

        Spacer(modifier = Modifier.height(16.dp))

        val largeRadialGradient = object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                val biggerDimension = maxOf(size.height, size.width)
                return RadialGradientShader(
                    colors = listOf(Color(0xFF2be4dc), Color(0xFF243484)),
                    center = size.center,
                    radius = biggerDimension / 2f,
                    colorStops = listOf(0f, 0.95f)
                )
            }
        }

        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth()
                .background(largeRadialGradient)
        )

        Spacer(modifier = Modifier.height(16.dp))
        T1()
        Spacer(modifier = Modifier.height(16.dp))
T2()
        Spacer(modifier = Modifier.height(16.dp))


    }
}

@Composable
private fun T1() {
    val colorStops = arrayOf(
        0.0f to Color.Yellow,
        0.2f to Color.Red,
        1f to Color.Blue
    )
    val brush = Brush.horizontalGradient(colorStops = colorStops)
    Box(
        modifier = Modifier
            .requiredSize(200.dp)
            .drawBehind {
                drawRect(brush = brush) // will allocate a shader to occupy the 200 x 200 dp drawing area
                inset(10f) {
                    /* Will allocate a shader to occupy the 180 x 180 dp drawing area as the
                     inset scope reduces the drawing  area by 10 pixels on the left, top, right,
                    bottom sides */
                    drawRect(brush = brush)
                    inset(5f) {
                        /* will allocate a shader to occupy the 170 x 170 dp drawing area as the
                         inset scope reduces the  drawing area by 5 pixels on the left, top,
                         right, bottom sides */
                        drawRect(brush = brush)
                    }
                }
            }
    )
}

@OptIn(ExperimentalTextApi::class)
@Composable
private fun T2() {
    val imageBrush =
        ShaderBrush(ImageShader(ImageBitmap.imageResource(id = R.drawable.goldengate)))

// Use ImageShader Brush with background
    Box(
        modifier = Modifier
            .requiredSize(200.dp)
            .background(imageBrush)
    )

// Use ImageShader Brush with TextStyle
    Text(
//        text = "Hello Android!",
        text = TEXT,
        style = TextStyle(
            brush = imageBrush,
            fontWeight = FontWeight.ExtraBold,
            fontSize = 36.sp
        )
    )

// Use ImageShader Brush with DrawScope#drawCircle()
    Canvas(onDraw = {
        drawCircle(imageBrush)
    }, modifier = Modifier.size(200.dp))
}

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

