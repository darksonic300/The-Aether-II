package com.aetherteam.aetherii.item.tools.holystone;

import com.aetherteam.aetherii.item.AetherIIItemTiers;
import com.aetherteam.aetherii.item.tools.abilities.HolystoneTool;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.Tiers;

public class HolystoneAxeItem extends AxeItem implements HolystoneTool {
    public HolystoneAxeItem() {
        super(AetherIIItemTiers.HOLYSTONE, new Properties().attributes(AxeItem.createAttributes(AetherIIItemTiers.HOLYSTONE, 7.0F, -3.2F)));
    }
}
