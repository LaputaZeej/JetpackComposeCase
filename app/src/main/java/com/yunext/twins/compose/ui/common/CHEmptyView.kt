package com.yunext.twins.compose.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.theme.app_textColor_333333
import com.yunext.twins.compose.ui.theme.app_textColor_666666
import com.yunext.twins.compose.ui.theme.app_textColor_999999

@Preview
@Composable
fun CHEmptyViewPreview() {
    PreviewPart {
//        CHEmptyView()
        CHEmptyViewForDevice()
    }
}

@Composable
fun CHEmptyView() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.mipmap.ic_app), contentDescription = null)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "没有找到相关内容", fontSize = 14.sp, color = app_textColor_666666)
    }
}

@Composable
fun CHEmptyViewForDevice() {
    Column(modifier = Modifier.fillMaxSize(), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Image(painter = painterResource(id = R.mipmap.ic_app), contentDescription = null)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "你还没有添加设备", fontSize = 16.sp, color = app_textColor_333333)
        Spacer(modifier = Modifier.height(12.dp))
        Text(text = "添加设备，实时查看设备信息", fontSize = 12.sp, color = app_textColor_999999)
    }
}