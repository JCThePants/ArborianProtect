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

import com.jcwhatever.nucleus.storage.DeserializeException;
import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.storage.IDataNodeSerializable;
import com.jcwhatever.nucleus.utils.PreCon;

/**
 * Player event filter settings.
 */
public class PlayerEventFilter implements IDataNodeSerializable {

    private IDataNode _dataNode;

    private FilterPermission _pvp = FilterPermission.DEFAULT;//
    private FilterPermission _break = FilterPermission.DEFAULT;//
    private FilterPermission _place = FilterPermission.DEFAULT;//
    private FilterPermission _ignite = FilterPermission.DEFAULT;//
    private FilterPermission _useMachines = FilterPermission.DEFAULT;//
    private FilterPermission _throwSwitch = FilterPermission.DEFAULT;//
    private FilterPermission _openDoor = FilterPermission.DEFAULT;//
    private FilterPermission _openChest = FilterPermission.DEFAULT;//
    private FilterPermission _openGate = FilterPermission.DEFAULT;//
    private FilterPermission _openTrapDoor = FilterPermission.DEFAULT;//
    private FilterPermission _chat = FilterPermission.DEFAULT;
    private FilterPermission _trampleCrops = FilterPermission.DEFAULT;
    // block place private FilterPermission _poorWaterBucket = FilterPermission.DEFAULT;
    // block place private FilterPermission _poorLavaBucket = FilterPermission.DEFAULT;

    private PlayerEventFilter() {}

    /**
     * Constructor.
     *
     * @param dataNode  The filters data node.
     */
    public PlayerEventFilter(IDataNode dataNode) {
        PreCon.notNull(dataNode);

        _dataNode = dataNode;
    }

    /**
     * Get the PVP permission.
     */
    public FilterPermission getPVP() {
        return _pvp;
    }

    /**
     * Set the PVP permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setPVP(FilterPermission permission) {
        PreCon.notNull(permission);

        _pvp = permission;
        _dataNode.set("pvp", permission);
        _dataNode.save();
    }

    /**
     * Get the player block/item break permission.
     */
    public FilterPermission getBreak() {
        return _break;
    }

    /**
     * Set the player block/item break permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setBreak(FilterPermission permission) {
        PreCon.notNull(permission);

        _break = permission;
        _dataNode.set("break", permission);
        _dataNode.save();
    }

    /**
     * Get the player block/item place permission.
     */
    public FilterPermission getPlace() {
        return _place;
    }

    /**
     * Set the player block/item place permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setPlace(FilterPermission permission) {
        PreCon.notNull(permission);

        _place = permission;
        _dataNode.set("place", permission);
        _dataNode.save();
    }

    /**
     * Get the player ignite (flint and steel) permission.
     */
    public FilterPermission getIgnite() {
        return _ignite;
    }

    /**
     * Set the player ignite (flint and steel) permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setIgnite(FilterPermission permission) {
        PreCon.notNull(permission);

        _ignite = permission;
        _dataNode.set("ignite", permission);
        _dataNode.save();
    }

    /**
     * Get the player machine usage permission.
     */
    public FilterPermission getUseMachines() {
        return _useMachines;
    }

    /**
     * Set the player machine usage permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setUseMachines(FilterPermission permission) {
        PreCon.notNull(permission);

        _useMachines = permission;
        _dataNode.set("use-machines", permission);
        _dataNode.save();
    }

    /**
     * Get the players redstone switch permission.
     */
    public FilterPermission getThrowSwitch() {
        return _throwSwitch;
    }

    /**
     * Set the players redstone switch permission.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setThrowSwitch(FilterPermission permission) {
        PreCon.notNull(permission);

        _throwSwitch = permission;
        _dataNode.set("throw-switch", permission);
        _dataNode.save();
    }

    /**
     * Get the players permission to open doors.
     */
    public FilterPermission getOpenDoor() {
        return _openDoor;
    }

