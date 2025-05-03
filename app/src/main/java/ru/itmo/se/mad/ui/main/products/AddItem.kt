package ru.itmo.se.mad.ui.main.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.itmo.se.mad.NavRoutes
import ru.itmo.se.mad.R
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGray80

//@Preview
@Composable
fun AddItem(navController: NavController) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(32.dp))
            .background(color = White)
            .width(450.dp)
            .padding(12.dp)
    ) {
        Box(
            modifier = Modifier
                .size(height = 7.dp, width = 30.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(WidgetGray10)
                .padding(5.dp)
        )
        Row(
            horizontalArrangement = Arrangement.spacedBy(72.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 16.dp)
        ) {
            Text(
                "Что вы хотите добавить?",
                fontSize = 22.sp,
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.SemiBold
            )
            Button(
                onClick = {},
                shape = CircleShape,
                contentPadding = PaddingValues(0.dp),
                colors = ButtonColors(
                    WidgetGray5, Black, White, White
                ),
                modifier = Modifier
                    .size(32.dp)
            ) {
                Icon(Icons.Filled.Close, "", tint = WidgetGray80)
            }
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 5.dp)
        ) {
            AddItemElement(navController, "Приём пищи", R.drawable.image_utensils, NavRoutes.FoodTimeChoiceWidget.route)
            AddItemElement(navController, "Активность", R.drawable.image_activity)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(vertical = 5.dp)
        ) {
            AddItemElement(navController, "Измерение", R.drawable.image_ruler, NavRoutes.MeasureWidget.route)
            AddItemElement(navController, "Вода", R.drawable.image_water)
        }
        Row(
            horizontalArrangement = Arrangement.spacedBy(20.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 18.dp, vertical = 16.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.image_qr),
                contentDescription = null,
                modifier = Modifier
                    .width(30.dp)
                    .height(30.dp)
            )
            Text(
                "Сканировать код",
                fontFamily = SFProDisplay,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun AddItemElement(
    navController: NavController,
    thingType: String = "Приём пищи",
    imageName: Int = R.drawable.image_utensils,
    route: String = NavRoutes.FoodTimeChoiceWidget.route
) {
    Column(
        modifier = Modifier
            .width(190.dp)
            .height(150.dp)
            .clip(shape = RoundedCornerShape(16.dp))
            .background(WidgetGray5)
            .clickable(onClick = {
                navController.navigate(route)
            }),
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = imageName),
            contentDescription = null,
            modifier = Modifier
                .width(30.dp)
                .height(30.dp)
                .align(Alignment.CenterHorizontally)
        )
        Text(
            text = thingType,
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(top = 8.dp)
        )
    }
}