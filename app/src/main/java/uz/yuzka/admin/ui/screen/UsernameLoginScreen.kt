package uz.yuzka.admin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.admin.R
import uz.yuzka.admin.ui.theme.BackButton
import uz.yuzka.admin.ui.theme.PrimaryColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UsernameLoginScreen(onBackPress: () -> Unit, onVerifyClick: (String, String) -> Unit) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(navigationIcon = {
            BackButton {
                onBackPress()
            }
        }, title = {

        }, colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.White))
    }) { pad ->

        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color.White)
        ) {

            var username by remember {
                mutableStateOf("")
            }

            var password by remember {
                mutableStateOf("")
            }

            var checked by remember {
                mutableStateOf(false)
            }

            Column(modifier = Modifier.fillMaxWidth()) {

                Text(
                    text = "Tizimga kirish",
                    fontSize = 22.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF202020),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 36.dp)
                        .align(Alignment.CenterHorizontally)
                )

                Text(
                    text = "Login va parolni kiriting:",
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF868686),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .align(Alignment.CenterHorizontally)
                )

                OutlinedTextField(
                    value = username,
                    onValueChange = { ch ->
                        username = ch
                    },
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 30.dp,
                            end = 16.dp
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedBorderColor = Color(81, 174, 231),
                        focusedBorderColor = Color(81, 174, 231),
                        focusedPlaceholderColor = Color(0xFF868686),
                        unfocusedPlaceholderColor = Color(0xFF868686),
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color(81, 174, 231),
                        unfocusedLabelColor = Color(81, 174, 231),
                        cursorColor = Color.Black
                    ),
                    label = {
                        Text(text = "Login")
                    },
                    placeholder = {
                        Text(text = "Login kiriting...")
                    },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Phone
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                OutlinedTextField(
                    value = password,
                    onValueChange = { ch ->
                        password = ch
                    },
                    modifier = Modifier
                        .padding(
                            start = 16.dp,
                            top = 10.dp,
                            end = 16.dp
                        )
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.Black,
                        unfocusedBorderColor = Color(81, 174, 231),
                        focusedBorderColor = Color(81, 174, 231),
                        focusedPlaceholderColor = Color(0xFF868686),
                        unfocusedPlaceholderColor = Color(0xFF868686),
                        unfocusedTextColor = Color.Black,
                        focusedLabelColor = Color(81, 174, 231),
                        unfocusedLabelColor = Color(81, 174, 231),
                        cursorColor = Color.Black
                    ),
                    label = {
                        Text(text = "Parol")
                    },
                    placeholder = {
                        Text(text = "Parolni kiriting...")
                    },
                    textStyle = TextStyle.Default.copy(
                        fontSize = 16.sp
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation()
                )

                Row(
                    modifier = Modifier
                        .padding(
                            horizontal = 5.dp
                        )
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checked,
                        onCheckedChange = {
                            checked = it
                        },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color(0xFF51AEE7),
                            uncheckedColor = Color(0xFF868686)
                        )
                    )

                    Text(
                        text = "Men foydalanish shartlari bilan tanishdim",
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = if (checked) Color.Black else Color(0xFF868686),
                        modifier = Modifier.clickable(
                            interactionSource = remember {
                                MutableInteractionSource()
                            },
                            indication = rememberRipple()
                        ) {
                            //todo
                        }
                    )

                }

                Button(
                    onClick = {
                        onVerifyClick(username, password)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            horizontal = 20.dp,
                            vertical = 10.dp
                        )
                        .align(Alignment.CenterHorizontally),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                    enabled = username.isNotBlank() && password.isNotBlank()
                ) {
                    Text(
                        text = "Kirish",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                        textAlign = TextAlign.Center,
                    )
                }
            }


        }

    }
}