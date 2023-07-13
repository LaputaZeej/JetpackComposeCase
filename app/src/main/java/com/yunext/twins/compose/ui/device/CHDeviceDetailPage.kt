package com.yunext.twins.compose.ui.device

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.debugInspectorInfo
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.yunext.twins.compose.R
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.ui.common.CHBackgroundBlock
import com.yunext.twins.compose.ui.common.CHEmptyView
import com.yunext.twins.compose.ui.common.CHItemShadowShape
import com.yunext.twins.compose.ui.common.CHTitle
import com.yunext.twins.compose.ui.common.LabelTextBlock
import com.yunext.twins.compose.ui.theme.ItemDefaults
import com.yunext.twins.compose.ui.theme.app_background_70
import com.yunext.twins.compose.ui.theme.app_textColor_333333
import com.yunext.twins.compose.ui.theme.app_textColor_666666
import com.yunext.twins.compose.ui.xpl.CHTipsDialog

enum class DeviceTab(val text: Int) {
    Properties(R.string.tab_properties),
    Events(R.string.tab_events),
    Services(R.string.tab_services)
    ;
}

@Composable
fun <T> CHDeviceDetailPage(
//    navBackStackEntry: NavBackStackEntry,
//    viewModel: MainViewModel,
    device: DeviceAndState?,
    list: List<T>,
    onTabSelected: (tab: DeviceTab) -> Unit,
    onMenuClick: (MenuData) -> Unit,
    onLeft: () -> Unit,
    onRight: () -> Unit,
) {
//    LaunchedEffect(key1 = "connectAndRefresh") {
//        viewModel.connectAndRefresh()
//    }
//    val curDevice: DeviceAndState? by viewModel.curDeviceAndStateFlow.collectAsState()
//    // The currently selected tab.
    var curTab by remember { mutableStateOf(DeviceTab.Properties) }
    var showMenu by remember { mutableStateOf(false) }
    var editingProperty: Any? by remember { mutableStateOf(null) }

    var addProperty by remember { mutableStateOf(false) }

    var showTips by remember {
        mutableStateOf("")
    }
//    val device = curDevice
    CHBackgroundBlock()
//    if (device == null) {
//        Spacer(modifier = Modifier.height(100.dp))
//        Text(text = "device is empty")
//    } else {


    Column(modifier = Modifier.fillMaxSize()) {
//        Column(modifier = Modifier.background(Color(1f, 1f, 1f, 0.7f))) {
        // [title]
        CHTitle(
            modifier = Modifier
                .background(app_background_70),
            text = device?.name ?: "",
            leftClick = {
                onLeft()
            },
            rightClick = {
                showMenu = !showMenu
            })

        // [content]
        Box(Modifier.fillMaxWidth()) {
            Column() {
                // top
                Box(
                    Modifier
                        .fillMaxWidth()
                        .background(app_background_70)
                        .padding(vertical = 0.dp, horizontal = 12.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Top(device = device)
                }
                // tab
                ConstraintLayout(modifier = Modifier.fillMaxWidth()) {
                    val (tabRef, countRef) = createRefs()
                    Tab(
                        modifier = Modifier
                            .padding(16.dp)
                            .constrainAs(tabRef) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(parent.start)
                                end.linkTo(countRef.start)
                                width = Dimension.fillToConstraints
                            }, curTab
                    ) {
                        curTab = it
                        onTabSelected.invoke(it)
                    }
                    CountInfo(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .constrainAs(countRef) {
                                top.linkTo(parent.top)
                                bottom.linkTo(parent.bottom)
                                start.linkTo(tabRef.end)
                                end.linkTo(parent.end)
                            }, count = list.size
                    )
                }
                // list
                KVList(
                    curTab,
                    list,
                    onPropertyAction = {
                        if (editingProperty == null) {
                            editingProperty = it
                        }
                    },
                    onEventAction = {},
                    onServiceAction = {})
            }

            // pop
            if (showMenu) {
                MenuList(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .align(Alignment.TopEnd)
                ) {
                    onMenuClick(it)
                }
            }

            // 属性修改
            val temp = editingProperty
            if (temp != null) {
                Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                    PropertyBottomSheet(temp, onClose = {
                        editingProperty = null
                    }, onCommitted = {
                        editingProperty = null
                        showTips = "选择了$it"
                    }, add = false to {
                        addProperty = true
                    })
                }
            }

            // 属性添加
            if (addProperty) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Box(
                        modifier = Modifier
                            .align(Alignment.BottomCenter)
                    ) {
                        PropertyBottomSheet("", onClose = {
                            addProperty = false
                        }, onCommitted = {
                            addProperty = false
                            showTips = "添加了$it"
                        }, add = true to {

                        })
                    }
                }
            }
        }
    }


    // tips ialog

    if (showTips.isNotEmpty()) {
        CHTipsDialog(showTips) {
            showTips = ""
        }

//        CHAlertDialog("测试","adadadadada", onDismissRequest = {
//            showTips=""
//        })
    }
}


