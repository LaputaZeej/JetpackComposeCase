package com.yunext.twins.compose.ui.debug.cases

import android.icu.text.CaseMap.Title
import android.view.LayoutInflater
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.animation.Animatable
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.EnterExitState
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.animateColor
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.FastOutLinearInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.Spring.DampingRatioHighBouncy
import androidx.compose.animation.core.Spring.DampingRatioLowBouncy
import androidx.compose.animation.core.Spring.DampingRatioMediumBouncy
import androidx.compose.animation.core.Spring.DampingRatioNoBouncy
import androidx.compose.animation.core.Spring.StiffnessHigh
import androidx.compose.animation.core.Spring.StiffnessLow
import androidx.compose.animation.core.Spring.StiffnessMedium
import androidx.compose.animation.core.Spring.StiffnessMediumLow
import androidx.compose.animation.core.Spring.StiffnessVeryLow
import androidx.compose.animation.core.TargetBasedAnimation
import androidx.compose.animation.core.VectorConverter
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateRect
import androidx.compose.animation.core.calculateTargetValue
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.repeatable
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.graphics.ExperimentalAnimationGraphicsApi
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.animation.splineBasedDecay
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.gestures.draggable
import androidx.compose.foundation.gestures.horizontalDrag
import androidx.compose.foundation.gestures.rememberDraggableState
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.rememberTransformableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.gestures.transformable
import androidx.compose.foundation.interaction.DragInteraction
import androidx.compose.foundation.interaction.Interaction
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.interaction.collectIsFocusedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredHeightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import com.yunext.twins.compose.ui.debug.cases.compoents.XHuaLiDeFenGeXian
import com.yunext.twins.compose.ui.debug.cases.compoents.XTips
import com.yunext.twins.compose.ui.debug.cases.compoents.XTitle
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.time.Clock.offset
import kotlin.math.absoluteValue
import kotlin.math.roundToInt

private const val ULR =
    "https://img2.baidu.com/it/u=3853345508,384760633&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1200"

@Preview
@Composable
fun LayoutCase19Preview() {
    LayoutCase19()
}

