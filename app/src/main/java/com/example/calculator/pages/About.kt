package com.example.calculator.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun AboutPage() {
    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ){
        Text(
            text = "Calculator App\n\n",
            fontSize = 34.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold
        )
        Text(
            text =  "Made by: Dmytro Malinovskyi - 252501\n\n\n" +
                    "This calculator app combines both basic and advanced functionality in one place " +
                    "It handles standard arithmetic operations like addition, subtraction, multiplication, and " +
                    "division as well as scientific functions such as square roots," +
                    " exponents, trigonometry, and logarithms—perfect for everyday calculations and " +
                    "more complex math needs.",
            fontSize = 20.sp,
        )
    }
}