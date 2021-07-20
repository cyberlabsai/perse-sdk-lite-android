package ai.cyberlabs.perselite

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.assertj.core.api.Assertions
import org.junit.Test
import org.junit.runner.RunWith
import java.io.File
import java.io.FileOutputStream
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

@RunWith(AndroidJUnit4::class)
class ComparePathTest {

    private val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun test_with_same_human() {
        val perse = PerseLite(BuildConfig.API_KEY)
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val path = context.externalCacheDir.toString()
        val file = File(path, "test-perse-human-1".plus(".jpg"))

        val fileOutputStream = FileOutputStream(file)
        bitmap?.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            fileOutputStream
        )
        fileOutputStream.close()

        val lock = CountDownLatch(1)
        var similarity = 0f

        perse.face.compare(
            file.absolutePath,
            file.absolutePath,
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
        val basePath = context.externalCacheDir.toString()

        val firstHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val firstHumanFile = File(basePath, "test-perse-human-1".plus(".jpg"))
        val firstHumanFileOutputStream = FileOutputStream(firstHumanFile)
        firstHumanBitmap?.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            firstHumanFileOutputStream
        )
        firstHumanFileOutputStream.close()

        val secondHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.human)
        val secondHumanFile = File(basePath, "test-perse-human-2".plus(".jpg"))
        val secondHumanFileOutputStream = FileOutputStream(secondHumanFile)
        secondHumanBitmap?.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            secondHumanFileOutputStream
        )
        secondHumanFileOutputStream.close()

        val lock = CountDownLatch(1)
        var similarity = 0f

        perse.face.compare(
            firstHumanFile.absolutePath,
            secondHumanFile.absolutePath,
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
        val basePath = context.externalCacheDir.toString()

        val humanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.haroldo)
        val humanFile = File(basePath, "test-perse-human-1".plus(".jpg"))
        val humanFileOutputStream = FileOutputStream(humanFile)
        humanBitmap?.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            humanFileOutputStream
        )
        humanFileOutputStream.close()

        val nonHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.wall)
        val nonHumanFile = File(basePath, "test-perse-non-human".plus(".jpg"))
        val nonHumanFileOutputStream = FileOutputStream(nonHumanFile)
        nonHumanBitmap?.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            nonHumanFileOutputStream
        )
        nonHumanFileOutputStream.close()

        val lock = CountDownLatch(1)
        var error = ""

        perse.face.compare(
            humanFile.absolutePath,
            nonHumanFile.absolutePath,
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
        val basePath = context.externalCacheDir.toString()

        val nonHumanBitmap = BitmapFactory.decodeResource(context.resources, R.drawable.wall)
        val nonHumanFile = File(basePath, "test-perse-non-human".plus(".jpg"))
        val nonHumanFileOutputStream = FileOutputStream(nonHumanFile)
        nonHumanBitmap?.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            nonHumanFileOutputStream
        )
        nonHumanFileOutputStream.close()

        val lock = CountDownLatch(1)
        var error = ""

        perse.face.compare(
            nonHumanFile.absolutePath,
            nonHumanFile.absolutePath,
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
}