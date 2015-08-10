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
import com.jcwhatever.arborianprotect.filters.BlockEventFilter;
import com.jcwhatever.arborianprotect.filters.FilterPermission;
import com.jcwhatever.arborianprotect.filters.MobEventFilter;
import com.jcwhatever.arborianprotect.filters.PlayerEventFilter;
import com.jcwhatever.nucleus.managed.commands.CommandInfo;
import com.jcwhatever.nucleus.managed.commands.arguments.ICommandArguments;
import com.jcwhatever.nucleus.managed.commands.exceptions.CommandException;
import com.jcwhatever.nucleus.managed.commands.mixins.IExecutableCommand;
import com.jcwhatever.nucleus.managed.language.Localizable;
import org.bukkit.command.CommandSender;

@CommandInfo(
        command="stasis",
        staticParams = { "targetName", "allow|deny|default" },
        flags = { "world" },
        description="Change all filters related to preserving a build from natural changes for the specified target.",

        paramDescriptions = {
                "targetName= The name of the target region or world. If targeting " +
                        "a world, the 'world' flag is required.",
                "allow|deny|default= Use 'allow' to force allow, 'deny' to deny damage," +
                        "'default' to ignore.",
                "world= Include this flag to specify that the target is a world."
        })

public final class StasisCommand
        extends AbstractProtectCommand implements IExecutableCommand {

    @Localizable static final String _SETTING = "{0: filter} set to {1: permission}.";

    @Localizable static final String _SUCCESS =
            "Stasis filters set to {0: permission} for target '{1: target}'.";

    @Override
    public void execute(CommandSender sender, ICommandArguments args) throws CommandException {

        IProtected target = getTarget(sender, args);
        if (target == null)
            return;

        FilterPermission permission = args.getEnum("allow|deny|default", FilterPermission.class);

        setFilters(sender, target, permission);

        tellSuccess(sender, Lang.get(_SUCCESS,
                permission.name(), target.getName()));
    }

    private static void tellSet(CommandSender sender, String settingName, FilterPermission permission) {
        ArborianProtect.getPlugin().getMessenger().tell(
                sender, Lang.get(_SETTING, settingName, permission.name()));
    }

    static void setFilters(CommandSender sender, IProtected target, FilterPermission permission) {
        BlockEventFilter blocks = target.getBlockEventFilter();

        blocks.setCreeperDamage(permission);
        tellSet(sender, "Creeper Damage", permission);

        blocks.setFireballDamage(permission);
        tellSet(sender, "Fireball Damage", permission);

        blocks.setTntDamage(permission);
        tellSet(sender, "TNT Damage", permission);

        blocks.setFireSpread(permission);
        tellSet(sender, "Fire Spread", permission);

        blocks.setGrassGrowth(permission);
        tellSet(sender, "Grass Growth", permission);

        blocks.setIceMelt(permission);
        tellSet(sender, "Ice Melt", permission);

        blocks.setLeafDecay(permission);
        tellSet(sender, "Leaf Decay", permission);

        blocks.setMobChangeBlock(permission);
        tellSet(sender, "Mob Change Block", permission);

        blocks.setMushroomGrowth(permission);
        tellSet(sender, "Mushroom Growth", permission);

        blocks.setMyceliumGrowth(permission);
        tellSet(sender, "Mycelium Growth", permission);

        blocks.setSnowMelt(permission);
        tellSet(sender, "Snow Melt", permission);

        blocks.setSoilDehydrate(permission);
        tellSet(sender, "Soil Dehydrate", permission);

        blocks.setVineGrowth(permission);
        tellSet(sender, "Vine Growth", permission);

        PlayerEventFilter players = target.getPlayerEventFilter();

        players.setIgnite(permission);
        tellSet(sender, "Ignite", permission);

        players.setTrampleCrops(permission);
        tellSet(sender, "Players Trample Crops", permission);

        MobEventFilter mobEvents = target.getMobEventFilter();

        mobEvents.setTrampleCrops(permission);
        tellSet(sender, "Mobs Trample Crops", permission);
    }
}

