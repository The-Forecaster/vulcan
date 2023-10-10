package me.austin.util

import com.google.gson.GsonBuilder
import com.google.gson.JsonSyntaxException
import kotlinx.serialization.json.Json
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
/**
 * A string of all content within the file represented by the path.
 *
 * @throws OutOfMemoryError hardware issue, unrecoverable.
 */
@get:Throws(OutOfMemoryError::class)
inline val Path.readString: String
    get() {
        return try {
            Files.readString(this) ?: ""
        } catch (e: Exception) {
            when (e) {
                is IOException, is SecurityException -> Vulcan.LOGGER.error("Couldn't read $this\n${e.printStackTrace()}")
                else -> throw e
            }

            ""
        }
    }

/**
 * Writes to the json represented by this path.
 *
 * @receiver Path to the json.
 *
 * @return
 */
@Throws(IOException::class)
fun Path.writeToJson(element: JsonObject) {
    val writer = BufferedWriter(OutputStreamWriter(Files.newOutputStream(this)))

    writer.write(gson.toJson(element))
    writer.close()
}

@Throws(IOException::class)
fun File.writeToJson(element: JsonObject) {
    this.toPath().writeToJson(element)
}

/**
 * Returns a [JsonObject] from this path.
 *
 * @receiver Path to the json file.
 *
 * @return [JsonObject] generated from the file.
 */
fun Path.fromJson(clearIfException: Boolean = true): JsonObject {
    return try {
        Json.Default.decodeFromString<JsonObject>(this.readString)

        gson.fromJson(this.readString, JsonObject::class.java) ?: JsonObject(mapOf())
    } catch (e: JsonSyntaxException) {
        if (clearIfException) {
            this.runCatching {
                this.clearJson()
            }.onFailure {
                it.printStackTrace()
            }
        }

        JsonObject(mapOf())
    }
}

/**
 * Returns a [JsonObject] from this path.
 *
 * @receiver The json file.
 *
 * @return [JsonObject] generated from the file.
 */
fun File.fromJson(clearIfException: Boolean = false): JsonObject {
    return this.toPath().fromJson(clearIfException)
}

/**
 * Writes an empty [JsonObject] to the file.
 *
 * @throws IOException If the IO system is interrupted or too busy.
 *
 * @receiver Path of the file that you want to be cleared.
 */
@Throws(IOException::class)
fun Path.clearJson() {
    this.writeToJson(JsonObject(mapOf()))
}

/**
 * Writes an empty [JsonObject] to the file.
 *
 * @receiver File that you want to be cleared.
 */
@Throws(IOException::class)
fun File.clearJson() {
    this.toPath().clearJson()
}