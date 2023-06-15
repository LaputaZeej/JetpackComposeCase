package com.yunext.twins.compose.ui.debug

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.yunext.twins.compose.ui.debug.route.DebugCase
import com.yunext.twins.compose.ui.debug.route.TitleLevel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.updateAndGet
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DebugViewModel @Inject constructor(app: Application) : AndroidViewModel(app) {

    private val casesFlow: MutableStateFlow<List<DebugCase>> = MutableStateFlow(listOf())
    val cases = casesFlow.asStateFlow()

    fun loadCase() {
        viewModelScope.launch {
            try {
                delay(500L)
                casesFlow.value = withContext(Dispatchers.IO) {
                    DebugCase::class.sealedSubclasses
                        .map {
                            it.objectInstance as DebugCase
                        }
//                        .filter {
//                            it.level == TitleLevel.First
//                        }
                }
            } catch (e: Throwable) {
                e.printStackTrace()
            }
        }

    }

    private fun debug(){
        casesFlow.update {
            it
        }

        casesFlow.updateAndGet {
            it
        }

        casesFlow.getAndUpdate {
            it
        }
    }
}