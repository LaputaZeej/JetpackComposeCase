package com.yunext.twins.compose.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Surface
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import com.yunext.twins.compose.ui.theme.Twins
import com.yunext.twins.compose.ui.theme.app_appColor
import com.yunext.twins.compose.ui.theme.app_background_70
import com.yunext.twins.compose.ui.theme.app_background_brush
import com.yunext.twins.compose.ui.theme.app_button_brush
import com.yunext.twins.compose.ui.theme.app_gray_f4f5f7
import com.yunext.twins.compose.ui.theme.app_textColor_333333

@Composable
fun CHBackgroundBlock(modifier: Modifier = Modifier, grey: Boolean = false) {
//    Box(  modifier = modifier.fillMaxSize(), contentAlignment = Alignment.TopCenter) {
    if (grey) {
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(app_background_brush)
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
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
) {
    TextField(
        value = text,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
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
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = China.r_luo_xia_hong,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        )
    )
}

@Composable
fun EditTextCenterBlock(
    modifier: Modifier = Modifier,
    text: String,
    hint: String,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
    keyboardActions: KeyboardActions = KeyboardActions(),
    onValueChange: (String) -> Unit,
) {
    val textAlign = TextAlign.Center
    TextField(
        value = text,
        onValueChange = onValueChange,
        modifier = modifier,
        textStyle = Twins.twins_edit_text.copy(textAlign = textAlign),
        placeholder = {
            androidx.compose.material.Text(
                modifier = Modifier.fillMaxWidth(),
                text = hint,
                style = Twins.twins_edit_text_hint.copy(textAlign = textAlign)
            )
        },
        singleLine = true,
        maxLines = 1,
        shape = RoundedCornerShape(12.dp),
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = China.r_luo_xia_hong,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent

        ), keyboardOptions = keyboardOptions, keyboardActions = keyboardActions
    )
}

@Composable
fun CheckedTextBlock(
    modifier: Modifier = Modifier,
    text: String,
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit,
) {
    val style = if (checked) {
        Twins.twins_title.copy(color = app_appColor)
    } else {
        Twins.twins_title
    }
    val shape = RoundedCornerShape(22.dp)
    androidx.compose.material.Text(
        text = text,
        style = style,
        modifier = modifier
            .clip(shape)
            .run {
                if (checked) {
                    this.border(width = 1.dp, color = app_appColor, shape = shape)
                } else {
                    this.background(Color(0xfff4f5f7))
                }
            }
            .clickable {
                onCheckedChanged.invoke(!checked)
            }
            .padding(vertical = 5.dp, horizontal = 16.dp)
    )
}


@Preview
@Composable
fun CommitButtonBlockPreview() {
    CommitButtonBlock("ahhaha", true) {

    }
}

@Composable
fun CommitButtonBlock2(
    text: String,
    enable: Boolean = true,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(app_button_brush)
            .clickable(enabled = enable) {
                onClick()
            }, contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            textAlign = TextAlign.Center,
            style = TextStyle(
                fontSize = 16.sp,
                color = Color(1f, 1f, 1f, if (enable) 1f else .5f),
                fontWeight = FontWeight(400), textAlign = TextAlign.Center,
            ),
            maxLines = 1,
            modifier = Modifier

        )
    }

}

@Composable
fun CommitButtonBlock(
    text: String,
    enable: Boolean = true,
    onClick: () -> Unit,
) {

    Text(
        text = text,
        //textAlign = TextAlign.Center,
        style = TextStyle(
            fontSize = 16.sp,
            color = Color(1f, 1f, 1f, if (enable) 1f else .5f),
            fontWeight = FontWeight(400), //textAlign = TextAlign.Center,
        ),
        maxLines = 1,
        modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .padding(horizontal = 0.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(app_button_brush)
            .clickable(enabled = enable) {
                onClick()
            }
            .wrapContentHeight()
            .wrapContentWidth()


    )

}

// 文本居中示例
@Preview
@Composable
private fun TextDemo() {
    Box(
        Modifier
            .background(China.g_zhu_lv)
            .padding(32.dp)
            .size(200.dp)
    ) {
        Text(
            text = "hello !",
            modifier = Modifier
                .height(56.dp)
                .fillMaxWidth()
                .background(China.r_luo_xia_hong)
                .wrapContentHeight()
                .wrapContentWidth(),
//            textAlign = TextAlign.Center
        )
    }
}

@Deprecated("delete")
@Composable
fun FloatActionBlock(modifier: Modifier = Modifier, onClick: () -> Unit) {
    Box(
        modifier
            .size(58.dp)
            .padding(4.dp)
    ) {
        Surface(
            elevation = 4.dp, modifier = Modifier

                .fillMaxSize()
                .clip(CircleShape)
                .drawWithContent {
                    drawCircle(app_button_brush)
                    drawContent()
                }

//                .size(58.dp)
//                .clip(CircleShape)


//        .background(China.r_luo_xia_hong)
//            , color = blue_1
        ) {
            Icon(
                Icons.Filled.Add,
                tint = Color.White,
                contentDescription = "add_device",
                modifier = Modifier
                    .wrapContentSize()
//                .background(China.g_zhu_lv)
            )

        }
    }
}

@Composable
fun DividerBlock(color: Color = app_textColor_333333, horizontal: Boolean = true) {
    Spacer(modifier = Modifier
        .run {
            if (horizontal) {
                this
                    .fillMaxWidth()
                    .height(.5.dp)
            } else {
                this
                    .width(.5.dp)
                    .fillMaxHeight()

            }

        }
        .background(color))
}