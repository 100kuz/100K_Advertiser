package uz.yuzka.a100kadmin.ui.screen

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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
import coil.compose.AsyncImage
import uz.yuzka.a100kadmin.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllStreamsContent(onStreamClick: (Int) -> Unit) {

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
            ), modifier = Modifier.shadow(2.dp)
        )
    }) { pad ->

        Box(
            modifier = Modifier
                .padding(pad)
                .background(Color(0xFFF0F0F0))
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(1.dp),
                contentPadding = PaddingValues(bottom = 20.dp)
            ) {
                items(20) {
                    ItemStream(onStreamClick)
                }
            }
        }
    }
}


@Composable
fun ItemStream(onStreamClick: (Int) -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(interactionSource = remember {
                MutableInteractionSource()
            }, indication = rememberRipple()) {
                onStreamClick(0)
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
            AsyncImage(
                model = "2332", contentDescription = null, modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(
                        width = 1.dp,
                        color = Color(0xFFE9EBEA),
                        shape = RoundedCornerShape(50)
                    )
            )

            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "Ikkinchi havolam",
                    style = TextStyle(
                        fontSize = 17.sp,
                        lineHeight = 20.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )

                Text(
                    text = "Vitek issiq va sovuq havo haydovc...",
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
                text = "34,094",
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