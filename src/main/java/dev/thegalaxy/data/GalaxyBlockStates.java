package dev.thegalaxy.data;

import dev.thegalaxy.TheGalaxy;
import dev.thegalaxy.registry.GalaxyBlocks;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.BlockStateProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class GalaxyBlockStates extends BlockStateProvider {
    public GalaxyBlockStates(PackOutput output, ExistingFileHelper files) {
        super(output, TheGalaxy.MOD_ID, files);
    }

    @Override
    protected void registerStatesAndModels() {
        // Reference vanilla textures until custom art is ready; no texture copies are bundled.
        simpleBlockWithItem(GalaxyBlocks.OBSERVATORY_CONTROLLER.get(),
                models().cubeBottomTop("observatory_controller", mcLoc("block/iron_block"),
                        mcLoc("block/smooth_stone"), mcLoc("block/daylight_detector_top")));
        simpleBlockWithItem(GalaxyBlocks.LAUNCH_PAD.get(),
                models().cubeBottomTop("launch_pad", mcLoc("block/lodestone_side"),
                        mcLoc("block/smooth_stone"), mcLoc("block/lodestone_top")));
    }
}
