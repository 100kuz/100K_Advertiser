package uz.yuzka.admin.ui.screen

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import kotlinx.coroutines.launch
import uz.yuzka.admin.R
import uz.yuzka.admin.data.request.CreateStreamRequest
import uz.yuzka.admin.ui.screen.tools.ModalSheetType
import uz.yuzka.admin.ui.theme.BackButton
import uz.yuzka.admin.ui.viewModel.main.MainViewModel
import uz.yuzka.admin.utils.formatToPrice

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CreateStreamScreen(
    id: Int,
    viewModel: MainViewModel,
    onBackPress: () -> Unit,
    onCreateSuccess: (Int) -> Unit
) {

    val context = LocalContext.current

    val modalState = rememberModalBottomSheetState(
        initialValue = ModalBottomSheetValue.Hidden,
        skipHalfExpanded = true
    )

    val scope = rememberCoroutineScope()

    var sheetType by remember {
        mutableStateOf<ModalSheetType>(ModalSheetType.SetCharity)
    }

    var name by remember {
        mutableStateOf("")
    }

    val charity = remember {
        mutableStateOf("")
    }

    val product by viewModel.productFlow.observeAsState(initial = null)

    val createStreamResult by viewModel.createStreamFlow.collectAsState(initial = null)
    val error by viewModel.errorFlow.collectAsState(initial = null)
    val progress by viewModel.progressFlow.collectAsState(initial = false)

    LaunchedEffect(key1 = createStreamResult) {
        if (createStreamResult != null) {
            createStreamResult?.data?.id?.let { onCreateSuccess(it) }
            viewModel.gotCreateSuccess()
        }
    }

    LaunchedEffect(key1 = error) {
        if (error != null) {
            Toast.makeText(context, error, Toast.LENGTH_SHORT).show()
            viewModel.gotError()
        }
    }

    ModalBottomSheetLayout(
        sheetState = modalState,
        sheetContent = {
            if (sheetType is ModalSheetType.SetCharity) {
                SetCharityBalance(charity) {
                    charity.value = it
                    scope.launch {
                        modalState.hide()
                    }
                }
            }
        },
        sheetShape = RoundedCornerShape(20.dp)
    ) {

        Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Oqim yaratish",
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
            if (product != null) Box(
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
                                    model = product?.image,
                                    contentDescription = null,
                                    modifier = Modifier
                                        .height(105.dp)
                                        .width(80.dp)
                                        .clip(
                                            RoundedCornerShape(
                                                topStart = 20.dp,
                                                bottomEnd = 20.dp
                                            )
                                        ),
                                    contentScale = ContentScale.Crop
                                )

                                Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                    Text(
                                        text = product?.title ?: "",
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
                                            text = "${
                                                product?.adminFee?.toString()?.formatToPrice() ?: 0
                                            } so'm",
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
                                    text = "${
                                        product?.price?.toString()?.formatToPrice() ?: 0
                                    } so'm",
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
                                    text = product?.quantity?.toString()?.formatToPrice() ?: "0",
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

                            TextField(
                                value = name,
                                onValueChange = {
                                    name = it
                                },
                                singleLine = true,
                                modifier = Modifier
                                    .padding(top = 5.dp)
                                    .fillMaxWidth()
                                    .background(
                                        Color(0x26C2D9E7),
                                        RoundedCornerShape(12.dp)
                                    ),
                                textStyle = TextStyle.Default.copy(
                                    color = Color.Black,
                                    fontSize = 15.sp
                                ),
                                colors = TextFieldDefaults.colors(
                                    errorIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    errorContainerColor = Color.Transparent,
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                ),
                                placeholder = {
                                    Text(
                                        text = "Misol uchun: 1-oqim linki",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            lineHeight = 18.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF8A8A8A),
                                        )
                                    )
                                }
                            )
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
                                    .clickable(interactionSource = remember {
                                        MutableInteractionSource()
                                    }, indication = rememberRipple()) {
                                        sheetType = ModalSheetType.SetCharity
                                        scope.launch {
                                            modalState.show()
                                        }
                                    }
                                    .padding(vertical = 12.dp),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {

                                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                    Text(
                                        text = "${
                                            charity.value.formatToPrice().ifBlank { "0" }
                                        } so'm",
                                        style = TextStyle(
                                            fontSize = 15.sp,
                                            lineHeight = 18.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                            fontWeight = FontWeight(500),
                                            color = Color(0xFF222222),
                                        )
                                    )

                                    Text(
                                        text = "Xayriyaga pul ajratish",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 14.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF83868B),
                                        )
                                    )
                                }
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

                        }

                    }

                }
                Button(
                    onClick = {
                        viewModel.createStream(
                            CreateStreamRequest(
                                charity = charity.value.toIntOrNull(),
                                name = name,
                                product_id = id
                            )
                        )
                    },
                    modifier = Modifier
                        .padding(horizontal = 20.dp, vertical = 30.dp)
                        .align(Alignment.BottomCenter)
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(Color(0xFF51AEE7)),
                    enabled = name.length >= 5
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

                if (progress) CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))


            }
        }
    }
}