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
import androidx.compose.foundation.shape.CircleShape
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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.data.response.CharityItem
import uz.yuzka.a100kadmin.ui.theme.BackButton
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModel
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModelImpl

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun CharityHistoryScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModelImpl>(),
    onBackPress: () -> Unit
) {

    val charities = viewModel.charities.collectAsLazyPagingItems()

    val hasLoaded by viewModel.hasLoadedCharities.observeAsState(false)

    val progress by viewModel.progressFlow.collectAsState(initial = false)

    LaunchedEffect(key1 = null) {
        if (!hasLoaded) {
            viewModel.getCharities()
        }
    }

    val pullRefreshState =
        rememberPullRefreshState(
            refreshing = progress,
            onRefresh = { charities.refresh() })

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Xayriya qutisi",
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
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 5.dp)
                            .background(Color.White)
                            .padding(horizontal = 16.dp, vertical = 20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(
                            text = "Umumiy yig’ilgan",
                            style = TextStyle(
                                fontSize = 16.sp,
                                lineHeight = 20.sp,
                                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                fontWeight = FontWeight(400),
                                color = Color(0xFF202020),
                            )
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "780,345,000 uzs",
                                style = TextStyle(
                                    fontSize = 32.sp,
                                    lineHeight = 38.sp,
                                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                                    fontWeight = FontWeight(600),
                                    color = Color(0xFF202020),
                                )
                            )

                            Icon(
                                painter = painterResource(id = R.drawable.ic_warning),
                                contentDescription = null,
                                tint = Color.Unspecified
                            )

                        }
                    }
                }

                items(charities, key = {
                    it.id
                }) {
                    it?.let { ch ->
                        ItemCharityHistory(ch)
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

//@Preview
@Composable
fun ItemCharityHistory(data: CharityItem) {
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
            Text(
                text = "${data.charity} so'm",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color(0xFF23B60B), CircleShape)
                    .background(Color(0x3323B60B))
                    .padding(12.dp),
                fontSize = 10.sp,
                color = Color(0xFF23B60B),
                textAlign = TextAlign.Center
            )

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "${data.product_id} - oqim",
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )

                Text(
                    text = "Tashrif: ${data.visits} ta",
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
            text = "ID: ${data.id}",
            style = TextStyle(
                fontSize = 16.sp,
                lineHeight = 19.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontWeight = FontWeight(500),
                color = Color(0xFF202020),
                textAlign = TextAlign.Right,
            ),
            modifier = Modifier.align(Alignment.CenterEnd)
        )

    }
}
