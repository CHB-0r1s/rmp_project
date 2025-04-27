package ru.itmo.se.mad.ui.main.products.stepsActivity

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.outlined.Favorite
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Size
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
    
    // Function to refresh steps data from API
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
    
    // Load steps from API
    LaunchedEffect(Unit) {
        refreshStepsData()
    }

    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = White),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.dp)
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
                    "Активность",style = TextStyle(
                        fontFamily = SFProDisplay,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        fontStyle = FontStyle.Normal
                    )
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Outlined.Favorite,
                        contentDescription = "Favorite",
                        tint = Red,
                        modifier = Modifier.size(20.dp)
                    )

                    IconButton(onClick = { menuExpanded = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "More options",
                            tint = Gray,
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

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            fontFamily = SFProDisplay,
                            fontSize = 32.sp,
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
                            fontWeight = FontWeight.Normal,
                            color = Gray
                        )
                    ) {
                        append(" /$currentDailyGoal шагов")
                    }
                }
            )

            Spacer(modifier = Modifier.height(8.dp))

            Canvas(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(8.dp)
            ) {
                val width = size.width
                val height = size.height
                val cornerRadius = CornerRadius(height / 2, height / 2)

                drawRoundRect(
                    color = Beige,
                    size = Size(width, height),
                    cornerRadius = cornerRadius
                )

                val progress = (currentSteps / currentDailyGoal.toFloat()).coerceIn(0f, 1f)
                if (progress > 0f) {
                    drawRoundRect(
                        color = Orange,
                        size = Size(width * progress, height),
                        cornerRadius = cornerRadius
                    )
                }
            }
        }
    }
    
    // Daily goal dialog
    if (showGoalDialog) {
        AlertDialog(
            onDismissRequest = { showGoalDialog = false },
            title = { Text("Set Daily Goal") },
            text = {
                OutlinedTextField(
                    value = goalInputValue,
                    onValueChange = { goalInputValue = it.filter { char -> char.isDigit() } },
                    label = { Text("Steps") },
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
                                Log.e("StepsActivityWidget", "Error setting goal", e)
                            }
                        }
                        showGoalDialog = false
                    }
                ) {
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showGoalDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
    
    // Steps dialog
    if (showStepsDialog) {
        AlertDialog(
            onDismissRequest = { showStepsDialog = false },
            title = { Text("Set Steps") },
            text = {
                OutlinedTextField(
                    value = stepsInputValue,
                    onValueChange = { stepsInputValue = it.filter { char -> char.isDigit() } },
                    label = { Text("Steps") },
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
                    Text("Save")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { showStepsDialog = false }
                ) {
                    Text("Cancel")
                }
            }
        )
    }
}
