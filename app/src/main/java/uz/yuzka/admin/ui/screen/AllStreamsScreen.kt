package uz.yuzka.admin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import uz.yuzka.admin.data.response.StreamDetailedDto
import uz.yuzka.admin.ui.viewModel.main.MainViewModel
import uz.yuzka.admin.ui.viewModel.main.MainViewModelImpl
import uz.yuzka.admin.utils.formatToPrice

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun AllStreamsContent(
    mainViewModel: MainViewModel = hiltViewModel<MainViewModelImpl>(),
    onStreamClick: (Int) -> Unit,
    onUserClick: () -> Unit
) {

    val hasLoadedStreams by mainViewModel.hasLoadedStreams.observeAsState(initial = false)
    val streams = mainViewModel.streamsFlow.collectAsLazyPagingItems()
    val getMeDto = mainViewModel.getMeFlow.collectAsState(null)
    val progress by mainViewModel.progressFlow.collectAsState(initial = false)

    val pullRefreshState = rememberPullRefreshState(
        refreshing = progress,
        onRefresh = {
            streams.refresh()
        }
    )

    LaunchedEffect(key1 = hasLoadedStreams) {
        if (!hasLoadedStreams) {
            mainViewModel.getAllStreams()
        }
    }

    LaunchedEffect(key1 = null) {
        mainViewModel.getMeFromLocal()
    }

    val lazyListState = rememberLazyListState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Mening oqimlarim",
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
                        model = getMeDto.value?.data?.avatar,
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
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(
                    items = streams,
                    key = {
                        it.id
                    }
                ) {
                    it?.let { ItemStream(it, onStreamClick) }
                }
            }

            if (streams.loadState.refresh is LoadState.NotLoading
                || streams.loadState.refresh is LoadState.Error
            ) {
                if (streams.itemCount == 0) Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Text(
                        text = "Oqim mavjud emas...",
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
                modifier = Modifier.align(
                    Alignment.TopCenter
                )
            )
        }
    }
}

@Composable
fun ItemStream(data: StreamDetailedDto, onStreamClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple()) {
                onStreamClick(data.id)
            }
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
            when (data.product_status) {
                "archived" -> Icon(
                    painter = painterResource(id = R.drawable.ic_archive_line),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE9EBEA),
                            shape = RoundedCornerShape(50)
                        )
                        .background(
                            Color(0xCCEB1414),
                            RoundedCornerShape(50)
                        )
                        .padding(15.dp)
                )

                "deleted" -> Icon(
                    painter = painterResource(id = R.drawable.ic_close_line),
                    tint = Color.White,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE9EBEA),
                            shape = RoundedCornerShape(50)
                        )
                        .background(
                            Color(0xCCEB1414),
                            RoundedCornerShape(50)
                        )
                        .padding(15.dp)
                )

                else -> AsyncImage(
                    model = data.product?.image,
                    contentDescription = null,
                    modifier = Modifier
                        .size(48.dp)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = Color(0xFFE9EBEA),
                            shape = RoundedCornerShape(50)
                        )
                )
            }

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = data.name ?: "",
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )

                Text(
                    text = data.product?.title ?: "---",
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

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(2.dp),
            modifier = Modifier.align(Alignment.TopEnd)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_eye_line),
                contentDescription = "image description",
                tint = Color.Unspecified
            )

            Text(
                text = data.visits.toString().formatToPrice(),
                style = TextStyle(
                    fontSize = 13.sp,
                    lineHeight = 15.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF51AEE7),
                    textAlign = TextAlign.Right,
                )
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.ic_arrow_right_icon),
            contentDescription = null,
            tint = Color(0xFF868686),
            modifier = Modifier.align(Alignment.BottomEnd)
        )

    }
}