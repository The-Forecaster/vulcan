package me.austin.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import kotlinx.serialization.json.JsonObject
import me.austin.api.Vulcan
import java.io.BufferedWriter
import java.io.File
import java.io.IOException
import java.io.OutputStreamWriter
import java.nio.file.Files
import java.nio.file.Path

private val gson = GsonBuilder().setLenient().setPrettyPrinting().create()

// If this ever throws a memory error then you're fucked
@get:Throws(OutOfMemoryError::class)
inline val Path.readString: String
    get() = try {
        Files.readString(this) ?: ""
    } catch (e: Exception) {
        when (e) {
            is IOException, is SecurityException -> Vulcan.LOGGER.error("Couldn't read $this ${e.printStackTrace()}")
            else -> throw e
        }

        ""
    }

@Throws(
    IOException::class,
    IllegalArgumentException::class,
    UnsupportedOperationException::class,
    FileAlreadyExistsException::class,
    SecurityException::class
)
fun Path.writeToJson(element: JsonObject) {
    val writer = BufferedWriter(OutputStreamWriter(Files.newOutputStream(this)))

    writer.write(gson.toJson(element))
    writer.close()
}

@Throws(
    IOException::class,
    IllegalArgumentException::class,
    UnsupportedOperationException::class,
    FileAlreadyExistsException::class,
    SecurityException::class
)
fun File.writeToJson(element: JsonObject) = this.toPath().writeToJson(element)

fun Path.fromJson(clearIfException: Boolean = true) = try {
    gson.fromJson(this.readString, JsonObject::class.java) ?: JsonObject(mapOf())
} catch (e: JsonSyntaxException) {
    if (clearIfException) runCatching(Path::clearJson).onFailure(Throwable::printStackTrace)

    JsonObject(mapOf())
}

fun File.fromJson(clearIfException: Boolean = false) = this.toPath().fromJson(clearIfException)

/** Writes an empty JsonObject to the file */
@Throws(
    IOException::class,
    IllegalArgumentException::class,
    UnsupportedOperationException::class,
    FileAlreadyExistsException::class,
    SecurityException::class
)
fun Path.clearJson() = this.writeToJson(JsonObject(mapOf()))

@Throws(
    IOException::class,
    IllegalArgumentException::class,
    UnsupportedOperationException::class,
    FileAlreadyExistsException::class,
    SecurityException::class
)
fun File.clearJson() = this.toPath().clearJson()