    /**
     * Set the players permission to open doors.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setOpenDoor(FilterPermission permission) {
        PreCon.notNull(permission);

        _openDoor = permission;
        _dataNode.set("open-door", permission);
        _dataNode.save();
    }

    /**
     * Get the players permission to open fence gates.
     */
    public FilterPermission getOpenGate() {
        return _openGate;
    }

    /**
     * Set the players permission to open fence gates.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setOpenGate(FilterPermission permission) {
        PreCon.notNull(permission);

        _openGate = permission;
        _dataNode.set("open-gate", permission);
        _dataNode.save();
    }

    /**
     * Get the players permission to open trap doors.
     */
    public FilterPermission getOpenTrapDoor() {
        return _openTrapDoor;
    }

    /**
     * Set the players permission to open trap doors.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setOpenTrapDoor(FilterPermission permission) {
        PreCon.notNull(permission);

        _openTrapDoor = permission;
        _dataNode.set("open-trap-door", permission);
        _dataNode.save();
    }

    /**
     * Get the players permission to open chests.
     */
    public FilterPermission getOpenChest() {
        return _openChest;
    }

    /**
     * Set the players permission to open chests.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setOpenChest(FilterPermission permission) {
        PreCon.notNull(permission);

        _openChest = permission;
        _dataNode.set("open-chest", permission);
        _dataNode.save();
    }

    /**
     * Get the players permission to use chat.
     */
    public FilterPermission getChat() {
        return _chat;
    }

    /**
     * Set the players permission to use chat.
     *
     * @param permission  The {@link FilterPermission}.
     */
    public void setChat(FilterPermission permission) {
        PreCon.notNull(permission);

        _chat = permission;
        _dataNode.set("chat", permission);
        _dataNode.save();
    }

    /**
     * Get the players permission to trample crops.
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
        dataNode.set("pvp", _pvp);
        dataNode.set("break", _break);
        dataNode.set("place", _place);
        dataNode.set("ignite", _ignite);
        dataNode.set("use-machines", _useMachines);
        dataNode.set("throw-switch", _throwSwitch);
        dataNode.set("open-door", _openDoor);
        dataNode.set("open-chest", _openChest);
        dataNode.set("open-gate", _openGate);
        dataNode.set("open-trap-door", _openTrapDoor);
        dataNode.set("chat", _chat);
        dataNode.set("trample-crops", _trampleCrops);
    }

    @Override
    public void deserialize(IDataNode dataNode) throws DeserializeException {

        _dataNode = dataNode;

        _pvp = dataNode.getEnum("pvp",
                FilterPermission.DEFAULT, FilterPermission.class);

        _break = dataNode.getEnum("break",
                FilterPermission.DEFAULT, FilterPermission.class);

        _place = dataNode.getEnum("place",
                FilterPermission.DEFAULT, FilterPermission.class);

        _ignite = dataNode.getEnum("ignite",
                FilterPermission.DEFAULT, FilterPermission.class);

        _useMachines = dataNode.getEnum("use-machines",
                FilterPermission.DEFAULT, FilterPermission.class);

        _throwSwitch = dataNode.getEnum("throw-switch",
                FilterPermission.DEFAULT, FilterPermission.class);

        _openDoor = dataNode.getEnum("open-door",
                FilterPermission.DEFAULT, FilterPermission.class);

        _openChest = dataNode.getEnum("open-chest",
                FilterPermission.DEFAULT, FilterPermission.class);

        _openGate = dataNode.getEnum("open-gate",
                FilterPermission.DEFAULT, FilterPermission.class);

        _openTrapDoor = dataNode.getEnum("open-trap-door",
                FilterPermission.DEFAULT, FilterPermission.class);

        _chat = dataNode.getEnum("chat",
                FilterPermission.DEFAULT, FilterPermission.class);

        _trampleCrops = dataNode.getEnum("trample-crops",
                FilterPermission.DEFAULT, FilterPermission.class);
    }
}
