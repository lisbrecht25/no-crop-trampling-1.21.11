package lukeisbrecht.notrampling.mixin;

import lukeisbrecht.notrampling.NoCropTrampling;
import lukeisbrecht.notrampling.config.TrampleProtection;
import net.minecraft.block.BlockState;
import net.minecraft.block.FarmlandBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FarmlandBlock.class)
public class FarmlandBlockMixin {
    @Inject(method = "onLandedUpon", at = @At("HEAD"), cancellable = true)
    private void preventTrample(World world, BlockState state, BlockPos pos, Entity entity, double fallDistance, CallbackInfo ci) {
        if (world instanceof ServerWorld serverWorld) {
            TrampleProtection protection;

            if (NoCropTrampling.CONFIG.overrideGamerule) {
                protection = NoCropTrampling.CONFIG.defaultProtectionLevel;
            } else {
                protection = lukeisbrecht.notrampling.config.TrampleProtectionGameRules.getTrampleProtectionLevel(serverWorld);
            }

            boolean shouldProtect = switch (protection) {
                case FULL -> true;
                case MOBS_ONLY -> !(entity instanceof PlayerEntity);
                case PLAYERS_ONLY -> !(entity instanceof MobEntity);
                case NONE -> false;
            };

            if (shouldProtect) {
                ci.cancel();
                entity.handleFallDamage(fallDistance, 1.0F, world.getDamageSources().fall());
            }
        }
    }
}
