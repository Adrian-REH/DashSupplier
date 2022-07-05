package com.example.proveedordashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_maq_data.*

class MaqDataActivity : AppCompatActivity() {
    var Dtxtfecha:TextView?=null        // FECHA TEXTO
    var Dtxtfecharest:TextView?=null    // FECHA RESTANTE TEXTO
    var txtMaqData: TextView?=null      // MAQUINA ID
    var Dtxthost:TextView?=null         // EL HOST DE LA MAQUINA
    var Dtxtnsop:TextView?=null         // CLIENTES SOPORTADOS
    var Dtxtnmaq:TextView?=null         // NUMERO DE MAQUINA ASIGNADO POR EL PROVEEDOR
    var Dtxtnocupados:TextView?=null    // NUMERO DE CLIENTES QUE OCUPAN LA MAQUINA
    var Dtxtpass:TextView?=null         // CONTRASEÑA
    var Dtxtprecio:TextView?=null       // PRESIO QUE LE ASGINO A CADA MAQUINA DE FORMA CALCULADA
    var Dtxtuser:TextView?=null         // USUARIO
    var DbtnVender:Button?=null         // BOTON PARA VENDER
    var URL:String?=null      // Celular

    val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    val medio = String(byteArrayOf(47, 97, 112, 105, 47, 109, 97, 113, 117, 105, 110, 97, 46, 112, 104, 112, 63, 109, 97, 113, 117, 105, 110, 97, 105, 100, 61))// /api/maquina.php?maquinaid=


    val k0=String(byteArrayOf(85, 82, 76))                                                                   //URL
    val k1=String(byteArrayOf(109, 97, 113, 117, 105, 110, 97, 105, 100))                                      //maquinaid
    val k2=String(byteArrayOf(102, 101, 99, 104, 97))                                                          //fecha
    val k3=String(byteArrayOf(104, 111, 115, 116))                                                           //host
    val k4=String(byteArrayOf(110, 117, 109, 101, 114, 111))                                                  //numero
    val k5=String(byteArrayOf(115, 111, 112, 111, 114, 116, 97, 100, 111, 115))                                //soportados
    val k6=String(byteArrayOf(99, 97, 110, 99, 108, 105, 101, 110, 116, 101, 115))                               //canclientes
    val k7=String(byteArrayOf(112, 97, 115, 115, 119, 111, 114, 100))                                           //password
    val k8=String(byteArrayOf(117, 115, 117, 97, 114, 105, 111))                                              //usuario
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maq_data)

        Dtxtfecha = findViewById(R.id.Dtxtfecha)        // FECHA TEXTO
        txtMaqData=findViewById(R.id.txtMaqData)        // MAQUINA ID
        Dtxthost=findViewById(R.id.Dtxthost)            // EL HOST DE LA MAQUINA
        Dtxtnsop=findViewById(R.id.Dtxtnsop)            // CLIENTES SOPORTADOS
        Dtxtnmaq=findViewById(R.id.Dtxtnmaq)            // NUMERO DE MAQUINA ASIGNADO POR EL PROVEEDOR
        Dtxtnocupados=findViewById(R.id.Dtxtnocupados)  // NUMERO DE CLIENTES QUE OCUPAN LA MAQUINA
        Dtxtpass=findViewById(R.id.Dtxtpass)            // CONTRASEÑA
        Dtxtprecio=findViewById(R.id.Dtxtprecio)        // PRESIO QUE LE ASGINO A CADA MAQUINA DE FORMA CALCULADA
        Dtxtuser=findViewById(R.id.Dtxtuser)            // USUARIO
        DbtnVender=findViewById(R.id.DbtnVender)        // BOTON PARA VENDER



        if(intent.extras !=null){
            txtMaqData?.setText(intent.getStringExtra(k1).toString())
            URL = intent.getStringExtra(k0).toString()
            generar()
        }

    }

    fun generar(){
        val id =txtMaqData?.text.toString()
        val queue = Volley.newRequestQueue(this)
        val url = "$base$URL$medio$id"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                Dtxtfecha?.setText(response.getString(k2))
                Dtxthost?.setText(response.getString(k3))
                Dtxtnmaq?.setText(response.getString(k4))
                Dtxtnsop?.setText(response.getString(k5))
                Dtxtnocupados?.setText(response.getString(k6))
                Dtxtpass?.setText(response.getString(k7))
                Dtxtuser?.setText(response.getString(k8))


            }, { error ->
                Toast.makeText(this,error.toString(), Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)



    }

}