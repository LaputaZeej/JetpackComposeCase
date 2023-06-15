package com.yunext.twins.compose.ui.device

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.constraintlayout.helper.widget.Carousel
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentActivity
import com.yunext.twins.compose.R
import com.yunext.twins.compose.base.BaseActivity
import com.yunext.twins.compose.data.DeviceAndState
import com.yunext.twins.compose.ui.theme.CTwinsTheme

@Deprecated("")
class DeviceActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
       setContentView(R.layout.activity_motion_layout)
    }

    companion object{
        @JvmStatic
        fun skip(activity: Activity){
            activity.startActivity(Intent(activity,DeviceActivity::class.java))
        }
    }
}

@Deprecated("")
class DeviceActivity2 : FragmentActivity() {

    private var colors = intArrayOf(
        android.graphics.Color.parseColor("#ffd54f"),
        android.graphics.Color.parseColor("#ffca28"),
        android.graphics.Color.parseColor("#ffc107"),
        android.graphics.Color.parseColor("#ffb300"),
        android.graphics.Color.parseColor("#ffa000"),
        android.graphics.Color.parseColor("#ff8f00"),
        android.graphics.Color.parseColor("#ff6f00"),
        android.graphics.Color.parseColor("#c43e00")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout_carousel)

        WindowCompat.setDecorFitsSystemWindows(window,false)
        window.statusBarColor = android.graphics.Color.TRANSPARENT
        ViewCompat.setOnApplyWindowInsetsListener(findViewById<View>(R.id.content_s)){
            view,insets->
            val systemWindow = insets.getInsets(WindowInsetsCompat.Type.systemBars() or WindowInsetsCompat.Type.displayCutout())
            view.setPadding(0,systemWindow.top,0,0)
            insets
        }



        findViewById<Carousel>(R.id.carousel).apply {
            this.setAdapter(object :Carousel.Adapter{
                override fun count(): Int {
                   return 5
                }

                override fun populate(view: View?, index: Int) {
//                    if (view is TextView) {
                        view?.setBackgroundColor(colors[index])
//                    }
                }

                override fun onNewItem(index: Int) {
                }

            })
        }
    }

    companion object{
        @JvmStatic
        fun skip(activity: Activity){
            activity.startActivity(Intent(activity,DeviceActivity2::class.java))
        }
    }
}
