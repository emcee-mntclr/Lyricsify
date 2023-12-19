package com.example.lyricsify

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class register : AppCompatActivity() {
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.register)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        emailEditText = findViewById(R.id.REmail)
        passwordEditText = findViewById(R.id.RPassword)
        signUpButton = findViewById(R.id.Rbtn)

        signUpButton.setOnClickListener {
            val email = emailEditText.text.toString().trim()
            val password = passwordEditText.text.toString().trim()

            if (validatePassword(password)) {
                if (email.isNotEmpty() && password.isNotEmpty()) {
                    auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(this) { task ->
                            if (task.isSuccessful) {
                                Log.d("SignUp", "createUserWithEmail:success")
                                val userId = auth.currentUser?.uid
                                val usersRef = database.reference.child("users")
                                val user = User(userId, email)
                                usersRef.child(userId ?: "").setValue(user)
                                val myIntent = Intent(this, MainActivity::class.java)
                                startActivity(myIntent)
                            } else {
                                Log.w("SignUp", "createUserWithEmail:failure", task.exception)
                            }
                        }
                } else {
                    Toast.makeText(this, "Kindly fill all the information needed.", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Invalid password. Password must contain at least one number and be at least 6 characters long.", Toast.LENGTH_SHORT).show()
            }
        }

        val logInClick = findViewById<TextView>(R.id.Rbtn)
        logInClick.setOnClickListener {
            val myIntent = Intent(this, login::class.java)
            startActivity(myIntent)
        }
    }

    private fun validatePassword(password: String): Boolean {
        val numberPattern = ".*\\d.*"
        return password.length >= 6 && password.matches(numberPattern.toRegex())
    }
}