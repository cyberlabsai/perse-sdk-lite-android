package ai.cyberlabs.perselitedemo.fragment

import ai.cyberlabs.perselitedemo.MainActivity
import ai.cyberlabs.perselitedemo.R
import ai.cyberlabs.perselitedemo.adapter.DetectAdapter
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.detect_fragment.*
import java.io.ByteArrayOutputStream

class DetectFragment: Fragment() {

    private val activity by lazy { requireActivity() as MainActivity }
    private val adapter by lazy { DetectAdapter() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        choose_image.setOnClickListener { openGalleryForImage() }

        upload_button.setOnClickListener {
            this.activity.perseLite.face.detect(
                uriToByteArray(),
                { detectResponse ->
                    recycler_view.adapter = adapter
                    val layoutManager = LinearLayoutManager(requireContext())
                    layoutManager.orientation = RecyclerView.VERTICAL
                    recycler_view.layoutManager = layoutManager
                    adapter.setData(detectResponse)
                },
                { error ->
                    Log.d("PERSE_TEST", error)
                }
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.detect_fragment, container, false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK && requestCode == this.activity.GALLERY_REQUEST_CODE) {
            gallery_image.setImageURI(data?.data)
        }
    }

    private fun openGalleryForImage() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        startActivityForResult(intent, this.activity.GALLERY_REQUEST_CODE)
    }

    private fun uriToByteArray(): ByteArray {
        var bitmap = (gallery_image.drawable as BitmapDrawable).bitmap
        bitmap = this.activity.getResizedBitmap(bitmap, 300)

        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        return byteArrayOutputStream.toByteArray()
    }
}