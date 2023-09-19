package uz.yuzka.a100kadmin.ui.screen

import androidx.compose.foundation.background
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.data.response.ProductDto
import uz.yuzka.a100kadmin.ui.screen.tools.CustomTab
import uz.yuzka.a100kadmin.ui.theme.PrimaryColor
import uz.yuzka.a100kadmin.ui.viewModel.main.MainViewModel
import uz.yuzka.a100kadmin.ui.viewModel.main.MainViewModelImpl
import uz.yuzka.a100kadmin.utils.formatToPrice

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun MarketScreen(
    viewModel: MainViewModel = hiltViewModel<MainViewModelImpl>(),
    onProductClick: (ProductDto) -> Unit,
    onUserClick: () -> Unit
) {

    val hasLoadedCategories by viewModel.hasLoadedCategories.observeAsState(initial = false)
    val categories = viewModel.categories.collectAsLazyPagingItems()
    val products = viewModel.products.collectAsLazyPagingItems()
    val hasLoadedProducts by viewModel.hasLoadedProducts.observeAsState(initial = false)
    val selectedCategory by viewModel.categoryId.observeAsState(initial = null)
    val progress by viewModel.progressFlow.collectAsState(initial = false)

    LaunchedEffect(hasLoadedCategories) {
        if (!hasLoadedCategories) {
            viewModel.getCategories()
        }
    }

    LaunchedEffect(hasLoadedProducts) {
        if (!hasLoadedProducts) {
            viewModel.getStoreProducts(category = selectedCategory)
        }
    }


    val pullRefreshState = rememberPullRefreshState(
        refreshing = progress,
        onRefresh = { products.refresh() }
    )

    val lazyListState = rememberLazyListState()

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Market",
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
                    Icon(
                        painter = painterResource(id = R.drawable.ic_user),
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

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                contentPadding = PaddingValues(horizontal = 15.dp)
            ) {
                item {
                    CustomTab(
                        selected = selectedCategory == null,
                        onClick = { viewModel.getStoreProducts(null) },
                        selectedContentColor = Color(0xFF51AEE7),
                        unselectedContentColor = Color(0xFF9B9B9B),
                        label = "Barchasi"
                    )
                }

                items(categories.itemCount, key = {
                    categories[it]?.id ?: Any()
                }) {
                    categories[it]?.let { cat ->
                        CustomTab(
                            selected = selectedCategory == cat.id,
                            onClick = { viewModel.getStoreProducts(cat.id) },
                            selectedContentColor = Color(0xFF51AEE7),
                            unselectedContentColor = Color(0xFF9B9B9B),
                            label = cat.title ?: ""
                        )
                    }
                }

            }

            LazyColumn(
                state = lazyListState,
                modifier = Modifier
                    .padding(top = 50.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(bottom = 70.dp)
            ) {
                items(products) {
                    it?.let { pr -> ItemProduct(pr, onProductClick) }
                }
            }


            if (products.loadState.refresh is LoadState.NotLoading) {
                if (products.itemCount == 0) Box(
                    modifier = Modifier
                        .padding(top = 50.dp)
                        .fillMaxSize()
                        .verticalScroll(
                            rememberScrollState()
                        )
                ) {
                    Text(
                        text = "Mahsulot topilmadi...",
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
fun ItemProduct(
    data: ProductDto,
    onProductClick: (ProductDto) -> Unit
) {
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
                model = data.image,
                contentDescription = null,
                modifier = Modifier
                    .height(105.dp)
                    .width(80.dp)
                    .clip(RoundedCornerShape(topStart = 20.dp, bottomEnd = 20.dp)),
                contentScale = ContentScale.Crop
            )

            Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(
                    text = data.title ?: "",
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
                        text = "${data.adminFee?.toString()?.formatToPrice() ?: "0"} so'm",
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
                    vertical = 5.dp
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
                text = "${data.price?.toString()?.formatToPrice() ?: "0"} so'm",
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
                    vertical = 5.dp
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
                    vertical = 5.dp
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
                text = data.quantity?.toString()?.formatToPrice() ?: "0",
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

        Button(
            onClick = {
                onProductClick(data)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    top = 5.dp
                ),
            shape = RoundedCornerShape(8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = PrimaryColor),
            contentPadding = PaddingValues(vertical = 5.dp)
        ) {

            Icon(
                painter = painterResource(id = R.drawable.ic_link_m),
                contentDescription = null,
                modifier = Modifier
                    .size(23.dp)
                    .padding(
                        end = 8.dp
                    )
            )

            Text(
                text = "Oqim yaratish",
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
                fontWeight = FontWeight(500),
                color = Color(0xFFFFFFFF),
                textAlign = TextAlign.Center
            )
        }


    }
}