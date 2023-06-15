package com.yunext.twins.compose.ui.debug.route

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import com.yunext.twins.compose.R
import com.yunext.twins.compose.ui.debug.cases.LayoutCase01
import com.yunext.twins.compose.ui.debug.cases.LayoutCase02
import com.yunext.twins.compose.ui.debug.cases.LayoutCase03
import com.yunext.twins.compose.ui.debug.cases.LayoutCase04
import com.yunext.twins.compose.ui.debug.cases.LayoutCase05
import com.yunext.twins.compose.ui.debug.cases.LayoutCase06
import com.yunext.twins.compose.ui.debug.cases.LayoutCase07
import com.yunext.twins.compose.ui.debug.cases.LayoutCase08
import com.yunext.twins.compose.ui.debug.cases.LayoutCase09
import com.yunext.twins.compose.ui.debug.cases.LayoutCase10
import com.yunext.twins.compose.ui.debug.cases.LayoutCase11
import com.yunext.twins.compose.ui.debug.cases.LayoutCase12

@Immutable
data class Desc(val title: String, val url: String, val summary: String = "")

val Desc.desc: String
    get() = "${url}\n${summary}"

enum class TitleLevel {
    First,
    Second
    ;
}

sealed interface DebugCase {
    val route: String
    val desc: Desc
    val level: TitleLevel
        get() = TitleLevel.First
    val sub: Array<DebugCase>
        get() = arrayOf()
    val icon: Int
        get() = R.mipmap.ic_app

    val defaultUi: @Composable () -> Unit
        get() = @Composable {}

    companion object : DebugCase {
        override val route: String
            get() = "DebugCase"
        override val desc: Desc
            get() = Desc(
                "Jetpack Compose 指南",
                "https://developer.android.com/jetpack/compose/layouts/basics?hl=zh-cn",
                ".."
            )
    }
}

object LayoutDebugCase : DebugCase {
    override val route: String
        get() = "LayoutDebugCase"
    override val desc: Desc
        get() = Desc(
            "Compose 布局",
            "https://developer.android.com/jetpack/compose/layouts/basics?hl=zh-cn", ".."
        )
    override val sub: Array<DebugCase>
        get() = arrayOf(
            LayoutDebugCase01,
            LayoutDebugCase02,
            LayoutDebugCase03,
            LayoutDebugCase04,
            LayoutDebugCase05,
            LayoutDebugCase06,
            LayoutDebugCase07,
            LayoutDebugCase08,
            LayoutDebugCase09,
            LayoutDebugCase10,
            LayoutDebugCase11,
            LayoutDebugCase12,
        )

}

object LayoutDebugCase01 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase01"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Compose 布局基础知识",
            "https://developer.android.com/jetpack/compose/layouts/basics?hl=zh-cn", ".."
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = @Composable {
            LayoutCase01()
        }


}

object LayoutDebugCase02 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase02"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Compose 固有特性测量 Intrinsic Measurements ",
            "https://developer.android.com/jetpack/compose/layouts/intrinsic-measurements?hl=zh-cn",
            "Compose 有一项规则，即，子项只能测量一次，测量两次就会引发运行时异常。但是，有时需要先收集一些关于子项的信息，然后再测量子项。\n" +
                    "\n" +
                    "借助固有特性，您可以先查询子项，然后再进行实际测量。"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = @Composable {
            LayoutCase02()
        }

}

object LayoutDebugCase03 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase03"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Compose Material 组件和布局 ",
            "https://developer.android.com/jetpack/compose/layouts/material?hl=zh-cn",
            "Jetpack Compose 提供了 Material Design 的实现，后者是一个用于创建数字化界面的综合设计系统。Material 组件（按钮、卡片、开关等）和布局（如 Scaffold）可作为可组合函数提供。"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = @Composable {
            LayoutCase03()
        }

}

object LayoutDebugCase04 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase04"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Compose Material 自定义布局 ",
            "https://developer.android.com/jetpack/compose/layouts/custom?hl=zh-cn",
            "在界面树中布置每个节点的过程分为三个步骤。每个节点必须：\n" +
                    "\n" +
                    "测量所有子项\n" +
                    "确定自己的尺寸\n" +
                    "放置其子项"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase04()
        }

}

object LayoutDebugCase05 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase05"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Custom layouts and graphics in Compose Base ",
            "https://developer.android.com/jetpack/compose/layouts/custom?hl=zh-cn \n https://youtu.be/xcfEQO0k_gU",
            "自定义一个作息图表Base "
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase05()
        }

}

object LayoutDebugCase06 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase06"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Custom layouts and graphics in Compose ",
            "https://developer.android.com/jetpack/compose/layouts/custom?hl=zh-cn \n https://youtu.be/xcfEQO0k_gU",
            "自定义一个作息图表"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase06()
        }

}

object LayoutDebugCase07 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase07"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Custom layouts and graphics in Compose final ",
            "https://developer.android.com/jetpack/compose/layouts/custom?hl=zh-cn \n https://youtu.be/xcfEQO0k_gU",
            "自定义一个作息图表 final"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase07()
        }

}

object LayoutDebugCase08 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase08"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Custom layouts and graphics in Compose  DrawScope ",
            "https://developer.android.com/jetpack/compose/layouts/custom?hl=zh-cn \n https://youtu.be/xcfEQO0k_gU",
            "自定义一个作息图表 Path"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase08()
        }

}

object LayoutDebugCase09 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase09"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "构建自适应布局 ",
            "https://developer.android.com/jetpack/compose/layouts/adaptive?hl=zh-cn",
            "构建自适应布局 01"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase09()
        }

}

object LayoutDebugCase10 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase10"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "构建自适应布局 ",
            "https://developer.android.com/jetpack/compose/layouts/adaptive?hl=zh-cn",
            "构建自适应布局 02"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase10()
        }

}

object LayoutDebugCase11 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase11"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Nav",
            "https://developer.android.com/jetpack/compose/layouts/alignment-lines?hl=zh-cn",
            "Nav"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase11()
        }

}

object LayoutDebugCase12 : DebugCase {
    override val route: String
        get() = "LayoutDebugCase12"
    override val level: TitleLevel
        get() = TitleLevel.Second
    override val desc: Desc
        get() = Desc(
            "Jetpack Compose 中的对齐线 ",
            "https://developer.android.com/jetpack/compose/layouts/alignment-lines?hl=zh-cn",
            "Jetpack Compose 中的对齐线"
        )
    override val sub: Array<DebugCase>
        get() = super.sub

    override val defaultUi: @Composable () -> Unit
        get() = {
            LayoutCase12()
        }

}