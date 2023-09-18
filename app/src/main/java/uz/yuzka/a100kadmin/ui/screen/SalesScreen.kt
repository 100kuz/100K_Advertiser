package uz.yuzka.a100kadmin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.base.SaleStatus
import uz.yuzka.a100kadmin.data.response.OrderItem
import uz.yuzka.a100kadmin.ui.theme.BackButton
import uz.yuzka.a100kadmin.ui.theme.PrimaryColor
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModel
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModelImpl
import java.lang.Integer.max

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AllSalesScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModelImpl>(),
    onBackPress: () -> Unit,
    onSaleClick: (Int) -> Unit
) {

    val orders = viewModel.sales.collectAsLazyPagingItems()

    val hasLoaded by viewModel.hasLoadedOrders.observeAsState(false)

    val progress by viewModel.progressFlow.collectAsState(initial = false)

    val tabs = listOf(
        SaleStatus.ALL,
        SaleStatus.NEW,
        SaleStatus.ACCEPTED,
        SaleStatus.SENT,
        SaleStatus.HOLD,
        SaleStatus.DELIVERED,
        SaleStatus.CANCELLED,
        SaleStatus.ARCHIVE,
    )

    LaunchedEffect(key1 = null) {
        if (!hasLoaded) {
            viewModel.getOrders(SaleStatus.ALL)
        }
    }

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = progress,
            onRefresh = { orders.refresh() })



    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "So'rovlar",
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
                    onBackPress.invoke()
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            )
        )
    }) { pad ->

        val selectedTab by viewModel.status.observeAsState(initial = SaleStatus.ALL)
        val tabId = max(0, tabs.indexOf(selectedTab))

        Box(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .pullRefresh(pullRefreshState)
        ) {

            ScrollableTabRow(
                selectedTabIndex = tabId,
                edgePadding = 5.dp,
                indicator = { list ->
                    Spacer(
                        modifier = Modifier
                            .tabIndicatorOffset(list[tabId])
                            .height(4.dp)
                            .background(
                                color = PrimaryColor, RoundedCornerShape(
                                    topStart = 8.dp,
                                    topEnd = 8.dp
                                )
                            )
                    )
                }, modifier = Modifier
                    .fillMaxWidth()
                    .height(41.dp)
            ) {
                Tab(
                    selected = selectedTab is SaleStatus.ALL,
                    onClick = { if (selectedTab != SaleStatus.ALL) viewModel.getOrders(SaleStatus.ALL) },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B),
                    modifier = Modifier.height(41.dp)
                ) {
                    Text(text = "Barchasi", fontSize = 15.sp)
                }
                Tab(
                    selected = selectedTab is SaleStatus.NEW,
                    onClick = {
                        if (selectedTab != SaleStatus.NEW)
                            viewModel.getOrders(SaleStatus.NEW)
                    },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Yangi", fontSize = 15.sp)
                }
                Tab(
                    selected = selectedTab is SaleStatus.ACCEPTED,
                    onClick = {
                        if (selectedTab != SaleStatus.ACCEPTED)
                            viewModel.getOrders(
                                SaleStatus.ACCEPTED
                            )
                    },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Yetkazishga tayyor", fontSize = 15.sp)
                }

                Tab(
                    selected = selectedTab is SaleStatus.SENT,
                    onClick = {
                        if (selectedTab != SaleStatus.SENT)
                            viewModel.getOrders(SaleStatus.SENT)
                    },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Yo'lda", fontSize = 15.sp)
                }

                Tab(
                    selected = selectedTab is SaleStatus.HOLD,
                    onClick = {
                        if (selectedTab != SaleStatus.HOLD)
                            viewModel.getOrders(
                                SaleStatus.HOLD
                            )
                    },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Hold", fontSize = 15.sp)
                }

                Tab(
                    selected = selectedTab is SaleStatus.DELIVERED,
                    onClick = {
                        if (selectedTab != SaleStatus.DELIVERED)
                            viewModel.getOrders(
                                SaleStatus.DELIVERED
                            )
                    },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Yetqazildi", fontSize = 15.sp)
                }

                Tab(
                    selected = selectedTab is SaleStatus.CANCELLED,
                    onClick = {
                        if (selectedTab != SaleStatus.CANCELLED)
                            viewModel.getOrders(
                                SaleStatus.CANCELLED
                            )
                    },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Bekor qilindi", fontSize = 15.sp)
                }

                Tab(
                    selected = selectedTab is SaleStatus.ARCHIVE,
                    onClick = {
                        if (selectedTab != SaleStatus.ARCHIVE)
                            viewModel.getOrders(
                                SaleStatus.ARCHIVE
                            )
                    },
                    selectedContentColor = Color(0xFF51AEE7),
                    unselectedContentColor = Color(0xFF9B9B9B)
                ) {
                    Text(text = "Arxiv", fontSize = 15.sp)
                }
            }

            LazyColumn(
                modifier = Modifier
                    .padding(top = 42.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 70.dp)
            ) {
                items(orders, key = {
                    it.id
                }) {
                    it?.let { ord -> ItemSale(ord, onSaleClick) }
                }
            }

            if (orders.loadState.refresh is LoadState.NotLoading) {
                if (orders.itemCount == 0) Box(
                    modifier = Modifier
                        .padding(top = 42.dp)
                        .fillMaxSize()
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Text(
                        text = "Buyurtmalar mavjud emas...",
                        color = Color.Black,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight.Medium,
                        fontSize = 20.sp, modifier = Modifier.align(Alignment.Center)
                    )
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

@Composable
fun ItemSale(data: OrderItem, onSaleClick: (Int) -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(start = 16.dp, end = 16.dp, bottom = 5.dp)
    ) {

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_list),
                    contentDescription = null
                )

                Column {
                    Text(
                        text = "#${data.id}",
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF202020),
                        )
                    )
                    Text(
                        text = data.created_at,
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9C9A9D),
                        )
                    )

                }

            }

            Text(
                text = data.status,
                style = TextStyle(
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                ), modifier = Modifier
                    .background(
                        color = when (data.status) {
                            SaleStatus.DELIVERED.status -> Color(0xFF23B60B)
                            SaleStatus.NEW.status,
                            SaleStatus.ACCEPTED.status,
                            SaleStatus.SENT.status -> Color(
                                0xFFF1A30C
                            )

                            else -> Color(0xFFD60A0A)
                        },
                        shape = RoundedCornerShape(
                            size = 5.dp
                        )
                    )
                    .padding(
                        start = 7.dp,
                        top = 5.dp,
                        end = 7.dp,
                        bottom = 5.dp
                    )
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp),
            thickness = 0.5.dp,
            color = Color(0xFFE9EBEA)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_message_line),
                    contentDescription = null
                )

                Text(
                    text = data.note ?: data.cancel_reason ?: "",
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF202020),
                    )
                )
            }

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp),
            thickness = 0.5.dp,
            color = Color(0xFFE9EBEA)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_contacts_line),
                    contentDescription = null
                )

                Text(
                    text = data.client_full_name,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF202020),
                    )
                )

            }

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp),
            thickness = 0.5.dp,
            color = Color(0xFFE9EBEA)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_distance_line),
                    contentDescription = null
                )

                Text(
                    text = data.full_address,
                    style = TextStyle(
                        fontSize = 15.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF202020),
                    )
                )

            }

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp),
            thickness = 0.5.dp,
            color = Color(0xFFE9EBEA)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_journal),
                    contentDescription = null
                )

                Column {
                    Text(
                        text = data.product_title,
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF202020),
                        )
                    )

                    Text(
                        text = "#${data.id}",
                        style = TextStyle(
                            fontSize = 12.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF9C9A9D),
                        )
                    )

                }
            }


        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp),
            thickness = 0.5.dp,
            color = Color(0xFFE9EBEA)
        )


        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(interactionSource = remember {
                    MutableInteractionSource()
                }, indication = rememberRipple()) {
                    data.stream_id?.let { onSaleClick.invoke(it) }
                }
                .padding(
                    vertical = 8.dp
                ),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {

                Icon(
                    painter = painterResource(id = R.drawable.ic_link_m),
                    contentDescription = null,
                    tint = Color(0xFF51AEE7)
                )

                Text(
                    text = data.stream_name ?: "${data.stream_id ?: "?"}-oqim",
                    style = TextStyle(
                        fontSize = 15.sp,
                        lineHeight = 18.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF51AEE7),
                    )
                )
            }

            Icon(
                painter = painterResource(id = R.drawable.ic_arrow_right_icon),
                contentDescription = null
            )

        }

        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(0.5.dp),
            thickness = 0.5.dp,
            color = Color(0xFFE9EBEA)
        )


    }

}
