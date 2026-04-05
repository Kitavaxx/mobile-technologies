package com.example.calculator.pages

import android.app.Activity
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.calculator.About
import com.example.calculator.Advanced
import com.example.calculator.Basic
import com.example.calculator.buttons.MenuButton

@Composable
fun CalculatorMenu(navController: NavController) {
    val activity = LocalContext.current as? Activity

    Column(
        Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            text = "Calculator Menu",
            fontSize = 42.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 20.dp)
        )

        MenuButton("Basic"){
            navController.navigate(Basic)
        }
        MenuButton("Advanced"){
            navController.navigate(Advanced)
        }
        MenuButton("About"){
            navController.navigate(About)
        }
        MenuButton("Exit"){
            activity?.finishAffinity()
        }
    }
}