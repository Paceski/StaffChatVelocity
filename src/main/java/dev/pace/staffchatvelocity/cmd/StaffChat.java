package dev.pace.staffchatvelocity.cmd;

import com.google.inject.Inject;
import com.mojang.brigadier.context.CommandContext;
import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

/**
 * Created by Pace
 * No part of this publication may be reproduced, disturbed, or transmitted in any form or any means.
 */

public class StaffChat {

    @Inject
    private ProxyServer proxyServer;

    public String getName(CommandSource source) {
        if (source instanceof Player)
            return ((Player) source).getUsername();
        return "Console";
    }

    public String getServerName(CommandSource source) {
        if (source instanceof Player)
            return ((Player) source).getCurrentServer().get().getServerInfo().getName();
        return "proxy";
    }

    public int execute(CommandContext<CommandSource> ctx) {
        if (ctx.getSource().hasPermission("staffchat.velocity")) {
            Component deserialized = Component.text()
                    .append(Component.text()
                            .color(NamedTextColor.DARK_GRAY)
                            .append(Component.text("["))
                            .build()
                    )
                    .append(Component.text()
                            .color(NamedTextColor.YELLOW)
                            .append(Component.text("StaffChat"))
                            .build()
                    )
                    .append(Component.text()
                            .color(NamedTextColor.DARK_GRAY)
                            .append(Component.text("] "))
                            .build()
                    )
                    .append(Component.text()
                            .color(NamedTextColor.DARK_GRAY)
                            .append(Component.text("["))
                            .build()
                    )
                    .append(Component.text()
                            .color(NamedTextColor.YELLOW)
                            .append(Component.text(getServerName(ctx.getSource())))
                            .build()
                    )
                    .append(Component.text()
                            .color(NamedTextColor.DARK_GRAY)
                            .append(Component.text("] "))
                            .build()
                    )
                    .append(Component.text()
                            .color(NamedTextColor.WHITE)
                            .append(Component.text(getName(ctx.getSource())))
                            .build()
                    )
                    .append(Component.text()
                            .color(NamedTextColor.WHITE)
                            .append(Component.text(": "))
                            .build()
                    )
                    .append(LegacyComponentSerializer.legacyAmpersand().deserialize("&f" + ctx.getArgument("message", String.class)))
                    .build();
            proxyServer.getAllPlayers().stream().filter(target -> target.hasPermission("staffchat.velocity")).forEach(player -> player.sendMessage(deserialized));
        } else {
            Component deserialized = Component.text()
                    .append(Component.text()
                            .color(NamedTextColor.RED)
                            .append(Component.text("You do not have permission."))
                            .build()
                    )
                    .build();
        }
        return 1;
    }
}
