package com.example.lyricsify

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class login : AppCompatActivity() {
    //lateinit var databaseReference: DatabaseReference

    private lateinit var usernameEditTxt: EditText
    private lateinit var passwordEditTxt: EditText
    private lateinit var loginButton: Button
    private lateinit var signinButton: Button
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login)

        auth = FirebaseAuth.getInstance()

        usernameEditTxt = findViewById(R.id.LUsername)
        passwordEditTxt = findViewById(R.id.LPassword)
        loginButton = findViewById(R.id.Lloginbtn)
        signinButton = findViewById(R.id.Lsigninbtn)

        loginButton.setOnClickListener {
            val username = usernameEditTxt.text.toString()
            val password = passwordEditTxt.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty()) {
                auth.signInWithEmailAndPassword(username, password)
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val myIntent = Intent(this, MainActivity::class.java)
                            startActivity(myIntent)
                            finish()
                        } else{
                            Toast.makeText(this,"Failed. Confirm your Email and Password.", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(this, "Enter Email and Password", Toast.LENGTH_SHORT).show()
            }
        }
        signinButton.setOnClickListener {
            val myIntent = Intent(this, register::class.java)
            startActivity(myIntent)
        }
        loginButton.setOnClickListener {
            val myIntent = Intent(this, MainActivity::class.java)
            startActivity(myIntent)
        }
    }




    //databaseReference = FirebaseDatabase.getInstance().getReference("DatabaseName")
    //databaseReference.child("EmployeeDetails").child("201820011")

    /*editLEmailtxt = findViewById(R.id.LEmail);
    editLPasswordtxt = findViewById(R.id.LPassword);
    btnLbtn = findViewById(R.id.LBtn);

    btnLbtn.setOnClickListener( new View.onClickListener(){
        public void onClick(View view){
            String email, password;
            email = String.valueOf(editEmailtxt.getText());
            password = String.valueOf(editLPassword.getText());

            if (TextUtils.isEmpty(email)){
                Toast.makeText(Register.this, "Enter Email", Toast.LENGTH_SHORT).show();
                return;
            }
            if (TextUtils.isEmpty(password)){
                Toast.makeText(Register.this, "Enter Password", Toast.LENGTH_SHORT).show();

            }
        }
    }



    )
} */
}