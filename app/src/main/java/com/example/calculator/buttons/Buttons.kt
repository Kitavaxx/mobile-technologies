package com.example.calculator.buttons

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdvancedCalculatorButton(label: String, onClick: (String) -> Unit = {}) {
    val config = LocalConfiguration.current
    val isLandscape = (config.orientation == Configuration.ORIENTATION_LANDSCAPE)

    if(!isLandscape) AdvancedCalculatorButtonUI(label, onClick)
    else AdvancedCalculatorButtonLandscapeUI(label, onClick)
}

@Composable
fun CalculatorButton(label: String, onClick: (String) -> Unit = {}) {
    val config = LocalConfiguration.current
    val isLandscape = (config.orientation == Configuration.ORIENTATION_LANDSCAPE)

    if(!isLandscape) CalculatorButtonUI(label, onClick)
    else CalculatorButtonLandscapeUI(label, onClick)
}

@Composable
fun AdvancedCalculatorButtonUI(label: String, onClick: (String) -> Unit = {}) {
    OutlinedButton(
        onClick = { onClick(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(70.dp)
    ){
        Text(
            text=  label,
            fontSize = 28.sp,
        )
    }
}

@Composable
fun AdvancedCalculatorButtonLandscapeUI(label: String, onClick: (String) -> Unit = {}) {
    OutlinedButton(
        onClick = { onClick(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(45.dp)
    ){
        Text(
            text=  label,
            fontSize = 28.sp,
        )
    }
}
@Composable
fun CalculatorButtonUI(label: String, onClick: (String) -> Unit = {}) {
    OutlinedButton(
        onClick = { onClick(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(100.dp)
            .size(70.dp)
    ){
        Text(
            text=  label,
            fontSize = 28.sp
        )
    }
}

@Composable
fun CalculatorButtonLandscapeUI(label: String, onClick: (String) -> Unit = {}) {
    OutlinedButton(
        onClick = { onClick(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(50.dp)
    ){
        Text(
            text=  label,
            fontSize = 28.sp
        )
    }
}

@Composable
fun MenuButton(text: String = "", onClick: () -> Unit = {}){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(50.dp)
    ){
        Text(
            text = text,
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
        )
    }
}