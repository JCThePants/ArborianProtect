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
import com.jcwhatever.arborianprotect.filters.FilterPolicy;
import com.jcwhatever.nucleus.commands.CommandInfo;
import com.jcwhatever.nucleus.commands.arguments.CommandArguments;
import com.jcwhatever.nucleus.commands.exceptions.CommandException;
import com.jcwhatever.nucleus.utils.language.Localizable;

import org.bukkit.command.CommandSender;

@CommandInfo(
        command="policy",
        staticParams = { "targetName", "whitelist|blacklist|info=info" },
        flags = { "world" },
        description="Set or view the mob spawning filter policy for the specified target.",

        paramDescriptions = {
                "targetName= The name of the target region or world. If targeting " +
                        "a world, the 'world' flag is required.",
                "whitelist|blacklist|info= Use 'whitelist' to set the policy to whitelist, " +
                        "'blacklist' to set to blacklist, leave blank or type 'info' to see the " +
                        "current setting.",
                "world= Include this flag to specify that the target is a world."
        })

public final class PolicySubCommand extends AbstractProtectCommand {

    @Localizable static final String _INFO =
            "Filter policy for '{0: target}' is {1: policy}.";

    @Localizable static final String _SET =
            "Mob spawn filter policy for '{0: target}' set to {1: policy}.";

    @Override
    public void execute(CommandSender sender, CommandArguments args) throws CommandException {

        IProtected target = getTarget(sender, args);
        if (target == null)
            return;

        if (args.getString("whitelist|blacklist|info").equalsIgnoreCase("info")) {

            FilterPolicy policy = target.getMobSpawnFilter().getPolicy();

            tell(sender, Lang.get(_INFO, target.getName(), policy.name()));
            return; // finished
        }

        FilterPolicy policy = args.getEnum("whitelist|blacklist|info", FilterPolicy.class);
        target.getMobSpawnFilter().setPolicy(policy);

        tellSuccess(sender, Lang.get(_SET,
                target.getName(), policy.name()));
    }
}
