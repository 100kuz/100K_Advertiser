package uz.yuzka.admin.utils

import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.View
import android.webkit.MimeTypeMap
import androidx.compose.foundation.interaction.InteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import kotlinx.coroutines.delay
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.io.OutputStream
import java.io.UnsupportedEncodingException
import java.nio.charset.Charset
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object StringUtils {
    /**
     * string to byte[]
     */
    fun strTobytes(str: String): ByteArray? {
        var b: ByteArray? = null
        var data: ByteArray? = null
        try {
            b = str.toByteArray(charset("utf-8"))
            data = String(b, Charset.forName("utf-8")).toByteArray(charset("gbk"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
        }
        return data
    }

    /**
     * byte[] merger
     */
    fun byteMerger(byte_1: ByteArray, byte_2: ByteArray): ByteArray {
        val byte_3 = ByteArray(byte_1.size + byte_2.size)
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.size)
        System.arraycopy(byte_2, 0, byte_3, byte_1.size, byte_2.size)
        return byte_3
    }

    fun strTobytes(str: String, charset: String?): ByteArray? {
        var b: ByteArray? = null
        var data: ByteArray? = null
        try {
            b = str.toByteArray(charset("utf-8"))
            data = String(b, Charset.forName("utf-8")).toByteArray(charset(charset!!))
        } catch (e: UnsupportedEncodingException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
        }
        return data
    }
}

fun View.gone(): View {
    visibility = View.GONE
    return this
}

fun View.invisible(): View {
    visibility = View.INVISIBLE
    return this
}

fun View.visible(): View {
    visibility = View.VISIBLE
    return this
}


fun fileFromUri(context: Context, uri: Uri, mimeType: String = "jpg"): File? {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

    val tmpFile = context.getExternalFilesDir(Environment.DIRECTORY_PICTURES)?.absolutePath?.let {
        File(
            it,
            "$mimeType${timeStamp}.$mimeType",
        )
    }

    val input: InputStream? = context.contentResolver.openInputStream(uri)
    val out: OutputStream = FileOutputStream(tmpFile)
    val buf = ByteArray(1024)
    var len: Int
    if (input != null) while (input.read(buf).also { len = it } > 0) {
        out.write(buf, 0, len)
    }
    out.close()
    input?.close()

    return tmpFile
}


fun getMimeType(context: Context, uri: Uri): String {
    //Check uri format to avoid null
    val extension: String = if (uri.scheme == ContentResolver.SCHEME_CONTENT) {
        //If scheme is a content
        val mime = MimeTypeMap.getSingleton()
        mime.getExtensionFromMimeType(context.contentResolver.getType(uri)) ?: "jpg"
    } else {
        //If scheme is a File
        //This will replace white spaces with %20 and also other special characters. This will avoid returning null values on file name with spaces and special characters.
        MimeTypeMap.getFileExtensionFromUrl(Uri.fromFile(File(uri.path)).toString())
    }
    return extension
}


@Composable
fun InteractionSource.collectClickAsState(): State<Boolean> {
    val onClick = remember { mutableStateOf(false) }
    LaunchedEffect(this) {
        var wasPressed = false
        interactions.collect { interaction ->
            when (interaction) {
                is PressInteraction.Press -> {
                    wasPressed = true
                }
                is PressInteraction.Release -> {
                    if (wasPressed) onClick.value = true
                    wasPressed = false
                }
                is PressInteraction.Cancel -> {
                    wasPressed = false
                }
            }
            // reset state with some delay otherwise it can re-emit the previous state
            delay(50L)
            onClick.value = false
        }
    }
    return onClick
}