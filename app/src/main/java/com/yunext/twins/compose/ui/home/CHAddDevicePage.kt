package com.yunext.twins.compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.yunext.twins.compose.ui.common.CHBackgroundBlock
import com.yunext.twins.compose.ui.common.CHTitle
import com.yunext.twins.compose.ui.common.EditTextBlock
import com.yunext.twins.compose.ui.theme.Twins

@Composable
fun CHAddDevicePage(
    onLeft: () -> Unit,
) {
    CHBackgroundBlock(grey = true)
    Column() {
        CHTitle(text = "新增设备", leftClick = {
            onLeft()
        })
        AddDevice()
    }

}

private val lineBlock: @Composable () -> Unit = { Spacer(modifier = Modifier.height(1.dp)) }

private const val KEY_DEVICE_NAME = "device_name"
private const val KEY_DEVICE_NAME_VALUE = "device_name_value"
private const val Key_Device_Type = "device_type"

@Composable
private fun AddDevice() {

    var deviceName by rememberSaveable(Unit) {
        mutableStateOf("")
    }

    Spacer(modifier = Modifier.height(16.dp))

    fun Modifier.styleInternal() = height(48.dp)
        .fillMaxWidth()
        .background(Color.White)
        .padding(start = 16.dp, end = 16.dp)

    /* 设备名称 */
    ConstraintLayout(
        modifier = Modifier
            .styleInternal(), constraintSet = ConstraintSet {
            val deviceNameRef = createRefFor(KEY_DEVICE_NAME)
            val deviceNameValueRef = createRefFor(KEY_DEVICE_NAME_VALUE)
            constrain(deviceNameRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(deviceNameValueRef.start)
            }

            constrain(deviceNameValueRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(deviceNameRef.end)
                width = Dimension.fillToConstraints
            }
        }
    ) {
        SubItemTitle("设备名称", KEY_DEVICE_NAME)
        EditTextBlock(text = deviceName, hint = "请输入设备名称", onValueChange = {
            deviceName = it
        }, modifier = Modifier.layoutId(KEY_DEVICE_NAME_VALUE))
    }
    lineBlock()
    /* 通信类型 */
    ConstraintLayout(
        modifier = Modifier.styleInternal()
    ) {
        SubItemTitle("通信类型", "device_type")
        Row(modifier = Modifier.layoutId("")) {
            
        }
    }
    lineBlock()
    /* 通信ID */
    ConstraintLayout(
        modifier = Modifier
            .styleInternal()
    ) {
        SubItemTitle("通信ID", "device_communication_id")

    }
    lineBlock()
    /* 设备型号 */
    ConstraintLayout(
        modifier = Modifier
            .styleInternal()
    ) {
        SubItemTitle("设备型号", "device_model")

    }

}

@Composable
private fun SubItemTitle(text: String, id: String) {
    Text(text = text, style = Twins.twins_title, modifier = Modifier.layoutId(id))
}
