package uz.yuzka.admin.ui.screen

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.yuzka.admin.R
import uz.yuzka.admin.ui.theme.ErrorRed
import uz.yuzka.admin.ui.theme.PrimaryColor
import uz.yuzka.admin.ui.viewModel.auth.AuthViewModel
import uz.yuzka.admin.ui.viewModel.auth.AuthViewModelImpl
import uz.yuzka.admin.utils.MaskVisualTransformation

@Composable
fun VerifyScreen(
    viewModel: AuthViewModel = hiltViewModel<AuthViewModelImpl>(),
    onBackPress: () -> Unit,
    phone: String,
    onLoginClick: (String) -> Unit
) {

    val context = LocalContext.current

    LaunchedEffect(key1 = null) {
        viewModel.startTimer()
    }

    val error by viewModel.errorFlow.collectAsState(initial = null)

    LaunchedEffect(key1 = error) {
        if (error != null) {
            Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            viewModel.gotError()
        }
    }

    val timer by viewModel.time.collectAsState(initial = "0:60")

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {

        var code by remember {
            mutableStateOf("")
        }


        Column(modifier = Modifier.fillMaxWidth()) {

            Image(
                painter = painterResource(id = R.drawable.lock_drawable_red),
                contentDescription = null,
                modifier = Modifier
                    .padding(top = 40.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "Kodni kiriting",
                fontSize = 22.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(600),
                color = Color(0xFF202020),
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 20.dp)
                    .align(Alignment.CenterHorizontally)
            )

            Text(
                text = "SMS kod +$phone raqamiga yuborildi",
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
                value = code,
                onValueChange = { ch ->
                    if (ch.length <= 5) code = ch.filter { it.isDigit() }
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
                    Text(text = "SMS-kod")
                },
                placeholder = {
                    Text(text = "X-X-X-X-X")
                },
                textStyle = TextStyle.Default.copy(
                    fontSize = 16.sp
                ),
                visualTransformation = MaskVisualTransformation("#-#-#-#-#"),
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number
                )
            )

            Text(
                text = "Raqamni o‘zgartirish",
                fontSize = 15.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(400),
                color = Color(0xFF51AEE7),
                textAlign = TextAlign.Center,
                textDecoration = TextDecoration.Underline,
                modifier = Modifier
                    .padding(top = 12.dp)
                    .align(Alignment.CenterHorizontally)
                    .clickable(interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = rememberRipple()) {
                        onBackPress()
                    }
            )

        }

        Text(
            text = "Qayta yuborish uchun vaqt: $timer",
            fontSize = 15.sp,
            lineHeight = 18.sp,
            fontFamily = FontFamily(Font(R.font.roboto_regular)),
            fontWeight = FontWeight(400),
            color = Color(0xFF868686),
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(bottom = 100.dp)
                .align(Alignment.BottomCenter)
        )

        Button(
            onClick = { onLoginClick(code) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 20.dp,
                    vertical = 30.dp
                )
                .align(Alignment.BottomCenter),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            enabled = code.length == 5
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

@Composable
private fun CharView(
    index: Int,
    text: String,
    isError: Boolean = false
) {

    val isFocused = text.length == index

    val char = when {
        index >= text.length -> ""
        else -> text[index].toString()
    }

    Text(
        modifier = Modifier
            .width(48.dp)
            .border(
                1.dp,
                if (isError) Color(0xFFED5974) else if (isFocused) PrimaryColor else Color(
                    0x9951AEE7
                ),
                RoundedCornerShape(8.dp)
            )
            .padding(vertical = 16.dp, horizontal = 15.dp),
        text = char,
        color = if (isError) ErrorRed else Color.Black,
        textAlign = TextAlign.Center,
        fontSize = 20.sp
    )
}