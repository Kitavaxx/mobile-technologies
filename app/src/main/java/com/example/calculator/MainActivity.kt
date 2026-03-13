package com.example.calculator

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { padding ->
                    CalculatorMenu(
                        modifier = Modifier.padding(padding),

                        { println("Basic clicked") },
                        { println("Basic clicked") },
                        { println("Basic clicked") },
                        { println("Basic clicked") }
                    )
                }
            )
        }
    }
}

@Composable
fun CalculatorMenu(
    modifier: Modifier = Modifier,
    
    onBasicClick: () -> Unit,
    onAdvancedClick: () -> Unit,
    onAboutClick: () -> Unit,
    onExitClick: () -> Unit
) {
    Column(
        modifier = modifier
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
            .fillMaxWidth()
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
