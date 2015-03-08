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
import com.jcwhatever.arborianprotect.filters.MobSpawnFilter;
import com.jcwhatever.arborianprotect.filters.FilterPolicy;
import com.jcwhatever.arborianprotect.regions.ProtectedRegion;
import com.jcwhatever.arborianprotect.worlds.ProtectedWorld;
import com.jcwhatever.nucleus.Nucleus;

import org.bukkit.entity.Chicken;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import java.util.List;

/**
 * Mob spawn event listener
 */
public class MobSpawnListener implements Listener {

    @EventHandler(priority = EventPriority.HIGH, ignoreCancelled = true)
    private void onCreatureSpawn(CreatureSpawnEvent event) {

        // check regions first

        List<ProtectedRegion> regions = Nucleus.getRegionManager().getRegions(
                event.getLocation(), ProtectedRegion.class);

        for (ProtectedRegion region : regions) {
            if (processSpawnEvent(region, event))
                return;
        }

        // check world

        String worldName = event.getLocation().getWorld().getName();

        ProtectedWorld world = ArborianProtect.getWorldManager().get(worldName);
        if (world != null) {
            processSpawnEvent(world, event);
        }

        if (!event.isCancelled() && event.getEntity() instanceof Chicken &&
                event.getLocation().getWorld().getName().equalsIgnoreCase("Spawn")) {
            throw new IllegalStateException();
        }
    }

    private boolean processSpawnEvent(IProtected target, CreatureSpawnEvent event) {

        EntityType type = event.getEntityType();
        SpawnReason reason = event.getSpawnReason();

        MobSpawnFilter filter = target.getMobSpawnFilter();

        boolean canSpawn = filter.canSpawn(type, reason);

        if (canSpawn && filter.getPolicy() == FilterPolicy.WHITELIST) {
            return true;
        }
        else if (!canSpawn && filter.getPolicy() == FilterPolicy.BLACKLIST) {
            event.setCancelled(true);
            return true;
        }
        return false;
    }
}
