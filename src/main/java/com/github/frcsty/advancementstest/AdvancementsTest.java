package com.github.frcsty.advancementstest;

import com.github.frcsty.advancementapi.AdvancementAPI;
import com.github.frcsty.advancementapi.manager.AdvancementManager;
import com.github.frcsty.advancementstest.listener.AdvancementHolder;
import com.github.frcsty.advancementstest.listener.BlockBreakListener;
import com.github.frcsty.advancementstest.listener.PlayerDamageListener;
import com.github.frcsty.advancementstest.listener.PlayerJoinListener;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class AdvancementsTest extends JavaPlugin implements Listener {

    private static final Map<Player, List<AdvancementHolder>> AVAILABILITY = new HashMap<>();
    private static final AdvancementManager MANAGER = AdvancementAPI.getNewAdvancementManager();

    @Override
    public void onEnable() {
        registerListeners(
                new PlayerJoinListener(this),
                new BlockBreakListener(this),
                new PlayerDamageListener(this)
        );
    }

    private void registerListeners(@NotNull final Listener... listeners) {
        Arrays.asList(listeners).forEach(listener -> getServer().getPluginManager().registerEvents(listener, this));
    }

    public AdvancementManager getManager() {
        return MANAGER;
    }

    public Map<Player, List<AdvancementHolder>> getAvailability() {
        return AVAILABILITY;
    }
}
