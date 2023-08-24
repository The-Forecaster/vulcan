package me.austin.gui

import net.minecraft.util.Formatting

object GuiManager {
    private var red = 0.55f
    private var green = 0.7f
    private var blue = 0.25f

    private var moduleListRed = 255
    private var moduleListGreen = 255
    private var moduleListBlue = 255
    private var textRadarPlayers = 8

    private var textRadarSpots = true

    private var textColor = Formatting.GRAY.toString()

    private var moduleListMode = ModuleListMode.RAINBOW

    enum class ModuleListMode {
        STATIC,
        RAINBOW
    }
}