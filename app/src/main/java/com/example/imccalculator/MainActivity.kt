package com.example.imccalculator

import android.graphics.Color
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

//implementando o Click Listener utilizando lambda

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setListeners()
    }
    private fun setListeners(){
        buttonCalculate.setOnClickListener{
            val height = editHeight.text.toString().toDouble()
            val weight = editWeight.text.toString().toDouble()
            val imc = weight/ height.pow(2)

            //torna o TextView visível
            textResponse.visibility = View.VISIBLE

            when{
                imc < 18.5 -> textResponse.text = "Abaixo do peso"
                imc < 24.9 -> textResponse.text = "Peso ideal"
                imc < 29.9 -> textResponse.text = "Levemente acima do peso"
                imc <  34.9 -> textResponse.text = "Obesidade Grau I"
                imc < 39.9 -> textResponse.text = "Obesidade Grau II"
                else -> textResponse.text = "Obesidade Mórbida (Tu vai morreerr)"
            }
            when{
                imc < 18.5 || imc > 34.9 -> textResponse.setTextColor(Color.RED)
                else -> textResponse.setTextColor(Color.GREEN)
            }
        }
    }

}