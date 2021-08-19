package ai.cyberlabs.perselite

import ai.cyberlabs.perselite.PerseLite.Companion.api
import ai.cyberlabs.perselite.PerseLite.Companion.perseAPIKey
import ai.cyberlabs.perselite.util.Util
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

open class Enrollment {

    fun create(
        imageFile: ByteArray,
        onSuccess: (PerseAPIResponse.Enrollment.Face.Create) -> Unit,
        onError: (String) -> Unit
    ) {
        api
            .create(
                perseAPIKey,
                Util.fileToRequestBody(imageFile)
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    onSuccess(response)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }

    fun create(
        imagePath: String,
        onSuccess: (PerseAPIResponse.Enrollment.Face.Create) -> Unit,
        onError: (String) -> Unit
    ) {
        api
            .create(
                perseAPIKey,
                Util.fileToRequestBody(imagePath)
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    onSuccess(response)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }

    fun read(
        onSuccess: (PerseAPIResponse.Enrollment.Face.Read) -> Unit,
        onError: (String) -> Unit
    ) {
        api.read(perseAPIKey)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    onSuccess(response)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }

    fun update(
        userToken: String,
        imageFile: ByteArray,
        onSuccess: (PerseAPIResponse.Enrollment.Face.Update) -> Unit,
        onError: (String) -> Unit
    ) {
        api
            .update(
                perseAPIKey,
                Util.fileToRequestBody(imageFile),
                userToken
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    onSuccess(response)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }

    fun update(
        userToken: String,
        imagePath: String,
        onSuccess: (PerseAPIResponse.Enrollment.Face.Update) -> Unit,
        onError: (String) -> Unit
    ) {
        api
            .update(
                perseAPIKey,
                Util.fileToRequestBody(imagePath),
                userToken
            )
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    onSuccess(response)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }

    fun delete(
        userToken: String,
        onSuccess: (PerseAPIResponse.Enrollment.Face.Delete) -> Unit,
        onError: (String) -> Unit
    ) {
        api.delete(perseAPIKey, userToken)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { response ->
                    onSuccess(response)
                },
                { error ->
                    error.message?.let(onError)
                }
            ).isDisposed
    }
}