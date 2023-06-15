package com.yunext.twins.compose.route

import android.os.Bundle
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import com.yunext.twins.compose.R
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.ui.debug.cases.LayoutCase01
import com.yunext.twins.compose.ui.home.CHHomeScreenPage

sealed interface AppDestination {
    val icon: Int
    val route: String
    //val screen: @Composable () -> Unit  // 实际中有很多参数要传递 所以移动到XNavHost中
}

val APP_PAGES = listOf(Empty,HomeDestination,AddDeviceDestination,DeviceDestination,BleDestination,LogDestination,SettingDestination)

object Empty :AppDestination{
    override val icon: Int
        get() = R.mipmap.ic_app
    override val route: String
        get() = "empty"

}

object HomeDestination:AppDestination{
    override val icon: Int
        get() = R.mipmap.ic_app
    override val route: String
        get() = "home"

}

object AddDeviceDestination:AppDestination{
    override val icon: Int
        get() = R.mipmap.ic_app
    override val route: String
        get() = "add_device"

}

object DeviceDestination:AppDestination{
    override val icon: Int
        get() = R.mipmap.ic_app
    override val route: String
        get() = "device"

    private const val key_device = "key_device"
    private const val key_demo = "key_demo"
    fun parseDevice(bundle: Bundle?) = bundle?.getString(key_device)
    fun parseDemo(bundle: Bundle?) = bundle?.getString(key_demo)
    val routeWithArgs = "$route/{$key_device}/{${key_demo}}"
    val arguments = listOf(
        //navArgument(key_device) { type = NavType.ParcelableType<DeviceAndState>(DeviceAndState::class.java) },
        navArgument(key_device) { type = NavType.StringType },
        navArgument(key_demo) { type = NavType.StringType },
    )
//    val deepLinks = listOf(
//        navDeepLink { uriPattern = "xpl://$route/{$routeWithArgs}" }
//    )

}

object BleDestination:AppDestination{
    override val icon: Int
        get() = R.mipmap.ic_app
    override val route: String
        get() = "ble"

}

object LogDestination:AppDestination{
    override val icon: Int
        get() = R.mipmap.ic_app
    override val route: String
        get() = "log"

}

object SettingDestination:AppDestination{
    override val icon: Int
        get() = R.mipmap.ic_app
    override val route: String
        get() = "setting"
}