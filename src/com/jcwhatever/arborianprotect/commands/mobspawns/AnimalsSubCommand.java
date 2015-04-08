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

package com.jcwhatever.arborianprotect.commands.mobspawns;

import com.jcwhatever.arborianprotect.IProtected;
import com.jcwhatever.arborianprotect.Lang;
import com.jcwhatever.arborianprotect.commands.AbstractProtectCommand;
import com.jcwhatever.nucleus.managed.commands.CommandInfo;
import com.jcwhatever.nucleus.managed.commands.arguments.ICommandArguments;
import com.jcwhatever.nucleus.managed.commands.exceptions.CommandException;
import com.jcwhatever.nucleus.managed.commands.mixins.IExecutableCommand;
import com.jcwhatever.nucleus.managed.language.Localizable;

import org.bukkit.command.CommandSender;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

@CommandInfo(
        command="animals",
        staticParams = { "targetName" },
        flags = { "remove", "world", "natural" },
        description="Adds animal spawning to the filter of the specified target.",

        paramDescriptions = {
                "targetName= The name of the target region or world. If targeting " +
                        "a world, the 'world' flag is required.",
                "remove= Include flag to remove animal spawning from filter.",
                "world= Include this flag to specify that the target is a world.",
                "natural= Include this flag to target only natural spawns."
        })

public final class AnimalsSubCommand extends AbstractProtectCommand implements IExecutableCommand {

    @Localizable static final String _REMOVED =
            "Animals removed from spawn filter in '{0: target}'.";

    @Localizable static final String _ADDED =
            "Animals added to spawn filter in '{0: target}'.";

    @Override
    public void execute(CommandSender sender, ICommandArguments args) throws CommandException {

        boolean isRemove = args.getBoolean("remove");
        SpawnReason reason = args.getBoolean("natural")
                ? SpawnReason.NATURAL
                : null;

        IProtected target = getTarget(sender, args);
        if (target == null)
            return;

        if (isRemove) {
            target.getMobSpawnFilter().removeAnimals(reason);
            tellSuccess(sender, Lang.get(_REMOVED, target.getName()));
        } else {
            target.getMobSpawnFilter().addAnimals(reason);
            tellSuccess(sender, Lang.get(_ADDED, target.getName()));
        }
    }
}
