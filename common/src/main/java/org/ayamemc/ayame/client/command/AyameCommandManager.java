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

import com.mojang.brigadier.Command;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import org.ayamemc.ayame.Ayame;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.client.renderer.AyamePlayerRender;
import org.ayamemc.ayame.client.script.JavaScriptLoader;
import org.ayamemc.ayame.model.AyameModelData;
import org.ayamemc.ayame.model.sync.ModelSelection;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.mozilla.javascript.Context;
import software.bernie.geckolib.util.CompoundException;

import java.util.UUID;
import java.util.stream.Collectors;

import static org.ayamemc.ayame.client.AyameClient.MINECRAFT;


@SuppressWarnings("unchecked")
public class AyameCommandManager {
    private static final SuggestionProvider<?> MODEL_LIST = (context, builder) -> {
        for (InMemoryModelData modelData : AyameClient.modelManagerClient.getAllModels()) {
            builder.suggest(modelData.getId());
        }
        return builder.buildFuture();
    };

    public static <T extends SharedSuggestionProvider> void createCommands(CommandDispatcher<T> dispatcher, CommandBuildContext cx) {
        // ------------------------------------------ ayame -------------------------------------------------------------------------
        dispatcher.register(LiteralArgumentBuilder.<T>literal("ayame")
                .then(LiteralArgumentBuilder.<T>literal("animation")
                        .then(LiteralArgumentBuilder.<T>literal("play")
                                .then(RequiredArgumentBuilder.<T, String>argument("animation_name", StringArgumentType.string())
                                        .then(RequiredArgumentBuilder.<T, Boolean>argument("is_loop", BoolArgumentType.bool())
                                                .executes(context1 -> {
                                                    MINECRAFT.player.ayame$playAnimation(context1.getArgument("animation_name", String.class), context1.getArgument("is_loop", Boolean.class));

                                                    Ayame.LOGGER.info("Play animation {}", context1.getArgument("animation_name", String.class));
                                                    return Command.SINGLE_SUCCESS;
                                                })
                                        )

                                )
                        )
                        .then(LiteralArgumentBuilder.<T>literal("reset")
                                .executes(context1 -> {
                                    MINECRAFT.player.ayame$resetAnimation();
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )


                .then(LiteralArgumentBuilder.<T>literal("molang")
                        .then(LiteralArgumentBuilder.<T>literal("exec").then(
                                RequiredArgumentBuilder.<T, String>argument("code", StringArgumentType.string())
                                        .executes(context -> {
                                            try {
                                                final String code = StringArgumentType.getString(context, "code");
                                                final double result = AyamePlayerRender.execMolang(code);

                                                sendMessageToClient(Component.translatable("message.ayame.command.script.exec.result", result));

                                            } catch (CompoundException e) {
                                                sendMessageToClient(Component.translatable("message.ayame.command.script.exec.error", e));
                                            }
                                            return Command.SINGLE_SUCCESS;
                                        })
                        ))
                )

                .then(LiteralArgumentBuilder.<T>literal("typescript")
                        .then(LiteralArgumentBuilder.<T>literal("execJs").then(
                                        RequiredArgumentBuilder.<T, String>argument("code", StringArgumentType.string())
                                                .executes(context -> {
                                                    String code = StringArgumentType.getString(context, "code");

                                                    Object result = JavaScriptLoader.runCode(code);
                                                    if (result instanceof Exception e) {
                                                        sendMessageToClient(Component.translatable("message.ayame.command.script.exec.error", e.getMessage()));
                                                    } else if (result != null) {
                                                        sendMessageToClient(Component.translatable("message.ayame.command.script.exec.result", Context.toString(result)));
                                                    }


                                                    return Command.SINGLE_SUCCESS;
                                                })
                                )

                        )

                        .then(LiteralArgumentBuilder.<T>literal("version")
                                .executes((context -> {
                                    new Thread(() -> {
                                        final String tsVersion = JavaScriptLoader.getTsVersion();
                                        if (tsVersion == null) {
                                            return;
                                        }
                                        sendMessageToClient(Component.translatable("message.ayame.command.ts.version", tsVersion));
                                    }, "Ayame-TSC-Version").start();
                                    return Command.SINGLE_SUCCESS;
                                }))
                        )
                        .then(LiteralArgumentBuilder.<T>literal("reload")
                                .executes(context -> {
                                    JavaScriptLoader.reloadTs();
                                    return Command.SINGLE_SUCCESS;
                                })
                        )

                )

                .then(LiteralArgumentBuilder.<T>literal("model")
                        .then(LiteralArgumentBuilder.<T>literal("set")
                                .then(RequiredArgumentBuilder.<T, String>argument("model_id", StringArgumentType.string())
                                        .executes(AyameCommandManager::setModel)
                                        .suggests((SuggestionProvider<T>) MODEL_LIST)
                                ))

                        .then(LiteralArgumentBuilder.<T>literal("rescan")
                                .executes(commandContext -> {
                                    AyameClient.loadAllModelLocal().whenComplete((r, ex) -> {
                                        sendMessageToClient(Component.translatable("message.ayame.command.model.rescan.successes"));
                                    });
                                    return Command.SINGLE_SUCCESS;
                                }))

                        .then(LiteralArgumentBuilder.<T>literal("reload")
                                .executes(commandContext -> {
                                    final ModelSelection selection = AyameClient.modelManagerClient.getModelOfPlayer(MINECRAFT.player.getUUID());
                                    final InMemoryModelData modelRes = AyameClient.modelManagerClient.getModel(selection.getId());


                                    if (modelRes == null) {
                                        sendMessageToClient(Component.translatable("message.ayame.command.reload.failed"));
                                        return 0;
                                    }

                                    // TODO: 只重载特定模型
                                    AyameClient.modelManagerClient.updateAndReloadModelOfPlayer(MINECRAFT.player.getUUID(), modelRes.getFallbackModelSelection());
                                    sendMessageToClient(Component.translatable("message.ayame.command.model.reload.successes"));
                                    return Command.SINGLE_SUCCESS;
                                })
                        )

                        .then(LiteralArgumentBuilder.<T>literal("list")
                                .executes(commandContext -> {
                                    final String allModels = AyameClient.modelManagerClient.getAllModels().stream()
                                            .map(resource -> {
                                                        final AyameModelData.MetaData metaData = resource.getMetaData();

                                                        return "§e" + metaData.id + " " + "(" + metaData.name + ")";
                                                    }
                                            )
                                            .collect(Collectors.joining("\n"));

                                    sendMessageToClient(Component.translatable("message.ayame.command.model.list", allModels));
                                    return Command.SINGLE_SUCCESS;
                                })
                        )
                )
        );


        // -----------------------------------------------------------------------------------------------
        YttribumeCommand.init(dispatcher, cx);

        // 重定向到
        dispatcher.register(LiteralArgumentBuilder.<T>literal("aym").redirect(dispatcher.getRoot().getChild("ayame")));

    }

    private static <T extends SharedSuggestionProvider> int setModel(CommandContext<T> context) {
        // TODO 暂时只用于测试，需要后续完善
        final String name = StringArgumentType.getString(context, "model_id");
        final InMemoryModelData modelData = AyameClient.modelManagerClient.getModel(name);

        if (modelData == null) {
            sendMessageToClient(Component.translatable("message.ayame.command.model.set.failed", name));
            return 0;
        }

        final UUID targetPlayer = MINECRAFT.player.getUUID(); // Client-only

        // TODO - ???
        // TODO - 这东西怎么设置玩家模型的到底?
        // TODO: 只重载特定模型
        AyameClient.modelManagerClient.updateAndReloadModelOfPlayer(targetPlayer, modelData.getFallbackModelSelection());

        sendMessageToClient(
                Component.translatable(
                        "message.ayame.command.model.set.successes",
                        "§e" + modelData.getMetaData().name,
                        "§e" + modelData.getMetaData().authors.getFirst()
                )
        );

        return Command.SINGLE_SUCCESS;
    }

    @SuppressWarnings("DataFlowIssue")
    public static void sendMessageToClient(Component message) {
        MINECRAFT.player.displayClientMessage(message, false);
    }


}
