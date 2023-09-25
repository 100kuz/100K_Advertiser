package uz.yuzka.admin.ui.screen

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import coil.compose.AsyncImage
import uz.yuzka.admin.R
import uz.yuzka.admin.data.request.GetMoneyRequest
import uz.yuzka.admin.data.response.WithdrawsDto
import uz.yuzka.admin.ui.viewModel.withdraws.WithdrawsViewModel
import uz.yuzka.admin.ui.viewModel.withdraws.WithdrawsViewModelImpl
import uz.yuzka.admin.utils.CurrencyAmountInputVisualTransformation
import uz.yuzka.admin.utils.MaskVisualTransformation
import uz.yuzka.admin.utils.formatToPrice

@OptIn(ExperimentalMaterialApi::class)
@Composable
@ExperimentalMaterial3Api
fun TransactionsScreen(
    viewModel: WithdrawsViewModel = hiltViewModel<WithdrawsViewModelImpl>(),
    onUserClick: () -> Unit
) {
    val context = LocalContext.current

    val withdraws = viewModel.withdraws.collectAsLazyPagingItems()

    val getMeDto by viewModel.getMeData.collectAsState(null)

    val hasLoadedWithdraws by viewModel.hasLoadedWithdraws.observeAsState(false)

    val hasCanceled by viewModel.hasCanceled.collectAsState(false)
    val hasCreatedWithdraw by viewModel.hasCreatedWithdraw.collectAsState(false)

    val progress by viewModel.progressFlow.collectAsState(initial = false)
    val error by viewModel.errorFlow.collectAsState(initial = null)

    var cancelId by remember {
        mutableIntStateOf(-1)
    }

    var showCancelDialog by remember {
        mutableStateOf(false)
    }


    var sum by remember {
        mutableStateOf("")
    }

    var cardNumber by remember {
        mutableStateOf("")
    }


    LaunchedEffect(key1 = hasLoadedWithdraws) {
        if (!hasLoadedWithdraws) {
            viewModel.getWithdraws()
        }
    }

    LaunchedEffect(key1 = hasCanceled) {
        if (hasCanceled) {
            withdraws.refresh()
            viewModel.getMeFromLocal()
            viewModel.gotCancel()
        }
    }

    LaunchedEffect(key1 = error) {
        if (error != null) {
            Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            viewModel.gotError()
        }
    }

    LaunchedEffect(key1 = hasCreatedWithdraw) {
        if (hasCreatedWithdraw) {
            cardNumber = ""
            sum = ""
            viewModel.gotCreateSuccess()
            viewModel.getMeFromLocal()
            withdraws.refresh()
        }
    }

    LaunchedEffect(key1 = null) {
        viewModel.getMeFromLocal()
    }

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = progress,
            onRefresh = {
                withdraws.refresh()
                viewModel.getMe()
            }
        )

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
            ), modifier = Modifier.shadow(2.dp),
            navigationIcon = {
                IconButton(onClick = {
                    onUserClick()
                }) {
                    AsyncImage(
                        model = getMeDto?.data?.avatar,
                        contentDescription = null,
                        error = painterResource(id = R.drawable.ic_user),
                        placeholder = painterResource(id = R.drawable.ic_user),
                        modifier = Modifier
                            .size(32.dp)
                            .clip(CircleShape)
                            .border(
                                width = 1.dp,
                                color = Color(0xFFE9EBEA),
                                shape = RoundedCornerShape(50)
                            ),
                        contentScale = ContentScale.Crop
                    )
                }
            }
        )
    }) { pad ->

        Box(
            modifier = Modifier
                .padding(pad)
                .background(Color(0xFFF0F0F0))
                .pullRefresh(pullRefreshState)
        ) {

            if (showCancelDialog) {
                AlertDialog(onDismissRequest = { showCancelDialog = false }, confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.cancelWithdraw(cancelId)
                            showCancelDialog = false
                        }) {
                        Text(
                            text = "Ha, bekor qilinsin",
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 15.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFFD60A0A),
                                textAlign = TextAlign.Right,
                            )
                        )
                    }
                }, dismissButton = {
                    TextButton(
                        onClick = {
                            showCancelDialog = false
                        }
                    ) {
                        Text(
                            text = "Yo‘q, ortga qaytish",
                            style = TextStyle(
                                fontSize = 13.sp,
                                lineHeight = 15.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF51AEE7),
                                textAlign = TextAlign.Right,
                            )
                        )
                    }
                }, title = {
                    Text(
                        text = "To‘lo‘v uchun so‘rovingiz bekor qilinmoqda",
                        style = TextStyle(
                            fontSize = 20.sp,
                            lineHeight = 23.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF222222),
                        )
                    )
                }, text = {
                    Text(
                        text = "Hisobingizga pul 10 daqiqa ichida qayta tushirib beriladi",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF83868B),
                        )
                    )
                }, shape = RoundedCornerShape(20.dp),
                    backgroundColor = Color(0xE6FFFFFF)
                )
            }

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
                            text = "${
                                getMeDto?.data?.balance?.toString()?.formatToPrice()?.ifBlank {
                                    "0"
                                }
                            } uzs",
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
                                text = "${
                                    getMeDto?.data?.hold_balance?.toString()?.formatToPrice()
                                        ?.ifBlank {
                                            "0"
                                        }
                                } uzs",
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
                                    text = "${getMeDto?.data?.coins ?: 0}",
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
                                    text = "${getMeDto?.data?.id}",
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
                            TextField(
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
                                    .wrapContentWidth(Alignment.Start),
                                singleLine = true,
                                textStyle = TextStyle(
                                    fontSize = 30.sp,
                                    lineHeight = 45.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                    fontWeight = FontWeight(600),
                                    color = Color.Black,
                                ),
                                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    errorContainerColor = Color.Transparent,
                                    disabledContainerColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent,
                                    disabledIndicatorColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    errorIndicatorColor = Color.Transparent,
                                ),
                                placeholder = {
                                    Text(
                                        text = "0 so'm",
                                        style = TextStyle(
                                            fontSize = 30.sp,
                                            lineHeight = 45.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                            fontWeight = FontWeight(600),
                                            color = Color(0xFF8C8C8C),
                                        )
                                    )
                                },
                                visualTransformation = CurrencyAmountInputVisualTransformation()
                            )

                        }

                    }
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

                items(withdraws, key = {
                    it.id
                }) {
                    it?.let { wth ->
                        ItemTransactionRequest(
                            wth
                        ) {
                            cancelId = it
                            showCancelDialog = true
                        }
                    }
                }

                if (withdraws.loadState.refresh is LoadState.NotLoading && withdraws.itemCount == 0 || withdraws.loadState.refresh is LoadState.Error)
                    item {
                        Box(
                            modifier = Modifier
                                .padding(top = 50.dp)
                                .fillMaxWidth()
                        ) {
                            Text(
                                text = "O'tkazmalar mavjud emas...",
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp, modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
            }



            PullRefreshIndicator(
                refreshing = progress, state = pullRefreshState, modifier = Modifier.align(
                    Alignment.TopCenter
                )
            )

            Button(
                onClick = {
                    viewModel.createWithdraw(
                        GetMoneyRequest(
                            cardNumber,
                            sum.toLongOrNull() ?: 0
                        )
                    )
                },
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
fun ItemTransactionRequest(
    data: WithdrawsDto,
    onCancelClick: (Int) -> Unit
) {

    var expanded by remember {
        mutableStateOf(false)
    }

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
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = null) {
                expanded = !expanded
            }
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
                    painter = painterResource(
                        id = when (data.status) {
                            "new" -> R.drawable.ic_time_fill
                            "cancelled" -> R
                                .drawable.ic_cancelled

                            else -> R.drawable.ic_success_green
                        }
                    ),
                    contentDescription = null,
                    tint = Color.Unspecified,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFF1A30C),
                            CircleShape
                        )
                        .background(Color(if (data.status == "new") 0x33F1A30C else if (data.status == "cancelled") 0x33ED5974 else 0x3323B60B))
                        .padding(12.dp)
                )

                Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(
                        text = data.account,
                        style = TextStyle(
                            fontSize = 17.sp,
                            lineHeight = 20.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_medium)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF202020),
                        )
                    )
                    Text(
                        text = "${data.amount.toString().formatToPrice()} so‘m",
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
                text = data.created_at_label,
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

        if (data.status == "new") AnimatedVisibility(visible = expanded) {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(7.dp))
                    .border(
                        width = 1.dp,
                        color = Color(0xFFD60A0A),
                        shape = RoundedCornerShape(size = 7.dp)
                    )
                    .clickable(interactionSource = remember {
                        MutableInteractionSource()
                    }, indication = rememberRipple()) {
                        onCancelClick(data.id)
                    }
                    .padding(vertical = 10.dp),
                horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_close_line),
                    contentDescription = null,
                    tint = Color(0xFFD60A0A),
                    modifier = Modifier.size(16.dp)
                )

                Text(
                    text = "Bekor qilish",
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 15.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFD60A0A),
                    )
                )
            }

        }

    }
}