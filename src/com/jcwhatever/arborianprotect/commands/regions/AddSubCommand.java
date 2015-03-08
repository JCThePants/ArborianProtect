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

package com.jcwhatever.arborianprotect.commands.regions;

import com.jcwhatever.arborianprotect.ArborianProtect;
import com.jcwhatever.arborianprotect.Lang;
import com.jcwhatever.arborianprotect.commands.AbstractProtectCommand;
import com.jcwhatever.nucleus.commands.CommandInfo;
import com.jcwhatever.nucleus.commands.arguments.CommandArguments;
import com.jcwhatever.nucleus.commands.exceptions.CommandException;
import com.jcwhatever.nucleus.regions.selection.IRegionSelection;
import com.jcwhatever.nucleus.utils.language.Localizable;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@CommandInfo(
        command="add",
        staticParams = { "regionName" },
        description="Add a protected region using your current region selection.",

        paramDescriptions = {
                "regionName= The name of the region to create.",
        })

public final class AddSubCommand extends AbstractProtectCommand {

    @Localizable static final String _ALREADY_EXISTS =
            "A protected region named '{0: region name}' already exists.";

    @Localizable static final String _FAILED =
            "Failed to add protected region.";

    @Localizable static final String _SUCCESS =
            "Added protected region '{0: region name}'.";

    @Override
    public void execute(CommandSender sender, CommandArguments args) throws CommandException {

        CommandException.checkNotConsole(this, sender);

        String name = args.getName("regionName", 45);

        IRegionSelection selection = getRegionSelection((Player)sender);
        if (selection == null)
            return; // finished

        if (ArborianProtect.getRegionManager().contains(name)) {
            tellError(sender, Lang.get(_ALREADY_EXISTS, name));
            return; // finished
        }

        if (ArborianProtect.getRegionManager().add(name, selection) == null) {
            tellError(sender, Lang.get(_FAILED));
            return; // finished
        }

        tellSuccess(sender, Lang.get(_SUCCESS, name));
    }
}
