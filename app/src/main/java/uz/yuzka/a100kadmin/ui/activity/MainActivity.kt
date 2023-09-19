package uz.yuzka.a100kadmin.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import dagger.hilt.android.AndroidEntryPoint
import uz.yuzka.a100kadmin.R
import uz.yuzka.a100kadmin.ui.navigation.MainNavigation
import uz.yuzka.a100kadmin.ui.navigation.Screen
import uz.yuzka.a100kadmin.ui.navigation.rememberNavigationState
import uz.yuzka.a100kadmin.ui.screen.AllPromoCodeScreen
import uz.yuzka.a100kadmin.ui.screen.AllSalesScreen
import uz.yuzka.a100kadmin.ui.screen.AllStreamsContent
import uz.yuzka.a100kadmin.ui.screen.BalanceHistoryScreen
import uz.yuzka.a100kadmin.ui.screen.CharityHistoryScreen
import uz.yuzka.a100kadmin.ui.screen.CreatePromoCodeScreen
import uz.yuzka.a100kadmin.ui.screen.CreateStreamScreen
import uz.yuzka.a100kadmin.ui.screen.HomeScreen
import uz.yuzka.a100kadmin.ui.screen.MarketScreen
import uz.yuzka.a100kadmin.ui.screen.StatisticsScreen
import uz.yuzka.a100kadmin.ui.screen.StreamDetailedScreen
import uz.yuzka.a100kadmin.ui.screen.TransactionsScreen
import uz.yuzka.a100kadmin.ui.screen.UserScreen
import uz.yuzka.a100kadmin.ui.viewModel.createpromocode.CreatePromoCodeViewModel
import uz.yuzka.a100kadmin.ui.viewModel.createpromocode.CreatePromoCodeViewModelImpl
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModel
import uz.yuzka.a100kadmin.ui.viewModel.home.HomeViewModelImpl
import uz.yuzka.a100kadmin.ui.viewModel.main.MainViewModel
import uz.yuzka.a100kadmin.ui.viewModel.main.MainViewModelImpl
import uz.yuzka.a100kadmin.ui.viewModel.withdraws.WithdrawsViewModel
import uz.yuzka.a100kadmin.ui.viewModel.withdraws.WithdrawsViewModelImpl

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val bottomDestinations = listOf(
        Screen.HomeContent.route,
        Screen.TransactionsContent.route,
        Screen.StreamsContent.route,
        Screen.MarketContent.route,
        Screen.StatisticsContent.route
    )

    private val homeViewModel: HomeViewModel by viewModels<HomeViewModelImpl>()
    private val withdrawsViewModel: WithdrawsViewModel by viewModels<WithdrawsViewModelImpl>()
    private val mainViewModel: MainViewModel by viewModels<MainViewModelImpl>()
    private val createPromoCodeVM: CreatePromoCodeViewModel by viewModels<CreatePromoCodeViewModelImpl>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val navigationState = rememberNavigationState()
            val destination by navigationState.navHostController.currentBackStackEntryAsState()
            val visible = bottomDestinations.contains(destination?.destination?.route)
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Color(0xFFF0F0F0),
                bottomBar = {
                    AnimatedVisibility(
                        visible = visible,
                        enter = slideInVertically(
                            initialOffsetY = { it },
                            animationSpec = tween(400)
                        ),
                        exit = slideOutVertically(
                            targetOffsetY = { it },
                            animationSpec = tween(10)
                        )
                    ) {
                        Box(modifier = Modifier.fillMaxWidth()) {

                            NavigationBar(
                                modifier = Modifier.fillMaxWidth(),
                                containerColor = Color.White,

                                ) {

                                NavigationBarItem(
                                    selected = destination?.destination?.route == Screen.HomeContent.route,
                                    onClick = { navigationState.navigateTo(Screen.HomeContent.route) },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_home),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    label = {
                                        Text(text = "Asosiy")
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedTextColor = Color.Black,
                                        unselectedTextColor = Color(0xFF8C8C8C),
                                        selectedIconColor = Color.Black,
                                        unselectedIconColor = Color(0xFF8C8C8C),
                                        indicatorColor = Color(0xFFAEDEFC),
                                    )
                                )

                                NavigationBarItem(
                                    selected = destination?.destination?.route == Screen.TransactionsContent.route,
                                    onClick = {
                                        navigationState.navigateTo(Screen.TransactionsContent.route)
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_coupon),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    label = {
                                        Text(text = "To'lov")
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedTextColor = Color.Black,
                                        unselectedTextColor = Color(0xFF8C8C8C),
                                        selectedIconColor = Color.Black,
                                        unselectedIconColor = Color(0xFF8C8C8C),
                                        indicatorColor = Color(0xFFAEDEFC)
                                    )
                                )

                                NavigationBarItem(
                                    selected = destination?.destination?.route == Screen.StreamsContent.route,
                                    onClick = {
                                        navigationState.navigateTo(Screen.StreamsContent.route)
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_link_m),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    label = {
                                        Text(text = "Oqim")
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedTextColor = Color.Black,
                                        unselectedTextColor = Color(0xFF8C8C8C),
                                        selectedIconColor = Color.Black,
                                        unselectedIconColor = Color(0xFF8C8C8C),
                                        indicatorColor = Color(0xFFAEDEFC)
                                    )
                                )

                                NavigationBarItem(
                                    selected = destination?.destination?.route == Screen.MarketContent.route,
                                    onClick = {
                                        navigationState.navigateTo(Screen.MarketContent.route)
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_store),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    label = {
                                        Text(text = "Market")
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedTextColor = Color.Black,
                                        unselectedTextColor = Color(0xFF8C8C8C),
                                        selectedIconColor = Color.Black,
                                        unselectedIconColor = Color(0xFF8C8C8C),
                                        indicatorColor = Color(0xFFAEDEFC)
                                    )
                                )

                                NavigationBarItem(
                                    selected = destination?.destination?.route == Screen.StatisticsContent.route,
                                    onClick = {
                                        navigationState.navigateTo(Screen.StatisticsContent.route)
                                    },
                                    icon = {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_statistics),
                                            contentDescription = null,
                                            modifier = Modifier.size(20.dp)
                                        )
                                    },
                                    label = {
                                        Text(text = "Statistika")
                                    },
                                    colors = NavigationBarItemDefaults.colors(
                                        selectedTextColor = Color.Black,
                                        unselectedTextColor = Color(0xFF8C8C8C),
                                        selectedIconColor = Color.Black,
                                        unselectedIconColor = Color(0xFF8C8C8C),
                                        indicatorColor = Color(0xFFAEDEFC)
                                    )
                                )
                            }

                            Divider(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(1.dp)
                                    .align(Alignment.TopCenter),
                                thickness = 1.dp,
                                color = Color(0xFFE9EBEA)
                            )
                        }
                    }
                }) { pad ->
                Box(
                    modifier = Modifier
                        .padding(pad)
                        .fillMaxSize()
                ) {
                    MainNavigation(
                        navController = navigationState.navHostController,
                        homeContent = {
                            HomeScreen(
                                onPromoCodesClick = {
                                    navigationState.navigateTo(
                                        Screen.PromoCodesContent.route,
                                        false
                                    )
                                },
                                onBalanceClick = {
                                    navigationState.navigateTo(
                                        Screen.BalanceHistoryContent.route,
                                        false
                                    )
                                },
                                onCharityClick = {
                                    navigationState.navigateTo(
                                        Screen.CharityHistoryContent.route,
                                        false
                                    )
                                },
                                onCompetitionClick = {

                                },
                                onSalesClick = {
                                    navigationState.navigateTo(
                                        Screen.SalesContent.route,
                                        false
                                    )
                                },
                                onUserClick = {
                                    navigationState.navigateTo(
                                        Screen.UserContent.route,
                                        false
                                    )
                                }
                            )
                        },
                        transactionsContent = {
                            TransactionsScreen(withdrawsViewModel,
                                onUserClick = {
                                    navigationState.navigateTo(
                                        Screen.UserContent.route,
                                        false
                                    )
                                })
                        },
                        streamsContent = {
                            AllStreamsContent(
                                mainViewModel = mainViewModel,
                                onStreamClick = { id ->
                                    navigationState.navigateTo(
                                        Screen.StreamDetailedContent.getRouteWithArgs(
                                            id
                                        ),
                                        false
                                    )
                                },
                                onUserClick = {
                                    navigationState.navigateTo(
                                        Screen.UserContent.route,
                                        false
                                    )
                                }
                            )
                        },
                        marketContent = {
                            MarketScreen(
                                viewModel = mainViewModel,
                                onProductClick = {
                                    mainViewModel.selectProduct(it)
                                    navigationState.navigateTo(
                                        Screen.CreateStreamContent.getRouteWithArgs(it.id),
                                        false
                                    )
                                },
                                onUserClick = {
                                    navigationState.navigateTo(
                                        Screen.UserContent.route,
                                        false
                                    )
                                }
                            )
                        },
                        statisticsContent = {
                            StatisticsScreen(
                                onUserClick = {
                                    navigationState.navigateTo(
                                        Screen.UserContent.route,
                                        false
                                    )
                                }
                            )
                        },
                        promoCodesContent = {
                            AllPromoCodeScreen(
                                viewModel = homeViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                },
                                onCreateClick = {
                                    navigationState.navigateTo(
                                        Screen.CreatePromoCodeContent.route,
                                        false
                                    )
                                },
                                onPromoCodeClick = {
                                    createPromoCodeVM.setData(promoCodeData = it)
                                    navigationState.navigateTo(
                                        Screen.CreatePromoCodeContent.route,
                                        false
                                    )
                                }
                            )
                        },
                        salesContent = {
                            AllSalesScreen(
                                viewModel = homeViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                },
                                onSaleClick = { id ->
                                    navigationState.navigateTo(
                                        Screen.StreamDetailedContent.getRouteWithArgs(
                                            id
                                        ),
                                        false
                                    )
                                }
                            )
                        },
                        balanceHistoryContent = {
                            BalanceHistoryScreen(
                                viewModel = homeViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                })
                        },
                        charityHistoryContent = {
                            CharityHistoryScreen(
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                }
                            )
                        },
                        userScreenContent = {
                            UserScreen(onBackPress = {
                                navigationState.navHostController.popBackStack()
                            }
                            )
                        },
                        streamItemContent = { id ->
                            StreamDetailedScreen(
                                id,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                }
                            )
                        },
                        createPromoCodeContent = {
                            CreatePromoCodeScreen(
                                viewModel = createPromoCodeVM,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                }
                            )
                        },
                        createStreamContent = { id ->
                            CreateStreamScreen(
                                id = id,
                                viewModel = mainViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                },
                                onCreateSuccess = {
                                    navigationState.popUpTo(Screen.MarketContent.route)
                                    navigationState.navigateTo(
                                        Screen.StreamDetailedContent.getRouteWithArgs(
                                            it
                                        ), false
                                    )
                                }
                            )
                        }
                    )
                }
            }
        }
    }
}
