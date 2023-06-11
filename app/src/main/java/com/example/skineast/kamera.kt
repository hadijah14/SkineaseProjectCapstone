package com.example.skineast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat


private var imageCapture: ImageCapture? = null
private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

