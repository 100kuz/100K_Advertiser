package uz.yuzka.a100kadmin.ui.activity

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import uz.yuzka.a100kadmin.ui.navigation.AuthNavigation
import uz.yuzka.a100kadmin.ui.navigation.Screen
import uz.yuzka.a100kadmin.ui.navigation.rememberNavigationState
import uz.yuzka.a100kadmin.ui.screen.LoginScreen
import uz.yuzka.a100kadmin.ui.screen.VerifyScreen
import uz.yuzka.a100kadmin.ui.viewModel.auth.AuthViewModel
import uz.yuzka.a100kadmin.ui.viewModel.auth.AuthViewModelImpl
import uz.yuzka.seller.data.request.LoginRequest
import uz.yuzka.seller.data.request.PasswordRequest

@AndroidEntryPoint
class AuthActivity : ComponentActivity() {

    private val viewModel: AuthViewModel by viewModels<AuthViewModelImpl>()

    private var phone: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        lifecycleScope.launch { viewModel.getIntroStart() }

        viewModel.introStartFlow.onEach {
            if (it) {
                startActivity(Intent(this@AuthActivity, MainActivity::class.java))
                this@AuthActivity.finish()
            }
        }.launchIn(lifecycleScope)

        viewModel.errorFlow.onEach {
            if (it != null) Toast.makeText(this, "$it", Toast.LENGTH_SHORT).show()
        }.launchIn(lifecycleScope)


        setContent {

            val navigationState = rememberNavigationState()

            val getPassword: (String) -> Unit = { phone ->
                viewModel.getPassword(PasswordRequest(phone))
            }

            val successFlow by viewModel.successFlow.collectAsState(initial = false)

            LaunchedEffect(key1 = successFlow) {
                if (successFlow) {
                    navigationState.navigateTo(Screen.VerifyScreen.getRouteWithArgs(phone))
                    viewModel.gotSuccess()
                }
            }

            Box(modifier = Modifier.fillMaxSize()) {
                AuthNavigation(modifier = Modifier.fillMaxSize(),
                    navController = navigationState.navHostController,
                    authContent = {
                        LoginScreen(
                            onVerifyClick = {
                                phone = it
                                getPassword.invoke("+${it}")
                            }
                        )
                    },
                    verifyContent = { phone ->
                        VerifyScreen(
                            viewModel,
                            onBackPress = {
                                navigationState.navHostController.popBackStack()
                            },
                            phone = phone,
                            onLoginClick = { code ->
                                viewModel.login(LoginRequest("+${phone}", code))
                            }
                        )
                    }
                )
            }

        }
    }
}