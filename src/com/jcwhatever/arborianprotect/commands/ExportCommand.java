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
import com.jcwhatever.nucleus.storage.JsonDataNode;
import org.bukkit.command.CommandSender;

import java.io.File;

@CommandInfo(
        command="export",
        staticParams = { "filename", "targetName" },
        flags = { "world" },
        description="Export settings from the specified target.",

        paramDescriptions = {
                "filename= The name of the file to export to, excluding the .json extension. " +
                        "Files exported to import_export folder.",
                "targetName= The name of the target region or world. If targeting " +
                        "a world, the 'world' flag is required.",
                "world= Include this flag to specify that the target is a world."
        })

public final class ExportCommand
        extends AbstractProtectCommand implements IExecutableCommand {

    @Localizable static final String _ERROR =
            "Failed to import settings from file '{0: filename}'.";

    @Localizable static final String _SUCCESS =
            "Settings from file '{0: filename}' imported into target '{1: target}'.";

    @Override
    public void execute(CommandSender sender, ICommandArguments args) throws CommandException {

        IProtected target = getTarget(sender, args);
        if (target == null)
            return;

        String filename = args.getString("filename");
        File folder = new File(ArborianProtect.getPlugin().getDataFolder(), "import_export");
        if (!folder.exists() && !folder.mkdirs())
            throw new CommandException(Lang.get(_ERROR, folder.getName()));

        File file = new File(folder, filename + ".json");

        JsonDataNode dataNode = new JsonDataNode(ArborianProtect.getPlugin(), file);
        target.serialize(dataNode);
        dataNode.save();

        tellSuccess(sender, Lang.get(_SUCCESS,
                file.getName(), target.getName()));
    }
}
