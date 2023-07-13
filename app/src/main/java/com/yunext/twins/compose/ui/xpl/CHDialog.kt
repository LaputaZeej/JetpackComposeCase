package com.yunext.twins.compose.ui.xpl

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.view.View
import android.view.Window
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.compose.ui.window.DialogWindowProvider
import androidx.compose.ui.window.SecureFlagPolicy
import com.yunext.twins.compose.ui.common.CHPressedView
import com.yunext.twins.compose.ui.debug.cases.compoents.China


internal object DialogDefaults {
    internal val DEFAULT_FONT_Color = China.w_yu_du_bai
    internal val DEFAULT_FONT_Size = 12.sp
    internal val DEFAULT_SIZE = 150.dp
    internal val DEFAULT_SHAPE = RoundedCornerShape(12.dp)
    internal val DEFAULT_DEBUG_COLOR = China.r_luo_xia_hong
    internal val DEFAULT_Progress_Color = China.w_yu_du_bai
    internal val DEFAULT_Padding = 16.dp
}

internal fun Modifier.debug(debug: Boolean, block: Modifier.() -> Modifier): Modifier {
    return if (debug) {
        block(this)
    } else this
}

internal fun Modifier.debugShape(debug: Boolean) = debug(debug) {
    border(
        1.dp, DialogDefaults.DEFAULT_DEBUG_COLOR, shape = DialogDefaults.DEFAULT_SHAPE
    )
}

@Composable
fun CHDialog(
    properties: DialogProperties = DialogProperties(
        decorFitsSystemWindows = true,
        usePlatformDefaultWidth = true,
        dismissOnBackPress = true,
        dismissOnClickOutside = true,
        securePolicy = SecureFlagPolicy.Inherit
    ),
    dimAmount: Float = .1f,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit,
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
            /**
             * 此代码逻辑来自 ComposeInputMethodManager.kt 下的[androidx.compose.foundation.text2.service.ImmHelper30]
             */
            /**
             * 此代码逻辑来自 ComposeInputMethodManager.kt 下的[androidx.compose.foundation.text2.service.ImmHelper30]
             */

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
                //lp.width = LayoutParams.MATCH_PARENT
                lp.dimAmount = dimAmount
                window.attributes = lp
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }
        content()
    }
}


@Composable
internal fun CHPressedText(modifier: Modifier, text: String, onClick: () -> Unit) {
    CHPressedView(content = { isPressed ->
        val scale = if (isPressed) 1.2f else 1f
        val alpha = if (isPressed) .5f else 1f
        Text(
            text = text,
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold, modifier = Modifier
                .fillMaxSize()
                .alpha(alpha)
                .scale(scale)
//                                .animateContentSize { initialValue, targetValue ->  }

        )
    }, modifier = modifier, onClick = onClick)
}