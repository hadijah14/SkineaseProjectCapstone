package com.example.skineast

import android.app.Instrumentation.ActivityResult
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Camera
import android.hardware.camera2.CameraCaptureSession
import android.hardware.camera2.CameraDevice
import android.hardware.camera2.CameraManager
import android.hardware.camera2.CaptureRequest
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.provider.MediaStore
import android.view.TextureView
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton


lateinit var handler: Handler
lateinit var handlerThread: HandlerThread
lateinit var cameraManager: CameraManager
lateinit var textureView: TextureView
lateinit var cameraCaptureSession: CameraCaptureSession
lateinit var cameraDevice: CameraDevice
lateinit var captureRequest: CaptureRequest
lateinit var imageView: ImageView
lateinit var button: Button
lateinit var buttonProses: Button
lateinit var viewResultButton: Button
val  REQUEST_IMAGE_CAPTURE = 100



public class HalamanKamera : AppCompatActivity(),View.OnClickListener {

    private var our_request_code : Int = 123
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_halaman_kamera)
        get_permission()

        imageView = findViewById(R.id.Image_Save)
        cameraManager = getSystemService(Context.CAMERA_SERVICE)as CameraManager
        handlerThread = HandlerThread ("VideoThread")
        handlerThread.start()
        handler = Handler((handlerThread).looper)
        button = findViewById(R.id.btn_camera)
        buttonProses = findViewById(R.id.btn_proses)
        buttonProses.visibility = View.GONE


// CARA KALAU PAKE BITMAP
        button.setOnClickListener{
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)

            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
            }catch (e: ActivityNotFoundException){
                Toast.makeText(this,"Error:" + e.localizedMessage, Toast.LENGTH_SHORT).show()
            }
        }

        buttonProses.setOnClickListener{
           // Toast.makeText(this, "Menampilkan hasil gambar...", Toast.LENGTH_SHORT).show()
            val pindahIntent = Intent(this, HalamanDetail::class.java)
            startActivity(pindahIntent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK){
            val imageBitmap = data?.extras?.get("data") as Bitmap

            // Mendapatkan dimensi tampilan ImageView
            val imageViewWidth = imageView.width
            val imageViewHeight = imageView.height

            // Mendapatkan dimensi gambar yang diambil
            val imageWidth = imageBitmap.width
            val imageHeight = imageBitmap.height

            // Menghitung faktor skala untuk menyesuaikan ukuran gambar dengan tampilan ImageView
            val scaleWidth = imageViewWidth.toFloat() / imageWidth
            val scaleHeight = imageViewHeight.toFloat() / imageHeight
            val scaleFactor = scaleWidth.coerceAtMost(scaleHeight)

            // Mengatur skala gambar
            val scaledBitmap = Bitmap.createScaledBitmap(
                imageBitmap,
                (imageWidth * scaleFactor).toInt(),
                (imageHeight * scaleFactor).toInt(),
                true
            )

            val HD_WIDTH = 300
            val HD_HEIGHT = 500
            val hdBitmap = Bitmap.createScaledBitmap(
                scaledBitmap,
                HD_WIDTH,
                HD_HEIGHT,
                true
            )
            imageView.setImageBitmap(hdBitmap)
            buttonProses.visibility = View.VISIBLE
            button.visibility = View.GONE
        }else{
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
    fun get_permission(){
        val permissionLst =  mutableListOf<String>()

        if(checkSelfPermission(android.Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED)
            permissionLst.add(android.Manifest.permission.CAMERA)
        if(checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            permissionLst.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED)
            permissionLst.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if(permissionLst.size >0){
            requestPermissions(permissionLst.toTypedArray(),101)
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if(it != PackageManager.PERMISSION_GRANTED){
                get_permission()
            }
        }
    }

    override fun onClick(v: View?) {

        if (v!=null){
            when(v.id){
                R.id.btn_proses -> {
                    val pindahIntent = Intent(this, HalamanDetail::class.java)
                    startActivity(pindahIntent)
                }
            }
        }
    }
}