package com.yunext.twins.compose

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.data.repository.DeviceRepository
import com.yunext.twins.compose.data.repository.RemoteTslRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val deviceRepository: DeviceRepository,
    private val tslRepository: RemoteTslRepository
) : ViewModel() {
    private val _deviceAndStateListFlow: MutableStateFlow<List<DeviceAndState>> =
        MutableStateFlow(listOf())

    val deviceAndStateListFlow = _deviceAndStateListFlow.asStateFlow()

    private val _curDeviceAndStateFlow: MutableStateFlow<DeviceAndState?> =
        MutableStateFlow(null)

    val curDeviceAndStateFlow = _curDeviceAndStateFlow.asStateFlow()

    fun loadDeviceData() {
        Log.i("MainViewModel", "loadData")
        viewModelScope.launch {
            try {
                _deviceAndStateListFlow.value = deviceRepository.loadDevice()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
            }
        }
    }


    fun prepareDeviceDetail(deviceAndState: DeviceAndState){
        _curDeviceAndStateFlow.value = deviceAndState
    }

    fun connectAndRefresh(){
        Log.i("MainViewModel", "loadDeviceDetail")
        viewModelScope.launch {
            try {
                _curDeviceAndStateFlow.value = deviceRepository.findDevice(curDeviceAndStateFlow.value?:throw IllegalStateException("device为空"))
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
            }
        }
    }
}