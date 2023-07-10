package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.FixedScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.debug.cases.compoents.XplSpacer
import com.yunext.twins.compose.ui.debug.cases.compoents.desc
import com.yunext.twins.compose.ui.debug.cases.compoents.randomContentScale
import com.yunext.twins.compose.ui.debug.cases.compoents.xplBorder
import com.yunext.twins.compose.ui.debug.cases.compoents.xplBrush
import com.yunext.twins.compose.ui.debug.cases.compoents.xplRandomColor
import com.yunext.twins.compose.ui.debug.cases.data.XplMsg
import com.yunext.twins.compose.ui.theme.xf98
import java.util.Random


@Preview
@Composable
fun LayoutCase14Preview() {
    LayoutCase14()
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun LayoutCase14() {
    D.i("∆∆∆∆ LayoutCase14 ")
    val verticalScrollState = rememberScrollState()
    val horizontalScrollState = rememberScrollState()
    val size = 300.dp

    Column(
        Modifier
            .fillMaxSize()
            .background(Color.DarkGray)
            .padding(16.dp)
            .background(Color.Gray)
            .padding(16.dp)
            .verticalScroll(verticalScrollState)
            .horizontalScroll(horizontalScrollState)
    ) {
        XplSpacer()
        Text("延迟网格")
        var contentScale: ContentScale by remember {
            mutableStateOf(ContentScale.Fit)
        }
        Button(onClick = {
            contentScale = randomContentScale()
        }) {
            Text(text = "[${contentScale.desc}] --> 切换ContentScale")
        }

        Spacer(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 8.dp)
                .width(128.dp)
                .height(4.dp)
                .background(xplRandomColor())
        )
        Text("1、 GridCells.Adaptive*(minSize)")
        Row(
            Modifier
                .wrapContentWidth()
                .height(size)
                .border(1.dp, xf98)
        ) {

            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()
            ) {
                LazyVerticalGrid(columns = GridCells.Adaptive(minSize = 128.dp)) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem2(msg = it, contentScale = contentScale) {

                        }
                    }
                }
            }
            XplSpacer(false)
            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()

            ) {
                LazyHorizontalGrid(rows = GridCells.Adaptive(128.dp)) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(
                            modifier = MDF.fillMaxSize(),
                            msg = it,
                            contentScale = contentScale
                        ) {

                        }
                    }
                }

            }


        }

        Text(" GridCells.Fixed(3)")
        Row(
            Modifier
                .wrapContentWidth()
                .height(size)
                .border(1.dp, xf98)
        ) {
            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()

            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                }

            }
            XplSpacer(false)

            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()

            ) {
                LazyHorizontalGrid(rows = GridCells.Fixed(3)) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                }

            }
        }

        Text("Span")
        Row(
            Modifier
                .wrapContentWidth()
                .height(size)
                .border(1.dp, xf98)
        ) {

            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(3)) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                    item(span = {
                        //GridItemSpan(maxLineSpan)
                        GridItemSpan(2)
                    }){
                        Text(text = "我是Span 我是Span 最大：maxLineSpan 当前：2" , fontSize = 24.sp, modifier = Modifier.background(Color.White))
                    }
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                }
            }
            XplSpacer(false)
            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()
            ) {
                LazyHorizontalGrid(rows = GridCells.Fixed(3)) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                    item(span = {
                        //GridItemSpan(maxLineSpan)
                        GridItemSpan(2)
                    }){
                        Text(text = "我是Span 我是Span 最大：maxLineSpan 当前：2" , fontSize = 24.sp, modifier = Modifier.background(Color.White))
                    }
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                }
            }
        }

        Text("【内容内边距】PaddingValues contentPadding")
        Row(
            Modifier
                .wrapContentWidth()
                .height(size)
                .border(1.dp, xf98)
        ) {

            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(vertical = 8.dp)) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                    item(span = {
                        //GridItemSpan(maxLineSpan)
                        GridItemSpan(2)
                    }){
                        Text(text = "我是Span 我是Span 最大：maxLineSpan 当前：2" , fontSize = 24.sp, modifier = Modifier.background(Color.White))
                    }
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                }
            }
            XplSpacer(false)
            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()
            ) {
                LazyHorizontalGrid(rows = GridCells.Fixed(3), contentPadding = PaddingValues(horizontal = 8.dp)) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                    item(span = {
                        //GridItemSpan(maxLineSpan)
                        GridItemSpan(2)
                    }){
                        Text(text = "我是Span 我是Span 最大：maxLineSpan 当前：2" , fontSize = 24.sp, modifier = Modifier.background(Color.White))
                    }
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                }
            }
        }

        Text("【内容间距】PaddingValues contentPadding")
        Row(
            Modifier
                .wrapContentWidth()
                .height(size)
                .border(1.dp, xf98)
        ) {

            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(3), contentPadding = PaddingValues(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(modifier = MDF.animateItemPlacement(),msg = it, contentScale = contentScale) {

                        }
                    }
                    item(span = {
                        //GridItemSpan(maxLineSpan)
                        GridItemSpan(2)
                    }){
                        Text(text = "我是Span 我是Span 最大：maxLineSpan 当前：2" , fontSize = 24.sp, modifier = Modifier.background(Color.White))
                    }
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(modifier = MDF.animateItemPlacement(tween(durationMillis = 250)),msg = it, contentScale = contentScale) {

                        }
                    }
                }
            }
            XplSpacer(false)
            Box(
                Modifier
                    .width(size)
                    .height(size * 2)
                    .xplBorder()
            ) {
                LazyHorizontalGrid(rows = GridCells.Fixed(3), contentPadding = PaddingValues(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                    item(span = {
                        //GridItemSpan(maxLineSpan)
                        GridItemSpan(2)
                    }){
                        Text(text = "我是Span 我是Span 最大：maxLineSpan 当前：2" , fontSize = 24.sp, modifier = Modifier.background(Color.White))
                    }
                    items(XplMsg.LIST, key = {
                        it.id
                    }) {
                        XplMsgItem(msg = it, contentScale = contentScale) {

                        }
                    }
                }
            }
        }
    }

}


