package dev.thegalaxy.data;

import dev.thegalaxy.TheGalaxy;
import dev.thegalaxy.registry.GalaxyBlocks;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class GalaxyBlockTags extends BlockTagsProvider {
    public GalaxyBlockTags(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup,
                           ExistingFileHelper files) {
        super(output, lookup, TheGalaxy.MOD_ID, files);
    }

    @Override
    protected void addTags(HolderLookup.Provider lookup) {
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(
                GalaxyBlocks.OBSERVATORY_CONTROLLER.get(), GalaxyBlocks.LAUNCH_PAD.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(
                GalaxyBlocks.OBSERVATORY_CONTROLLER.get(), GalaxyBlocks.LAUNCH_PAD.get());
    }
}
