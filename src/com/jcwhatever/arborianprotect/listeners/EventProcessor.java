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

package com.jcwhatever.arborianprotect.listeners;

import com.jcwhatever.arborianprotect.ArborianProtect;
import com.jcwhatever.arborianprotect.IProtected;
import com.jcwhatever.arborianprotect.filters.FilterPermission;
import com.jcwhatever.arborianprotect.regions.ProtectedRegion;
import com.jcwhatever.arborianprotect.worlds.ProtectedWorld;
import com.jcwhatever.nucleus.Nucleus;

import org.bukkit.Location;
import org.bukkit.event.Cancellable;

import java.util.List;

/**
 * Process an event based on filter settings for the location of the event.
 */
public abstract class EventProcessor<T> {

    /**
     * Process an event and cancel or allow based on region and world filter settings.
     *
     * @param location  The location of the event.
     * @param event     The event.
     *
     * @return  True if the event was processed, False if no action taken.
     */
    public boolean processEvent(Location location, T event) {
        // check regions first

        // cancel everything until plugin is finished loading
        if (!ArborianProtect.getPlugin().isLoaded()) {
            setCancelled(event, true);
            return true;
        }

        List<ProtectedRegion> regions = Nucleus.getRegionManager().getRegions(
                location, ProtectedRegion.class);

        for (ProtectedRegion region : regions) {

            FilterPermission permission = getPermission(region);
            if (processPermission(permission, event))
                return true;
        }

        // check world

        String worldName = location.getWorld().getName();

        ProtectedWorld world = ArborianProtect.getWorldManager().get(worldName);
        if (world != null) {
            FilterPermission permission = getPermission(world);
            if (processPermission(permission, event))
                return true;
        }

        return false;
    }

    /**
     * Invoked to get permission from an {@link IProtected} instance.
     *
     * @param target  The target to get a permission from.
     */
    protected abstract FilterPermission getPermission(IProtected target);

    /**
     * Invoked to process a {@link FilterPermission}.
     *
     * @param permission  The permission for the event.
     * @param event       The event.
     * @return
     */
    protected boolean processPermission(FilterPermission permission, T event) {
        switch (permission) {
            case ALLOW:
                setCancelled(event, false);
                return true;
            case DENY:
                setCancelled(event, true);
                return true;
            default:
                return false;
        }
    }

    /**
     * Invoked to set the cancelled state of an event.
     *
     * @param event        The event.
     * @param isCancelled  True to cancel, false to un-cancel.
     */
    protected void setCancelled(T event, boolean isCancelled) {
        if (event instanceof Cancellable)
            ((Cancellable) event).setCancelled(isCancelled);
    }
}