package com.example.firebaseejemplo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.firebaseejemplo.databinding.ActivityMainBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONStringer

private lateinit var database: DatabaseReference
private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.btnSet.setOnClickListener {

            writeUser(binding.etUserId.text.toString(), binding.etUserName.text.toString(), binding.etUserEmail.text.toString())
            binding.etUserId.text.clear()
            binding.etUserName.text.clear()
            binding.etUserEmail.text.clear()

        }
        binding.btnGet.setOnClickListener {
            getUsar(binding.etUserIdToGet.text.toString())
        }
        val myDB= FirebaseDatabase.getInstance()
        database=myDB.reference
        //writeUser("002", "El pilo", "fg7893210@gmail.com")
        //getUsar("001")
    }
    fun getUsar(userId: String) {
        database.child("usuarios").child(userId).get().addOnSuccessListener { record ->


            if (record.exists()) {

                    Log.d("resultado", "${record.value}")
                val json = JSONObject(record.value.toString())
                binding.resultado.setText("Nombre:  ${json.getString("nombre")} Correo: ${json.getString("correo")}")
                binding.etUserIdToGet.text.clear()
                Log.d("resultado", "${json}")

            }else{
                binding.resultado.setText("")
                binding.resultado.setText("No se encontraron resultados")
                binding.etUserIdToGet.text.clear()
            }


        }
    }


    fun writeUser(userId: String, name: String, email: String ){
        val user= User(name, email)
        database.child("usuarios").child(userId).setValue(user)

    }

}
class User(name:String, email:String )
{
    val nombre=name
    val correo=email
}