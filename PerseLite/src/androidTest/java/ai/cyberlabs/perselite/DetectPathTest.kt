package ai.cyberlabs.perselite

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetectPathTest {

    private val context = InstrumentationRegistry
        .getInstrumentation()
        .context

    @Test
    fun test_with_human_face() {
        detectWithFile(
            this.context,
            R.drawable.human_2,
            BuildConfig.API_KEY,
            { assertThat(it.totalFaces).isEqualTo(1) },
            { Assert.assertTrue(false) }
        )
    }

    @Test
    fun test_with_non_human_face() {
        detectWithFile(
            this.context,
            R.drawable.wall,
            BuildConfig.API_KEY,
            { assertThat(it.totalFaces).isEqualTo(0) },
            { Assert.assertTrue(false) }
        )
    }
}