package ai.cyberlabs.perselite

import ai.cyberlabs.perselite.model.CompareResponse
import ai.cyberlabs.perselite.model.DetectResponse
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.CountDownLatch

fun getFile(
    context: Context,
    name: String,
    resource: Int
): String {
    val bitmap = BitmapFactory.decodeResource(
        context.resources,
        resource
    )
    val path = context.externalCacheDir.toString()
    val file = File(path, name.plus(".jpg"))
    val fileOutputStream = FileOutputStream(file)

    bitmap?.compress(
        Bitmap.CompressFormat.JPEG,
        100,
        fileOutputStream
    )

    fileOutputStream.close()

    return file.absolutePath
}

fun getByteArray(
    context: Context,
    resource: Int
): ByteArray {
    val bitmap = BitmapFactory
        .decodeResource(
            context.resources,
            resource
        )
    val byteArrayOutputStream = ByteArrayOutputStream()
    bitmap?.compress(
        Bitmap.CompressFormat.JPEG,
        100,
        byteArrayOutputStream
    )
    return byteArrayOutputStream.toByteArray()
}

fun detectWithFile(
    context: Context,
    resource: Int,
    apiKey: String,
    onSuccess: (DetectResponse) -> Unit,
    onError: (String) -> Unit
) {
    val lock = CountDownLatch(1)
    val file = getFile(
        context,
        "perse-image",
        resource
    )

    PerseLite(apiKey).face.detect(
        file,
        {
            onSuccess(it)
            lock.countDown()
        },
        {
            onError(it)
            lock.countDown()
        }
    )
    lock.await()
}

fun detectWithByteArray(
    context: Context,
    resource: Int,
    apiKey: String,
    onSuccess: (DetectResponse) -> Unit,
    onError: (String) -> Unit
) {
    val lock = CountDownLatch(1)
    val byteArray = getByteArray(context, resource)

    PerseLite(apiKey).face.detect(
        byteArray,
        {
            onSuccess(it)
            lock.countDown()
        },
        {
            onError(it)
            lock.countDown()
        }
    )
    lock.await()
}

fun compareWithFile(
    context: Context,
    resource1: Int,
    resource2: Int,
    apiKey: String,
    onSuccess: (CompareResponse) -> Unit,
    onError: (String) -> Unit
) {
    val lock = CountDownLatch(1)
    val filePath1 = getFile(context, "perse-image-1", resource1)
    val filePath2 = getFile(context, "perse-image-2", resource2)

    PerseLite(apiKey).face.compare(
        filePath1,
        filePath2,
        {
            onSuccess(it)
            lock.countDown()
        },
        {
            onError(it)
            lock.countDown()
        }
    )
    lock.await()
}

fun compareWithByteArray(
    context: Context,
    resource1: Int,
    resource2: Int,
    apiKey: String,
    onSuccess: (CompareResponse) -> Unit,
    onError: (String) -> Unit
) {
    val lock = CountDownLatch(1)
    val byteArray1 = getByteArray(context, resource1)
    val byteArray2 = getByteArray(context, resource2)

    PerseLite(apiKey).face.compare(
        byteArray1,
        byteArray2,
        {
            onSuccess(it)
            lock.countDown()
        },
        {
            onError(it)
            lock.countDown()
        }
    )
    lock.await()
}