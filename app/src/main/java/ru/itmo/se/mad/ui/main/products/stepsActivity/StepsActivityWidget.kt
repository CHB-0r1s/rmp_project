package ru.itmo.se.mad.ui.main.products.stepsActivity

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.main.products.stepsActivity.fit.FitApiService
import kotlinx.coroutines.launch
import ru.itmo.se.mad.ui.theme.*
import androidx.compose.ui.text.TextStyle
import ru.itmo.se.mad.R

@Composable
fun StepsActivityWidget(
    steps: Int = 0,
    dailyGoal: Int = 10000,
    modifier: Modifier = Modifier,
    fitApiService: FitApiService = FitApiService()
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showGoalDialog by remember { mutableStateOf(false) }
    var showStepsDialog by remember { mutableStateOf(false) }
    var goalInputValue by remember { mutableStateOf(dailyGoal.toString()) }
    var stepsInputValue by remember { mutableStateOf(steps.toString()) }
    var currentDailyGoal by remember { mutableStateOf(dailyGoal) }
    var currentSteps by remember { mutableStateOf(steps) }
    val scope = rememberCoroutineScope()
    
    suspend fun refreshStepsData() {
        scope.launch {
            try {
                val (apiSteps, apiGoal) = fitApiService.getUserStepsAndGoal()
                currentSteps = apiSteps
                currentDailyGoal = apiGoal
                goalInputValue = apiGoal.toString()
                stepsInputValue = apiSteps.toString()
            } catch (e: Exception) {
                Log.e("StepsActivityWidget", "Error refreshing data", e)
            }
        }
    }
    
    LaunchedEffect(Unit) {
        refreshStepsData()
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = WidgetGray5),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "Активность", style = TextStyle(
                        fontFamily = SFProDisplay,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal
                    )
                )
                Box {
                    IconButton(
                        onClick = { menuExpanded = true },
                        modifier = Modifier.size(24.dp)
                    ) {
                        Icon(
                            painter = painterResource(R.drawable.more_horiz_24px),
                            contentDescription = "More options",
                            tint = BackgroundGray4560,
                            modifier = Modifier.size(24.dp)
                        )
                    }
                    DropdownMenu(
                        expanded = menuExpanded,
                        onDismissRequest = { menuExpanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Установить цель шагов", style = TextStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal
                            ))},
                            onClick = {
                                menuExpanded = false
                                showGoalDialog = true
                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Установить количество шагов", style = TextStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                fontStyle = FontStyle.Normal
                            )) },
                            onClick = {
                                menuExpanded = false
                                showStepsDialog = true
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row (
                modifier = Modifier.fillMaxWidth().padding(0.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = buildAnnotatedString {
                        withStyle(
                            style = SpanStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 34.sp,
                                fontWeight = FontWeight.Bold,
                                color = Black
                            )
                        ) {
                            append(String.format("%,d", currentSteps))
                        }
                        withStyle(
                            style = SpanStyle(
                                fontFamily = SFProDisplay,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = BackgroundGray53
                            )
                        ) {
                            append(" /" + String.format("%,d", currentDailyGoal) + " шагов")
                        }
                    }
                )
                Icon(
                    painter = painterResource(R.drawable.image_google_fit),
                    contentDescription = "Favorite",
                    tint = Color.Unspecified,
                    modifier = Modifier.size(30.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            ) {
                val width = size.width
                val height = size.height
                val cornerRadius = CornerRadius(height / 3, height / 3)

                drawRoundRect(
                    color = ActivityOrange15,
                    size = Size(width, height),
                    cornerRadius = cornerRadius
                )

                val progress = (currentSteps / currentDailyGoal.toFloat()).coerceIn(0f, 1f)
                if (progress > 0f) {
                    drawRoundRect(
                        color = ActivityOrange85,
                        size = Size(width * progress, height),
                        cornerRadius = cornerRadius
                    )
                }
            }
        }
    }
    
    if (showGoalDialog) {
        AlertDialog(
            onDismissRequest = { showGoalDialog = false },
            title = { Text("Установить цель шагов") },
            text = {
                OutlinedTextField(
                    value = goalInputValue,
                    onValueChange = { goalInputValue = it.filter { char -> char.isDigit() } },
                    label = { Text("Количество шагов") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newGoal = goalInputValue.toIntOrNull() ?: currentDailyGoal
                        scope.launch {
                            try {
                                val success = fitApiService.setDailyGoal(newGoal)
                                if (success) {
                                    refreshStepsData()
                                }
                            } catch (e: Exception) {
                            }
                        }
                        showGoalDialog = false
                    }
                ) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showGoalDialog = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
    
    if (showStepsDialog) {
        AlertDialog(
            onDismissRequest = { showStepsDialog = false },
            title = { Text("Установить количество шагов") },
            text = {
                OutlinedTextField(
                    value = stepsInputValue,
                    onValueChange = { stepsInputValue = it.filter { char -> char.isDigit() } },
                    label = { Text("Количество шагов") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        val newSteps = stepsInputValue.toIntOrNull() ?: currentSteps
                        scope.launch {
                            try {
                                val success = fitApiService.setSteps(newSteps)
                                if (success) {
                                    refreshStepsData()
                                }
                            } catch (e: Exception) {
                                Log.e("StepsActivityWidget", "Error setting steps", e)
                            }
                        }
                        showStepsDialog = false
                    }
                ) {
                    Text("Сохранить")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showStepsDialog = false }
                ) {
                    Text("Отмена")
                }
            }
        )
    }
}
