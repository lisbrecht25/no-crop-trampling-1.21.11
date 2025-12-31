package lukeisbrecht.notrampling.config;

import lukeisbrecht.notrampling.TrampleProtection;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;

@Config(name = "notrampling")
public class NoCropTramplingConfig implements ConfigData {

    @ConfigEntry.Gui.Tooltip
    @ConfigEntry.Gui.EnumHandler(option = ConfigEntry.Gui.EnumHandler.EnumDisplayOption.BUTTON)
    public TrampleProtection defaultProtectionLevel = TrampleProtection.ALL;

    @ConfigEntry.Gui.Tooltip
    public boolean overrideGamerule = true;
}
