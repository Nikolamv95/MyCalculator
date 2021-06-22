package com.example.mycalculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.mycalculator.databinding.ActivityMainBinding
import java.lang.ArithmeticException

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val lastCharIsDot: Boolean
        get() = binding.tvInput.text.lastOrNull() == '.'

    private val lastCharIsNumber: Boolean
        get() = binding.tvInput.text.lastOrNull()?.isDigit() ?: false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun onDigit(view: View) {
        binding.tvInput.append((view as Button).text)
    }

    fun onClear(view: View) {
        binding.tvInput.text = ""
    }

    fun onDecimalPoint(view: View) {
        if (lastCharIsNumber && !lastCharIsDot) {
            binding.tvInput.append(".")
        }
    }

    fun onOperator(view: View) {
        if (binding.tvInput.text.toString().isEmpty() && (view as TextView).text == "-"){
            binding.tvInput.append((view as Button).text)
        }
        if (lastCharIsNumber && !isOperatorAdded(binding.tvInput.text.toString())) {
            binding.tvInput.append((view as Button).text)
        }
    }

    fun onCalculate(view: View) {
        if (lastCharIsNumber) {
            var input = binding.tvInput.text.toString()
            var prefix = ""
            try {
                if (input.startsWith("-")) {
                    prefix = "-"
                    input = input.substring(1)
                }

                when {
                    input.contains("-") -> {
                        val splitValue = input.split("-")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        binding.tvInput.text =
                            removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())

                    }
                    input.contains("+") -> {
                        val splitValue = input.split("+")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (!prefix.isEmpty()) {
                            one = prefix + one
                        }
                        binding.tvInput.text =
                            removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())

                    }
                    input.contains("*") -> {
                        val splitValue = input.split("*")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        binding.tvInput.text =
                            removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                    }
                    else -> {
                        val splitValue = input.split("/")
                        var one = splitValue[0]
                        val two = splitValue[1]

                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        binding.tvInput.text =
                            removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())

                    }
                }
            } catch (e: ArithmeticException) {
                e.printStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result: String): String {
        var value = result
        if (result.contains(".0"))
            value = result.substring(0, result.length - 2)
        return value
    }

    private fun isOperatorAdded(value: String): Boolean =
        if (value.startsWith("-")) {
            false
        } else {
            value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")
        }
}