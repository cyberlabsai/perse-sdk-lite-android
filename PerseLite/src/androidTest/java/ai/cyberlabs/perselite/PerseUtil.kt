package ai.cyberlabs.perselite

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import org.assertj.core.api.Assertions
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
    onSuccess: (PerseAPIResponse.Face.Detect) -> Unit,
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
    onSuccess: (PerseAPIResponse.Face.Detect) -> Unit,
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
    onSuccess: (PerseAPIResponse.Face.Compare) -> Unit,
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
    onSuccess: (PerseAPIResponse.Face.Compare) -> Unit,
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

fun faceCreate(
    context: Context,
    resource: Int,
    onSuccess: (PerseAPIResponse.Face.Enrollment.Create) -> Unit,
    onError: (String) -> Unit
) {
    val lock = CountDownLatch(1)
    val byteArray = getByteArray(context, resource)

    PerseLite(BuildConfig.API_KEY).face.enrollment.create(
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

fun faceRead(
    onSuccess: (PerseAPIResponse.Face.Enrollment.Read) -> Unit,
    onError: (String) -> Unit
) {
    val lock = CountDownLatch(1)

    PerseLite(BuildConfig.API_KEY)
        .face
        .enrollment
        .read(
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

fun faceUpdate(
    context: Context,
    resource: Int,
    userToken: String,
    onSuccess: (PerseAPIResponse.Face.Enrollment.Update) -> Unit,
    onError: (String) -> Unit
) {
    val lock = CountDownLatch(1)
    val byteArray = getByteArray(context, resource)

    PerseLite(BuildConfig.API_KEY).face.enrollment.update(
        userToken,
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

fun faceDelete(
    userToken: String,
    onSuccess: (PerseAPIResponse.Face.Enrollment.Delete) -> Unit,
    onError: (String) -> Unit
) {
    val lock = CountDownLatch(1)

    PerseLite(BuildConfig.API_KEY)
        .face
        .enrollment
        .delete(
            userToken,
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