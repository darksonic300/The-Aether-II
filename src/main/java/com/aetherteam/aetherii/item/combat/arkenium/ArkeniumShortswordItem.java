package com.aetherteam.aetherii.item.combat.arkenium;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.combat.ShortswordItem;

public class ArkeniumShortswordItem extends ShortswordItem {
    public ArkeniumShortswordItem() {
        super(AetherIIItemTiers.ARKENIUM, new Properties().attributes(ShortswordItem.createAttributes(AetherIIItemTiers.ARKENIUM, 3, -2.4F)));
    }
}
