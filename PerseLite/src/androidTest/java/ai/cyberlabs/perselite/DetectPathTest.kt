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
class DetectPathTest {

    private val context = InstrumentationRegistry.getInstrumentation().context

    @Test
    fun test_with_human_face() {
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
        var totalFaces = 0

        perse.face.detect(
            file.absolutePath,
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
        Assertions.assertThat(totalFaces).isEqualTo(1)
    }

    @Test
    fun test_with_non_human_face() {
        val perse = PerseLite(BuildConfig.API_KEY)
        val bitmap = BitmapFactory.decodeResource(context.resources, R.drawable.wall)
        val path = context.externalCacheDir.toString()
        val file = File(path, "test-perse-non-human".plus(".jpg"))

        val fileOutputStream = FileOutputStream(file)

        bitmap?.compress(
            Bitmap.CompressFormat.JPEG,
            100,
            fileOutputStream
        )

        fileOutputStream.close()

        val lock = CountDownLatch(1)
        var totalFaces = -1

        perse.face.detect(
            file.absolutePath,
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
        Assertions.assertThat(totalFaces).isEqualTo(0)
    }
}