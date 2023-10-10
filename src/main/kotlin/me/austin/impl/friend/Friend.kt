package me.austin.impl.friend

import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonPrimitive
import me.austin.VulcanMod
import me.austin.api.Manager
import me.austin.api.Name
import me.austin.api.Wrapper
import me.austin.util.fromJson
import me.austin.util.writeToJson
import net.minecraft.client.network.ClientPlayerEntity
import java.io.File
import java.util.*

data class Friend(override val name: String, val uuid: UUID) : Name

object FriendManager : Manager<Friend, MutableList<Friend>>, Wrapper {
    override val values = ArrayList<Friend>()

    private val friendFile = File("${VulcanMod.configFile}\\friends.json")

    override fun load() {
        if (!friendFile.exists()) {
            friendFile.createNewFile()
            return
        }

        for (friend in friendFile.fromJson().keys) {
            values.add(Friend(friend, minecraft.socialInteractionsManager.getUuid(friend)))
        }
    }

    override fun unload() {
        friendFile.writeToJson(JsonObject(values.associate {
            it.name to JsonPrimitive(it.uuid.toString())
        }))

        values.clear()
    }

    fun add(name: String) {
        values.add(Friend(name, minecraft.socialInteractionsManager.getUuid(name)))
    }

    fun remove(name: String) {
        values.removeIf { it.name == name }
    }
}

val ClientPlayerEntity.isFriend
    get() = FriendManager.values.any {
        it.uuid == this.uuid
    }
