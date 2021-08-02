package ai.cyberlabs.perselitedemo.fragment

import ai.cyberlabs.perselitedemo.MainActivity
import ai.cyberlabs.perselitedemo.R
import ai.cyberlabs.yoonit.camera.CameraView
import ai.cyberlabs.yoonit.camera.interfaces.CameraEventListener
import android.Manifest
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.util.Pair
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.camera_fragment.*
import java.io.File

class CameraFragment: Fragment() {

    private val activity by lazy { requireActivity() as MainActivity }

    private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)

    private lateinit var cameraView: CameraView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        buildCameraView()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.camera_fragment, container, false)
    }

    override fun onPause() {
        super.onPause()
        cameraView.destroy()
    }

    override fun onResume() {
        super.onResume()
        buildCameraView()
    }

    private fun buildCameraView() {
        cameraView = camera_view
        cameraView.setCameraEventListener(buildCameraEventListener())

        if (allPermissionsGranted()) {
            cameraView.startPreview()
            cameraView.startCaptureType("face")
            cameraView.detectionTopSize = 0.2f
            cameraView.detectionRightSize = 0.2f
            cameraView.detectionBottomSize = 0.2f
            cameraView.detectionLeftSize = 0.2f
            cameraView.setSaveImageCaptured(true)
            cameraView.setDetectionBox(true)
            cameraView.setFaceContours(true)
            cameraView.setSaveImageCaptured(true)
            return
        }

        requestPermissions(
            REQUIRED_PERMISSIONS,
            PackageManager.PERMISSION_GRANTED
        )
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(
            activity, it
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PackageManager.PERMISSION_GRANTED) {
            if (allPermissionsGranted()) {
                cameraView.startPreview()
                cameraView.startCaptureType("face")
                cameraView.setSaveImageCaptured(true)
            } else {
                Toast.makeText(
                    activity,
                    "Permissions not granted by the user.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun buildCameraEventListener(): CameraEventListener = object : CameraEventListener {
        override fun onEndCapture() {
            Log.d("OnEndCapture", "OnEndCapture")
        }

        override fun onError(error: String) {
            Log.d("OnError", error)
        }

        override fun onFaceDetected(
            x: Int,
            y: Int,
            width: Int,
            height: Int,
            leftEyeOpenProbability: Float?,
            rightEyeOpenProbability: Float?,
            smilingProbability: Float?,
            headEulerAngleX: Float,
            headEulerAngleY: Float,
            headEulerAngleZ: Float
        ) {
            leftEyeOpenProbability?.let {
                left_eye_tv.text = getString(R.string.left_eye_open_probability, it.times(100).toString().substring(0,4))
                if (it > 0.8) {
                    left_eye_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check,0,0,0)
                    return@let
                }
                left_eye_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_off,0,0,0)
            }
            rightEyeOpenProbability?.let {
                right_eye_tv.text = getString(R.string.right_eye_open_probability, it.times(100).toString().substring(0,4))
                if (it > 0.8) {
                    right_eye_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check,0,0,0)
                    return@let
                }
                right_eye_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_off,0,0,0)
            }
            smilingProbability?.let {
                smiling_tv.text = getString(R.string.smiling_probability, it.times(100).toString().substring(0,4))
                if (it > 0.8) {
                    smiling_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check,0,0,0)
                    return@let
                }
                smiling_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_off,0,0,0)
            }
            setFaceHorizontalMovement(headEulerAngleY)
            setFaceVerticalMovement(headEulerAngleX)
            setFaceTiltMovement(headEulerAngleZ)
        }

        override fun onFaceUndetected() {
            cameraView.setDetectionBoxColor(0,0,0,0)
            cameraView.setFaceContoursColor(0,0,0,0)
            left_eye_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_neutral,0,0,0)
            right_eye_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_neutral,0,0,0)
            smiling_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_neutral,0,0,0)
            image_sharpness_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_neutral,0,0,0)
            image_underexpose_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_neutral,0,0,0)
            face_sharpness_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_neutral,0,0,0)
            face_underexpose_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_neutral,0,0,0)
            left_eye_tv.text = getString(R.string.left_eye_open)
            right_eye_tv.text = getString(R.string.right_eye_open)
            smiling_tv.text = getString(R.string.smiling)
            image_sharpness_tv.text = getString(R.string.image_sharpness)
            image_underexpose_tv.text = getString(R.string.image_underexpose)
            face_sharpness_tv.text = getString(R.string.face_sharpness)
            face_underexpose_tv.text = getString(R.string.face_underexpose)
            horizontal_movement_tv.text = getString(R.string.horizontal_movement)
            vertical_movement_tv.text = getString(R.string.vertical_movement)
            tilt_movement_tv.text = getString(R.string.tilt_movement)
            image_preview.visibility = View.GONE
        }

        override fun onImageCaptured(
            type: String,
            count: Int,
            total: Int,
            imagePath: String,
            inferences: ArrayList<Pair<String, FloatArray>>,
            darkness: Double,
            lightness: Double,
            sharpness: Double
        ) {
            activity.perseLite.face.detect(
                imagePath,
                {
                    setImageUnderexpose(it.imageMetrics.underexposure)
                    setImageSharpness(it.imageMetrics.sharpness)
                    setFaceUnderexpose(it.faces.first().faceMetrics.underexposure)
                    setFaceSharpness(it.faces.first().faceMetrics.sharpness)

                    if (it.faces.first().livenessScore > 0.8) {
                        cameraView.setDetectionBoxColor(255,0,255,0)
                        cameraView.setFaceContoursColor(255,0,255,0)
                        return@detect
                    }
                    cameraView.setDetectionBoxColor(255,255,0,0)
                    cameraView.setFaceContoursColor(255,255,0,0)
                },
                {
                    Log.d("PerseCamera error", it)
                }
            )

            image_preview.visibility = View.VISIBLE
            val imageFile = File(imagePath)
            if (imageFile.exists()) {
                val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
                image_preview.setImageBitmap(imageBitmap)
            }
        }

        override fun onMessage(message: String) {
            Log.d("OnEndCapture", message)
        }

        override fun onPermissionDenied() {
            Log.d("OnPermissionDenied", "onPermissionDenied")
        }

        override fun onQRCodeScanned(content: String) {
            Log.d("OnQRCodeScanned", content)
        }
    }

    private fun setImageSharpness(sharpness: Float) {
        image_sharpness_tv.text =
            getString(
                R.string.image_sharpness_probability,
                sharpness.times(100).toString().substring(0,4)
            )
        if (sharpness < 0.5) {
            image_sharpness_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check,0,0,0)
            return
        }
        image_sharpness_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_off,0,0,0)
    }

    private fun setImageUnderexpose(underexpose: Float) {
        image_underexpose_tv.text =
            getString(
                R.string.image_underexpose_probability,
                underexpose.times(100).toString().substring(0,4)
            )
        if (underexpose < 0.5) {
            image_underexpose_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check,0,0,0)
            return
        }
        image_underexpose_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_off,0,0,0)
    }

    private fun setFaceSharpness(sharpness: Float) {
        face_sharpness_tv.text =
            getString(
                R.string.face_sharpness_probability,
                sharpness.times(100).toString().substring(0,4)
            )
        if (sharpness < 0.5) {
            face_sharpness_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check,0,0,0)
            return
        }
        face_sharpness_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_off,0,0,0)
    }

    private fun setFaceUnderexpose(underexpose: Float) {
        face_underexpose_tv.text =
            getString(
                R.string.face_underexpose_probability,
                underexpose.times(100).toString().substring(0,4)
            )
        if (underexpose < 0.5) {
            face_underexpose_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_check,0,0,0)
            return
        }
        face_underexpose_tv.setCompoundDrawablesRelativeWithIntrinsicBounds(R.drawable.ic_off,0,0,0)
    }

    private fun setFaceHorizontalMovement(headAngleY: Float) {
        when {
            headAngleY < -36 -> {
                horizontal_movement_tv.text =
                    getString(
                        R.string.horizontal_movement_probability,
                        "Super Left"
                    )
            }
            headAngleY > -36 && headAngleY < -12 -> {
                horizontal_movement_tv.text =
                    getString(
                        R.string.horizontal_movement_probability,
                        "Left"
                    )
            }
            headAngleY > -12 && headAngleY < 12 -> {
                horizontal_movement_tv.text =
                    getString(
                        R.string.horizontal_movement_probability,
                        "Frontal"
                    )
            }
            headAngleY > 12 && headAngleY < 36 -> {
                horizontal_movement_tv.text =
                    getString(
                        R.string.horizontal_movement_probability,
                        "Right"
                    )
            }
            headAngleY > 36 -> {
                horizontal_movement_tv.text =
                    getString(
                        R.string.horizontal_movement_probability,
                        "Super Right"
                    )
            }
        }
    }

    private fun setFaceVerticalMovement(headAngleX: Float) {
        when {
            headAngleX < -36 -> {
                vertical_movement_tv.text =
                    getString(
                        R.string.vertical_movement_probability,
                        "Super Down"
                    )
            }
            headAngleX > -36 && headAngleX < -12 -> {
                vertical_movement_tv.text =
                    getString(
                        R.string.vertical_movement_probability,
                        "Down"
                    )
            }
            headAngleX > -12 && headAngleX < 12 -> {
                vertical_movement_tv.text =
                    getString(
                        R.string.vertical_movement_probability,
                        "Frontal"
                    )
            }
            headAngleX > 12 && headAngleX < 36 -> {
                vertical_movement_tv.text =
                    getString(
                        R.string.vertical_movement_probability,
                        "Up"
                    )
            }
            headAngleX > 36 -> {
                vertical_movement_tv.text =
                    getString(
                        R.string.vertical_movement_probability,
                        "Super Up"
                    )
            }
        }
    }

    private fun setFaceTiltMovement(headAngleZ: Float) {
        when {
            headAngleZ < -36 -> {
                tilt_movement_tv.text =
                    getString(
                        R.string.tilt_movement_probability,
                        "Super Right"
                    )
            }
            headAngleZ > -36 && headAngleZ < -12 -> {
                tilt_movement_tv.text =
                    getString(
                        R.string.tilt_movement_probability,
                        "Right"
                    )
            }
            headAngleZ > -12 && headAngleZ < 12 -> {
                tilt_movement_tv.text =
                    getString(
                        R.string.tilt_movement_probability,
                        "Frontal"
                    )
            }
            headAngleZ > 12 && headAngleZ < 36 -> {
                tilt_movement_tv.text =
                    getString(
                        R.string.tilt_movement_probability,
                        "Left"
                    )
            }
            headAngleZ > 36 -> {
                tilt_movement_tv.text = getString(
                    R.string.tilt_movement_probability,
                    "Super Left"
                )
            }
        }
    }
}