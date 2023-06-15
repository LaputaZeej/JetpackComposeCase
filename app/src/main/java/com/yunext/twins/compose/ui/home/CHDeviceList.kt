package com.yunext.twins.compose.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.R
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.ui.common.CHItemShadowShape

@Preview
@Composable
fun CHDeviceListPreview() {
    CHDeviceList(list = DeviceAndState.DEBUG_LIST)
}

@Composable
fun CHDeviceList(
    modifier: Modifier = Modifier,
    list: List<DeviceAndState>,
    onDeviceClick: (DeviceAndState) -> Unit = {},
) {
    if (list.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
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


}
