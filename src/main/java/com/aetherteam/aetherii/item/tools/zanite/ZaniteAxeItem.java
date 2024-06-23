package com.aetherteam.aetherii.item.tools.zanite;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.ZaniteTool;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tiers;

public class ZaniteAxeItem extends AxeItem implements ZaniteTool {
    public ZaniteAxeItem() {
        super(AetherIIItemTiers.ZANITE, new Properties().attributes(AxeItem.createAttributes(AetherIIItemTiers.ZANITE, 6.0F, -3.1F)));
    }
}
