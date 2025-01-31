
/*

package com.micky.proyectokotlin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val correo = findViewById<EditText>(R.id.usernameInput)
        val pass = findViewById<EditText>(R.id.passwordInput)
        val boton_login = findViewById<Button>(R.id.loginButton)
        val boton_crear_cuenta = findViewById<Button>(R.id.registerButton)

        boton_login.setOnClickListener {

                if(pass.text.toString()!=""){

                    if(correo.text.toString()!="" && Patterns.EMAIL_ADDRESS.matcher(correo.text.toString()).matches()){
                            login_firebase(correo.text.toString(), pass.text.toString())
                    }else{
                        Toast.makeText(applicationContext, "Escriba un correo valido", Toast.LENGTH_SHORT).show()
                    }

                }else{
                    Toast.makeText(applicationContext, "Escriba la contraseña", Toast.LENGTH_SHORT).show()
                }

        }

        boton_crear_cuenta.setOnClickListener {
            DialogoCrearCuenta().show(supportFragmentManager,null)

        }


    }

    fun login_firebase(correo:String, pass:String) {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {

                    var intent = Intent(this, MainActivity::class.java)
                    intent.putExtra("Correo", task.result.user?.email.toString())
                    intent.putExtra("Proveedor", "usuario/contraseña")
                    startActivity(intent)
                } else {
                    Toast.makeText(applicationContext, "Usuario o la contraseña no registrado", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
*/

package com.micky.proyectokotlin

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        auth = FirebaseAuth.getInstance()

        val correo = findViewById<EditText>(R.id.usernameInput)
        val pass = findViewById<EditText>(R.id.passwordInput)
        val botonLogin = findViewById<Button>(R.id.loginButton)
        val botonRegistro = findViewById<TextView>(R.id.registerButton)
        val botonRecuperar = findViewById<Button>(R.id.forgotPasswordLink)

        botonLogin.setOnClickListener {
            val emailText = correo.text.toString().trim()
            val passText = pass.text.toString().trim()

            if (emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(this, "Escriba un correo válido", Toast.LENGTH_SHORT).show()
            } else if (passText.isEmpty()) {
                Toast.makeText(this, "Escriba la contraseña", Toast.LENGTH_SHORT).show()
            } else {
                iniciarSesion(emailText, passText)
            }
        }

        botonRegistro.setOnClickListener {
            val dialogo = DialogoCrearCuenta()
            dialogo.show(supportFragmentManager, "DialogoCrearCuenta")
        }

        botonRecuperar.setOnClickListener {
            val emailText = correo.text.toString().trim()
            if (emailText.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
                Toast.makeText(this, "Ingrese un correo válido para recuperar la contraseña", Toast.LENGTH_SHORT).show()
            } else {
                restaurarPassword(emailText)
            }
        }
    }

    private fun iniciarSesion(correo: String, pass: String) {
        auth.signInWithEmailAndPassword(correo, pass)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = auth.currentUser
                    if (user != null && user.isEmailVerified) {
                        val intent = Intent(this, MainActivity::class.java)
                        intent.putExtra("Correo", user.email)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "Por favor, verifica tu correo antes de iniciar sesión.", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this, "Error en el inicio de sesión. Verifique sus datos.", Toast.LENGTH_SHORT).show()
                }
            }
    }

    private fun restaurarPassword(correo: String) {
        auth.sendPasswordResetEmail(correo)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Correo de restauración enviado.", Toast.LENGTH_LONG).show()
                } else {
                    Toast.makeText(this, "Error al enviar el correo de restauración.", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
