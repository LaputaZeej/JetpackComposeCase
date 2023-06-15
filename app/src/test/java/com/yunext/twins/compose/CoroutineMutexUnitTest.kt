package com.yunext.twins.compose

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import org.junit.Test

class CoroutineMutexUnitTest {

    @Test
    fun t1() {
        val coroutine = CoroutineScope(Dispatchers.IO)
        var counter = 0L
        repeat(10000) {
            coroutine.launch {
                counter++
            }
        }
        println("final counter = $counter")
    }

    @Test
    fun t2() {
        val coroutine = CoroutineScope(Dispatchers.IO)
        val mutex = Mutex(true)
        var counter = 0L
        repeat(10000) {
            coroutine.launch {
                mutex.withLock {
                    counter++
                }
            }
        }
        Thread.sleep(5000)
        println("final counter = $counter")
    }

    @Test
    fun t3() {
        val coroutine = CoroutineScope(Dispatchers.IO)
        val mutex = Mutex()
        var counter = 0L
        repeat(10000) {
            coroutine.launch {
                mutex.withLock {
                    {
                        launch {
                            counter++
                        }
                    }.invoke()

                }
            }
        }
        println("final counter = $counter")
    }

    private val any = Any()

    @Test
    fun t4() {
        val mutex = Mutex()
        var counter = 0L
        runBlocking {
            launch(Dispatchers.Default) {
                println("====isLock = ${mutex.isLocked}")
                mutex.withLock() {
                    println("====isLock = ${mutex.isLocked}")
                    repeat(10000) {
                        //println("$counter isLock = ${mutex.isLocked}")
                        counter++
                    }
                }
            }.join()
        }

        println("final counter = $counter")
    }

    @Test
    fun t5() {
        val mutex = Mutex()
        var counter = 0L
        runBlocking {
            repeat(10000) {
                launch(Dispatchers.Default) {
                    mutex.withLock() {
                        println("====isLock = ${mutex.isLocked}")
                        //println("$counter isLock = ${mutex.isLocked}")
                        counter++
                    }
                }
            }
        }

        println("final counter = $counter")
    }

    private val list: MutableList<String> = mutableListOf()
    private fun insert(i: String) {
        list.add(i)
    }

    private fun isExist(i: String): Boolean {
        return list.contains(i)
    }

    @Test
    fun t6() {
        val mutex = Mutex()
        println("start :${list.size}")
        runBlocking {
            mutex.withLock {
                repeat(10000) {
                    print("0")
                    insertInternalOk("1")
                }
            }
        }
        println("end :${list.size}")
    }

    private fun insertInternal(i: String) { // 有并发问题
        CoroutineScope(Dispatchers.IO).launch {
            if (!isExist(i)) {
                launch(Dispatchers.IO) {
                    insert(i)
                }
            }
        }
    }

    private suspend fun insertInternalOk(i: String) { // 无并发问题
        coroutineScope() {
            launch(Dispatchers.IO) {
                if (!isExist(i)) {
                    insert(i)
                }
            }
        }
    }


}