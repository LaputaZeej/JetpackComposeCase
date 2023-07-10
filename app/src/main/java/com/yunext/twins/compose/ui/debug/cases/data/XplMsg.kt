package com.yunext.twins.compose.ui.debug.cases.data

import com.yunext.twins.compose.R

data class XplMsg(val id: String, val title: String, val desc: String, val icon: Int) {

    companion object {
        val LIST = List(20) {
            XplMsg("$it", "title$it", "desc$it", xplIcons.random())
        }
    }
}

val xplIcons by lazy {
    arrayOf(
        R.drawable.bryce_canyon,
        R.drawable.cathedral_rock,
        R.drawable.death_valley,
        R.drawable.fitzgerald_marine_reserve,
        R.drawable.goldengate,
        R.drawable.golden_gate_bridge,
        R.drawable.grand_canyon,
        R.drawable.horseshoe_bend,
        R.drawable.muir_beach,
        R.drawable.rainbow_falls,
        R.drawable.rockaway_beach,
        R.drawable.sf_coast,
        R.drawable.shipwreck_1,
        R.drawable.shipwreck_2,
    )
}