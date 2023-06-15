package com.yunext.twins.compose.ui.common

import androidx.compose.runtime.Composable

@Composable
fun <T> ListBlock(
    list:List<T>,
    emptyBlock:@Composable() ()->Unit,
    onItemClick: () -> Unit,
) {

}