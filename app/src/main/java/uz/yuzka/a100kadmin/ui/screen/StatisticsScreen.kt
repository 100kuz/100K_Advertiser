package uz.yuzka.a100kadmin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.ui.theme.PrimaryColor

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StatisticsScreen() {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Statistika",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ), modifier = Modifier.shadow(2.dp)
        )
    }) { pad ->

        var selectedTab by remember {
            mutableIntStateOf(2)
        }

        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
        ) {

            ScrollableTabRow(
                selectedTabIndex = selectedTab,
                edgePadding = 5.dp,
                indicator = { list ->
                    Spacer(
                        modifier = Modifier
                            .tabIndicatorOffset(list[selectedTab])
                            .height(4.dp)
                            .background(
                                color = PrimaryColor, RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp
                                )
                            )
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(41.dp)
            ) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B),
                    modifier = Modifier.height(41.dp)
                ) {
                    Text(text = "Barchasi", fontSize = 15.sp)
                }
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Yangi", fontSize = 15.sp)
                }
                Tab(
                    selected = selectedTab == 2,
                    onClick = { selectedTab = 2 },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Yetkazishga tayyor", fontSize = 15.sp)
                }
                Tab(
                    selected = selectedTab == 3,
                    onClick = { selectedTab = 3 },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Yo'lda", fontSize = 15.sp)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(top = 42.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                contentPadding = PaddingValues(bottom = 70.dp)
            ) {
                items(20) {
                    ItemStatistics()
                }
            }


        }
    }
}

@Composable
fun ItemStatistics() {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text(
                text = "Yangi 888 oqim",
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF202020),
                )
            )

            Text(
                text = "Ratsiya 888",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF868686),
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "1,959",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 19.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF51AEE7),
                        textAlign = TextAlign.Right,
                    )
                )

                Text(
                    text = "7,503",
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF868686),
                        textAlign = TextAlign.Right,
                    )
                )
            }

            Text(
                text = "+50K",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                ), modifier = Modifier
                    .background(
                        color = Color(0xFF23B60B),
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(
                        start = 11.5.dp,
                        top = 9.5.dp,
                        end = 11.5.dp,
                        bottom = 9.5.dp
                    )
            )

        }

    }

}