@Composable
private fun XplMsgItem(
    modifier: Modifier = Modifier,
    msg: XplMsg,
    contentScale: ContentScale,
    onAction: (XplMsg) -> Unit,
) {

    Box(modifier = modifier
        .border(1.dp, Color.Red)
        .clickable {
            onAction.invoke(msg)
        }) {
        Image(
            painter = painterResource(id = msg.icon),
            contentDescription = msg.desc,
            contentScale = contentScale
        )
    }
}

@Composable
private fun XplMsgItem2(
    modifier: Modifier = Modifier,
    msg: XplMsg,
    contentScale: ContentScale,
    onAction: (XplMsg) -> Unit,
) {
    Box(modifier = modifier
        .border(1.dp, Color.Red)
        .clickable {
            onAction.invoke(msg)
        }) {
        Image(
            painter = painterResource(id = msg.icon),
            contentDescription = msg.desc,
            contentScale = contentScale
        )
    }
}


inline fun TODO(content: () -> Unit) {
    TODO("待实现")
}

fun test() {
    TODO {
        val hello = "hello"
        println(hello)
    }
}

//inline fun TODO(content: @Composable ()->Unit){
//    TODO("待实现")
//}
//
//@Composable
//@Preview
//fun TestTODO(){
//    TODO {
//        Box() {
//            Text(text = "看看什么颜色")
//        }
//    }
//}


//@Composable
//inline fun TODO(content: @Composable ()->Unit){
//    content()
//}
//
//@Composable
//@Preview
//fun TestTODO(){
//    TODO {
//        Box() {
//            Text(text = "看看什么颜色")
//        }
//    }
//}


//TODO(1) {
//    val a = "123"
//    println(a)
//    for (i in 1..20) {
//
//    }
//}
// TODO   {
// TODO          }
//


interface A {
    companion object {
        val a = object : A {
            override fun plus(a: List<A>): List<A> {
                return a + this
            }

        }
        val b = object : A {
            override fun plus(a: List<A>): List<A> {
                return a + this
            }
        }
    }

    operator fun plus(a: List<A>): List<A>
}

class C(val value: Float) : A {
    override operator fun plus(a: List<A>): List<A> {
        return a + this
    }
}

val list: List<A> = listOf(A.a, A.b)

fun random() {
    val c1: A = C(kotlin.random.Random.nextFloat())
    val test01 = list + c1
    val test02 = c1 + list

    val c2 = C(kotlin.random.Random.nextFloat())
    val test03 = list + c2
    val test04 = c2 + list
}


