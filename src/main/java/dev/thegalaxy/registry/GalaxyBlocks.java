package dev.thegalaxy.registry;

import dev.thegalaxy.TheGalaxy;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class GalaxyBlocks {
    public static final DeferredRegister.Blocks BLOCKS = DeferredRegister.createBlocks(TheGalaxy.MOD_ID);

    // Ordinary blocks for now; machine behavior belongs in future block/entity classes.
    public static final DeferredBlock<Block> OBSERVATORY_CONTROLLER = BLOCKS.registerSimpleBlock(
            "observatory_controller", metalProperties());
    public static final DeferredBlock<Block> LAUNCH_PAD = BLOCKS.registerSimpleBlock(
            "launch_pad", metalProperties());

    private GalaxyBlocks() {}

    private static BlockBehaviour.Properties metalProperties() {
        return BlockBehaviour.Properties.of()
                .mapColor(MapColor.METAL)
                .strength(3.5F, 6.0F)
                .sound(SoundType.METAL)
                .requiresCorrectToolForDrops();
    }
}
