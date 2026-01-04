package lukeisbrecht.notrampling.config;

import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "notrampling")
public class NoCropTramplingConfig implements ConfigData {

    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public TrampleProtection defaultProtectionLevel = TrampleProtection.FULL;

    public boolean overrideGamerule = false;
}
