package dev.thegalaxy.data;

import java.util.List;
import java.util.Set;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.neoforged.neoforge.data.event.GatherDataEvent;

public final class GalaxyDataGenerators {
    private GalaxyDataGenerators() {}

    public static void gatherData(GatherDataEvent event) {
        var generator = event.getGenerator();
        var output = generator.getPackOutput();
        var lookup = event.getLookupProvider();
        var files = event.getExistingFileHelper();

        generator.addProvider(event.includeClient(), new GalaxyBlockStates(output, files));
        generator.addProvider(event.includeClient(), new GalaxyItemModels(output, files));
        generator.addProvider(event.includeClient(), new GalaxyLanguage(output, "en_us"));
        generator.addProvider(event.includeClient(), new GalaxyLanguage(output, "zh_cn"));
        generator.addProvider(event.includeServer(), new GalaxyRecipes(output, lookup));
        generator.addProvider(event.includeServer(), new GalaxyBlockTags(output, lookup, files));
        generator.addProvider(event.includeServer(), new LootTableProvider(output, Set.of(),
                List.of(new LootTableProvider.SubProviderEntry(
                        GalaxyBlockLoot::new, LootContextParamSets.BLOCK)), lookup));
    }
}
