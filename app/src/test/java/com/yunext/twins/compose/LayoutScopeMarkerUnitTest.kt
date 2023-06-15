package com.yunext.twins.compose

import androidx.compose.foundation.layout.LayoutScopeMarker
import org.junit.Test

class LayoutScopeMarkerUnitTest {

    @Test
    fun t1() {
        test()
    }
}

@LayoutScopeMarker
interface AScope {
    fun visitB(msg: Any) {
        println("AScope::visitB $msg")
    }
    fun visitA(msg: Any) {
        println("visitA $msg")
    }



    companion object : AScope
}

@LayoutScopeMarker
interface BScope {
    fun visitB() {
        println("visitB")
    }

    companion object : BScope
}

fun funA(scope: AScope.() -> Unit) {
    scope(AScope)
}

fun funB(scope: BScope.() -> Unit) {
    scope(BScope)
}

private fun test() {
    funA {
        visitA(1)
        funB {
            // visitA(2) // error : 'fun visitA(msg: Any): Unit' can't be called in this context by implicit receiver. Use the explicit one if necessary
            this@funA.visitA(3)
            this@funA.visitB(4)
            visitB()
        }
    }
}



