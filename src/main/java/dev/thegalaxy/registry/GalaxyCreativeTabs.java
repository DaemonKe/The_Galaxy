package dev.thegalaxy.registry;

import dev.thegalaxy.TheGalaxy;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public final class GalaxyCreativeTabs {
    public static final DeferredRegister<CreativeModeTab> TABS = DeferredRegister.create(
            Registries.CREATIVE_MODE_TAB, TheGalaxy.MOD_ID);

    public static final DeferredHolder<CreativeModeTab, CreativeModeTab> EXPLORATION = TABS.register(
            "exploration", () -> CreativeModeTab.builder()
                    .title(Component.translatable("itemGroup.the_galaxy"))
                    .icon(() -> GalaxyItems.TELESCOPE.get().getDefaultInstance())
                    .displayItems((parameters, output) -> {
                        output.accept(GalaxyItems.TELESCOPE.get());
                        output.accept(GalaxyItems.OBSERVATORY_CONTROLLER.get());
                        output.accept(GalaxyItems.SATELLITE_CORE.get());
                        output.accept(GalaxyItems.ROCKET_ENGINE.get());
                        output.accept(GalaxyItems.LAUNCH_PAD.get());
                    })
                    .build());

    private GalaxyCreativeTabs() {}
}
