package com.github.frcsty.advancementstest.listener

import com.github.frcsty.advancementapi.wrapper.Advancement
import com.github.frcsty.advancementapi.wrapper.AdvancementDisplay
import com.github.frcsty.advancementapi.wrapper.AdvancementVisibility
import com.github.frcsty.advancementapi.wrapper.NameKey
import com.github.frcsty.advancementstest.AdvancementsTest
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.Damageable
import org.bukkit.scheduler.BukkitRunnable
import java.util.*

class BlockBreakListener(private val plugin: AdvancementsTest) : Listener {
    @EventHandler
    fun onBlockBreak(event: BlockBreakEvent) {
        val player = event.player
        val item = event.player.inventory.itemInMainHand
        if (item.type == Material.AIR && !ALLOWED.contains(item.type)) return
        val meta = item.itemMeta as Damageable
        val durability = item.type.maxDurability - meta.damage - 1
        if (AVAILABILITY.contains(player)) return
        handleAdvancement(item, durability, event.player)
    }

    private fun getDisplayTitle(durability: Int): String? {
        return when (durability <=2){
            durability <=2 -> "§cYour item is about to break!"
            durability <=5 -> "§4Low item durability! §7- §4$durability"
            durability <=60 -> "§cLow item durability! §7- §c$durability"
            else -> null
        }
    }

    private fun handleAdvancement(item: ItemStack, durability: Int, player: Player) {
        val displayTitle = getDisplayTitle(durability) ?: return
        val display = AdvancementDisplay(
                item.type,
                displayTitle,
                "",
                AdvancementDisplay.AdvancementFrame.CHALLENGE,
                true,
                false,
                AdvancementVisibility.VANILLA
        )
        val advancement = Advancement(null, NameKey("custom", "damage"), display)
        advancement.displayToast(player)
        AVAILABILITY.add(player)
        object : BukkitRunnable() {
            override fun run() {
                AVAILABILITY.remove(player)
            }
        }.runTaskLater(plugin, TOAST_DURATION.toLong())
    }

    companion object {
        private val AVAILABILITY: MutableList<Player> = ArrayList()
        private val ALLOWED = listOf(
                Material.WOODEN_PICKAXE, Material.STONE_PICKAXE, Material.IRON_PICKAXE, Material.GOLDEN_PICKAXE,
                Material.DIAMOND_PICKAXE, Material.NETHERITE_PICKAXE)
        private const val TOAST_DURATION = 130
    }

}