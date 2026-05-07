package com.example.percobaanp1.pertemuan6

import android.content.Context // Tambahkan ini
import android.content.Intent // Tambahkan ini
import android.os.Bundle
import android.widget.Button // Tambahkan ini
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog // Tambahkan ini
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.percobaanp1.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Pastikan R.layout.activity_main2 adalah layout yang benar untuk activity ini
        setContentView(R.layout.activity_main2)

        // Penanganan padding Edge-to-Edge agar tidak tertutup Navbar/Statusbar
        // Pastikan root layout di activity_main2.xml memiliki android:id="@+id/main"
        val mainView = findViewById<android.view.View>(R.id.main)
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }
        }

        val btnLogout = findViewById<Button>(R.id.btnLogout)

        btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi Logout")
            builder.setMessage("Apakah Anda yakin ingin keluar?")

            builder.setPositiveButton("Ya") { _, _ ->
                // 1. Update SharedPreferences: ubah isLogin menjadi false
                val sharedPref = getSharedPreferences("SlebewPrefs", Context.MODE_PRIVATE)
                val editor = sharedPref.edit()
                editor.putBoolean("isLogin", false)
                editor.apply()

                // 2. Arahkan kembali ke AuthActivity
                val intent = Intent(this, AuthActivity::class.java)
                // Flags ini memastikan user tidak bisa memencet tombol 'Back' kembali ke MainActivity
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)

                // 3. Tutup MainActivity
                finish()
            }

            builder.setNegativeButton("Tidak") { dialog, _ ->
                dialog.dismiss()
            }

            builder.show()
        }
    }
}