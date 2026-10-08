package dev.thegalaxy.data;

import dev.thegalaxy.registry.GalaxyBlocks;
import java.util.Set;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;

public final class GalaxyBlockLoot extends BlockLootSubProvider {
    public GalaxyBlockLoot(HolderLookup.Provider lookup) {
        super(Set.of(), FeatureFlags.DEFAULT_FLAGS, lookup);
    }

    @Override
    protected void generate() {
        dropSelf(GalaxyBlocks.OBSERVATORY_CONTROLLER.get());
        dropSelf(GalaxyBlocks.LAUNCH_PAD.get());
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return GalaxyBlocks.BLOCKS.getEntries().stream().map(holder -> (Block) holder.get()).toList();
    }
}
