package com.aetherteam.aetherii.client.renderer;

import com.aetherteam.aetherii.AetherII;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.resources.ResourceLocation;

public class AetherIIModelLayers {
    public static final ModelLayerLocation SKYROOT_BED_FOOT = register("skyroot_bed_foot");
    public static final ModelLayerLocation SKYROOT_BED_HEAD = register("skyroot_bed_head");
    public static final ModelLayerLocation MOA_EGG = register("moa_egg");

    public static final ModelLayerLocation PHYG = register("phyg");
    public static final ModelLayerLocation AERBUNNY = register("aerbunny");
    public static final ModelLayerLocation AERBUNNY_COLLAR = register("aerbunny", "collar");
    public static final ModelLayerLocation FLYING_COW = register("flying_cow");
    public static final ModelLayerLocation SHEEPUFF = register("sheepuff");
    public static final ModelLayerLocation HIGHFIELDS_KIRRID = register("highfields_kirrid");
    public static final ModelLayerLocation HIGHFIELDS_KIRRID_BABY = register("highfields_kirrid_baby");
    public static final ModelLayerLocation MAGNETIC_KIRRID = register("magnetic_kirrid");
    public static final ModelLayerLocation MAGNETIC_KIRRID_BABY = register("magnetic_kirrid_baby");
    public static final ModelLayerLocation ARCTIC_KIRRID = register("arctic_kirrid");
    public static final ModelLayerLocation ARCTIC_KIRRID_BABY = register("arctic_kirrid_baby");
    public static final ModelLayerLocation MOA = register("moa");
    public static final ModelLayerLocation MOA_BABY = register("moa_baby");

    public static final ModelLayerLocation ZEPHYR = register("zephyr");
    public static final ModelLayerLocation ZEPHYR_TRANSPARENCY = register("zephyr", "transparency");

    private static ModelLayerLocation register(String name) {
        return register(name, "main");
    }

    private static ModelLayerLocation register(String name, String type) {
        return register(new ResourceLocation(AetherII.MODID, name), type);
    }

    private static ModelLayerLocation register(ResourceLocation location, String type) {
        return new ModelLayerLocation(location, type);
    }
}
