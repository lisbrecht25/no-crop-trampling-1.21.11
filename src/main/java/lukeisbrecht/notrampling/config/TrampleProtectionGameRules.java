package lukeisbrecht.notrampling.config;

import net.fabricmc.fabric.api.gamerule.v1.GameRuleFactory;
import net.fabricmc.fabric.api.gamerule.v1.GameRuleRegistry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;

import java.util.Objects;

public class TrampleProtectionGameRules {
    public static final GameRules.Key<GameRules.IntRule> TRAMPLE_PROTECTION_LEVEL =
            GameRuleRegistry.register(
                    "trampleProtectionLevel",
                    GameRules.Category.MISC,
                    GameRuleFactory.createIntRule(1, 0, 3)
            );

    public static void register() {}

    public static TrampleProtection getTrampleProtectionLevel(World world) {
        int value = Objects.requireNonNull(world.getServer()).getGameRules().getInt(TRAMPLE_PROTECTION_LEVEL);
        return TrampleProtection.fromInt(value);
    }

    public static void setTrampleProtectionLevel(World world, TrampleProtection protectionLevel) {
        Objects.requireNonNull(world.getServer()).getGameRules().get(TRAMPLE_PROTECTION_LEVEL).set(protectionLevel.getValue(), world.getServer());
    }
}