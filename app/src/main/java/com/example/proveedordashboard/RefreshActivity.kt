package com.example.proveedordashboard

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.util.*
import kotlin.collections.HashMap

class RefreshActivity : AppCompatActivity() {
    var EdtPA: EditText?=null
    var URL: String?=null
    var TID: String?=null
    var FECHA: String?=null
    var FECHA2:Int?=0
    var valor: Boolean=false
    val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    val medio1= String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 101, 100, 105, 116, 118, 97, 108, 111, 114, 46, 112, 104, 112))// /api/usuarioseditvalor.php
    val medio2= String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61))// /api/usuarios.php?proveedorid=
    val k0=String(byteArrayOf(85, 82, 76))            //URL
    val k1=String(byteArrayOf(85, 82, 76))            //proveedorid
    val k2=String(byteArrayOf(85, 82, 76))            //precioarch
    val k3=String(byteArrayOf(85, 82, 76))            //fecha

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_refresh)
        EdtPA =findViewById(R.id.EdtPA)
        if(intent.extras !=null){
            URL = intent.getStringExtra(k0).toString()
            if (intent.getStringExtra(k1) !=null){
                TID = (intent.getStringExtra(k1).toString())
                Toast.makeText(this,TID.toString(), Toast.LENGTH_LONG).show()
            }
        }
    }

    fun ComprobadorFecha(){
        if(valor){
            val c = Calendar.getInstance()
            val day: Int= c.get(Calendar.DAY_OF_MONTH)
            var month: Int= c.get(Calendar.MONTH) + 1
            var year: Int= c.get(Calendar.YEAR)
            if(month.equals(13)){
                month = 1
                year += 1
                var fecha:Int = ("$year"+"0"+"$month$day").toInt()
                FECHA=("$fecha")


            }else if (month<10){
                var fecha:Int = ("$year"+"0"+"$month$day").toInt()
                FECHA=("$fecha")
            }else{
                FECHA=("$year$month$day")
            }
            //Primero Verifico si esta registrado el TOKEN ID


            val url = "$base$URL$medio1"
            val queue= Volley.newRequestQueue(this)
            //con este parametro aplico el metodo POST
            var resultadoPost = object : StringRequest(Method.POST,url,
                Response.Listener<String> { response ->
                    Toast.makeText(this,"Datos de venta Actualizado!", Toast.LENGTH_LONG).show()
                }, Response.ErrorListener { error ->
                    Toast.makeText(this,"Tuvo un error con el servidor o su Acceso a la red", Toast.LENGTH_LONG).show()
                }){
                override fun getParams(): MutableMap<String, String>? {
                    val parametros = HashMap<String,String>()
                    // Key y value

                    parametros.put(k2,EdtPA?.text.toString())
                    parametros.put(k1,TID!!)
                    parametros.put(k3, FECHA!!.toString())
                    return parametros
                }
            }
            // con esto envio o SEND todo
            queue.add(resultadoPost)


        }else{
            Toast.makeText(this,"Solo se puede cambiar una vez cada 30 dias el valor del archivo por este medio",Toast.LENGTH_LONG).show()
        }



    }
    fun clickGuardar(view: View){

        val c = Calendar.getInstance()
        val day2: Int= c.get(Calendar.DAY_OF_MONTH)
        var month2: Int= c.get(Calendar.MONTH)
        var year2: Int= c.get(Calendar.YEAR)
        var fecha: Int?

        //VERIFICO  QUE LA FECHA DEL MES QUE VIENE TENGA CIERTAS CONDICIONES PARA ESCRIBIRSE A LA VARIABLE QUE LUEGO COMPROBARE
        if (month2<10){
            fecha = ("$year2"+"0"+"$month2$day2").toInt()

        }else{
            fecha = ("$year2$month2$day2").toInt()
        }


        val queue = Volley.newRequestQueue(this)

        val url = "$base$URL$medio2$TID"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                FECHA2=response.getString(k3).toString().toInt()
                if ( FECHA2.toString().toInt() > fecha.toInt()){
                    valor=false
                }else if( FECHA2.toString().toInt() < fecha.toInt()){
                    valor=true
                    //   Toast.makeText(this,FECHA2.toString()+"<"+fecha.toString(), Toast.LENGTH_LONG).show()
                }else if( FECHA2.toString().toInt() == fecha.toInt()){valor=true
                }
                ComprobadorFecha()
            }, { error ->
                FECHA2=fecha
                Toast.makeText(this,error.toString(), Toast.LENGTH_LONG).show()
                valor=true
                ComprobadorFecha()
            }
        )
        queue.add(jsonObjectRequest)






    }
}