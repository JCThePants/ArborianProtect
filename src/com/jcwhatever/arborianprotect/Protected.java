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

package com.jcwhatever.arborianprotect;

import com.jcwhatever.arborianprotect.filters.BlockEventFilter;
import com.jcwhatever.arborianprotect.filters.MobEventFilter;
import com.jcwhatever.arborianprotect.filters.MobSpawnFilter;
import com.jcwhatever.arborianprotect.filters.PlayerEventFilter;
import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.storage.serialize.DeserializeException;
import com.jcwhatever.nucleus.utils.PreCon;

/**
 * Base protected target implementation.
 */
public class Protected implements IProtected {

    private String _name;
    private String _searchName;

    private MobEventFilter _mobEventFilter;
    private MobSpawnFilter _mobSpawnFilter;
    private BlockEventFilter _blockEventFilter;
    private PlayerEventFilter _playerEventFilter;

    public Protected() {}

    /**
     * Constructor.
     *
     * @param name      The name.
     * @param dataNode  The protected targets data node.
     */
    public Protected(String name, IDataNode dataNode) {
        PreCon.notNull(name);
        PreCon.notNull(dataNode);

        _name = name;
        _searchName = name.toLowerCase();

        _mobEventFilter =
                new MobEventFilter(dataNode.getNode("mob-event-filter"));

        _mobSpawnFilter =
                new MobSpawnFilter(dataNode.getNode("mob-spawn-filter"));

        _blockEventFilter =
                new BlockEventFilter(dataNode.getNode("block-event-filter"));

        _playerEventFilter =
                new PlayerEventFilter(dataNode.getNode("player-event-filter"));
    }

    @Override
    public String getName() {
        return _name;
    }

    @Override
    public String getSearchName() {
        return _searchName;
    }

    @Override
    public MobEventFilter getMobEventFilter() {
        return _mobEventFilter;
    }

    @Override
    public MobSpawnFilter getMobSpawnFilter() {
        return _mobSpawnFilter;
    }

    @Override
    public BlockEventFilter getBlockEventFilter() {
        return _blockEventFilter;
    }

    @Override
    public PlayerEventFilter getPlayerEventFilter() {
        return _playerEventFilter;
    }

    @Override
    public void importSettings(IDataNode dataNode) {
        PreCon.notNull(dataNode);

        _mobEventFilter =
                dataNode.getSerializable("mob-event-filter", MobEventFilter.class);

        if (_mobEventFilter == null)
            _mobEventFilter = new MobEventFilter(dataNode.getNode("mob-event-filter"));

        _mobSpawnFilter =
                dataNode.getSerializable("mob-spawn-filter", MobSpawnFilter.class);

        if (_mobSpawnFilter == null)
            _mobSpawnFilter = new MobSpawnFilter(dataNode.getNode("mob-spawn-filter"));

        _blockEventFilter =
                dataNode.getSerializable("block-event-filter", BlockEventFilter.class);

        if (_blockEventFilter == null)
            _blockEventFilter = new BlockEventFilter(dataNode.getNode("block-event-filter"));

        _playerEventFilter =
                dataNode.getSerializable("player-event-filter", PlayerEventFilter.class);

        if (_playerEventFilter == null)
            _playerEventFilter = new PlayerEventFilter(dataNode.getNode("player-event-filter"));
    }

    @Override
    public void serialize(IDataNode dataNode) {
        dataNode.set("name", getName());
        dataNode.set("mob-event-filter", _mobEventFilter);
        dataNode.set("mob-spawn-filter", _mobSpawnFilter);
        dataNode.set("block-event-filter", _blockEventFilter);
        dataNode.set("player-event-filter", _playerEventFilter);
    }

    @Override
    public void deserialize(IDataNode dataNode) throws DeserializeException {

        _name = dataNode.getName();
        _searchName = _name.toLowerCase();
        importSettings(dataNode);
    }
}
