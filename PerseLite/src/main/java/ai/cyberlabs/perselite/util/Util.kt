package ai.cyberlabs.perselite.util

import okhttp3.MediaType
import okhttp3.RequestBody
import java.io.File

class Util {
    companion object {
        fun fileToRequestBody(file: String): RequestBody {
            return RequestBody.create(
                MediaType.parse("image/jpeg"),
                File(file)
            )
        }

        fun fileToRequestBody(file: ByteArray): RequestBody {
            return RequestBody.create(
                MediaType.parse("image/jpeg"),
                file
            )
        }
    }
}