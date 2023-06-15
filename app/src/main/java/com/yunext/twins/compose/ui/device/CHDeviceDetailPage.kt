package com.yunext.twins.compose.ui.device

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TabPosition
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavBackStackEntry
import com.yunext.twins.compose.MainViewModel
import com.yunext.twins.compose.R
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.ui.common.CHBackgroundBlock
import com.yunext.twins.compose.ui.common.CHTitle
import com.yunext.twins.compose.ui.common.LabelTextBlock

private enum class DeviceTab(val text: Int) {
    Properties(R.string.tab_properties),
    Events(R.string.tab_properties),
    Services(R.string.tab_events)
    ;
}

@Composable
fun CHDeviceDetailPage(
    navBackStackEntry: NavBackStackEntry,
    viewModel: MainViewModel,
    onLeft: () -> Unit,
    onRight: () -> Unit,
) {
    LaunchedEffect(key1 = "connectAndRefresh") {
        viewModel.connectAndRefresh()
    }
    val curDevice: DeviceAndState? by viewModel.curDeviceAndStateFlow.collectAsState()
    // The currently selected tab.
    var curTab by remember { mutableStateOf(DeviceTab.Properties) }
    val device = curDevice
    CHBackgroundBlock()
    if (device == null) {
        Spacer(modifier = Modifier.height(100.dp))
        Text(text = "device is empty")
    } else {
        Column(modifier = Modifier.fillMaxSize()) {
            Column(modifier = Modifier.background(Color(1f, 1f, 1f, 0.7f))) {
                CHTitle(text = device.name, leftClick = {
                    onLeft()
                }, rightClick = {
                    onRight()
                })

                CHDeviceDetail(device = device)
            }

//            Tab(modifier = Modifier.padding(16.dp), curTab) {
//                curTab = it
//            }

            CHTab(modifier = Modifier.padding(16.dp), curTab) {
                curTab = it
            }


        }


    }


}

