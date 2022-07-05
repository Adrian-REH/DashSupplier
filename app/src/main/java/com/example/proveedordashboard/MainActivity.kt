package com.example.proveedordashboard

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    var txtloguser:EditText?=null         // TEXTO DE USUARIO
    var txtlogpas:EditText?=null          // TEXTO DE PASSWORD
    var I:Int=0      // Celular
    val base= String(byteArrayOf(104, 116, 116, 112, 58, 47, 47))// http://
    //  val URL=String(byteArrayOf(100, 105, 114, 97, 99, 54, 101, 105, 100, 46, 100, 100, 110, 115, 46, 110, 101, 116))//  dirac6eid.ddns.net
    val medioinises=String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 117, 115, 117, 97, 114, 105, 111, 61))// /api/usuariosedit.php?usuario=
    val medioinises2=String(byteArrayOf(47, 97, 112, 105, 47, 117, 115, 117, 97, 114, 105, 111, 115, 101, 100, 105, 116, 46, 112, 104, 112, 63, 112, 97, 115, 115, 119, 111, 114, 100, 61))//  /api/usuariosedit.php?password=
    val k0=String(byteArrayOf(100, 97, 116, 97))//  data
    val k1=String(byteArrayOf(112, 114, 111, 118, 101, 101, 100, 111, 114, 105, 100))//  proveedorid
    val k2=String(byteArrayOf(85, 82, 76))//  URL
    //val URL=String(byteArrayOf(49, 57, 50, 46, 49, 54, 56, 46, 52, 46, 49, 48, 48))// 192.168.4.100
    val URL=String(byteArrayOf(50, 51, 104, 101, 114, 114, 101, 114, 97, 46, 120, 121, 122, 58, 56, 49))// 23herrera.xyz:81

    var idproveedorput: String?=null      // N EN VALOR NULO PARA PODER ACCEDER USANDO IF
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        txtloguser = findViewById(R.id.txtloguser)        // TEXTO DE USUARIO
        txtlogpas=findViewById(R.id.txtlogpas)            // TEXTO DE PASSWORD





        button.setOnClickListener {
            checkPermissions()
            }
        reg.setOnClickListener {
            Registrarse()
        }
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_GRANTED){
            requestInternetPermission()
        }else{
             ClickIniciarSesion()
        }
    }

    private fun requestInternetPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.INTERNET)){
            Toast.makeText(this,"Permiso Rechazado ",Toast.LENGTH_LONG).show()

        }else{
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.INTERNET),458)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode ==458){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                ClickIniciarSesion()
            }else{
                Toast.makeText(this,"Permiso Rechazado por primera Vez",Toast.LENGTH_LONG).show()

            }


        }

    }

    fun ClickIniciarSesion(){

        try {
            val id =txtloguser?.text.toString()
            var queue = Volley.newRequestQueue(this)
            var url = "$base$URL$medioinises$id"
            var jsonObjectRequest= JsonObjectRequest(Request.Method.GET,url,null,
                { response ->
                             var jsonArray = response.getJSONArray(k0)
                            var jsonObject= jsonArray.getJSONObject(0)
                            val txtproveedorid= jsonObject.getString(k1).toString()
                            idproveedorput=txtproveedorid
                    I=1

                }, { error ->

                    Toast.makeText(this,"Su Usuario es Incorrecto", Toast.LENGTH_LONG).show()
                }
            )
            queue.add(jsonObjectRequest)


                val id2 =txtlogpas?.text.toString()
                val queue2 = Volley.newRequestQueue(this)
                val url2 = "$base$URL$medioinises2$id2"
                val jsonObjectRequest2= JsonObjectRequest(
                    Request.Method.GET,url2,null,
                    { response ->

                        if(I==1){
                            val intent = Intent(this, Button_Navigation_Bar::class.java)
                            intent.putExtra(k2,URL)
                            intent.putExtra(k1,idproveedorput)
                            startActivity(intent)
                        }




                    }, { error ->
                        Toast.makeText(this,"Su password es Incorrecta", Toast.LENGTH_LONG).show()
                    }
                )
                queue2.add(jsonObjectRequest2)




        }catch (e: JSONException){
            e.printStackTrace()
        }


    }


    fun Registrarse(){
        val intent = Intent(this, RegistroProveedorActivity::class.java)
        intent.putExtra(k2,URL)
        startActivity(intent)
    }
}