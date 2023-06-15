package com.yunext.twins.compose.di

import com.yunext.twins.compose.data.repository.DeviceRepository
import com.yunext.twins.compose.data.repository.DeviceRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryDI {
    @Binds
    abstract fun bindDeviceRepository(repo:DeviceRepositoryImpl):DeviceRepository
}