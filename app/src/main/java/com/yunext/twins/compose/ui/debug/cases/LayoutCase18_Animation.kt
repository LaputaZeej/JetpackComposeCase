package com.yunext.twins.compose.ui.debug.cases

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
import androidx.compose.animation.with
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
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
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import com.yunext.twins.compose.ui.debug.cases.compoents.XHuaLiDeFenGeXian
import com.yunext.twins.compose.ui.debug.cases.compoents.XTips
import com.yunext.twins.compose.ui.debug.cases.compoents.XTitle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

private const val ULR =
    "https://img2.baidu.com/it/u=3853345508,384760633&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1200"

@Preview
@Composable
fun LayoutCase18Preview() {
    LayoutCase18()
}

// https://developer.android.com/jetpack/compose/animation?hl=zh-cn

@OptIn(
    ExperimentalTextApi::class, ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)
@Composable
fun LayoutCase18() {
    D.i("∆∆∆∆ LayoutCase18 ")
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
            .padding(32.dp)
    ) {
        XTitle(title = "AnimatedVisibility", desc = "")
        var editable by remember { mutableStateOf(false) }
        Button(onClick = { editable = !editable }) {
            Text(text = "${if (editable) "隐藏" else "显示"}")
        }
        AnimatedVisibility(visible = editable) {
            Text(
                text = TEXT,
                Modifier
                    .requiredHeightIn(min = 40.dp)
            )
        }
        Spacer(modifier = Modifier.height(16.dp))


        var visible by remember { mutableStateOf(false) }
        Button(onClick = { visible = !visible }) {
            Text(text = "${if (visible) "隐藏" else "显示"}")
        }
        val density = LocalDensity.current
        AnimatedVisibility(
            visible = visible,
            enter = slideInVertically {
                // Slide in from 40 dp from the top.
                with(density) { -40.dp.roundToPx() }
            } + expandVertically(
                // Expand from the top.
                expandFrom = Alignment.Top
            ) + fadeIn(
                // Fade in with the initial alpha of 0.3f.
                initialAlpha = 0.3f
            ),
            exit = slideOutVertically() + shrinkVertically() + fadeOut()
        ) {
            Text(
                TEXT + TEXT + TEXT,
                Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            )
        }


        Spacer(modifier = Modifier.height(16.dp))

        // Create a MutableTransitionState<Boolean> for the AnimatedVisibility.
        val coroutineScope = rememberCoroutineScope()
        val state = remember {
            MutableTransitionState(false).apply {
                // Start the animation immediately.
                coroutineScope.launch {
                    while (true) {
                        delay(2000)
                        targetState = !targetState
                    }
                }

            }
        }

        Column(Modifier.height(100.dp)) {
            AnimatedVisibility(visibleState = state) {
                Text(text = "Hello, world!")
            }

            // Use the MutableTransitionState to know the current animation state
            // of the AnimatedVisibility.
            Text(
                text = when {
                    state.isIdle && state.currentState -> "Visible"
                    !state.isIdle && state.currentState -> "Disappearing"
                    state.isIdle && !state.currentState -> "Invisible"
                    else -> "Appearing"
                }
            )
        }

        Spacer(modifier = Modifier.height(16.dp))
        var visible2 by remember { mutableStateOf(true) }
        Button(onClick = { visible2 = !visible2 }) {
            Text(text = "${if (visible2) "隐藏" else "显示"}")
        }
        AnimatedVisibility(
            visible = visible2,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            // Fade in/out the background and the foreground.
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Color.DarkGray)
            ) {
                Box(
                    Modifier
                        .align(Alignment.Center)
                        .animateEnterExit(
                            // Slide in/out the inner box.
                            enter = slideInVertically(),
                            exit = slideOutVertically()
                        )
                        .sizeIn(minWidth = 256.dp, minHeight = 64.dp)
                        .background(Color.Red)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.goldengate),
                        contentDescription = null
                    )
                }
            }
        }
        XTitle(title = "添加自定义动画", desc = "")
        var visible3 by remember { mutableStateOf(false) }
        Button(onClick = { visible3 = !visible3 }) {
            Text(text = "${if (visible3) "隐藏" else "显示"}")
        }
        AnimatedVisibility(
            visible = visible3,
            enter = fadeIn(),
            exit = fadeOut()
        ) { // this: AnimatedVisibilityScope
            // Use AnimatedVisibilityScope#transition to add a custom animation
            // to the AnimatedVisibility.
            val background by transition.animateColor(label = "") { state ->
                if (state == EnterExitState.Visible) Color.Blue else Color.Red
            }
            Box(
                modifier = Modifier
                    .size(128.dp)
                    .background(background)
            )
        }
        XTitle(
            title = "animate*AsState",
            desc = "animate*AsState 函数是 Compose 中最简单的动画 API，用于为单个值添加动画效果。您只需提供结束值（或目标值），该 API 就会从当前值开始向指定值播放动画"
        )
        var enabled by remember { mutableStateOf(false) }
        Button(onClick = { enabled = !enabled }) {
            Text(text = "${if (enabled) "关闭" else "打开"}")
        }
        val alpha: Float by animateFloatAsState(
            if (enabled) 1f else 0.1f,
            animationSpec = tween(500, 1000)
        )
        Box(
            Modifier
                .size(200.dp)
                .graphicsLayer(alpha = alpha)
                .background(Color.Red)
        )
        XTitle(
            "animate*AnimatedContent（实验性)",
            "AnimatedContent 可组合项会在内容根据目标状态发生变化时，为内容添加动画效果。"
        )

        Row {
            var count by remember { mutableStateOf(0) }
            Button(onClick = { count++ }) {
                Text("Add")
            }
            Button(onClick = { count-- }) {
                Text("Minus")
            }
            AnimatedContent(targetState = count) { targetCount ->
                // Make sure to use `targetCount`, not `count`.
                Text(text = "Count: $targetCount")
            }
            Spacer(modifier = Modifier.width(16.dp))
            AnimatedContent(
                targetState = count,
                transitionSpec = {
                    // Compare the incoming number with the previous number.
                    if (targetState > initialState) {
                        // If the target number is larger, it slides up and fades in
                        // while the initial (smaller) number slides up and fades out.
                        slideInVertically { height -> height } + fadeIn() with
                                slideOutVertically { height -> -height } + fadeOut()
                    } else {
                        // If the target number is smaller, it slides down and fades in
                        // while the initial number slides down and fades out.
                        slideInVertically { height -> -height } + fadeIn() with
                                slideOutVertically { height -> height } + fadeOut()
                    } using (
                            // Disable clipping since the faded slide-in/out should
                            // be displayed out of bounds.
                            SizeTransform(clip = false)
                            )
                }
            ) { targetCount ->
                Text(
                    text = "$targetCount", modifier = Modifier
                        .border(1.dp, Color.Red)
                        .padding(16.dp)
                )
            }
        }

        var expanded by remember { mutableStateOf(false) }
        Button(onClick = { expanded = !expanded }) {
            Text(text = "${if (expanded) "关闭" else "打开"}")
        }
        Surface(
            color = MaterialTheme.colorScheme.primary,
            onClick = { expanded = !expanded }
        ) {
            AnimatedContent(
                targetState = expanded,
                transitionSpec = {
                    fadeIn(animationSpec = tween(150, 150)) with
                            fadeOut(animationSpec = tween(150)) using
                            SizeTransform { initialSize, targetSize ->
                                if (targetState) {
                                    keyframes {
                                        // Expand horizontally first.
                                        IntSize(targetSize.width, initialSize.height) at 150
                                        durationMillis = 300
                                    }
                                } else {
                                    keyframes {
                                        // Shrink vertically first.
                                        IntSize(initialSize.width, targetSize.height) at 150
                                        durationMillis = 300
                                    }
                                }
                            }
                }
            ) { targetExpanded ->
                if (targetExpanded) {
                    Text(TEXT)
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.goldengate),
                        contentDescription = ""
                    )
                }
            }
        }

        XTitle(
            title = "animateContentSize",
            desc = "animateContentSize 修饰符可为大小变化添加动画效果。"
        )
        var message by remember { mutableStateOf("Hello") }
        Box(
            modifier = Modifier
                .background(Color.Yellow)
                .animateContentSize()
        ) {
            Text(text = message, modifier = Modifier.combinedClickable(onClick = {
                message += message
            }, onLongClick = {
                message = "hello compose"
            }))
        }
        var expanded2 by remember { mutableStateOf(false) }
        Button(onClick = { expanded2 = !expanded2 }) {
            Text(text = "${if (expanded2) "关闭" else "打开"}")
        }
        Box(
            modifier = Modifier
                .height(if (expanded2) 50.dp else 200.dp)
                .animateContentSize()
        ) {
            XTips("animateContentSize 在修饰符链中的位置顺序很重要。为了确保流畅的动画，请务必将其放置在任何大小修饰符（如 size 或 defaultMinSize）前面，以确保 animateContentSize 会将带动画效果的值的变化报告给布局")
        }
        XTitle(
            title = "Crossfade",
            desc = "Crossfade 可使用淡入淡出动画在两个布局之间添加动画效果。通过切换传递给 current 参数的值，可以使用淡入淡出动画来切换内容。"
        )
        var currentPage by remember { mutableStateOf("A") }
        var expanded3 by remember { mutableStateOf(true) }
        Button(onClick = {
            expanded3 = !expanded3
            currentPage = if (expanded3) "A" else "B"
        }) {
            Text(text = "${if (expanded3) "关闭" else "打开"}")
        }

        Crossfade(targetState = currentPage) { screen ->
            when (screen) {
                "A" -> Text("Page A")
                "B" -> XTips("Page B")
            }
        }

        XTitle(
            title = "updateTransition",
            desc = "Transition 可管理一个或多个动画作为其子项，并在多个状态之间同时运行这些动画。"
        )

        var currentState by remember { mutableStateOf(BoxState.Collapsed) }
        Button(onClick = {
            currentState = when (currentState) {
                BoxState.Collapsed -> BoxState.Expanded
                BoxState.Expanded -> BoxState.Collapsed
            }
        }) {
            Text(text = "${currentState}")
        }
        val transition = updateTransition(currentState, label = "")
        val rect by transition.animateRect(label = "") { state ->
            when (state) {
                BoxState.Collapsed -> Rect(0f, 0f, 100f, 100f)
                BoxState.Expanded -> Rect(100f, 100f, 300f, 300f)
            }
        }
        val borderWidth by transition.animateDp(label = "") { state ->
            when (state) {
                BoxState.Collapsed -> 4.dp
                BoxState.Expanded -> 0.dp
            }
        }
        val color by transition.animateColor(
            transitionSpec = {
                when {
                    BoxState.Expanded isTransitioningTo BoxState.Collapsed ->
                        spring(stiffness = 50f)

                    else ->
                        tween(durationMillis = 500)
                }
            }, label = ""
        ) { state ->
            when (state) {
                BoxState.Collapsed -> China.g_zhu_lv//MaterialTheme.colors.primary
                BoxState.Expanded -> China.h_gu_huang//MaterialTheme.colors.background
            }
        }

        Box(
            Modifier
                .size(200.dp)
                .border(width = borderWidth, color = color, shape = CircleShape)
                .drawBehind {
                    drawRect(color = color, topLeft = rect.topLeft, size = rect.size)
                }
        ) {

        }

        XTips(tips = "对于涉及多个可组合函数的更复杂的过渡，可使用 createChildTransition 来创建子过渡。此方法对于在复杂的可组合项中分离多个子组件之间的关注点非常有用。父过渡将会知道子过渡中的所有动画值。")
        Spacer(modifier = Modifier.height(16.dp))
        Demo01()
        XTitle(
            title = "封装 Transition 并使其可重复使用",
            desc = "对于简单的用例，在与界面相同的可组合项中定义过渡动画是一种非常有效的选择方案。但是，在处理具有大量动画值的复杂组件时，您可能会希望将动画实现与可组合界面分开。\n" +
                    "\n" +
                    "为此，您可以创建一个类来保存所有动画值，同时创建一个“update”函数来返回该类的实例。过渡实现可提取到新的独立函数中。当您需要集中处理动画逻辑或使复杂动画可重复使用时，这种模式很有用。"
        )

        var currentState2 by remember { mutableStateOf(BoxState.Collapsed) }
        Button(onClick = {
            currentState2 = when (currentState2) {
                BoxState.Collapsed -> BoxState.Expanded
                BoxState.Expanded -> BoxState.Collapsed
            }
        }) {
            Text(text = "${currentState2}")
        }
        AnimatingBox(currentState2)

        XHuaLiDeFenGeXian("低级别动画 API")

        XTitle(
            title = "Animatable",
            desc = "Animatable 是一个值容器，它可以在通过 animateTo 更改值时为值添加动画效果。该 API 支持 animate*AsState 的实现。它可确保一致的连续性和互斥性，这意味着值变化始终是连续的，并且会取消任何正在播放的动画。"
        )

        var ok by remember {
            mutableStateOf(false)
        }
        val colorA = remember { Animatable(China.b_hai_tian_lan) }
        LaunchedEffect(key1 = ok, currentState2) {
            if (currentState2 == BoxState.Collapsed) {

                colorA.animateTo(
                    if (ok) China.h_ji_dan_huang else China.z_wu_mei_zi,
                    animationSpec = tween(1 * 1000)
                )
            } else {

                colorA.animateTo(
                    if (ok) China.h_bei_gua_huang else China.g_bo_he_lv,
                    animationSpec = tween(2 * 1000)
                )
            }
        }

        Box(modifier = Modifier
            .size(200.dp)
            .background(colorA.value)
            .clickable {
                coroutineScope.launch {
                    ok = !ok
                    //colorA.animateTo(if (ok) China.h_bei_gua_huang else China.g_bo_he_lv, animationSpec = tween(1000))
                }
            })

        XTitle(
            title = "Animation",
            desc = "Animation 是可用的最低级别的 Animation API。到目前为止，我们看到的许多动画都是基于 Animation 构建的。Animation 子类型有两种：TargetBasedAnimation 和 DecayAnimation。"
        )
        val anim = remember {
            TargetBasedAnimation(
                animationSpec = tween(9 * 1000),
                typeConverter = Float.VectorConverter,
                initialValue = 200f,
                targetValue = 10 * 000f
            )
        }
        var playTime by remember { mutableStateOf(0L) }
        var text by remember {
            mutableStateOf("")
        }

        LaunchedEffect(anim) {
            val startTime = withFrameNanos { it }

            do {
                playTime = withFrameNanos { it } - startTime
                val animationValue = anim.getValueFromNanos(playTime)
                text = "-> $animationValue"
            } while (true)
        }
        Text(text = "$text", modifier = Modifier.clickable {

        })

        XHuaLiDeFenGeXian("自定义动画")
        XTitle(
            title = "AnimationSpec",
            desc = "大多数动画 API 允许开发者通过可选的 AnimationSpec 参数来自定义动画规范。\n" +
                    "\n" +
                    "\n"
        )


        var stiffnessIndex by remember {
            mutableStateOf(0)
        }
        val list by remember {
            mutableStateOf(listOf(
                    StiffnessHigh,
            StiffnessMedium,
            StiffnessMediumLow,
            StiffnessLow,
            StiffnessVeryLow
            ))
        }
        val stiffness = list[stiffnessIndex]
        Button(onClick = {
            stiffnessIndex++
            if (stiffnessIndex > list.size - 1) {
                stiffnessIndex = 0
            }
        }) {
            Text(text = "stiffness[${stiffnessIndex}] = $stiffness")
        }
        var openA by remember {
            mutableStateOf(false)
        }
        Button(onClick = { openA = !openA }) {
            Text(text = "$openA")
        }
        val defaultHeight = 50.dp
        val defaultWidth = 300.dp
        val distance1 by animateDpAsState(
            targetValue = if (openA) 0.dp else (defaultWidth - defaultHeight),
            animationSpec = spring(
                DampingRatioHighBouncy, stiffness
            )
        )
        val distance2 by animateDpAsState(
            targetValue = if (openA) 0.dp else (defaultWidth - defaultHeight),
            animationSpec = spring(
                DampingRatioMediumBouncy, stiffness
            )
        )
        val distance3 by animateDpAsState(
            targetValue = if (openA) 0.dp else (defaultWidth - defaultHeight),
            animationSpec = spring(
                DampingRatioLowBouncy, stiffness
            )
        )
        val distance4 by animateDpAsState(
            targetValue = if (openA) 0.dp else (defaultWidth - defaultHeight),
            animationSpec = spring(
                DampingRatioNoBouncy, stiffness
            )
        )
        val distance5 by animateDpAsState(
            targetValue = if (openA) 0.dp else (defaultWidth - defaultHeight),
            animationSpec = spring(
                2f, stiffness
            )
        )
        Text(text = "DampingRatioHighBouncy", color = China.hui_xiao_hui)
        Row(
            Modifier
                .height(defaultHeight)
                .width(defaultWidth)
                .background(China.hui_xiao_hui)
        ) {
            Spacer(modifier = Modifier.width(distance1))
            Spacer(
                modifier = Modifier
                    .height(defaultHeight)
                    .width(defaultHeight)
                    .background(China.r_yan_zhi_hong)
            )
        }
        Text(text = "DampingRatioMediumBouncy", color = China.hui_xiao_hui)
        Row(
            Modifier
                .height(defaultHeight)
                .width(defaultWidth)
                .background(China.hui_xiao_hui)
        ) {
            Spacer(modifier = Modifier.width(distance2))
            Spacer(
                modifier = Modifier
                    .height(defaultHeight)
                    .width(defaultHeight)
                    .background(China.r_yan_zhi_hong)
            )
        }
        Text(text = "DampingRatioLowBouncy", color = China.hui_xiao_hui)
        Row(
            Modifier
                .height(defaultHeight)
                .width(defaultWidth)
                .background(China.hui_xiao_hui)
        ) {
            Spacer(modifier = Modifier.width(distance3))
            Spacer(
                modifier = Modifier
                    .height(defaultHeight)
                    .width(defaultHeight)
                    .background(China.r_yan_zhi_hong)
            )
        }
        Text(text = "DampingRatioNoBouncy", color = China.hui_xiao_hui)
        Row(
            Modifier
                .height(defaultHeight)
                .width(defaultWidth)
                .background(China.hui_xiao_hui)
        ) {
            Spacer(modifier = Modifier.width(distance4))
            Spacer(
                modifier = Modifier
                    .height(defaultHeight)
                    .width(defaultHeight)
                    .background(China.r_yan_zhi_hong)
            )
        }
        Text(text = "2f", color = China.hui_xiao_hui)
        Row(
            Modifier
                .height(defaultHeight)
                .width(defaultWidth)
                .background(China.hui_xiao_hui)
        ) {
            Spacer(modifier = Modifier.width(distance5))
            Spacer(
                modifier = Modifier
                    .height(defaultHeight)
                    .width(defaultHeight)
                    .background(China.r_yan_zhi_hong)
            )
        }
        XTitle(title = "tween", desc = "")
        val value by animateFloatAsState(
            targetValue = if (openA) 2f else 0.5f,
            animationSpec = tween(
                durationMillis = 300,
                delayMillis = 50,
                easing = CubicBezierEasing(1f, 2f, 3f, 4f)
            )
        )
        Text(text = "2f", color = China.hui_xiao_hui)
        Row(
            Modifier
                .size(100.dp * value)
                .background(China.g_zhu_lv)
        ) {

        }
        XTitle(title = "keyframes", desc = "keyframes 会根据在动画时长内的不同时间戳中指定的快照值添加动画效果。在任何给定时间，动画值都将插值到两个关键帧值之间。对于其中每个关键帧，您都可以指定 Easing 来确定插值曲线。")
        var openB by remember {
            mutableStateOf(false)
        }
        val value2 by animateFloatAsState(
            targetValue = if (openB) 2f else 0.0f,
            animationSpec = keyframes {
                durationMillis = 375
                0.0f at 0 with LinearOutSlowInEasing // for 0-15 ms
                0.2f at 15 with FastOutLinearInEasing // for 15-75 ms
                0.4f at 75 // ms
                0.4f at 225 // ms
            }
        )
        Row(
            Modifier
                .size(100.dp * (1 + value2))
                .background(China.r_yan_zhi_hong)
                .clickable {
                    openB = !openB
                }
        ) {

        }
        XTitle(title = "repeatable/infiniteRepeatable", desc ="repeatable 反复运行基于时长的动画（例如 tween 或 keyframes），直至达到指定的迭代计数。您可以传递 repeatMode 参数来指定动画是从头开始 (RepeatMode.Restart) 还是从结尾开始 (RepeatMode.Reverse) 重复播放。" )

        val value3 by animateFloatAsState(
            targetValue = if (openB) 2f else 0.0f,
            animationSpec = repeatable(
                iterations = 13,
                animation = tween(durationMillis = 300),
                repeatMode = RepeatMode.Reverse
            )
        )

        Row(
            Modifier
                .size(100.dp * (1 + value3))
                .background(China.r_yan_zhi_hong)
                .clickable {
                    openB = !openB
                }
        ) {

        }

        val value4 by animateFloatAsState(
            targetValue = if (openB) 2f else 0.0f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 300),
                repeatMode = RepeatMode.Reverse
            )
        )

        Row(
            Modifier
                .size(100.dp * (1 + value4))
                .background(China.g_zhu_lv)
                .clickable {
                    openB = !openB
                }
        ) {

        }

        XTitle(title = "snap", desc = "")
        XTitle(title = "Easing", desc = "基于时长的 AnimationSpec 操作（如 tween 或 keyframes）使用 Easing 来调整动画的小数值。这样可让动画值加速和减速，而不是以恒定的速率移动。小数是介于 0（起始值）和 1.0（结束值）之间的值，表示动画中的当前点。\n" +
                "\n" +
                "Easing 实际上是一个函数，它取一个介于 0 和 1.0 之间的小数值并返回一个浮点数。返回的值可能位于边界之外，表示过冲或下冲。您可以使用如下所示的代码创建一个自定义 Easing。")
        XTitle(title = "AnimationVector", desc ="大多数 Compose 动画 API 都支持将 Float、Color、Dp 以及其他基本数据类型作为开箱即用的动画值，但有时您需要为其他数据类型（包括您的自定义类型）添加动画效果。在动画播放期间，任何动画值都表示为 AnimationVector。使用相应的 TwoWayConverter 即可将值转换为 AnimationVector，反之亦然，这样一来，核心动画系统就可以统一对其进行处理")
        XHuaLiDeFenGeXian(msg = "动画形式的矢量资源（实验性）")
