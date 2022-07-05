package com.example.proveedordashboard

import android.content.Intent
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
import com.google.gson.JsonObject
import org.json.JSONObject
import java.util.*
import kotlin.collections.HashMap

class RegistroActivity : AppCompatActivity() {
    var nota: Int =12
    var txtname: EditText?=null     // Nombre
    var txttid: EditText?=null      // Token ID
    var txtfecha: EditText?=null    // Fecha
    var txtmaquina: EditText?=null  // Maquina
    var txtcel: EditText?=null      // Celular
    var txtuser: EditText?=null      // Celular
    var txtpass: EditText?=null      // Celular
    var txthost: EditText?=null      // Celular
    var URL:String?=null                //URL
    var f:String="0"
    var PID:String?=null
     val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    val mediosave1= String(byteArrayOf(47, 97, 112, 105, 47, 99, 108, 105, 101, 110, 116, 101, 115, 46, 112, 104, 112, 63, 116, 111, 107, 101, 110, 105, 100, 61))// /api/clientes.php?tokenid=
    val mediosave2= String(byteArrayOf(47, 97, 112, 105, 47, 99, 108, 105, 101, 110, 116, 101, 115, 101, 100, 105, 116, 46, 112, 104, 112))// /api/clientesedit.php
    val mediosave3= String(byteArrayOf(47, 97, 112, 105, 47, 99, 108, 105, 101, 110, 116, 101, 115, 46, 112, 104, 112))// /api/clientes.php
    val k0=String(byteArrayOf(85, 82, 76))                                                     //URL
    val k1=String(byteArrayOf(116, 111, 107, 101, 110, 105, 100))                              //tokenid
    val k2=String(byteArrayOf(110, 111, 109, 98, 114, 101))                                   //nombre
    val k3=String(byteArrayOf(70, 101, 99, 104, 97))                                           //Fecha
    val k4=String(byteArrayOf(116, 101, 108, 101, 102, 111, 110, 111))                           //telefono
    val k5=String(byteArrayOf(110, 109, 97, 113, 117, 105, 110, 97))                            //nmaquina
    val k6=String(byteArrayOf(117, 115, 117, 97, 114, 105, 111))                               //usuario
    val k7=String(byteArrayOf(112, 97, 115, 115, 119, 111, 114, 100))                           //password
    val k8=String(byteArrayOf(104, 111, 115, 116))                                               //host
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)

        txtname=findViewById(R.id.txtname)      // Nombre
        txttid=findViewById(R.id.txttid)        // Token ID
        txtfecha=findViewById(R.id.txtfecha)    // Fecha
        txtmaquina=findViewById(R.id.txtmaquina)// Maquina
        txtcel=findViewById(R.id.txtcel)        // Celular
        txtuser=findViewById(R.id.txtuser)        // Celular
        txtpass=findViewById(R.id.txtpass)        // Celular
        txthost=findViewById(R.id.txthost)        // Celular


        if(intent.extras !=null){
            URL = intent.getStringExtra(k0).toString()
            if (intent.getStringExtra(k1) !=null){
                txttid?.setText(intent.getStringExtra(k1).toString())
                clickRestaurar(View(applicationContext))
            }
        }


    }



    fun clickGuardar(view: View){
        //Primero Verifico si esta registrado el TOKEN ID
        val id =txttid?.text.toString()
        val queue = Volley.newRequestQueue(this)
        val url = "$base$URL$mediosave1$id"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                //ESTA REGISTRADO
                val url = "$base$URL$mediosave2"
                val queue= Volley.newRequestQueue(this)
                //con este parametro aplico el metodo POST
                var resultadoPost = object : StringRequest(Method.POST,url,
                    Response.Listener<String> { response ->
                        Toast.makeText(this,"CLIENTE EDITADO!", Toast.LENGTH_LONG).show()
                    }, Response.ErrorListener { error ->
                        Toast.makeText(this,"ERROR $error", Toast.LENGTH_LONG).show()
                    }){
                    override fun getParams(): MutableMap<String, String>? {
                        val parametros = HashMap<String,String>()
                        // Key y value
                        parametros.put(k2,txtname?.text.toString())
                        parametros.put(k1,txttid?.text.toString())
                        parametros.put(k3,txtfecha?.text.toString())
                        parametros.put(k5,txtmaquina?.text.toString())
                        parametros.put(k4,txtcel?.text.toString())
                        parametros.put(k7,txtpass?.text.toString())
                        parametros.put(k6,txtuser?.text.toString())
                        parametros.put(k8,txthost?.text.toString())
                        return parametros
                    }
                }
                // con esto envio o SEND todo
                queue.add(resultadoPost)

            }, { error ->

                //NO ESTA REGISTRADO
                val url = "$base$URL$mediosave3"
                val queue= Volley.newRequestQueue(this)
                //con este parametro aplico el metodo POST
                var resultadoPost = object : StringRequest(Method.POST,url,
                    Response.Listener<String> { response ->
                        Toast.makeText(this,"CLIENTE REGISTRADO!", Toast.LENGTH_LONG).show()
                    }, Response.ErrorListener { error ->
                        Toast.makeText(this,"ERROR $error", Toast.LENGTH_LONG).show()
                    }){
                    override fun getParams(): MutableMap<String, String>? {
                        val parametros = HashMap<String,String>()
                        // Key y value
                        parametros.put(k2,txtname?.text.toString())
                        parametros.put(k1,txttid?.text.toString())
                        parametros.put(k3,txtfecha?.text.toString())
                        parametros.put(k5,txtmaquina?.text.toString())
                        parametros.put(k4,txtcel?.text.toString())
                        parametros.put(k7,txtpass?.text.toString())
                        parametros.put(k6,txtuser?.text.toString())
                        parametros.put(k8,txthost?.text.toString())
                        return parametros
                    }
                }
                // con esto envio o SEND todo
                queue.add(resultadoPost)


            }




        )
        queue.add(jsonObjectRequest)



    }
    fun clickRestaurar(view: View){
        val id =txttid?.text.toString()
        val queue = Volley.newRequestQueue(this)
        val url = "$base$URL$mediosave1$id"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                txtname?.setText(response.getString(k2))
                txtfecha?.setText(response.getString(k3))
                txtcel?.setText(response.getString(k4))
                txtmaquina?.setText(response.getString(k5))
                txtuser?.setText(response.getString(k6))
                txtpass?.setText(response.getString(k7))
                txthost?.setText(response.getString(k8))
            }, { error ->
                Toast.makeText(this,error.toString(), Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)

    }
    fun ClickBorrar(view: View){
        val id =txttid?.text.toString()
        val url = "$base$URL$mediosave1$id"

        val queue = Volley.newRequestQueue(this)

        var resultadoDelete = object : StringRequest(
            Request.Method.DELETE,url,
            Response.Listener { response ->
                Toast.makeText(this,"El usuario se borro de forma exitosa", Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(this,"El usuario se borro de forma exitosa", Toast.LENGTH_LONG).show()
            }
        ){

        }
        queue.add(resultadoDelete)
    }
    fun ClickComprobar(view: View){
        val id =txttid?.text.toString()
        val queue = Volley.newRequestQueue(this)
        val url = "$base$URL$mediosave1$id"
        val jsonObjectRequest= JsonObjectRequest(
            Request.Method.GET,url,null,
            { response ->
                Toast.makeText(this,"Este Token ID Existe!", Toast.LENGTH_LONG).show()
            }, { error ->
                GeneraTokenID()
                val id2 =PID
                val queue2 = Volley.newRequestQueue(this)
                val url2 = "$base$URL$mediosave1$id2"
                val jsonObjectRequest2= JsonObjectRequest(
                    Request.Method.GET,url2,null,
                    { response ->
                        //SI EXISTE ENTONCES DEBO GENERAR OTRO YA QUE NO LO ESTOY GUARDANDO POR ENDE ESTUVO AHI ANTES DE COMPROBARLO
                        Toast.makeText(this," Token ID NO se pudo generar intenta con otro Usuario y Contraseña", Toast.LENGTH_LONG).show()
                    }, { error ->
                        Toast.makeText(this," Token ID! GENERADO EXITOSAMENTE", Toast.LENGTH_LONG).show()
                        //COMO NO EXISTE ENTONCES YA ENCONTRE UN CODIGO ID QUE SEA UNICO ENTRE TODOS
                        /*DEBO GENERAR UN CODIGO UNICO PARA CADA PROVEEDOR COMO LO HAGO?*/
                    }
                )
                queue2.add(jsonObjectRequest2)

                // Actualizamos la condicion


                txttid?.setText(PID)



            }
        )
        queue.add(jsonObjectRequest)
    }
    fun GeneraTokenID(){
        f= txtuser?.text.toString() + txtpass?.text.toString()
        val c = f.toByteArray()
        PID= Integer.toHexString(c.hashCode())

    }
    fun ClickHora(view: View){
        val c = Calendar.getInstance()
        val day: Int= c.get(Calendar.DAY_OF_MONTH)
        var month: Int= c.get(Calendar.MONTH) + 1
        var year: Int= c.get(Calendar.YEAR)
        if(month.equals(13)){
            month = 1
            year += 1
            var fecha:Int = ("$year"+"0"+"$month$day").toInt()
            txtfecha?.setText("$fecha")


        }else if (month<10){
            var fecha:Int = ("$year"+"0"+"$month$day").toInt()
            txtfecha?.setText("$fecha")
        }else{
            txtfecha?.setText("$year$month$day")
        }

        //  val datepicker = DatePickerFragment{day,month,year ->onDateSelected(day,month,year)}
        //    datepicker.show(supportFragmentManager,"datepicker")

    }


}