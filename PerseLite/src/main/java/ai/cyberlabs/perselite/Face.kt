package ai.cyberlabs.perselite

import ai.cyberlabs.perselite.PerseLite.Companion.apiInstance
import ai.cyberlabs.perselite.PerseLite.Companion.perseAPIKey
import ai.cyberlabs.perselite.model.CompareResponse
import ai.cyberlabs.perselite.model.DetectResponse
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class Face {

    fun detect(
        imageFile: ByteArray,
        onSuccess: (DetectResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageFile)
        apiInstance.detect(perseAPIKey, requestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { detectResponse ->
                    onSuccess(detectResponse)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }

    fun detect(
        imagePath: String,
        onSuccess: (DetectResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val imageFile = File(imagePath)
        val requestBody = RequestBody.create(MediaType.parse("image/jpeg"), imageFile)
        apiInstance.detect(perseAPIKey, requestBody)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { detectResponse ->
                    onSuccess(detectResponse)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }

    fun compare(
        imageFile1: ByteArray,
        imageFile2: ByteArray,
        onSuccess: (CompareResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val requestBody1 = RequestBody.create(MediaType.parse("image/jpeg"), imageFile1)
        val requestBody2 = RequestBody.create(MediaType.parse("image/jpeg"), imageFile2)
        apiInstance.compare(perseAPIKey, requestBody1, requestBody2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { compareResponse ->
                    onSuccess(compareResponse)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }

    fun compare(
        imagePath1: String,
        imagePath2: String,
        onSuccess: (CompareResponse) -> Unit,
        onError: (String) -> Unit
    ) {
        val imageFile1 = File(imagePath1)
        val imageFile2 = File(imagePath2)
        val requestBody1 = RequestBody.create(MediaType.parse("image/jpeg"), imageFile1)
        val requestBody2 = RequestBody.create(MediaType.parse("image/jpeg"), imageFile2)
        apiInstance.compare(perseAPIKey, requestBody1, requestBody2)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { compareResponse ->
                    onSuccess(compareResponse)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }
}