package ru.itmo.se.mad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.itmo.se.mad.ui.main.measure.MeasureWidget
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.main.products.FoodTimeChoiceWidget
import ru.itmo.se.mad.ui.theme.MyApplicationTheme
import ru.itmo.se.mad.ui.main.calories.CalorieWidgetView
import ru.itmo.se.mad.ui.main.main_screen.BottomNavBar
import ru.itmo.se.mad.ui.main.main_screen.DateItem
import ru.itmo.se.mad.ui.main.stepsActivity.StepsActivityWidget
import ru.itmo.se.mad.ui.main.water.MainScreen
import ru.itmo.se.mad.ui.main.calendar.CalendarScreen
import ru.itmo.se.mad.ui.main.water.WaterSlider
import ru.itmo.se.mad.ui.theme.AddWaterWidget

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
        setContent {
            Main()
        }
    }
}


@Composable
fun Main() {

//    val navController = rememberNavController()
//    Column(Modifier.padding(top = 10.dp).verticalScroll(rememberScrollState())) {
//        NavHost(navController, startDestination = NavRoutes.AddItem.route) {
//            composable(NavRoutes.AddItem.route) { AddItem(navController) }
//            composable(NavRoutes.FoodTimeChoiceWidget.route) { FoodTimeChoiceWidget()  }
//            composable(NavRoutes.MeasureWidget.route) { MeasureWidget()  }
//        }
//        CalorieWidgetView()
//    }
      val navController = rememberNavController()
     var isExpanded by remember { mutableStateOf(false) }
     var totalWater by remember { mutableStateOf(0.5f) }
     val maxWater = 2.25f
     CalendarScreen()

         Scaffold(
             bottomBar = {
                 BottomNavBar(navController)
             }
         ) { padding ->
             NavHost(
                 navController,
                 startDestination = "home",
                 modifier = Modifier.padding(padding)
             ) {
                 composable("home") {
                     Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                         DateItem(onCalendarClick = {
                             // TODO: когда перехожу в календарь навбар сейчас остается, и по нему можно вернуться, вроде ощущается даже нормально, я бы так и оставила
                             navController.navigate(NavRoutes.CalendarWidget.route)
                         })
                         CalorieWidgetView()
                         WaterSlider(
                             totalWater = totalWater,
                             onWaterAmountChange = { newAmount -> totalWater = newAmount },
                             maxWater = maxWater,
                             onExpandCollapseClick = { isExpanded = true },
                             expandable = false
                         )
                         StepsActivityWidget()

                     }
                 }
                composable(NavRoutes.AddItem.route) {
                     AddItem(navController)
                 }
                 composable(NavRoutes.FoodTimeChoiceWidget.route) {
                     FoodTimeChoiceWidget()
                 }
                 composable("measure") {
                     MeasureWidget()
                 }
                 composable(NavRoutes.CalendarWidget.route){
                     CalendarScreen()
                 }

                 composable(NavRoutes.AddWaterWidget.route) { AddWaterWidget(
                     darkTheme = false,
                     content = {
                         MainScreen()
                     }
                 ) }
            }
        }
}

sealed class NavRoutes(val route: String) {
    data object FoodTimeChoiceWidget : NavRoutes("FoodTimeChoiceWidget")
    data object AddItem : NavRoutes("AddItem")
    data object AddWaterWidget : NavRoutes("AddWaterWidget")
    data object MeasureWidget : NavRoutes("MeasureWidget")
    data object CalendarWidget : NavRoutes("CalendarWidget")
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Sean Combs")
    }
}