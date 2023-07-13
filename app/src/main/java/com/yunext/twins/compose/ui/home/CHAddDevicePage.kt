package com.yunext.twins.compose.ui.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import com.yunext.twins.compose.data.DeviceType
import com.yunext.twins.compose.ui.common.CHBackgroundBlock
import com.yunext.twins.compose.ui.common.CHTitle
import com.yunext.twins.compose.ui.common.CheckedTextBlock
import com.yunext.twins.compose.ui.common.CommitButtonBlock
import com.yunext.twins.compose.ui.common.EditTextBlock
import com.yunext.twins.compose.ui.theme.Twins
import com.yunext.twins.compose.ui.xpl.CHLoadingDialog
import java.util.UUID

@Composable
fun CHAddDevicePage(
    onLeft: () -> Unit,
    loading:Boolean,
    onDeviceChanged: (deviceName: String, deviceType: DeviceType, deviceCommunicationId: String, deviceModel: String) -> Unit,
) {
    CHBackgroundBlock(grey = true)
    Column() {
        CHTitle(text = "新增设备", leftClick = {
            onLeft()
        })
        AddDevice(onDeviceChanged)
    }

    if (loading) {
        CHLoadingDialog(dimAmount = 0.1f) {

        }
    }
}

private val LineBlock: @Composable () -> Unit = { Spacer(modifier = Modifier.height(1.dp)) }

private const val Key_Device_Name = "device_name"
private const val Key_Device_Name_Value = "device_name_value"
private const val Key_Device_Type = "device_type"
private const val Key_Device_Type_Value = "device_type_value"
private const val Key_Device_Model = "device_model"
private const val Key_Device_Model_Value = "device_model_value"
private const val Key_Device_Communication_Id = "device_communication_id"
private const val Key_Device_Communication_Id_Value = "device_communication_id_value"

@Composable
private fun AddDevice(onDeviceChanged: (deviceName: String, deviceType: DeviceType, deviceCommunicationId: String, deviceModel: String) -> Unit) {

    var deviceName by rememberSaveable(Unit) {
        mutableStateOf("")
    }
    var deviceType: DeviceType by rememberSaveable(Unit) {
        mutableStateOf(DeviceType.WIFI)
    }
    var deviceCommunicationId: String by rememberSaveable(Unit) {
        mutableStateOf(UUID.randomUUID().toString())
    }
    var deviceModel: String by rememberSaveable(Unit) {
        mutableStateOf("")
    }

    Spacer(modifier = Modifier.height(16.dp))
    fun Modifier.styleInternal() = height(48.dp)
        .fillMaxWidth()
        .background(Color.White)
        .padding(start = 16.dp, end = 16.dp)

    /* 设备名称 */
    SubItem(modifier = Modifier.styleInternal(),
        title = {
            SubItemTitle("设备名称", modifier = Modifier)
        },
        value = {
            EditTextBlock(
                text = deviceName,
                hint = "请输入设备名称",
                onValueChange = {
                    deviceName = it
                }, modifier = Modifier.fillMaxSize()
            )
        })
    LineBlock()
    /*ConstraintLayout(
        modifier = Modifier
            .styleInternal(), constraintSet = ConstraintSet {
            val deviceNameRef = createRefFor(Key_Device_Name)
            val deviceNameValueRef = createRefFor(Key_Device_Name_Value)
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
        SubItemTitle("设备名称", Modifier.layoutId(Key_Device_Name))
        EditTextBlock(text = deviceName, hint = "请输入设备名称", onValueChange = {
            deviceName = it
        }, modifier = Modifier
            .layoutId(Key_Device_Name_Value)
            .fillMaxSize())
    }
    LineBlock()*/
    /* 通信类型 */
    SubItem(
        modifier = Modifier.styleInternal(),
        title = { SubItemTitle("通信类型", Modifier) }) {

        Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.End) {
//            Checkbox(checked = selDeviceType == DeviceType.WIFI, onCheckedChange = {
//                selDeviceType = if (it) DeviceType.WIFI else selDeviceType
//            })
//
//
//            Checkbox(checked = selDeviceType == DeviceType.GPRS, onCheckedChange = {
//                selDeviceType = if (it) DeviceType.GPRS else selDeviceType
//            })

            CheckedTextBlock(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "WiFi/Bluetooth",
                deviceType == DeviceType.WIFI
            ) {
                if (it && deviceType != DeviceType.WIFI) {
                    deviceType = DeviceType.WIFI
                }
            }

            CheckedTextBlock(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "4G",
                deviceType == DeviceType.GPRS
            ) {
                if (it && deviceType != DeviceType.GPRS) {
                    deviceType = DeviceType.GPRS
                }
            }
        }
    }
    LineBlock()
    /* 通信ID */
    SubItem(
        modifier = Modifier.styleInternal(),
        title = { SubItemTitle("通信ID", Modifier) },
        value = {
            EditTextBlock(
                text = deviceCommunicationId,
                hint = "自动填充可修改",
                onValueChange = {
                    deviceCommunicationId = it
                }, modifier = Modifier.fillMaxSize()
            )
        })
    LineBlock()
    /* 设备型号 */
    SubItem(
        modifier = Modifier.styleInternal(),
        title = { SubItemTitle("设备型号", Modifier) },
        value = {
            EditTextBlock(
                text = deviceModel,
                hint = "请输入设备型号",
                onValueChange = {
                    deviceModel = it
                }, modifier = Modifier.fillMaxSize()
            )
        })

    Spacer(modifier = Modifier.height(32.dp))
    Box(modifier = Modifier.padding(16.dp)){
        CommitButtonBlock(
            text = "新增",
            enable = deviceModel.isNotEmpty() && deviceName.isNotEmpty() && deviceCommunicationId.isNotEmpty()
        ) {
            onDeviceChanged(deviceName, deviceType, deviceCommunicationId, deviceModel)
        }
    }


}

@Composable
private fun SubItemTitle(text: String, modifier: Modifier = Modifier) {
    Text(text = text, style = Twins.twins_title, modifier = modifier)
}


private const val Key_SubItem_Title = "Key_SubItem_Title"
private const val Key_SubItem_Title_Value = "Key_SubItem_Title_Value"


@Preview
@Composable
fun SubItemPreview() {
    var name by remember {
        mutableStateOf("")
    }
    Column(
        Modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        SubItem(modifier = Modifier
            .height(48.dp)
            .fillMaxWidth(), title = {
            Text(text = "我是标题")
        }) {
            EditTextBlock(
                text = name,
                hint = "请输入名字",
                onValueChange = { name = it },
                modifier = Modifier.fillMaxSize()
            )
        }
    }

}

@Composable
private fun SubItem(
    modifier: Modifier,
    title: @Composable () -> Unit,
    value: @Composable () -> Unit,
) {
    ConstraintLayout(
        modifier = modifier,
        constraintSet = ConstraintSet {
            val titleRef = createRefFor(Key_SubItem_Title)
            val valueRef = createRefFor(Key_SubItem_Title_Value)
            constrain(titleRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(valueRef.start)
            }

            constrain(valueRef) {
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                end.linkTo(parent.end)
                start.linkTo(titleRef.end)
                width = Dimension.fillToConstraints
            }
        }
    ) {
        Box(
            modifier = Modifier
                .layoutId(Key_SubItem_Title)
            //.background(China.r_luo_xia_hong)
        ) {
            title()
        }
        Box(
            modifier = Modifier
                .layoutId(Key_SubItem_Title_Value)
            //.background(China.g_zhu_lv),
        ) {
            value()
        }
    }
}