enum class MenuData(@DrawableRes val icon: Int, @StringRes val text: Int) {
    ConfigWiFi(R.mipmap.icon_twins_wifi, R.string.menu_config_wifi),
    Setting(R.mipmap.icon_twins_wifi, R.string.menu_setting),
    Logger(R.mipmap.icon_twins_wifi, R.string.menu_logger),
    UpdateTsl(R.mipmap.icon_twins_wifi, R.string.menu_update_tsl)
    ;
}

/**
 * todo 点击事件的问题
 */
@Composable
internal fun XPopContainer(content: @Composable () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White.copy(alpha = 0.6f))
    ) {
        content()
    }
}

@Composable
private fun MenuList(modifier: Modifier = Modifier, onMenuClick: (MenuData) -> Unit) {
    val list: Array<MenuData> by remember {
        mutableStateOf(MenuData.values())
    }
    XPopContainer() {
        CHItemShadowShape(elevation = 16.dp, modifier = modifier) {
            Column(
                modifier = Modifier
//                .size(300.dp)
//                .wrapContentWidth()
                    .width(162.dp)
//                .widthIn(max = 300.dp, min = 100.dp)
//                .wrapContentHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .background(ItemDefaults.itemBackground)
//                .padding(16.dp)
            ) {
                list.forEach {
                    MenuItem(it) {
                        onMenuClick(it)
                    }
                }
            }
        }
    }

}

@Composable
private fun MenuItem(menuData: MenuData, onClick: () -> Unit) {
    Row(modifier = Modifier
        .height(49.dp)
        .fillMaxWidth()
        .clickable {
            onClick()
        }
        .padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
        Image(
            painter = painterResource(id = menuData.icon),
            contentDescription = null,
            modifier = Modifier.size(20.dp),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = stringResource(id = menuData.text),
            fontSize = 14.sp,
            color = app_textColor_333333
        )

    }
}

@Composable
private fun <T> KVList(
    tab: DeviceTab, list: List<T>,
    onPropertyAction: (T) -> Unit,
    onServiceAction: (T) -> Unit,
    onEventAction: (T) -> Unit,
) {
    if (list.isEmpty()) {
        CHEmptyView()
    } else {
        Box(Modifier.padding(horizontal = 16.dp)) {
            when (tab) {
                DeviceTab.Properties -> {

                    ListTslProperty(list = list) {
                        onPropertyAction.invoke(it)
                    }
                }

                DeviceTab.Events -> {

                    ListTslEvent(list = list) {
                        onEventAction(it)
                    }
                }

                DeviceTab.Services -> {

                    ListTslService(list = list) {
                        onServiceAction(it)
                    }
                }
            }
        }
    }
}

@Composable
private fun Top(
    device: DeviceAndState?,
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
            text = device?.name ?: "-",
            Modifier.layoutId("deviceId"),
            fontSize = 11.sp,
            color = Color(0xff999999),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Text(
            text = device?.name ?: "-",
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
private fun Tab(modifier: Modifier, tab: DeviceTab, onTabSelected: (tab: DeviceTab) -> Unit) {
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
private fun CountInfo(modifier: Modifier = Modifier, count: Int) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "共计",
            color = app_textColor_666666,
            fontSize = 12.sp,
            fontWeight = FontWeight.Light
        )
        Spacer(modifier = Modifier.width(5.dp))
        Text(
            text = "$count",
            color = app_textColor_333333,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )
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
        modifier = modifier
            .width(56.dp)
            .height(36.dp)
            //.border(2.dp,Color.Red)
            .clip(CircleShape)
            .clickable(onClick = onClick),
        //.padding(16.dp)

//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
    ) {
//        Icon(
//            imageVector = icon,
//            contentDescription = null
//        )
//        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = title,
            color = Color(0xff333333),
            fontSize = if (selected) 18.sp else 14.sp,
            fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
            modifier = Modifier.align(
                Alignment.Center
            )
        )
        Spacer(modifier = Modifier.height(16.dp))
        if (selected) {
            Box(
                modifier = Modifier
                    .width(12.dp)
                    .height(3.dp)
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