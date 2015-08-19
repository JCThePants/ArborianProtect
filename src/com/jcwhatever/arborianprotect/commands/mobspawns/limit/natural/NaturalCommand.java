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

package com.jcwhatever.arborianprotect.commands.mobspawns.limit.natural;

import com.jcwhatever.arborianprotect.IProtected;
import com.jcwhatever.arborianprotect.Lang;
import com.jcwhatever.arborianprotect.commands.AbstractProtectCommand;
import com.jcwhatever.nucleus.managed.commands.CommandInfo;
import com.jcwhatever.nucleus.managed.commands.arguments.ICommandArguments;
import com.jcwhatever.nucleus.managed.commands.exceptions.CommandException;
import com.jcwhatever.nucleus.managed.commands.mixins.IExecutableCommand;
import com.jcwhatever.nucleus.managed.language.Localizable;
import org.bukkit.command.CommandSender;

@CommandInfo(
        command={ "natural" },
        staticParams = { "targetName", "isEnabled=" },
        flags = { "world" },
        description="Manage natural mob spawn limiting.")

public final class NaturalCommand extends AbstractProtectCommand implements IExecutableCommand {

    @Localizable static final String _INFO_ENABLED =
            "Natural mob spawn limiter for '{0: target}' is currently enabled.\n" +
                    "The spawn limit is {1: spawn limit}";

    @Localizable static final String _INFO_DISABLED =
            "Natural mob spawn limiter for '{0: target}' is currently disabled.";

    @Localizable static final String _SET_ENABLED =
            "Natural mob spawn limiter for '{0: target}' has been enabled.\n" +
                    "The spawn limit is {1: spawn limit}";

    @Localizable static final String _SET_DISABLED =
            "Natural mob spawn limiter for '{0: target}' has been disabled.";

    public NaturalCommand() {
        super();

        registerCommand(RadiusSubCommand.class);
        registerCommand(SetSubCommand.class);
    }

    @Override
    public void execute(CommandSender sender, ICommandArguments args) throws CommandException {

        IProtected target = getTarget(sender, args);
        if (target == null)
            return; // finished

        if (args.isDefaultValue("isEnabled")) {
            boolean isEnabled = target.getMobSpawnFilter().isNaturalLimitEnabled();

            tell(sender, Lang.get(isEnabled ? _INFO_ENABLED : _INFO_DISABLED, target.getName(),
                    target.getMobSpawnFilter().getNaturalLimit()));
            return;
        }

        boolean isEnabled = args.getBoolean("isEnabled");

        target.getMobSpawnFilter().setNaturalLimitEnabled(isEnabled);

        tellSuccess(sender, Lang.get(isEnabled ? _SET_ENABLED : _SET_DISABLED, target.getName(),
                target.getMobSpawnFilter().getNaturalLimit()));
    }
}