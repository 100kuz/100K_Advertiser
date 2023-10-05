package uz.yuzka.admin.ui.activity

import android.content.Intent
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
import com.google.android.play.core.appupdate.AppUpdateInfo
import com.google.android.play.core.appupdate.AppUpdateManager
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallState
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability
import com.google.firebase.messaging.FirebaseMessaging
import dagger.hilt.android.AndroidEntryPoint
import uz.yuzka.admin.R
import uz.yuzka.admin.data.request.SetDeviceTokenRequest
import uz.yuzka.admin.pref.MyPref
import uz.yuzka.admin.ui.navigation.MainNavigation
import uz.yuzka.admin.ui.navigation.Screen
import uz.yuzka.admin.ui.navigation.rememberNavigationState
import uz.yuzka.admin.ui.screen.AllPromoCodeScreen
import uz.yuzka.admin.ui.screen.AllSalesScreen
import uz.yuzka.admin.ui.screen.AllStreamsContent
import uz.yuzka.admin.ui.screen.BalanceHistoryScreen
import uz.yuzka.admin.ui.screen.CharityHistoryScreen
import uz.yuzka.admin.ui.screen.CreatePromoCodeScreen
import uz.yuzka.admin.ui.screen.CreateStreamScreen
import uz.yuzka.admin.ui.screen.HomeScreen
import uz.yuzka.admin.ui.screen.MarketScreen
import uz.yuzka.admin.ui.screen.StatisticsScreen
import uz.yuzka.admin.ui.screen.StreamDetailedScreen
import uz.yuzka.admin.ui.screen.TransactionsScreen
import uz.yuzka.admin.ui.screen.UserScreen
import uz.yuzka.admin.ui.viewModel.createpromocode.CreatePromoCodeViewModel
import uz.yuzka.admin.ui.viewModel.createpromocode.CreatePromoCodeViewModelImpl
import uz.yuzka.admin.ui.viewModel.home.HomeViewModel
import uz.yuzka.admin.ui.viewModel.home.HomeViewModelImpl
import uz.yuzka.admin.ui.viewModel.main.MainViewModel
import uz.yuzka.admin.ui.viewModel.main.MainViewModelImpl
import uz.yuzka.admin.ui.viewModel.withdraws.WithdrawsViewModel
import uz.yuzka.admin.ui.viewModel.withdraws.WithdrawsViewModelImpl
import javax.inject.Inject

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    companion object {
        const val APP_UPDATE_REQUEST_CODE = 100
    }


    @Inject
    lateinit var pref: MyPref

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

    private lateinit var appUpdateManager: AppUpdateManager
    private var updateInfo: AppUpdateInfo? = null
    private var updateListener = InstallStateUpdatedListener { state: InstallState ->

        if (state.installStatus() == InstallStatus.DOWNLOADED) {
            appUpdateManager.completeUpdate()
        }
    }


    private var installStateUpdatedListener: InstallStateUpdatedListener =
        object : InstallStateUpdatedListener {
            override fun onStateUpdate(state: InstallState) {
                if (state.installStatus() == InstallStatus.DOWNLOADED) {
                    appUpdateManager.completeUpdate()
                } else if (state.installStatus() == InstallStatus.INSTALLED) {
                    appUpdateManager.unregisterListener(this)
                }
            }
        }

    private fun startForInAppUpdate(it: AppUpdateInfo?) {
        try {
            appUpdateManager.startUpdateFlowForResult(
                it!!,
                AppUpdateType.IMMEDIATE,
                this,
                APP_UPDATE_REQUEST_CODE
            )
        } catch (_: java.lang.Exception) {

        }
    }

    private fun checkForUpdate() {
        appUpdateManager.appUpdateInfo.addOnSuccessListener {
            if (it.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                it.isUpdateTypeAllowed(AppUpdateType.IMMEDIATE)
            ) {
                updateInfo = it
                startForInAppUpdate(updateInfo)
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        appUpdateManager.unregisterListener(installStateUpdatedListener)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            appUpdateManager = AppUpdateManagerFactory.create(this)
            appUpdateManager.registerListener(updateListener)
            checkForUpdate()
        } catch (_: Exception) {

        }


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
                                viewModel = homeViewModel,
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
                                viewModel = homeViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                }
                            )
                        },
                        userScreenContent = {
                            UserScreen(
                                viewModel = mainViewModel,
                                onBackPress = {
                                    navigationState.navHostController.popBackStack()
                                }, onLogoutClick = {
                                    startActivity(
                                        Intent(
                                            this@MainActivity,
                                            AuthActivity::class.java
                                        )
                                    )
                                    this@MainActivity.finish()
                                }
                            )
                        },
                        streamItemContent = { id ->
                            StreamDetailedScreen(
                                id,
                                onBackPress = {
                                    if (it) mainViewModel.updateStreams()
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

        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@addOnCompleteListener
            }
            val token = task.result
            if (token != pref.lastFcmToken)
                mainViewModel.setDeviceToken(SetDeviceTokenRequest(device_id = token))

        }

    }


    override fun onResume() {
        super.onResume()

        appUpdateManager
            .appUpdateInfo
            .addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability()
                    == UpdateAvailability.DEVELOPER_TRIGGERED_UPDATE_IN_PROGRESS
                ) {
                    // If an in-app update is already running, resume the update.
                    appUpdateManager.startUpdateFlowForResult(
                        appUpdateInfo,
                        AppUpdateType.IMMEDIATE,
                        this,
                        APP_UPDATE_REQUEST_CODE
                    )
                }
            }
    }


}