//        AnimatedVectorDrawable()
        XHuaLiDeFenGeXian(msg = "列表项动画")
        XHuaLiDeFenGeXian(msg = "手势和动画（高级）")
    }


}


@OptIn(ExperimentalAnimationGraphicsApi::class)
@Composable
fun AnimatedVectorDrawable() {
    val image = AnimatedImageVector.animatedVectorResource(R.drawable.ic_launcher_background)
    var atEnd by remember { mutableStateOf(false) }
    Image(
        painter = rememberAnimatedVectorPainter(image, atEnd),
        contentDescription = "Timer",
        modifier = Modifier.clickable {
            atEnd = !atEnd
        },
        contentScale = ContentScale.Crop
    )
}

@Composable
private fun AnimatingBox(boxState: BoxState) {
    //val updateTransition1 = updateTransition(targetState = boxState, "")
    val updateTransition = updateTransitionData(boxState)
    Box(
        modifier = Modifier
            .background(updateTransition.color)
            .size(updateTransition.size)
    )
}

// holds the animation values.
private class TransitionData(color: State<Color>, size: State<Dp>) {
    val color by color
    val size by size
}

@Composable
private fun updateTransitionData(boxState: BoxState): TransitionData {
    val updateTransition = updateTransition(targetState = boxState, "")
    val color =
        updateTransition.animateColor(label = "", transitionSpec = { tween(2000) }) { state ->
            when (state) {
                BoxState.Collapsed -> China.b_tian_lan
                BoxState.Expanded -> China.r_chen_xi_hong
            }
        }
    val size = updateTransition.animateDp(label = "") { state ->
        when (state) {
            BoxState.Collapsed -> 64.dp
            BoxState.Expanded -> 128.dp
        }
    }
    return remember(updateTransition) {
        TransitionData(color = color, size = size)
    }

}


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Demo01() {
    var selected by remember { mutableStateOf(false) }
// Animates changes when `selected` is changed.
    val transition = updateTransition(selected, label = "")
    val borderColor by transition.animateColor(label = "") { isSelected ->
        if (isSelected) Color.Magenta else Color.White
    }
    val elevation by transition.animateDp(label = "") { isSelected ->
        if (isSelected) 10.dp else 2.dp
    }
    Surface(
        modifier = Modifier.clickable { selected = !selected },
        shape = RoundedCornerShape(8.dp),
        border = BorderStroke(2.dp, borderColor),
        shadowElevation = elevation

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(text = "Hello, world!")
            // AnimatedVisibility as a part of the transition.
            transition.AnimatedVisibility(
                visible = { targetSelected -> targetSelected },
                enter = expandVertically(),
                exit = shrinkVertically()
            ) {
                Text(text = "It is fine today.")
            }
            // AnimatedContent as a part of the transition.
            transition.AnimatedContent { targetState ->
                if (targetState) {

//                    Text(text = "Selected")
                    XTips(tips = "Selected")
                } else {
                    Icon(imageVector = Icons.Default.Star, contentDescription = "Phone")
                }
            }
        }
    }
}

private enum class BoxState {
    Collapsed,
    Expanded
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

