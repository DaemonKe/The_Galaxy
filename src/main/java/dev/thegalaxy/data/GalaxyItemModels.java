package dev.thegalaxy.data;

import dev.thegalaxy.TheGalaxy;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;

public final class GalaxyItemModels extends ItemModelProvider {
    public GalaxyItemModels(PackOutput output, ExistingFileHelper files) {
        super(output, TheGalaxy.MOD_ID, files);
    }

    @Override
    protected void registerModels() {
        withExistingParent("telescope", mcLoc("item/spyglass"));
        singleTexture("satellite_core", mcLoc("item/generated"), "layer0", mcLoc("item/ender_eye"));
        singleTexture("rocket_engine", mcLoc("item/generated"), "layer0", mcLoc("item/fire_charge"));
    }
}
