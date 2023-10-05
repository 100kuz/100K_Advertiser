package uz.yuzka.admin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
import coil.compose.AsyncImage
import uz.yuzka.admin.R
import uz.yuzka.admin.base.SaleStatus
import uz.yuzka.admin.data.response.StatisticsDto
import uz.yuzka.admin.ui.theme.PrimaryColor
import uz.yuzka.admin.ui.viewModel.statistics.StatisticsViewModel
import uz.yuzka.admin.ui.viewModel.statistics.StatisticsViewModelImpl
import uz.yuzka.admin.utils.formatToPrice

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun StatisticsScreen(
    viewModel: StatisticsViewModel = hiltViewModel<StatisticsViewModelImpl>(),
    onUserClick: () -> Unit
) {

    val statistics = viewModel.statistics.collectAsLazyPagingItems()

    val hasLoaded by viewModel.hasLoadedStatistics.observeAsState(false)

    val progress by viewModel.progressFlow.collectAsState(initial = false)
    val getMeDto by viewModel.getMeFlow.collectAsState(initial = null)

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
            viewModel.getStatistics(SaleStatus.ALL)
        }
        viewModel.getMeFromLocal()
    }

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = progress,
            onRefresh = { statistics.refresh() })




    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Statistika",
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

        val selectedTab by viewModel.status.observeAsState(initial = SaleStatus.ALL)
        val tabId = Integer.max(0, tabs.indexOf(selectedTab))

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
                    onClick = {
                        if (selectedTab != SaleStatus.ALL) viewModel.getStatistics(
                            SaleStatus.ALL
                        )
                    },
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
                            viewModel.getStatistics(SaleStatus.NEW)
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
                            viewModel.getStatistics(
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
                            viewModel.getStatistics(SaleStatus.SENT)
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
                            viewModel.getStatistics(
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
                            viewModel.getStatistics(
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
                            viewModel.getStatistics(
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
                            viewModel.getStatistics(
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
                verticalArrangement = Arrangement.spacedBy(1.dp),
                contentPadding = PaddingValues(bottom = 70.dp)
            ) {
                items(statistics, key = {
                    it.id
                }) {
                    it?.let { dto -> ItemStatistics(dto) }
                }

            }
            if (statistics.loadState.refresh is LoadState.NotLoading && statistics.itemCount == 0 || statistics.loadState.refresh is LoadState.Error) {
                Box(
                    modifier = Modifier
                        .padding(top = 45.dp)
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

@Composable
fun ItemStatistics(data: StatisticsDto) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(
                horizontal = 16.dp,
                vertical = 12.dp
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Column(
            verticalArrangement = Arrangement.spacedBy(5.dp),
            modifier = Modifier.weight(0.6f)
        ) {
            Text(
                text = data.name,
                style = TextStyle(
                    fontSize = 17.sp,
                    lineHeight = 20.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF202020),
                )
            )

            Text(
                text = data.product_title ?: "",
                style = TextStyle(
                    fontSize = 15.sp,
                    lineHeight = 18.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF868686),
                )
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.End),
            modifier = Modifier.weight(0.4f)
        ) {

            Column(
                verticalArrangement = Arrangement.spacedBy(3.dp),
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = "${data.orders_count}".formatToPrice(),
                    style = TextStyle(
                        fontSize = 16.sp,
                        lineHeight = 19.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF51AEE7),
                        textAlign = TextAlign.Right,
                    )
                )

                Text(
                    text = "${data.orders_all_count}".formatToPrice(),
                    style = TextStyle(
                        fontSize = 14.sp,
                        lineHeight = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_regular)),
                        fontWeight = FontWeight(400),
                        color = Color(0xFF868686),
                        textAlign = TextAlign.Right,
                    )
                )
            }

            Text(
                text = "0 K",
                style = TextStyle(
                    fontSize = 14.sp,
                    lineHeight = 16.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFFFFFFFF),
                    textAlign = TextAlign.Center,
                ), modifier = Modifier
                    .background(
                        color = Color(0xFF23B60B),
                        shape = RoundedCornerShape(size = 8.dp)
                    )
                    .padding(
                        start = 11.5.dp,
                        top = 9.5.dp,
                        end = 11.5.dp,
                        bottom = 9.5.dp
                    )
            )

        }

    }

}