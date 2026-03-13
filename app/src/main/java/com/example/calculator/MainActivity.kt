package com.example.calculator

import android.os.Bundle
import android.view.Menu
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.calculator.ui.theme.MycalculatorTheme


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MycalculatorTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun CalculatorMenu(
    onBasicClick: () -> Unit,
    onAdvancedClick: () -> Unit,
    onAboutClick: () -> Unit,
    onExitClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Calculator Menu",
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 40.dp)
        )

        MenuButton("Basic", onBasicClick)
        MenuButton("Advanced", onAdvancedClick)
        MenuButton("About", onAboutClick)
        MenuButton("Exit", onExitClick)
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .height(60.dp)
    ){
        Text(
            text = text,
            fontSize = 20.sp
        )
    }
}

@Composable
fun CalculatorUI() {

}

@Composable
fun CalculatorButton(label: String, onClick: (String) -> Unit) {

}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MycalculatorTheme {
        Greeting("Android")
    }
}