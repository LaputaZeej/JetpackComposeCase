package com.yunext.twins.compose.data.repository

import com.yunext.twins.compose.data.DeviceAndState
import kotlinx.coroutines.delay
import javax.inject.Inject

interface DeviceRepository {

    suspend fun loadDevice():List<DeviceAndState>
    suspend fun findDevice(device:DeviceAndState):DeviceAndState?
}

class DeviceRepositoryImpl @Inject constructor():DeviceRepository{
    override suspend fun loadDevice(): List<DeviceAndState> {
        delay(2000)
        return DeviceAndState.DEBUG_LIST
    }

    override suspend fun findDevice(device: DeviceAndState): DeviceAndState? {
        delay(1000)
        return DeviceAndState.DEBUG_LIST.singleOrNull(){
            it.communicationId == device.communicationId
        }
    }


}