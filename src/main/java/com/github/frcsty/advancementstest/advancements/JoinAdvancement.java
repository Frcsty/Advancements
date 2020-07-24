package com.github.frcsty.advancementstest.advancements;

import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementReward;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.NameKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class JoinAdvancement implements Advancement {

    @Override
    public eu.endercentral.crazy_advancements.Advancement getAdvancement(final eu.endercentral.crazy_advancements.Advancement root) {
        final AdvancementDisplay joinDisplay = new AdvancementDisplay(
                Material.PAPER,
                "Welcome To The Server",
                "Successfully join the server.",
                AdvancementDisplay.AdvancementFrame.CHALLENGE,
                true,
                false,
                AdvancementVisibility.ALWAYS
        );
        joinDisplay.setCoordinates(1, 1);
        final eu.endercentral.crazy_advancements.Advancement join = new eu.endercentral.crazy_advancements.Advancement(root, new NameKey("custom", "join"), joinDisplay);
        join.setReward(new AdvancementReward() {
            @Override
            public void onGrant(Player player) {
                player.giveExp(10);
            }
        });

        return join;
    }
}
