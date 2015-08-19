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
import com.jcwhatever.arborianprotect.filters.FilterPolicy;
import com.jcwhatever.arborianprotect.filters.MobSpawnFilter;
import com.jcwhatever.arborianprotect.regions.ProtectedRegion;
import com.jcwhatever.arborianprotect.worlds.ProtectedWorld;
import com.jcwhatever.nucleus.Nucleus;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;
import org.bukkit.event.entity.SpawnerSpawnEvent;

import java.util.List;

/**
 * Mob spawn event listener
 */
public class MobSpawnListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
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
    }

    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    private void onSpawnerSpawn(SpawnerSpawnEvent event) {

        List<ProtectedRegion> regions = Nucleus.getRegionManager().getRegions(
                event.getLocation(), ProtectedRegion.class);

        for (ProtectedRegion region : regions) {
            if (processSpawnerSpawnEvent(region, event))
                return;
        }

        // check world

        String worldName = event.getLocation().getWorld().getName();

        ProtectedWorld world = ArborianProtect.getWorldManager().get(worldName);
        if (world != null) {
            processSpawnerSpawnEvent(world, event);
        }
    }

    private boolean processSpawnEvent(IProtected target, CreatureSpawnEvent event) {

        EntityType type = event.getEntityType();
        SpawnReason reason = event.getSpawnReason();

        if (reason == SpawnReason.NATURAL && processNaturalSpawnEvent(target, event))
            return true;

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

    private boolean processNaturalSpawnEvent(IProtected target, CreatureSpawnEvent event) {

        MobSpawnFilter filter = target.getMobSpawnFilter();

        if (!filter.isNaturalLimitEnabled())
            return false;

        int totalEntities = countEntities(event.getLocation(), filter.getSpawnerLimitRadius());

        if (totalEntities >= filter.getNaturalLimit()) {
            event.setCancelled(true);
            return true;
        }
        else {
            return false;
        }
    }

    private boolean processSpawnerSpawnEvent(IProtected target, SpawnerSpawnEvent event) {

        MobSpawnFilter filter = target.getMobSpawnFilter();

        if (!filter.isSpawnerLimitEnabled())
            return false;

        int totalEntities = countEntities(event.getLocation(), filter.getSpawnerLimitRadius());

        if (totalEntities >= filter.getSpawnerLimit()) {
            event.getSpawner().setDelay(filter.getSpawnerDelayLimited());
            event.setCancelled(true);
            return true;
        }
        else {
            event.getSpawner().setDelay(filter.getSpawnerDelay());
            return false;
        }
    }

    private int countEntities(Location location, int chunkRadius) {

        int chunkX = location.getBlockX() >> 4;
        int chunkZ = location.getBlockZ() >> 4;
        int result = 0;

        for (int x=chunkX - chunkRadius; x < chunkX + chunkRadius; x++) {
            for (int z=chunkZ - chunkRadius; z < chunkZ + chunkRadius; z++) {

                Chunk chunk = location.getWorld().getChunkAt(x, z);
                result += chunk.getEntities().length;
            }
        }

        return result;
    }
}
