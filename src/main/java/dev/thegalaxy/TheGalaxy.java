package dev.thegalaxy;

import com.mojang.logging.LogUtils;
import dev.thegalaxy.data.GalaxyDataGenerators;
import dev.thegalaxy.registry.GalaxyBlocks;
import dev.thegalaxy.registry.GalaxyCreativeTabs;
import dev.thegalaxy.registry.GalaxyItems;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import org.slf4j.Logger;

@Mod(TheGalaxy.MOD_ID)
public final class TheGalaxy {
    public static final String MOD_ID = "the_galaxy";
    public static final Logger LOGGER = LogUtils.getLogger();

    public TheGalaxy(IEventBus modEventBus) {
        GalaxyBlocks.BLOCKS.register(modEventBus);
        GalaxyItems.ITEMS.register(modEventBus);
        GalaxyCreativeTabs.TABS.register(modEventBus);
        modEventBus.addListener(GalaxyDataGenerators::gatherData);
        LOGGER.info("The Galaxy: astronomy and space exploration foundation loaded");
    }
}
