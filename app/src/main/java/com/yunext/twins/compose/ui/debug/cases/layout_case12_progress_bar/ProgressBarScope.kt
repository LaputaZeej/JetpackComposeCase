package com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar

import androidx.compose.foundation.layout.LayoutScopeMarker
import androidx.compose.runtime.Stable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ParentDataModifier
import androidx.compose.ui.unit.Density
import javax.annotation.concurrent.Immutable

@LayoutScopeMarker
@Immutable
object ProgressBarScope {

    @Stable
    fun Modifier.progressBar(
        progress: Float,
        max: Float,
    ): Modifier {
        return this then ProgressBarParentData(progress = progress, max = max)
    }

}

class ProgressBarParentData(val progress: Float, val max: Float) : ParentDataModifier {
    override fun Density.modifyParentData(parentData: Any?): Any {
        return this@ProgressBarParentData
    }
}

// 【构建一个Scope 限定扩展方法使用范围 避免扩展函数泛滥】
// 示例
// 1.定义扩展方法
private fun Int.toHelloInt(): String {
    return "hello $this"
}

// 2.定义一个作用域
@LayoutScopeMarker // 防止跨越作用域调用
@Immutable
object HelloIntScope {
    @Stable
     fun Act.toHelloInt(value: Int): String {
        return this.toString() + value.toHelloInt()
    }

    @Stable
     fun Frg.toHelloIntFrg(value: Int): String {
        return this.toString() + value.toHelloInt()
    }
}

fun use(block:HelloIntScope.()->Unit){
    block(HelloIntScope)
}

fun use2(block:HelloIntScope2.()->Unit){
    block(HelloIntScope2)
}


@LayoutScopeMarker
@Immutable
object HelloIntScope2
class Act
class Frg
