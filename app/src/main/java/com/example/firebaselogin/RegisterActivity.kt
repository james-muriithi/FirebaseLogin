package com.example.firebaselogin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth

class RegisterActivity : AppCompatActivity() {
    lateinit var editTextEmail: EditText
    lateinit var editTextPassword: EditText
    lateinit var editTextCPassword: EditText
    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        editTextEmail = findViewById(R.id.editTextEmail)
        editTextPassword = findViewById(R.id.editTextPassword)
        editTextCPassword=findViewById(R.id.editTextCPassword)
        val buttonLogin = findViewById<Button>(R.id.buttonLogin)
        val buttonRegister = findViewById<Button>(R.id.buttonRegister)

        auth = FirebaseAuth.getInstance()
        buttonLogin.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        }

        buttonRegister.setOnClickListener {
            val email = editTextEmail.text.toString()
            val password1 = editTextPassword.text.toString()
            val password2 = editTextCPassword.text.toString()
            Register(email,password1,password2)
        }

    }
    private fun checkPassword(password1:String,password2:String):Boolean{
        return password1.equals(password2)
    }
    private fun checkPasswordLength(password1:String):Boolean{
        return password1.length >= 6
    }

    private fun clearText(){
        editTextEmail.text.clear()
        editTextPassword.text.clear()
        editTextCPassword.text.clear()
    }

    private fun Register(email:String,password1: String,password2: String){
        if (TextUtils.isEmpty(email)){
            editTextEmail.requestFocus()
            editTextEmail.error = "Cannot be empty"
        }else if(TextUtils.isEmpty(password1)){
            editTextPassword.requestFocus()
            editTextPassword.error = "Cannot be empty"
        }
        else if(TextUtils.isEmpty(password2)){
            editTextCPassword.requestFocus()
            editTextCPassword.error = "Cannot be empty"
        }
        else if(!checkPassword(password1,password2)){
            editTextCPassword.requestFocus()
            editTextCPassword.error = "Password do not match"
        }
        else if(!checkPasswordLength(password1)){
            editTextPassword.requestFocus()
            editTextPassword.error = "Password too short"
        }else{
            auth.createUserWithEmailAndPassword(email,password1).addOnCompleteListener(this){
                task->
                clearText()
                if (task.isSuccessful){
                    //success
                    Toast.makeText(this,"Registration Success",Toast.LENGTH_LONG).show()
                }
                else{
                    //failed
                    Toast.makeText(this,"Registration Failed",Toast.LENGTH_LONG).show()
                }
            }
        }
    }

}
