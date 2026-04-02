package com.example.calculator.utils

import android.content.Context
import android.widget.Toast
import com.example.calculator.MyViewModel

fun analyzeInput(button: String, input: String, viewModel: MyViewModel, context: Context){
    val operatorsList = listOf("/", "+", "*", "-", "%")
    val tokens = tokenizeInput(input).toMutableList()
    val lastToken = tokens.lastOrNull().orEmpty()

    when(button){
        "C" -> viewModel.deleteLast()
        "AC" -> viewModel.clear()
        "." -> {
            if ('.' !in lastToken && !lastToken.isEmpty()) {
                viewModel.append(button)
            } else {
                Toast.makeText(context, "Invalid operation", Toast.LENGTH_SHORT).show()
            }
        }
        in operatorsList ->{
            val lastChar = input.lastOrNull()

            if (lastChar != null && lastChar.toString() in operatorsList) {
                viewModel.onInputChange(input.dropLast(1) + button)
            } else if (lastChar != null) {
                viewModel.append(button)
            }
        }
        "=" -> {
            if(lastToken in operatorsList){
                Toast.makeText(context, "Invalid operation", Toast.LENGTH_SHORT).show()
                return
            }

            val evalResult = evaluate(tokens).toString()
            viewModel.setResult(evalResult)
        }
        "+/-" -> {
            val token = tokens.last()
            if(token !in operatorsList){
                val sign = tokens.getOrNull(tokens.size - 2)
                if(sign == "-"){
                    tokens[tokens.size - 2] = "+"
                }else if(sign == "+"){
                    tokens[tokens.size - 2] = "-"
                }
                viewModel.onInputChange(tokens.joinToString(""))
            }
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