package dev.thegalaxy.data;

import dev.thegalaxy.registry.GalaxyItems;
import java.util.concurrent.CompletableFuture;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.RecipeCategory;
import net.minecraft.data.recipes.RecipeOutput;
import net.minecraft.data.recipes.RecipeProvider;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.world.item.Items;

public final class GalaxyRecipes extends RecipeProvider {
    public GalaxyRecipes(PackOutput output, CompletableFuture<HolderLookup.Provider> lookup) {
        super(output, lookup);
    }

    @Override
    protected void buildRecipes(RecipeOutput output) {
        ShapelessRecipeBuilder.shapeless(RecipeCategory.TOOLS, GalaxyItems.TELESCOPE.get())
                .requires(Items.SPYGLASS).requires(Items.AMETHYST_SHARD)
                .unlockedBy("has_spyglass", has(Items.SPYGLASS)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GalaxyItems.SATELLITE_CORE.get())
                .pattern("IRI").pattern("RQR").pattern("IRI")
                .define('I', Items.IRON_INGOT).define('R', Items.REDSTONE).define('Q', Items.QUARTZ)
                .unlockedBy("has_redstone", has(Items.REDSTONE)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, GalaxyItems.ROCKET_ENGINE.get())
                .pattern("I I").pattern("IFI").pattern(" B ")
                .define('I', Items.IRON_INGOT).define('F', Items.FURNACE).define('B', Items.BLAZE_POWDER)
                .unlockedBy("has_blaze_powder", has(Items.BLAZE_POWDER)).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.DECORATIONS, GalaxyItems.OBSERVATORY_CONTROLLER.get())
                .pattern("ITI").pattern("RCR").pattern("III")
                .define('I', Items.IRON_INGOT).define('T', GalaxyItems.TELESCOPE.get())
                .define('R', Items.REDSTONE).define('C', GalaxyItems.SATELLITE_CORE.get())
                .unlockedBy("has_telescope", has(GalaxyItems.TELESCOPE.get())).save(output);

        ShapedRecipeBuilder.shaped(RecipeCategory.BUILDING_BLOCKS, GalaxyItems.LAUNCH_PAD.get(), 4)
                .pattern("III").pattern("SRS").pattern("SSS")
                .define('I', Items.IRON_INGOT).define('S', Items.SMOOTH_STONE).define('R', Items.REDSTONE)
                .unlockedBy("has_iron_ingot", has(Items.IRON_INGOT)).save(output);
    }
}