// https://developer.android.com/jetpack/compose/animation?hl=zh-cn
// 手势和动画
@OptIn(
    ExperimentalTextApi::class, ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

@Composable
fun LayoutCase19() {
    D.i("∆∆∆∆ LayoutCase19 ")
    val verticalScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = MDF
            .verticalScroll(verticalScrollState)
            .fillMaxSize()
            .padding(32.dp)
    ) {
        var editable by remember { mutableStateOf(false) }
        Button(onClick = { editable = !editable }) {
            Text(text = if (editable) "隐藏" else "显示")
        }

        XHuaLiDeFenGeXian(msg = "手势和动画（高级）")

        Gesture()

        Box(
            Modifier
                .size(200.dp)
                .background(if (editable) China.g_zhu_lv else China.h_bei_gua_huang)
                .swipeToDismiss {
                    editable = !editable
                }) {

        }
        MyComposable()

        XHuaLiDeFenGeXian(msg = "轻触和输入")
        XTitle(
            title = "手势",
            desc = "Compose 提供了多种 API，可帮助您检测用户互动生成的手势。API 涵盖各种用例：\n" +
                    "\n" +
                    "其中一些级别较高，旨在覆盖最常用的手势。例如，clickable 修饰符可用于轻松检测点击，此外它还提供无障碍功能，并在点按时显示视觉指示（例如涟漪）。\n" +
                    "\n" +
                    "还有一些不太常用的手势检测器，它们在较低级别提供更大的灵活性，例如 PointerInputScope.detectTapGestures 或 PointerInputScope.detectDragGestures，但不提供额外功能。"
        )
        ClickableSample()
        Spacer(modifier = Modifier.height(16.dp))
        ClickableSample2()
        Spacer(modifier = Modifier.height(16.dp))
        ScrollBoxes()
        Spacer(modifier = Modifier.height(16.dp))
        ScrollBoxesSmooth()
        XTitle(
            title = "可滚动的修饰符scrollable",
            desc = "scrollable 修饰符与滚动修饰符不同，区别在于 scrollable 可检测滚动手势，但不会偏移其内容。此修饰符只有在指定了 ScrollableState 的情况下，才能正常工作。构造 ScrollableState 时，您必须提供一个 consumeScrollDelta 函数，该函数将在每个滚动步骤调用（通过手势输入、流畅滚动或快速滑动），并且增量以像素为单位。该函数必须返回所消耗的滚动距离，以确保在存在具有 scrollable 修饰符的嵌套元素时，可以正确传播相应事件。"
        )
        XTips(tips = "scrollable 修饰符不会影响它所应用到的元素的布局。这意味着，对元素布局或其子级进行的任何更改都必须通过由 ScrollableState 提供的增量进行处理。另外请务必注意，scrollable 不会考虑子级的布局，这意味着它无需测量子级，即可传播滚动增量。")
        ScrollableSample()
        Spacer(modifier = Modifier.height(16.dp))
        XTitle(
            title = "自动浅套滚动",
            desc = "简单的嵌套滚动无需您执行任何操作。启动滚动操作的手势会自动从子级传播到父级，这样一来，当子级无法进一步滚动时，手势就会由其父元素处理。\n" +
                    "\n" +
                    "部分 Compose 组件和修饰符原生支持自动嵌套滚动，包括：verticalScroll、horizontalScroll、scrollable、Lazy API 和 TextField。这意味着，当用户滚动嵌套组件的内部子级时，之前的修饰符会将滚动增量传播到支持嵌套滚动的父级。"
        )
        val gradient = Brush.verticalGradient(0f to China.g_lan_lv, 1000f to China.r_yan_zhi_hong)
        Box(
            modifier = Modifier
                .size(300.dp)
                .background(China.b_tian_lan)
                .verticalScroll(rememberScrollState())
                .padding(32.dp)
        ) {
            Column {
                repeat(6) {
                    Box(
                        modifier = Modifier
                            .height(128.dp)
                            .verticalScroll(rememberScrollState())
                    ) {
                        Text(
                            "Scroll here",
                            modifier = Modifier
                                .border(12.dp, Color.DarkGray)
                                .background(brush = gradient)
                                .padding(24.dp)
                                .height(150.dp)
                        )
                    }
                }
            }
        }
        XTitle(title = "包含子级 AndroidView 的父级可组合项", desc = "")
        NestedScrollInteropComposeParentWithAndroidChildExample()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NestedScrollInteropComposeParentWithAndroidChildExample() {
    val toolbarHeightPx = with(LocalDensity.current) { 56.dp.toPx() }
    val toolbarOffsetHeightPx = remember { mutableStateOf(0f) }

    // Sets up the nested scroll connection between the Box composable parent
    // and the child AndroidView containing the RecyclerView
    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                // Updates the toolbar offset based on the scroll to enable
                // collapsible behaviour
                val delta = available.y
                val newOffset = toolbarOffsetHeightPx.value + delta
                toolbarOffsetHeightPx.value = newOffset.coerceIn(-toolbarHeightPx, 0f)
                return Offset.Zero
            }
        }
    }

    Box(
        Modifier
            .height(400.dp)
            .width(300.dp)
            .nestedScroll(nestedScrollConnection)
    ) {
        TopAppBar(
            title = {
                Text(text = "title")
            },
            navigationIcon = {

            },
            actions = {

            },
            modifier = Modifier
                .height(56.dp)
                .background(China.b_tian_lan)
                .offset { IntOffset(x = 0, y = toolbarOffsetHeightPx.value.roundToInt()) }
        )

        AndroidView(
            { context ->
                LayoutInflater.from(context)
                    .inflate(R.layout.view_in_compose_nested_scroll_interop, null).apply {
                        with(findViewById<RecyclerView>(R.id.recycler)) {
                            layoutManager =
                                LinearLayoutManager(context, RecyclerView.VERTICAL, false)
                            adapter = NestedScrollInteropAdapter()
                        }
                    }.also {
                        // Nested scrolling interop is enabled when
                        // nested scroll is enabled for the root View
                        ViewCompat.setNestedScrollingEnabled(it, true)
                    }
            },
            // ...
        )
    }

    XHuaLiDeFenGeXian(msg = "拖动")
    var offsetX by remember { mutableStateOf(0f) }
    var state by remember {
        mutableStateOf("")
    }
    Text(
        modifier = Modifier
            .offset { IntOffset(offsetX.roundToInt(), 0) }
            .background(China.g_lan_lv)
            .draggable(
                orientation = Orientation.Horizontal,
                state = rememberDraggableState { delta ->
                    offsetX += delta
                },
                onDragStarted = {
                    state = "Drag ing !"
                },
                onDragStopped = {
                    state = "Drag me!"
                }
            )
            .padding(16.dp),
        text = state,
        color = China.w_qian_shi_bai
    )

    Box(
        modifier = Modifier
            .size(300.dp)
            .background(China.g_zhu_lv)
    ) {
        var offsetX by remember { mutableStateOf(0f) }
        var offsetY by remember { mutableStateOf(0f) }

        Box(
            Modifier
                .offset { IntOffset(offsetX.roundToInt(), offsetY.roundToInt()) }
                .background(China.r_yan_zhi_hong)
                .size(50.dp)
                .pointerInput(Unit) {
                    detectDragGestures { change, dragAmount ->
                        change.consumeAllChanges()
                        offsetX += dragAmount.x
                        offsetY += dragAmount.y
                    }
                }
        )
    }
    XHuaLiDeFenGeXian(msg = "滑动")
    XTitle(
        title = "滑动 swipeable",
        desc = "您可以使用 swipeable 修饰符拖动元素，释放后，这些元素通常朝一个方向定义的两个或多个锚点呈现动画效果。其常见用途是实现“滑动关闭”模式。\n" +
                "\n" +
                "请务必注意，此修饰符不会移动元素，而只检测手势。您需要保存状态并在屏幕上表示，例如通过 offset 修饰符移动元素。"
    )
