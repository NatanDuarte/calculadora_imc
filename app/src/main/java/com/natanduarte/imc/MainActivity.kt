package com.natanduarte.imc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var weightField: EditText
    private lateinit var heightField: EditText
    private lateinit var classificationField: TextView
    private lateinit var imcField: TextView
    private lateinit var calculateButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = getString(R.string.app_title)

        initializeFields()
        handleCalculateButton()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.help_buttom, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.activity_main_help) {
            val dialogBuilder = AlertDialog.Builder(this)
            dialogBuilder.setTitle("Atenção")
            dialogBuilder.setMessage(getString(R.string.alert_message))
            dialogBuilder.setPositiveButton("entendi") { dialog, which -> }
            val dialog = dialogBuilder.create()
            dialog.show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun initializeFields() {
        weightField = findViewById(R.id.activity_main_weight_field)
        heightField = findViewById(R.id.activity_main_height_field)
        classificationField = findViewById(R.id.activity_main_classification)
        imcField = findViewById(R.id.activity_main_imc)
        calculateButton = findViewById(R.id.activity_main_calculate_button)
    }

    private fun handleCalculateButton() {
        calculateButton.setOnClickListener {
            if (noValidFields()) {
                sendWarn()
            } else {
                setImcResult()
            }
        }
    }

    private fun sendWarn() {
        Toast.makeText(
            this.applicationContext,
            getString(R.string.toast_error_message),
            Toast.LENGTH_SHORT
        ).show()
        classificationField.text = getString(R.string.classification_field)
        imcField.text = getString(R.string.imc_field)
    }

    private fun setImcResult() {
        val weight: Float = weightField.text.toString().toFloat()
        val height: Float = heightField.text.toString().toFloat()

        val imc = getIMC(weight, height)

        imcField.text = String.format("ICM: %.2f", imc)
        classificationField.text =
            String.format("Status: ${evaluateImcClassification(imc)}")
    }

    private fun evaluateImcClassification(imc: Float): String {
        return if (imc < 15.5)
            "magreza"
        else if (imc >= 18.5 && imc < 25)
            "Normal"
        else if (imc >= 25 && imc < 30)
            "Sobrepeso"
        else if (imc >= 30 && imc < 40)
            "Obesidade"
        else
            "Obesidade grave"
    }

    private fun getIMC(weight: Float, height: Float): Float =
        weight / height.pow(2)

    private fun noValidFields() =
        weightField.text.isNullOrEmpty() || heightField.text.isNullOrEmpty()
}