package uz.yuzka.admin.ui.screen

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import uz.yuzka.admin.R
import uz.yuzka.admin.ui.viewModel.home.HomeViewModel
import uz.yuzka.admin.ui.viewModel.home.HomeViewModelImpl
import uz.yuzka.admin.utils.formatToPrice

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel<HomeViewModelImpl>(),
    onPromoCodesClick: () -> Unit,
    onSalesClick: () -> Unit,
    onBalanceClick: () -> Unit,
    onCharityClick: () -> Unit,
    onCompetitionClick: () -> Unit,
    onUserClick: () -> Unit,
) {

    LaunchedEffect(key1 = null) {
        viewModel.getMeFromLocal()
    }

    val getMeDto by viewModel.getMeData.collectAsState(initial = null)
    val hasLoadedGetMe by viewModel.hasLoadedGetMe.observeAsState(initial = false)
    val progress by viewModel.getMeProgressFlow.collectAsState(initial = false)

    LaunchedEffect(key1 = hasLoadedGetMe) {
        if (!hasLoadedGetMe) {
            viewModel.getMe()
        } else {
            viewModel.getMeFromLocal()
        }
    }

    val pullRefreshState = rememberPullRefreshState(
        refreshing = progress,
        onRefresh = {
            viewModel.getMe()
        }
    )

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Bosh sahifa",
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
            ),
            modifier = Modifier.shadow(2.dp),
            navigationIcon = {
                IconButton(
                    onClick = {
                        onUserClick()
                    }
                ) {
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
                .fillMaxSize()
                .background(Color(0xFFF0F0F0))
                .pullRefresh(pullRefreshState)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .shadow(
                            elevation = 10.dp,
                            spotColor = Color(0x0A000000),
                            ambientColor = Color(0x0A000000)
                        )
                        .shadow(
                            elevation = 1.dp,
                            spotColor = Color(0x0D000000),
                            ambientColor = Color(0x0D000000)
                        )
                        .shadow(
                            elevation = 15.dp,
                            spotColor = Color(0x33000000),
                            ambientColor = Color(0x33000000)
                        )
                        .background(Color.White)
                        .padding(
                            horizontal = 16.dp,
                            vertical = 20.dp
                        ),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {

                    Text(
                        text = "Hisobingizda",
                        style = TextStyle(
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(400),
                            color = Color(0xFF202020),
                        )
                    )

                    Text(
                        text = "${
                            (getMeDto?.data?.balance ?: "\uD83D\uDD04").toString().formatToPrice()
                        } uzs",
                        style = TextStyle(
                            fontSize = 32.sp,
                            lineHeight = 38.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
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
                                (getMeDto?.data?.hold_balance ?: "🔄").toString().formatToPrice()
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

                }

                Column(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth()
                        .background(Color.White)
                )
                {

                    Text(
                        text = "Xizmatlar",
                        style = TextStyle(
                            fontSize = 15.sp,
                            lineHeight = 18.sp,
                            fontFamily = FontFamily(Font(R.font.roboto_regular)),
                            fontWeight = FontWeight(500),
                            color = Color(0xFF51AEE7),
                        ),
                        modifier = Modifier.padding(
                            top = 20.dp,
                            bottom = 10.dp,
                            start = 16.dp,
                            end = 16.dp
                        )
                    )

                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(0.5.dp),
                        thickness = 0.5.dp,
                        color = Color(0xFFE9EBEA)
                    )

                    IconTextRowClickable(
                        icon = R.drawable.ic_coupon,
                        label = "Promo-kodlar",
                        onClick = onPromoCodesClick
                    )

                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(0.5.dp),
                        thickness = 0.5.dp,
                        color = Color(0xFFE9EBEA)
                    )

                    IconTextRowClickable(
                        icon = R.drawable.ic_journal,
                        label = "So‘ro‘vlar",
                        onClick = onSalesClick
                    )

                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(0.5.dp),
                        thickness = 0.5.dp,
                        color = Color(0xFFE9EBEA)
                    )

                    IconTextRowClickable(
                        icon = R.drawable.ic_balance,
                        label = "Balans tarixi",
                        onClick = onBalanceClick
                    )

                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(0.5.dp),
                        thickness = 0.5.dp,
                        color = Color(0xFFE9EBEA)
                    )

                    IconTextRowClickable(
                        icon = R.drawable.ic_hand_heart_fill,
                        label = "Xayriya qutisi",
                        onClick = onCharityClick
                    )

                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(0.5.dp),
                        thickness = 0.5.dp,
                        color = Color(0xFFE9EBEA)
                    )

                    IconTextRowClickable(
                        icon = R.drawable.ic_trophy_fill,
                        label = "Konkurslar",
                        onClick = onCompetitionClick
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
fun IconTextRowClickable(
    icon: Int,
    label: String,
    iconTint: Color = Color(0xFF868686),
    labelColor: Color = Color.Black,
    value: String? = null,
    valueColor: Color = Color(0xFF868686),
    onClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(enabled = onClick != null, interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple()) {
                onClick?.invoke()
            }
            .padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = iconTint, modifier = Modifier.size(25.dp)
            )

            Text(
                text = label,
                color = labelColor,
                fontSize = 17.sp,
                fontFamily = FontFamily(Font(R.font.roboto_regular)),
            )

        }

        value?.let {
            Text(
                text = value,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = valueColor,
                )
            )
        }

    }
}