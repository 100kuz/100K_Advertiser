package uz.yuzka.admin.ui.screen.tools

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
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.admin.R
import uz.yuzka.admin.data.response.ChartItem
import kotlin.math.abs
import kotlin.math.max

@Preview
@OptIn(ExperimentalTextApi::class)
@Composable
fun BalanceChart(
    modifier: Modifier = Modifier,
    list: List<ChartItem> = listOf(),
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

            if (list.isNotEmpty()) {
                val ordinates = mutableSetOf<Int>()
                var maxY = Int.MIN_VALUE
                list.forEach {
                    maxY = max(maxY, max(it.plus, it.minus))
                    ordinates.add(it.plus)
                    ordinates.add(it.minus)
                }

                var maxYLen = 0

                ordinates.forEach { y1 ->

                    maxYLen = max(
                        maxYLen,
                        textMeasurer.measure(y1.toString(), ordinatesTextStyle).size.width
                    )
                    drawText(
                        textMeasurer,
                        y1.formatToKilos(),
                        Offset(0f, size.height * (1f - 1F * y1 / maxY) - 45f),
                        ordinatesTextStyle
                    )

                }

                val spacePerDay = (size.width - maxYLen) / list.size

                for (i in list.indices) {
                    val x1 = list[i].date
                    val cor = maxYLen + spacePerDay / 2f + i * spacePerDay

                    drawText(
                        textMeasurer,
                        x1.takeLast(2),
                        Offset(
                            1f * cor, size.height - 35
                        ),
                        ordinatesTextStyle

                    )
                }

                val cornerRadius = CornerRadius(25f, 25f)

                for (i in list.indices) {

                    val cor = list[i]
                    val x1 = maxYLen + spacePerDay / 2f + i * spacePerDay

                    val path = Path()
                    path.addRoundRect(
                        RoundRect(
                            rect = Rect(
                                offset = Offset(
                                    x1 - spacePerDay / 4,
                                    size.height * (1f - (1f * cor.plus / maxY)) - 29f
                                ),
                                size = Size(
                                    max(spacePerDay * 0.9f, 5f),
                                    1F * size.height * (1F * cor.plus / maxY)
                                ),
                            ),
                            topLeft = cornerRadius,
                            topRight = cornerRadius
                        )
                    )

                    drawPath(path, Color(0x9951AEE7))
                    path.reset()
                    path.addRoundRect(
                        RoundRect(
                            rect = Rect(
                                offset = Offset(
                                    x1 - spacePerDay / 4,
                                    size.height * (1f - (1f * cor.minus / maxY)) - 29f
                                ),
                                size = Size(
                                    max(spacePerDay * 0.9f, 5f),
                                    1F * size.height * (1F * cor.minus / maxY)
                                ),
                            ),
                            topLeft = cornerRadius,
                            topRight = cornerRadius
                        )
                    )

                    drawPath(path, Color(0x99ED5974))
                }

            } else {

                val textStyle = TextStyle(
                    color = Color(0xFF000000),
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    letterSpacing = 0.sp,
                    textAlign = TextAlign.Center
                )
                val textLayoutResult: TextLayoutResult =
                    textMeasurer.measure(text = AnnotatedString("Grafik chizish ilojsiz..."))
                val textSize = textLayoutResult.size
                drawText(
                    textMeasurer,
                    "Grafik chizish ilojsiz...",
                    Offset(
                        (size.width - textSize.width) / 2f,
                        (size.height - textSize.height) / 2f
                    ),
                    textStyle
                )
            }
        }
    }
}

fun Int.formatToKilos(): String {
    return if (abs(this) < 1000) return this.toString()
    else if (abs(this) < 1000000) return "${(1F * this / 1000).toString().take(5)}K"
    else if (abs(this) < 1000000000) "${(1F * this / 1000000).toString().take(5)} M"
    else "${(1F * this / 1000000000).toString().take(5)}B"
}