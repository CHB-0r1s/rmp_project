package ru.itmo.se.mad.ui.main.measure

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.Black
import ru.itmo.se.mad.ui.theme.SFProDisplay
import ru.itmo.se.mad.ui.theme.White
import ru.itmo.se.mad.ui.theme.WidgetGray10
import ru.itmo.se.mad.ui.theme.WidgetGray5
import ru.itmo.se.mad.ui.theme.WidgetGray80

//@Preview
@Composable
fun MeasureWidget() {
    Column(horizontalAlignment = Alignment.Start,
        modifier = Modifier
            .clip(shape = RoundedCornerShape(32.dp))
            .background(color = White)
            .width(450.dp)
            .padding(horizontal = 22.dp, vertical = 22.dp)
    ) {
        Box(
            modifier = Modifier
                .size(height = 7.dp, width = 30.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(WidgetGray10)
                .padding(5.dp)
        )
        Row (
            horizontalArrangement = Arrangement.spacedBy(72.dp),
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 0.dp, vertical = 16.dp)
                .fillMaxWidth()
        ) {
            Text("Что вы хотите добавить?", fontSize = 20.sp, fontFamily = SFProDisplay, fontWeight = FontWeight.SemiBold)
            Spacer(modifier = Modifier.weight(1f))
            Button(onClick = {}, shape = CircleShape, contentPadding = PaddingValues(0.dp), colors = ButtonColors(
                WidgetGray5, Black, White, White
            ), modifier = Modifier
                .size(32.dp)
            ) {
                Icon(Icons.Filled.Close, "", tint = WidgetGray80)
            }
        }
        val parameters = listOf(
            "Вес",
            "Талия",
            "Бёдра",
            "Грудь",
            "Руки",
            "Объём жира",
            "Мышечная масса",
            "Глюкоза в крови",
            "Артериальное давление"
        )

        LazyColumn {
            items(parameters) { param ->
                ParameterElement(param)
            }
        }
    }
}

@Composable
fun ParameterElement(parameterName: String = "Вес") {
    Row(
        horizontalArrangement = Arrangement.spacedBy(72.dp),
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .padding(horizontal = 0.dp, vertical = 16.dp)
            .fillMaxWidth()
    ){
        Text(
            text = parameterName,
            fontSize = 16.sp,
            color = Color.Black,
            fontFamily = SFProDisplay,
            fontWeight = FontWeight.SemiBold,
        )
        Spacer(modifier = Modifier.weight(1f))
        Button(onClick = {}, shape = CircleShape,
            contentPadding = PaddingValues(0.dp),

            colors = ButtonColors(
            WidgetGray5, Black, White, White
        ), modifier = Modifier
            .size(32.dp)
        ) {
            Icon(Icons.AutoMirrored.Filled.KeyboardArrowRight, "", tint = WidgetGray80)
        }
    }
}