//    SwipeableSample()
    XHuaLiDeFenGeXian(msg = "多点触控：平移、缩放、旋转")

    TransformableSample()
    XHuaLiDeFenGeXian(msg = "处理交互")
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()


    Button(
        onClick = { /* do something */ },
        interactionSource = interactionSource
    ) {
        Text(if (isPressed) "Pressed!" else "Not pressed")
    }
    Spacer(modifier = Modifier.height(16.dp))
    XTitle(title = "使用 InteractionSource")
    val interactionSource2 = remember { MutableInteractionSource() }
    val isPressedOrDragged by interactionSource2.collectIsPressedOrDraggedAsState()
    Button(
        onClick = { /* do something */ },
        interactionSource = interactionSource2
    ) {
        Text(if (isPressedOrDragged) "Pressed Or Dragged!" else "Not pressed or Dragged")
    }
    Spacer(modifier = Modifier.height(16.dp))
    PressIconButton(onClick = { },
        icon = {
            Icon( imageVector= Icons.Default.Add,"")
        },
        text = {
            Text(text = "click")
        }
    )
}

@Composable
private fun PressIconButton(
    onClick: () -> Unit,
    icon: @Composable () -> Unit,
    text: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    interactionSource: MutableInteractionSource =
        remember { MutableInteractionSource() },
) {
    val isPressed by interactionSource.collectIsPressedAsState()
    Button(
        onClick = onClick, modifier = modifier,
        interactionSource = interactionSource
    ) {
        AnimatedVisibility(visible = isPressed) {
            if (isPressed) {
                Row {
                    icon()
                    Spacer(Modifier.size(ButtonDefaults.IconSpacing))
                }
            }
        }
        val scale = if(isPressed) 2f else 1f
        Box(modifier=Modifier.scale(scale,scale)){
            text()
        }
    }
}


@Composable
fun InteractionSource.collectIsPressedOrDraggedAsState(): State<Boolean> {
    val isPressedOrDragged = remember { mutableStateOf(false) }
    val interactions = remember { mutableStateListOf<Interaction>() }
    LaunchedEffect(key1 = isPressedOrDragged) {
        this@collectIsPressedOrDraggedAsState.interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    interactions.add(interaction)
                }

                is PressInteraction.Release -> {
                    interactions.remove(interaction.press)
                }

                is PressInteraction.Cancel -> {
                    interactions.remove(interaction.press)
                }

                is DragInteraction.Start -> {
                    interactions.add(interaction)
                }

                is DragInteraction.Stop -> {
                    interactions.remove(interaction.start)
                }

                is DragInteraction.Cancel -> {
                    interactions.remove(interaction.start)
                }
            }
            isPressedOrDragged.value = interactions.isNotEmpty()
        }
    }

    return isPressedOrDragged

}

@Composable
private fun TransformableSample() {
    // set up all transformation states
    var scale by remember { mutableStateOf(1f) }
    var rotation by remember { mutableStateOf(0f) }
    var offset by remember { mutableStateOf(Offset.Zero) }
    val state = rememberTransformableState { zoomChange, offsetChange, rotationChange ->
        scale *= zoomChange
        rotation += rotationChange
        offset += offsetChange
    }
    Box(
        Modifier
            // apply other transformations like rotation and zoom
            // on the pizza slice emoji
            .graphicsLayer(
                scaleX = scale,
                scaleY = scale,
                rotationZ = rotation,
                translationX = offset.x,
                translationY = offset.y
            )
            // add transformable to listen to multitouch transformation events
            // after offset
            .transformable(state = state)
            .background(China.g_zhu_lv)
            .size(200.dp)
    ) {
        Spacer(
            modifier = Modifier
                .size(48.dp)
                .background(China.r_luo_xia_hong)
        )
    }
}

