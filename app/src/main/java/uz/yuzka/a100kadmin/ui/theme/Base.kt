package uz.yuzka.a100kadmin.ui.theme

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import uz.yuzka.a100kadmin.R

@Composable
fun BackButton(onClick: () -> Unit) {
    IconButton(onClick = onClick) {
        Icon(painter = painterResource(id = R.drawable.ic_back), contentDescription = null)
    }
}