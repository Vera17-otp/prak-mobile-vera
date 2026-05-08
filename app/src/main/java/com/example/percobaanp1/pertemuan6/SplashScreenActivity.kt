package com.example.percobaanp1.pertemuan6

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.percobaanp1.R
import com.example.percobaanp1.pertemuan6.MainActivity
import com.example.percobaanp1.pertemuan6.AuthActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menampilkan layout splash screen (biasanya berisi logo)
        setContentView(R.layout.activity_splash_screen)

        // Menggunakan lifecycleScope untuk menjalankan delay di background thread
        lifecycleScope.launch {
            // 1. Simulasi loading selama 2 detik
            delay(2000)

            // 2. Inisialisasi SharedPreferences
            val sharedPref = getSharedPreferences("SlebewPrefs", Context.MODE_PRIVATE)
            val isLogin = sharedPref.getBoolean("isLogin", false)

            // 3. Tentukan tujuan berdasarkan status login
            val targetActivity = if (isLogin) {
                MainActivity::class.java
            } else {
                AuthActivity::class.java
            }

            // 4. Pindah ke activity tujuan
            val intent = Intent(this@SplashScreenActivity, AuthActivity::class.java)
            startActivity(intent)

            // 5. Tutup Splash Screen
            finish()
        }
    }
}