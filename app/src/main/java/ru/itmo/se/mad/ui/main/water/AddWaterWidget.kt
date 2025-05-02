package ru.itmo.se.mad.ui.main.water

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.itmo.se.mad.ui.theme.WaterTrackerTheme
import androidx.compose.foundation.clickable
import ru.itmo.se.mad.R

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WaterTrackerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    var isExpanded by remember { mutableStateOf(false) }
    var totalWater by remember { mutableStateOf(0.5f) }
    val maxWater = 2.25f

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        val alphaExpanded by animateFloatAsState(
            targetValue = if (isExpanded) 1f else 0f,
            label = "expandedAlpha"
        )
        val alphaCollapsed by animateFloatAsState(
            targetValue = if (isExpanded) 0f else 1f,
            label = "collapsedAlpha"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alphaExpanded)
        ) {
            WaterCard(
                totalWater = totalWater,
                onTotalWaterChange = { newAmount -> totalWater = newAmount },
                maxWater = maxWater,
                onExpandCollapseClick = { isExpanded = false }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(alphaCollapsed)
        ) {
            WaterSlider(
                totalWater = totalWater,
                onWaterAmountChange = { newAmount -> totalWater = newAmount },
                maxWater = maxWater,
                onExpandCollapseClick = { isExpanded = true }


            )
        }
    }
}

@Composable
fun ExpandCollapseButton(isExpanded: Boolean, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .size(32.dp)
            .clip(CircleShape)
            .background(Color.White)
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = painterResource(
                id = if (isExpanded) R.drawable.ic_collapse else R.drawable.ic_expand

            ),
            contentDescription = if (isExpanded) "Collapse" else "Expand",
            tint = Color.Black,
            modifier = Modifier.size(18.dp)
        )
    }
}

// ------WaterCard ------
@Composable
fun WaterCard(
    totalWater: Float,
    onTotalWaterChange: (Float) -> Unit,
    maxWater: Float,
    onExpandCollapseClick: () -> Unit
) {


    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(color = Color(0xFF00AFFF), shape = RoundedCornerShape(20.dp))
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "%.2f".format(totalWater),
                    fontSize = 28.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                )
                Text("/${maxWater} л", fontSize = 28.sp, color = Color.White)
                Text("В том числе\n из еды: 0.3 л", fontSize = 14.sp, color = Color.White)
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                WaterItem(
                    label = "Стакан",
                    volume = "0.25 л",
                    onClick = {
                        if (totalWater + 0.25f <= maxWater) {
                            onTotalWaterChange(totalWater + 0.25f)
                        }
                    }
                )
                WaterItem(
                    label = "Бутылка",
                    volume = "0.35 л",
                    onClick = {
                        if (totalWater + 0.35f <= maxWater) {
                            onTotalWaterChange(totalWater + 0.35f)
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Divider(color = Color.White.copy(alpha = 0.3f), thickness = 1.dp)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { /* Add custom volume */ },
                horizontalArrangement = Arrangement.Start
            ) {
                // Text("+", fontSize = 24.sp, color = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text("+ Другой объём", fontSize = 16.sp, color = Color.White)
            }
        }

        ExpandCollapseButton(
            isExpanded = true,
            onClick = onExpandCollapseClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 12.dp, y = (-12).dp)
        )
    }
}

@Composable
fun WaterItem(label: String, volume: String, onClick: () -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clickable(onClick = onClick)
                .background(Color(0x66000000), shape = RoundedCornerShape(20.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(id = R.drawable.baseline_add_circle_24),
                contentDescription = "Add",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        Text(label, color = Color.White)
        Text(volume, color = Color.White.copy(alpha = 0.8f), fontSize = 12.sp)
    }
}

// ------  WaterSlider ------
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WaterSlider(
    totalWater: Float,
    onWaterAmountChange: (Float) -> Unit,
    maxWater: Float,
    onExpandCollapseClick: () -> Unit
) {

    var dragging by remember { mutableStateOf(false) }
    var dragOffset by remember { mutableStateOf(0f) }
    var maxReachedOffset by remember { mutableStateOf(0f)}

    BoxWithConstraints(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 24.dp),
        contentAlignment = Alignment.Center
    ) {
        val componentWidth = maxWidth
        val componentWidthPx = with(LocalDensity.current) { componentWidth.toPx() }

        val addedWater = remember { derivedStateOf {
            (maxReachedOffset  / componentWidthPx * 1f).coerceIn(0f, 1f)
        }}

        val fillWidth by animateDpAsState(
            targetValue = with(LocalDensity.current) {
                (maxReachedOffset  / componentWidthPx * componentWidth.value).dp
            },
            label = "fillWidth"
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF00A6FF))
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth(fillWidth / componentWidth)
                    .height(80.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color(0xFF66CCFF))
            )

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(24.dp))
                    .pointerInput(Unit) {
                        detectHorizontalDragGestures(
                            onDragStart = {
                                dragging = true
                                dragOffset = 0f
                                maxReachedOffset = 0f
                            },
                            onHorizontalDrag = { _, dragAmount ->
                                if (dragAmount > 0) {
                                    dragOffset = (dragOffset + dragAmount)
                                        .coerceAtMost(componentWidthPx)
                                    maxReachedOffset = maxOf(maxReachedOffset, dragOffset)
                                }

                            },
                            onDragEnd = {
                                val newWater = (totalWater + addedWater.value)
                                    .coerceAtMost(maxWater)
                                if (newWater > totalWater) {
                                    onWaterAmountChange(newWater)
                                }
                                dragOffset = 0f
                                maxReachedOffset = 0f
                                dragging = false
                            }
                        )
                    }
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        if (dragging) {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_water),
                                contentDescription = null,
                                tint = Color.White,
                                modifier = Modifier.size(32.dp)
                            )
                        } else {
                            Text(
                                text = "Проведите,\nчтобы добавить",
                                fontSize = 16.sp,
                                color = Color.White
                            )
                        }
                    }

                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterEnd
                    ) {
                        if (dragging) {
                            Column(
                                horizontalAlignment = Alignment.End,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = "+%.2f л".format(addedWater.value),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    textAlign = TextAlign.End
                                )
                                Text(
                                    text = "Отпустите, чтобы добавить",
                                    fontSize = 14.sp,
                                    color = Color.White,
                                    modifier = Modifier.fillMaxWidth(),
                                    lineHeight = 16.sp,
                                    maxLines = 2,
                                    textAlign = TextAlign.End
                                )
                            }
                        } else {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(
                                    painter = painterResource(id = R.drawable.baseline_arrow_forward_ios_24),
                                    contentDescription = null,
                                    tint = Color.White,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(4.dp))
                                Text(
                                    text = "%.1f/%.2f л".format(totalWater, maxWater),
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
        }

        ExpandCollapseButton(
            isExpanded = false,
            onClick = onExpandCollapseClick,
            modifier = Modifier
                .align(Alignment.TopEnd)
                .offset(x = 12.dp, y = (-12).dp)
        )
    }
}
