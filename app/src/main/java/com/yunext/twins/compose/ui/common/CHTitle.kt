package com.yunext.twins.compose.ui.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.requiredWidth
import androidx.compose.foundation.layout.requiredWidthIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import com.yunext.twins.compose.R

@Preview
@Composable
fun CHTitlePreview() {
    Column() {
        Spacer(modifier = Modifier.height(200.dp))
        CHTitle(text = "ahhahah", icon = R.mipmap.icon_twins_wifi)
        CHTitle(text = "ahhahahahhahahahhahahahhahahahhahahahhahahahhahahahhahahahhahah")
        CHTitle(
            text = "ahhahahahhahahahhahahahhahahahhahahahhahahahhahahahhahahahhahah",
            icon = R.mipmap.icon_twins_wifi
        )
    }

}

@Composable
fun CHTitle(
    modifier: Modifier = Modifier,
    text: String,
    icon: Int? = null,
    leftClick: () -> Unit = {},
    rightClick: () -> Unit = {},
    padding: @Composable () -> Unit = {
             Spacer(modifier = Modifier.height(40.dp))
    },
) {
    Column() {
        padding()
        ConstraintLayout(
            modifier
                .fillMaxWidth()
                .height(44.dp)

        ) {
            val (left, title, right) = createRefs()
            Image(
                painter = painterResource(id = R.mipmap.icon_twins_return_nor),
                contentDescription = "back",
                Modifier
                    .clickable {
                        leftClick.invoke()
                    }
                    .applyIcon()

                    .constrainAs(left) {
                        this.top.linkTo(parent.top)
                        this.bottom.linkTo(parent.bottom)
                        this.start.linkTo(parent.start)
                    }
            )
            Row(modifier = Modifier
                .wrapContentSize()
                .constrainAs(title) {
                    this.top.linkTo(parent.top)
                    this.bottom.linkTo(parent.bottom)
                    this.start.linkTo(left.end)
                    this.end.linkTo(right.start)
                }) {
                Text(
                    text = text,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .requiredWidthIn(max = 200.dp),
//                    .width(200.dp),
                    maxLines = 1
                )
                if (icon != null) {
                    Spacer(modifier = Modifier.width(2.dp))
                    Image(
                        painter = painterResource(id = icon),
                        modifier = Modifier.align(Alignment.CenterVertically),
                        contentDescription = "status"
                    )
                }

            }

            Image(
                painter = painterResource(id = R.mipmap.icon_twins_more_nor),
                contentDescription = "more",
                modifier = Modifier
                    .clickable {
                        rightClick.invoke()
                    }
                    .applyIcon()

                    .constrainAs(right) {
                        this.top.linkTo(parent.top)
                        this.bottom.linkTo(parent.bottom)
                        this.end.linkTo(parent.end)
                    }
            )

        }
    }

}

private fun Modifier.applyIcon() = this
    .size(44.dp)
    .padding(10.dp)