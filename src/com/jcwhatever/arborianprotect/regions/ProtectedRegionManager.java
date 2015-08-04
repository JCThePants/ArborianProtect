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

import com.jcwhatever.arborianprotect.ArborianProtect;
import com.jcwhatever.nucleus.providers.regionselect.IRegionSelection;
import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.utils.PreCon;
import com.jcwhatever.nucleus.utils.managers.NamedInsensitiveDataManager;

import javax.annotation.Nullable;

/**
 * Manages {@link ProtectedRegion}'s.
 */
public class ProtectedRegionManager extends NamedInsensitiveDataManager<ProtectedRegion> {

    /**
     * Constructor.
     *
     * @param dataNode The data node.
     */
    public ProtectedRegionManager(IDataNode dataNode) {
        super(dataNode, true);
    }

    @Nullable
    public ProtectedRegion add(String name, IRegionSelection selection) {
        PreCon.validNodeName(name);
        PreCon.notNull(selection);

        if (contains(name))
            return null;

        IDataNode dataNode = getNode(name);

        ProtectedRegion region = new ProtectedRegion(ArborianProtect.getPlugin(), name, dataNode);
        region.setCoords(selection.getP1(), selection.getP2());

        add(region);

        return region;
    }

    @Nullable
    @Override
    protected ProtectedRegion load(String name, IDataNode itemNode) {
        return new ProtectedRegion(ArborianProtect.getPlugin(), name, itemNode);
    }

    @Override
    protected void save(ProtectedRegion item, IDataNode itemNode) {
        // do nothing
    }
}
