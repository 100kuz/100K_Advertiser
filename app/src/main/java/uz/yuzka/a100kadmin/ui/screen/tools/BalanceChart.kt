package uz.yuzka.a100kadmin.ui.screen.tools

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.a100kadmin.R
import kotlin.math.abs
import kotlin.math.max

@Preview
@OptIn(ExperimentalTextApi::class)
@Composable
fun BalanceChart(
    list: List<Pair<Int, Int>> = listOf(
        Pair(1, 10),
        Pair(2, 20),
        Pair(3, 30),
        Pair(4, 40),
        Pair(5, 50),
        Pair(6, 50),
        Pair(7, 40),
        Pair(8, 30),
        Pair(9, 20),
        Pair(10, 10),
        Pair(12, 80)
    ),
    modifier: Modifier = Modifier
) {

    Column(
        modifier = modifier
            .fillMaxWidth()
            .height(300.dp)
            .background(Color.White)
            .padding(
                top = 20.dp,
                start = 16.dp,
                end = 16.dp
            ),
        verticalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        Text(
            text = "To‘lovlar monitoringi",
            style = TextStyle(
                fontSize = 15.sp,
                lineHeight = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF51AEE7),
            )
        )

        Text(
            text = "xarid qilingan va bekor qilingan mahsulotlar statistikasi",
            style = TextStyle(
                fontSize = 12.sp,
                lineHeight = 13.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF9C9A9D),
            )
        )

        val textMeasurer = rememberTextMeasurer()

        val ordinatesTextStyle = TextStyle(
            color = Color(0xFF868686),
            fontSize = 11.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            letterSpacing = 0.sp,
        )

        Canvas(
            modifier = Modifier
                .fillMaxSize()
                .padding(bottom = 10.dp)
        ) {

            var maxY = Int.MIN_VALUE
            list.forEach {
                maxY = max(maxY, it.second)
                it.second
            }

            val spacePerDay = (size.width - 13 - 13f * list.size) / (list.size)

            for (i in list.indices) {
                val y1 = list[i].second
                drawText(
                    textMeasurer,
                    y1.formatToKilos(),
                    Offset(0f, size.height * (1f - 1F * y1 / maxY) - 45f),
                    ordinatesTextStyle
                )
            }

            for (i in list.indices) {
                val x1 = 60.sp.value + (i) * (spacePerDay) + i * 13f

                drawText(
                    textMeasurer,
                    list[i].first.toString(),
                    Offset(x1, size.height - 45f),
                    ordinatesTextStyle
                )

            }

            val cornerRadius = CornerRadius(25f, 25f)

            for (i in list.indices) {

                val coor = list[i]
                val x1 = (i + 1) * (spacePerDay) + i * 13f

                val path = Path()
                path.addRoundRect(
                    RoundRect(
                        rect = Rect(
                            offset = Offset(
                                x1 - spacePerDay / 2,
                                size.height * (1f - (1f * coor.second / maxY)) - 29f
                            ),
                            size = Size(
                                spacePerDay / 1f,
                                1F * size.height * (1F * coor.second / maxY) - 13f
                            ),
                        ),
                        topLeft = cornerRadius,
                        topRight = cornerRadius
                    )
                )

                drawPath(path, if (i % 2 == 0) Color(0xFF51AEE7) else Color(0xFFED5974))
            }

        }

    }
}

fun Int.formatToKilos(): String {
    return if (abs(this) < 1000) return this.toString()
    else if (abs(this) < 1000000) return "${this / 1000}K"
    else if (abs(this) < 1000000000) "${this / 1000000}M"
    else "${this / 1000000000}B"
}