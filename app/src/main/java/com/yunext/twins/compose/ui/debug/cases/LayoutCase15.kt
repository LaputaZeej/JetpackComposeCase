package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathFillType
import androidx.compose.ui.graphics.PathOperation
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
fun LayoutCase15Preview() {
    LayoutCase15()
}

@Composable
fun LayoutCase15() {
    D.i("∆∆∆∆ LayoutCase15 ")
    val verticalScrollState = rememberScrollState()

    Column(modifier = MDF.verticalScroll(verticalScrollState)) {
        AsyncImage(
            model = ULR, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = MDF
                .aspectRatio(1f)
                .clip(CircleShape)
        )

        //val image =  ImageBitmap.imageResource(id = R.drawable.goldengate)
        Image(painter = painterResource(id = R.drawable.goldengate), contentDescription = null)

        AsyncImage(
            model = ULR, contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = MDF
                .size(200.dp)
                .background(xf98)

                .clip(MyShape())
        )
        Image(
            painter = painterResource(id = R.drawable.goldengate), contentDescription = null,
            contentScale = ContentScale.Crop,

            modifier = MDF
                .size(200.dp)
                .background(x98f)

                .clip(MyShape2())
        )
        val borderWidth = 8.dp
        Image(
            painter = painterResource(id = R.drawable.goldengate), contentDescription = null,
            contentScale = ContentScale.Crop,

            modifier = MDF
                .size(200.dp)
                .background(xf98)

                .border(borderWidth/4,Color.Yellow,MyShape2())
                .padding(borderWidth)
                .clip(MyShape2())
        )
        val rainbowColorsBrush = remember {
            Brush.sweepGradient(
                listOf(
                    Color(0xFF9575CD),
                    Color(0xFFBA68C8),
                    Color(0xFFE57373),
                    Color(0xFFFFB74D),
                    Color(0xFFFFF176),
                    Color(0xFFAED581),
                    Color(0xFF4DD0E1),
                    Color(0xFF9575CD)
                )
            )
        }
        Image(
            painter = painterResource(id = R.drawable.goldengate), contentDescription = null,
            contentScale = ContentScale.Crop,

            modifier = MDF
                .size(200.dp)
                .background(Color.LightGray)

                .border(borderWidth/4,rainbowColorsBrush, CircleShape)
                .padding(borderWidth)
                .clip(MyShape2())
        )

        Image(
            painter = painterResource(id = R.drawable.goldengate), contentDescription = null,
            contentScale = ContentScale.Crop,

            modifier = MDF
                .size(200.dp)
                .background(xf98)
                .border(borderWidth,rainbowColorsBrush, CircleShape)
                .padding(borderWidth)
                .clip(CircleShape)
        )
        Image(
            painter = painterResource(id = R.drawable.goldengate), contentDescription = null,
            contentScale = ContentScale.Crop,

            modifier = MDF
                    .width(200.dp)
                .aspectRatio(9/16f)
                .background(x98f)
                .padding(borderWidth)
                .border(borderWidth,rainbowColorsBrush, RoundedCornerShape(10.dp))
                //.padding(borderWidth)
                .clip(RoundedCornerShape(10.dp))
        )

        val rainbowImage = ImageBitmap.imageResource(id = R.drawable.goldengate)
        val dogImage = ImageBitmap.imageResource(id = R.drawable.death_valley)
        val customPainter = remember {
            OverlayImagePainter(dogImage, rainbowImage)
        }
        Image(
            painter = customPainter,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.wrapContentSize()
        )
    }
}

private class MyShape : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val path = Path().apply {
//            fillType = PathFillType.EvenOdd
            fillType = PathFillType.NonZero
            addArcRad(
                Rect(
                    left =0f,
                    top = 0f,
                    right = size.width/2 ,
                    bottom = size.height/2
                ), 0f, 60f
            )

//            addRect(Rect(
//                left =size.width/3,
//                top = size.height/3,
//                right = size.width ,
//                bottom = size.height
//            ))
        }
        val path2 = Path().apply {
//            fillType = PathFillType.EvenOdd
            fillType = PathFillType.NonZero
//            addArcRad(
//                Rect(
//                    left =0f,
//                    top = 0f,
//                    right = size.width/2 ,
//                    bottom = size.height/2
//                ), 0f, 60f
//            )

            addRect(Rect(
                left =size.width/3,
                top = size.height/3,
                right = size.width ,
                bottom = size.height
            ))
        }

        return Outline.Generic(path = Path.combine(path1 = path, path2 = path2, operation = PathOperation.Union))
    }

}
private class MyShape2 : Shape {
    override fun createOutline(
        size: Size,
        layoutDirection: LayoutDirection,
        density: Density,
    ): Outline {
        val offsetS  =size.width/10
        val offset  =offsetS*2
        val path = Path().apply {
            this.reset()

            this.moveTo(offset,offset)
            this.lineTo(size.width-offset,offset)
            this.lineTo(offset,size.height-offset)
            this.lineTo(size.width/2,0f)
            this.quadraticBezierTo(size.width-offset,size.height-offset,size.width/2,size.height/2)
            this.lineTo(offset,offset)

        }


        return Outline.Generic(path = path)
    }

}


class OverlayImagePainter constructor(
    private val image: ImageBitmap,
    private val imageOverlay: ImageBitmap,
    private val srcOffset: IntOffset = IntOffset.Zero,
    private val srcSize: IntSize = IntSize(image.width, image.height),
    private val overlaySize: IntSize = IntSize(imageOverlay.width, imageOverlay.height)
) : Painter() {

    private val size: IntSize = validateSize(srcOffset, srcSize)
    override fun DrawScope.onDraw() {
        // draw the first image without any blend mode
        drawImage(
            image,
            srcOffset,
            srcSize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            )
        )
        // draw the second image with an Overlay blend mode to blend the two together
        drawImage(
            imageOverlay,
            srcOffset,
            overlaySize,
            dstSize = IntSize(
                this@onDraw.size.width.roundToInt(),
                this@onDraw.size.height.roundToInt()
            ),
            blendMode = BlendMode.Overlay
        )
    }

    /**
     * Return the dimension of the underlying [ImageBitmap] as it's intrinsic width and height
     */
    override val intrinsicSize: Size get() = size.toSize()

    private fun validateSize(srcOffset: IntOffset, srcSize: IntSize): IntSize {
        require(
            srcOffset.x >= 0 &&
                    srcOffset.y >= 0 &&
                    srcSize.width >= 0 &&
                    srcSize.height >= 0 &&
                    srcSize.width <= image.width &&
                    srcSize.height <= image.height
        )
        return srcSize
    }
}

