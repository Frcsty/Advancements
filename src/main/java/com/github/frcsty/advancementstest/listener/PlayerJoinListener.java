package com.github.frcsty.advancementstest.listener;

import com.github.frcsty.advancementstest.AdvancementsTest;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class PlayerJoinListener implements Listener {

    @NotNull
    private final AdvancementsTest plugin;

    public PlayerJoinListener(@NotNull final AdvancementsTest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(@NotNull final PlayerJoinEvent event) {
        final Player player = event.getPlayer();
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getManager().addPlayer(player);
            }
        }.runTaskLater(plugin, 2L);
    }
}
