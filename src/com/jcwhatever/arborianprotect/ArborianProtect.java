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

import com.jcwhatever.arborianprotect.commands.Dispatcher;
import com.jcwhatever.arborianprotect.listeners.BlockListener;
import com.jcwhatever.arborianprotect.listeners.MobSpawnListener;
import com.jcwhatever.arborianprotect.listeners.PlayerListener;
import com.jcwhatever.arborianprotect.regions.ProtectedRegionManager;
import com.jcwhatever.arborianprotect.worlds.ProtectedWorldManager;
import com.jcwhatever.nucleus.NucleusPlugin;
import com.jcwhatever.nucleus.storage.DataPath;
import com.jcwhatever.nucleus.storage.DataStorage;
import com.jcwhatever.nucleus.storage.IDataNode;
import com.jcwhatever.nucleus.utils.text.TextColor;

/**
 * A lightweight world and region protection plugin.
 */
public class ArborianProtect extends NucleusPlugin {

    private static ArborianProtect _instance;

    private ProtectedWorldManager _worldManager;
    private ProtectedRegionManager _regionManager;

    /**
     * Get the current plugin instance.
     */
    public static ArborianProtect getPlugin() {
        return _instance;
    }

    /**
     * Get the world manager.
     */
    public static ProtectedWorldManager getWorldManager() {
        return _instance._worldManager;
    }

    /**
     * Get the region manager.
     */
    public static ProtectedRegionManager getRegionManager() {
        return _instance._regionManager;
    }

    @Override
    public String getChatPrefix() {
        return TextColor.WHITE + "[" + TextColor.GREEN + "Protect" + TextColor.WHITE + "] ";
    }

    @Override
    public String getConsolePrefix() {
        return "[ArborianProtect] ";
    }

    @Override
    protected void onPreEnable() {

        _instance = this;
    }

    @Override
    protected void onPostPreEnable() {

        IDataNode worldNode = DataStorage.get(this, new DataPath("worlds"));
        worldNode.load();

        IDataNode regionNode = DataStorage.get(this, new DataPath("regions"));
        regionNode.load();

        _worldManager = new ProtectedWorldManager(worldNode);
        _regionManager = new ProtectedRegionManager(regionNode);

        // setup listeners in post pre-enable to ensure they
        // are running when server first starts
        registerEventListeners(
                new BlockListener(),
                new MobSpawnListener(),
                new PlayerListener());
    }

    @Override
    protected void onEnablePlugin() {

        registerCommands(new Dispatcher(this));
    }

    @Override
    protected void onDisablePlugin() {

        _instance = null;
    }
}
