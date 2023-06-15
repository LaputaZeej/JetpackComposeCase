package com.yunext.twins.compose.ui.debug

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.yunext.twins.compose.route.navigateSingleTopTo
import com.yunext.twins.compose.ui.debug.cases.ActionSlot
import com.yunext.twins.compose.ui.debug.cases.HomeCase
import com.yunext.twins.compose.ui.debug.cases.LayoutCase01
import com.yunext.twins.compose.ui.debug.cases.LayoutCase02
import com.yunext.twins.compose.ui.debug.cases.LayoutCase03
import com.yunext.twins.compose.ui.debug.cases.LayoutCase04
import com.yunext.twins.compose.ui.debug.cases.LayoutCase05
import com.yunext.twins.compose.ui.debug.route.DebugCase
import com.yunext.twins.compose.ui.debug.route.LayoutDebugCase01
import com.yunext.twins.compose.ui.debug.route.LayoutDebugCase02
import com.yunext.twins.compose.ui.debug.route.LayoutDebugCase03
import com.yunext.twins.compose.ui.debug.route.LayoutDebugCase04
import com.yunext.twins.compose.ui.debug.route.LayoutDebugCase05
import com.yunext.twins.compose.ui.debug.route.LayoutDebugCase06
import com.yunext.twins.compose.ui.debug.route.LayoutDebugCase07
import com.yunext.twins.compose.ui.debug.route.TitleLevel
import com.yunext.twins.compose.ui.theme.CTwinsTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DebugActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        D.i("DebugActivity::onCreate")
        setContent {
            CTwinsTheme() {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    D.i("DebugActivity::setContent")
                    // vm
                    val viewModel: DebugViewModel = viewModel()
                    val cases by viewModel.cases.collectAsState()
                    LaunchedEffect(key1 = "loadDebugCase") {
                        D.i("loadCase")
                        viewModel.loadCase()
                    }


                    // nav
                    val navController = rememberNavController()
                    val currentBackStackEntryAsState by navController.currentBackStackEntryAsState()
                    val currentRoute = cases.singleOrNull() {
                        it.route == currentBackStackEntryAsState?.destination?.route
                    } ?: DebugCase
//                    var currentRoute:DebugCase by remember {
//                        mutableStateOf(DebugCase)
//                    }
                    D.i("route : $currentRoute")

                    fun NavHostController.navigateSingleTopTo(route: DebugCase){
//                        currentRoute=route
                        navigateSingleTopTo(route.route)
                    }

                    BackHandler() {
                        D.i("BackHandler")
                        // navController.popBackStack()
                        navController.navigateSingleTopTo(DebugCase)
                    }

                    fun hasPre(): DebugCase? {
                        val p = cases.indexOf(currentRoute)
                        if (p < 1) return null
                        var temp: DebugCase
                        var index = p
                        while (index > 0) {
                            index--
                            temp = cases[index]
                            if (temp.level != TitleLevel.First) {
                                return temp
                            }

                        }
                        return null
                    }

                    fun hasNext(): DebugCase? {
                        val p = cases.indexOf(currentRoute)
                        if (p < 0 || p >= cases.size-1) return null
                        var temp: DebugCase
                        var index = p
                        while (index <cases.size-1) {
                            index++
                            temp = cases[index]
                            if (temp.level != TitleLevel.First) {
                                return temp
                            }

                        }
                        return null
                    }

                    ActionSlot(
                        title = currentRoute.desc.title,
                        onPreEnable = {
                            hasPre() != null
                        },
                        onNextEnable = {
                            hasNext() != null
                        },
                        onPre = {
                            val pre = hasPre()
                            if (pre != null) {
                                navController.navigateSingleTopTo(pre)
                            }
                        },
                        onNext = {
                            val next = hasNext()
                            if (next != null) {
                                navController.navigateSingleTopTo(next)
                            }
                        }) {
                        // ui
                        NavHost(
                            navController = navController,
                            startDestination = currentRoute.route
                        ) {
                            composable(DebugCase.route) {
                                HomeCase(cases) { case ->
                                   navController.navigateSingleTopTo(case)
                                }
                            }
//
//                            composable(LayoutDebugCase01.route) {
//                                LayoutCase01()
//                            }
//
//                            composable(LayoutDebugCase02.route) {
//                                LayoutCase02()
//                            }
//
//                            composable(LayoutDebugCase03.route) {
//                                LayoutCase03()
//                            }
//
//                            composable(LayoutDebugCase04.route) {
////                                LayoutCase04()
//                                LayoutDebugCase04.defaultUi()
//                            }
//
//                            composable(LayoutDebugCase05.route) {
//                                LayoutCase05()
////                                LayoutDebugCase05.defaultUi
//                            }
//
//                           /* composable(LayoutDebugCase06.route) {
//                                LayoutDebugCase06.defaultUi()
//                            }*/
//                            composableExt(LayoutDebugCase06)
//
//                            composableExt(LayoutDebugCase07)

                            cases.filter {
                                it.level !=TitleLevel.First
                            }.forEach {
                                composableExt(it)
                            }
                        }
                    }
                }
            }
        }
    }


}

private fun NavGraphBuilder.composableExt(debugCase: DebugCase){
    composable(debugCase.route){
        debugCase.defaultUi()
    }
}