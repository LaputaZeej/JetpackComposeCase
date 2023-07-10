package com.yunext.twins.compose.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.material.pullrefresh.PullRefreshDefaults
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.R
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.ui.common.CHItemShadowShape
import com.yunext.twins.compose.ui.debug.cases.SmartSwipeRefresh
import com.yunext.twins.compose.ui.debug.cases.SmartSwipeRefreshState
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Preview
@Composable
fun CHDeviceListPreview() {
    val state = SmartSwipeRefreshState()
    SmartSwipeRefresh({
    }, state = state) {
        CHDeviceList(list = DeviceAndState.DEBUG_LIST)
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CHDeviceList(
    modifier: Modifier = Modifier,
    list: List<DeviceAndState>,
    onRefresh: () -> Unit = {},
    onDeviceClick: (DeviceAndState) -> Unit = {},
) {
    var refreshing by remember {
        mutableStateOf(false)
    }


    val coroutineScope = rememberCoroutineScope()

 /*   val rememberPullRefreshState =
        rememberPullRefreshState(refreshing = refreshing, onRefresh = {
            refreshing = true
            onRefresh()
            coroutineScope.launch {
                delay(2000)
                refreshing = false

            }
        })*/
//    LaunchedEffect( rememberPullRefreshState){
//
//        delay(1000)
//        refreshing = true
//        delay(2000)
//        refreshing = false
//    }

    Box(
        modifier = Modifier
            .fillMaxSize()
//            .pullRefresh(rememberPullRefreshState)
    ) {
        if (list.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Image(
                    painter = painterResource(id = R.mipmap.icon_twins_no_device),
                    contentDescription = "no device",
                )
            }

        } else {
            LazyColumn(
                modifier = modifier
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(vertical = 16.dp),

                ) {
                items(list, key = {
                    it.communicationId
                }) { device ->
                    CHItemShadowShape {
                        CHDeviceItem(device = device) {
                            onDeviceClick(device)
                        }
                    }

                }
            }
        }

//        AnimatedVisibility(visibleState = MutableTransitionState(refreshing)) {
        //下拉刷新指示器
//        if (refreshing){
/*            Text(text = "progress = ${rememberPullRefreshState.progress}")
            PullRefreshIndicator(refreshing, rememberPullRefreshState,
                Modifier
                    .align(Alignment.TopCenter)
//                    .offset {
//                        IntOffset(0, -(PullRefreshDefaults.RefreshThreshold+PullRefreshDefaults.RefreshingOffset).roundToPx())
//                    }
                //.alpha(rememberPullRefreshState.progress)

                , contentColor = China.r_luo_xia_hong, backgroundColor = China.g_bo_he_lv, scale = true
            )*/
//        }

//        }

    }

}
