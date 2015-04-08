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
import com.jcwhatever.arborianprotect.IProtected;
import com.jcwhatever.arborianprotect.Lang;
import com.jcwhatever.nucleus.managed.commands.CommandInfo;
import com.jcwhatever.nucleus.managed.commands.arguments.ICommandArguments;
import com.jcwhatever.nucleus.managed.commands.exceptions.CommandException;
import com.jcwhatever.nucleus.managed.commands.mixins.IExecutableCommand;
import com.jcwhatever.nucleus.managed.language.Localizable;
import com.jcwhatever.nucleus.managed.messaging.ChatPaginator;
import com.jcwhatever.nucleus.utils.text.TextUtils.FormatTemplate;

import org.bukkit.command.CommandSender;

@CommandInfo(
        command="info",
        staticParams = { "targetName", "page=1" },
        flags = { "world" },
        description="Get filter settings for the specified target.",

        paramDescriptions = {
                "targetName= The name of the target region or world. If targeting " +
                        "a world, the 'world' flag is required.",
                "page= {PAGE}",
                "world= Include this flag to specify that the target is a world."
        })

public final class InfoCommand extends AbstractProtectCommand implements IExecutableCommand {

    @Localizable static final String _PAGINATOR_TITLE =
            "Filters: {0: target}";

    @Localizable static final String _HEADER_BLOCKS =
            "BLOCK EVENTS";

    @Localizable static final String _HEADER_PLAYERS =
            "PLAYER EVENTS";

    @Localizable static final String _HEADER_MOBS =
            "MOB SPAWNING";

    @Override
    public void execute(CommandSender sender, ICommandArguments args) throws CommandException {

        int page = args.getInteger("page");

        IProtected target = getTarget(sender, args);
        if (target == null)
            return;

        ChatPaginator pagin = new ChatPaginator(ArborianProtect.getPlugin(),
                7, Lang.get(_PAGINATOR_TITLE, target.getName()));

        pagin.addFormatted(FormatTemplate.SUB_HEADER, Lang.get(_HEADER_BLOCKS));
        InfoPaginFiller.blockFilter(pagin, target);

        pagin.addFormatted(FormatTemplate.SUB_HEADER, Lang.get(_HEADER_PLAYERS));
        InfoPaginFiller.playerFilter(pagin, target);

        pagin.addFormatted(FormatTemplate.SUB_HEADER, Lang.get(_HEADER_MOBS));
        InfoPaginFiller.mobSpawns(pagin, target);

        pagin.show(sender, page, FormatTemplate.LIST_ITEM_DESCRIPTION);
    }
}