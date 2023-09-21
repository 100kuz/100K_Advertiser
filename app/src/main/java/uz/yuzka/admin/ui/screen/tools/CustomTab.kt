package uz.yuzka.admin.ui.screen.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.admin.R

@Composable
fun CustomTab(
    selected: Boolean,
    label: String,
    onClick: () -> Unit,
    selectedContentColor: Color = Color(0xFF51AEE7),
    unselectedContentColor: Color = Color(0xFF9B9B9B),
) {
    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .width(IntrinsicSize.Min)
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple()) {
                onClick()
            }
    ) {
        Text(
            text = label,
            style = TextStyle(
                fontSize = 14.sp,
                fontFamily = FontFamily(Font(R.font.roboto_medium)),
                fontWeight = FontWeight(500),
                color = if (selected) selectedContentColor else unselectedContentColor,
                textAlign = TextAlign.Center,
            ),
            maxLines = 2,
            minLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(top = 7.dp, start = 5.dp, end = 5.dp)
        )

        if (selected) Spacer(
            modifier = Modifier
                .height(4.dp)
                .fillMaxWidth()
                .background(
                    color = Color(0xFF51AEE7),
                    shape = RoundedCornerShape(size = 6.67345.dp)
                )
        )

    }
}