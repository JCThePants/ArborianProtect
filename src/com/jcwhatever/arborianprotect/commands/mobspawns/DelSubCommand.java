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
import org.bukkit.entity.EntityType;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

@CommandInfo(
        command="del",
        staticParams = { "targetName", "entityType", "spawnReason=all" },
        flags = { "world" },
        description="Remove a mob spawning filter from the specified target.",

        paramDescriptions = {
                "targetName= The name of the target region or world. If targeting " +
                        "a world, the 'world' flag is required.",
                "entityType= The entity type filter that is being removed.",
                "spawnReason= Optional spawn reason filter to remove. Default is all.",
                "world= Include this flag to specify that the target is a world."
        })

public final class DelSubCommand extends AbstractProtectCommand implements IExecutableCommand {

    @Localizable static final String _SUCCESS_TYPE_ONLY =
            "Mob spawn filter for type '{0: entity type}' removed from '{1: target}'.";

    @Localizable static final String _SUCCESS_TYPE_AND_REASON =
            "Mob spawn filter for type '{0: entity type}' and reason '{1: reason}' removed from '{2: target}'.";

    @Override
    public void execute(CommandSender sender, ICommandArguments args) throws CommandException {

        EntityType entityType = args.getEnum("entityType", EntityType.class);
        SpawnReason reason = args.isDefaultValue("spawnReason")
                ? null
                : args.getEnum("spawnReason", SpawnReason.class);

        IProtected target = getTarget(sender, args);
        if (target == null)
            return;

        if (reason == null) {
            target.getMobSpawnFilter().removeFilter(entityType);
            tellSuccess(sender, Lang.get(_SUCCESS_TYPE_ONLY,
                    entityType.name(), target.getName()));
        } else {
            target.getMobSpawnFilter().removeFilter(entityType, reason);
            tellSuccess(sender, Lang.get(_SUCCESS_TYPE_AND_REASON,
                    entityType.name(), reason.name(), target.getName()));
        }
    }
}
