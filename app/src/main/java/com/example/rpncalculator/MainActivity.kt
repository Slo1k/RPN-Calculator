package com.example.rpncalculator

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.example.rpncalculator.databinding.ActivityMainBinding
import kotlin.math.pow
import kotlin.math.sqrt
import java.util.Stack

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private var roundToXDecimalPlaces = 2
    private var stackLevels = 4

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val stack = Stack<Double>()

        fun enterNumber(num: String) {
            var currentText = binding.tvWorking.text.toString()
            if (currentText.toDouble()
                    .equals(0.0) && !currentText.contains(getString(R.string.dot))
            ) {
                binding.tvWorking.text = num
            } else {
                currentText += num
                binding.tvWorking.text = currentText
            }
        }

        fun displayStack() {
            if (stack.empty()) {
                binding.tvStack.text = ""
            } else {
                var stackString: String
                if (stack.size > stackLevels - 1) {
                    stackString =
                        stack.slice(stack.size - 1 downTo stack.size - (stackLevels - 1)).reversed()
                            .joinToString("\n") { String.format("%.${roundToXDecimalPlaces}f", it) }
                } else {
                    stackString = stack.joinToString("\n") {
                        String.format(
                            "%.${roundToXDecimalPlaces}f",
                            it
                        )
                    }
                    for (i in 0..stackLevels - 2 - stack.size) {
                        stackString = "\n" + stackString
                    }
                }
                binding.tvStack.text = stackString
            }
        }

        binding.btnAllClear.setOnClickListener {
            stack.clear()
            binding.tvWorking.text = getString(R.string.zero)
            displayStack()
        }

        binding.btnUndo.setOnClickListener {
            val length = binding.tvWorking.length()
            if (length > 1) {
                binding.tvWorking.text = binding.tvWorking.text.subSequence(0, length - 1)
            } else if (length == 1) {
                binding.tvWorking.text = getString(R.string.zero)
            }
        }

        binding.btnUndo.setOnLongClickListener {
            binding.tvWorking.text = getString(R.string.zero)
            true
        }

        binding.btn0.setOnClickListener {
            enterNumber(getString(R.string.zero))
        }

        binding.btn1.setOnClickListener {
            enterNumber(getString(R.string.one))
        }

        binding.btn2.setOnClickListener {
            enterNumber(getString(R.string.two))
        }

        binding.btn3.setOnClickListener {
            enterNumber(getString(R.string.three))
        }

        binding.btn4.setOnClickListener {
            enterNumber(getString(R.string.four))
        }

        binding.btn5.setOnClickListener {
            enterNumber(getString(R.string.five))
        }

        binding.btn6.setOnClickListener {
            enterNumber(getString(R.string.six))
        }

        binding.btn7.setOnClickListener {
            enterNumber(getString(R.string.seven))
        }

        binding.btn8.setOnClickListener {
            enterNumber(getString(R.string.eight))
        }

        binding.btn9.setOnClickListener {
            enterNumber(getString(R.string.nine))
        }

        binding.btnDot.setOnClickListener {
            var currentText = binding.tvWorking.text.toString()
            if (currentText.contains(getString(R.string.dot))) {
                Toast.makeText(
                    this,
                    getString(R.string.errorMultipleDecimalPoints),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            } else {
                currentText += "."
                binding.tvWorking.text = currentText
            }
        }

        binding.btnEnter.setOnClickListener {
            stack.push(binding.tvWorking.text.toString().toDouble())
            displayStack()
        }

        binding.btnAddition.setOnClickListener {
            if (stack.size > 0) {
                val value1 = stack.pop()
                val value2 = binding.tvWorking.text.toString().toDouble()
                val result = value1 + value2
                binding.tvWorking.text = result.toString()
                displayStack()
            } else {
                Toast.makeText(this, getString(R.string.errorAddition), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.btnSubtraction.setOnClickListener {
            if (stack.size > 0) {
                val value1 = stack.pop()
                val value2 = binding.tvWorking.text.toString().toDouble()
                val result = value1 - value2
                binding.tvWorking.text = result.toString()
                displayStack()
            } else {
                Toast.makeText(this, getString(R.string.errorSubtraction), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }

        binding.btnMultiplication.setOnClickListener {
            if (stack.size > 0) {
                val value1 = stack.pop()
                val value2 = binding.tvWorking.text.toString().toDouble()
                val result = value1 * value2
                binding.tvWorking.text = result.toString()
                displayStack()
            } else {
                Toast.makeText(this, getString(R.string.errorMultiplication), Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
        }

        binding.btnDivision.setOnClickListener {
            if (stack.size > 0) {
                val value2 = binding.tvWorking.text.toString().toDouble()
                if (value2.equals(0.0)) {
                    Toast.makeText(
                        this,
                        getString(R.string.errorDivisionByZero),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val value1 = stack.pop()
                val result = value1 / value2
                binding.tvWorking.text = result.toString()
                displayStack()
            } else {
                Toast.makeText(this, getString(R.string.errorDivision), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }

        binding.btnPower.setOnClickListener {
            if (stack.size > 0) {
                val value1 = stack.peek()
                val value2 = binding.tvWorking.text.toString().toDouble()
                val result = value1.pow(value2)
                if (result.isNaN()) {
                    Toast.makeText(this, getString(R.string.errorInvalidPower), Toast.LENGTH_SHORT)
                        .show()
                    return@setOnClickListener
                }
                stack.pop()
                binding.tvWorking.text = result.toString()
                displayStack()
            } else {
                Toast.makeText(this, getString(R.string.errorPower), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
        }


        binding.btnRoot.setOnClickListener {
            val value1 = binding.tvWorking.text.toString().toDouble()
            if (value1.equals(0.0)) {
                Toast.makeText(this, getString(R.string.errorSqrRoot), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            binding.tvWorking.text = sqrt(value1).toString()
            displayStack()
        }

        binding.btnChangeSign.setOnClickListener {
            val newText = binding.tvWorking.text.toString().toDouble() * -1
            if (newText.equals(0.0)) {
                return@setOnClickListener
            }
            binding.tvWorking.text = newText.toString()
            displayStack()
        }

        binding.btnDrop.setOnClickListener {
            if (stack.size > 0) {
                val firstElementFromStack = stack.pop().toString()
                binding.tvWorking.text = firstElementFromStack
            } else {
                binding.tvWorking.text = getString(R.string.zero)
            }
            displayStack()
        }

        binding.btnSwap.setOnClickListener {
            if (stack.size == 0) {
                return@setOnClickListener
            }
            val value1: Double = binding.tvWorking.text.toString().toDouble()
            val value2: Double = stack.pop()
            stack.push(value1)
            binding.tvWorking.text = value2.toString()
            displayStack()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.miSettings) {
            val intent = Intent(this, SettingsActivity::class.java)
            startActivityForResult(intent, 1000)
        }
        return true
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            if (data != null) {
                stackLevels = data.getStringExtra("STACKLEVELS")?.toInt() ?: 4
                roundToXDecimalPlaces = data.getStringExtra("ROUNDTOX")?.toInt() ?: 2
                when (data.getStringExtra("STACKCOLOR")) {
                    "White" -> binding.clStackDisplay.setBackgroundResource(R.color.white)
                    "Blue" -> binding.clStackDisplay.setBackgroundResource(R.color.blue)
                    "Green" -> binding.clStackDisplay.setBackgroundResource(R.color.green)
                }
                when (stackLevels) {
                    4 -> binding.tvStackId.text = getString(R.string.stackInd4)
                    3 -> binding.tvStackId.text = getString(R.string.stackInd3)
                    2 -> binding.tvStackId.text = getString(R.string.stackInd2)
                }
            }
        }
    }
}
