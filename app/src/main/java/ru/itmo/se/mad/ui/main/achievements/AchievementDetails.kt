package ru.itmo.se.mad.ui.main.achievements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.theme.BackgroundGray14
import ru.itmo.se.mad.ui.theme.BackgroundGray53
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10
import ru.itmo.se.mad.ui.theme.WidgetGray20
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGray60
import ru.itmo.se.mad.ui.theme.WidgetGray70
import ru.itmo.se.mad.ui.theme.WidgetGray80

@Preview
@Composable
fun AchievementDetails(rating: Int = 1, ) {
    Column(horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(24.dp))
            .background(Brush.verticalGradient(listOf(BackgroundGray53, BackgroundGray14)))
            .padding(top = 8.dp)
    ) {
        Box(
            modifier = Modifier
                .size(height = 6.dp, width = 40.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(WidgetGray10)
        )
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 10.dp)
                .width(400.dp)
        ) {
            Text("Награда", style = TextStyle(
//                fontFamily = SFProDisplay,
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                fontStyle = FontStyle.Normal,
                color = White
            )
            )
            Button(onClick = {}, contentPadding = PaddingValues(0.dp),
                colors = ButtonColors(WidgetGray5, Black, White, White),
                modifier = Modifier
                    .height(28.dp)
                    .fillMaxWidth(0.3f)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Назад", style = TextStyle(
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        color = White
                    )
                    )
                    Spacer(modifier = Modifier.size(5.dp))
                    Icon(Icons.Rounded.Close, "", tint = White, modifier = Modifier.size(18.dp))
                }
            }
        }
        HorizontalDivider(modifier = Modifier.fillMaxWidth(0.92f), color = WidgetGray10)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .height(500.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.achievement),
                contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .shadow(24.dp, CircleShape, spotColor = Black)
                    .padding(vertical = 20.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 10.dp)
            ) {
                Text("Идеальная неделя", style = TextStyle(
                    color = White,
//                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.Medium,
                    fontStyle = FontStyle.Normal,
                    fontSize = 32.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
            )
                Text("Записывайте приёмы пищи каждый день на протяжении недели", textAlign = TextAlign.Center, style = TextStyle(
                    color = White,
//                    fontFamily = SFProDisplay,
                    fontWeight = FontWeight.Normal,
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp))
            }
        }

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(WidgetGray10)
                .fillMaxWidth()
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Column {
                    Text("Редкость", style = TextStyle(
                        color = WidgetGray70,
//                    fontFamily = SFProDisplay,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp), modifier = Modifier.padding(bottom = 4.dp))
                    Text("Обычная", style = TextStyle(
                        color = White,
//                    fontFamily = SFProDisplay,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp))
                }
                Row (
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    for (i in 1..5) {
                        Box(
                            modifier = Modifier
                                .width(8.dp)
                                .height(36.dp)
                                .clip(RoundedCornerShape(16.dp))
                                .background(if (i <= rating) WidgetGray80 else WidgetGray20)
                        )
                    }
                }
            }
            HorizontalDivider(modifier = Modifier.fillMaxWidth(), color = WidgetGray10)
            Row (
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.bshvevgn),
                    contentDescription = null,
                    modifier = Modifier
                        .size(50.dp)
                        .clip(CircleShape)
                )
                Column (
                    verticalArrangement = Arrangement.SpaceAround,
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                ) {
                    Text("Евгений Башаримов", style = TextStyle(
                        color = White,
//                    fontFamily = SFProDisplay,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp))
                    Text("Получено 25.03.2025", style = TextStyle(
                        color = WidgetGray60,
//                    fontFamily = SFProDisplay,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 16.sp))
                }
            }
        }
    }
}