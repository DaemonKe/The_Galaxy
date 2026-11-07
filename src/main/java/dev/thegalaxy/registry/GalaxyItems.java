package dev.thegalaxy.registry;

import dev.thegalaxy.TheGalaxy;
import dev.thegalaxy.item.SpaceComponentItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpyglassItem;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class GalaxyItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(TheGalaxy.MOD_ID);

    public static final DeferredItem<SpyglassItem> TELESCOPE = ITEMS.register("telescope",
            () -> new SpyglassItem(new Item.Properties().stacksTo(1)));
    public static final DeferredItem<SpaceComponentItem> SATELLITE_CORE = ITEMS.register("satellite_core",
            () -> new SpaceComponentItem(new Item.Properties(), "tooltip.the_galaxy.satellite_core"));
    public static final DeferredItem<SpaceComponentItem> ROCKET_ENGINE = ITEMS.register("rocket_engine",
            () -> new SpaceComponentItem(new Item.Properties(), "tooltip.the_galaxy.rocket_engine"));

    public static final DeferredItem<BlockItem> OBSERVATORY_CONTROLLER = ITEMS.registerSimpleBlockItem(
            "observatory_controller", GalaxyBlocks.OBSERVATORY_CONTROLLER);
    public static final DeferredItem<BlockItem> LAUNCH_PAD = ITEMS.registerSimpleBlockItem(
            "launch_pad", GalaxyBlocks.LAUNCH_PAD);

    private GalaxyItems() {}
}
