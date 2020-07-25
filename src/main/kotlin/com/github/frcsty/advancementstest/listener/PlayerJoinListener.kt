package com.github.frcsty.advancementstest.listener

import com.github.frcsty.advancementstest.AdvancementsTest
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.scheduler.BukkitRunnable

class PlayerJoinListener(private val plugin: AdvancementsTest) : Listener {
    @EventHandler
    fun onPlayerJoin(event: PlayerJoinEvent) {
        val player = event.player
        object : BukkitRunnable() {
            override fun run() {
                plugin.manager.addPlayer(player)
            }
        }.runTaskLater(plugin, 2L)
    }

}