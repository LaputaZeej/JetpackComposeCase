package com.yunext.twins.compose.ui.debug.cases

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.yunext.twins.compose.ui.common.MDF
import com.yunext.twins.compose.ui.debug.D
import com.yunext.twins.compose.ui.theme.Purple80
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LayoutCase03() {

    D.i("∆∆∆∆ LayoutCase03 ")

    val scope = rememberCoroutineScope()
    Scaffold(
        topBar = {},
        floatingActionButton = {
            FloatingActionButton(onClick = {
                scope.launch {

                }
            }) {
                Text(text = "FAB")
            }
        },
        bottomBar = {


            BottomAppBar() {
                Box(modifier = MDF
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .background(Color.Blue)) {

                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        ) { padding ->
        Surface(
            color = Purple80,
            modifier = MDF
                .padding(padding)
                .background(Color.Red)
                .padding(16.dp)
                .background(Color.Gray)
                .fillMaxSize()
                .padding(16.dp)
        ) {

            Column() {
                Button(onClick = { }, contentPadding = PaddingValues(16.dp)) {
                    Text(text = "点我")
                }

                Spacer(modifier = Modifier.height(10.dp))

                ExtendedFloatingActionButton(
                    onClick = { },
                ) {
                    Row() {
                        Icon(imageVector = Icons.Filled.Favorite, contentDescription = "")

                        Text(text = "Like")
                    }

                }
            }
        }
    }


}