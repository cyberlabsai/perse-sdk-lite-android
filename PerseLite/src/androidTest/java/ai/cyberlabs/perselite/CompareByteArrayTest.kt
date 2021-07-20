package ai.cyberlabs.perselite

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import java.io.ByteArrayOutputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class CompareByteArrayTest {

    private val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun test_with_same_human() {
        val perse = PerseLite(BuildConfig.API_KEY)
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val lock = CountDownLatch(1)
        var similarity = 0f

        perse.face.compare(
            byteArray,
            byteArray,
            {
                similarity = it.similarity
                lock.countDown()
            },
            {
                lock.countDown()
            }
        )
        lock.await(10000, TimeUnit.MILLISECONDS)
        Assertions.assertThat(similarity).isGreaterThan(80f)
    }

    @Test
    fun test_with_different_human() {
        val perse = PerseLite(BuildConfig.API_KEY)
        val firstHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val firstHumanByteArrayOutputStream = ByteArrayOutputStream()
        firstHumanBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, firstHumanByteArrayOutputStream)
        val firstHumanByteArray = firstHumanByteArrayOutputStream.toByteArray()

        val secondHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.human)
        val secondHumanByteArrayOutputStream = ByteArrayOutputStream()
        secondHumanBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, secondHumanByteArrayOutputStream)
        val secondHumanByteArray = secondHumanByteArrayOutputStream.toByteArray()

        val lock = CountDownLatch(1)
        var similarity = 0f

        perse.face.compare(
            firstHumanByteArray,
            secondHumanByteArray,
            {
                similarity = it.similarity
                lock.countDown()
            },
            {
                lock.countDown()
            }
        )
        lock.await(10000, TimeUnit.MILLISECONDS)
        Assertions.assertThat(similarity).isLessThan(20f)
    }

    @Test
    fun test_with_human_and_non_human() {
        val perse = PerseLite(BuildConfig.API_KEY)
        val humanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val humanByteArrayOutputStream = ByteArrayOutputStream()
        humanBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, humanByteArrayOutputStream)
        val humanByteArray = humanByteArrayOutputStream.toByteArray()

        val nonHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.wall)
        val nonHumanByteArrayOutputStream = ByteArrayOutputStream()
        nonHumanBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, nonHumanByteArrayOutputStream)
        val nonHumanByteArray = nonHumanByteArrayOutputStream.toByteArray()

        val lock = CountDownLatch(1)
        var error = ""

        perse.face.compare(
            humanByteArray,
            nonHumanByteArray,
            {
                lock.countDown()
            },
            {
                error = it
                lock.countDown()
            }
        )
        lock.await(10000, TimeUnit.MILLISECONDS)
        Assertions.assertThat(error).isEqualTo("HTTP 402 ")
    }

    @Test
    fun test_with_non_human() {
        val perse = PerseLite(BuildConfig.API_KEY)
        val nonHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.wall)
        val nonHumanByteArrayOutputStream = ByteArrayOutputStream()
        nonHumanBitmap?.compress(Bitmap.CompressFormat.JPEG, 100, nonHumanByteArrayOutputStream)
        val nonHumanByteArray = nonHumanByteArrayOutputStream.toByteArray()

        val lock = CountDownLatch(1)
        var error = ""

        perse.face.compare(
            nonHumanByteArray,
            nonHumanByteArray,
            {
                lock.countDown()
            },
            {
                error = it
                lock.countDown()
            }
        )
        lock.await(10000, TimeUnit.MILLISECONDS)
        Assertions.assertThat(error).isEqualTo("HTTP 402 ")
    }

    @Test
    fun test_with_invalid_api_key() {
        val perse = PerseLite("xxxxxx")
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        val lock = CountDownLatch(1)
        var error = ""

        perse.face.compare(
            byteArray,
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
        Assertions.assertThat(error).isEqualTo("HTTP 403 ")
    }
}