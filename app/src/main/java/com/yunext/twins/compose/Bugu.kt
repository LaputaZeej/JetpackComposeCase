package com.yunext.twins.compose

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.bugu.android.logger.BuguLoggers
import com.bugu.android.logger.BuildConfig
import com.bugu.android.logger.cases.point.Point
import com.bugu.android.logger.main.LogContext
import com.bugu.android.logger.upload.AppInfo
import com.bugu.android.logger.upload.PhoneInfo
import kotlinx.coroutines.launch
import java.util.concurrent.Executors
import java.util.concurrent.LinkedBlockingQueue

object Bugu {
    @JvmStatic
    fun init(app: Application) {
        ProcessLifecycleOwner.get().lifecycleScope.launch {
            val param = LogContext.Param.Builder()
                .appId("6140577d7dada821f9635a82")
                .appKey("5551abf7c6924a06bb66d2d0219eb77c")
                .host("")
                .user("ctwins")
                .debug(false)
                .minLogSize(5)
                .mode(0)
                .phoneInfo(PhoneInfo.Builder(app).build())
                .appInfo(
                    AppInfo.Builder(app)
                        .applicationId(BuildConfig.LIBRARY_PACKAGE_NAME)
                        .versionCode("28")
                        .versionName("1.0.0")
                        .buildType(BuildConfig.BUILD_TYPE)
                        .flavor("")
                        .build()
                )
                .build()
            BuguLoggers.init(app, param)
            BuguLoggers.start()
        }
    }
}


sealed interface BuguLoggerTag {
    val logTag: String
    val debug: Boolean
        get() = true

    fun start() {
        if (!debug) return
        BuguLoggers.newTask(logTag)
    }

    fun stop() {
        if (!debug) return
        BuguLoggers.cancelTaskByTag(logTag)
    }

    fun add(msgTag: String, msg: String) {
        if (!debug) return
        BuguLoggers.addCustomByTag(logTag, Point(System.currentTimeMillis(), msgTag, msg))
    }
}

object AppointmentActivityTag : BuguLoggerTag {
    override val logTag: String
        get() = "AppointmentActivity"

}

object StoveOtaUpgradeTag : BuguLoggerTag {
    override val logTag: String
        get() = "ota"

}

object BleConfigTag : BuguLoggerTag {
    override val logTag: String
        get() = "_ble_config_"

}

sealed class AutoTag(val max: Int) : BuguLoggerTag {


    private var count: Long = 0
    private var tag = ""

    override fun stop() {
        super.stop()
        count = 0
        tag = ""
        curCount = 0
        start = false
    }

    private var curCount = 0L
    private val queue: LinkedBlockingQueue<Point> = LinkedBlockingQueue(max)
    private val pool = Executors.newSingleThreadExecutor()
    private var start: Boolean = true

    override fun start() {
        throw java.lang.IllegalStateException("use add auto start...")
    }

    open fun printLog(msgTag: String, msg: String) {
        //Log.d(msgTag, msgTag)
    }

    override fun add(msgTag: String, msg: String) {
        if (!debug) return
        try {
            printLog(msgTag, msg)
            queue.put(Point(tag = msgTag, content = msg, time = System.currentTimeMillis()))
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    init {
        run()
    }

    private fun run() {
        if (!debug) return
        pool.execute {
            try {
                while (start) {
                    val take = queue.take()
                    addInternal(take)
                }
                start = false
            } catch (e: Throwable) {
                e.printStackTrace()
            } finally {

            }
        }
    }

    private fun addInternal(point: Point) {
        if (!debug) return
        // tag：日志文件的tag
        if (tag.isEmpty()) {
            curCount = 0
            tag = logTag + System.currentTimeMillis() + "-" + count + "-${count / max}"
            //LogUtil.e(logTag, "{============= new.....$count <-- $tag ")
            try {
                val id = BuguLoggers.newTask(tag)
                if (id.isNullOrEmpty()) {
                    //LogUtil.e(logTag, "    ============= new empty")
                    tag = ""
                }
            } catch (e: Throwable) {
                e.printStackTrace()
                tag = ""
                //LogUtil.e(logTag, "    ============= new.....error")
            }
        }
        BuguLoggers.addCustomByTag(tag, point.copy(tag = point.tag + "[$curCount]"))
        curCount++

        if (curCount % max == 0L) {
            //LogUtil.e("============= end.....$count <-- $tag }", logTag)
            BuguLoggers.cancelTaskByTag(tag)
            tag = ""
        }
        count++
    }
}

object OkHttpTag : AutoTag(500) {
    override val logTag: String
        get() = "_OkHttp_"
}

object MqttTag : AutoTag(500) {
    override val logTag: String
        get() = "_MqttTag_"

}

object ElectTag : BuguLoggerTag {
    override val logTag: String
        get() = "_elect_"
}

object TuoBangTimingTag : AutoTag(100) {

    override fun printLog(msgTag: String, msg: String) {
        super.printLog(msgTag, msg)
    }
    override val logTag: String
        get() = "_timing_"

}

