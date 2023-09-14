package uz.yuzka.a100kadmin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.ui.theme.BackButton

@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateStreamScreen(onBackPress: () -> Unit) {

    val context = LocalContext.current

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "So'rovlar",
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
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 100.dp)
            ) {
                item {
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

                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 15.dp)
                    ) {
                        Text(
                            text = "Oqim nomi",
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF51AEE7),
                            ),
                            modifier = Modifier.padding(horizontal = 4.dp)
                        )

                        BasicTextField(
                            value = "Birinchi oqim",
                            onValueChange = {

                            },
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .fillMaxWidth(),
                            singleLine = true,
                        ) {
                            Text(
                                text =
                                "Birinchi oqim",
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .background(
                                        Color(0x26C2D9E7),
                                        RoundedCornerShape(12.dp)
                                    )
                                    .padding(
                                        horizontal = 20.dp,
                                        vertical = 10.dp
                                    ),
                                fontSize = 15.sp,
                                color = Color.Black
                            )
                        }

                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(horizontal = 20.dp)
                    ) {
                        Text(
                            text = "Qo‘shimcha",

                            // Headline 15px Medium
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF51AEE7)
                            ),
                            modifier = Modifier.padding(
                                top = 20.dp,
                                bottom = 10.dp
                            )
                        )

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp),
                            thickness = 0.5.dp,
                            color = Color(0xFFEDEDED)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Xayriyaga pul ajratish",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color.Black,
                                )
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_right_icon),
                                contentDescription = null,
                                tint = Color(0xFF868686)
                            )

                        }

                        Divider(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(0.5.dp),
                            thickness = 0.5.dp,
                            color = Color(0xFFEDEDED)
                        )

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 12.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Text(
                                text = "Maxsulotga chegirma qo‘yish",
                                style = TextStyle(
                                    fontSize = 12.sp,
                                    lineHeight = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color.Black,
                                )
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_arrow_right_icon),
                                contentDescription = null,
                                tint = Color(0xFF868686)
                            )

                        }


                    }

                }

            }
            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 30.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF51AEE7)),
                enabled = false
            ) {
                Text(
                    text = "Oqim yaratish",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }
        }
    }
}