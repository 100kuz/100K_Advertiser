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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MarketScreen(onProductClick: (Int) -> Unit) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Market",
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 70.dp)
            ) {
                items(20) {
                    ItemProduct(onProductClick)
                }
            }


        }
    }
}


@Composable
fun ItemProduct(onProductClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 10.dp
            )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            AsyncImage(
                model = "https://dfstudio-d420.kxcdn.com/wordpress/wp-content/uploads/2019/06/digital_camera_photo-980x653.jpg",
                contentDescription = null,
                modifier = Modifier
                    .height(105.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp)),
                contentScale = ContentScale.Crop
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = "Uy uchun gul idishda - aloe guli",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 19.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF222222),
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_copper_coin_fill),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )
                    Text(
                        text = "94,000 UZS",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 19.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF23B60B),
                        )
                    )
                }

            }

        }

        Row(
            modifier = Modifier
                .padding(
                    horizontal = 4.dp,
                    vertical = 10.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Maxsulot narxi",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF8C8C8C),
                )
            )
            Text(
                text = "130,000 so‘m",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF222222),
                    textAlign = TextAlign.Right,
                )
            )
        }

        Row(
            modifier = Modifier
                .padding(
                    horizontal = 4.dp,
                    vertical = 10.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Yetkazish xizmati",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF8C8C8C),
                )
            )
            Text(
                text = "Pullik",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF222222),
                    textAlign = TextAlign.Right,
                )
            )
        }

        Row(
            modifier = Modifier
                .padding(
                    horizontal = 4.dp,
                    vertical = 10.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Zaxirada",

                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF8C8C8C),
                )
            )
            Text(
                text = "4,204",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF222222),
                    textAlign = TextAlign.Right,
                )
            )
        }

        Button(
            onClick = {
                onProductClick(1)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 5.dp
                ),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            contentPadding = PaddingValues(vertical = 5.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_link_m),
                contentDescription = null,
                modifier = Modifier
                    .size(23.dp)
                    .padding(
                        end = 8.dp
                    )
            )

            Text(
                text = "Oqim yaratish",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center
            )
        }


    }
}