package dev.thegalaxy.data;

import dev.thegalaxy.TheGalaxy;
import dev.thegalaxy.registry.GalaxyBlocks;
import dev.thegalaxy.registry.GalaxyItems;
import net.minecraft.data.PackOutput;
import net.neoforged.neoforge.common.data.LanguageProvider;

public final class GalaxyLanguage extends LanguageProvider {
    private final boolean chinese;

    public GalaxyLanguage(PackOutput output, String locale) {
        super(output, TheGalaxy.MOD_ID, locale);
        chinese = locale.equals("zh_cn");
    }

    @Override
    protected void addTranslations() {
        add("itemGroup.the_galaxy", chinese ? "星河探索" : "The Galaxy");
        addItem(GalaxyItems.TELESCOPE, chinese ? "天文望远镜" : "Astronomical Telescope");
        addItem(GalaxyItems.SATELLITE_CORE, chinese ? "卫星核心" : "Satellite Core");
        addItem(GalaxyItems.ROCKET_ENGINE, chinese ? "火箭引擎" : "Rocket Engine");
        addBlock(GalaxyBlocks.OBSERVATORY_CONTROLLER, chinese ? "观测站控制器" : "Observatory Controller");
        addBlock(GalaxyBlocks.LAUNCH_PAD, chinese ? "发射台" : "Launch Pad");
        add("tooltip.the_galaxy.satellite_core", chinese
                ? "卫星制造组件；卫星系统尚在开发中。"
                : "Satellite crafting component; satellite systems are planned.");
        add("tooltip.the_galaxy.rocket_engine", chinese
                ? "航天制造组件；火箭飞行系统尚在开发中。"
                : "Spacecraft crafting component; rocket flight is planned.");
    }
}
