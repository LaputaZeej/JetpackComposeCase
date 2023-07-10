package com.yunext.twins.compose.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import com.yunext.twins.compose.ui.theme.ItemShape
import com.yunext.twins.compose.ui.theme.Twins
import com.yunext.twins.compose.ui.theme.backgroundColor

@Composable
fun CHBackgroundBlock(modifier: Modifier = Modifier, grey: Boolean = false) {
//    Box(  modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    if (grey) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(backgroundColor)
        )
    } else {
        Image(
            painter = painterResource(id = R.mipmap.icon_twins_body_bg),
            contentScale = ContentScale.Crop,
            contentDescription = "background",
            modifier = modifier.fillMaxWidth()
        )
    }

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
    Box(contentAlignment = Alignment.Center, modifier = modifier) {
        Image(
            painter = painterResource(id = R.mipmap.icon_twins_label_bg),
            contentDescription = "bg"
        )
        Text(
            text = text, fontSize = 11.sp, color = Color(0x66666666), modifier = Modifier
                .align(
                    Alignment.CenterStart
                )
                .padding(start = 6.dp)
        )
    }
}

@Composable
fun EditTextBlock(
    text: String,
    hint: String,
    modifier: Modifier = Modifier, onValueChange: (String) -> Unit,
) {
    TextField(
        //modifier=Modifier.background(Color.Transparent),
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = Twins.twins_edit_text.copy(textAlign = TextAlign.End),
        placeholder = {
            androidx.compose.material.Text(
                modifier = Modifier.fillMaxWidth(),
                text = hint,
                style = Twins.twins_edit_text_hint.copy(textAlign = TextAlign.End)
            )
        },
        singleLine = true,
        maxLines = 1,
        shape = ItemShape,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = China.r_luo_xia_hong,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        )
    )
}