//@Composable
// fun SwipeableSample() {
//    val width = 96.dp
//    val squareSize = 48.dp
//    val swipeableState = rememberSwipeableState(0)
//    val sizePx = with(LocalDensity.current) { squareSize.toPx() }
//    val anchors = mapOf(0f to 0, sizePx to 1) // Maps anchor points (in px) to states
//
//    Box(
//        modifier = Modifier
//            .width(width)
//            .swipeable(
//                state = swipeableState,
//                anchors = anchors,
//                thresholds = { _, _ -> FractionalThreshold(0.3f) },
//                orientation = Orientation.Horizontal
//            )
//            .background(Color.LightGray)
//    ) {
//        Box(
//            Modifier
//                .offset { IntOffset(swipeableState.offset.value.roundToInt(), 0) }
//                .size(squareSize)
//                .background(Color.DarkGray)
//        )
//    }
//}

private class NestedScrollInteropAdapter :
    RecyclerView.Adapter<NestedScrollInteropAdapter.NestedScrollInteropViewHolder>() {
    val items = (1..10).map { it.toString() }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): NestedScrollInteropViewHolder {
        return NestedScrollInteropViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.view_in_compose_nested_scroll_interop_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: NestedScrollInteropViewHolder, position: Int) {
        // ...
        holder.bind("->${items[position]}")
    }

    class NestedScrollInteropViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind(item: String) {
            // ...
            itemView.findViewById<TextView>(R.id.tv).text = item
        }
    }
    // ...
}

