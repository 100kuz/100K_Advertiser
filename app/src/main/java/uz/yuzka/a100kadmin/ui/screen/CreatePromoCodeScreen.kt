package uz.yuzka.a100kadmin.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.ui.theme.BackButton
import uz.yuzka.a100kadmin.ui.theme.PrimaryColor
import uz.yuzka.a100kadmin.ui.viewModel.createpromocode.CreatePromoCodeViewModel
import uz.yuzka.a100kadmin.ui.viewModel.createpromocode.CreatePromoCodeViewModelImpl

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreatePromoCodeScreen(
    viewModel: CreatePromoCodeViewModel =
        hiltViewModel<CreatePromoCodeViewModelImpl>(),
    onBackPress: () -> Unit
) {

    val context = LocalContext.current

    val clipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager

    val error by viewModel.errorFlow.collectAsState(initial = null)

    LaunchedEffect(key1 = error) {
        if (error != null) {
            Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            viewModel.gotError()
        }
    }

    DisposableEffect(key1 = null) {
        onDispose {
            viewModel.clearData()
        }
    }

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Promo-kod yaratish",
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
                )
            )
        }) { pad ->

        var code by remember {
            mutableStateOf("")
        }

        val promoCode by viewModel.hasCreatedPromoCode.collectAsState(initial = null)

        val progress by viewModel.progressFlow.collectAsState(initial = false)


        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
        ) {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
                    .background(Color.White)
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        top = 20.dp,
                        bottom = 10.dp
                    )
            ) {
                Text(
                    text = "Promo-kod nomi",
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF51AEE7),
                    ),
                    modifier = Modifier.padding(horizontal = 4.dp)
                )

                BasicTextField(
                    value = code,
                    onValueChange = {
                        code = it
                    },
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    singleLine = true,
                ) {
                    Text(
                        text = code.ifBlank {
                            "100KUZ"
                        },
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
                        color = if (code.isBlank())
                            Color(0x73000000) else Color.Black
                    )
                }

            }


            Text(
                text = "Quyidagi link orqali ilovani yuklab olish mumkun. Ilovani yuklagan foydalanuvchilar quyidagi promo-kod orqali chegirmalarga ega bo‘lishadi.",
                style = TextStyle(
                    fontSize = 12.sp,
                    lineHeight = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF9C9A9D),
                ),
                modifier = Modifier
                    .padding(
                        top = 105.dp,
                        start = 16.dp,
                        end = 16.dp
                    )
            )

            Button(
                onClick = {
                    viewModel.createPromoCode(code)
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        horizontal = 20.dp,
                        vertical = 30.dp
                    )
                    .align(Alignment.BottomCenter),
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                enabled = code.length >= 4
            ) {
                Text(
                    text = "Promo-kod yaratish",
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                )
            }

            AnimatedVisibility(
                visible = promoCode != null,
                modifier = Modifier.fillMaxSize(),
                enter = fadeIn(animationSpec = tween(700))
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(rememberScrollState())
                        .background(Color.White)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {

                    Text(
                        text = "Promo-kod yaratildi",
                        style = TextStyle(
                            fontSize = 18.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF202020),
                            textAlign = TextAlign.Center,
                        ), modifier = Modifier.padding(top = 30.dp)
                    )

                    Text(
                        text = "Quyidagi Promo-kod orqali ilovadan ro‘yhatdan o‘tgan foydalanuvchilar chegirmalarga ega bo‘lishadi va sizga bonuslar tushishni boshlaydi",
                        style = TextStyle(
                            fontSize = 16.sp,
                            lineHeight = 19.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9C9A9D),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.padding(top = 16.dp)
                    )

                    Box(
                        modifier = Modifier
                            .padding(
                                top = 30.dp
                            )
                            .fillMaxWidth()

                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.bg_promocode),
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxWidth(),
                            contentScale = ContentScale.FillWidth,
                        )

                        Text(
                            text = "100KUZ",
                            style = TextStyle(
                                fontSize = 28.sp,
                                lineHeight = 34.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_bold)),
                                fontWeight = FontWeight(700),
                                color = Color(0xFFFFFFFF),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .align(Alignment.Center)
                                .offset((-30).dp)
                        )

                    }

                    Text(
                        text = promoCode?.url ?: "",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color.Black,
                            textAlign = TextAlign.Start,
                        ),
                        modifier = Modifier
                            .padding(top = 20.dp)
                            .fillMaxWidth()
                            .background(
                                Color(0xFFF5F5F5),
                                RoundedCornerShape(7.dp)
                            )
                            .padding(
                                horizontal = 20.dp,
                                vertical = 10.dp
                            )
                    )

                    Text(
                        text = "Sizning yangi “Promo-kod” manzilingiz",
                        style = TextStyle(
                            fontSize = 12.sp,
                            lineHeight = 14.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9C9A9D),
                            textAlign = TextAlign.Center,
                        ),
                        modifier = Modifier.padding(
                            top = 10.dp
                        )
                    )


                    Button(
                        onClick = {
                            clipboardManager.setPrimaryClip(
                                ClipData.newPlainText(
                                    "",
                                    promoCode?.url
                                )
                            )
                            Toast.makeText(
                                context,
                                "Manzil nusha qilindi!",
                                Toast.LENGTH_SHORT
                            ).show()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                vertical = 30.dp
                            ),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                        contentPadding = PaddingValues(vertical = 5.dp)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.ic_copy_icon),
                            contentDescription = null
                        )

                        Text(
                            text = "Manzilni nusxalash",
                            fontSize = 14.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFFFFFFFF),
                            textAlign = TextAlign.Center
                        )
                    }


                }

            }

            Divider(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp),
                thickness = 1.dp,
                color = Color(0xFFEDEDED)
            )

            if (progress) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))

        }
    }
}