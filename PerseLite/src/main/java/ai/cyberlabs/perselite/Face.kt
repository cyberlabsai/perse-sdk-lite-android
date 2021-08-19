/**
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 * | Perse SDK Lite Android.                                         |
 * | More About: https://www.getperse.com/                           |
 * | From CyberLabs.AI: https://cyberlabs.ai/                        |
 * | Haroldo Teruya & Victor Goulart @ Cyberlabs AI 2021             |
 * +-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
 */

package ai.cyberlabs.perselite

import ai.cyberlabs.perselite.PerseLite.Companion.api
import ai.cyberlabs.perselite.PerseLite.Companion.perseAPIKey
import ai.cyberlabs.perselite.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * This class has the functions responsible to call API and retrieve the Response.Face.
 */
open class Face {

    val enrollment = Enrollment()

    /**
     * Send Image by ByteArray to API and return the DetectResponse Object.
     */
    fun detect(
        imageFile: ByteArray,
        onSuccess: (PerseAPIResponse.Face.Detect) -> Unit,
        onError: (String) -> Unit
    ) {
        api
            .detect(
                perseAPIKey,
                Util.fileToRequestBody(imageFile)
            )
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

    /**
     * Send Image by Image Path to API and return the DetectResponse Object.
     */
    fun detect(
        imagePath: String,
        onSuccess: (PerseAPIResponse.Face.Detect) -> Unit,
        onError: (String) -> Unit
    ) {
        api
            .detect(
                perseAPIKey,
                Util.fileToRequestBody(imagePath)
            )
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

    /**
     * Send two Images by ByteArray to API to return the CompareResponse of the API.
     */
    fun compare(
        imageFile1: ByteArray,
        imageFile2: ByteArray,
        onSuccess: (PerseAPIResponse.Face.Compare) -> Unit,
        onError: (String) -> Unit
    ) {
        api
            .compare(
                perseAPIKey,
                Util.fileToRequestBody(imageFile1),
                Util.fileToRequestBody(imageFile2)
            )
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

    /**
     * Send two Images by Image Path to API to return the CompareResponse of the API.
     */
    fun compare(
        imagePath1: String,
        imagePath2: String,
        onSuccess: (PerseAPIResponse.Face.Compare) -> Unit,
        onError: (String) -> Unit
    ) {
        api
            .compare(
                perseAPIKey,
                Util.fileToRequestBody(imagePath1),
                Util.fileToRequestBody(imagePath2)
            )
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