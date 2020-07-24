package com.github.frcsty.advancementstest.advancements;

import eu.endercentral.crazy_advancements.AdvancementDisplay;
import eu.endercentral.crazy_advancements.AdvancementReward;
import eu.endercentral.crazy_advancements.AdvancementVisibility;
import eu.endercentral.crazy_advancements.NameKey;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class KillAdvancement implements Advancement {

    public eu.endercentral.crazy_advancements.Advancement getAdvancement(final eu.endercentral.crazy_advancements.Advancement root) {
        AdvancementDisplay killDisplay = new AdvancementDisplay(
                Material.PLAYER_HEAD,
                "Suicidal Tendencies",
                "Commit suicide and gain 10XP",
                AdvancementDisplay.AdvancementFrame.CHALLENGE,
                true,
                true,
                AdvancementVisibility.ALWAYS);
        killDisplay.setCoordinates(1, 0);
        eu.endercentral.crazy_advancements.Advancement kill = new eu.endercentral.crazy_advancements.Advancement(root, new NameKey("custom", "suicide"), killDisplay);

        kill.setReward(new AdvancementReward() {
            @Override
            public void onGrant(Player player) {
                player.giveExp(10);
            }
        });

        return kill;
    }

}
