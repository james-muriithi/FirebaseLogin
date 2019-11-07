package com.example.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var auth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)

        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        auth = FirebaseAuth.getInstance()
        buttonRegister.setOnClickListener{
            val intent = Intent(this,RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }
        buttonLogin.setOnClickListener {
            Login(editTextEmail.text.toString(),editTextPassword.text.toString())
        }
    }

    private fun clearText(){
        editTextEmail.text.clear()
        editTextPassword.text.clear()
    }

    private fun Login(email:String, password:String){
        if (TextUtils.isEmpty(email)){
            editTextEmail.requestFocus()
            editTextEmail.error = "Cannot be empty"
        }else if(TextUtils.isEmpty(password)){
            editTextPassword.requestFocus()
            editTextPassword.error = "Cannot be empty"
        }else{
            auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this){task ->
                  clearText()
                    if (task.isSuccessful){
                        Toast.makeText(this,"Login Success", Toast.LENGTH_LONG).show()
                    }else{
                        Toast.makeText(this,"Login Failed",Toast.LENGTH_LONG).show()
                    }

                }
        }


    }
}
