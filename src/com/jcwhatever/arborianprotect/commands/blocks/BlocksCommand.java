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

package com.jcwhatever.arborianprotect.commands.blocks;

import com.jcwhatever.nucleus.managed.commands.CommandInfo;
import com.jcwhatever.nucleus.managed.commands.utils.AbstractCommand;

@CommandInfo(
        command="blocks",
        description="Manage block event filters.")

public final class BlocksCommand extends AbstractCommand {

    public BlocksCommand() {
        super();

        registerCommand(TntDamageSubCommand.class);
        registerCommand(CreeperDamageSubCommand.class);
        registerCommand(FireballDamageSubCommand.class);
        registerCommand(MobChangeBlockSubCommand.class);
        registerCommand(FireSpreadSubCommand.class);
        registerCommand(GrassGrowthSubCommand.class);
        registerCommand(IceMeltSubCommand.class);
        registerCommand(InfoSubCommand.class);
        registerCommand(LeafDecaySubCommand.class);
        registerCommand(MushroomGrowthSubCommand.class);
        registerCommand(MyceliumGrowthSubCommand.class);
        registerCommand(SnowMeltSubCommand.class);
        registerCommand(SoilDehydrateSubCommand.class);
        registerCommand(VineGrowthSubCommand.class);
    }
}
