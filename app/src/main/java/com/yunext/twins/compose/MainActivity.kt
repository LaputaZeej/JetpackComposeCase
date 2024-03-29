package com.yunext.twins.compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yunext.twins.compose.base.BaseActivity
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.data.DeviceType
import com.yunext.twins.compose.route.APP_PAGES
import com.yunext.twins.compose.route.AddDeviceDestination
import com.yunext.twins.compose.route.BleDestination
import com.yunext.twins.compose.route.DeviceDestination
import com.yunext.twins.compose.route.HomeDestination
import com.yunext.twins.compose.route.LogDestination
import com.yunext.twins.compose.route.SettingDestination
import com.yunext.twins.compose.route.navigateSingleTopTo
import com.yunext.twins.compose.ui.debug.D

import com.yunext.twins.compose.ui.device.CHDeviceDetailPage
import com.yunext.twins.compose.ui.device.DeviceActivity
import com.yunext.twins.compose.ui.device.DeviceActivity2
import com.yunext.twins.compose.ui.device.DeviceTab
import com.yunext.twins.compose.ui.device.MenuData
import com.yunext.twins.compose.ui.device.profile.ble.CHConfigWiFiPage
import com.yunext.twins.compose.ui.device.profile.log.CHLogPage
import com.yunext.twins.compose.ui.device.profile.setting.CHSettingPage
import com.yunext.twins.compose.ui.home.CHAddDevicePage
import com.yunext.twins.compose.ui.home.CHHomeScreenPage
import com.yunext.twins.compose.ui.theme.CTwinsTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.random.Random

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CTwinsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    // Nav
                    val navController = rememberNavController()

                    val currentBackStack by navController.currentBackStackEntryAsState()
                    Log.i("XNavigationScreen", "currentBackStack :${currentBackStack?.destination?.route}")
                    val curScreen =
                        APP_PAGES.find { it.route == currentBackStack?.destination?.route }
                            ?: HomeDestination // 使用currentBackStackEntryAsState确定screen
                    Log.i("XNavigationScreen", "curScreen :$curScreen")
                    // var curScreen: XDestination by remember { mutableStateOf(X1) }

                    val viewModel: MainViewModel = viewModel()
                    // content
//                    val list: List<DeviceAndState> by remember {
//                        mutableStateOf(DeviceAndState.DEBUG_LIST)
//                    }
                    LaunchedEffect(key1 = "loadData") {
                        viewModel.loadDeviceData()
                    }


//                    val list by produceState(initialValue = listOf<DeviceAndState>() ){
//                        value = viewModel.deviceAndStateListFlow.value
//                    }
                    val list by viewModel.deviceAndStateListFlow.collectAsState()
                    val uiState by viewModel.uiState.collectAsState()
//                    val list = remember {
//                        viewModel.deviceAndStateListFlow
//                    }

                    var loading by remember {
                        mutableStateOf(false)
                    }

                    NavHost(
                        navController = navController,
                        startDestination = HomeDestination.route,
                        modifier = Modifier
                    ) {
                        // 嵌套导航
//                        navigation(startDestination = Empty.route, route = HomeDestination.route) {
                        composable(route = HomeDestination.route) {
                            // home
                            CHHomeScreenPage(list = list,
                                uiState = uiState,
                                onDeviceSelected = { device ->
                                    //DeviceActivity.skip(this@MainActivity)
                                    //navController.navigateSingleTopTo(DeviceDestination.route)
                                    viewModel.prepareDeviceDetail(device)
                                    navController.navigateSingleTopTo("${DeviceDestination.route}/${device.communicationId}/${device.name}")
                                },
                                onRefresh = {
                                    viewModel.loadDeviceData()
                                },
                                onActionAdd = {
                                    navController.navigateSingleTopTo(AddDeviceDestination.route)
                                })
//                            DialogsPreview()
                        }

                        composable(route = AddDeviceDestination.route) {
                            CHAddDevicePage(onLeft = {
                                navController.popBackStack()
                            },
                                loading = loading,
                                onDeviceChanged = { deviceName: String, deviceType: DeviceType, deviceCommunicationId: String, deviceModel: String ->
                                    lifecycleScope.launch {
                                        loading = true
                                        delay(2000)
                                        loading = false
                                        navController.popBackStack()
                                        viewModel.loadDeviceData()
                                    }


                                })
                        }

                        composable(
                            route = DeviceDestination.routeWithArgs,
                            arguments = DeviceDestination.arguments,
                            //deepLinks = DeviceDestination.deepLinks

                        ) {
                            //val device = DeviceDestination.parseDevice(it.arguments) ?: ""
                            //val demo = DeviceDestination.parseDemo(it.arguments) ?: ""

//                            LaunchedEffect(key1 = "connectAndRefresh") {
//                                viewModel.connectAndRefresh()
//                            }
//                            val curDevice: DeviceAndState? by viewModel.curDeviceAndStateFlow.collectAsState()
//                            // The currently selected tab.
//                            var curTab by remember { mutableStateOf(DeviceTab.Properties) }
//                            val device = curDevice
                            val curDevice: DeviceAndState? by viewModel.curDeviceAndStateFlow.collectAsState()
//                            var curTab by remember { mutableStateOf(DeviceTab.Properties) }
                            var kvList: List<Int> by remember {
                                mutableStateOf(List(20) { it })
                            }
                            CHDeviceDetailPage(curDevice, list = kvList, onLeft = {
                                navController.popBackStack()
                            }, onRight = {
                                         // delete
                            }, onTabSelected = {
                                D.i("onTabSelected : $it")
                                kvList = List(list.size + 1 + Random.nextInt(50)) { it }
                            }, onMenuClick = {
                                when (it) {
                                    MenuData.ConfigWiFi -> navController.navigateSingleTopTo(
                                        BleDestination.route
                                    )

                                    MenuData.Setting -> navController.navigateSingleTopTo(
                                        SettingDestination.route
                                    )

                                    MenuData.Logger -> {
                                        navController.navigateSingleTopTo(LogDestination.route)
                                    }

                                    MenuData.UpdateTsl -> {
                                        viewModel.updateTsl()
                                    }
                                }
                            })
                        }

                        composable(route = BleDestination.route) {
                            CHConfigWiFiPage(){
                                navController.popBackStack()
                            }
                        }

                        composable(route = SettingDestination.route) {
                            CHSettingPage()
                        }

                        composable(route = LogDestination.route) {
                            CHLogPage()
                        }


                    }
//                    }
                }
            }
        }

        // DeviceActivity.skip(this)
        // DeviceActivity2.skip(this)

        // startActivity(Intent(this,DebugActivity::class.java))
        // startActivity(Intent(this,ActivityTemplate::class.java))


    }
}