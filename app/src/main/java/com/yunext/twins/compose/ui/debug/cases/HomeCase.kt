package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.route.DebugCase
import com.yunext.twins.compose.ui.debug.route.TitleLevel
import com.yunext.twins.compose.ui.debug.route.desc

@Composable
fun HomeCase(list: List<DebugCase>, onItemClick: (DebugCase) -> Unit) {
    D.i("∆∆∆∆ HomeCase ${list.size}")
    var expendList: Map<String, Set<String>> by remember {
        mutableStateOf(mapOf())
    }

    fun has(item: DebugCase): Boolean {
        expendList.values.forEach {
            it.forEach {
                if (it == item.route) {
                    return true
                }
            }
        }
        return false
    }
    if (list.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(text = "空", modifier = Modifier.align(Alignment.Center))
        }
    } else {
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(list, key = { item ->
                item.route
            }) { item ->
                CaseItem(case = item, expand = has(item)) {
                    D.i("HomeCase onItemClick:$item")
                    if (item.sub.isNotEmpty()) {
                        val has = expendList.keys.contains(item.route)
                        expendList = if (has) {
                            expendList.filter {
                                it.key != item.route
                            }
                        } else {
                            expendList + mapOf(item.route to item.sub.map { it.route }.toSet())
                        }
                    } else {
                        onItemClick(item)
                    }
                }
            }
        }

    }

}

@Composable
private fun CaseItem(case: DebugCase, expand: Boolean, onClick: () -> Unit) {
    D.i("HomeCase CaseItem: ${case.route} expand=$expand")
    if (case.level == TitleLevel.First || case.sub.isNotEmpty() || expand) {
        Row(
            modifier = Modifier
                //.clickable {
                //    onClick()
                //}
                //.background(Color.Yellow, RectangleShape)
                .padding(8.dp)
                .border(1.dp,if(expand) Color.Cyan else Color.Red)
                .padding(8.dp)
                .background(Color.Yellow.copy(.5f), RectangleShape)
                .padding(8.dp)
                .clickable {
                    onClick()
                }
        ) {
            if (expand) {
                Spacer(modifier = Modifier.width(32.dp))
            }
            Image(
                painter = painterResource(id = case.icon),
                contentDescription = case.desc.title,
                modifier = MDF.align(Alignment.CenterVertically)
            )
            Spacer(modifier = MDF.width(8.dp))
            Column() {
                Text(text = case.desc.title, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(4.dp))
                Text(text = case.desc.url, fontSize = 11.sp, fontWeight = FontWeight.Light)
                Text(text = case.desc.summary)
            }
        }
    }

}

@Composable
private fun CaseItem2(case: DebugCase, onClick: () -> Unit) {
    var expand: Boolean by remember {
        mutableStateOf(false)
    }
    val list = case.sub
    LazyColumn(modifier = Modifier.wrapContentHeight()) {
        item {
            Row(modifier = Modifier
                .wrapContentSize()
                .clickable {
                    if (list.isNotEmpty()) {
                        expand = !expand
                    } else {
                        onClick()
                    }
                }) {
                Icon(
                    painter = painterResource(id = case.icon),
                    contentDescription = case.desc.title
                )
                Column() {
                    Text(text = case.desc.title)
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(text = case.desc.desc)
                }
            }
        }

//        if (list.isNotEmpty() && expand) {
//            item {
//                Row() {
//                    Spacer(modifier = Modifier.width(16.dp))
//                    LazyColumn() {
//                        items(list, key = { item ->
//                            item.route
//                        }) { item ->
//                            CaseItem(case = item) {
//                                onClick()
//                            }
//                        }
//                    }
//                }
//            }
//        }
    }
}
