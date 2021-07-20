package ai.cyberlabs.perselite

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class DetectByteArrayTest {

    private val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun test_with_human_face() {
        val perse = PerseLite(BuildConfig.API_KEY)
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val lock = CountDownLatch(1)
        var totalFaces = 0

        perse.face.detect(
            byteArray,
            {
                totalFaces = it.totalFaces
                lock.countDown()
            },
            {
                totalFaces = -1
                lock.countDown()
            }
        )
        lock.await(10000, TimeUnit.MILLISECONDS)
        assertThat(totalFaces).isEqualTo(1)
    }

    @Test
    fun test_with_non_human_face() {
        val perse = PerseLite(BuildConfig.API_KEY)
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.wall)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val lock = CountDownLatch(1)
        var totalFaces = -1

        perse.face.detect(
            byteArray,
            {
                totalFaces = it.totalFaces
                lock.countDown()
            },
            {
                totalFaces = -1
                lock.countDown()
            }
        )
        lock.await(10000, TimeUnit.MILLISECONDS)
        assertThat(totalFaces).isEqualTo(0)
    }

    @Test
    fun test_with_invalid_api_key() {
        val perse = PerseLite("xxxxxxx")
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val lock = CountDownLatch(1)
        var error = ""

        perse.face.detect(
            byteArray,
            {
                lock.countDown()
            },
            {
                error = it
                lock.countDown()
            }
        )
        lock.await(10000, TimeUnit.MILLISECONDS)
        assertThat(error).isEqualTo("HTTP 403 ")
    }
}