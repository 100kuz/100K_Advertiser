package uz.yuzka.a100kadmin.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.ui.theme.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScreen(onBackPress: () -> Unit) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Shaxsiy sahifa",
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
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(12.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    Image(
                        painter = painterResource(id = R.drawable.ic_user),
                        contentDescription = null,
                        modifier = Modifier
                            .size(67.dp)
                            .clip(CircleShape)
                            .border(width = 1.dp, color = Color(0xFF51AEE7), CircleShape)
                            .background(color = Color(0x3351AEE7))
                            .padding(15.dp)
                    )

                }


                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 5.dp),
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
                            painter = painterResource(id = R.drawable.ic_link_m),
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

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 15.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(15.dp)
                ) {

                    Text(
                        text = "Ma’lumotlarni o‘zgartirish",

                        // Headline 15px Medium
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF51AEE7),
                        )
                    )

                    TextField(
                        value = "Javohir",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Ismingiz")
                        },
                        placeholder = {
                            Text(text = "Ismingiz...")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF868686),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            errorContainerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color(0xFF51AEE7),
                            focusedPlaceholderColor = Color(0xFF868686),
                            unfocusedPlaceholderColor = Color(0xFF868686),
                            disabledPlaceholderColor = Color(0xFF868686),
                            errorPlaceholderColor = Color(0xFF868686),
                            focusedLabelColor = Color(0xFF51AEE7),
                            disabledLabelColor = Color(0xFF51AEE7),
                            errorLabelColor = Color(0xFF51AEE7),
                            unfocusedLabelColor = Color(0xFF51AEE7),
                            errorIndicatorColor = Color.Red,
                            disabledIndicatorColor = Color(0xFF868686),
                            unfocusedIndicatorColor = Color(0xFF868686)
                        ),
                        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                    )

                    TextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Familiyangiz")
                        },
                        placeholder = {
                            Text(text = "Familiya...")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF868686),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            errorContainerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color(0xFF51AEE7),
                            focusedPlaceholderColor = Color(0xFF868686),
                            unfocusedPlaceholderColor = Color(0xFF868686),
                            disabledPlaceholderColor = Color(0xFF868686),
                            errorPlaceholderColor = Color(0xFF868686),
                            focusedLabelColor = Color(0xFF51AEE7),
                            disabledLabelColor = Color(0xFF51AEE7),
                            errorLabelColor = Color(0xFF51AEE7),
                            unfocusedLabelColor = Color(0xFF51AEE7),
                            errorIndicatorColor = Color.Red,
                            disabledIndicatorColor = Color(0xFF868686),
                            unfocusedIndicatorColor = Color(0xFF868686)
                        ),
                        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                    )

                    TextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Telefon raqam")
                        },
                        placeholder = {
                            Text(text = "+998901234455")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF868686),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            errorContainerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color(0xFF51AEE7),
                            focusedPlaceholderColor = Color(0xFF868686),
                            unfocusedPlaceholderColor = Color(0xFF868686),
                            disabledPlaceholderColor = Color(0xFF868686),
                            errorPlaceholderColor = Color(0xFF868686),
                            focusedLabelColor = Color(0xFF51AEE7),
                            disabledLabelColor = Color(0xFF51AEE7),
                            errorLabelColor = Color(0xFF51AEE7),
                            unfocusedLabelColor = Color(0xFF51AEE7),
                            errorIndicatorColor = Color.Red,
                            disabledIndicatorColor = Color(0xFF868686),
                            unfocusedIndicatorColor = Color(0xFF868686)
                        ),
                        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                    )

                    TextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Viloyat/shahar")
                        },
                        placeholder = {
                            Text(text = "Ismingiz...")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF868686),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            errorContainerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color(0xFF51AEE7),
                            focusedPlaceholderColor = Color(0xFF868686),
                            unfocusedPlaceholderColor = Color(0xFF868686),
                            disabledPlaceholderColor = Color(0xFF868686),
                            errorPlaceholderColor = Color(0xFF868686),
                            focusedLabelColor = Color(0xFF51AEE7),
                            disabledLabelColor = Color(0xFF51AEE7),
                            errorLabelColor = Color(0xFF51AEE7),
                            unfocusedLabelColor = Color(0xFF51AEE7),
                            errorIndicatorColor = Color.Red,
                            disabledIndicatorColor = Color(0xFF868686),
                            unfocusedIndicatorColor = Color(0xFF868686)
                        ),
                        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                    )

                    TextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Tuman")
                        },
                        placeholder = {
                            Text(text = "Ismingiz...")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF868686),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            errorContainerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color(0xFF51AEE7),
                            focusedPlaceholderColor = Color(0xFF868686),
                            unfocusedPlaceholderColor = Color(0xFF868686),
                            disabledPlaceholderColor = Color(0xFF868686),
                            errorPlaceholderColor = Color(0xFF868686),
                            focusedLabelColor = Color(0xFF51AEE7),
                            disabledLabelColor = Color(0xFF51AEE7),
                            errorLabelColor = Color(0xFF51AEE7),
                            unfocusedLabelColor = Color(0xFF51AEE7),
                            errorIndicatorColor = Color.Red,
                            disabledIndicatorColor = Color(0xFF868686),
                            unfocusedIndicatorColor = Color(0xFF868686)
                        ),
                        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                    )

                    TextField(
                        value = "",
                        onValueChange = {

                        },
                        modifier = Modifier.fillMaxWidth(),
                        label = {
                            Text(text = "Manzil")
                        },
                        placeholder = {
                            Text(text = "Sergeli...")
                        },
                        colors = TextFieldDefaults.colors(
                            focusedTextColor = Color.Black,
                            unfocusedTextColor = Color.Black,
                            disabledTextColor = Color(0xFF868686),
                            focusedContainerColor = Color.White,
                            unfocusedContainerColor = Color.White,
                            disabledContainerColor = Color.White,
                            errorContainerColor = Color.White,
                            cursorColor = Color.Black,
                            focusedIndicatorColor = Color(0xFF51AEE7),
                            focusedPlaceholderColor = Color(0xFF868686),
                            unfocusedPlaceholderColor = Color(0xFF868686),
                            disabledPlaceholderColor = Color(0xFF868686),
                            errorPlaceholderColor = Color(0xFF868686),
                            focusedLabelColor = Color(0xFF51AEE7),
                            disabledLabelColor = Color(0xFF51AEE7),
                            errorLabelColor = Color(0xFF51AEE7),
                            unfocusedLabelColor = Color(0xFF51AEE7),
                            errorIndicatorColor = Color.Red,
                            disabledIndicatorColor = Color(0xFF868686),
                            unfocusedIndicatorColor = Color(0xFF868686)
                        ),
                        textStyle = TextStyle(color = Color.Black, fontSize = 15.sp)
                    )

                }


                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start,
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .padding(horizontal = 16.dp, vertical = 12.dp),
                ) {

                    Icon(
                        painter = painterResource(id = R.drawable.ic_logout_box),
                        contentDescription = null,
                        tint = Color.Unspecified
                    )

                    Text(
                        text = "Hisobdan chiqish",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color.Black,
                        ),
                        modifier = Modifier.padding(start = 20.dp)
                    )
                }

            }

            Button(
                onClick = { /*TODO*/ },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(Color(0xFF51AEE7)),
                enabled = false
            ) {
                Text(
                    text = "O'zgarishlarni saqlash",
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