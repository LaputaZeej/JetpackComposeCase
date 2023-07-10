package com.yunext.twins.compose.ui.debug.cases

import android.content.Context
import android.content.res.Resources
import android.graphics.BitmapShader
import android.graphics.Shader
import android.icu.text.CaseMap.Title
import android.media.Image
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.annotation.DrawableRes
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
import androidx.compose.foundation.layout.aspectRatio
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshotFlow
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
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.asAndroidBitmap
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.consumeAllChanges
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.input.pointer.positionChange
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.Velocity

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.view.ViewCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.DividerItemDecoration.VERTICAL
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.DebugViewModel
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import com.yunext.twins.compose.ui.debug.cases.compoents.XHuaLiDeFenGeXian
import com.yunext.twins.compose.ui.debug.cases.compoents.XTips
import com.yunext.twins.compose.ui.debug.cases.compoents.XTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import java.time.Clock.offset
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import kotlin.random.Random

private const val ULR =
    "https://img2.baidu.com/it/u=3853345508,384760633&fm=253&fmt=auto&app=120&f=JPEG?w=800&h=1200"

@Preview
@Composable
fun LayoutCase21Preview() {
    LayoutCase21()
}

// https://developer.android.com/jetpack/compose/animation?hl=zh-cn
// 手势和动画
@OptIn(
    ExperimentalTextApi::class, ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

@Composable
fun LayoutCase21() {
    D.i("∆∆∆∆ LayoutCase21 ")
    val verticalScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var test by remember {
        mutableStateOf(MyState(""))
    }
    MoviesScreen(
        snackbarHostState = SnackbarHostState(

        )
    )


}


@Composable
private fun LandingScreen(text: String, onTimeout: (String) -> Unit) {
    Text(text = "LaunchedEffect key变化一次重启一次")
    D.i("LandingScreen $text")
    // This will always refer to the latest onTimeout function that
    // LandingScreen was recomposed with
    //val currentOnTimeout by rememberUpdatedState(onTimeout)

    // Create an effect that matches the lifecycle of LandingScreen.
    // If LandingScreen recomposes, the delay shouldn't start again.
    LaunchedEffect(text) {
        D.i("LaunchedEffect a")
        delay(3000L)
        D.i("LaunchedEffect z")
        //currentOnTimeout(text)
        onTimeout(text)
    }

    /* Landing screen content */
    Text(
        text = "LandingScreen $text",
        Modifier
            .size(100.dp)
            .background(China.g_zhu_lv)
    )
}

@Composable
private fun LandingScreen2(text: String, onTimeout: (String) -> Unit) {
    Text(text = "LaunchedEffect key变化一次重启一次  rememberUpdatedState")
    D.i("LandingScreen $text")
    // This will always refer to the latest onTimeout function that
    // LandingScreen was recomposed with
    val currentOnTimeout by rememberUpdatedState(onTimeout)

    // Create an effect that matches the lifecycle of LandingScreen.
    // If LandingScreen recomposes, the delay shouldn't start again.
    LaunchedEffect(true) {
        D.i("LaunchedEffect a")
        delay(4000L)
        D.i("LaunchedEffect z")
        currentOnTimeout(text)
//        onTimeout(text)
    }

    /* Landing screen content */
    Text(
        text = "LandingScreen $text",
        Modifier
            .size(100.dp)
            .background(China.b_tian_lan)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MoviesScreen(snackbarHostState: SnackbarHostState) {

    // Creates a CoroutineScope bound to the MoviesScreen's lifecycle
    val scope = rememberCoroutineScope()

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        Column(
            Modifier
                .padding(contentPadding)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {

            XHuaLiDeFenGeXian(msg = "Side-effects")
            Button(
                onClick = {
                    // Create a new coroutine in the event handler to show a snackbar
                    scope.launch {
                        snackbarHostState.showSnackbar(
                            "Something happened! ${System.currentTimeMillis()}",
                            actionLabel = "close me",
                            withDismissAction = true,
                            duration = SnackbarDuration.Short
                        )
                    }
                }
            ) {
                Text("Press me")
            }


            XTitle(title = "rememberUpdatedState：在效应中引用某个值，该效应在值改变时不应重启")
            var count by remember {
                mutableStateOf(0)
            }
            Button(onClick = { count++ }) {
                Text(text = "count++", color = China.r_fen_hong)
            }
            LandingScreen("->$count") {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "key不同：在效应中引用某个值，该效应在值改变时不应重启 ${it}}",
                        actionLabel = "x"
                    )
                }
            }
            XTitle(title = "rememberUpdatedState保存某个参数或者状态的最新值，当被调用的时候，返回已保存的最新值。")
            LandingScreen2("->$count") {
                scope.launch {
                    snackbarHostState.showSnackbar(
                        "key不变：在效应中引用某个值，该效应在值改变时不应重启 ${it}}",
                        actionLabel = "close"
                    )
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
            MyComponent()

            XTitle(title = "DisposableEffect")
            var msg by remember {
                mutableStateOf("")
            }
            HomeScreen(onStart = {
                msg += "onStart \n"
            }, onStop = {
                msg += "onStop \n"
            })

            Text("当前：$msg", Modifier.padding(16.dp), color = China.r_yan_zhi_hong)

            XTitle(
                title = "SideEffect",
                "如需与非 Compose 管理的对象共享 Compose 状态，请使用 SideEffect 可组合项，因为每次成功重组时都会调用该可组合项。"
            )

            XTitle(
                title = "produceState：将非 Compose 状态转换为 Compose 状态",
                "produceState 会启动一个协程，该协程将作用域限定为可将值推送到返回的 State 的组合。使用此协程将非 Compose 状态转换为 Compose 状态，例如将外部订阅驱动的状态（如 Flow、LiveData 或 RxJava）引入组合。"
            )

            var changed by remember {
                mutableStateOf(false)
            }
            val img by loadNetworkImage(if (changed) R.drawable.goldengate else R.drawable.death_valley)
            Button(onClick = {
                changed = !changed
            }
            ) {

                Text(text = "loadNetworkImage")
            }
            when (img) {
                Result.Error -> {
                    Text(text = "load error", color = China.r_luo_xia_hong)
                }

                Result.Loading -> {
                    Text(text = "loading......")
                }

                is Result.Success<ImageBitmap> -> {
                    val tmp = (img as Result.Success<ImageBitmap>).data
                    androidx.compose.foundation.Image(bitmap = tmp, contentDescription = null)
                }
            }
            XTitle(
                title = "snapshotFlow：将 Compose 的 State 转换为 Flow",
                "使用 snapshotFlow 将 State<T> 对象转换为冷 Flow。snapshotFlow 会在收集到块时运行该块，并发出从块中读取的 State 对象的结果。当在 snapshotFlow 块中读取的 State 对象之一发生变化时，如果新值与之前发出的值不相等，Flow 会向其收集器发出新值（此行为类似于 Flow.distinctUntilChanged 的行为）。"
            )


            val listState = rememberLazyListState()

            val list by remember {
                mutableStateOf(TEXT.split("a").toList())
            }
            var countP by remember {
                mutableStateOf(0)
            }
            Text(text = "sendScrolledPastFirstItemEvent---> $countP")
            LazyColumn(state = listState, modifier = Modifier
                .height(300.dp)
                .fillMaxWidth()
                .background(China.b_tian_lan)) {
                items(list, key = { it }) {
                    Text(text = "* $it",Modifier.background(China.g_zhu_lv))
                }
            }


            LaunchedEffect(listState) {
                snapshotFlow { listState.firstVisibleItemIndex }
                    .map { index -> index > 0 }
                    .distinctUntilChanged()
                    .filter { it == true }
                    .collect {
                        //MyAnalyticsService.sendScrolledPastFirstItemEvent()
                        countP++
                    }
            }

            backPressHandler(true){
                countP++
            }
        }
    }
}


@Composable
fun backPressHandler(enabled:Boolean,onBackPressed:()->Unit) {
    val onBackPressedDispatcher = checkNotNull(LocalOnBackPressedDispatcherOwner.current) {
"No LocalOnBackPressedDispatcherOwner was provided "
    }.onBackPressedDispatcher
    val backCallback = remember{
            object :OnBackPressedCallback(enabled){
                override fun handleOnBackPressed() {
                    onBackPressed()
                }
            }
    }

    DisposableEffect(key1 =onBackPressedDispatcher ){
        onBackPressedDispatcher.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}

private sealed interface Result<out T> {
    object Loading : Result<Nothing>
    object Error : Result<Nothing>
    class Success<out T>(val data: T) : Result<T>
}

private class ImageRepository {
    suspend fun load(context: Context, @DrawableRes url: Int): ImageBitmap? {
        return coroutineScope {
            delay(1000)
            ImageBitmap.imageResource(context.resources, id = url)
//            null

//            Image(painter = painterResource(id = R.drawable.death_valley), contentDescription = null)
        }
    }
}

@Composable
private fun loadNetworkImage(
    @DrawableRes url: Int,
    imageRepository: ImageRepository = ImageRepository(),
): State<Result<ImageBitmap>> {

    // Creates a State<T> with Result.Loading as initial value
    // If either `url` or `imageRepository` changes, the running producer
    // will cancel and will be re-launched with the new inputs.
    val context = LocalContext.current.applicationContext
    return produceState<Result<ImageBitmap>>(initialValue = Result.Loading, url, imageRepository) {

        // In a coroutine, can make suspend calls
        //value = Result.Loading
        val image = imageRepository.load(context, url)

        // Update State with either an Error or Success result.
        // This will trigger a recomposition where this State is read
        value = if (image == null) {
            Result.Error
        } else {
            Result.Success(image)
        }
    }
}

private data class User(val name: String, val age: Int)
private class FirebaseAnalytics() {
    val map: MutableMap<String, String> = mutableMapOf()
}

@Composable
private fun rememberAnalytics(user: User): FirebaseAnalytics {
    val analytics: FirebaseAnalytics = remember {
        /* ... */
        FirebaseAnalytics()
    }

    // On every successful composition, update FirebaseAnalytics with
    // the userType from the current User, ensuring that future analytics
    // events have this metadata attached
    SideEffect {
        analytics.map["userType"] = user.name
    }
    return analytics
}

@Composable
private fun HomeScreen(
    lifecycleOwner: LifecycleOwner = LocalLifecycleOwner.current,
    onStart: () -> Unit, // Send the 'started' analytics event
    onStop: () -> Unit, // Send the 'stopped' analytics event
) {
    // Safely update the current lambdas when a new one is provided
    val currentOnStart by rememberUpdatedState(onStart)
    val currentOnStop by rememberUpdatedState(onStop)

    // If `lifecycleOwner` changes, dispose and reset the effect
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_START) {
                currentOnStart()
//                onStart()
            } else if (event == Lifecycle.Event.ON_STOP) {
                currentOnStop()
//                onStop()
            }
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    /* Home screen content */
}


//@Composable
//private fun MyComponent2() {
//    var count by remember { mutableStateOf(0) }
//    var data by remember { mutableStateOf(emptyList<Int>()) }
//    val memoizedCallback = remember {// some expensive computation
//    }
//
//    val shouldUpdate = rememberUpdatedState(count)
//    if (count > 5) {
//        return
//    }
//
//    Column {
//        Button(onClick = { count++ }) { Text("Increment Count") }
//        Button(onClick =
//        { data += count }) { Text("Add Data") }
//        Text("Count: $count")
//        Text("Data: ${data.joinToString()}")
//        memoizedCallback()
//    }
//}

@Composable
private fun MyComponent() {
    var count by remember { mutableStateOf(0) }
    val shouldUpdate = rememberUpdatedState(count)
    if (shouldUpdate.value > 5) {
        return
    }
    Button(onClick = { count++ }) { Text("Click Count: $count") }
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


class SmartSwipeRefreshState {
    private val indicatorOffsetAnimatable = Animatable(0.dp, Dp.VectorConverter)
    val indicatorOffset get() = indicatorOffsetAnimatable.value
    private val _indicatorOffsetFlow  = MutableStateFlow(0f)
    val indicatorOffsetFlow: Flow<Float> get() = _indicatorOffsetFlow
    val isSwipeInProgress by derivedStateOf { indicatorOffset != 0.dp }

    var isRefreshing: Boolean by mutableStateOf(false)

    fun updateOffsetDelta(value: Float) {
        _indicatorOffsetFlow.value = value
    }

    suspend fun snapToOffset(value: Dp) {
        indicatorOffsetAnimatable.snapTo(value)
    }

    suspend fun animateToOffset(value: Dp) {
        indicatorOffsetAnimatable.animateTo(value, tween(1000))
    }
}

private const val TAG = "SmartSwipe"
private class SmartSwipeRefreshNestedScrollConnection(
    val state: SmartSwipeRefreshState,
    val height: Dp
): NestedScrollConnection {
    override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
        Log.d(TAG, "onPreScroll")
        if (source == NestedScrollSource.Drag && available.y < 0) {
            state.updateOffsetDelta(available.y)
            return if (state.isSwipeInProgress) Offset(x = 0f, y = available.y) else Offset.Zero
        } else {
            return Offset.Zero
        }
    }

    override fun onPostScroll(
        consumed: Offset,
        available: Offset,
        source: NestedScrollSource
    ): Offset {
        Log.d(TAG, "onPostScroll")
        if (source == NestedScrollSource.Drag && available.y > 0) {
            state.updateOffsetDelta(available.y)
            return Offset(x = 0f, y = available.y)
        } else {
            return Offset.Zero
        }
    }

    override suspend fun onPreFling(available: Velocity): Velocity {
        Log.d(TAG, "onPreFling")
        if (state.indicatorOffset > height / 2) {
            state.animateToOffset(height)
            state.isRefreshing = true
        } else {
            state.animateToOffset(0.dp)
        }
        return super.onPreFling(available)
    }

    override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
        Log.d(TAG, "onPostFling")
        return super.onPostFling(consumed, available)
    }
}

@Composable
private fun SubcomposeSmartSwipeRefresh(
    indicator: @Composable () -> Unit,
    content: @Composable (Dp) -> Unit
) {
    SubcomposeLayout { constraints: Constraints ->
        var indicatorPlaceable = subcompose("indicator", indicator).first().measure(constraints)
        var contentPlaceable = subcompose("content") {
            content(indicatorPlaceable.height.toDp())
        }.map {
            it.measure(constraints)
        }.first()
        Log.d(TAG,"dp: ${indicatorPlaceable.height.toDp()}")
        layout(contentPlaceable.width, contentPlaceable.height) {
            contentPlaceable.placeRelative(0, 0)
        }
    }
}

@Composable
fun SmartSwipeRefresh(
    onRefresh: suspend () -> Unit,
    state: SmartSwipeRefreshState = remember { SmartSwipeRefreshState() },
    loadingIndicator: @Composable () -> Unit = { CircularProgressIndicator() },
    content: @Composable () -> Unit
) {
    SubcomposeSmartSwipeRefresh(indicator = loadingIndicator) { height ->
        val smartSwipeRefreshNestedScrollConnection = remember(state, height) {
            SmartSwipeRefreshNestedScrollConnection(state, height)
        }
        Box(
            Modifier
                .nestedScroll(smartSwipeRefreshNestedScrollConnection),
            contentAlignment = Alignment.TopCenter
        ) {
            Box(Modifier.offset(y = -height + state.indicatorOffset)) {
                loadingIndicator()
            }
            Box(Modifier.offset(y = state.indicatorOffset)) {
                content()
            }
        }
        var density = LocalDensity.current
        LaunchedEffect(Unit) {
            state.indicatorOffsetFlow.collect {
                var currentOffset = with(density) { state.indicatorOffset + it.toDp() }
                state.snapToOffset(currentOffset.coerceAtLeast(0.dp).coerceAtMost(height))
            }
        }
        LaunchedEffect(state.isRefreshing) {
            if (state.isRefreshing) {
                onRefresh()
                state.animateToOffset(0.dp)
                state.isRefreshing = false
            }
        }
    }
}

