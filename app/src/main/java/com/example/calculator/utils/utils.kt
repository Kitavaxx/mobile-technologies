package com.example.calculator.utils

import android.content.Context
import android.widget.Toast
import com.example.calculator.viewModels.MyViewModel
import kotlin.math.*

fun analyzeInput(button: String, input: String, viewModel: MyViewModel, context: Context){
    val operatorsList = listOf("/", "+", "*", "-", "%")
    val advancedOperatorsList: Map<String, (Double) -> Double> = mapOf(
        "sqrt" to { x -> sqrt(x) },
        "sin" to { x -> sin(x) },
        "cos" to { x -> cos(x) },
        "tan" to { x -> tan(x) },
        "ln" to { x -> ln(x) },
        "log" to { x -> log10(x) },
        )
    val advancedTrigonometricOperators = listOf("sqrt", "sin", "cos", "tan")
    val tokens = tokenizeInput(input).toMutableList()
    var lastToken = tokens.lastOrNull().orEmpty()

    when(button){
        "C" -> viewModel.deleteLast()
        "AC" -> viewModel.clear()
        "." -> {
            if ('.' !in lastToken && !lastToken.isEmpty() && lastToken !in operatorsList) {
                viewModel.append(button)
            } else {
                Toast.makeText(context, "Invalid operation", Toast.LENGTH_SHORT).show()
            }
        }
        in operatorsList ->{
            if (!lastToken.isEmpty() && lastToken in operatorsList) {
                viewModel.onInputChange(input.dropLast(1) + button)
            } else if (!lastToken.isEmpty()) {
                viewModel.append(button)
            }
        }
        "=" -> {
            if(lastToken in operatorsList || lastToken.isEmpty()){
                Toast.makeText(context, "Invalid operation", Toast.LENGTH_SHORT).show()
                return
            }

            val evalResult = evaluate(tokens).toString()
            viewModel.setResult(evalResult)
        }
        "+/-" -> {
            if(lastToken !in operatorsList){
                val sign = tokens.getOrNull(tokens.size - 2)
                if(sign == "-"){
                    tokens[tokens.size - 2] = "+"
                }else if(sign == "+"){
                    tokens[tokens.size - 2] = "-"
                }
                viewModel.onInputChange(tokens.joinToString(""))
            }
        }
        in advancedOperatorsList -> {
            if(lastToken.toDoubleOrNull() != null && !input.isEmpty()){
                var buffNumber = lastToken.toDouble()

                if(button in advancedTrigonometricOperators){
                    buffNumber *= PI / 180
                }

                val evalFunc = advancedOperatorsList[button]

                if (evalFunc != null) {
                    lastToken = evalFunc(buffNumber).toString().take(12)
                    viewModel.deleteLast()
                    viewModel.append(lastToken)
                }else{
                    Toast.makeText(context, "Unknown operator", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(context, "Invalid operation", Toast.LENGTH_SHORT).show()
            }
        }

        "()" -> {
            val openCount = input.count { it == '(' }
            val closeCount = input.count { it == ')' }

            when {
                input.isEmpty() -> {
                    viewModel.append("(")
                }
                lastToken == "(" -> {
                    viewModel.append("(")
                }
                lastToken in operatorsList -> {
                    viewModel.append("(")
                }
                lastToken.toDoubleOrNull() != null || lastToken == ")" -> {
                    if (openCount > closeCount) {
                        viewModel.append(")")
                    } else {
                        viewModel.append("*(")
                    }
                }
                openCount > closeCount -> {
                    viewModel.append(")")
                }
                else -> {
                    viewModel.append("(")
                }
            }
        }
        "x^2" -> {
            if(lastToken.toDoubleOrNull() != null && !input.isEmpty()){
                lastToken = (lastToken.toDouble() * lastToken.toDouble()).toString().take(12)
                viewModel.deleteLast()
                viewModel.append(lastToken)
            }else{
                Toast.makeText(context, "Invalid operation", Toast.LENGTH_SHORT).show()
            }
        }
        "x^y" -> {
            viewModel.append("^(")
        }
        else -> viewModel.append(button)
    }
}

fun tokenizeInput(expr: String): List<String> {
    val tokens = mutableListOf<String>()
    var numberBuffer = ""

    for(char in expr){
        if(char.isDigit() || char == '.'){
            numberBuffer += char
        }else if(char in "+-*/%"){
            if(numberBuffer.isNotEmpty()){
                tokens.add(numberBuffer)
                numberBuffer = ""
            }
            tokens.add(char.toString())
        }
    }

    if(numberBuffer.isNotEmpty()) tokens.add(numberBuffer)

    return tokens
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
            "^" -> a.pow(b)
            else -> 0.0
        }
        stack.add(result)
    }

    val precedence = mapOf(
        "+" to 1, "-" to 1,
        "*" to 2, "/" to 2, "%" to 2, "^" to 2
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