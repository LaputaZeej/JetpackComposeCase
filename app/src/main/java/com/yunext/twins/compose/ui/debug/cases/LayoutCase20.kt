package com.yunext.twins.compose.ui.debug.cases

import android.content.res.Resources
import android.graphics.BitmapShader
import android.graphics.Shader
import android.icu.text.CaseMap.Title
import android.os.Parcelable
import android.util.Log
import android.view.LayoutInflater
import android.view.VelocityTracker
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.listSaver
import androidx.compose.runtime.saveable.mapSaver
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.UiMode
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
import com.yunext.twins.compose.ui.debug.DebugViewModel
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import com.yunext.twins.compose.ui.debug.cases.compoents.XHuaLiDeFenGeXian
import com.yunext.twins.compose.ui.debug.cases.compoents.XTips
import com.yunext.twins.compose.ui.debug.cases.compoents.XTitle
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
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
fun LayoutCase20Preview() {
    LayoutCase20()
}

// https://developer.android.com/jetpack/compose/animation?hl=zh-cn
// 手势和动画
@OptIn(
    ExperimentalTextApi::class, ExperimentalAnimationApi::class, ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class
)

@Composable
fun LayoutCase20() {
    D.i("∆∆∆∆ LayoutCase20 ")
    val verticalScrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    var test by remember {
        mutableStateOf(MyState(""))
    }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            test = (MyState(System.currentTimeMillis().toString()))
        }
    }

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
        Spacer(modifier = Modifier.height(16.dp))
        MyComposable(myState = test)
        Spacer(modifier = Modifier.height(16.dp))
        HelloContent()

//        val vm = DebugViewModel()
//        vm.cases.collectAsState()
        XTitle(title = "在 Compose 中恢复状态")
        CityScreen()

        XHuaLiDeFenGeXian(msg = "Compose 中的状态容器")
        XTips(
            tips = "关键术语：状态容器用于管理可组合项的逻辑和状态。\n" +
                    "\n" +
                    "请注意，在其他资料中，状态容器也称为“提升的状态对象”。\n" +
                    "\n"
        )

        var avatarRes by remember {
            mutableStateOf(false)
        }
        Button(onClick = { avatarRes = !avatarRes }) {
            Text(text = "changed avatarRes $avatarRes")
        }
        BackgroundBanner(if (avatarRes) R.drawable.death_valley else R.drawable.goldengate)
        Spacer(modifier = Modifier.height(16.dp))
        BackgroundBanner2(if (avatarRes) R.drawable.death_valley else R.drawable.goldengate)
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f / 2)
                .background(BackgroundState(if (avatarRes) R.drawable.death_valley else R.drawable.goldengate).value)
        ) {
            // ...
        }
        Spacer(modifier = Modifier.height(16.dp))
        var key by remember {
            mutableStateOf(1)
        }
        Button(onClick = { key++ }) {
            Text(text = "key ++ $key")
        }
        TestA(key.toString())
        val text by produceText(key.toString())
        Text(text = text)

        XHuaLiDeFenGeXian(msg = "Side-effects")

    }
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
        Column(Modifier.padding(contentPadding).size(400.dp)) {
            Button(
                onClick = {
                    // Create a new coroutine in the event handler to show a snackbar
                    scope.launch {
                        snackbarHostState.showSnackbar("Something happened!")
                    }
                }
            ) {
                Text("Press me")
            }
        }
    }
}



// Marking the type as stable to favor skipping and smart recompositions.
interface XResult<T>

@Stable
interface UiState<T : XResult<T>> {
    val value: T?
    val exception: Throwable?

    val hasError: Boolean
        get() = exception != null
}

class StringResult : XResult<StringResult>
class StringUiState2 : UiState<StringResult> {
    override val value: StringResult?
        get() = TODO("Not yet implemented")
    override val exception: Throwable?
        get() = TODO("Not yet implemented")
}

class StringUiState() :
    XResult<StringUiState>, UiState<StringUiState> {
    override val value: StringUiState?
        get() = TODO("Not yet implemented")
    override val exception: Throwable?
        get() = TODO("Not yet implemented")

}

//abstract class MyUiState2<T>:UiState<Result<T>>{
//
//}

//class MyUiState2:UiState<Result<String>>
//class MyUiState3:UiState<String>
//
//
//class MyUiState4(private val delegate:UiState<String>) :UiState<String> by delegate{
//
//}


//class MyUiState1 : UiState<Result<Nothing>> {
//    override val value: Result<Nothing>?
//        get() = Result.success(TODO())
//    override val exception: Throwable?
//        get() = IllegalStateException("")
//
//}


class MyUiState4 : UiState<Nothing> {
    override val value: Nothing?
        get() = throw IllegalStateException("")
    override val exception: Throwable?
        get() = IllegalStateException("")

}

//val uiState = object : UiState<String> {
//    override val value: String?
//        get() = TODO("Not yet implemented")
//    override val exception: Throwable?
//        get() = TODO("Not yet implemented")
//
//}


