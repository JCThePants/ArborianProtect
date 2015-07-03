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

package com.jcwhatever.arborianprotect.filters;

import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.storage.serialize.DeserializeException;
import com.jcwhatever.nucleus.storage.serialize.IDataNodeSerializable;
import com.jcwhatever.nucleus.utils.PreCon;

/*
 * 
 */
public class MobEventFilter implements IDataNodeSerializable {

    private IDataNode _dataNode;

    private FilterPermission _trampleCrops = FilterPermission.DEFAULT;

    private MobEventFilter() {}

    /**
     * Constructor.
     *
     * @param dataNode  The filters data node.
     */
    public MobEventFilter(IDataNode dataNode) {
        PreCon.notNull(dataNode);

        _dataNode = dataNode;
    }

    /**
     * Get the non-player entity permission to trample crops.
     */
    public FilterPermission getTrampleCrops() {
        return _trampleCrops;
    }

    /**
     * Set the players permission to trample crops.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setTrampleCrops(FilterPermission permission) {
        PreCon.notNull(permission);

        _trampleCrops = permission;
        _dataNode.set("trample-crops", permission);
        _dataNode.save();
    }

    @Override
    public void serialize(IDataNode dataNode) {
        dataNode.set("trample-crops", _trampleCrops);
    }

    @Override
    public void deserialize(IDataNode dataNode) throws DeserializeException {

        _dataNode = dataNode;

        _trampleCrops = dataNode.getEnum("trample-crops",
                FilterPermission.DEFAULT, FilterPermission.class);
    }
}
