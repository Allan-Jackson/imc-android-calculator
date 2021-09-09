package com.example.imccalculator

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.pow

//configurando Click Listener implementado a interface

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private var latestImc: Double? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        buttonCalculate.setOnClickListener(this) //seta o clickListener para ser a própria classe que implementa OnClickListener
    }

    private fun setLatestImcView(){
        textLatestImc.visibility = View.VISIBLE
        //utilizando Strings com placeholder ("verbos de formatação" em Go) é o recomendado
        textLatestImc.text = resources.getString(R.string.latest_imc).format(latestImc)
    }

    //chamado quando a Activity vai ser destruída por alteração do sistema, e salva o bundle
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putDouble("latest-imc", latestImc?:0.0) //0 se for nulo
    }

    //executado quando um bundle é recuperado
    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        //não é necessário verificar se o bundle é nulo aqui
        //pois esse método só é chamado quando a Activity é reconstruída pelo sistema
        //ou seja, o Bundle com certeza existirá, diferente de fazer isso no onCreate
        //onde a Activity pode estar sendo criada pela primeira vez
        latestImc = savedInstanceState.getDouble("latest-imc")
        setLatestImcView()
    }

    //Esse listener será chamado quando qualquer elemento do layout
    //receber um clique, por isso é necessário verificar pelo ID
    //qual elemento está sendo clicado e aplicar a lógica que lhe cabe
    override fun onClick(view: View) { //view se refere ao elemento que está sendo clicado
        //só vai se ativar para o botão "buttonCalculate"
        if(view.id == R.id.buttonCalculate){
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
            latestImc = imc
            setLatestImcView()
        }
    }
}