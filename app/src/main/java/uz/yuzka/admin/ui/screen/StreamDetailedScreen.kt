package uz.yuzka.admin.ui.screen

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.material.AlertDialog
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import uz.yuzka.admin.R
import uz.yuzka.admin.ui.theme.BackButton
import uz.yuzka.admin.ui.theme.PrimaryColor
import uz.yuzka.admin.ui.viewModel.main.MainViewModel
import uz.yuzka.admin.ui.viewModel.main.MainViewModelImpl
import uz.yuzka.admin.utils.formatToPrice

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun StreamDetailedScreen(
    id: Int,
    viewModel: MainViewModel = hiltViewModel<MainViewModelImpl>(),
    onBackPress: (Boolean) -> Unit
) {

    val context = LocalContext.current

    val clipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

    LaunchedEffect(id) {
        viewModel.getStreamById(id)
    }

    val data by viewModel.streamFlow.collectAsState(initial = null)
    val progress by viewModel.progressFlow.collectAsState(initial = false)
    val error by viewModel.errorFlow.collectAsState(initial = null)
    val deleteStreamResult by viewModel.deleteStreamFlow.collectAsState(initial = false)

    LaunchedEffect(key1 = deleteStreamResult) {
        if (deleteStreamResult) {
            onBackPress(true)
            viewModel.gotDeleteStream()
        }
    }

    LaunchedEffect(key1 = error) {
        if (error != null) {
            Toast.makeText(context, "$error", Toast.LENGTH_SHORT).show()
            viewModel.gotError()
        }
    }

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = progress,
            onRefresh = { viewModel.getStreamById(id) })

    var showDeleteDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Oqim ma’lumoti",
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
                    onBackPress(false)
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ),
            modifier = Modifier.shadow(2.dp),
            actions = {
                IconButton(
                    onClick = {
                        showDeleteDialog = !showDeleteDialog
                    }
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_delete_bin),
                        contentDescription = null,
                        tint = Color.Black,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }
        )
    }) { pad ->
        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .pullRefresh(pullRefreshState)
        ) {

            if (showDeleteDialog) {
                AlertDialog(onDismissRequest = { showDeleteDialog = false }, confirmButton = {
                    TextButton(
                        onClick = {
                            viewModel.deleteStream(id)
                            showDeleteDialog = false
                        }) {
                        Text(
                            text = "Ha, oqim o'chirilsin",
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
                            showDeleteDialog = false
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
                        text = "Oqimni chindan ham o‘chirmoqchimisiz?",
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
                        text = "Agar o‘chirsangiz oqimni qayta tiklab bo‘lmaydi",
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


            if (data != null) LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
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
                                model = data?.product?.image,
                                contentDescription = null,
                                modifier = Modifier
                                    .height(105.dp)
                                    .width(80.dp)
                                    .clip(RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp)),
                                contentScale = ContentScale.Crop
                            )

                            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                                Text(
                                    text = data?.product?.title ?: "",
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
                                            data?.product?.admin_fee?.toString()?.formatToPrice()
                                                ?: "0"
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
                                    data?.product?.price?.toString()?.formatToPrice()
                                        ?: "0"
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
                                text = "${data?.product?.quantity ?: 0} ta",
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
                            text = "Oqim linki",
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
                            value = data?.link ?: "",
                            onValueChange = {

                            },
                            modifier = Modifier
                                .padding(top = 5.dp)
                                .fillMaxWidth(),
                            singleLine = true,
                            readOnly = true,
                            enabled = false
                        ) {
                            Text(
                                text =
                                data?.link ?: "",
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

                        Row(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {


                            Button(
                                onClick = {
                                    clipboardManager.setPrimaryClip(
                                        ClipData.newPlainText(
                                            "",
                                            data?.link
                                        )
                                    )
                                    Toast.makeText(
                                        context,
                                        "Manzil nusha qilindi!",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                },
                                modifier = Modifier
                                    .weight(1f),
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

                            Button(
                                onClick = {
                                    data?.product_id?.let { viewModel.generatePost(it) }
                                },
                                modifier = Modifier
                                    .weight(1f),
                                shape = RoundedCornerShape(8.dp),
                                colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
                                contentPadding = PaddingValues(vertical = 5.dp)
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.ic_share_rectange),
                                    contentDescription = null
                                )

                                Text(
                                    text = "Reklama posti",
                                    fontSize = 14.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontWeight = FontWeight(500),
                                    color = Color(0xFFFFFFFF),
                                    textAlign = TextAlign.Center
                                )
                            }


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

                            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                                Text(
                                    text = "${
                                        data?.charity?.toString()?.formatToPrice() ?: 0
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

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 20.dp
                            )
                    ) {

                        Text(
                            text = "Statistika",
                            style = TextStyle(
                                fontSize = 20.sp,
                                lineHeight = 23.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF222222),
                            )
                        )

                        Text(
                            text = "Tashriflar",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF838C95),
                            ),
                            modifier = Modifier
                                .padding(top = 15.dp, bottom = 10.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                        Text(
                            text = "${data?.visits ?: 0}",
                            style = TextStyle(
                                fontSize = 24.sp,
                                lineHeight = 28.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight(600),
                                color = Color(0xFF202020),
                                textAlign = TextAlign.Center,
                            ),
                            modifier = Modifier
                                .padding(top = 15.dp, bottom = 10.dp)
                                .align(Alignment.CenterHorizontally)
                        )

                    }
                }


                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 20.dp
                            )
                    ) {
                        Text(
                            text = "Aktivlar",
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF51AEE7),
                            )
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(
                                                Color(0xFFF1A30C)
                                            )
                                    )

                                    Text(
                                        text = "Yangi",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF838C95),
                                        )
                                    )

                                }

                                Text(
                                    text = "${data?.orders_stats?.new ?: 0}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 23.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp),
                                thickness = 1.dp,
                                color = Color(0xFFE9EBEA)
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(
                                                Color(0xFFF1A30C)
                                            )
                                    )

                                    Text(
                                        text = "Qayta qo‘ng’iroq",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF838C95),
                                        )
                                    )

                                }

                                Text(
                                    text = "${data?.orders_stats?.pending ?: 0}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 23.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                        }

                    }
                }


                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 20.dp
                            )
                    ) {
                        Text(
                            text = "Buyurtma",
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF51AEE7),
                            )
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(
                                                Color(0xFF23B60B)
                                            )
                                    )

                                    Text(
                                        text = "Yo‘lda",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF838C95),
                                        )
                                    )

                                }

                                Text(
                                    text = "${data?.orders_stats?.sent ?: 0}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 23.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp),
                                thickness = 1.dp,
                                color = Color(0xFFE9EBEA)
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(
                                                Color(0xFF23B60B)
                                            )
                                    )

                                    Text(
                                        text = "Yetkazib berildi",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF838C95),
                                        )
                                    )

                                }

                                Text(
                                    text = "${data?.orders_stats?.delivered ?: 0}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 23.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp),
                                thickness = 1.dp,
                                color = Color(0xFFE9EBEA)
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(
                                                Color(0xFF23B60B)
                                            )
                                    )

                                    Text(
                                        text = "Qabul qilingan",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF838C95),
                                        )
                                    )

                                }

                                Text(
                                    text = "${data?.orders_stats?.accepted ?: 0}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 23.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                        }

                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(
                                horizontal = 16.dp,
                                vertical = 20.dp
                            )
                    ) {
                        Text(
                            text = "Arxiv",
                            style = TextStyle(
                                fontSize = 15.sp,
                                lineHeight = 18.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(500),
                                color = Color(0xFF51AEE7),
                            )
                        )

                        Row(
                            modifier = Modifier
                                .padding(top = 15.dp)
                                .fillMaxWidth()
                                .height(IntrinsicSize.Min),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(
                                                Color(0xFFF84F57)
                                            )
                                    )

                                    Text(
                                        text = "Spam",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF838C95),
                                        )
                                    )

                                }

                                Text(
                                    text = "${data?.orders_stats?.spam ?: 0}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 23.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp),
                                thickness = 1.dp,
                                color = Color(0xFFE9EBEA)
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(
                                                Color(0xFFF84F57)
                                            )
                                    )

                                    Text(
                                        text = "Qaytib keldi",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF838C95),
                                        )
                                    )

                                }

                                Text(
                                    text = "${data?.orders_stats?.canceled ?: 0}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 23.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                            Divider(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .width(1.dp),
                                thickness = 1.dp,
                                color = Color(0xFFE9EBEA)
                            )

                            Column(
                                verticalArrangement = Arrangement.spacedBy(10.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier.weight(1f)
                            ) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(2.dp)
                                ) {
                                    Spacer(
                                        modifier = Modifier
                                            .clip(CircleShape)
                                            .size(6.dp)
                                            .background(
                                                Color(0xFFF84F57)
                                            )
                                    )

                                    Text(
                                        text = "Arxivlandi",
                                        style = TextStyle(
                                            fontSize = 12.sp,
                                            lineHeight = 13.sp,
                                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                            fontWeight = FontWeight(400),
                                            color = Color(0xFF838C95),
                                        )
                                    )

                                }

                                Text(
                                    text = "${data?.orders_stats?.archived ?: 0}",
                                    style = TextStyle(
                                        fontSize = 20.sp,
                                        lineHeight = 23.sp,
                                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                        fontWeight = FontWeight(600),
                                        color = Color(0xFF222222),
                                        textAlign = TextAlign.Center,
                                    )
                                )

                            }

                        }

                    }
                }

            }

            PullRefreshIndicator(
                refreshing = progress,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

        }
    }
}