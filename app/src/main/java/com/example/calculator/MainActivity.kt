package com.example.calculator

import android.app.Activity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.serialization.Serializable
@Serializable
object Menu
@Serializable
object Basic
@Serializable
object Advanced
@Serializable
object About

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            val navController = rememberNavController()

            Scaffold(
                modifier = Modifier.fillMaxSize(),
                content = { padding ->
                    NavHost(
                        navController = navController,
                        startDestination = Menu,
                        modifier = Modifier.padding(padding)
                    ) {
                        composable<Menu> {
                            CalculatorMenu(navController)
                        }
                        composable<Basic> {
                            BasicCalculator(navController)
                        }
                        composable<Advanced> {
                            AdvancedCalculator(navController)
                        }
                        composable<About> {
                            AboutPage(navController)
                        }
                    }
                }
            )
        }
    }
}

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
            fontSize = 32.sp,
            modifier = Modifier.padding(bottom = 40.dp)
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

fun tokenizeInput(expr: String): List<String> {
    val tokens = mutableListOf<String>();
    var numberBuffer = ""

    for(char in expr){
        if(char.isDigit() || char == '.'){
            numberBuffer += char
        }else if(char in "+-*/%"){
            if(numberBuffer.isNotEmpty()){
                tokens.add(numberBuffer);
                numberBuffer = ""
            }
            tokens.add(char.toString())
        }
    }

    if(numberBuffer.isNotEmpty()) tokens.add(numberBuffer)

    return tokens;
}

fun evaluate(tokens: List<String>): Double {
    val stack = mutableListOf<Double>()
    val ops = mutableListOf<String>()

    fun applyOp() {
        val b = stack.removeLast()
        val a = stack.removeLast()
        val op = ops.removeLast()
        val result = when(op) {
            "+" -> a + b
            "-" -> a - b
            "*" -> a * b
            "/" -> a / b
            "%" -> a % b
            else -> 0.0
        }
        stack.add(result)
    }

    val precedence = mapOf(
        "+" to 1, "-" to 1,
        "*" to 2, "/" to 2, "%" to 2
    )

    for (token in tokens) {
        if (token.toDoubleOrNull() != null) {
            stack.add(token.toDouble())
        } else if (token in precedence) {
            while (ops.isNotEmpty() && precedence[ops.last()]!! >= precedence[token]!!) {
                applyOp()
            }
            ops.add(token)
        }
    }

    while (ops.isNotEmpty()) applyOp()

    return stack.first()
}

@Composable
fun BasicCalculator(navController : NavController) {

    val viewModel : MyViewModel = viewModel()
    val input = viewModel.input.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.weight(0.5f))

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
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

        val currentContext = LocalContext.current
        val toastDuration = Toast.LENGTH_SHORT
        val errorText = "Invalid operation"
        val operations = "+-/%.*"
        val operatorsList = listOf("/", "+", "*", "-", "%")
        var token = ""

        LazyVerticalGrid(
            columns = GridCells.Fixed(4),
        ) {
            items(buttons) { button ->
                CalculatorButton(
                    label = button,
                    onClick = {
                        when(button){
                            "C" -> viewModel.deleteLast();
                            "AC" -> viewModel.clear();
                            in operatorsList + "." ->{
                                val lastChar = input.value.lastOrNull()
                                val tokens = tokenizeInput(input.value)
                                val lastToken = tokens.lastOrNull().orEmpty()
                                var dotSet = 0

                                if (button == ".") {
                                    if ('.' !in lastToken && !lastToken.isEmpty()) {
                                        viewModel.append(button)
                                        dotSet = 1
                                    } else {
                                        Toast.makeText(currentContext, errorText, toastDuration).show()
                                    }

                                }

                                if (lastChar != null && lastChar in operations) {
                                    if(dotSet == 1) viewModel.onInputChange(input.value.dropLast(1) + button)
                                } else if (lastChar != null) {
                                    if(dotSet == 0) viewModel.append(button)
                                }
                            }
                            "=" -> {
                                val evalResult = evaluate(tokenizeInput(input.value)).toString()
                                viewModel.setResult(evalResult)
                            }
                            "+/-" -> {
                                val tokenized = tokenizeInput(input.value).toMutableList()
                                token = tokenized.last()
                                if(token !in operations){
                                    val sign = tokenized.getOrNull(tokenized.size - 2)
                                    if(sign == "-"){
                                        tokenized[tokenized.size - 2] = "+"
                                    }else if(sign == "+"){
                                        tokenized[tokenized.size - 2] = "-"
                                    }
                                    viewModel.onInputChange(tokenized.joinToString(""))
                                }
                            }
                            else -> viewModel.append(button)
                        }
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AdvancedCalculator(navController : NavController) {
    val viewModel : MyViewModel = viewModel()
    val input = viewModel.input.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ){
        Spacer(modifier = Modifier.weight(0.5f))

        TextField(
            modifier = Modifier
                .fillMaxWidth(),
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
            "sin","cos","tan","ln",
            "sqrt","x^2","x^y","log",
            "AC","()","%","Del",
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "0",".","=","+"
        )
        Spacer(modifier = Modifier.weight(1f))

        LazyVerticalGrid(
            columns = GridCells.Fixed(4)

        ) {
            items(buttons) { button ->
                AdvancedCalculatorButton(
                    label = button,
                    onClick = {
                        when(button){
                            button -> viewModel.append(button)
                        }
                    }
                )
            }
        }
    }
}
@Composable
fun AboutPage(navController : NavController) {
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

class MyViewModel : ViewModel() {
    private val _input = MutableStateFlow("")
    val input: StateFlow<String> = _input

    fun onInputChange(newValue: String){
        _input.value = newValue
    }

    fun clear(){
        _input.value = ""
    }

    fun append(value: String){
        _input.value += value
    }

    fun deleteLast(){
        _input.value = _input.value.dropLast(1)
    }

    fun setResult(value: String){
        _input.value = value
    }
}