@Composable
private fun ScrollableSample() {
    // actual composable state
    var offset by remember { mutableStateOf(0f) }
    var deltaS by remember { mutableStateOf(0f) }
    Box(
        Modifier
            .height(200.dp)
            .fillMaxWidth()
            .scrollable(
                orientation = Orientation.Vertical,
                // Scrollable state: describes how to consume
                // scrolling delta and update offset
                state = rememberScrollableState { delta ->
                    deltaS = delta
                    offset += delta
                    delta
                }
            )
            .background(
                Brush.verticalGradient(
                    listOf(
                        China.b_hai_tian_lan,
                        China.r_chen_xi_hong
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Text("$offset----$deltaS")
        Spacer(modifier = Modifier

            .offset {
                IntOffset(offset.toDp().value.toInt(), 0)
            }
            .size(20.dp)
            .background(China.g_lan_lv)

        )
    }
}

@Composable
private fun ScrollBoxesSmooth() {

    // Smoothly scroll 100px on first composition
    val state = rememberScrollState()
    LaunchedEffect(Unit) {
        delay(5000)
        state.animateScrollTo(100)
    }

    Column(
        modifier = Modifier
            .background(Color.LightGray)
            .size(100.dp)
            .padding(horizontal = 8.dp)
            .verticalScroll(state)
    ) {
        repeat(10) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
private fun ScrollBoxes() {
    Column(
        modifier = Modifier
            .background(China.h_bei_gua_huang)
            .size(100.dp)
            .verticalScroll(rememberScrollState())
    ) {
        repeat(10) {
            Text("Item $it", modifier = Modifier.padding(2.dp))
        }
    }
}

@Composable
private fun ClickableSample2() {
    val count = remember { mutableStateOf(0) }
    var text by remember { mutableStateOf("") }
    // content that you want to make clickable
    Text(
        text = count.value.toString(),
        modifier = Modifier
            .background(China.h_ji_dan_huang)
            .pointerInput(Unit) {
                detectTapGestures(
                    onDoubleTap = {
                        text += " onDoubleTap"
                    },
                    onLongPress = {
                        text += " onLongPress"
                    },
                    onPress = {
                        text += " onPress"
                    },
                    onTap = {
                        text += " onTap"
                        count.value = count.value + 1
                    }
                )
            }
            .padding(32.dp),
        color = China.z_wu_mei_zi,
        fontSize = 22.sp
    )
    Text(text = text, color = China.r_fen_hong, fontWeight = FontWeight.Light, fontSize = 12.sp)
}

@Composable
private fun ClickableSample() {
    val count = remember { mutableStateOf(0) }
    // content that you want to make clickable
    Text(
        text = count.value.toString(),
        modifier = Modifier
            .background(China.h_ji_dan_huang)
            .clickable { count.value += 1 }
            .padding(32.dp),
        color = China.z_wu_mei_zi,
        fontSize = 22.sp

    )
}

@Composable
private fun Gesture() {
    val offset = remember { Animatable(Offset(0f, 0f), Offset.VectorConverter) }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(China.b_hai_tian_lan)
            .pointerInput(Unit) {
                coroutineScope {
                    while (true) {
                        // Detect a tap event and obtain its position.
                        val position = awaitPointerEventScope {
                            awaitFirstDown().position
                        }
                        launch {
                            // Animate to the tap position.
                            offset.animateTo(position)
                            D.i("   offset.animateTo($position)")
                        }
                    }
                }
            }
    ) {
        Box(modifier = Modifier
            .offset {
                D.i("offset-> ${offset.value}")
                offset.value.toIntOffset()
            }
            .size(90.dp)
            .drawBehind {
                drawCircle(China.r_fen_hong)
            }
        )
        Box(modifier = Modifier
            .size(90.dp)
            .drawBehind {
                drawCircle(China.r_fen_hong)
            }
            .offset {
                IntOffset(100, 100)
            }
        )
    }
}


@Preview
@Composable
fun MyComposablePreview() {
    MyComposable()
}

@Composable
fun MyComposable() {
    Column(
        Modifier
            .size(200.dp)
            .background(Color.Black)
    ) {
        Box(
            Modifier
                .size(50.dp)
//                .border(1.dp,Color.Red)
                .drawBehind {
                    drawCircle(China.r_fen_hong)
                }
                .offset(20.dp)
                .drawBehind {
                    drawCircle(China.r_yan_zhi_hong)
                }
                .offset(20.dp)
                .drawBehind {
                    drawCircle(China.g_zhu_lv)
                }
                .offset(20.dp)
                .border(1.dp, China.h_gu_huang)
        ) {

        }
        Box(
            Modifier
                .offset(20.dp)
                .size(50.dp)
//                .border(1.dp,Color.Red)
                .drawBehind {
                    drawCircle(China.r_fen_hong)
                }
                .offset(20.dp)
                .drawBehind {
                    drawCircle(China.r_yan_zhi_hong)
                }
                .offset(20.dp)
                .drawBehind {
                    drawCircle(China.g_zhu_lv)
                }
                .border(1.dp, China.h_gu_huang)


        ) {

        }
    }
}


private fun Offset.toIntOffset() = IntOffset(x.roundToInt(), y.roundToInt())


fun Modifier.swipeToDismiss(
    onDismissed: () -> Unit,
): Modifier = composed {
    val offsetX = remember { Animatable(0f) }
    pointerInput(Unit) {
        // Used to calculate fling decay.
        val decay = splineBasedDecay<Float>(this)
        // Use suspend functions for touch events and the Animatable.
        coroutineScope {
            while (true) {
                // Detect a touch down event.
                val pointerId = awaitPointerEventScope { awaitFirstDown().id }
                val velocityTracker = androidx.compose.ui.input.pointer.util.VelocityTracker()
                // Stop any ongoing animation.
                offsetX.stop()
                awaitPointerEventScope {
                    horizontalDrag(pointerId) { change ->
                        // Update the animation value with touch events.
                        launch {
                            offsetX.snapTo(
                                offsetX.value + change.positionChange().x
                            )
                        }
                        velocityTracker.addPosition(
                            change.uptimeMillis,
                            change.position
                        )
                    }
                }
                // No longer receiving touch events. Prepare the animation.
                val velocity = velocityTracker.calculateVelocity().x
                val targetOffsetX = decay.calculateTargetValue(
                    offsetX.value,
                    velocity
                )
                // The animation stops when it reaches the bounds.
                offsetX.updateBounds(
                    lowerBound = -size.width.toFloat(),
                    upperBound = size.width.toFloat()
                )
                launch {
                    if (targetOffsetX.absoluteValue <= size.width) {
                        // Not enough velocity; Slide back.
                        offsetX.animateTo(
                            targetValue = 0f,
                            initialVelocity = velocity
                        )
                    } else {
                        // The element was swiped away.
                        offsetX.animateDecay(velocity, decay)
                        onDismissed()
                    }
                }
            }
        }
    }
        .offset { IntOffset(offsetX.value.roundToInt(), 0) }
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

