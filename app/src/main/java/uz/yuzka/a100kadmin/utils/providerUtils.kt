package uz.yuzka.a100kadmin.utils

import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.provider.DocumentsContract
import android.provider.MediaStore
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import com.google.gson.Gson
import uz.yuzka.a100kadmin.app.App.Companion.instance
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.lang.Integer.max
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.absoluteValue

fun <A> String.fromJson(type: Class<A>): A {
    return Gson().fromJson(this, type)
}

fun <A> A.toJson(): String? {
    return Gson().toJson(this)
}

fun Uri.asFile(): File? {
    instance.contentResolver
        .query(this, arrayOf(MediaStore.Images.Media.DATA), null, null, null)
        ?.use { cursor ->
            cursor.moveToFirst()
            val cursorData =
                cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))

            return if (cursorData == null) {
                returnCursorData(this)?.let { File(it) }
            } else {
                File(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)))
            }
        }
    return null
}

private fun returnCursorData(uri: Uri?): String? {
    val context = instance

    if (DocumentsContract.isDocumentUri(context, uri)) {
        val wholeID = DocumentsContract.getDocumentId(uri)
        val splits = wholeID.split(":".toRegex()).toTypedArray()
        if (splits.size == 2) {
            val id = splits[1]
            val column = arrayOf(MediaStore.Images.Media.DATA)
            val sel = MediaStore.Images.Media._ID + "=?"

            val cursor: Cursor? = context.contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                column, sel, arrayOf(id), null
            )

            val columnIndex: Int? = cursor?.getColumnIndex(column[0])
            if (cursor?.moveToFirst() == true) {
                return columnIndex?.let { cursor.getString(it) }
            }
            cursor?.close()
        }
    } else {
        return uri?.path
    }
    return null
}


fun getCachedFile(path: String?, context: Context, id: Int = 0): File {
    val timeStamp =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

    val file = File(context.cacheDir, "JPEG${timeStamp}$id.jpg")

    val inputStream =
        FileInputStream(
            (FileUtilsIPaul.getPath(context, Uri.parse(path)) ?: path)?.let { File(it) })
    val outputStream = FileOutputStream(file)

    inputStream.copyTo(outputStream)

    return file
}

fun getCachedVideoFile(path: String?, context: Context): File {
    val timeStamp =
        SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())

    val file = File(context.cacheDir, "VID${timeStamp}.mp4")

    val inputStream =
        FileInputStream(
            (FileUtilsIPaul.getPath(context, Uri.parse(path)) ?: path)?.let { File(it) })
    val outputStream = FileOutputStream(file)

    inputStream.copyTo(outputStream)

    return file
}

private val SaveMap = mutableMapOf<String, KeyParams>()

private data class KeyParams(
    val params: String = "",
    val index: Int,
    val scrollOffset: Int
)

/**
 * Save scroll state on all time.
 * @param key value for comparing screen
 * @param params arguments for find different between equals screen
 * @param initialFirstVisibleItemIndex see [LazyListState.firstVisibleItemIndex]
 * @param initialFirstVisibleItemScrollOffset see [LazyListState.firstVisibleItemScrollOffset]
 */
@Composable
fun rememberForeverLazyListState(
    key: String,
    params: String = "",
    initialFirstVisibleItemIndex: Int = 0,
    initialFirstVisibleItemScrollOffset: Int = 0
): LazyListState {
    val scrollState = rememberSaveable(saver = LazyListState.Saver) {
        var savedValue = SaveMap[key]
        if (savedValue?.params != params) savedValue = null
        val savedIndex = savedValue?.index ?: initialFirstVisibleItemIndex
        val savedOffset = savedValue?.scrollOffset ?: initialFirstVisibleItemScrollOffset
        LazyListState(
            savedIndex,
            savedOffset
        )
    }
    DisposableEffect(Unit) {
        onDispose {
            val lastIndex = scrollState.firstVisibleItemIndex
            val lastOffset = scrollState.firstVisibleItemScrollOffset
            SaveMap[key] = KeyParams(params, lastIndex, lastOffset)
        }
    }
    return scrollState
}

class MaskVisualTransformation(private val mask: String) : VisualTransformation {

    private val specialSymbolsIndices = mask.indices.filter { mask[it] != '#' }

    override fun filter(text: AnnotatedString): TransformedText {
        var out = ""
        var maskIndex = 0
        text.forEach { char ->
            while (specialSymbolsIndices.contains(maskIndex)) {
                out += mask[maskIndex]
                maskIndex++
            }
            out += char
            maskIndex++
        }
        return TransformedText(AnnotatedString(out), offsetTranslator())
    }

