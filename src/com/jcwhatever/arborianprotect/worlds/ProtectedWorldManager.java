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

package com.jcwhatever.arborianprotect.worlds;

import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.utils.managers.NamedInsensitiveDataManager;

import org.bukkit.Bukkit;
import org.bukkit.World;

import javax.annotation.Nullable;

/**
 * Manages {@link ProtectedWorld}'s.
 */
public class ProtectedWorldManager extends NamedInsensitiveDataManager<ProtectedWorld> {

    /**
     * Constructor.
     *
     * @param dataNode The data node.
     */
    public ProtectedWorldManager(IDataNode dataNode) {
        super(dataNode, true);
    }

    /**
     * Get an existing {@link ProtectedWorld} or create a new one if not found.
     *
     * @param worldName  The name of the world.
     *
     * @return  The new or existing {@link ProtectedWorld} or null if the Bukkit
     * {@link org.bukkit.World} could not be found.
     */
    @Nullable
    public ProtectedWorld getOrCreate(String worldName) {

        if (!contains(worldName)) {
            World world = Bukkit.getWorld(worldName);
            if (world == null)
                return null;

            ProtectedWorld protectedWorld = new ProtectedWorld(
                    world.getName(), getNode(world.getName()));

            add(protectedWorld);

            return protectedWorld;
        }

        return get(worldName);
    }

    @Nullable
    @Override
    protected ProtectedWorld load(String name, IDataNode itemNode) {
        return itemNode.getSerializable("", ProtectedWorld.class);
    }

    @Override
    protected void save(ProtectedWorld item, IDataNode itemNode) {
        itemNode.set("", item);
    }
}
