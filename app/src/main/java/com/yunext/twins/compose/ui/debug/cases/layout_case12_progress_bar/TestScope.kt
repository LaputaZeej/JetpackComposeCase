package com.yunext.twins.compose.ui.debug.cases.layout_case12_progress_bar

class TestScope {

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