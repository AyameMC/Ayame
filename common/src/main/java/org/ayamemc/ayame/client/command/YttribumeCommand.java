/*
 *     Custom player model mod. Powered by GeckoLib.
 *     Copyright (C) 2024-2025  CrystalNeko, HappyRespawnanchor, pertaz(Icon Designer)
 *
 *     This file is part of Ayame.
 *
 *     Ayame is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Ayame is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Ayame.  If not, see <https://www.gnu.org/licenses/>.
 */

package org.ayamemc.ayame.client.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.FloatArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.arguments.ResourceLocationArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.ayamemc.ayame.client.yttribume.Yttribume;
import org.ayamemc.ayame.client.yttribume.Yttribumes;

import static org.ayamemc.ayame.client.AyameClient.MINECRAFT;
import static org.ayamemc.ayame.client.command.AyameCommandManager.sendMessageToClient;

public class YttribumeCommand {
    private static final SuggestionProvider<?> YTTRIBUME_SUGGESTION_PROVIDER = (c, b) -> {
        Yttribumes.getIds().forEach((y) -> b.suggest(y.toString()));
        return b.buildFuture();
    };

    //别改了好不好哇
    @SuppressWarnings("unchecked")
    public static <T extends SharedSuggestionProvider> void init(CommandDispatcher<T> dispatcher, CommandBuildContext context) {
        dispatcher.register(LiteralArgumentBuilder.<T>literal("ayame-yttribume")
                .then(LiteralArgumentBuilder.<T>literal("set")
                        .then(RequiredArgumentBuilder.<T, ResourceLocation>argument("yttribume", ResourceLocationArgument.id())
                                .suggests((SuggestionProvider<T>) YTTRIBUME_SUGGESTION_PROVIDER)
                                .then(RequiredArgumentBuilder.<T, Float>argument("value", FloatArgumentType.floatArg())
                                        .executes(YttribumeCommand::setYttribume) // TODO 最大值&最小值处理
                                )
                        )
                )
                .then(LiteralArgumentBuilder.<T>literal("get")
                        .then(RequiredArgumentBuilder.<T, ResourceLocation>argument("yttribume", ResourceLocationArgument.id())
                                .suggests((SuggestionProvider<T>) YTTRIBUME_SUGGESTION_PROVIDER)
                                .executes(YttribumeCommand::getYttribume)
                        )
                )
                .then(LiteralArgumentBuilder.<T>literal("reset")
                        .then(RequiredArgumentBuilder.<T, ResourceLocation>argument("yttribume", ResourceLocationArgument.id())
                                .suggests((SuggestionProvider<T>) YTTRIBUME_SUGGESTION_PROVIDER)
                                .executes(YttribumeCommand::resetYttribume)
                        )
                )
                .then(LiteralArgumentBuilder.<T>literal("unlock")
                        .executes(YttribumeCommand::unlock)
                )
                .then(LiteralArgumentBuilder.<T>literal("help")
                        .executes(YttribumeCommand::help)
                )
                .executes(YttribumeCommand::help)
        );

        // 重定向
        dispatcher.register(LiteralArgumentBuilder.<T>literal("aym-yttribume").redirect(dispatcher.getRoot().getChild("ayame-yttribume")));
    }

    public static <T extends SharedSuggestionProvider> int unlock(CommandContext<T> context) {
        MINECRAFT.player.ayame$setRestriction(false);
        sendMessageToClient(Component.translatable("message.ayame.command.yttribume.unlock_success"));
        return 1;
    }


    @SuppressWarnings("DataFlowIssue")
    private static <T extends SharedSuggestionProvider> int help(CommandContext<T> context) {
        MINECRAFT.player.displayClientMessage(Component.translatable("message.ayame.command.yttribume.help"), false);
        return 0;
    }

    @SuppressWarnings("DataFlowIssue")
    private static <T extends SharedSuggestionProvider> int resetYttribume(CommandContext<T> context) {
        Yttribume yttribume = Yttribumes.get(ResourceLocationArgument.getId((CommandContext<CommandSourceStack>) context, "yttribume"));
        MINECRAFT.player.ayame$setYttribume(yttribume, yttribume.defaultValue());
        sendMessageToClient(Component.translatable("message.ayame.command.yttribume.reset_success"));
        return 0;
    }

    @SuppressWarnings("DataFlowIssue")
    private static <T extends SharedSuggestionProvider> int getYttribume(CommandContext<T> context) {
        sendMessageToClient(Component.translatable("message.ayame.command.yttribume.get_success",
                "§e" + MINECRAFT.player.ayame$getYttribume(Yttribumes.get(ResourceLocationArgument.getId((CommandContext<CommandSourceStack>) context, "yttribume"))))
        );
        return 0;
    }

    @SuppressWarnings("DataFlowIssue")
    private static <T extends SharedSuggestionProvider> int setYttribume(CommandContext<T> context) {
        float value = FloatArgumentType.getFloat(context, "value");
        ResourceLocation resource = ResourceLocationArgument.getId((CommandContext<CommandSourceStack>) context, "yttribume");
        Yttribume yttribume = Yttribumes.get(resource);
        if (!yttribume.isRegal(value, MINECRAFT.player)) {
            sendMessageToClient(Component.translatable("message.ayame.command.yttribume.set_fail",
                    "§e" + yttribume.min(), "§e" + yttribume.max()));
        } else {
            MINECRAFT.player.ayame$setYttribume(yttribume, value);
            sendMessageToClient(Component.translatable("message.ayame.command.yttribume.set_success"));
        }
        return 0;
    }
}
