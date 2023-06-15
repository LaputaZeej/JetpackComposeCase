package com.yunext.twins.compose

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yunext.twins.compose.base.BaseActivity
import com.yunext.twins.compose.route.APP_PAGES
import com.yunext.twins.compose.route.AddDeviceDestination
import com.yunext.twins.compose.route.DeviceDestination
import com.yunext.twins.compose.route.HomeDestination
import com.yunext.twins.compose.route.SettingDestination
import com.yunext.twins.compose.route.navigateSingleTopTo
import com.yunext.twins.compose.ui.debug.DebugActivity
import com.yunext.twins.compose.ui.debug.MainActivity
import com.yunext.twins.compose.ui.debug.route.DebugCase
import com.yunext.twins.compose.ui.device.CHDeviceDetailPage
import com.yunext.twins.compose.ui.device.DeviceActivity
import com.yunext.twins.compose.ui.device.DeviceActivity2
import com.yunext.twins.compose.ui.device.profile.setting.CHSettingPage
import com.yunext.twins.compose.ui.home.CHAddDevicePage
import com.yunext.twins.compose.ui.home.CHHomeScreenPage
import com.yunext.twins.compose.ui.theme.CTwinsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            CTwinsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Nav
                    val navController = rememberNavController()

                    val currentBackStack by navController.currentBackStackEntryAsState()
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
//                    val list = remember {
//                        viewModel.deviceAndStateListFlow
//                    }

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
                                onDeviceSelected = { device ->
                                    //DeviceActivity.skip(this@MainActivity)
                                    //navController.navigateSingleTopTo(DeviceDestination.route)
                                    viewModel.prepareDeviceDetail(device)
                                    navController.navigateSingleTopTo("${DeviceDestination.route}/${device.communicationId}/${device.name}")
                                },
                                onActionAdd = {
                                    navController.navigateSingleTopTo(AddDeviceDestination.route)
                                })
                        }

                        composable(route = AddDeviceDestination.route) {
                            CHAddDevicePage()
                        }

                        composable(
                            route = DeviceDestination.routeWithArgs,
                            arguments = DeviceDestination.arguments,
                            //deepLinks = DeviceDestination.deepLinks

                        ) {
                            //val device = DeviceDestination.parseDevice(it.arguments) ?: ""
                            //val demo = DeviceDestination.parseDemo(it.arguments) ?: ""
                            CHDeviceDetailPage(it, viewModel, onLeft = {
                                navController.popBackStack()
                            }, onRight = {
                                navController.navigateSingleTopTo(SettingDestination.route)
                            })
                        }

                        composable(route = SettingDestination.route) {
                            CHSettingPage()
                        }

                    }
//                    }


                }
            }
        }

        // DeviceActivity.skip(this)
        // DeviceActivity2.skip(this)
        // startActivity(Intent(this,MainActivity::class.java))
         startActivity(Intent(this,DebugActivity::class.java))
    }
}