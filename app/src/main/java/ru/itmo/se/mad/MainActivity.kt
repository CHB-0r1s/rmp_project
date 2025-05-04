package ru.itmo.se.mad

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ru.itmo.se.mad.ui.main.measure.MeasureWidget
import ru.itmo.se.mad.ui.main.products.AddItem
import ru.itmo.se.mad.ui.main.products.FoodTimeChoiceWidget
import ru.itmo.se.mad.ui.main.water.MainScreen
import ru.itmo.se.mad.ui.theme.AddWaterWidget
import ru.itmo.se.mad.ui.theme.MyApplicationTheme

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
    val navController = rememberNavController()
    Column(Modifier.padding(top = 10.dp)) {
        NavHost(navController, startDestination = NavRoutes.AddItem.route) {
            composable(NavRoutes.AddItem.route) { AddItem(navController) }
            composable(NavRoutes.FoodTimeChoiceWidget.route) { FoodTimeChoiceWidget()  }
            composable(NavRoutes.AddWaterWidget.route) { AddWaterWidget(
                darkTheme = false,
                content = {
                    MainScreen()
                }
            ) }
            composable(NavRoutes.MeasureWidget.route) { MeasureWidget()  }
        }
    }
}

sealed class NavRoutes(val route: String) {
    object FoodTimeChoiceWidget : NavRoutes("FoodTimeChoiceWidget")
    object AddItem : NavRoutes("AddItem")
    object AddWaterWidget : NavRoutes("AddWaterWidget")
    object MeasureWidget : NavRoutes("MeasureWidget")
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