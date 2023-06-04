package com.example.skineast

import android.Manifest
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val CAMERA_PERMISSION_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val captureButton: Button = findViewById(R.id.capture_button)
        captureButton.setOnClickListener {
            if (checkCameraPermission()) {
                // Panggil metode untuk mengambil gambar
                captureImage()
            } else {
                requestCameraPermission()
            }
        }
    }

    private fun captureImage() {
        // Tambahkan kode di sini untuk mengambil gambar
        Toast.makeText(this, "Mengambil gambar...", Toast.LENGTH_SHORT).show()
    }

    private fun checkCameraPermission(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun requestCameraPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.CAMERA),
            CAMERA_PERMISSION_CODE
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == CAMERA_PERMISSION_CODE) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Izin kamera diberikan
                captureImage()
            } else {
                // Izin kamera ditolak
                Toast.makeText(this, "Izin kamera ditolak.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
