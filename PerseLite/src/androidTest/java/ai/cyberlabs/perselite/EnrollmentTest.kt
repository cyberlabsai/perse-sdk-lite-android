package ai.cyberlabs.perselite

import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import org.junit.runner.RunWith
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.Assert

@RunWith(AndroidJUnit4::class)
class EnrollmentTest {

    private val context = InstrumentationRegistry
        .getInstrumentation()
        .context

    @Test
    fun test_enrollment_face_create_and_delete() {
        var userToken: String? = null

        faceCreate(
            this.context,
            R.drawable.human_1,
            { createResponse ->
                println("Created user token: " + createResponse.userToken)
                userToken = createResponse.userToken
            },
            {
                println(it)
                Assert.assertTrue(false)
            }
        )

        faceRead(
            { readResponse ->
                println("Read user tokens: " + readResponse.totalUsers)
            },
            {
                println(it)
                Assert.assertTrue(false)
            }
        )

        if (userToken !== null) {
            faceDelete(
                userToken!!,
                { deleteResponse ->
                    println("Delete status : " + deleteResponse.status)
                    Assert.assertTrue(deleteResponse.status == 200)
                },
                {
                    Assert.assertTrue(false)
                }
            )
        } else {
            Assert.assertTrue(false)
        }
    }
}