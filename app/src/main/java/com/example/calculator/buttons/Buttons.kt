package com.example.calculator.buttons

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AdvancedCalculatorButton(label: String, onClick: (String) -> Unit = {}) {
    OutlinedButton(
        onClick = { onClick(label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .height(70.dp)
            .size(70.dp)
    ){
        Text(
            text=  label,
            fontSize = 28.sp
        )
    }
}

@Composable
fun CalculatorButton(label: String, onClick: (String) -> Unit = {}) {
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
fun MenuButton(text: String = "", onClick: () -> Unit = {}){
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .height(70.dp)
    ){
        Text(
            text = text,
            fontSize = 30.sp
        )
    }
}