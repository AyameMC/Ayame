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
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;


public class AyameCommandManager {
    public static <T extends SharedSuggestionProvider> void createCommands(CommandDispatcher<T> dispatcher, CommandBuildContext context) {
        // ------------------------------------------ ayame -------------------------------------------------------------------------
        dispatcher.register(LiteralArgumentBuilder.<T>literal("ayame")

                        .then(LiteralArgumentBuilder.<T>literal("model")

                        )


        );
        // -----------------------------------------------------------------------------------------------


        // aym重定向到ayame
        dispatcher.register(LiteralArgumentBuilder.<T>literal("aym").redirect(dispatcher.getRoot().getChild("ayame")));

        // 注册yttribume命令
        YttribumeCommand.init(dispatcher,context);
    }


//    private static Yttribume getByString(String yttribume) {
//        String[] yttribumeSplit = yttribume.split(":");
//        String namespace = yttribumeSplit[0];
//
//        String path = yttribumeSplit[1];
//        if (namespace.isEmpty()){
//            return Yttribumes.get(ResourceLocation.fromNamespaceAndPath("ayame", path));
//        }
//        return Yttribumes.get(ResourceLocation.fromNamespaceAndPath(namespace, path));
//    }

}
