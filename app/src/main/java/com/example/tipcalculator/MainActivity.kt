package com.example.tipcalculator

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.tipcalculator.databinding.ActivityMainBinding
import java.text.NumberFormat
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTextWatchers()
    }

    private fun setupTextWatchers() {
        val commonTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                calculateTip()
            }
            override fun afterTextChanged(s: Editable?) {}
        }

        binding.billAmountEditText.addTextChangedListener(commonTextWatcher)
        binding.tipPercentageEditText.addTextChangedListener(commonTextWatcher)
    }

    private fun calculateTip() {
        val billAmount = getDoubleFromEditText(binding.billAmountEditText.text)
        val tipPercentage = getDoubleFromEditText(binding.tipPercentageEditText.text)

        if (billAmount != null && tipPercentage != null) {
            val tipAmount = billAmount * (tipPercentage / 100)
            displayTipAmount(tipAmount)
        } else {
            displayTipAmount(0.0)
        }
    }

    private fun getDoubleFromEditText(text: Editable?): Double? {
        return text?.toString()?.toDoubleOrNull()
    }

    private fun displayTipAmount(amount: Double) {
        val formattedAmount = NumberFormat.getCurrencyInstance(Locale.US).format(amount)
        binding.tipAmountText.text = getString(R.string.tip_amount, formattedAmount)
    }
}