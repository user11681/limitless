package user11681.limitless.integration;

import io.github.prospector.modmenu.api.ConfigScreenFactory;
import io.github.prospector.modmenu.api.ModMenuApi;
import me.shedaniel.autoconfig.AutoConfig;
import net.minecraft.client.gui.screen.Screen;
import user11681.limitless.config.LimitlessConfiguration;

public class LimitlessModMenuIntegration implements ModMenuApi {
    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return (Screen parent) -> AutoConfig.getConfigScreen(LimitlessConfiguration.class, parent).get();
    }
}
