package com.example.calculator.pages.Basic

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.calculator.buttons.CalculatorButton
import com.example.calculator.viewModels.MyViewModel
import com.example.calculator.utils.analyzeInput

@Composable
fun BasicCalculator() {
    val config = LocalConfiguration.current
    val isLandscape = (config.orientation == Configuration.ORIENTATION_LANDSCAPE)

    if(!isLandscape) BasicCalculatorUI()
    else BasicCalculatorLandscapeUI()
}

@Composable
fun BasicCalculatorUI() {
    val viewModel : MyViewModel = viewModel()
    val input = viewModel.input.collectAsState()
    val currentContext = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(input.value) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.weight(0.5f))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .verticalScroll(scrollState),
            value = input.value,
            onValueChange = {
                viewModel.onInputChange(it)
            },
            textStyle = TextStyle(
                fontSize = 48.sp,
                textAlign = TextAlign.End
            ),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        val buttons = listOf(
            "AC","C","%","/",
            "7","8","9","*",
            "4","5","6","-",
            "1","2","3","+",
            "+/-","0",".","="
        )
        Spacer(modifier = Modifier.weight(1f))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
        ) {
            items(buttons) { button ->
                CalculatorButton(
                    label = button,
                    onClick = {
                        analyzeInput(
                            button = button,
                            input = input.value,
                            viewModel = viewModel,
                            context = currentContext
                        )
                    }
                )
            }
        }
    }
}

@Composable
fun BasicCalculatorLandscapeUI() {
    val viewModel : MyViewModel = viewModel()
    val input = viewModel.input.collectAsState()
    val currentContext = LocalContext.current
    val scrollState = rememberScrollState()

    LaunchedEffect(input.value) {
        scrollState.scrollTo(scrollState.maxValue)
    }

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.weight(0.5f))

        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .height(95.dp)
                .verticalScroll(scrollState),
            value = input.value,
            onValueChange = { viewModel.onInputChange(it) },
            textStyle = TextStyle(fontSize = 48.sp, textAlign = TextAlign.End),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent
            )
        )

        val buttons = listOf(
            "AC","C","%","/",
            "7","8","9","*",
            "4","5","6","-",
            "1","2","3","+",
            "+/-","0",".","="
        )
        Spacer(modifier = Modifier.weight(1f))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
        ) {
            items(buttons) { button ->
                CalculatorButton(
                    label = button,
                    onClick = {
                        analyzeInput(
                            button = button,
                            input = input.value,
                            viewModel = viewModel,
                            context = currentContext
                        )
                    }
                )
            }
        }
    }
}
