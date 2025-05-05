package ru.itmo.se.mad.ui.main.achievements

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.sharp.KeyboardArrowRight
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import androidx.navigation.compose.rememberNavController
import ru.itmo.se.mad.NavRoutes
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.theme.BackgroundGray14
import ru.itmo.se.mad.ui.theme.BackgroundGray53
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGray60
import ru.itmo.se.mad.ui.theme.WidgetGray70

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun AchievementScreen() {
    var showBottomSheet by remember { mutableStateOf(false) }
    val navController = rememberNavController()
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = false,
    )
    ModalBottomSheet(
        modifier = Modifier
            .fillMaxHeight()
            .clip(shape = RoundedCornerShape(24.dp))
            .padding(top = 8.dp),
        sheetState = sheetState,
        onDismissRequest = { showBottomSheet = false }
    ) {
        Column (
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(listOf(BackgroundGray53, BackgroundGray14)))
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .width(400.dp)
            ) {
                Text(
                    "Ваши награды", style = TextStyle(
//                fontFamily = SFProDisplay,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                        color = White
                    )
                )
                Button(
                    onClick = {}, contentPadding = PaddingValues(0.dp),
                    colors = ButtonColors(WidgetGray5, Black, White, White),
                    modifier = Modifier
                        .height(28.dp)
                        .fillMaxWidth(0.5f)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Закрыть", style = TextStyle(
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
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .width(388.dp)
            ) {
                Text(
                    "Получено", style = TextStyle(
//                fontFamily = SFProDisplay,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Medium,
                        fontStyle = FontStyle.Normal,
                        color = White
                    )
                )
                Row(
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .clickable { },
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        "Смотреть все", style = TextStyle(
                            color = WidgetGray60,
//                    fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontSize = 14.sp
                        )
                    )
                    Icon(Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                        "",
                        tint = WidgetGray60,
                        modifier = Modifier
                            .size(28.dp)
                            .clickable {})
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(WidgetGray10)
                    .fillMaxWidth()
                    .clickable(
                        onClick = {
                            navController.navigate(NavRoutes.AchievementDetails.route)
                        }
                    )
            ) {
                Image(
                    painter = painterResource(id = R.drawable.achievement),
                    contentDescription = null,
                    modifier = Modifier
                        .size(130.dp)
                        .shadow(24.dp, CircleShape, spotColor = Black)
                        .padding(top = 20.dp)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier
                        .padding(vertical = 10.dp)
                        .fillMaxWidth(0.8f)
                        .height(130.dp)
                        .clickable(
                            onClick = {
                                navController.navigate(NavRoutes.AchievementDetails.route)
                            }
                        )
                ) {
                    Text(
                        "Идеальная неделя", style = TextStyle(
                            color = White,
//                    fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Medium,
                            fontStyle = FontStyle.Normal,
                            fontSize = 18.sp
                        )
                    )
                    Text(
                        "Записывайте приёмы пищи каждый день на протяжении недели",
                        textAlign = TextAlign.Center,
                        style = TextStyle(
                            color = White,
//                    fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontSize = 15.sp
                        )
                    )
                    Text(
                        "Получено 25.03.2025", style = TextStyle(
                            color = WidgetGray60,
//                    fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontSize = 12.sp
                        )
                    )
                }
            }
            Row(
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 8.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(WidgetGray10)
                    .height(110.dp)
                    .fillMaxWidth()
                    .clickable { }
            ) {
                Image(
                    painter = painterResource(id = R.drawable.achievement),
                    contentDescription = null,
                    modifier = Modifier
                        .size(70.dp)
                        .shadow(20.dp, CircleShape, spotColor = Black)
                )
                Column(
                    verticalArrangement = Arrangement.Top,
                    modifier = Modifier
                        .fillMaxWidth(0.5f)
                ) {
                    Text(
                        text = "Начало пути", style = TextStyle(
                            color = White,
//                    fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontSize = 18.sp
                        ), modifier = Modifier.padding(bottom = 5.dp)
                    )
                    Text(
                        "Получено 25.03.2025", style = TextStyle(
                            color = WidgetGray60,
//                    fontFamily = SFProDisplay,
                            fontWeight = FontWeight.Normal,
                            fontStyle = FontStyle.Normal,
                            fontSize = 12.sp
                        )
                    )
                }
                Icon(Icons.AutoMirrored.Sharp.KeyboardArrowRight,
                    "",
                    tint = WidgetGray70,
                    modifier = Modifier
                        .size(32.dp)
                        .clickable {})
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .padding(horizontal = 16.dp, vertical = 10.dp)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "Можно открыть", style = TextStyle(
                        color = White,
//                    fontFamily = SFProDisplay,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(top = 5.dp)
                )
                Text(
                    text = "12 наград", style = TextStyle(
                        color = WidgetGray60,
//                    fontFamily = SFProDisplay,
                        fontWeight = FontWeight.Normal,
                        fontStyle = FontStyle.Normal,
                        fontSize = 18.sp
                    ), modifier = Modifier.padding(top = 5.dp)
                )
            }
            val achievements = listOf(
                "Идеальный месяц",
                "Душа компании",
                "Кето-новичок",
                "Веган-мастер"
            )
            LazyVerticalGrid(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(16.dp)
            ) {
                items(achievements) { achievement ->
                    AchievementTile(navController, achievement, NavRoutes.AchievementDetails.route)
                }
            }
        }
    }
}
