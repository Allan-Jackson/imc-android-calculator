package com.example.imccalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import com.example.imccalculator.databinding.ActivityMainBinding
//import kotlinx.android.synthetic.main.activity_main.* -- import do Kotlin Synthetics
import kotlin.math.pow

//implementação do view binding

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var latestImc: Double? = null
    private lateinit var binding: ActivityMainBinding //o 'binding' depende do layoutInflater que só existe após a criação do contexto

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonCalculate.setOnClickListener(this) //seta o clickListener para ser a própria classe que implementa OnClickListener

        //verifica se o Bundle não é nulo (se não é a primeira vez que a Activity está sendo criada)
        if(savedInstanceState != null){
            latestImc = savedInstanceState.getDouble("latest-imc")
            setLatestImcView()
        }
    }

    private fun setLatestImcView(){
        binding.textLatestImc.visibility = View.VISIBLE
        binding.textLatestImc.text = resources.getString(R.string.latest_imc).format(latestImc)
    }

    //chamado quando a Activity vai ser destruída por alteração do sistema, e salva o bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latest-imc", latestImc?:0.0) //0 se for nulo
    }

    override fun onClick(view: View) { //view se refere ao elemento que está sendo clicado

        //só vai se ativar para o botão "buttonCalculate"
        if(view.id == R.id.button_calculate){
            val height = binding.editHeight.text.toString().toDouble()
            val weight = binding.editWeight.text.toString().toDouble()
            val imc = weight/ height.pow(2)

            binding.textResponse.visibility = View.VISIBLE

            when{
                imc < 18.5 -> binding.textResponse.text = "Abaixo do peso"
                imc < 24.9 -> binding.textResponse.text = "Peso ideal"
                imc < 29.9 -> binding.textResponse.text = "Levemente acima do peso"
                imc <  34.9 -> binding.textResponse.text = "Obesidade Grau I"
                imc < 39.9 -> binding.textResponse.text = "Obesidade Grau II"
                else -> binding.textResponse.text = "Obesidade Mórbida (Tu vai morreerr)"
            }
            when{
                imc < 18.5 || imc > 34.9 -> binding.textResponse.setTextColor(Color.RED)
                else -> binding.textResponse.setTextColor(Color.GREEN)
            }
            latestImc = imc
            setLatestImcView()
        }
    }
}