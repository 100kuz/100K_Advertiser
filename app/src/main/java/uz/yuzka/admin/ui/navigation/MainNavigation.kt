package uz.yuzka.admin.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun MainNavigation(
    navController: NavHostController,
    homeContent: @Composable () -> Unit,
    transactionsContent: @Composable () -> Unit,
    streamsContent: @Composable () -> Unit,
    marketContent: @Composable () -> Unit,
    statisticsContent: @Composable () -> Unit,
    promoCodesContent: @Composable () -> Unit,
    salesContent: @Composable () -> Unit,
    balanceHistoryContent: @Composable () -> Unit,
    charityHistoryContent: @Composable () -> Unit,
    userScreenContent: @Composable () -> Unit,
    streamItemContent: @Composable (Int) -> Unit,
    createPromoCodeContent: @Composable () -> Unit,
    createStreamContent: @Composable (Int) -> Unit,
) {
    NavHost(navController = navController, Screen.HomeContent.route) {

        composable(Screen.HomeContent.route) {
            homeContent()
        }

        composable(Screen.TransactionsContent.route) {
            transactionsContent()
        }

        composable(Screen.StreamsContent.route) {
            streamsContent()
        }

        composable(Screen.MarketContent.route) {
            marketContent()
        }

        composable(Screen.StatisticsContent.route) {
            statisticsContent()
        }

        composable(Screen.PromoCodesContent.route) {
            promoCodesContent()
        }

        composable(Screen.SalesContent.route) {
            salesContent()
        }

        composable(Screen.BalanceHistoryContent.route) {
            balanceHistoryContent()
        }

        composable(Screen.CharityHistoryContent.route) {
            charityHistoryContent()
        }

        composable(Screen.UserContent.route) {
            userScreenContent()
        }

        composable(
            Screen.StreamDetailedContent.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            val id = it.arguments?.getInt("id")
            id?.let { streamItemContent(id) }
        }

        composable(Screen.CreatePromoCodeContent.route) {
            createPromoCodeContent()
        }

        composable(
            Screen.CreateStreamContent.route,
            arguments = listOf(navArgument("id") {
                type = NavType.IntType
            })
        ) {
            val id = it.arguments?.getInt("id")

            if (id != null) {
                createStreamContent(id)
            }
        }

    }
}

@Composable
fun AuthNavigation(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screen.AuthScreen.route,
    authContent: @Composable () -> Unit,
    verifyContent: @Composable (String) -> Unit,
    usernameLoginScreen: @Composable () -> Unit,
) {

    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {

        composable(Screen.AuthScreen.route) {
            authContent()
        }

        composable(Screen.UsernameLoginScreen.route) {
            usernameLoginScreen()
        }

        composable(Screen.VerifyScreen.route, arguments = listOf(navArgument("phone") {
            type = NavType.StringType
        })) {
            val phone = it.arguments?.getString("phone")
            phone?.let { verifyContent(phone) }
        }
    }

}

sealed class Screen(val route: String) {
    object HomeContent : Screen(HOME_ROUTE)
    object TransactionsContent : Screen(TRANSACTIONS_ROUTE)
    object StreamsContent : Screen(STREAMS_ROUTE)
    object MarketContent : Screen(MARKET_ROUTE)
    object StatisticsContent : Screen(STATISTICS_ROUTE)
    object PromoCodesContent : Screen(PROMO_CODES_ROUTE)
    object SalesContent : Screen(SALES_ROUTE)
    object BalanceHistoryContent : Screen(BALANCE_HISTORY_ROUTE)
    object CharityHistoryContent : Screen(CHARITY_HISTORY_ROUTE)
    object UserContent : Screen(USER_ROUTE)
    object StreamDetailedContent : Screen(STREAM_ROUTE) {
        fun getRouteWithArgs(id: Int): String {
            return "streams/$id"
        }
    }

    object CreatePromoCodeContent : Screen(CREATE_PROMO_CODE_ROUTE)
    object CreateStreamContent : Screen(CREATE_STREAM_ROUTE) {
        fun getRouteWithArgs(id: Int): String {
            return "create_stream/$id"
        }
    }

    object AuthScreen : Screen(AUTH_ROUTE)
    object UsernameLoginScreen : Screen(USERNAME_LOGIN_ROUTE)
    object VerifyScreen : Screen(VERIFY_ROUTE) {
        fun getRouteWithArgs(phone: String): String {
            return "verify/$phone"
        }
    }

    private companion object {
        const val HOME_ROUTE = "home"
        const val TRANSACTIONS_ROUTE = "transactions"
        const val STREAMS_ROUTE = "streams"
        const val MARKET_ROUTE = "market"
        const val STATISTICS_ROUTE = "statistics"
        const val PROMO_CODES_ROUTE = "promo_codes"
        const val SALES_ROUTE = "sales"
        const val BALANCE_HISTORY_ROUTE = "balance_history"
        const val CHARITY_HISTORY_ROUTE = "charity_history"
        const val USER_ROUTE = "user"
        const val STREAM_ROUTE = "streams/{id}"
        const val CREATE_PROMO_CODE_ROUTE = "create_promo_code"
        const val CREATE_STREAM_ROUTE = "create_stream/{id}"
        const val AUTH_ROUTE = "auth"
        const val USERNAME_LOGIN_ROUTE = "username_login"
        const val VERIFY_ROUTE = "verify/{phone}"
    }
}