fun CoroutineScope.demo() {
    val job = this.launch {
        val jobA = launch {
            delay(5000)
            println("jobA")
        }
        jobA.invokeOnCompletion {
            println("jobA completion")
        }
        val jobB = launch {
            delay(1000)
            println("jobB")
        }
        jobB.invokeOnCompletion {
            println("jobB completion")
        }
    }
    job.invokeOnCompletion {
        println("job completion")
    }
    this.launch {
        delay(3000)
        job.cancel()
    }
}

@Composable
fun TestA(key: String) { // 2.更新了key=1
    var x: String = remember(key) {
        "abc"
    }
    var y: String by remember(key) {
        mutableStateOf("def")
    }
    Text(text = "$x@$key", modifier = Modifier
        .padding(16.dp)
        .clickable {
            x += x
            D.i("TestA x=$x")
        })
    Text(text = "$y@$key", modifier = Modifier //3.显示了def@1 ，y=defdef无效了。
        .padding(16.dp)
        .clickable {
            y += y // 1.点击显示defdef@0
            D.i("TestA y=$y")
        })
}

@Composable
fun produceText(id: String): State<String> {
    return remember(id) {
        mutableStateOf("$id->${System.currentTimeMillis()}")
    }
}

@Composable
private fun BackgroundState(@DrawableRes avatarRes: Int): State<ShaderBrush> {
    val res: Resources = LocalContext.current.resources
    val r = remember(avatarRes) {
        mutableStateOf(
            ShaderBrush(
                BitmapShader(
                    ImageBitmap.imageResource(res, avatarRes).asAndroidBitmap(),
                    Shader.TileMode.REPEAT,
                    Shader.TileMode.REPEAT
                )
            )
        )

    }
    return r
}

@Composable
private fun BackgroundBanner(
    @DrawableRes avatarRes: Int,
    modifier: Modifier = Modifier,
    res: Resources = LocalContext.current.resources,
) {
    val brush = remember(key1 = avatarRes) {
        ShaderBrush(
            BitmapShader(
                ImageBitmap.imageResource(res, avatarRes).asAndroidBitmap(),
                Shader.TileMode.REPEAT,
                Shader.TileMode.REPEAT
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f / 2)
            .background(brush)
    ) {
        // ...
    }
}

@Composable
private fun BackgroundBanner2(
    @DrawableRes avatarRes: Int,
    modifier: Modifier = Modifier,
    res: Resources = LocalContext.current.resources,
) {
    val brush by remember(key1 = avatarRes) {
        mutableStateOf(
            ShaderBrush(
                BitmapShader(
                    ImageBitmap.imageResource(res, avatarRes).asAndroidBitmap(),
                    Shader.TileMode.REPEAT,
                    Shader.TileMode.REPEAT
                )
            )
        )
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f / 2)
            .background(brush)
    ) {
        // ...
    }
}

@Parcelize
data class City(val name: String, val country: String) : Parcelable

val CitySaver = run {
    val nameKey = "Name"
    val countryKey = "Country"
    mapSaver(
        save = { mapOf(nameKey to it.name, countryKey to it.country) },
        restore = { City(it[nameKey] as String, it[countryKey] as String) }
    )
}

val CitySaverList = listSaver<City, Any>(
    save = { listOf(it.name, it.country) },
    restore = { City(it[0] as String, it[1] as String) }
)


@Composable
private fun CityScreen() {
    var selectedCity = rememberSaveable {
        mutableStateOf(City("Madrid", "Spain"))
    }

    var selectedCity2 = rememberSaveable(stateSaver = CitySaver) {
        mutableStateOf(City("Madrid", "Spain"))
    }

    var selectedCity3 = rememberSaveable(stateSaver = CitySaverList) {
        mutableStateOf(City("Madrid", "Spain"))
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HelloContent() {
    Column(modifier = Modifier.padding(16.dp)) {
        var name: String by remember { mutableStateOf("") }
        val nameTemp = name ?: return@Column
        if (nameTemp.isNotEmpty()) {
            Text(
                text = "Hello, $name!",
                modifier = Modifier.padding(bottom = 8.dp),
                style = MaterialTheme.typography.bodySmall
            )
        }
        OutlinedTextField(
            value = nameTemp,
            onValueChange = { name = it },
            label = { Text("Name") }
        )
    }
}


//@Stable()
class MyState(var abc: String) {
    private val efg: String = ""
    val hij: Int = Random.nextInt(100)
    override fun toString(): String {
        return efg
    }

//    override fun equals(other: Any?): Boolean {
//        return (other as? MyState)?.efg?.equals(this.efg) ?: false
//    }

    override fun hashCode(): Int {
        var result = abc.hashCode()
        result = 31 * result + efg.hashCode()
        return result
    }
}

@Composable
fun MyComposable(myState: MyState) {
    D.i("MyComposable ${myState.abc}")
    Text(text = "${myState.abc}")
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

