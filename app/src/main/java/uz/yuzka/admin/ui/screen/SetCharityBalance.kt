package uz.yuzka.admin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import uz.yuzka.admin.R
import uz.yuzka.admin.ui.theme.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SetCharityBalance(
    charity: State<String>,
    onConfirm: (String) -> Unit
) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Xayriyaga pul ajratish",
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
                    onConfirm(charity.value)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ), modifier = Modifier.shadow(2.dp)
        )
    }) { pad ->

        var sum by remember {
            mutableStateOf(charity.value)
        }

        Box(
            modifier = Modifier
                .padding(pad)
                .background(Color(0xFFF0F0F0))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(bottom = 30.dp),
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_hand_heart_fill),
                        contentDescription = null,
                        modifier = Modifier
                            .size(60.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF51AEE7))
                            .padding(15.dp),
                        tint = Color.White
                    )

                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {

                        Text(
                            text = "Diqqat eslatma!",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 16.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF202020),
                            )
                        )

                        Text(
                            text = "Hayriyaga ajratiladigan summa har bir buyurtmadan 5,000 UZS gacha olinadi",
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 15.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF484848),
                            )
                        )

                    }

                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                ) {

                    Text(
                        text = "Summa",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF51AEE7),
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                start = 20.dp,
                                end = 20.dp,
                                top = 20.dp,
                                bottom = 8.dp
                            )
                    )

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color(0x26C2D9E7))
                    ) {

                        BasicTextField(
                            value = sum,
                            onValueChange = { str ->
                                sum = if (str.startsWith("0")) {
                                    "0"
                                } else
                                    str.filter {
                                        it.isDigit()
                                    }
                            },
                            modifier = Modifier
                                .padding(top = 5.dp, start = 16.dp, end = 16.dp)
                                .fillMaxWidth(),
                            singleLine = true,
                        ) {
                            Row(verticalAlignment = Alignment.Bottom) {
                                Text(
                                    text = sum.ifBlank { "0" },
                                    style = TextStyle(
                                        fontSize = 36.sp,
                                        lineHeight = 45.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF202020),
                                    )
                                )

                                Text(
                                    text = " uzs",
                                    style = TextStyle(
                                        fontSize = 36.sp,
                                        lineHeight = 45.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF8C8C8C),
                                    )
                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .horizontalScroll(rememberScrollState())
                                .padding(
                                    start = 10.dp,
                                    end = 10.dp,
                                    bottom = 16.dp
                                ),
                            horizontalArrangement = Arrangement.spacedBy(10.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Button(
                                onClick = { sum = "1000" },
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(
                                    horizontal = 18.dp,
                                    vertical = 7.dp
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFEAF7ED
                                    )
                                )
                            )
                            {

                                Text(
                                    text = "1000 uzs",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF04A410),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }
                            Button(
                                onClick = { sum = "3000" },
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(
                                    horizontal = 18.dp,
                                    vertical = 7.dp
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFEAF7ED
                                    )
                                )
                            )
                            {

                                Text(
                                    text = "3000 uzs",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF04A410),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                            Button(
                                onClick = { sum = "5000" },
                                shape = RoundedCornerShape(50),
                                contentPadding = PaddingValues(
                                    horizontal = 18.dp,
                                    vertical = 7.dp
                                ),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = Color(
                                        0xFFEAF7ED
                                    )
                                )
                            )
                            {

                                Text(
                                    text = "5000 uzs",
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF04A410),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                        }

                    }
                    Divider(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp),
                        thickness = 1.dp,
                        color = Color(0xFFCACACA)
                    )
                }

                Text(
                    text = "Yuqorida kiritilgan summa ushbu oqim buyurtmasi yetkazib berilganda admin hisobidan ushlab qolinadi. Yig'ilgan mablag'ni bosh sahifadagi “hayriya qutisi” sahifasidan  kuzatishingiz mumkin.",
                    style = TextStyle(
                        fontSize = 12.sp,
                        lineHeight = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF9C9A9D),
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 12.dp)
                )

            }

            Button(
                onClick = { onConfirm(sum) },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 30.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(Color(0xFF51AEE7)),
                enabled = true
            ) {
                Text(
                    text = "Tasdiqlash",
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
