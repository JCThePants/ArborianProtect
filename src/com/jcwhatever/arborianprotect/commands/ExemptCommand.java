/*
 * This file is part of ArborianProtect for Bukkit/Spigot, licensed under the MIT License (MIT).
 *
 * Copyright (c) JCThePants (www.jcwhatever.com)
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.jcwhatever.arborianprotect.commands;

import com.jcwhatever.arborianprotect.ArborianProtect;
import com.jcwhatever.arborianprotect.Lang;
import com.jcwhatever.nucleus.managed.commands.CommandInfo;
import com.jcwhatever.nucleus.managed.commands.arguments.ICommandArguments;
import com.jcwhatever.nucleus.managed.commands.exceptions.CommandException;
import com.jcwhatever.nucleus.managed.commands.mixins.IExecutableCommand;
import com.jcwhatever.nucleus.managed.language.Localizable;
import com.jcwhatever.nucleus.utils.player.PlayerUtils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        command="exempt",
        staticParams = { "playerName=" },
        flags = {"remove"},
        description="Exempt a player from player event filters.",

        paramDescriptions = {
                "playerName= The name of the player to exempt. Leave blank to exempt self.",
                "remove= Include flag to remove exemption."
        })

public final class ExemptCommand extends AbstractProtectCommand implements IExecutableCommand {

    @Localizable static final String _PLAYER_NOT_FOUND =
            "Player not found.";

    @Localizable static final String _REMOVED =
            "'{0: player name}' removed from player event filter exemptions.";

    @Localizable static final String _ADDED =
            "'{0: player name}' added to player event filter exemptions.";

    @Override
    public void execute(CommandSender sender, ICommandArguments args) throws CommandException {

        Player player;

        if (args.isDefaultValue("playerName")) {
            CommandException.checkNotConsole(getPlugin(), this, sender);

            player = (Player)sender;
        }
        else {
            String playerName = args.getString("playerName");
            player = PlayerUtils.getPlayer(playerName);
        }

        if (player == null) {
            tellError(sender, Lang.get(_PLAYER_NOT_FOUND));
            return;
        }

        boolean isRemove = args.getBoolean("remove");

        ArborianProtect.setExempt(player, !isRemove);

        if (isRemove)
            tellSuccess(sender, Lang.get(_REMOVED, player.getName()));
        else
            tellSuccess(sender, Lang.get(_ADDED, player.getName()));
    }
}
