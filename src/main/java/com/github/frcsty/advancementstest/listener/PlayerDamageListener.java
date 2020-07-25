package com.github.frcsty.advancementstest.listener;

import com.github.frcsty.advancementapi.wrapper.Advancement;
import com.github.frcsty.advancementapi.wrapper.AdvancementDisplay;
import com.github.frcsty.advancementapi.wrapper.AdvancementVisibility;
import com.github.frcsty.advancementapi.wrapper.NameKey;
import com.github.frcsty.advancementstest.AdvancementsTest;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

public final class PlayerDamageListener implements Listener {

    private static final int TOAST_DURATION = 130;
    @NotNull
    private final AdvancementsTest plugin;

    public PlayerDamageListener(@NotNull final AdvancementsTest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDamage(@NotNull final EntityDamageEvent event) {
        if (!(event.getEntity() instanceof Player)) return;
        final Player player = (Player) event.getEntity();

        if (plugin.getAvailability().get(player).contains(AdvancementHolder.DAMAGE_ADVANCEMENT)) return;
        handleAdvancement(event.getFinalDamage(), player);
    }

    private void handleAdvancement(final double damage, final Player player) {
        final AdvancementDisplay display = new AdvancementDisplay(
                Material.GOLDEN_SWORD,
                "§bYou Took §f" + damage + " §bDamage!",
                "",
                AdvancementDisplay.AdvancementFrame.GOAL,
                true,
                false,
                AdvancementVisibility.VANILLA
        );
        final Advancement damageAdvancement = new Advancement(null, new NameKey("custom", "damage"), display);

        damageAdvancement.displayToast(player);
        plugin.getAvailability().get(player).add(AdvancementHolder.DAMAGE_ADVANCEMENT);
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getAvailability().get(player).remove(AdvancementHolder.DAMAGE_ADVANCEMENT);
            }
        }.runTaskLater(plugin, TOAST_DURATION);
    }
}
