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
import com.jcwhatever.arborianprotect.regions.ProtectedRegion;
import com.jcwhatever.nucleus.managed.commands.CommandInfo;
import com.jcwhatever.nucleus.managed.commands.arguments.ICommandArguments;
import com.jcwhatever.nucleus.managed.commands.exceptions.CommandException;
import com.jcwhatever.nucleus.managed.commands.mixins.IExecutableCommand;
import com.jcwhatever.nucleus.managed.language.Localizable;
import com.jcwhatever.nucleus.managed.messaging.ChatPaginator;
import com.jcwhatever.nucleus.utils.text.TextUtils.FormatTemplate;

import org.bukkit.command.CommandSender;

import java.util.Collection;

@CommandInfo(
        command="list",
        staticParams = { "page=1" },
        description="List protected regions.",

        paramDescriptions = {
                "page= {PAGE}",
        })

public final class ListSubCommand extends AbstractProtectCommand implements IExecutableCommand {

    @Localizable static final String _PAGINATOR_TITLE =
            "Protected Regions";

    @Override
    public void execute(CommandSender sender, ICommandArguments args) throws CommandException {

        int page = args.getInteger("page");

        Collection<ProtectedRegion> regions = ArborianProtect.getRegionManager().getAll();

        ChatPaginator pagin = createPagin(args, 7, Lang.get(_PAGINATOR_TITLE));

        for (ProtectedRegion region : regions) {
            pagin.add(region.getName(), region.getWorldName() + ", priority: " + region.getPriority());
        }

        pagin.show(sender, page, FormatTemplate.LIST_ITEM_DESCRIPTION);
    }
}
