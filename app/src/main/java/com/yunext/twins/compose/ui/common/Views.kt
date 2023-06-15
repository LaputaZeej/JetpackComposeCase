package com.yunext.twins.compose.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.R



@Composable
fun CHBackgroundBlock(modifier: Modifier = Modifier) {
//    Box(  modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
        Image(
            painter = painterResource(id = R.mipmap.icon_twins_body_bg),
            contentScale = ContentScale.Crop,
            contentDescription = "background",
            modifier = modifier.fillMaxWidth()
        )
//    }

}

@Composable
fun EmptyDeviceBlock(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.mipmap.icon_twins_no_device),
        contentScale = ContentScale.Crop,
        contentDescription = "EmptyDeviceBlock",
        modifier = modifier
    )
}

@Composable
fun EmptyDataBlock(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(id = R.mipmap.icon_twins_no_data),
        contentScale = ContentScale.Crop,
        contentDescription = "EmptyDataBlock",
        modifier = modifier
    )
}

@Composable
fun LabelTextBlock(text: String, modifier: Modifier = Modifier) {
    Box(contentAlignment = Alignment.Center,modifier=modifier) {
        Image(
            painter = painterResource(id = R.mipmap.icon_twins_label_bg),
            contentDescription = "bg"
        )
        Text(text = text, fontSize = 11.sp, color = Color(0x66666666), modifier = Modifier
            .align(
                Alignment.CenterStart
            )
            .padding(start = 6.dp))
    }
}