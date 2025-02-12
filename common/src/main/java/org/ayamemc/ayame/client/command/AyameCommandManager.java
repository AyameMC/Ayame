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
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.network.chat.Component;
import org.ayamemc.ayame.client.AyameClient;
import org.ayamemc.ayame.model.AyameModelData;
import org.ayamemc.ayame.model.resource.IModelResource;
import org.ayamemc.ayame.model.sync.ModelSelection;
import org.ayamemc.ayame.model.sync.data.InMemoryModelData;
import org.mozilla.javascript.Context;
import org.mozilla.javascript.Scriptable;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import static org.ayamemc.ayame.Ayame.MINECRAFT;


@SuppressWarnings("unchecked")
public class AyameCommandManager {
    private static final SuggestionProvider<?> MODEL_LIST = (context, builder) -> {
        for (InMemoryModelData modelData : AyameClient.modelManagerClient.getAllModels()) {
            builder.suggest(modelData.getId());
        }
        return builder.buildFuture();
    };

    public static <T extends SharedSuggestionProvider> void createCommands(CommandDispatcher<T> dispatcher, CommandBuildContext context) {
        // ------------------------------------------ ayame -------------------------------------------------------------------------
        dispatcher.register(LiteralArgumentBuilder.<T>literal("ayame")
                .then(LiteralArgumentBuilder.<T>literal("benchmark-compiled")
                        .executes(AyameCommandManager::benchmarkCompiled)
                )

                .then(LiteralArgumentBuilder.<T>literal("benchmark-interpreted")
                        .executes(AyameCommandManager::benchmarkInterpreted)
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
                                    return 0;
                                }))

                        .then(LiteralArgumentBuilder.<T>literal("reload")
                                .executes(commandContext -> {
                                    final ModelSelection selection = AyameClient.modelManagerClient.getModelOfPlayer(MINECRAFT.player.getUUID());
                                    final IModelResource modelRes = AyameClient.modelManagerClient.getModel(selection.getId());

                                    if (modelRes == null) {
                                        sendMessageToClient(Component.translatable("message.ayame.command.reload.failed"));
                                        return 1;
                                    }

                                    AyameClient.modelManagerClient.updateModelOfPlayer(MINECRAFT.player.getUUID(), modelRes.getFallbackModelSelection());
                                    return 0;
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
                                    return 0;
                                })
                        )
                )
        );


        // -----------------------------------------------------------------------------------------------
        YttribumeCommand.init(dispatcher, context);

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
        AyameClient.modelManagerClient.updateModelOfPlayer(targetPlayer, modelData.getFallbackModelSelection());

        sendMessageToClient(
                Component.translatable(
                        "message.ayame.command.model.set.successes",
                        "§e" + modelData.getMetaData().name,
                        "§e" + modelData.getMetaData().authors.getFirst()
                )
        );

        return 1;
    }

    private static <T extends SharedSuggestionProvider> int benchmarkTest(CommandContext<T> tCommandContext, boolean useInterpretedMode) {
        final var minecraft = Minecraft.getInstance();
        int numRuns = 10;
        final double[] totalTime = {0};
        String mode = useInterpretedMode ? "解释模式" : "编译模式";

        // 异步执行基准测试
        CompletableFuture.runAsync(() -> {
            sendMessageToClient(Component.literal("Rhino" + mode + "测试开始！"));

            for (int i = 0; i < numRuns; i++) {
                double elapsedTime = rhinoBenchmark(useInterpretedMode);
                totalTime[0] += elapsedTime;
                int runIndex = i + 1;

                minecraft.execute(() -> {
                    sendMessageToClient(Component.literal("（Rhino" + mode + "）第" + runIndex + "次 - 耗时: " + elapsedTime + " ms"));
                });
            }

            double averageTime = totalTime[0] / numRuns;

            minecraft.execute(() -> {
                sendMessageToClient(Component.literal("（Rhino" + mode + "）平均时间: " + averageTime + " ms"));
                sendMessageToClient(Component.literal("Rhino" + mode + "基准测试完成！"));
            });
        });

        return 0;
    }

    @SuppressWarnings("DataFlowIssue")
    public static void sendMessageToClient(Component message) {
        MINECRAFT.player.displayClientMessage(message, false);
    }

    public static double rhinoBenchmark(boolean useInterpretedMode) {
        Context context = Context.enter();
        context.setInterpretedMode(useInterpretedMode);
        Scriptable scope = context.initStandardObjects();

        String script = "function fibonacci(n) { " +
                "if (n <= 1) return n; " +
                "return fibonacci(n - 1) + fibonacci(n - 2); } " +
                "fibonacci(35);";

        long startTime = System.nanoTime();
        context.evaluateString(scope, script, "<cmd>", 1, null);
        long endTime = System.nanoTime();

        return (endTime - startTime) / 1e6; // 转换为毫秒
    }

    private static <T extends SharedSuggestionProvider> int benchmarkInterpreted(CommandContext<T> tCommandContext) {
        return benchmarkTest(tCommandContext, true);
    }

    private static <T extends SharedSuggestionProvider> int benchmarkCompiled(CommandContext<T> tCommandContext) {
        return benchmarkTest(tCommandContext, false);
    }


}
