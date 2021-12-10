package dev.pace.staffchatvelocity;

import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import com.velocitypowered.api.command.BrigadierCommand;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.ProxyServer;
import dev.pace.staffchatvelocity.cmd.StaffChat;

import javax.inject.Inject;
import java.util.logging.Logger;


/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

@Plugin(
        id = "staffchat",
        name = "StaffChatVelocity",
        version = "1.0",
        description = "StaffChat for Velocity Minecraft proxies.",
        url = "https://pace.is-a.dev/",
        authors = {"Pace"}
)
public class StaffChatVelocity {

    @Inject
    private Logger logger;

    @Inject
    private ProxyServer proxyServer;

    @Inject
    private StaffChat broadcastCommand;

    @Subscribe
    public void onProxyInitialization(ProxyInitializeEvent event) {
        LiteralCommandNode<CommandSource> broadcast = LiteralArgumentBuilder.<CommandSource>literal("staffchat")
                .requires(ctx -> ctx.hasPermission("staffchat.velocity"))
                .then(RequiredArgumentBuilder.<CommandSource, String>argument("message", StringArgumentType.greedyString())
                        .executes(broadcastCommand::execute)
                .build())
                .build();

        CommandManager manager = proxyServer.getCommandManager();
        manager.register(
        manager.metaBuilder("staffchat").aliases("sc").build(),
        new BrigadierCommand(broadcast));
    }
}




