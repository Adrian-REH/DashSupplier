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

class RegistroProveedorActivity : AppCompatActivity() {

    var Ptxthost: EditText?=null     // Nombre
    var Ptxtuser: EditText?=null      // Token ID
    var Ptxtpass: EditText?=null    // Fecha
    var Ptxtpass2: EditText?=null    // Fecha
    var URL:String="0"
    var PID:String="0"
    var f:String="0"
    val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    val mediosave= String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 46, 112, 104, 112, 63, 112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100, 61)) // /api/usuarios.php?proveedorid=
    val medio= String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 101, 100, 105, 116, 118, 97, 108, 111, 114, 46, 112, 104, 112)) // /api/usuarioseditvalor.php
    val k0=String(byteArrayOf(85, 82, 76))//  URL
    val k1=String(byteArrayOf(112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100))//  proveedorid
    val k2=String(byteArrayOf(117, 115, 117, 97, 114, 105, 111))//  usuario
    val k3=String(byteArrayOf(112, 97, 115, 115, 119, 111, 114, 100))//  password
    val k4=String(byteArrayOf(101, 100, 105, 99, 105, 111, 110))//  edicion




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_proveedor)

        Ptxthost=findViewById(R.id.Ptxthost)      // Nombre
        Ptxtuser=findViewById(R.id.Ptxtuser)        // Token ID
        Ptxtpass=findViewById(R.id.Ptxtpass)    // Fecha
        Ptxtpass2=findViewById(R.id.Ptxtpass2)    // Fecha
        if(intent.extras !=null){
            URL = intent.getStringExtra(k0).toString()
        }

    }

    fun clickGuardarP(view: View) {

        if(Ptxtpass2?.text.toString()== Ptxtpass?.text.toString()){
            //Primero Verifico si esta registrado el TOKEN ID
            val id =Ptxthost?.text.toString()
            val queue = Volley.newRequestQueue(this)
            val url = "$base$URL$mediosave$id"
            val jsonObjectRequest= JsonObjectRequest(
                Request.Method.GET,url,null,
                { response ->
                    //ESTA REGISTRADO

                    val url = "$base$URL$medio"
                    val queue= Volley.newRequestQueue(this)
                    //con este parametro aplico el metodo POST
                    var resultadoPost = object : StringRequest(Method.POST,url,
                        Response.Listener<String> { response ->
                            Toast.makeText(this,"Se Edito el usuario ahora puede ingresar", Toast.LENGTH_LONG).show()
                        }, Response.ErrorListener { error ->
                            Toast.makeText(this,"ERROR $error", Toast.LENGTH_LONG).show()
                        }){
                        override fun getParams(): MutableMap<String, String>? {
                            val parametros = HashMap<String,String>()
                            // Key y value
                            parametros.put(k1,Ptxthost?.text.toString())
                            parametros.put(k2,Ptxtuser?.text.toString())
                            parametros.put(k3,Ptxtpass?.text.toString())
                            parametros.put(k4,k4)


                            return parametros
                        }
                    }
                    // con esto envio o SEND todo
                    queue.add(resultadoPost)
                }, { error ->
                    Toast.makeText(this,"Error con el servicio de internet", Toast.LENGTH_SHORT).show()

                }
            )
            queue.add(jsonObjectRequest)
            finish()

        }else{
            Toast.makeText(this,"Las contraseñas no coinciden", Toast.LENGTH_LONG).show()
        }
    }

}