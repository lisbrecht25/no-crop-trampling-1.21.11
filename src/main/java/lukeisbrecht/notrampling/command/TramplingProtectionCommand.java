package lukeisbrecht.notrampling.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.context.CommandContext;
import lukeisbrecht.notrampling.NoCropTrampling;
import lukeisbrecht.notrampling.config.TrampleProtection;
import lukeisbrecht.notrampling.data.DataManager;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.command.permission.Permission;
import net.minecraft.command.permission.PermissionPredicate;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class TramplingProtectionCommand {
    public static TrampleProtection protectionLevel = DataManager.getData().protection;

    public static void register(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        dispatcher.register(CommandManager.literal("tramplingProtection")
                .requires(source -> {
                    if (source.getPlayer() != null) {
                        return source.getServer().getPlayerManager().isOperator(source.getPlayer().getPlayerConfigEntry());
                    }
                    return true;
                })
                .executes(TramplingProtectionCommand::getCurrentProtection)
                .then(CommandManager.argument("level", StringArgumentType.word())
                        .suggests((context, builder) -> {
                            for (TrampleProtection level : TrampleProtection.values()) {
                                builder.suggest(level.name().toLowerCase());
                            }
                            return builder.buildFuture();
                        })
                        .executes(TramplingProtectionCommand::setProtection)
                )
        );
    }

    private static int setProtection(CommandContext<ServerCommandSource> context) {
        String input = StringArgumentType.getString(context, "level").toUpperCase();

        try {
            protectionLevel = TrampleProtection.valueOf(input);
            DataManager.getData().protection = protectionLevel;
            DataManager.save();
            context.getSource().sendFeedback(
                    () -> Text.literal("Set trample protection level to " + protectionLevel.getDisplayName()),
                    true
            );
            return 1;
        } catch (IllegalArgumentException e) {
            context.getSource().sendError(Text.literal("Invalid protection level"));
            return 0;
        }
    }

    private static int getCurrentProtection(CommandContext<ServerCommandSource> context) {
        TrampleProtection current = DataManager.getData().protection;
        context.getSource().sendFeedback(
                () -> Text.literal("Current Protection Level: " + current.getDisplayName()), false
        );
        return 1;
    }
}
