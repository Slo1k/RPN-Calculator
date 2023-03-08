package com.example.rpncalculator

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import com.example.rpncalculator.databinding.ActivitySettingsBinding
import kotlin.math.round

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.btnApply.setOnClickListener {
            val stackColor =
                findViewById<RadioButton>(binding.rgColors.checkedRadioButtonId).text.toString()
            val stackLevels =
                findViewById<RadioButton>(binding.rgStack.checkedRadioButtonId).text.toString()
                    .take(1)
            val roundToX =
                findViewById<RadioButton>(binding.rgRound.checkedRadioButtonId).text.toString()
                    .take(1)
            val data = Intent()
            data.putExtra("STACKCOLOR", stackColor)
            data.putExtra("STACKLEVELS", stackLevels)
            data.putExtra("ROUNDTOX", roundToX)
            setResult(RESULT_OK, data)
            finish()
        }
    }
}