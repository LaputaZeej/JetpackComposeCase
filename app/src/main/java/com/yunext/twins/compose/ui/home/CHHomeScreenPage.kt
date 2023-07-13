package com.yunext.twins.compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.R
import com.yunext.twins.compose.base.End
import com.yunext.twins.compose.base.Processing
import com.yunext.twins.compose.base.Start
import com.yunext.twins.compose.base.UiState
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.ui.common.CHBackgroundBlock
import com.yunext.twins.compose.ui.common.CHEmptyViewForDevice
import com.yunext.twins.compose.ui.theme.app_button_brush
import com.yunext.twins.compose.ui.xpl.CHBottomSheetDialog
import com.yunext.twins.compose.ui.xpl.CHLoadingDialog

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CHHomeScreenPage(
    modifier: Modifier = Modifier,
    list: List<DeviceAndState>,
    uiState: UiState<Unit>,
    onRefresh: () -> Unit,
    onDeviceSelected: (DeviceAndState) -> Unit,
    onActionAdd: () -> Unit,
) {
    CHBackgroundBlock(modifier)
    val refreshing = when (uiState) {
        is End.Fail -> false
        is End.Success<Unit, *> -> false
        is Processing -> true
        is Start -> false
    }
    val pullRefreshState = rememberPullRefreshState(
        refreshing = refreshing, onRefresh = { onRefresh() })



    Box(
        modifier = Modifier
            .fillMaxSize()
//            .pullRefresh(pullRefreshState)
    ) {

        Column() {
            Spacer(modifier = Modifier.height(72.dp))

            Text(
                text = stringResource(id = R.string.app_name),
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                // fontStyle = MaterialTheme.typography.titleLarge
            )
            Spacer(modifier = Modifier.height(23.dp))
            Text(
                text = stringResource(id = R.string.app_virtual_device),
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(start = 16.dp, end = 16.dp)
                // fontStyle = MaterialTheme.typography.titleLarge
            )
            Box(
                modifier = Modifier
                    .pullRefresh(pullRefreshState)
                    .weight(1f)
            ) {

                if (list.isEmpty()) {
                    CHEmptyViewForDevice()
                } else {
                    CHDeviceList(list = list, onRefresh = {
                        onRefresh()
                    }) { device ->
                        onDeviceSelected.invoke(device)
                    }
                }

                PullRefreshIndicator(
                    refreshing = refreshing,
                    state = pullRefreshState,
                    scale = true,
                    modifier = Modifier.align(
                        Alignment.TopCenter
                    )
                )
            }

        }

        FloatingActionButton(
            onClick = {
                onActionAdd.invoke()
            },
            shape = CircleShape,
            elevation = FloatingActionButtonDefaults.elevation(4.dp),
            modifier = Modifier
                .padding(end = 16.dp, bottom = 16.dp)
                .align(Alignment.BottomEnd),
//            contentColor = Color.Red,
//            containerColor = China.r_luo_xia_hong

        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(58.dp)
                    .clip(CircleShape)
                    .background(brush = app_button_brush)
            ) {

                Text(
                    text = "+",
                    color = Color.White,
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        var show by remember {
            mutableStateOf(false)
        }
        Button(
            onClick = { show = !show },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(vertical = 16.dp)
        ) {
            Text(text = "Debug")
        }
        if (show) {
//            CHLoadingDialog(dimAmount = 0.1f) {
//                show = false
//            }
            CHBottomSheetDialog(){
                show = false
            }
        }

    }

}



