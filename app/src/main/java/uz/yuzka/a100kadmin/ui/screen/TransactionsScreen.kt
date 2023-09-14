package uz.yuzka.a100kadmin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.utils.MaskVisualTransformation

@Composable
@ExperimentalMaterial3Api
fun TransactionsScreen(
) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "To'lovlar",
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

        var sum by remember {
            mutableStateOf("")
        }

        var cardNumber by remember {
            mutableStateOf("")
        }

        Box(
            modifier = Modifier
                .padding(pad)
                .background(Color(0xFFF0F0F0))
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 70.dp)
            ) {
                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                top = 20.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 10.dp
                            ), verticalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        Text(
                            text = "Hisobingizda",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF202020),
                            )
                        )

                        Text(
                            text = "12,000,000 uzs",
                            style = TextStyle(
                                fontSize = 32.sp,
                                lineHeight = 38.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF202020),
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                text = "Taxminiy balans:",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 19.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF868686),
                                )
                            )

                            Text(
                                text = "86,005.500 uzs",
                                style = TextStyle(
                                    fontSize = 16.sp,
                                    lineHeight = 19.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontWeight = FontWeight(400),
                                    color = Color(0xFF202020),
                                )
                            )
                        }



                        Row(
                            modifier = Modifier
                                .padding(top = 7.dp)
                                .fillMaxWidth()
                                .background(Color.White),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFE9EBEA),
                                        shape = RoundedCornerShape(size = 8.dp)
                                    )
                                    .padding(10.dp),
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_copper_coin_fill),
                                    contentDescription = null,
                                    tint = Color.Black
                                )

                                Text(
                                    text = "56",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        lineHeight = 18.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(500),
                                        color = Color.Black,
                                    ),
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }

                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier
                                    .weight(1f)
                                    .border(
                                        width = 1.dp,
                                        color = Color(0xFFE9EBEA),
                                        shape = RoundedCornerShape(size = 8.dp)
                                    )
                                    .padding(10.dp),
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_list),
                                    contentDescription = null,
                                    tint = Color(0xFF51AEE7)
                                )

                                Text(
                                    text = "77709",
                                    style = TextStyle(
                                        fontSize = 15.sp,
                                        lineHeight = 18.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(500),
                                        color = Color(0xFF51AEE7),
                                    ),
                                    modifier = Modifier.padding(start = 5.dp)
                                )
                            }

                        }

                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                top = 20.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 10.dp
                            )
                    ) {

                        Text(
                            text = "Hisobdan pul yechish",

                            // Headline 15px Medium
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF51AEE7),
                            )
                        )

                        Text(
                            text = "Karta raqami",
                            style = TextStyle(
                                fontSize = 14.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF202020),
                            ), modifier = Modifier.padding(top = 10.dp)
                        )

                        TextField(
                            value = cardNumber,
                            onValueChange = { str ->
                                if (str.length <= 16) {
                                    cardNumber = str.filter { it.isDigit() }
                                }
                            },
                            modifier = Modifier.fillMaxWidth(),
                            colors = TextFieldDefaults.colors(
                                disabledContainerColor = Color.White,
                                errorContainerColor = Color.White,
                                unfocusedContainerColor = Color.White,
                                focusedContainerColor = Color.White,
                                errorPlaceholderColor = Color(0xFF8C8C8C),
                                disabledPlaceholderColor = Color(0xFF8C8C8C),
                                unfocusedPlaceholderColor = Color(0xFF8C8C8C),
                                focusedPlaceholderColor = Color(0xFF8C8C8C),
                                disabledTextColor = Color(0xFF8C8C8C),
                                unfocusedTextColor = Color.Black,
                                focusedTextColor = Color.Black,
                                errorTextColor = Color.Red,
                                unfocusedIndicatorColor = Color(0xFF8C8C8C),
                                disabledIndicatorColor = Color(0xFF8C8C8C),
                                errorIndicatorColor = Color.Red,
                                focusedIndicatorColor = Color.Black
                            ), placeholder = {
                                Text(text = "0000 1111 2222 3333", fontSize = 26.sp)
                            },
                            textStyle = TextStyle(fontSize = 26.sp),
                            singleLine = true,
                            visualTransformation = MaskVisualTransformation("#### #### #### ####"),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            verticalAlignment = Alignment.Bottom
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
                                    .padding(top = 5.dp)
                                    .width(IntrinsicSize.Min),
                                singleLine = true,
                                cursorBrush = SolidColor(Color.Black),
                                textStyle = TextStyle(
                                    fontSize = 36.sp,
                                    lineHeight = 45.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                    fontWeight = FontWeight(600),
                                    color = Color.Black,
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
                            )
                            Text(
                                text = "${if ((sum.toIntOrNull() ?: 0) > 0) "" else "0"} uzs",
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
                }

                item {
                    Text(
                        text = "Kutilmoqda",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF51AEE7),
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                top = 20.dp,
                                bottom = 10.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                    )
                }

                items(2) {
                    ItemTransactionRequest()
                }

                item {
                    Text(
                        text = "O'tkazmalar tarixi",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF51AEE7),
                        ),
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                top = 20.dp,
                                bottom = 10.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                    )
                }

                items(10) {
                    ItemTransactionRequest()
                }

            }


            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .padding(horizontal = 20.dp, vertical = 10.dp)
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF55BE61),
                    disabledContainerColor = Color(0xFF8BCC92)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 2.dp),
                enabled = cardNumber.length == 16 && (sum.toIntOrNull() ?: 0) > 1000
            ) {
                Text(
                    text = "Hisobdan pul yechish",
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

@Composable
fun ItemTransactionRequest() {

    Column(
        modifier = Modifier
            .padding(top = 1.dp)
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                top = 10.dp,
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            )
    ) {

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_time_fill),
                    contentDescription = null,
                    tint = Color(0xFFF1A30C),
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFF1A30C),
                            CircleShape
                        )
                        .background(Color(0x33F1A30C))
                        .padding(12.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(
                        text = "9860 1234 5678 9012",
                        style = TextStyle(
                            fontSize = 17.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF202020),
                        )
                    )
                    Text(
                        text = "500,000 so‘m",
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
                modifier = Modifier.align(Alignment.BottomEnd)
            )
        }

    }
}