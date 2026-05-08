package com.example.percobaanp1.Home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import com.example.percobaanp1.Home.pertemuan_7.SeventhActivity
import com.example.percobaanp1.R
import com.example.percobaanp1.databinding.FragmentHomeBinding
import com.example.percobaanp1.pertemuan6.AuthActivity


class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnp7.setOnClickListener {
            val intent = Intent(enterTransition, SeventhActivity::class.java)
            // Flags ini memastikan user tidak bisa memencet tombol 'Back' kembali ke MainActivity
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        binding.btnLogout.setOnClickListener {
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