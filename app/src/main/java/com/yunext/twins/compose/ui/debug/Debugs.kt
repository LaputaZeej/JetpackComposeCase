package com.yunext.twins.compose.ui.debug

import android.util.Log

object D {
    private const val TAG = "_debug_"
    fun i(msg: String) {
        Log.i(TAG, msg)
    }

    fun e(msg: String) {
        Log.e(TAG, msg)
    }
}