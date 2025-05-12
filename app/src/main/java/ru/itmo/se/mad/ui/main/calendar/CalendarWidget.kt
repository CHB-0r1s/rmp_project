package ru.itmo.se.mad.ui.main.calendar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.min
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.*
import java.util.Calendar
import kotlin.math.ceil

@Composable
fun CalendarScreen() { //TODO: take info about activity from server


    Column(
        modifier = Modifier
            .fillMaxSize()

    ) {
        Column(Modifier.padding(start = 16.dp, top = 16.dp, end = 16.dp)) {
            Text(
                text = "Календарь",
                fontSize = 24.sp,
                fontWeight = FontWeight.Medium
            )
            HorizontalDivider(
                color = WidgetGray10,
                thickness = 1.dp,
                modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
            )
        }
        val calendarT = Calendar.getInstance()
        val currentMonth = calendarT.get(Calendar.MONTH)
        Box(
            modifier = Modifier.weight(1f)
        ) {
            Column(
                modifier = Modifier.height(600.dp)
                    .padding(horizontal = 16.dp)
                    .verticalScroll(rememberScrollState(440 * calendarT.get(Calendar.MONTH)))
            ) {
                calendarT.set(Calendar.MONTH, (currentMonth+12-3)%12)
                CalendarGrid(calendarT)
                calendarT.set(Calendar.MONTH, (currentMonth+12-2)%12)
                CalendarGrid(calendarT)
                calendarT.set(Calendar.MONTH, (currentMonth+12-1)%12)
                CalendarGrid(calendarT)
                calendarT.set(Calendar.MONTH, currentMonth)
                CalendarGrid(calendarT)
                calendarT.set(Calendar.MONTH, (currentMonth+1)%12)
                CalendarGrid(calendarT)

            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(
                        Brush.verticalGradient(
                            0f to Color.Transparent,
                            1f to White,
                            endY=120f
                        )
                    )
            )
            Column(
                modifier = Modifier
                    .fillMaxWidth()

            ) {
                Spacer(modifier = Modifier.weight(1f)) // Гибкое пространство сверху

                SummaryInfo(
                )
            }
        }


    }

}

@Composable
fun CalendarGrid( month: Calendar) {
    val monthNames = arrayOf(
        "Январь", "Февраль", "Март", "Апрель", "Май", "Июнь",
        "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"
    )

    val days: List<DayData> = generateDaysForMonth(month.getActualMaximum(Calendar.DAY_OF_MONTH), month)
    val monthId: Int = month.get(Calendar.MONTH)
    val rows = ceil(days.size / 7f).toInt()
    month.set(Calendar.DAY_OF_MONTH, 1)
    Text(monthNames[monthId] + " " + month.get(Calendar.YEAR).toString(),
        fontSize = 24.sp,
        fontWeight = FontWeight.Medium,
        color = Black,
        fontFamily = SFProDisplay,
        modifier = Modifier.padding(top = 5.dp, start = min(55.dp*((month.get(Calendar.DAY_OF_WEEK)+6)%8), 55.dp*4))
    )
    LazyVerticalGrid(
        userScrollEnabled = false,
        columns = GridCells.Fixed(7),
        modifier = Modifier
            .fillMaxWidth()
            .height((48.dp * rows).coerceAtLeast(48.dp)) // Фиксированная высота для сетки в зависимости от количества строк
    ) {
        items(days) { day ->
            CalendarDay(day)
        }
    }
}

@Composable
fun CalendarDay(day: DayData) {
    val progressColor = when (day.activityLevel) {
        ActivityLevel.HIGH -> CalorieGreen
        ActivityLevel.NONE -> Color.Transparent
        ActivityLevel.ALERT -> CalorieOverflow  // Оранжевый
    }
    val backgroundColor = when (day.today){
        true -> CalorieGreen15
        false -> Color.Transparent
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(4.dp)
            .size(40.dp)
            .clip(RoundedCornerShape(8.dp)) // Закругленные углы квадрата
            .background(backgroundColor) // Фон квадрата
    ) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(vertical = 4.dp)
            .size(40.dp)
    ) {
        // Прогресс-бар сверху
        if (day.dayNumber != 0) {
            LinearProgressIndicator(
                progress = {day.activity},
                color = progressColor,
                drawStopIndicator = {},
                gapSize = 0.dp,
                strokeCap = StrokeCap.Square,
                trackColor = CalorieGreen15,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(15.dp)
                    .clip(RoundedCornerShape(50))
            )
            Text(
                text = day.dayNumber.toString(),
                fontSize = 18.sp,
                fontFamily = SFProDisplay,
                fontWeight = FontWeight.Normal
            )
        }
    }}
}

@Composable
fun SummaryInfo() {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),

        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = WidgetGrayF2F2F2// Светло-серый цвет
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 24.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            SummaryItem(title = "Активность", value = "13 из 30", icon = Icons.Default.CheckCircle)
            SummaryItem(title = "Всего дней", value = "245", icon = Icons.Default.CheckCircle)
            SummaryItem(title = "Вес", value = "-2.5 кг", icon = Icons.Default.Person) //TODO: weight icon
        }
    }
}

@Composable
fun SummaryItem(
    icon: ImageVector,
    title: String,
    value: String,
    iconTint: Color = Color.Black
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(horizontal = 8.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = iconTint,
            modifier = Modifier.size(30.dp)
        )
        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = title,
            fontSize = 14.sp,
            color = WidgetGray70
        )
        Spacer(modifier = Modifier.height(3.dp))

        Text(
            text = value,
            fontSize = 16.sp,
            color = Black
        )
    }
}


enum class ActivityLevel {
    HIGH, NONE, ALERT
}

data class DayData(
    val dayNumber: Int,
    val activityLevel: ActivityLevel,
    val activity: Float,
    val today: Boolean

)

fun generateDaysForMonth(daysInMonth: Int, calendar: Calendar = Calendar.getInstance()): List<DayData> {
    val days = mutableListOf<DayData>()
    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val blankDaysAtStart = if (calendar.get(Calendar.DAY_OF_WEEK) == 1) 6 else calendar.get(Calendar.DAY_OF_WEEK)-2
    val calendarToday: Calendar = Calendar.getInstance()

    for( i in 1..blankDaysAtStart) {
        days.add(DayData(dayNumber = 0, activityLevel = ActivityLevel.NONE, 0f, false))
    }

    for (i in 1..daysInMonth) {
        val activity = when {
            i == 8 -> ActivityLevel.ALERT
            else -> ActivityLevel.HIGH
        }
        calendar.set(Calendar.DAY_OF_MONTH, i)
        if(calendarToday.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) && calendarToday.get(Calendar.MONTH) == calendar.get(Calendar.MONTH))
            days.add(DayData(dayNumber = i, activityLevel = activity, if(activity == ActivityLevel.ALERT) 1.0f else Math.random().toFloat(), today = true))
        else if (calendarToday.after(calendar))
            days.add(DayData(dayNumber = i, activityLevel = activity, Math.random().toFloat(), today = false))
        else
            days.add(DayData(dayNumber = i, activityLevel = activity, 0f, today = false))
    }
    return days
}