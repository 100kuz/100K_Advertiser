package uz.yuzka.admin.ui.screen.tools

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import uz.yuzka.admin.R
import uz.yuzka.admin.ui.theme.BackButton

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharityInfoLayout(onBackPress: () -> Unit) {

    Scaffold(modifier = Modifier.fillMaxSize(), topBar = {
        TopAppBar(
            title = {
                Text(
                    text = "Xayriya qutisi",
                    style = TextStyle(
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.roboto_medium)),
                        fontWeight = FontWeight(500),
                        color = Color(0xFF202020),
                    )
                )
            },
            navigationIcon = {
                BackButton {
                    onBackPress()
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Color.White
            ), modifier = Modifier.shadow(2.dp)
        )
    }) { pad ->

        Column(
            modifier = Modifier
                .padding(pad)
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(Color.White)
                .padding(vertical = 20.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            Text(
                text = "Assalomu aleykum, hurmatli jamoamiz adminlari.",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 23.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_medium)),
                    fontWeight = FontWeight(500),
                    color = Color(0xFF51AEE7),
                    textAlign = TextAlign.Center,
                    letterSpacing = 0.16.sp,
                ),
                modifier = Modifier
                    .background(
                        color = Color(0x1A51AEE7),
                        shape = RoundedCornerShape(size = 12.dp)
                    )
                    .padding(
                        start = 10.dp,
                        top = 10.dp,
                        end = 10.dp,
                        bottom = 10.dp
                    )
            )

            Text(
                text = "Ushbu sahifada siz yani 100k.uz sayti adminlari yordamida hayriya ishlari uchun toplangan mablaglarni kuzatishingiz mumkin. Sahifada hozircha umumiy toplangan mablagni va hayriya funksiyasi yoqilgan oqimlar royhatini korishingiz mumkin. \n\nYigilgan mablag bolalar yoki qariyalar uylari hisob raqamiga yoki ogir kasalga uchragan lekin davolanishga sharoiti yoq vatandoshlarimiz karta raqamlariga yonaltiriladi. \n\nMablagni topshirish vaqtida 100k.uz saytida ishlovchi istalgan admin ishtirok etishi mumkin. \n\nUshbu sahifa keyinchalik yangiliklar ko'rinishida barcha hayriyalar haqida malumotlar chop etiladi.",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 23.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(400),
                    color = Color(0xFF555555),
                    letterSpacing = 0.08.sp,
                ),
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Eslatma: Hayriya qutisiga mablag ajratish uchun Market bolimiga tashrif buyuring va oqim yaratish oynasida hayriya summasini kiriting va tizim aynan osha oqim har bir sotilgan buyurtmasidan kiritilgan hayriya summasi ushlab qoladi. \n\nHayriya dasturida ishtirok etmagan bo'lsangiz albatta siz ham ishtirok etib hissangizni qoshing, hattoki 500 so'm ham kopchilik yordami kimnidir hayotini saqlab qolishi yoki yahshilikka ozgartirishi mumkin. \n\nBirgalikda biz katta kuchmiz!",
                style = TextStyle(
                    fontSize = 16.sp,
                    lineHeight = 23.sp,
                    fontFamily = FontFamily(Font(R.font.roboto_regular)),
                    fontWeight = FontWeight(600),
                    color = Color(0xFF000000),
                    letterSpacing = 0.08.sp,
                )
            )

        }

    }
}