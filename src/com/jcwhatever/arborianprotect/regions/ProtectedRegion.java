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

package com.jcwhatever.arborianprotect.regions;

import com.jcwhatever.arborianprotect.IProtected;
import com.jcwhatever.arborianprotect.Protected;
import com.jcwhatever.arborianprotect.filters.BlockEventFilter;
import com.jcwhatever.arborianprotect.filters.MobEventFilter;
import com.jcwhatever.arborianprotect.filters.MobSpawnFilter;
import com.jcwhatever.arborianprotect.filters.PlayerEventFilter;
import com.jcwhatever.nucleus.regions.Region;
import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.storage.serialize.DeserializeException;
import com.jcwhatever.nucleus.utils.PreCon;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;

/**
 * A protected region.
 */
public class ProtectedRegion extends Region implements IProtected {

    private final Protected _target;

    /**
     * Constructor.
     *
     * @param plugin    The owning plugin.
     * @param name      The region name.
     * @param dataNode  The regions data node.
     */
    public ProtectedRegion(Plugin plugin, String name, @Nonnull IDataNode dataNode) {
        super(plugin, name, dataNode);

        PreCon.notNull(dataNode);

        _target = dataNode.getSerializable("", Protected.class);
    }

    @Override
    public MobEventFilter getMobEventFilter() {
        return _target.getMobEventFilter();
    }

    @Override
    public MobSpawnFilter getMobSpawnFilter() {
        return _target.getMobSpawnFilter();
    }

    @Override
    public BlockEventFilter getBlockEventFilter() {
        return _target.getBlockEventFilter();
    }

    @Override
    public PlayerEventFilter getPlayerEventFilter() {
        return _target.getPlayerEventFilter();
    }

    @Override
    public void importSettings(IDataNode dataNode) {
        _target.importSettings(dataNode);
    }

    @Override
    public void setPriority(int priority) {
        super.setPriority(priority);
    }

    @Override
    public void serialize(IDataNode dataNode) {
        _target.serialize(dataNode);
    }

    @Override
    public void deserialize(IDataNode dataNode) throws DeserializeException {
        _target.deserialize(dataNode);
    }
}
