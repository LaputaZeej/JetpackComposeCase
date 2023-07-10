package com.yunext.twins.compose

import com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar.Act
import com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar.Frg
import com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar.HelloIntScope
import com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar.HelloIntScope.toHelloInt
import com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar.HelloIntScope.toHelloIntFrg
import com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar.HelloIntScope2

class TestScope {
    private fun use(block: HelloIntScope.() -> Unit) {
        block(HelloIntScope)
    }

    private fun use2(block: HelloIntScope2.() -> Unit) {
        block(HelloIntScope2)
    }

    fun test() {
        val act = Act()
        val frg = Frg()
        val any = Any()
        use {
            val num = 12
            act.toHelloInt(num)
            frg.toHelloIntFrg(num)
            //any.toHelloInt(num) // error
            use2 {
                // act.toHelloInt(num) // error
            }
        }

        with(HelloIntScope) {
            val num = 12
            act.toHelloInt(num)
            frg.toHelloIntFrg(num)
            // any.toHelloInt(num) // error
            use2 {
                //  act.toHelloInt(num) // error
            }
        }

    }
}