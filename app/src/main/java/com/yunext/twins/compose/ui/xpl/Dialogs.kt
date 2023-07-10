package com.yunext.twins.compose.ui.xpl

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.yunext.twins.compose.ui.debug.cases.compoents.China
import kotlinx.coroutines.delay

internal object LoadingDialogDefaults {
    internal val DEFAULT_FONT_Color = China.w_yu_du_bai
    internal val DEFAULT_FONT_Size = 12.sp
    internal val DEFAULT_SIZE = 150.dp
    internal val DEFAULT_SHAPE = RoundedCornerShape(12.dp)
    internal val DEFAULT_DEBUG_COLOR = China.r_luo_xia_hong
    internal val DEFAULT_Progress_Color = China.w_yu_du_bai
}

private fun Modifier.debug(debug: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (debug) {
        block(this)
    } else this
}

private fun Modifier.debugShape(debug: Boolean) = debug(debug) {
    border(
        1.dp, LoadingDialogDefaults.DEFAULT_DEBUG_COLOR, shape = LoadingDialogDefaults.DEFAULT_SHAPE
    )
}

@Composable
fun LoadingDialog(
    text: String = "加载中",
    properties: DialogProperties = DialogProperties(
        decorFitsSystemWindows = true,
        usePlatformDefaultWidth = true,
        dismissOnBackPress = false,
        dismissOnClickOutside = true,
        securePolicy = SecureFlagPolicy.Inherit
    ),
    debug: Boolean = false,
    dimAmount: Float,
    onDismissRequest: () -> Unit,
) {
    Dialog(
        onDismissRequest = {
            onDismissRequest()
        }, properties = properties
    ) {
        val curView = LocalView.current
        /* 更改dialog window的透明度 */
        LaunchedEffect(curView) {
            /**
             * 此代码逻辑来自 ComposeInputMethodManager.kt 下的[androidx.compose.foundation.text2.service.ImmHelper30]
             */
            tailrec fun Context.findWindow(): Window? = when (this) {
                is Activity -> window
                is ContextWrapper -> baseContext.findWindow()
                else -> null
            }

            fun View.findWindow(): Window? =
                (parent as? DialogWindowProvider)?.window ?: context.findWindow()

            try {
                val window = curView.findWindow() ?: return@LaunchedEffect
                val lp = window.attributes
                lp.dimAmount = dimAmount
                window.attributes = lp
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        Box(
            Modifier
                .size(LoadingDialogDefaults.DEFAULT_SIZE)
                .clip(LoadingDialogDefaults.DEFAULT_SHAPE)
                .background(China.g_zhu_lv)
                .debugShape(debug)
                .padding(16.dp)
                .debugShape(debug)

        ) {
            Column(
                Modifier
                    .wrapContentSize()
                    .align(Alignment.Center)
            ) {
                var end by remember {
                    mutableStateOf("")
                }
                LaunchedEffect(key1 = Unit) {
                    while (true) {
                        delay(500)
                        end += "."
                        if (end.length > 3) {
                            end = "."
                        }
                    }
                }

                CircularProgressIndicator(
                    color = LoadingDialogDefaults.DEFAULT_Progress_Color,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .debugShape(debug)
                )
                Spacer(modifier = Modifier.height(12.dp))
                Text(
                    text = "$text$end",
                    fontSize = LoadingDialogDefaults.DEFAULT_FONT_Size,
                    fontWeight = FontWeight.Light,
                    color = LoadingDialogDefaults.DEFAULT_FONT_Color,
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)

                        .animateContentSize(spring(Spring.DampingRatioHighBouncy))
                )
            }
        }
    }
}


@Composable
fun DialogsPreview() {
    Box(Modifier.fillMaxSize()) {
        var show by remember {
            mutableStateOf(false)
        }
        Button(onClick = { show = !show }, modifier = Modifier.align(Alignment.BottomCenter)) {
            Text(text = "${if (show) "hide" else "show"}")
        }
        if (show) {
            LoadingDialog(dimAmount = 0.1f) {
                show = false
            }
        }
    }
}

