package com.yunext.twins.compose.ui.debug.cases

import android.text.style.AlignmentSpan
import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PageSize
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.PointMode
import androidx.compose.ui.graphics.addOutline
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp

import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yunext.twins.compose.R
import com.yunext.twins.compose.route.navigateSingleTopTo
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.data.TangShiSongCi
import com.yunext.twins.compose.ui.theme.x98f
import com.yunext.twins.compose.ui.theme.xf98
import kotlinx.coroutines.launch


@Preview
@Composable
fun LayoutCase11Preview() {
    LayoutCase11()
}

@Composable
fun LayoutCase11() {
    D.i("∆∆∆∆ LayoutCase11 ")
    val list = List(5) { it }
    MyNavHost(list)
}


@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MyNavHost(list: List<Int>,) {
    val rememberNavController = rememberNavController()
//    val rememberPagerStateDebug = rememberPagerState()
    val currentBackStackEntryAsState by rememberNavController.currentBackStackEntryAsState()
    var index by remember {
        mutableStateOf(0)
    }
    val current = currentBackStackEntryAsState?.destination?.route
    D.e("current = $current")
    NavHost(
        navController = rememberNavController, startDestination = current?:"MAIN"
    ) {
        composable("TEST") {
            TestPage()
        }
        composable("MAIN") { entry ->
            MainPage(null,list, index,toTest={
                rememberNavController.navigate("TEST") {
                    popUpTo("MAIN") {
//                        inclusive =false
                        saveState = true
                    }
                    restoreState = true
                }
            }) { selected ->
                index = selected

            }
            BackHandler() {

            }
        }
    }
}

@Composable
private fun TestPage() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "TEST")
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun MainPage(
    debugState: PagerState? = null,
    list: List<Int>,
    selectedIndex: Int,
    toTest: () -> Unit,
    onPageSelected: (Int) -> Unit,
) {

    val rememberPagerState = rememberPagerState(initialPage = selectedIndex)
//    val rememberPagerState = debugState
    val rememberCoroutineScope = rememberCoroutineScope()
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        HorizontalPager(
            pageCount = list.size, modifier = MDF.padding(bottom = 48.dp),
            pageSize = PageSize.Fill, state = rememberPagerState
        ) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(text = "MAIN $it", modifier = Modifier.clickable {
                    toTest()
                })
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .height(48.dp)
                .background(xf98)
                .selectableGroup()
                .align(Alignment.BottomCenter),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Bottom
        ) {
            for (i in list) {
                Text(text = " Tab $i",
                    color = if (rememberPagerState.currentPage == i) Color.Red else Color.Black,
                    modifier = MDF
                        .weight(1f)
                        .height(48.dp)
                        .wrapContentHeight()
                        .clickable {
                            if (rememberPagerState.currentPage != i) {
                                rememberCoroutineScope.launch {
                                    rememberPagerState.animateScrollToPage(i)
                                }
                                onPageSelected(i)
                            }
                        }
                )
            }
        }
    }
}

