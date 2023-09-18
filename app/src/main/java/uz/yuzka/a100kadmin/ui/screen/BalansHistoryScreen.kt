package uz.yuzka.a100kadmin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
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
import uz.yuzka.a100kadmin.data.response.TransactionItem
import uz.yuzka.a100kadmin.ui.screen.tools.BalanceChart
import uz.yuzka.a100kadmin.ui.theme.BackButton
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModel
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModelImpl
import uz.yuzka.a100kadmin.utils.formatToPrice

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun BalanceHistoryScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModelImpl>(),
    onBackPress: () -> Unit
) {

    val transactions = viewModel.transactions.collectAsLazyPagingItems()

    val hasLoaded by viewModel.hasLoadedTransactions.observeAsState(false)

    val progress by viewModel.progressFlow.collectAsState(initial = false)

    LaunchedEffect(key1 = null) {
        if (!hasLoaded) {
            viewModel.getTransactions()
        }
    }

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = progress,
            onRefresh = { transactions.refresh() })



    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Balans tarixi",
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
                .background(Color(0xFFF0F0F0))
                .pullRefresh(pullRefreshState)
        ) {

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                item {
                    BalanceChart(modifier = Modifier.fillMaxWidth())
                }

                items(transactions, key = {
                    it.id
                }
                ) {
                    it?.let { dto -> ItemBalanceHistory(dto) }
                }

                if (transactions.loadState.refresh is LoadState.NotLoading && transactions.itemCount == 0) {
                    item {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(
                                    rememberScrollState()
                                )
                        ) {
                            Text(
                                text = "Ma'lumot mavjud emas...",
                                color = Color.Black,
                                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                                fontWeight = FontWeight.Medium,
                                fontSize = 20.sp, modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }

            if (transactions.loadState.refresh is LoadState.NotLoading) {
                if (transactions.itemCount == 0) Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Text(
                        text = "Ma'lumot mavjud emas...",
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

//@Preview
@Composable
fun ItemBalanceHistory(data: TransactionItem) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                vertical = 10.dp,
                horizontal = 16.dp
            )
    ) {

        Row(
            modifier = Modifier.align(Alignment.CenterStart),
            horizontalArrangement = Arrangement.spacedBy(10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(
                    id = if (data.type == "plus") R.drawable.ic_arrow_left_down_line
                    else R.drawable.ic_arrow_right_up_line
                ),
                contentDescription = null,
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(
                        1.dp,
                        if (data.type == "plus") Color(0xFF23B60B)
                        else Color(0xFFF1A30C),
                        CircleShape
                    )
                    .background(
                        if (data.type == "plus") Color(0x3323B60B)
                        else Color(0x33F1A30C)
                    )
                    .padding(12.dp),
                tint = Color.Unspecified
            )

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "${data.amount.toString().formatToPrice()} so‘m",
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )

                Text(
                    text = data.comment,
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
            modifier = Modifier.align(Alignment.TopEnd)
        )

    }
}