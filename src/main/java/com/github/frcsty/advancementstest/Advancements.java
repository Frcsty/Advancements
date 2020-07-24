package com.github.frcsty.advancementstest;

import com.github.frcsty.advancementstest.advancements.Advancement;
import com.github.frcsty.advancementstest.advancements.JoinAdvancement;
import com.github.frcsty.advancementstest.advancements.KillAdvancement;

public enum Advancements {

    KILL(new KillAdvancement()),
    JOIN(new JoinAdvancement())
    ;

    private final Advancement advancement;

    Advancements(final Advancement advancement) {
        this.advancement = advancement;
    }

    public eu.endercentral.crazy_advancements.Advancement getAdvancement(final eu.endercentral.crazy_advancements.Advancement root) {
        return this.advancement.getAdvancement(root);
    }
}
