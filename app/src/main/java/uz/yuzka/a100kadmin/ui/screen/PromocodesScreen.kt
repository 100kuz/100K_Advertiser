package uz.yuzka.a100kadmin.ui.screen

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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.data.response.PromoCodeItem
import uz.yuzka.a100kadmin.ui.theme.BackButton
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModel
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModelImpl

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AllPromoCodeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModelImpl>(),
    onBackPress: () -> Unit,
    onCreateClick: () -> Unit,
    onPromoCodeClick: (PromoCodeItem) -> Unit
) {

    val promoCodes = viewModel.promoCodes.collectAsLazyPagingItems()

    val hasLoaded by viewModel.hasLoadedPromoCodes.observeAsState(false)

    val progress by viewModel.progressFlow.collectAsState(initial = false)

    LaunchedEffect(key1 = null) {
        if (!hasLoaded) {
            viewModel.getPromoCodes()
        }
    }

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = progress,
            onRefresh = { promoCodes.refresh() })


    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Promo-kodlar",
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

        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .pullRefresh(state = pullRefreshState)
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(top = 10.dp, bottom = 70.dp)
            ) {
                items(promoCodes.itemCount, key = {
                    promoCodes[it]?.id ?: Any()
                }) {
                    promoCodes[it]?.let { prm -> ItemPromoCode(prm, onPromoCodeClick) }
                }
            }

            Button(
                onClick = {
                    onCreateClick()
                },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(Color(0xFF55BE61))
            ) {
                Text(
                    text = "Promo-kod yaratish",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFFFFFFFF),
                    )
                )
            }

            PullRefreshIndicator(
                refreshing = progress,
                state = pullRefreshState,
                modifier = Modifier.align(Alignment.TopCenter)
            )

        }
    }
}

@Composable
fun ItemPromoCode(
    data: PromoCodeItem,
    onPromoCodeClick: (PromoCodeItem) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple()) {
                onPromoCodeClick.invoke(data)
            }
    ) {
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
                .padding(
                    horizontal = 16.dp,
                    vertical = 10.dp
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_coupon),
                    contentDescription = null,
                    modifier = Modifier
                        .clip(
                            CircleShape
                        )
                        .size(35.dp)
                        .background(Color(0xFFF2F2F2))
                        .padding(7.dp),
                    tint = Color(0xFF51AEE7)
                )
                Text(
                    text = data.code,
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 19.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(800),
                        color = Color(0xFF202020),
                    )
                )
            }
            Text(
                text = if (data.is_active == 1) "Active" else "O'chirilgan",
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                ),
                modifier = Modifier
                    .background(
                        color = if (data.is_active == 1) Color(0xFF23B60B) else Color(0xFFF1A30C),
                        shape = RoundedCornerShape(size = 5.dp)
                    )
                    .padding(vertical = 5.dp, horizontal = 7.dp)
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp),
            thickness = 1.dp,
            color = Color(0xFFEDEDED)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Tashriflar",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0x73000000),
                )
            )

            Text(
                text = "${data.views}",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF202020),
                    textAlign = TextAlign.Right,
                )
            )

        }

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
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "O‘rnatishlar",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0x73000000),
                )
            )

            Text(
                text = "${data.installs}",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF202020),
                    textAlign = TextAlign.Right,
                )
            )

        }

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
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Mahsulot ko‘rishlar soni",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0x73000000),
                )
            )

            Text(
                text = "${data.products}",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF202020),
                    textAlign = TextAlign.Right,
                )
            )

        }

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
                .padding(vertical = 10.dp, horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "Buyurtmalar",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0x73000000),
                )
            )

            Text(
                text = "${data.orders}",

                // Regular 15px
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF202020),
                    textAlign = TextAlign.Right,
                )
            )

        }

    }

}
