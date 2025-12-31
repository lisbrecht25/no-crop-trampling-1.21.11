package lukeisbrecht.notrampling;

import lukeisbrecht.notrampling.config.NoCropTramplingConfig;
import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.fabricmc.api.ModInitializer;


import net.fabricmc.fabric.api.gamerule.v1.GameRuleBuilder;
import net.minecraft.util.Identifier;
import net.minecraft.world.rule.GameRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoCropTrampling implements ModInitializer {
	public static final String MOD_ID = "no-trampling";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static GameRule<TrampleProtection> CROP_TRAMPLING_PROTECTION;
	public static NoCropTramplingConfig CONFIG;

	@Override
	public void onInitialize() {
		AutoConfig.register(NoCropTramplingConfig.class, GsonConfigSerializer::new);
		CONFIG = AutoConfig.getConfigHolder(NoCropTramplingConfig.class).getConfig();

		CROP_TRAMPLING_PROTECTION = GameRuleBuilder.forEnum(TrampleProtection.ALL)
				.buildAndRegister(Identifier.of("notrampling", "crop_trampling_protection"));
	}
}