package ai.cyberlabs.perselitedemo.fragment

import ai.cyberlabs.perselitedemo.MainActivity
import ai.cyberlabs.perselitedemo.R
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.compare_fragment.*
import java.io.ByteArrayOutputStream

class CompareFragment: Fragment() {

    private val activity by lazy { requireActivity() as MainActivity }

    var imageInputFlag = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        upload_image1.setOnClickListener {
            imageInputFlag = 0
            openGalleryForImageWithFlag("IMAGE_1")
        }

        upload_image2.setOnClickListener {
            imageInputFlag = 1
            openGalleryForImageWithFlag("IMAGE_2")
        }

        upload_button.setOnClickListener {
            var bitmap1 = (gallery_image1.drawable as BitmapDrawable).bitmap
            bitmap1 = activity.getResizedBitmap(bitmap1, 300)

            val byteArrayOutputStream1 = ByteArrayOutputStream()
            bitmap1?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream1)

            val byteArray1 = byteArrayOutputStream1.toByteArray()

            var bitmap2 = (gallery_image2.drawable as BitmapDrawable).bitmap
            bitmap2 = activity.getResizedBitmap(bitmap2, 300)

            val byteArrayOutputStream2 = ByteArrayOutputStream()
            bitmap2?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream2)

            val byteArray2 = byteArrayOutputStream2.toByteArray()

            activity.perseLite.face.compare(
                byteArray1,
                byteArray2,
                { compareResponse ->
                    similarity_result.text = compareResponse.similarity.toString()
                    time_taken_result.text = compareResponse.timeTaken.toString()
                },
                { Toast.makeText(
                    requireContext(),
                    "Failed to compare images",
                    Toast.LENGTH_LONG).show()
                }
            )
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.compare_fragment, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == activity.GALLERY_REQUEST_CODE) {
            if (imageInputFlag == 1) {
                gallery_image2.setImageURI(data?.data)
                return
            }
            gallery_image1.setImageURI(data?.data)
        }
    }

    private fun openGalleryForImageWithFlag(flag: String = "") {
        val intent = Intent(Intent.ACTION_PICK).putExtra("BUTTON_FLAG", flag)
        intent.type = "image/*"
        startActivityForResult(intent, activity.GALLERY_REQUEST_CODE)
    }
}