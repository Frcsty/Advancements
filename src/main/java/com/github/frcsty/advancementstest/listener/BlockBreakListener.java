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
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

public final class BlockBreakListener implements Listener {

    private static final List<Material> ALLOWED = Arrays.asList(
            Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE,
            Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE);
    private static final int TOAST_DURATION = 130;
    @NotNull
    private final AdvancementsTest plugin;

    public BlockBreakListener(@NotNull final AdvancementsTest plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onBlockBreak(@NotNull final BlockBreakEvent event) {
        final Player player = event.getPlayer();
        final ItemStack item = event.getPlayer().getInventory().getItemInMainHand();
        if (item.getType() == Material.AIR && !ALLOWED.contains(item.getType())) return;

        final Damageable meta = (Damageable) item.getItemMeta();
        if (meta == null) return;

        final int durability = (item.getType().getMaxDurability() - meta.getDamage()) - 1;

        if (plugin.getAvailability().get(player).contains(AdvancementHolder.DURABILITY_ADVANCEMENT)) return;
        handleAdvancement(item, durability, event.getPlayer());
    }

    private String getDisplayTitle(final int durability) {
        if (durability <= 2) {
            return "§cYour item is about to break!";
        } else if (durability <= 5) {
            return "§4Low item durability! §7- §4" + durability;
        } else if (durability <= 60) {
            return "§cLow item durability! §7- §c" + durability;
        } else {
            return null;
        }
    }

    private void handleAdvancement(final ItemStack item, final int durability, final Player player) {
        final String displayTitle = getDisplayTitle(durability);
        if (displayTitle == null) return;

        final AdvancementDisplay display = new AdvancementDisplay(
                item.getType(),
                displayTitle,
                "",
                AdvancementDisplay.AdvancementFrame.CHALLENGE,
                true,
                false,
                AdvancementVisibility.VANILLA
        );
        final Advancement advancement = new Advancement(null, new NameKey("custom", "damage"), display);

        advancement.displayToast(player);
        plugin.getAvailability().get(player).add(AdvancementHolder.DURABILITY_ADVANCEMENT);
        new BukkitRunnable() {
            @Override
            public void run() {
                plugin.getAvailability().get(player).remove(AdvancementHolder.DURABILITY_ADVANCEMENT);
            }
        }.runTaskLater(plugin, TOAST_DURATION);
    }
}
