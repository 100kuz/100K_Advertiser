package uz.yuzka.a100kadmin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.ui.screen.tools.BalanceChart
import uz.yuzka.a100kadmin.ui.theme.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BalanceHistoryScreen(onBackPress: () -> Unit) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Balans tarixi",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )
            },
            navigationIcon = {
                BackButton {
                    onBackPress()
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ), modifier = Modifier.shadow(2.dp)
        )
    }) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .background(Color(0xFFF0F0F0))
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                item {
                    BalanceChart(modifier = Modifier.fillMaxWidth())
                }

                items(20) {
                    ItemBalanceHistory()
                }

            }

        }
    }
}

//@Preview
@Composable
fun ItemBalanceHistory() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                vertical = 10.dp,
                horizontal = 16.dp
            )
    ) {

        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_left_down_line),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFF23B60B), CircleShape)
                    .background(Color(0x3323B60B))
                    .padding(12.dp),
                tint = Color.Unspecified
            )

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "42,000 so‘m",
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )

                Text(
                    text = "#212987-pochta yetqazib berildi.",
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF868686),
                    )
                )
            }

        }

        Text(
            text = "2 daq oldin",
            style = TextStyle(
                fontSize = 11.sp,
                lineHeight = 13.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF8C8C8C),
                textAlign = TextAlign.Right,
            ),
            modifier = Modifier.align(Alignment.TopEnd)
        )

    }
}