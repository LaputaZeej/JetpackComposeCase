package com.yunext.twins.compose

import com.yunext.twins.compose.ui.debug.cases.demo
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    data class A(val a: String, val b: Int) {

    }

    @Test
    fun t3() {
        runBlocking {
            demo()
        }
    }

    @Test
    fun t2() {
        TestScope().test()
    }

    @Test
    fun addition_isCorrect() {
//        fun test(block: (List<String>) -> Unit) {
//            block(listOf("hello", "world"))
//        }
//
//        test { (a, b, c) ->
//            println(a)
//            println(b)
//            println(c)
//        }


        val P3 by lazy { arrayOf("a1VYw0yasRT", "a1H9poRp5as", "a1Wc1OLmCTd") }
        fun check(data: String): Boolean {
            return P3.any {
                it.equals(data, true)
            }
        }

        println("-${check("")}")
        println("-${check("a1VYw0yasRT")}")
        println("-${check("a1H9poRp5as")}")
        println("-${check("a1Wc1OLmCTd")}")
    }
}