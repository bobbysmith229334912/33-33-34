package com.hardcoreamature.a33_33_34

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Switch
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class BudgetFormActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var pricingPeriodSwitch: Switch
    private lateinit var categorySpinner: Spinner
    private lateinit var priceRangeSpinner: Spinner
    private lateinit var customPriceEditText: TextInputEditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_budget_form)

        auth = FirebaseAuth.getInstance()

        initializeUI()
    }

    private fun initializeUI() {
        pricingPeriodSwitch = findViewById(R.id.switchPricingPeriod)
        categorySpinner = findViewById(R.id.spinnerCategory)
        priceRangeSpinner = findViewById(R.id.spinnerPriceRange)
        customPriceEditText = findViewById(R.id.editTextCustomPrice)
        submitButton = findViewById(R.id.buttonSubmit)

        customPriceEditText.setText("0") // Set the default value for custom price

        setupSpinners()
        setupSubmitButton()
    }

    private fun setupSpinners() {
        val categories = arrayOf(
            "Housing", "Utilities", "Groceries", "Transportation", "Healthcare",
            "Debt Payments", "Savings and Investments", "Education", "Childcare",
            "Personal Spending", "Miscellaneous", "Holidays and Vacations", "Luxurious Holidays"
        )

        categorySpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, categories)

        pricingPeriodSwitch.setOnCheckedChangeListener { _, isChecked ->
            populatePriceRangeSpinner(isChecked)
        }

        populatePriceRangeSpinner(pricingPeriodSwitch.isChecked)
    }

    private fun populatePriceRangeSpinner(isYearly: Boolean) {
        val monthlyPrices = arrayOf(
            "100", "200", "300", "400", "500", "600", "700", "800", "900", "1000",
            "1100", "1200", "1300", "1400", "1500", "1600", "1700", "1800", "1900", "2000",
            "2100", "2200", "2300", "2400", "2500", "2600", "2700", "2800", "2900", "3000",
            "3100", "3200", "3300", "3400", "3500", "3600", "3700", "3800", "3900", "4000",
            "4100", "4200", "4300", "4400", "4500", "4600", "4700", "4800", "4900", "5000"
        )
        val yearlyPrices = monthlyPrices.map { (it.toInt() * 12).toString() }.toTypedArray()
        val priceRanges = if (isYearly) yearlyPrices else monthlyPrices
        priceRangeSpinner.adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, priceRanges)
    }

    private fun setupSubmitButton() {
        submitButton.setOnClickListener {
            val selectedCategory = categorySpinner.selectedItem.toString()
            val selectedPriceRange = priceRangeSpinner.selectedItem.toString()
            val customPrice = customPriceEditText.text.toString()

            if (validateInput(customPrice)) {
                saveBudgetData(selectedCategory, selectedPriceRange, customPrice)
            }
        }
    }

    private fun validateInput(customPrice: String): Boolean {
        return when {
            customPrice.isEmpty() -> {
                showToast("Please enter a custom price")
                false
            }
            customPrice.toDoubleOrNull() == null -> {
                showToast("Please enter a valid number for the custom price")
                false
            }
            else -> true
        }
    }

    private fun saveBudgetData(category: String, priceRange: String, customPrice: String) {
        val userId = auth.currentUser?.uid
        if (userId != null) {
            val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(userId).child("BudgetData")
            val budgetData = mapOf(
                "category" to category,
                "priceRange" to priceRange,
                "customPrice" to customPrice
            )
            databaseReference.child(category).setValue(budgetData)
                .addOnSuccessListener {
                    showToast("Data saved successfully")
                }
                .addOnFailureListener {
                    showToast("Failed to save data")
                }
        } else {
            showToast("User not authenticated")
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
