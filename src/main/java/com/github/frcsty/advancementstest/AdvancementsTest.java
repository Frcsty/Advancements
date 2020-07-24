package com.github.frcsty.advancementstest;

import eu.endercentral.crazy_advancements.*;
import eu.endercentral.crazy_advancements.manager.AdvancementManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public final class AdvancementsTest extends JavaPlugin implements Listener {

    private final AdvancementManager manager = CrazyAdvancements.getNewAdvancementManager();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        new BukkitRunnable() {
            @Override
            public void run() {
                manager.addPlayer(event.getPlayer());
            }
        }.runTaskLater(this, 2L);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        final ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR) return;
        if (!item.getType().toString().contains("PICKAXE")) return;

        handleAdvancement(item, event.getPlayer());
    }

    private void handleAdvancement(final ItemStack item, final Player player) {
        final Damageable meta = (Damageable) item.getItemMeta();
        if (meta == null) return;

        final int durability = (item.getType().getMaxDurability() - meta.getDamage()) - 1;
        if (durability > 25) return;
        final String title = durability == 1 ? "§cItem Break Alert!" : "§cLow Durability Alert! (" + durability + ")";
        final String description = "Durability Indication Advancement";

        final AdvancementDisplay display = new AdvancementDisplay(
                item.getType(),
                title,
                description,
                AdvancementDisplay.AdvancementFrame.CHALLENGE,
                true,
                false,
                AdvancementVisibility.VANILLA
        );
        final Advancement advancement = new Advancement(null, new NameKey("custom", "damage"), display);

        advancement.displayToast(player);
    }
}
