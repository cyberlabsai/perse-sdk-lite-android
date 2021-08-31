package ai.cyberlabs.perselite

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import org.assertj.core.api.Assertions.assertThat
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ComparePathTest {

    private val context = InstrumentationRegistry
        .getInstrumentation()
        .context

    @Test
    fun test_with_same_human() {
        compareWithFile(
            this.context,
            R.drawable.human_1,
            R.drawable.human_2,
            BuildConfig.API_KEY,
            {
                val similarity = it.similarity
                val defaultSimilarity = it.defaultThresholds.similarity

                assertThat(similarity)
                    .isGreaterThan(defaultSimilarity)
            },
            { assertThat(false) }
        )
    }

    @Test
    fun test_with_different_human() {
        compareWithFile(
            this.context,
            R.drawable.human_1,
            R.drawable.wall,
            BuildConfig.API_KEY,
            {
                val similarity = it.similarity
                val defaultSimilarity = it.defaultThresholds.similarity

                assertThat(similarity)
                    .isLessThan(defaultSimilarity)
            },
            { assertThat(false) }
        )
    }

    @Test
    fun test_with_human_and_non_human() {
        compareWithFile(
            this.context,
            R.drawable.human_1,
            R.drawable.wall,
            BuildConfig.API_KEY,
            { assertThat(false) },
            {
                assertThat(it).isEqualTo("HTTP 402 ")
            }
        )
    }

    @Test
    fun test_with_non_human() {
        compareWithFile(
            this.context,
            R.drawable.dog,
            R.drawable.wall,
            BuildConfig.API_KEY,
            { assertThat(false) },
            {
                assertThat(it).isEqualTo("HTTP 402 ")
            }
        )
    }
}