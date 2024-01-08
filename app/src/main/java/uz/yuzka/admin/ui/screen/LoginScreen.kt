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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.admin.R
import uz.yuzka.admin.ui.theme.PrimaryColor
import uz.yuzka.admin.utils.MaskVisualTransformation

@Composable
fun LoginScreen(onVerifyClick: (String) -> Unit, onLoginClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        var phone by remember {
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
                    .padding(top = 100.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Telefon raqamingizni kiriting:",
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
                value = phone,
                onValueChange = { ch ->
                    if (ch.length <= 9) phone = ch.filter { it.isDigit() }
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
                    Text(text = "Telefon raqamingiz")
                },
                placeholder = {
                    Text(text = "+998 (00) 000 00 00")
                },
                textStyle = TextStyle.Default.copy(
                    fontSize = 16.sp
                ),
                visualTransformation = MaskVisualTransformation("+998 (##) ###-##-##"),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Phone
                )
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


            Text(
                text = "Login va parol orqali kirish",
                fontSize = 15.sp,
                lineHeight = 18.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF2196F3),
                textAlign = TextAlign.Center,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .clickable(interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = rememberRipple()) {
                        onLoginClick()
                    }
            )


            Button(
                onClick = {
                    onVerifyClick("998$phone")
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 20.dp
                    )
                    .align(Alignment.CenterHorizontally),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                enabled = checked && phone.length == 9
            ) {
                Text(
                    text = "SMS kodni olish",
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