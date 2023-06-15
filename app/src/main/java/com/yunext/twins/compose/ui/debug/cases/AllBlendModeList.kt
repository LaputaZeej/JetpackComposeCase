package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.data.blendModes
import kotlinx.coroutines.launch

@Preview
@Composable
fun AllBlendModeListPreview() {
    AllBlendModeList(modifier = Modifier.fillMaxSize()){

    }
}

@Composable
fun AllBlendModeList(modifier: Modifier,onClick: BlendMode.() -> Unit) {
    var selected by remember {
        mutableStateOf(BlendMode.SrcOver)
    }
    val list by remember {
        mutableStateOf(blendModes)
    }
    val rememberCoroutineScope = rememberCoroutineScope()
    val rememberLazyListState = rememberLazyListState()
    LazyColumn(modifier, state = rememberLazyListState) {
        item {
            Box(modifier = modifier.border(2.dp, Color.Black)) {
                BlendModeItem(selected,false){

                }
            }

        }
        items(list, key = {
            it.toString()
        }) { item ->
            BlendModeItem(blendMode = item,item== selected) {
                selected = item
                rememberCoroutineScope.launch {
                    //rememberLazyListState.animateScrollToItem(0,0)
                }
                item.onClick()
            }
        }
    }

}

@Composable
fun BlendModeItem(blendMode: BlendMode,selected:Boolean, onClick: () -> Unit) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            text = "$blendMode",
            modifier = MDF
                .weight(1f)
                .clickable {
                    onClick()
                }
                .padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 8.dp)

        )
        if (selected){
            Icon(imageVector = Icons.Filled.CheckCircle, contentDescription = "")
        }
    }

}