    private fun offsetTranslator() = object : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int {
            val offsetValue = offset.absoluteValue
            if (offsetValue == 0) return 0
            var numberOfHashtags = 0
            val masked = mask.takeWhile {
                if (it == '#') numberOfHashtags++
                numberOfHashtags < offsetValue
            }
            return masked.length + 1
        }

        override fun transformedToOriginal(offset: Int): Int {
            return mask.take(offset.absoluteValue).count { it == '#' }
        }
    }
}


class CurrencyAmountInputVisualTransformation(
    private val fixedCursorAtTheEnd: Boolean = true,
    private val numberOfDecimals: Int = 0
) : VisualTransformation {

    private val symbols = DecimalFormat().decimalFormatSymbols

    override fun filter(text: AnnotatedString): TransformedText {
        val thousandsSeparator = symbols.groupingSeparator
//        val decimalSeparator = symbols.decimalSeparator
        val zero = symbols.zeroDigit

        val inputText = text.text

        val intPart = inputText
            .dropLast(numberOfDecimals)
            .reversed()
            .chunked(3)
            .joinToString(thousandsSeparator.toString())
            .reversed()
            .ifEmpty {
                zero.toString()
            }

//        val fractionPart = inputText.takeLast(numberOfDecimals).let {
//            if (it.length != numberOfDecimals) {
//                List(numberOfDecimals - it.length) {
//                    zero
//                }.joinToString("") + it
//            } else {
//                it
//            }
//        }

        val newText = AnnotatedString(
            text = intPart,
            spanStyles = text.spanStyles,
            paragraphStyles = text.paragraphStyles
        )

        val offsetMapping = if (fixedCursorAtTheEnd) {
            FixedCursorOffsetMapping(
                contentLength = inputText.length,
                formattedContentLength = intPart.length
            )
        } else {
            MovableCursorOffsetMapping(
                unmaskedText = text.toString(),
                maskedText = newText.toString(),
                decimalDigits = numberOfDecimals
            )
        }

        return TransformedText(newText, offsetMapping)
    }

    private class FixedCursorOffsetMapping(
        private val contentLength: Int,
        private val formattedContentLength: Int,
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int = formattedContentLength
        override fun transformedToOriginal(offset: Int): Int = contentLength
    }

    private class MovableCursorOffsetMapping(
        private val unmaskedText: String,
        private val maskedText: String,
        private val decimalDigits: Int
    ) : OffsetMapping {
        override fun originalToTransformed(offset: Int): Int =
            when {
                unmaskedText.length <= decimalDigits -> {
                    maskedText.length - (unmaskedText.length - offset)
                }

                else -> {
                    offset + offsetMaskCount(offset, maskedText)
                }
            }

        override fun transformedToOriginal(offset: Int): Int =
            when {
                unmaskedText.length <= decimalDigits -> {
                    max(unmaskedText.length - (maskedText.length - offset), 0)
                }

                else -> {
                    offset - maskedText.take(offset).count { !it.isDigit() }
                }
            }

        private fun offsetMaskCount(offset: Int, maskedText: String): Int {
            var maskOffsetCount = 0
            var dataCount = 0
            for (maskChar in maskedText) {
                if (!maskChar.isDigit()) {
                    maskOffsetCount++
                } else if (++dataCount > offset) {
                    break
                }
            }
            return maskOffsetCount
        }
    }
}

fun getRoundedPrice(price: Long): String {
    return if (price >= 1000000000 || price <= -1000000000) String.format(
        "%.2f",
        price * 1f / 1e9
    ) + ".mlrd"
    else if (price >= 1000000 || price <= -1000000) String.format("%.2f", price * 1f / 1e6) + ".mln"
    else if (price >= 1000 || price <= -1000) "${(price / 1000)}.K"
    else "$price UZS"
}

fun Modifier.dashedBorder(strokeWidth: Dp, color: Color, cornerRadiusDp: Dp) = composed(
    factory = {
        val density = LocalDensity.current
        val strokeWidthPx = density.run { strokeWidth.toPx() }
        val cornerRadiusPx = density.run { cornerRadiusDp.toPx() }

        this.then(
            Modifier.drawWithCache {
                onDrawBehind {
                    val stroke = Stroke(
                        width = strokeWidthPx,
                        pathEffect = PathEffect.dashPathEffect(floatArrayOf(10f, 10f), 0f)
                    )

                    drawRoundRect(
                        color = color,
                        style = stroke,
                        cornerRadius = CornerRadius(cornerRadiusPx)
                    )
                }
            }
        )
    }
)