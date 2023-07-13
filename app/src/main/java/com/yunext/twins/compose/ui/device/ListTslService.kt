package com.yunext.twins.compose.ui.device

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.yunext.twins.compose.ui.common.CHItemShadowShape
import com.yunext.twins.compose.ui.common.PreviewPart
import com.yunext.twins.compose.ui.device.data.ServiceData
import com.yunext.twins.compose.ui.theme.ItemDefaults
import com.yunext.twins.compose.ui.theme.app_appColor
import com.yunext.twins.compose.ui.theme.app_blue_light
import com.yunext.twins.compose.ui.theme.app_orange
import com.yunext.twins.compose.ui.theme.app_orange_light
import com.yunext.twins.compose.ui.theme.app_red
import com.yunext.twins.compose.ui.theme.app_red_light
import com.yunext.twins.compose.ui.theme.app_textColor_333333
import com.yunext.twins.compose.ui.theme.app_textColor_666666
import com.yunext.twins.compose.ui.theme.app_textColor_999999

// ----------------- services ----------------- //

@Preview
@Composable
fun ServiceItemPreview() {
    PreviewPart {
        ServiceItem(ServiceData.random()) {}
    }
}

@Preview
@Composable
fun ListTslServicePreview() {
    val list: List<Int> by remember {
        mutableStateOf(List(20) { it })
    }
    PreviewPart {
        ListTslService(list) {

        }
    }
}

/**
 * 服务列表
 */
@Composable
fun <T> ListTslService(list: List<T>, onClick: (T) -> Unit) {
    val realList = list.map {
        ServiceData.random()
    }
    LazyColumn(
        contentPadding = PaddingValues(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        itemsIndexed(items = realList, key = { _, it ->
            it.toString()
        }) { index, it ->
            ServiceItem(it) {
                onClick(list[index])
            }
        }
    }
}

@Composable
private fun ServiceItem(data: ServiceData, onClick: () -> Unit) {
    val desc = "说明信息显示处，如无，则不显示该区域"

    CHItemShadowShape() {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(ItemDefaults.itemShape)
                .background(ItemDefaults.itemBackground)
                .padding(16.dp)
        ) {
            // 头部基本信息
            HeaderParts(data = data) {
                onClick()
            }

            Spacer(modifier = Modifier.height(16.dp))
            // 输入
            InputPart("输入", data.input)
            Spacer(modifier = Modifier.height(16.dp))
            // 输出
            InputPart("输出", data.output)
            Spacer(modifier = Modifier.height(16.dp))
            // desc
            BottomPart(desc)
        }
    }
}


@Composable
private fun HeaderParts(data: ServiceData, onClick: () -> Unit) {
    ConstraintLayout(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
    ) {
        val (nameKeyRef, editRef, boxesRef) = createRefs()
        Row(modifier = Modifier.constrainAs(nameKeyRef) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            bottom.linkTo(boxesRef.top)
            end.linkTo(editRef.start)
            width = Dimension.fillToConstraints
        }) {
            Text(
                text = data.name.run { this.ifEmpty { "未知" } },
                fontSize = 16.sp,
                color = app_textColor_333333,
                fontWeight = FontWeight.Bold
            )
            Text(text = data.key.run {
                if (this.isEmpty()) "-" else "(${this})"
            }, fontSize = 16.sp, color = app_textColor_999999, fontWeight = FontWeight.Bold)

        }

        Text(
            modifier = Modifier
                .constrainAs(editRef) {
                    top.linkTo(nameKeyRef.top)
                    start.linkTo(nameKeyRef.end)
                    end.linkTo(parent.end)

                }
                .clip(ItemDefaults.editShape)
                .border(width = 1.dp, color = app_appColor, shape = ItemDefaults.editShape)
                .clickable {
                    onClick()
                }
                .padding(horizontal = 8.dp, vertical = 6.dp),
            text = "模拟",
            fontSize = 13.sp,
            color = app_appColor
        )

        Row(modifier = Modifier.constrainAs(boxesRef) {
            top.linkTo(nameKeyRef.bottom, margin = 8.dp)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)

        }) {
            val required = if (data.required) "必须" else "非必须"
            val requiredColor =
                if (data.required) app_red to app_red_light else app_appColor to app_blue_light
            LabelPart(
                required,
                requiredColor.first,
                requiredColor.second
            )
            Spacer(modifier = Modifier.width(8.dp))
            val async = if (data.async) "async" else "sync"
            val asyncColor =
                if (data.async) app_orange to app_orange_light else app_appColor to app_blue_light

            LabelPart(async, asyncColor.first, asyncColor.second)
            Spacer(modifier = Modifier.width(8.dp))

        }
    }
}

@Composable
internal fun InputPart(label: String, list: List<*>) {
    Column(modifier = ItemDefaults.borderModifier.padding(12.dp)) {
        Text(text = label, color = app_textColor_666666, fontSize = 13.sp)
        Spacer(modifier = Modifier.height(16.dp))
        Box(modifier = ItemDefaults.border4Modifier) {
            StructItemList(list)
        }
    }
}