@Composable
private fun CHDeviceDetail(
    device: DeviceAndState,
    modifier: Modifier = Modifier,
) {
    val constraints = ConstraintSet {
        val deviceId = createRefFor("deviceId")
        val deviceIdTitle = createRefFor("deviceIdTitle")
        val deviceType = createRefFor("deviceType")
        val deviceTypeTitle = createRefFor("deviceTypeTitle")

        constrain(deviceIdTitle) {
            top.linkTo(parent.top)
            bottom.linkTo(deviceTypeTitle.top)
            start.linkTo(parent.start)
        }

        constrain(deviceTypeTitle) {
            top.linkTo(deviceIdTitle.bottom, margin = 7.5.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
        }


        constrain(deviceId) {
            top.linkTo(deviceIdTitle.top)
            bottom.linkTo(deviceIdTitle.bottom)
            start.linkTo(deviceIdTitle.end, margin = 4.dp)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }

        constrain(deviceType) {
            top.linkTo(deviceTypeTitle.top)
            bottom.linkTo(deviceTypeTitle.bottom)
            start.linkTo(deviceTypeTitle.end, margin = 4.dp)
            end.linkTo(parent.end)
            width = Dimension.fillToConstraints
        }
    }
    ConstraintLayout(
        constraintSet = constraints, modifier = modifier
            .fillMaxWidth()
            .height(66.dp)
            .padding(start = 16.dp, end = 16.dp)
//            .border(width = 1.dp, color = Color.Red)

    ) {
        LabelTextBlock(stringResource(id = R.string.device_cid), Modifier.layoutId("deviceIdTitle"))

        LabelTextBlock(
            stringResource(id = R.string.device_type),
            Modifier.layoutId("deviceTypeTitle")
        )

        Text(
            text = device.name,
            Modifier.layoutId("deviceId"),
            fontSize = 11.sp,
            color = Color(0xff999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = device.name,
            fontSize = 12.sp,
            color = Color(0xff999999),
            modifier = Modifier.layoutId("deviceType"),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TabDemo(
    modifier: Modifier = Modifier,
    tab: DeviceTab,
    onTabSelected: (tab: DeviceTab) -> Unit,
) {


    Scaffold(
        topBar = {
            HomeTabBar(
                backgroundColor = Color.Transparent,
                tab = tab,
                onTabSelected = { onTabSelected.invoke(it) }
            )
        },
        modifier = modifier
            .fillMaxWidth()
            .border(2.dp, Color.Cyan),
        //backgroundColor = backgroundColor,
        floatingActionButton = {
            Text(text = "hello")
        }
    ) { paddingValues ->

        Text(text = "123", modifier = Modifier.padding(paddingValues))

    }

}

fun Modifier.tabIndicatorOffsetDemo(
    currentTabPosition: TabPosition,
): Modifier = composed(
    inspectorInfo = debugInspectorInfo {
        name = "tabIndicatorOffset"
        value = currentTabPosition
    }
) {
    val currentTabWidth by animateDpAsState(
        targetValue = currentTabPosition.width / 5,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )


    val indicatorOffset by animateDpAsState(
        targetValue = currentTabPosition.left,
        animationSpec = tween(durationMillis = 250, easing = FastOutSlowInEasing)
    )
    fillMaxWidth()
        .wrapContentSize(Alignment.BottomStart)
        .height(3.dp)
        .offset(x = indicatorOffset + (currentTabPosition.width - currentTabWidth) / 2)
        .width(currentTabWidth)
        .clip(RoundedCornerShape(3.dp))
}

@Composable
private fun CHTab(modifier: Modifier, tab: DeviceTab, onTabSelected: (tab: DeviceTab) -> Unit) {
    LazyRow(modifier = modifier) {
        val array = DeviceTab.values()
        items(items = array, key = {
            it.ordinal
        }) { item ->
            CHTabItem(
                tab == item,
                icon = Icons.Default.Home,
                title = stringResource(item.text),
                onClick = { onTabSelected.invoke(item) }
            )
        }
    }

}

@Composable
private fun HomeTabBar(
    backgroundColor: Color,
    tab: DeviceTab,
    onTabSelected: (tab: DeviceTab) -> Unit,
) {
    TabRow(
        modifier = Modifier
            .background(backgroundColor)
            .wrapContentSize(),
        selectedTabIndex = tab.ordinal,
//        indicator = { tabPositions ->
//            HomeTabIndicator(tabPositions, tab)
//        }
        indicator = {
            TabRowDefaults.Indicator(
                modifier = Modifier.tabIndicatorOffsetDemo(it[tab.ordinal]), height = 2.dp,
                color = Color(0xff339DFF)
            )
        },
        divider = {
            Spacer(modifier = Modifier)
        }
    ) {

        CHTabItem(
            tab == DeviceTab.Properties,
            icon = Icons.Default.Home,
            title = stringResource(DeviceTab.Events.text),
            onClick = { onTabSelected(DeviceTab.Properties) }
        )
        CHTabItem(
            tab == DeviceTab.Events,
            icon = Icons.Default.AccountBox,
            title = stringResource(DeviceTab.Events.text),
            onClick = { onTabSelected(DeviceTab.Events) }
        )

        CHTabItem(
            tab == DeviceTab.Services,
            icon = Icons.Default.AccountBox,
            title = stringResource(DeviceTab.Events.text),
            onClick = { onTabSelected(DeviceTab.Services) }
        )
    }
}

@Composable
private fun CHTabItem(
    selected: Boolean,
    icon: ImageVector,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier.width(56.dp)
            .height(36.dp)
            //.border(2.dp,Color.Red)
            .clickable(onClick = onClick)
            //.padding(16.dp)

        ,

//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null
//        )
//        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title, color = Color(0xff333333), fontSize = if (selected) 18.sp else 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal, modifier = Modifier.align(
                Alignment.Center)
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (selected){
            Box(
                modifier = Modifier
                    .width(12.dp).height(3.dp)
                    .background(color = Color(0xff339DFF))
                    .clip(RoundedCornerShape(3.dp))
                    .align(Alignment.BottomCenter)

            ) {

            }
        }

    }
}
//
//@Composable
//private fun HomeTabIndicator(
//    tabPositions: List<TabPosition>,
//    tabPage: DeviceTab
//) {
//    val transition = updateTransition(
//        tabPage,
//        label = "Tab indicator"
//    )
//    val indicatorLeft by transition.animateDp(
//        transitionSpec = {
//            if (TabPage.Home isTransitioningTo TabPage.Work) {
//                // Indicator moves to the right.
//                // Low stiffness spring for the left edge so it moves slower than the right edge.
//                spring(stiffness = Spring.StiffnessVeryLow)
//            } else {
//                // Indicator moves to the left.
//                // Medium stiffness spring for the left edge so it moves faster than the right edge.
//                spring(stiffness = Spring.StiffnessMedium)
//            }
//        },
//        label = "Indicator left"
//    ) { page ->
//        tabPositions[page.ordinal].left
//    }
//    val indicatorRight by transition.animateDp(
//        transitionSpec = {
//            if (TabPage.Home isTransitioningTo TabPage.Work) {
//                // Indicator moves to the right
//                // Medium stiffness spring for the right edge so it moves faster than the left edge.
//                spring(stiffness = Spring.StiffnessMedium)
//            } else {
//                // Indicator moves to the left.
//                // Low stiffness spring for the right edge so it moves slower than the left edge.
//                spring(stiffness = Spring.StiffnessVeryLow)
//            }
//        },
//        label = "Indicator right"
//    ) { page ->
//        tabPositions[page.ordinal].right
//    }
//    val color by transition.animateColor(
//        label = "Border color"
//    ) { page ->
//        if (page == TabPage.Home) Purple700 else Green800
//    }
//    Box(
//        Modifier
//            .fillMaxSize()
//            .wrapContentSize(align = Alignment.BottomStart)
//            .offset(x = indicatorLeft)
//            .width(indicatorRight - indicatorLeft)
//            .padding(4.dp)
//            .fillMaxSize()
//            .border(
//                BorderStroke(2.dp, color),
//                RoundedCornerShape(4.dp)
//            )
//    )
//}

//@Composable
//private fun HomeTabIndicator(
//    tabPositions: List<TabPosition>,
//    tabPage: DeviceTab,
//) {
//    val transition = updateTransition(
//        tabPage,
//        label = "Tab indicator"
//    )
//
//    val indicatorLeft by transition.animateDp(
//        transitionSpec = {
//            when {
//                DeviceTab.Properties isTransitioningTo DeviceTab.Events -> {
//                    spring(stiffness = Spring.StiffnessVeryLow)
//                }
//
//                DeviceTab.Properties isTransitioningTo DeviceTab.Services -> {
//                    spring(stiffness = Spring.StiffnessMedium)
//                }
//
//                DeviceTab.Events isTransitioningTo DeviceTab.Properties -> {
//                    spring(stiffness = Spring.StiffnessVeryLow)
//                }
//
//                DeviceTab.Events isTransitioningTo DeviceTab.Services -> {
//                    spring(stiffness = Spring.StiffnessVeryLow)
//                }
//
//                DeviceTab.Services isTransitioningTo DeviceTab.Properties -> {
//                    spring(stiffness = Spring.StiffnessMedium)
//                }
//
//                DeviceTab.Services isTransitioningTo DeviceTab.Events -> {
//                    spring(stiffness = Spring.StiffnessVeryLow)
//                }
//
//                else -> {
//                    spring(stiffness = Spring.StiffnessHigh)
//                }
//            }
////            if (DeviceTab.Properties isTransitioningTo DeviceTab.Events) {
////                // Indicator moves to the right.
////                // Low stiffness spring for the left edge so it moves slower than the right edge.
////                spring(stiffness = Spring.StiffnessVeryLow)
////            } else {
////                // Indicator moves to the left.
////                // Medium stiffness spring for the left edge so it moves faster than the right edge.
////                spring(stiffness = Spring.StiffnessMedium)
////            }
//        },
//        label = "Indicator left"
//    ) { page ->
//        tabPositions[page.ordinal].left
//    }
//
//    val indicatorRight by transition.animateDp(
//        transitionSpec = {
//            when {
//                DeviceTab.Properties isTransitioningTo DeviceTab.Events -> {
//                    spring(stiffness = Spring.StiffnessVeryLow)
//                }
//
//                DeviceTab.Properties isTransitioningTo DeviceTab.Services -> {
//                    spring(stiffness = Spring.StiffnessMedium)
//                }
//
//                DeviceTab.Events isTransitioningTo DeviceTab.Properties -> {
//                    spring(stiffness = Spring.StiffnessVeryLow)
//                }
//
//                DeviceTab.Events isTransitioningTo DeviceTab.Services -> {
//                    spring(stiffness = Spring.StiffnessVeryLow)
//                }
//
//                DeviceTab.Services isTransitioningTo DeviceTab.Properties -> {
//                    spring(stiffness = Spring.StiffnessMedium)
//                }
//
//                DeviceTab.Services isTransitioningTo DeviceTab.Events -> {
//                    spring(stiffness = Spring.StiffnessVeryLow)
//                }
//
//                else -> {
//                    spring(stiffness = Spring.StiffnessHigh)
//                }
//            }
////            if (DeviceTab.Events isTransitioningTo DeviceTab.Properties) {
////                // Indicator moves to the right
////                // Medium stiffness spring for the right edge so it moves faster than the left edge.
////                spring(stiffness = Spring.StiffnessMedium)
////            } else {
////                // Indicator moves to the left.
////                // Low stiffness spring for the right edge so it moves slower than the left edge.
////                spring(stiffness = Spring.StiffnessVeryLow)
////            }
//        },
//        label = "Indicator right"
//    ) { page ->
//        tabPositions[page.ordinal].right
//    }
//
//
//    val color by transition.animateColor(
//        label = "Border color"
//    ) { page ->
//        when (page) {
//            DeviceTab.Properties -> Color.Red
//            DeviceTab.Events -> Color.Green
//            DeviceTab.Services -> Color.Blue
//        }
//    }
//
//    Spacer(
//        modifier = Modifier
//            .width(20.dp)
//            .height(4.dp)
//            .border(
//                BorderStroke(2.dp, color),
//                RoundedCornerShape(4.dp)
//            )
//    )
//
//    Box(
//        Modifier
//            .fillMaxSize()
//            .wrapContentSize(align = Alignment.BottomStart)
//            .offset(x = indicatorLeft)
//            .width(tabPositions[0].width)
//            .padding(4.dp)
//            .fillMaxSize()
//            .border(
//                BorderStroke(2.dp, color),
//                RoundedCornerShape(4.dp)
//            )
//    )
//
//}


@Composable
private fun DataList(modifier: Modifier = Modifier) {

}