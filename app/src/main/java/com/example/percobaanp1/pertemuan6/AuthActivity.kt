package com.example.percobaanp1.pertemuan6

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button // Tambahkan ini
import android.widget.EditText // Tambahkan ini
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.percobaanp1.R

class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // 1. Inisialisasi SharedPreferences
        val sharedPref = getSharedPreferences("SlebewPrefs", Context.MODE_PRIVATE)

        // 2. CEK STATUS LOGIN: Cek sebelum setContentView untuk performa lebih baik
        if (sharedPref.getBoolean("isLogin", false)) {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
            return
        }

        enableEdgeToEdge()
        setContentView(R.layout.activity_auth)

        // Mengatur padding untuk sistem bars (Status bar/Navigation bar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Inisialisasi View
        val etUsername = findViewById<EditText>(R.id.etUsername)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnLogin = findViewById<Button>(R.id.btnLogin)

        btnLogin.setOnClickListener {
            val user = etUsername.text.toString().trim()
            val pass = etPassword.text.toString().trim()

            // Logika: Username == Password dan tidak boleh kosong
            if (user.isNotEmpty() && user == pass) {

                // 3. Simpan status login
                sharedPref.edit().apply {
                    putBoolean("isLogin", true)
                    putString("username", user) // Opsional: simpan nama user juga
                    apply()
                }

                // Pindah ke MainActivity
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                // Tampilkan pesan error
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Username dan Password harus sama & tidak boleh kosong")
                    .setPositiveButton("OK", null)
                    .show()
            }
        }
    }
}